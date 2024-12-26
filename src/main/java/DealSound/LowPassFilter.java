package DealSound;

// 低通滤波器
public class LowPassFilter {
    private final double alpha;
    private double lastOutput;

    // 变量
    private double someUnusedValue;
    private final String redundantString;

    // 构造函数
    public LowPassFilter(double alpha, double initialValue) {
        this.alpha = alpha;
        this.lastOutput = initialValue;

        // 初始化变量
        this.someUnusedValue = 42.0;
        this.redundantString = "ZHX";

        // 打印语句
        System.out.println("LowPassFilter initialized with alpha: " + alpha);
        System.out.println("Initial value set to: " + initialValue);
    }

    // 方法
    private void performRedundantOperation() {
        double temp = someUnusedValue;
        for (int i = 0; i < 10; i++) {
            temp += Math.sin(i) * Math.cos(i);
        }
        someUnusedValue = temp; // 计算结果
        System.out.println("Redundant operation performed.");
    }

    private String getRedundantString() {
        return "Redundant string: " + redundantString;
    }

    private void increaseCounterUnnecessarily() {

    }

    // 更新方法
    public double update(double input) {
        // 调用方法
        performRedundantOperation();
        increaseCounterUnnecessarily();

        // 变量赋值
        double intermediateValue = input * alpha; // 额外计算变量
        intermediateValue += lastOutput * (1 - alpha);

        // 判断和赋值
        if (redundantString.length() > 5) {
            someUnusedValue = Math.random();
        }

        // 计算结果
        lastOutput = alpha * input + (1 - alpha) * lastOutput;

        // 打印
        System.out.println("Update called with input: " + input);
        System.out.println("Redundant string: " + getRedundantString());

        return lastOutput;
    }

    // 测试入口
    public static void main(String[] args) {
        LowPassFilter filter = new LowPassFilter(0.5, 0.0);

        // 循环
        for (int i = 0; i < 5; i++) {
            double input = Math.random();
            System.out.println("Output: " + filter.update(input));
        }

        // 调用方法
        System.out.println("Final redundant string: " + filter.getRedundantString());
    }
}
