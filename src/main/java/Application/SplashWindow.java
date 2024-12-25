package Application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Splash启动前界面设计
 **/


class SplashWindow extends JFrame {
    private final JLabel countdownLabel;
    private int countdown = 3;

    public SplashWindow() {
        super("启动中...");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setSize(400, 600);
        setLocationRelativeTo(null);

        // 主面板使用BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 副标题放在NORTH
        JLabel subtitleLabel = new JLabel("Powered by 张恒鑫", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mainPanel.add(subtitleLabel, BorderLayout.NORTH);

        // 中间部分的面板，用BoxLayout垂直排列
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // 标题
        JLabel titleLabel = new JLabel("调音器和节拍器", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(titleLabel);

        // 英文标题
        JLabel englishTitleLabel = new JLabel("Tuning & Metronome", SwingConstants.CENTER);
        englishTitleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        englishTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(englishTitleLabel);

        // 用Box.createRigidArea添加固定间距，而不是 glue
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40))); // 添加20像素的固定间距

        // 设置centerPanel的不透明性为false
        centerPanel.setOpaque(false);

        // 将centerPanel添加到另一个JPanel中，并设置其布局为GridBagLayout
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.add(centerPanel);

        // 设置wrapperPanel的不透明性为false
        wrapperPanel.setOpaque(false);

        // 将包装后的面板添加到主面板的CENTER区域
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);

        // 倒计时放在SOUTH
        countdownLabel = new JLabel(String.format("等待%d秒后自动进入", countdown), SwingConstants.CENTER);
        countdownLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
        mainPanel.add(countdownLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public void startCountdown(Runnable onComplete) {
        Timer timer = new Timer(1000, e -> {
            countdown--;
            countdownLabel.setText(String.format("等待%d秒后自动进入", countdown));
            if (countdown < 0) {
                ((Timer) e.getSource()).stop();
                dispose();
                onComplete.run();
            }
        });
        timer.start();
    }
}