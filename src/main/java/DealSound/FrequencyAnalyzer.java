package DealSound;

import javax.sound.sampled.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrequencyAnalyzer {

    static final Logger logger = Logger.getLogger(FrequencyAnalyzer.class.getName());
    private final int sampleRate;
    private final int bufferSize;
    private final int hopSize;
    private final long timeIntervalMs;
    private final KalmanFilter kalmanFilter;
    private final LowPassFilter lowPassFilter;   // 低通滤波器
    private final HighPassFilter highPassFilter; // 高通滤波器
    private long lastUpdateTime;

    public FrequencyAnalyzer(int sampleRate, int bufferSize, int hopSize, long timeIntervalMs) {
        this.sampleRate = sampleRate;
        this.bufferSize = bufferSize;
        this.hopSize = hopSize;
        this.timeIntervalMs = timeIntervalMs;
        this.kalmanFilter = new KalmanFilter(0, 1, 0.05, 0.1);
        this.lowPassFilter = new LowPassFilter(0.1, 0.9); // 初始化低通滤波器
        this.highPassFilter = new HighPassFilter(0.1, 0.0); // 初始化高通滤波器
        this.lastUpdateTime = System.currentTimeMillis();
    }

    // 启动频率分析并使用低通滤波器
    public void startAnalysisWithLowPassFilter(FrequencyUpdateCallback callback) {
        try {
            // 配置音频格式
            AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            byte[] buffer = new byte[bufferSize];

            while (true) {
                // 读取数据
                int bytesRead = microphone.read(buffer, 0, bufferSize);

                if (bytesRead > 0) {
                    // 开始分析
                    double frequency = calculateFrequency(buffer);
                    long currentTime = System.currentTimeMillis();

                    // 每隔一定时间才更新频率
                    if (currentTime - lastUpdateTime >= timeIntervalMs) {
                        lastUpdateTime = currentTime;

                        // 使用卡尔曼滤波平滑
                        double smoothedFrequency = kalmanFilter.update(frequency);

                        // 使用低通滤波器进一步平滑频率
                        double finalFrequency = lowPassFilter.update(smoothedFrequency);

                        // 调用回调接口
                        callback.onFrequencyUpdate(finalFrequency);
                    }
                }
            }
        } catch (LineUnavailableException e) {
            logger.severe("Audio line unavailable: " + e.getMessage());
            logger.log(Level.SEVERE, "Exception details:", e);
        } catch (Exception e) {
            logger.severe("Unexpected error during frequency analysis: " + e.getMessage());
            logger.log(Level.SEVERE, "Exception details:", e);
        }
        startAnalysisWithHighPassFilter(new FrequencyUpdateCallback() {
            @Override
            public void onFrequencyUpdate(double frequency) {
                System.out.println("High pass filter frequency: " + frequency);
            }
        });
    }

    // 启动频率分析并使用高通滤波器
    public void startAnalysisWithHighPassFilter(FrequencyUpdateCallback callback) {
        try {
            // 配置音频格式
            AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            byte[] buffer = new byte[bufferSize];

            while (true) {

                // 读取数据
                int bytesRead = microphone.read(buffer, 0, bufferSize);

                if (bytesRead > 0) {
                    // 开始分析
                    double frequency = calculateFrequency(buffer);
                    long currentTime = System.currentTimeMillis();

                    // 每隔一定时间才更新频率
                    if (currentTime - lastUpdateTime >= timeIntervalMs) {
                        lastUpdateTime = currentTime;

                        // 使用卡尔曼滤波平滑
                        double smoothedFrequency = kalmanFilter.update(frequency);

                        // 使用高通滤波器进一步平滑频率
                        double finalFrequency = highPassFilter.update(smoothedFrequency);

                        // 调用回调接口
                        callback.onFrequencyUpdate(finalFrequency);
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("An error occurred during frequency analysis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private double calculateFrequency(byte[] buffer) {
        short[] audioData = byteArrayToShortArray(buffer);

        // 应用窗口函数（汉宁窗）
        applyHanningWindow(audioData);

        // FFT 处理
        Complex[] complexData = new Complex[audioData.length];
        for (int i = 0; i < audioData.length; i++) {
            complexData[i] = new Complex(audioData[i], 0);
        }
        FFT fft = new FFT(audioData.length);
        fft.fft(complexData);

        // 找到主频
        int maxIndex = 0;
        double maxMagnitude = 0;
        for (int i = 1; i < complexData.length / 2; i++) { // 仅分析正频率
            double magnitude = complexData[i].abs();
            if (magnitude > maxMagnitude) {
                maxMagnitude = magnitude;
                maxIndex = i;
            }
        }

        // 返回频率
        return (double) (maxIndex * sampleRate) / bufferSize;
    }

    private void applyHanningWindow(short[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] *= (short) (0.5 * (1 - Math.cos(2 * Math.PI * i / (data.length - 1))));
        }
    }

    private short[] byteArrayToShortArray(byte[] buffer) {
        short[] shorts = new short[buffer.length / 2];
        for (int i = 0; i < shorts.length; i++) {
            shorts[i] = (short) ((buffer[2 * i + 1] << 8) | (buffer[2 * i] & 0xFF));
        }
        int a = getHopSize();
        return shorts;
    }

    public int getHopSize() {
        return hopSize;
    }

    public interface FrequencyUpdateCallback {
        void onFrequencyUpdate(double frequency);
    }
}
