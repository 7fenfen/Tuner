package Application;

import java.util.HashMap;
import java.util.Map;

/**
 * Instrument类表示一个乐器，可以通过音频频率判断音名及计算音分偏差。
 */
public class Instrument {

    // 用于存储音名和对应频率的映射表。
    // 键（String）是音名，例如"A4"，值（Float）是对应的频率，例如440.0 Hz。
    protected final HashMap<String, Float> soundMap = new HashMap<>();

    /**
     * 默认构造函数。
     * 初始化Instrument对象。
     */
    Instrument() {
    }

    /**
     * 获取乐器的名称。
     *
     * @return 返回乐器的名称，默认返回"Instrument"。
     */
    String getName() {
        return "Instrument";
    }

    /**
     * 根据给定的频率，找到与其最接近的音名。
     *
     * @param pitch 测得频率（以赫兹为单位）。
     * @return 返回与输入频率最接近的音名。
     */
    public String getMusicalScale(float pitch) {
        // 存储与输入频率最接近的音名。
        String result = "";
        // 存储当前最小的频率偏移量，初始化为-1以指示尚未设置。
        float offset = -1;
        // 用于临时存储当前音名对应频率的偏移量。
        float temp;

        // 遍历存储音名和频率的映射表。
        for (Map.Entry<String, Float> entry : soundMap.entrySet()) {
            if (result.isEmpty()) {
                // 如果result为空，表示这是第一次迭代，初始化基准音名和偏移量。
                result = entry.getKey();
                offset = pitch - entry.getValue();
            } else {
                // 计算当前音名与输入频率的偏移量。
                temp = pitch - entry.getValue();
                // 如果当前音名的偏移量小于之前记录的最小偏移量，则更新结果。
                if (Math.abs(temp) < Math.abs(offset)) {
                    result = entry.getKey();
                    offset = temp;
                }
            }
        }

        // 返回最接近的音名。
        return result;
    }

    /**
     * 计算给定频率与其最近音名对应频率的音分（Cent）偏差。
     *
     * @param pitch 测得频率（以赫兹为单位）。
     * @return 返回音分偏差值，单位为音分（Cent）。
     */
    public int getOffset(float pitch) {
        // 根据输入频率获取最接近的音名对应的标准频率。
        Float targetPitch = soundMap.get(this.getMusicalScale(pitch));

        // 计算音分偏差：
        // 音分偏差公式：1200 * log2(pitch / targetPitch)
        // log2(x) 等价于 log(x) / log(2)。
        return (int) ((Math.log(pitch / targetPitch) / Math.log(2)) * 1200);
    }

}