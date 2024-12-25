package Application;

public class Piano extends Instrument {

    public Piano() {
        // 添加低音区音符
        soundMap.put("A0", 27.5F);
        soundMap.put("A#0", 29.14F);
        soundMap.put("B0", 30.87F);

        // 添加第一组音符
        soundMap.put("C1", 32.70F);
        soundMap.put("C#1", 34.65F);
        soundMap.put("D1", 36.71F);
        soundMap.put("D#1", 38.89F);
        soundMap.put("E1", 41.20F);
        soundMap.put("F1", 43.65F);
        soundMap.put("F#1", 46.25F);
        soundMap.put("G1", 49.00F);
        soundMap.put("G#1", 51.91F);
        soundMap.put("A1", 55.00F);
        soundMap.put("A#1", 58.27F);
        soundMap.put("B1", 61.74F);

        // 添加第二组音符
        soundMap.put("C2", 65.41F);
        soundMap.put("C#2", 69.30F);
        soundMap.put("D2", 73.42F);
        soundMap.put("D#2", 77.78F);
        soundMap.put("E2", 82.41F);
        soundMap.put("F2", 87.31F);
        soundMap.put("F#2", 92.50F);
        soundMap.put("G2", 98.00F);
        soundMap.put("G#2", 103.83F);
        soundMap.put("A2", 110.00F);
        soundMap.put("A#2", 116.54F);
        soundMap.put("B2", 123.47F);

        // 添加第三组音符
        soundMap.put("C3", 130.81F);
        soundMap.put("C#3", 138.59F);
        soundMap.put("D3", 146.83F);
        soundMap.put("D#3", 155.56F);
        soundMap.put("E3", 164.81F);
        soundMap.put("F3", 174.61F);
        soundMap.put("F#3", 185.00F);
        soundMap.put("G3", 196.00F);
        soundMap.put("G#3", 207.65F);
        soundMap.put("A3", 220.00F);
        soundMap.put("A#3", 233.08F);
        soundMap.put("B3", 246.94F);

        // 添加中央C和第四组音符
        soundMap.put("C4", 261.63F); // 中央C
        soundMap.put("C#4", 277.18F);
        soundMap.put("D4", 293.66F);
        soundMap.put("D#4", 311.13F);
        soundMap.put("E4", 329.63F);
        soundMap.put("F4", 349.23F);
        soundMap.put("F#4", 369.99F);
        soundMap.put("G4", 392.00F);
        soundMap.put("G#4", 415.30F);
        soundMap.put("A4", 440.00F); // 标准音A
        soundMap.put("A#4", 466.16F);
        soundMap.put("B4", 493.88F);

        // 添加第五组音符
        soundMap.put("C5", 523.25F);
        soundMap.put("C#5", 554.37F);
        soundMap.put("D5", 587.33F);
        soundMap.put("D#5", 622.25F);
        soundMap.put("E5", 659.26F);
        soundMap.put("F5", 698.46F);
        soundMap.put("F#5", 739.99F);
        soundMap.put("G5", 783.99F);
        soundMap.put("G#5", 830.61F);
        soundMap.put("A5", 880.00F);
        soundMap.put("A#5", 932.33F);
        soundMap.put("B5", 987.77F);

        // 添加第六组音符
        soundMap.put("C6", 1046.50F);
        soundMap.put("C#6", 1108.73F);
        soundMap.put("D6", 1174.66F);
        soundMap.put("D#6", 1244.51F);
        soundMap.put("E6", 1318.51F);
        soundMap.put("F6", 1396.91F);
        soundMap.put("F#6", 1479.98F);
        soundMap.put("G6", 1567.98F);
        soundMap.put("G#6", 1661.22F);
        soundMap.put("A6", 1760.00F);
        soundMap.put("A#6", 1864.66F);
        soundMap.put("B6", 1975.53F);

        // 添加第七组音符
        soundMap.put("C7", 2093.00F);
        soundMap.put("C#7", 2217.46F);
        soundMap.put("D7", 2349.32F);
        soundMap.put("D#7", 2489.02F);
        soundMap.put("E7", 2637.02F);
        soundMap.put("F7", 2793.83F);
        soundMap.put("F#7", 2959.96F);
        soundMap.put("G7", 3135.96F);
        soundMap.put("G#7", 3322.44F);
        soundMap.put("A7", 3520.00F);
        soundMap.put("A#7", 3729.31F);
        soundMap.put("B7", 3951.07F);

        // 添加第八组音符
        soundMap.put("C8", 4186.01F); // 最高音
    }

    @Override
    public String getName() {
        return "Piano";
    }

}
