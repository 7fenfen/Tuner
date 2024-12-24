package Application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Splash界面设计，控制打开后显示的第一个界面（自动跳转）
 **/

class SplashWindow extends JFrame {
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel countdownLabel;
    private int countdown = 3;

    public SplashWindow() {
        super("启动中..."); // 窗口标题
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 禁止关闭
        setUndecorated(true); // 去除窗口边框
        setSize(400, 600); // 窗口大小
        setLocationRelativeTo(null); // 居中显示

        // 主容器并设置边距
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // 设置边距

        // 标题标签
        titleLabel = new JLabel("调音器和节拍器", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.CENTER);

        // 副标题标签
        subtitleLabel = new JLabel("Powered by 张恒鑫 and TIANT", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mainPanel.add(subtitleLabel, BorderLayout.NORTH);

        // 倒计时标签
        countdownLabel = new JLabel(String.format("%d秒后自动进入", countdown), SwingConstants.CENTER);
        countdownLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mainPanel.add(countdownLabel, BorderLayout.SOUTH);

        // 添加主容器到窗口
        add(mainPanel);
    }

    public void startCountdown(Runnable onComplete) {
        Timer timer = new Timer(1000, e -> {
            countdown--;
            countdownLabel.setText(String.format("%d秒后自动进入", countdown));
            if (countdown < 0) {
                ((Timer) e.getSource()).stop();
                dispose(); // 关闭Splash窗口
                onComplete.run(); // 执行完成后需要执行的任务 (显示主窗口)
            }
        });
        timer.start();
    }
}