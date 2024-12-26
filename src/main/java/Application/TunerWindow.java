package Application;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;

public class TunerWindow extends JPanel {

    private final JComboBox<String> instrumentComboBox;
    private final JLabel currentPitchLabel;
    private final JLabel currentStandardNoteLabel;
    private final JLabel currentOffsetLabel;
    private Instrument currentInstrument;
    private final PitchDetector detector;
    private final JProgressBar negativeOffsetProgressBar;
    private final JProgressBar positiveOffsetProgressBar;

    public TunerWindow() {

        // 音高检测器
        detector = new PitchDetector(44100, 16, 1, true, false);
        try {
            detector.start();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // 标题
        JLabel titleLabel = new JLabel("Tuner", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.DARK_GRAY);
        add(titleLabel, BorderLayout.NORTH);

        // 主内容面板
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
                "Tuner Info",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                new Font("微软雅黑", Font.BOLD, 18),
                Color.DARK_GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // 乐器选择
        String[] instruments = {"吉他", "钢琴", "尤克里里", "小提琴"};
        instrumentComboBox = new JComboBox<>(instruments);
        instrumentComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        instrumentComboBox.setSelectedItem("钢琴");
        instrumentComboBox.addActionListener(e -> updateInstrument());
        addLabeledComponent(contentPanel, "选择乐器: ", instrumentComboBox, gbc);

        // 当前音高
        currentPitchLabel = createValueLabel();
        addLabeledComponent(contentPanel, "当前音高: ", currentPitchLabel, gbc);

        // 当前标准音
        currentStandardNoteLabel = createValueLabel();
        addLabeledComponent(contentPanel, "当前标准音: ", currentStandardNoteLabel, gbc);

        // 当前音分偏移
        currentOffsetLabel = createValueLabel();
        addLabeledComponent(contentPanel, "音分偏移量: ", currentOffsetLabel, gbc);

        // 负偏移进度条
        negativeOffsetProgressBar = createCustomProgressBar(Color.RED);
        addLabeledComponent(contentPanel, "负偏移: ", negativeOffsetProgressBar, gbc);

        // 正偏移进度条
        positiveOffsetProgressBar = createCustomProgressBar(Color.GREEN);
        addLabeledComponent(contentPanel, "正偏移: ", positiveOffsetProgressBar, gbc);

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
            default -> currentInstrument = new Instrument();
        }
    }

    private void updateDisplay(Float pitch) {
        currentPitchLabel.setText(String.format("%.2f Hz", pitch));

        if (currentInstrument != null) {
            // 计算基准音
            String musicalScale = currentInstrument.getMusicalScale(pitch);
            int offset = currentInstrument.getOffset(pitch);

            // 更新显示
            currentStandardNoteLabel.setText(musicalScale);
            currentOffsetLabel.setText(String.format("%d cent", offset));

            // 更新偏移度
            negativeOffsetProgressBar.setValue(offset < 0 ? -offset : 0);
            positiveOffsetProgressBar.setValue(Math.max(offset, 0));
        }
    }

    private JLabel createValueLabel() {
        JLabel label = new JLabel("");
        label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        label.setForeground(Color.BLUE);
        return label;
    }

    private JProgressBar createCustomProgressBar(Color color) {
        JProgressBar progressBar = new JProgressBar(0, 80);
        progressBar.setPreferredSize(new Dimension(250, 30));
        progressBar.setStringPainted(true);
        progressBar.setForeground(color);
        progressBar.setFont(new Font("微软雅黑", Font.BOLD, 16));
        return progressBar;
    }

    private void addLabeledComponent(JPanel panel, String labelText, JComponent component, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("微软雅黑", Font.BOLD, 20));
        gbc.gridx = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(component, gbc);
        gbc.gridy++;
    }
}
