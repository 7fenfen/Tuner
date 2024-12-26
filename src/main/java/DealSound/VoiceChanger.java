package DealSound;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.PitchShifter;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.writer.WriterProcessor;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Objects;

public class VoiceChanger {

    public void changePitch(String inputFilePath, String outputFilePath, float pitchFactor)
            throws UnsupportedAudioFileException, IOException {
        // 打开输入文件
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                Objects.requireNonNull(getClass().getResource(inputFilePath)));
        AudioFormat format = audioInputStream.getFormat();

        // 转换为 TarsosDSPAudioFormat
        TarsosDSPAudioFormat tarsosFormat = new TarsosDSPAudioFormat(
                format.getSampleRate(),
                format.getSampleSizeInBits(),
                format.getChannels(),
                format.isBigEndian(),
                format.isBigEndian()
        );

        // 创建音频分发器
        JVMAudioInputStream jvmAudioInputStream = new JVMAudioInputStream(audioInputStream);
        AudioDispatcher dispatcher = new AudioDispatcher(jvmAudioInputStream, 1024, 512);

        // 添加音调变换处理器
        PitchShifter pitchShifter = new PitchShifter(pitchFactor, format.getSampleRate(), 1024, 512);
        dispatcher.addAudioProcessor(pitchShifter);

        // 创建输出文件并设置写入处理器
        RandomAccessFile randomAccessFile = new RandomAccessFile(outputFilePath, "rw");
        WriterProcessor writerProcessor = new WriterProcessor(tarsosFormat, randomAccessFile);
        dispatcher.addAudioProcessor(writerProcessor);

        // 开始处理
        dispatcher.run();

        // 关闭文件流
        randomAccessFile.close();
    }

}
