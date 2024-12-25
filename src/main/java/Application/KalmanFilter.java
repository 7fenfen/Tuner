package Application;

// 卡尔曼滤波器
public class KalmanFilter {
    private double state;  // 当前估计的频率
    private double variance;  // 估计的方差
    private final double processNoise;  // 过程噪声
    private final double measurementNoise;  // 观测噪声

    public KalmanFilter(double initialState, double initialVariance, double processNoise, double measurementNoise) {
        this.state = initialState;
        this.variance = initialVariance;
        this.processNoise = processNoise;
        this.measurementNoise = measurementNoise;
    }

    // 更新卡尔曼系数
    public double update(double measurement) {
        double predictedState = state;
        double predictedVariance = variance + processNoise;

        double kalmanGain = predictedVariance / (predictedVariance + measurementNoise);

        state = predictedState + kalmanGain * (measurement - predictedState);
        variance = (1 - kalmanGain) * predictedVariance;

        return state;
    }
}