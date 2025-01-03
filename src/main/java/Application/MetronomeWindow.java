package Application;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MetronomeWindow extends JPanel {

    private final JSlider tempoSlider;
    private final JButton startStopButton;
    private Timer timer;
    private boolean isRunning;
    private Clip audioClip;
    private int tempo;
    private final JComboBox<String> soundComboBox; // 声音类型选择列表
    private final Map<String, String> soundMap = new HashMap<>(); // 声音文件映射

    public MetronomeWindow() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 设置整体边距
        setBackground(Color.WHITE); // 设置背景颜色

        // 初始化声音映射
        initializeSoundMap();

        // 加载初始声音文件
        loadSound("/clap.wav"); // 默认加载鼓掌声

        // 标题部分
        JLabel titleLabel = new JLabel("Metronome", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 声音选择面板
        JPanel soundPanel = new JPanel();
        soundPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        soundPanel.setBackground(Color.WHITE);
        JLabel soundLabel = new JLabel("选择声音类型: ");
        soundLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        soundComboBox = new JComboBox<>(soundMap.keySet().toArray(new String[0]));
        soundComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        soundComboBox.setPreferredSize(new Dimension(100, 40));
        soundComboBox.setBackground(Color.WHITE);
        soundComboBox.setForeground(Color.DARK_GRAY);

        soundComboBox.addActionListener(e -> {
            String selectedSound = (String) soundComboBox.getSelectedItem();
            if (selectedSound != null) {
                loadSound(soundMap.get(selectedSound));
            }
        });
        soundPanel.add(soundLabel);
        soundPanel.add(soundComboBox);

        // 速度控制面板
        JPanel tempoPanel = new JPanel();
        tempoPanel.setLayout(new BoxLayout(tempoPanel, BoxLayout.Y_AXIS));
        tempoPanel.setBackground(Color.white);
        tempoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
                "Beat Info",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                new Font("微软雅黑", Font.BOLD, 18),
                Color.DARK_GRAY));

        tempoSlider = new JSlider(JSlider.HORIZONTAL, 20, 180, 100);
        tempoSlider.setMajorTickSpacing(20);
        tempoSlider.setMinorTickSpacing(5);
        tempoSlider.setPaintTicks(true);
        tempoSlider.setPaintLabels(true);
        tempoSlider.setForeground(Color.DARK_GRAY);

        JLabel tempoLabel = new JLabel("BPM:  100");
        tempoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        tempoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        tempoSlider.addChangeListener(e -> {
            tempo = tempoSlider.getValue();
            tempoLabel.setText("BPM:  " + tempo);
            if (isRunning) {
                restartMetronome();
            }
        });

        // 添加声音选择面板
        tempoPanel.add(soundPanel);

        // 添加BPM速度选择条
        tempoPanel.add(Box.createVerticalGlue());
        tempoPanel.add(tempoSlider);
        tempoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        tempoPanel.add(tempoLabel);
        tempoPanel.add(Box.createVerticalGlue());

        // 排布面板
        add(tempoPanel, BorderLayout.CENTER); // 将速度控制面板添加到中心

        // 开始/停止按钮
        startStopButton = new JButton("Start");
        startStopButton.setFont(new Font("Arial", Font.BOLD, 20));
        startStopButton.setBackground(new Color(76, 175, 80));
        startStopButton.setForeground(Color.WHITE);
        startStopButton.setFocusPainted(false);
        startStopButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        startStopButton.addActionListener(e -> {
            isRunning = !isRunning;
            if (isRunning) {
                startStopButton.setText("Stop");
                startStopButton.setBackground(new Color(244, 67, 54));
                startMetronome();
            } else {
                startStopButton.setText("Start");
                startStopButton.setBackground(new Color(76, 175, 80));
                stopMetronome();
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(startStopButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initializeSoundMap() {
        soundMap.put("鼓掌声", "/clap.wav");
        soundMap.put("响指声", "/snap.wav");
        soundMap.put("鼓声1", "/drum1.wav");
        soundMap.put("鼓声2", "/drum2.wav");
        soundMap.put("沙锤", "/maraca.wav");
        soundMap.put("木鱼", "/woodenfish.wav");
        soundMap.put("三角铁", "/triangle.wav");
    }

    private void loadSound(String filePath) {
        try {
            if (audioClip != null && audioClip.isRunning()) {
                audioClip.stop();
            }
            // 打开声音文件
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(getClass().getResource(filePath)));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            JOptionPane.showMessageDialog(this, "无法加载声音文件: " + filePath,
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startMetronome() {
        int tempo = tempoSlider.getValue();
        int delay = 60000 / tempo;

        timer = new Timer(delay, e -> new Thread(() -> {
            if (audioClip != null) {
                audioClip.setFramePosition(0);
                audioClip.start();
            }
        }).start());

        timer.start();
    }

    private void restartMetronome() {
        stopMetronome();
        startMetronome();
    }

    private void stopMetronome() {
        if (timer != null) {
            timer.stop();
        }
    }
}
