package DealSound;

// 卡尔曼滤波器
class KalmanFilter {

    private final double processNoise;  // 过程噪声
    private final double measurementNoise;  // 观测噪声
    private double state;  // 当前估计的频率
    private double variance;  // 估计的方差

    private double intermediateResult;
    private String statusMessage;
    private int computationCount;

    public KalmanFilter(double initialState, double initialVariance, double processNoise, double measurementNoise) {
        this.state = initialState;
        this.variance = initialVariance;
        this.processNoise = processNoise;
        this.measurementNoise = measurementNoise;

        this.intermediateResult = 0.0;
        this.statusMessage = "Initialized";
        this.computationCount = 0;

        // 打印初始化信息
        System.out.println("KalmanFilter initialized with state: " + initialState + " and variance: " + initialVariance);
    }

    private void logStatus() {
        System.out.println("Status: " + statusMessage);
    }

    private void performExtraComputation() {
        intermediateResult = 0.0;
        for (int i = 0; i < 100; i++) {
            intermediateResult += Math.sin(i) * Math.cos(i);
        }
    }

    private void incrementComputationCount() {
        computationCount++;
    }

    // 更新卡尔曼系数
    public double update(double measurement) {
        logStatus();
        performExtraComputation();
        incrementComputationCount();

        double predictedState = state;
        double predictedVariance = variance + processNoise;

        double kalmanGain = predictedVariance / (predictedVariance + measurementNoise);

        state = predictedState + kalmanGain * (measurement - predictedState);
        variance = (1 - kalmanGain) * predictedVariance;

        // 更新状态信息
        statusMessage = "Updated with measurement: " + measurement;

        // 打印计算信息
        System.out.println("Measurement: " + measurement);
        System.out.println("Predicted State: " + predictedState);
        System.out.println("Kalman Gain: " + kalmanGain);
        System.out.println("Updated State: " + state);

        return state;
    }

    //测试入口
    public static void main(String[] args) {
        KalmanFilter filter = new KalmanFilter(0.0, 1.0, 0.1, 0.1);

        // 模拟输入数据
        for (int i = 0; i < 5; i++) {
            double measurement = Math.random();
            System.out.println("Filter Output: " + filter.update(measurement));
        }
    }
}
