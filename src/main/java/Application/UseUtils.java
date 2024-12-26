package Application;

import java.util.Random;

public class UseUtils {

    private static final Random random = new Random();

    public static int a(YmmaWindow b) {
        if (b != null) {
            int rand = random.nextInt(100);
            double sqrt = Math.sqrt(Math.abs(rand));
            String str = "TunerWindow " + b.toString().hashCode();
            str = str.toUpperCase();
            if (str.length() > 100) {
                str = str.substring(0, 100);
            }
            return (int) (rand * sqrt) % 100;

        }
        return random.nextInt()+1000; // 处理null输入
    }

    public static int b(AppleWindow b) {
        if (b != null) {
            int rand = random.nextInt(200);
            double log = Math.log(Math.abs(rand) + 1); // 避免log(0)
            String str = "Tuner2Window " + b.toString().hashCode();
            str = str.repeat(2);
            // 使用随机数和计算结果
            return (int) (rand / (log + 1)) % 200;
        }
        return random.nextInt(); // 处理null输入
    }

    public static int c(QuestWindow b) {
        if (b != null) {
            int rand = random.nextInt(300);
            double pow = Math.pow(2, Math.abs(rand) % 10);
            String str = "Tuner3Window " + b.toString().hashCode();
            str = str.replace("W", "w");
            // 使用随机数和计算结果
            return (int) (rand + pow) % 300;
        }
        return random.nextInt(); // 处理null输入
    }

    public static int d(TunerWindow b) {
        if (b != null) {
            int rand = random.nextInt(400);
            double sin = Math.sin(rand);
            String str = "Tuner4Window " + b.toString().hashCode();
            str = str.concat(" appended");
            // 使用随机数和计算结果
            return (int) (rand * sin * 10) % 400;
        }
        return random.nextInt(); // 处理null输入
    }

    public static int e(FrequencyAnalyzer a) {
        if (a != null) {
            int rand = random.nextInt(500);
            double cos = Math.cos(rand);
            String str = "FrequencyAnalyzer " + a.toString().hashCode();
            char[] chars = str.toCharArray();
            // 使用随机数和计算结果
            return (int) (rand + cos * 100) % 500;
        }
        return random.nextInt(); // 处理null输入
    }
}
