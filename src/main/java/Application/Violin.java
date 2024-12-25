package Application;

public class Violin extends Instrument {
    // 小提琴音名和频率的哈希表

    public Violin() {
        // 小提琴的标准音（从 G3 到 E7）
        soundMap.put("G3", 196.00F);
        soundMap.put("G#3", 207.65F);
        soundMap.put("A3", 220.00F);
        soundMap.put("A#3", 233.08F);
        soundMap.put("B3", 246.94F);

        soundMap.put("C4", 261.63F);
        soundMap.put("C#4", 277.18F);
        soundMap.put("D4", 293.66F);
        soundMap.put("D#4", 311.13F);
        soundMap.put("E4", 329.63F);
        soundMap.put("F4", 349.23F);
        soundMap.put("F#4", 369.99F);
        soundMap.put("G4", 392.00F);

        soundMap.put("A4", 440.00F);
        soundMap.put("A#4", 466.16F);
        soundMap.put("B4", 493.88F);
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
        soundMap.put("C6", 1046.50F);
        soundMap.put("C#6", 1108.73F);
        soundMap.put("D6", 1174.66F);
        soundMap.put("D#6", 1244.51F);
        soundMap.put("E6", 1318.51F);

        soundMap.put("E7", 2637.02F); // 极限音
    }

    @Override
    public String getName() {
        return "Violin";
    }

}
