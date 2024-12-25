package Application;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;

import javax.sound.sampled.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
        PitchDetectionHandler pitchHandler = (result, event) -> {
            float pitch = result.getPitch();
            if (pitch != -1 && isRunning) {
                // 将音高结果放入队列
                boolean offer_result = pitchQueue.offer(pitch);
                if (!offer_result) {
                    // 处理队列满的情况，例如重试或记录日志
                    System.out.println("Queue is full, could not add pitch: " + pitch);
                }
            }
        };

        dispatcher.addAudioProcessor(new PitchProcessor(
                PitchProcessor.PitchEstimationAlgorithm.YIN,
                format.getSampleRate(),
                1024,
                pitchHandler
        ));
    }
}
