package Application;

public interface Instrument {
    // 获得名字
    String getName();

    // 获得与当前频率最相近的基准音
    String getMusicalScale(float pitch);

    // 计算偏移量
    int getOffset(float pitch, float benchmark);
}
