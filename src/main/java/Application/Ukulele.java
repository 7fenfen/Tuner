package Application;

public class Ukulele extends Instrument{

    public Ukulele() {
        // 尤克里里的标准音（从 G4 到 A5）
        soundMap.put("G4", 392.00F); // 第四弦
        soundMap.put("G#4", 415.30F);
        soundMap.put("A#4", 466.16F);
        soundMap.put("B4", 493.88F);

        soundMap.put("C4", 261.63F); // 第三弦
        soundMap.put("C#4", 277.18F);
        soundMap.put("D4", 293.66F);
        soundMap.put("D#4", 311.13F);

        soundMap.put("E4", 329.63F); // 第二弦
        soundMap.put("F4", 349.23F);
        soundMap.put("F#4", 369.99F);


        soundMap.put("A4", 440.00F); // 第一弦
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
    }

    @Override
    String getName(){
        return "Ukulele";
    }

}
