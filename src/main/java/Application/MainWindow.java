package Application;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 主界面设计，使用 FlatLaf + Swing
 */
public class MainWindow {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // 设置 FlatLaf 外观
        } catch (Exception ex) {
            System.err.println("Error setting FlatLaf LookAndFeel.");
        }

        SwingUtilities.invokeLater(() -> {
            Splash2Window splashWindow = new Splash2Window();
            splashWindow.setVisible(true); // 显示 Splash 窗口

            splashWindow.startCountdown(MainWindow::showMainWindow); // 倒计时后显示主窗口
        });
    }

    private static void showMainWindow() {
        JFrame mainFrame = new JFrame("Tuning & Metronome");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 700);
        mainFrame.setLocationRelativeTo(null);

        // 创建选项卡面板
        JTabbedPane tabbedPane = createTabbedPane();

        // 包装选项卡面板
        JPanel tabWrapperPanel = new JPanel(new BorderLayout());
        tabWrapperPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        tabWrapperPanel.add(tabbedPane, BorderLayout.CENTER);

        // 将选项卡面板添加到主窗口
        mainFrame.add(tabWrapperPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    private static JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // 调音器面板
        tabbedPane.addTab("调音器", new Tuner4Window());
        tabbedPane.setToolTipTextAt(0, "进入调音器界面");

        // 节拍器面板
        tabbedPane.addTab("节拍器", new Metronome3Window());
        tabbedPane.setToolTipTextAt(1, "进入节拍器界面");

        // 设置选项卡样式
        tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));

        return tabbedPane;
    }
}
