package Instrument;

public class Erhu extends Instrument {

    public Erhu() {
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
        return "Erhu";
    }
}