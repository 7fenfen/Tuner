package Application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Splash 启动前界面设计
 */
class SplashWindow extends JFrame {
    private final JLabel countdownLabel;
    private int countdown = 3;

    public SplashWindow() {
        super("启动中...");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setSize(400, 600);
        setLocationRelativeTo(null);

        // 设置背景面板
        JPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 副标题放在 NORTH
        JLabel subtitleLabel = new JLabel("Powered by 张恒鑫", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        backgroundPanel.add(subtitleLabel, BorderLayout.NORTH);

        // 中间部分内容
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // 透明背景

        JLabel titleLabel = new JLabel("调音器和节拍器", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(titleLabel);

        JLabel englishTitleLabel = new JLabel("Tuning & Metronome", SwingConstants.CENTER);
        englishTitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        englishTitleLabel.setForeground(Color.LIGHT_GRAY);
        englishTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(englishTitleLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 40))); // 添加间距

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        // 倒计时放在 SOUTH
        countdownLabel = new JLabel(String.format("等待 %d 秒后自动进入", countdown), SwingConstants.CENTER);
        countdownLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        countdownLabel.setForeground(Color.WHITE);
        backgroundPanel.add(countdownLabel, BorderLayout.SOUTH);

        add(backgroundPanel);
    }

    public void startCountdown(Runnable onComplete) {
        Timer timer = new Timer(1000, e -> {
            countdown--;
            countdownLabel.setText(String.format("等待 %d 秒后自动进入", countdown));
            if (countdown < 0) {
                ((Timer) e.getSource()).stop();
                dispose();
                onComplete.run();
            }
        });
        timer.start();
    }

    /**
     * 自定义背景面板，绘制渐变背景
     */
    private static class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            // 设置渐变背景
            GradientPaint gradient = new GradientPaint(0, 0, new Color(45, 45, 45),
                    0, getHeight(), new Color(20, 20, 20));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
