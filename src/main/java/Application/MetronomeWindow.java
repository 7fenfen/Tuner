package Application;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import javax.sound.sampled.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MetronomeWindow extends JPanel {

    private JSlider tempoSlider;
    private JButton startStopButton;
    private Timer timer;
    private boolean isRunning;
    private Clip audioClip;
    private int tempo;

    public MetronomeWindow() {
        setLayout(new BorderLayout());

        // 加载节拍声音
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/beat.wav")));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            JOptionPane.showMessageDialog(this, "无法加载节拍声音文件！", "错误", JOptionPane.ERROR_MESSAGE);
            return; // 退出，防止后续空指针异常
        }

        // 速度控制面板
        JPanel tempoPanel = new JPanel();
        tempoPanel.setLayout(new BoxLayout(tempoPanel, BoxLayout.Y_AXIS)); // 设置为垂直BoxLayout

        tempoSlider = new JSlider(JSlider.HORIZONTAL, 20, 200, 120);
        tempoSlider.setMajorTickSpacing(20);
        tempoSlider.setMinorTickSpacing(5);
        tempoSlider.setPaintTicks(true);
        tempoSlider.setPaintLabels(true);

        JLabel tempoLabel = new JLabel("BPM:  120");
        tempoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 标签居中

        tempoSlider.addChangeListener(e -> {
            tempo = tempoSlider.getValue();
            tempoLabel.setText("BPM:  " + tempo);
            if (isRunning) {
                restartMetronome();
            }
        });

        tempoPanel.add(Box.createVerticalGlue()); // 添加垂直间距
        tempoPanel.add(tempoSlider);
        tempoPanel.add(Box.createRigidArea(new Dimension(0, 5))); //添加固定间距
        tempoPanel.add(tempoLabel);
        tempoPanel.add(Box.createVerticalGlue()); // 添加垂直间距

        add(tempoPanel, BorderLayout.CENTER);

        // 开始/停止按钮
        startStopButton = new JButton("开始");
        startStopButton.addActionListener(e -> {
            isRunning = !isRunning;
            startStopButton.setText(isRunning ? "停止" : "开始");
            if (isRunning) {
                startMetronome();
            } else {
                stopMetronome();
            }
        });
        add(startStopButton, BorderLayout.NORTH);

        // 界面外观
//        setPreferredSize(new Dimension(400, 200));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setVisible(true);

    }

    private void startMetronome() {
        int tempo = tempoSlider.getValue();
        int delay = 60000 / tempo;

        timer = new Timer(delay, e -> {
            new Thread(() -> {
                if (audioClip != null) {
                    audioClip.setFramePosition(0);
                    audioClip.start();
                }
            }).start();
        });

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