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
    private JProgressBar progressBar;
    private Timer timer;
    private boolean isRunning;
    private int targetProgress = 0;
    private double currentProgress = 0;
    private double progressIncrement = 0;
    private Clip audioClip;
    private Timer progressTimer; // 用于平滑进度条的Timer

    public MetronomeWindow() {
        setLayout(new BorderLayout());

        // 加载节拍声音
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/beat.wav")));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.setFramePosition(100);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            JOptionPane.showMessageDialog(this, "无法加载节拍声音文件！", "错误", JOptionPane.ERROR_MESSAGE);
        }


        // 速度滑块
        tempoSlider = new JSlider(JSlider.HORIZONTAL, 20, 200, 120);
        tempoSlider.setMajorTickSpacing(20);
        tempoSlider.setMinorTickSpacing(5);
        tempoSlider.setPaintTicks(true);
        tempoSlider.setPaintLabels(true);
        tempoSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (isRunning) {
                    restartMetronome(); // 滑块改变时重启节拍器
                }
            }
        });
        add(tempoSlider, BorderLayout.CENTER);

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
        setPreferredSize(new Dimension(400, 200));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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