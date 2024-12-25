package Application;

// 低通滤波器
public class LowPassFilter {
    private final double alpha;
    private double lastOutput;

    public LowPassFilter(double alpha, double initialValue) {
        this.alpha = alpha;
        this.lastOutput = initialValue;
    }

    public double update(double input) {
        lastOutput = alpha * input + (1 - alpha) * lastOutput;
        return lastOutput;
    }
}
