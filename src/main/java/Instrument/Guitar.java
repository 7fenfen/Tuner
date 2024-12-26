package Instrument;

public class Guitar extends Instrument {

    public Guitar() {

        soundMap.put("E2", 82.41F);
        soundMap.put("F2", 87.31F);
        soundMap.put("F#2", 92.50F);
        soundMap.put("G2", 98.00F);
        soundMap.put("G#2", 103.83F);
        soundMap.put("A2", 110.00F);
        soundMap.put("A#2", 116.54F);
        soundMap.put("B2", 123.47F);

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
        soundMap.put("C4", 261.63F);
        soundMap.put("C#4", 277.18F);
        soundMap.put("D4", 293.66F);

        soundMap.put("D#4", 311.13F);
        soundMap.put("E4", 329.63F);
        soundMap.put("F4", 349.23F);
        soundMap.put("F#4", 369.99F);
        soundMap.put("G4", 392.00F);
        soundMap.put("G#4", 415.30F);
        soundMap.put("A4", 440.00F);

        soundMap.put("A#4", 466.16F);
        soundMap.put("B4", 493.88F);
        soundMap.put("C5", 523.25F);
        soundMap.put("C#5", 554.37F);
        soundMap.put("D5", 587.33F);
        soundMap.put("D#5", 622.25F);
        soundMap.put("E5", 659.26F);

    }

    @Override
    public String getName() {
        return "Guitar";
    }

}
