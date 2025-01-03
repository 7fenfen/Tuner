package Application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;

public class YmmaWindow extends JPanel {

    private final JLabel pitchLabel;
    private final JLabel centsLabel;
    private final JProgressBar progressBar;
    private final DecimalFormat df = new DecimalFormat("#.00");

    public YmmaWindow() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        topPanel.add(new JLabel("440Hz"));
        topPanel.add(new JLabel("442Hz"));
        topPanel.add(new JLabel("音名"));
        topPanel.add(new JLabel("唱名"));
        add(topPanel, BorderLayout.NORTH);

        // 中间区域（仪表盘、音高、偏差）
        JPanel centerPanel = new JPanel(new GridBagLayout()); // GridBagLayout精细布局
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 组件之间的间距

        // 仪表盘（这里用JProgressBar简单代替)
        progressBar = new JProgressBar(-50, 50);
        progressBar.setStringPainted(true);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // 跨三列
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        centerPanel.add(progressBar, gbc);

        // 当前标准音
        JLabel currentStandardPitchLabel = new JLabel("当前标准音 440 Hz");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE; // 不填充
        gbc.anchor = GridBagConstraints.LINE_START;
        centerPanel.add(currentStandardPitchLabel, gbc);

        // 音高
        pitchLabel = new JLabel("D#4");
        pitchLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1; // 允许水平方向拉伸
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(pitchLabel, gbc);

        // 音准偏差
        centsLabel = new JLabel("+26");
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0; // 不允许水平方向拉伸
        gbc.anchor = GridBagConstraints.LINE_END;
        centerPanel.add(centsLabel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // 底部区域（键盘）
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(100, 149, 237));//设置背景颜色
        JLabel keyboardPlaceholder = new JLabel("键盘");
        keyboardPlaceholder.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(keyboardPlaceholder);
        add(bottomPanel, BorderLayout.SOUTH);

    }
}