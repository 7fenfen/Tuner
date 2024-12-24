package Application;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;

import javax.sound.sampled.*;

public class PitchDetector {

    public static void main(String[] args) {
        try {
            // 定义音频格式
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);

            // 获取麦克风输入
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            line.open(format);
            line.start();

            // 使用 TarsosDSP 创建音频调度器
            AudioInputStream audioStream = new AudioInputStream(line);
            AudioDispatcher dispatcher = new AudioDispatcher(new JVMAudioInputStream(audioStream), 1024, 512);

            // 定义音高处理器
            PitchDetectionHandler pitchHandler = (result, event) -> {
                float pitch = result.getPitch(); // 获取频率
                if (pitch != -1) { // pitch 为 -1 表示无音高检测
                    System.out.printf("Detected pitch: %.2f Hz%n", pitch);
                }
            };

            dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN, 44100, 1024, pitchHandler));

            // 开始处理
            new Thread(dispatcher).start();
            System.out.println("Listening for audio... Press Ctrl+C to exit.");

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
