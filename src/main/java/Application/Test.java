package Application;

import javax.sound.sampled.LineUnavailableException;

public class Test {
    public static void main(String[] args) throws LineUnavailableException {
        PitchDetector detector = new PitchDetector(44100, 16, 1, true, false);
        detector.start();
        new Thread(() -> {
            while (true) {
                try {
                    Float pitch = detector.getNextPitch();
                    System.out.printf("Detected pitch: %.2f Hz%n", pitch);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }
}
