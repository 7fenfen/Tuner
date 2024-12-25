package Application;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;

public class Tuner3Window extends JPanel {

    private JComboBox<String> instrumentComboBox;
    private JLabel currentPitchLabel;
    private JLabel currentStandardNoteLabel;
    private JLabel currentOffsetLabel;
    private Instrument currentInstrument;
    private PitchDetector detector;
    private JProgressBar negativeOffsetProgressBar, positiveOffsetProgressBar;

    public Tuner3Window() {

        // 音高检测器
        detector = new PitchDetector(44100, 16, 1, true, false);

        try {
            detector.start();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        setLayout(new BorderLayout(10, 10));
        // 设置整体边距
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // 设置背景颜色
        setBackground(Color.WHITE);

        // 标题
        JLabel titleLabel = new JLabel("Tuner", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // 主内容面板
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                "Tuner Info",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.DARK_GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // 乐器选择
        String[] instruments = {"吉他", "钢琴", "尤克里里", "小提琴"};
        instrumentComboBox = new JComboBox<>(instruments);
        instrumentComboBox.setSelectedItem("钢琴");
        instrumentComboBox.addActionListener(e -> updateInstrument());
        contentPanel.add(new JLabel("选择乐器: "), gbc);
        gbc.gridx = 1;
        contentPanel.add(instrumentComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // 当前音高
        currentPitchLabel = new JLabel("");
        currentPitchLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPanel.add(new JLabel("当前音高: "), gbc);
        gbc.gridx = 1;
        contentPanel.add(currentPitchLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // 当前标准音
        currentStandardNoteLabel = new JLabel("");
        currentStandardNoteLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPanel.add(new JLabel("当前标准音: "), gbc);
        gbc.gridx = 1;
        contentPanel.add(currentStandardNoteLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // 当前音分偏移
        currentOffsetLabel = new JLabel("");
        currentOffsetLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPanel.add(new JLabel("音分偏移量: "), gbc);
        gbc.gridx = 1;
        contentPanel.add(currentOffsetLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // 负偏移进度条
        negativeOffsetProgressBar = new JProgressBar(0, 80);
        negativeOffsetProgressBar.setStringPainted(true);
        negativeOffsetProgressBar.setForeground(Color.RED);
        contentPanel.add(new JLabel("负偏移: "), gbc);
        gbc.gridx = 1;
        contentPanel.add(negativeOffsetProgressBar, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // 正偏移进度条
        positiveOffsetProgressBar = new JProgressBar(0, 80);
        positiveOffsetProgressBar.setStringPainted(true);
        positiveOffsetProgressBar.setForeground(Color.GREEN);
        contentPanel.add(new JLabel("正偏移: "), gbc);
        gbc.gridx = 1;
        contentPanel.add(positiveOffsetProgressBar, gbc);

        add(contentPanel, BorderLayout.CENTER);

        // 初始化乐器
        updateInstrument();

        // 启动检测线程
        new Thread(() -> {
            while (true) {
                try {
                    Float pitch = detector.getNextPitch();
                    if (pitch != null && pitch > 0) {
                        SwingUtilities.invokeLater(() -> updateDisplay(pitch));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    private void updateInstrument() {
        String selectedInstrument = (String) Objects.requireNonNull(instrumentComboBox.getSelectedItem());
        switch (selectedInstrument) {
            case "吉他" -> currentInstrument = new Guitar();
            case "钢琴" -> currentInstrument = new Piano();
            case "尤克里里" -> currentInstrument = new Ukulele();
            case "小提琴" -> currentInstrument = new Violin();
            default -> currentInstrument = new Piano();
        }
    }

    private void updateDisplay(Float pitch) {
        currentPitchLabel.setText(String.format("%.2f Hz", pitch));

        if (currentInstrument != null) {
            String musicalScale = currentInstrument.getMusicalScale(pitch);
            int offset = currentInstrument.getOffset(pitch);

            currentStandardNoteLabel.setText(musicalScale);
            currentOffsetLabel.setText(String.format("%d cent", offset));

            negativeOffsetProgressBar.setValue(offset < 0 ? -offset : 0);
            positiveOffsetProgressBar.setValue(Math.max(offset, 0));
        }
    }
}
