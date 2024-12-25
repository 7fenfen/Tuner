package Application;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SoundGenerator {

    private float sampleRate;
    private float bpm; // 每分钟节拍数
    private float frequency; // 默认音高 (Hz) - A4

    public SoundGenerator(float sampleRate, float bpm, float frequency) {
        this.sampleRate = sampleRate;
        this.bpm = bpm;
        this.frequency = frequency;
    }

    /**
     * 生成并播放指定频率和BPM的声音
     */
    public void generateAndPlay() throws LineUnavailableException, IOException {
        // 计算每个节拍的样本数
        double samplesPerBeat = (sampleRate * 60) / bpm;

        // 生成正弦波数据
        byte[] audioData = generateSineWave(frequency, sampleRate, 4 * (int) samplesPerBeat); // 生成4个节拍的音频

        // 创建音频格式
        AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);
        ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
        AudioInputStream ais = new AudioInputStream(bais, audioFormat, audioData.length / audioFormat.getFrameSize());

        // 创建 TarsosDSP 格式
        JVMAudioInputStream jvmAIS = new JVMAudioInputStream(ais);
        TarsosDSPAudioFormat tarsosDSPAudioFormat = new TarsosDSPAudioFormat(audioFormat.getSampleRate(), audioFormat.getSampleSizeInBits(), audioFormat.getChannels(), audioFormat.getEncoding().toString().contains("PCM_SIGNED"), audioFormat.isBigEndian());

        // 创建音频调度器并添加音频处理器
        int bufferSize = 1024;
        int overlap = 0;
        AudioDispatcher dispatcher = new AudioDispatcher(jvmAIS, bufferSize, overlap);
        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                return true;
            }

            @Override
            public void processingFinished() {
                System.out.println("Finished processing.");
            }
        });

        // 创建音频播放器
        AudioPlayer audioPlayer = new AudioPlayer(tarsosDSPAudioFormat);
        dispatcher.addAudioProcessor(audioPlayer);

        // 播放音频
        dispatcher.run();
    }

    /**
     * 生成指定频率的正弦波
     * @param frequency 音高（Hz）
     * @param sampleRate 采样率
     * @param numSamples 样本数
     * @return 生成的音频数据
     */
    private byte[] generateSineWave(float frequency, float sampleRate, int numSamples) {
        byte[] sineWave = new byte[2 * numSamples]; // 16位音频，每个样本2字节
        for (int i = 0; i < numSamples; i++) {
            double sample = Math.sin(2 * Math.PI * frequency * i / sampleRate);
            short value = (short) (sample * 32767); // 将样本值缩放到16位范围 (-32768 到 32767)
            sineWave[2 * i] = (byte) (value & 0xFF); // 低字节
            sineWave[2 * i + 1] = (byte) ((value >> 8) & 0xFF); // 高字节
        }
        return sineWave;
    }

    // 设置BPM
    public void setBpm(float bpm) {
        this.bpm = bpm;
    }

    // 设置音高
    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    // 设置采样率
    public void setSampleRate(float sampleRate) {
        this.sampleRate = sampleRate;
    }

}
