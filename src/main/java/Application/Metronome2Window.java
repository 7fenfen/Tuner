package Application;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Metronome2Window extends JPanel {

    private JSlider tempoSlider;
    private JButton startStopButton;
    private Timer timer;
    private boolean isRunning;
    private Clip audioClip;
    private int tempo;

    public Metronome2Window() {
        setLayout(new BorderLayout(10, 10));
        // 设置整体边距
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // 设置背景颜色
        setBackground(Color.WHITE);

        // 加载节拍声音
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(getClass().getResource("/clap.wav")));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            JOptionPane.showMessageDialog(this, "无法加载节拍声音文件！",
                    "错误", JOptionPane.ERROR_MESSAGE);
            return; // 退出，防止后续空指针异常
        }

        // 标题部分
        JLabel titleLabel = new JLabel("Metronome");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 速度控制面板
        JPanel tempoPanel = new JPanel();
        tempoPanel.setLayout(new BoxLayout(tempoPanel, BoxLayout.Y_AXIS));
        tempoPanel.setBackground(Color.WHITE);
        tempoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                "Beat Info",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                new Font("微软雅黑", Font.BOLD, 14),
                Color.DARK_GRAY));

        tempoSlider = new JSlider(JSlider.HORIZONTAL, 20, 220, 120);
        tempoSlider.setMajorTickSpacing(20);
        tempoSlider.setMinorTickSpacing(5);
        tempoSlider.setPaintTicks(true);
        tempoSlider.setPaintLabels(true);
        tempoSlider.setForeground(Color.DARK_GRAY);

        JLabel tempoLabel = new JLabel("BPM:  120");
        tempoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        tempoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        tempoSlider.addChangeListener(e -> {
            tempo = tempoSlider.getValue();
            tempoLabel.setText("BPM:  " + tempo);
            if (isRunning) {
                restartMetronome();
            }
        });

        tempoPanel.add(Box.createVerticalGlue());
        tempoPanel.add(tempoSlider);
        tempoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        tempoPanel.add(tempoLabel);
        tempoPanel.add(Box.createVerticalGlue());
        add(tempoPanel, BorderLayout.CENTER);

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
