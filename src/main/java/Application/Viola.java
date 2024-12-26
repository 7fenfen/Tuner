package Application;

public class Viola extends Instrument {

    public Viola() {
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
    }

    @Override
    public String getName() {
        return "Viola";
    }
}