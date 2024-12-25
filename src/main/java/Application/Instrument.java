package Application;

import java.util.HashMap;
import java.util.Map;

public class Instrument {

    protected final HashMap<String, Float> soundMap = new HashMap<>();

    Instrument(){
    }

    String getName() {
        return "Instrument";
    }

    /**
     * @param pitch 测得频率
     * @return 与哪个音名最相近
     */
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
     * @return 偏差的音分
     */
    public int getOffset(float pitch){
        // 获取音名对应的赫兹
        Float targetPitch;
        targetPitch = soundMap.get(this.getMusicalScale(pitch));

        // 计算音分偏移量
        return (int)((Math.log(pitch / targetPitch) / Math.log(2)) * 1200);
    }

}