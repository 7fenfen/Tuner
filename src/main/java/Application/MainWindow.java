package Application;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 主界面设计，使用FlatLaf+swing
 **/

public class MainWindow {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // 设置FlatLaf外观
        } catch (Exception ex) {
            System.err.println("Error");
        }
        SwingUtilities.invokeLater(() -> {
            SplashWindow splashWindow = new SplashWindow();
            splashWindow.setVisible(true); // 显示Splash窗口

            splashWindow.startCountdown(MainWindow::showMainWindow); // 开始倒计时，完成后显示主窗口
        });    }

    private static void showMainWindow() {
        JFrame mainFrame = new JFrame("Tuning & Metronome");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 600);
        mainFrame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = getjTabbedPane();

        JPanel tabWrapperPanel = new JPanel(new BorderLayout());
        tabWrapperPanel.setBorder(new EmptyBorder(3, 5, 0, 5));
        tabWrapperPanel.add(tabbedPane, BorderLayout.CENTER);

        mainFrame.add(tabWrapperPanel, BorderLayout.NORTH);

        JPanel emptyCenterPanel = new JPanel();
        mainFrame.add(emptyCenterPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    private static JTabbedPane getjTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // 节拍器面板
        JPanel metronomePanel = new JPanel(new BorderLayout());
        JLabel metronomeLabel = new JLabel("节拍器界面", SwingConstants.CENTER);
        metronomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        metronomePanel.add(metronomeLabel, BorderLayout.CENTER);

        tabbedPane.addTab("调音器", new Tuner2Window());
        tabbedPane.addTab("节拍器", new MetronomeWindow());
        return tabbedPane;
    }
}
