package Application;

public class MorinKhuur extends Instrument {

    public MorinKhuur() {
        soundMap.put("C2", 65.41F);
        soundMap.put("D2", 73.42F);
        soundMap.put("E2", 82.41F);
        soundMap.put("F2", 87.31F);
        soundMap.put("G2", 98.00F);
        soundMap.put("A2", 110.00F);
        soundMap.put("B2", 123.47F);
        soundMap.put("C3", 130.81F);
        soundMap.put("D3", 146.83F);
        soundMap.put("E3", 164.81F);
        soundMap.put("F3", 174.61F);
        soundMap.put("G3", 196.00F);
    }

    @Override
    public String getName() {
        return "Morin Khuur";
    }
}