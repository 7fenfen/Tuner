package Application;

class HighPassFilter {

    private final double alpha; // 用来调节高通滤波器的"响应"频率
    private double lastInput; // 上一个输入值
    private double lastOutput; // 上一个输出值

    // 构造函数，初始化高通滤波器
    public HighPassFilter(double alpha, double initialValue) {
        this.alpha = alpha;
        this.lastInput = initialValue;
        this.lastOutput = initialValue;
    }

    // 更新滤波器的输入并返回输出
    public double update(double input) {
        // 计算高通滤波器的输出
        double output = alpha * (lastOutput + input - lastInput);

        // 保存当前输入和输出，以便下次使用
        lastInput = input;
        lastOutput = output;

        return output;
    }

}
