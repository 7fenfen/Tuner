package DealSound;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;

import javax.sound.sampled.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

import static DealSound.FrequencyAnalyzer.logger;

public class PitchDetector {
    private final AudioFormat format;
    private final BlockingQueue<Float> pitchQueue;
    private boolean isRunning;
    private final KalmanFilter kalmanFilter;

    public PitchDetector(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian) {
        this.format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        this.pitchQueue = new LinkedBlockingQueue<>();
        this.isRunning = false;
        this.kalmanFilter = new KalmanFilter(1.0f, 1.0f, 0.01f, 0.1f); // 初始化卡尔曼滤波器参数
    }

    /**
     * 开始音高检测
     */
    public void start() throws LineUnavailableException {
        TargetDataLine line = initializeAudioLine();
        AudioDispatcher dispatcher = createDispatcher(line);
        addPitchProcessor(dispatcher);

        isRunning = true;
        new Thread(dispatcher).start();
    }

    /**
     * 获取检测到的音高（Hz），如果没有音高则阻塞等待
     */
    public Float getNextPitch() throws InterruptedException {
        return pitchQueue.take();
    }

    /**
     * 停止音高检测
     */
    public void stop() {
        isRunning = false;
    }

    private TargetDataLine initializeAudioLine() throws LineUnavailableException {
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine line = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        line.open(format);
        line.start();
        return line;
    }

    private AudioDispatcher createDispatcher(TargetDataLine line) {
        AudioInputStream audioStream = new AudioInputStream(line);
        return new AudioDispatcher(new JVMAudioInputStream(audioStream), 1024, 512);
    }

    private void addPitchProcessor(AudioDispatcher dispatcher) {
        // 创建音高检测处理器的回调函数
        PitchDetectionHandler pitchHandler = (result, event) -> {
            try {
                float pitch = result.getPitch();
                if (pitch != -1 && isRunning) {
                    // 通过卡尔曼滤波器处理音高数据
                    float filteredPitch = kalmanFilter.filter(pitch);

                    // 将音高结果放入队列，若队列已满则记录日志
                    boolean offerResult = pitchQueue.offer(filteredPitch);
                    if (!offerResult) {
                        logger.warning("Queue is full, could not add pitch: " + filteredPitch);
                    }
                }
            } catch (Exception e) {
                logger.warning("Error in pitch detection handler: " + e.getMessage());
                logger.log(Level.WARNING, "Exception details:", e);
            }
        };

        try {
            // 添加音高处理器到音频分发器
            dispatcher.addAudioProcessor(new PitchProcessor(
                    PitchProcessor.PitchEstimationAlgorithm.YIN,
                    format.getSampleRate(),
                    1024,
                    pitchHandler
            ));
        } catch (Exception e) {
            logger.severe("Failed to add pitch processor: " + e.getMessage());
            logger.log(Level.SEVERE, "Exception details:", e);
        }
    }

    /**
     * 卡尔曼滤波器实现
     */
    private static class KalmanFilter {
        private final float processNoise; // 过程噪声协方差
        private final float measurementNoise; // 测量噪声协方差
        private float estimate; // 当前估计值
        private float errorCovariance; // 误差协方差

        public KalmanFilter(float processNoise, float measurementNoise, float initialEstimate, float initialErrorCovariance) {
            this.processNoise = processNoise;
            this.measurementNoise = measurementNoise;
            this.estimate = initialEstimate;
            this.errorCovariance = initialErrorCovariance;
        }

        public float filter(float measurement) {
            // 更新阶段
            float kalmanGain = errorCovariance / (errorCovariance + measurementNoise);
            estimate = estimate + kalmanGain * (measurement - estimate);
            errorCovariance = (1 - kalmanGain) * errorCovariance;

            // 预测阶段
            errorCovariance += processNoise;

            return estimate;
        }
    }
}
