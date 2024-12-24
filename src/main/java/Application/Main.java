package Application;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

/**
 * 主界面设计，使用FlatLaf+swing
 **/

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // 设置FlatLaf外观
        } catch (Exception ex) {
            System.err.println("Error");
        }
        SwingUtilities.invokeLater(() -> {
            SplashWindow splashWindow = new SplashWindow();
            splashWindow.setVisible(true); // 显示Splash窗口

            splashWindow.startCountdown(Main::showMainGUI); // 开始倒计时，完成后显示主窗口
        });    }

    private static void showMainGUI() {
        // 创建主窗口
        JFrame mainFrame = new JFrame("调音器和节拍器");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 600); // 设置固定窗口大小
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // 在主窗口中添加内容 (例如：添加一个标签)
        JLabel label = new JLabel("主界面", SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.BOLD, 24));
        mainFrame.add(label);
    }
}
