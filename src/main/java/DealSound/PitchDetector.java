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

    public PitchDetector(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian) {
        this.format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        this.pitchQueue = new LinkedBlockingQueue<>();
        this.isRunning = false;
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
                    // 将音高结果放入队列，若队列已满则记录日志
                    boolean offerResult = pitchQueue.offer(pitch);
                    if (!offerResult) {
                        logger.warning("Queue is full, could not add pitch: " + pitch);
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
}
