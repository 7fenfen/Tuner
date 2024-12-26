package Instrument;

public class Hulusi extends Instrument {

    public Hulusi() {
        soundMap.put("G4", 392.00F);
        soundMap.put("A4", 440.00F);
        soundMap.put("B4", 493.88F);
        soundMap.put("C5", 523.25F);
        soundMap.put("D5", 587.33F);
        soundMap.put("E5", 659.26F);
        soundMap.put("F#5", 739.99F);
        soundMap.put("G5", 783.99F);
        soundMap.put("A5", 880.00F);
        soundMap.put("B5", 987.77F);
    }

    @Override
    public String getName() {
        return "Hulusi";
    }
}
