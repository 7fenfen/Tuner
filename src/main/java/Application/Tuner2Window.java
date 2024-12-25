package Application;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tuner2Window extends JPanel {

    private JComboBox<String> instrumentComboBox;
    private JLabel currentPitchLabel;
    private JLabel currentStandardNoteLabel;
    private JLabel currentOffsetLabel;
    private Instrument currentInstrument;
    private PitchDetector detector;
    private JProgressBar negativeOffsetProgressBar, positiveOffsetProgressBar;


    public Tuner2Window() {
        detector = new PitchDetector(44100, 16, 1, true, false);
        try {
            detector.start();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        // 设置主面板布局为 BoxLayout，垂直排列
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 设置外边距

        // 乐器选择下拉列表
        String[] instruments = {"吉他", "钢琴", "尤克里里", "小提琴"};
        instrumentComboBox = new JComboBox<>(instruments);
        instrumentComboBox.setSelectedItem("钢琴"); // 默认选择钢琴
        instrumentComboBox.addActionListener(e -> updateInstrument());
        add(createPanel(instrumentComboBox));
        add(Box.createVerticalStrut(10)); // 添加垂直间距

        // 初始化乐器
        updateInstrument();

        // 当前音高标签
        currentPitchLabel = new JLabel("当前音高 - Hz");
        add(createPanel(currentPitchLabel));
        add(Box.createVerticalStrut(10)); // 添加垂直间距

        // 当前标准音标签
        currentStandardNoteLabel = new JLabel("当前标准音 - ");
        add(createPanel(currentStandardNoteLabel));
        add(Box.createVerticalStrut(10)); // 添加垂直间距

        // 当前音分偏移量标签
        currentOffsetLabel = new JLabel("当前音分偏移量 - ");
        add(createPanel(currentOffsetLabel));
        add(Box.createVerticalStrut(10)); // 添加垂直间距

        // 创建负音分偏移进度条面板
        JPanel negativeOffsetPanel = new JPanel(new BorderLayout());
        negativeOffsetProgressBar = new JProgressBar(0, 80);
        negativeOffsetProgressBar.setStringPainted(true);
        negativeOffsetPanel.add(negativeOffsetProgressBar, BorderLayout.CENTER);
        negativeOffsetPanel.add(new JLabel("负偏移", SwingConstants.CENTER), BorderLayout.EAST); // 添加“-”号
        add(createPanel(negativeOffsetPanel));
        add(Box.createVerticalStrut(10)); // 添加垂直间距

        // 创建正音分偏移进度条面板
        JPanel positiveOffsetPanel = new JPanel(new BorderLayout());
        positiveOffsetProgressBar = new JProgressBar(0, 80);
        positiveOffsetProgressBar.setStringPainted(true);
        positiveOffsetPanel.add(positiveOffsetProgressBar, BorderLayout.CENTER);
        positiveOffsetPanel.add(new JLabel("正偏移", SwingConstants.CENTER), BorderLayout.EAST); // 添加“+”号
        add(createPanel(positiveOffsetPanel));

//
//
//        // 当前音高标签
//        currentPitchLabel = new JLabel("当前音高 - Hz");
//        add(currentPitchLabel);
//
//        // 当前标准音标签
//        currentStandardNoteLabel = new JLabel("当前标准音 - ");
//        add(currentStandardNoteLabel);
//
//        // 当前音分偏移量标签
//        currentOffsetLabel = new JLabel("当前音分偏移量 - ");
//        add(currentOffsetLabel);

        // 启动检测线程
        new Thread(() -> {
            while (true) {
                try {
                    Float pitch = detector.getNextPitch();
                    if (pitch != null && pitch > 0) {
                        System.out.println("更新音高为" + pitch);
                        SwingUtilities.invokeLater(() -> updateDisplay(pitch));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    // 用来包装组件并设置边距
    private JPanel createPanel(JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(component, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 设置10的边距
        return panel;
    }

    private void updateInstrument() {
        String selectedInstrument = (String) Objects.requireNonNull(instrumentComboBox.getSelectedItem());
        switch (selectedInstrument) {
            case "吉他":
                currentInstrument = new Guitar();
                break;
            case "钢琴":
                currentInstrument = new Piano();
                break;
            case "尤克里里":
                currentInstrument = new Ukulele();
                break;
            case "小提琴":
                currentInstrument = new Violin();
                break;
            default:
                currentInstrument = new Piano(); // 默认钢琴
                break;
        }
    }

    private void updateDisplay(Float pitch) {
        currentPitchLabel.setText(String.format("当前音高 %.2f Hz", pitch));

        if (currentInstrument != null) {
            String musicalScale = currentInstrument.getMusicalScale(pitch);
            int offset = currentInstrument.getOffset(pitch);

            currentStandardNoteLabel.setText(String.format("当前标准音 %s", musicalScale));
            currentOffsetLabel.setText(String.format("当前音分偏移量 %d", offset));

            if (offset < 0) {
                negativeOffsetProgressBar.setValue((int) offset * -1);
                positiveOffsetProgressBar.setValue(0);
            } else {
                negativeOffsetProgressBar.setValue(0);
                positiveOffsetProgressBar.setValue((int) offset);
            }

        }
    }

}