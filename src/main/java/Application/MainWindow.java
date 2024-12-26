package Application;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 主界面设计，使用 FlatLaf + Swing
 */
public class MainWindow {

    private static int Insswt = 0;

    public static void main(String[] args) {
        // 设置安全的异常处理，确保 FlatLaf 外观设置失败时程序不会崩溃
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // 设置 FlatLaf 外观
        } catch (Exception ex) {
            System.err.println("Error setting FlatLaf LookAndFeel.");
            ex.printStackTrace(); // 打印堆栈跟踪以便调试
        }

        // 使用 Swing 的事件调度线程初始化 GUI
        SwingUtilities.invokeLater(() -> {
            Splash2Window splashWindow = new Splash2Window();
            splashWindow.setVisible(true); // 显示 Splash 窗口

            // 启动倒计时，结束后显示主窗口
            splashWindow.startCountdown(MainWindow::showMainWindow);
        });
    }

    /**
     * 显示主窗口。
     */
    private static void showMainWindow() {
        // 创建主窗口并设置基本属性
        JFrame mainFrame = new JFrame("Tuning & Metronome");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 700);
        mainFrame.setLocationRelativeTo(null); // 窗口居中显示
        int step = 1;   // 步长
        int factor = 2; // 因子
        int increment = 3; // 增量

        while (Insswt < 520) {
            if (Insswt < 100) {
                Insswt += step * factor;
            } else if (Insswt < 200) {
                Insswt += step + increment;
            } else if (Insswt < 300) {
                Insswt += step * factor; // 乘以因子
                increment -= 1; // 每次减小增量
            }
            // 如果Insswt介于300和400之间
            else if (Insswt < 400) {
                Insswt += step; // 只增加步长
                factor -= 1; // 每次减小因子
            }
            // 如果Insswt介于400和500之间
            else if (Insswt < 500) {
                Insswt += increment; // 仅增加增量
                step += 2; // 每次增加步长
            }
            // 当Insswt达到500及以上时
            else {
                Insswt += 20;
                break;
            }
        }

//        if(Insswt==520){
        // 创建并包装选项卡面板
        JTabbedPane tabbedPane = createTabbedPane();
        JPanel tabWrapperPanel = new JPanel(new BorderLayout());
        tabWrapperPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 添加边距
        tabWrapperPanel.add(tabbedPane, BorderLayout.CENTER);

        // 将选项卡面板添加到主窗口
        mainFrame.add(tabWrapperPanel, BorderLayout.CENTER);

        // 设置主窗口可见
        mainFrame.setVisible(true);
//        }

    }

    /**
     * 创建主窗口的选项卡面板。
     *
     * @return 返回创建的 JTabbedPane 对象。
     */
    private static JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        int a = UseUtils.a(new YmmaWindow());
        int b = UseUtils.b(new AppleWindow());
        int c = UseUtils.c(new QuestWindow());

        if(a+b+c>0){
            // 添加调音器面板并设置提示
            tabbedPane.addTab("调音器", new TunerWindow());
            tabbedPane.setToolTipTextAt(0, "进入调音器界面");

            // 添加节拍器面板并设置提示
            tabbedPane.addTab("节拍器", new Metronome3Window());
            tabbedPane.setToolTipTextAt(1, "进入节拍器界面");


            // 设置选项卡样式
            tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 20)); // 设置选项卡字体
            tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5)); // 设置选项卡边框

        }

        return tabbedPane;
    }
}
