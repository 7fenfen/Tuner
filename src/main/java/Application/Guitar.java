package Application;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Guitar implements Instrument {
    // 吉他6根弦的音名和频率的哈希表
    private final HashMap<String, Float> soundMap = new HashMap<>();

    public Guitar(){
        soundMap.put("E2", 82.41F);
        soundMap.put("A2", 110F);
        soundMap.put("D3", 146.83F);
        soundMap.put("G3", 196F);
        soundMap.put("B3", 246.94F);
        soundMap.put("E4", 329.63F);
    }

    @Override
    public String getName() {
        return "Guitar";
    }

    /**
     * @param pitch 测得频率
     * @return 与哪个音名最相近
     */
    @Override
    public String getMusicalScale(float pitch) {
        // 音名
        String result = "";
        // 偏移赫兹
        float offset = -1;
        // 暂存的偏移赫兹
        float temp;

        for (Map.Entry<String, Float> entry : soundMap.entrySet()) {
            if (result.isEmpty()) {
                // 初始化基准音
                result = entry.getKey();
                // 计算偏移量
                offset = pitch - entry.getValue();
            } else {
                temp = pitch - entry.getValue();
                // 如果与这个音相比偏移更小
                if (Math.abs(temp) < Math.abs(offset)) {
                    result = entry.getKey();
                    offset = temp;
                }
            }
        }

        return result;
    }

    /**
     * @param pitch 测得频率
     * @param benchmark 基准频率
     * @return 偏差的音分
     */

    @Override
    public int getOffset(float pitch, float benchmark){
        // 计算音分偏移量
        return (int)((Math.log(pitch / benchmark) / Math.log(2)) * 1200);
    }


    public static void main(String[] args) {
        Guitar guitar = new Guitar();
        System.out.println(guitar.getMusicalScale(120));
        System.out.println(guitar.getOffset(111,110));

    }
}
