package Application;

// FFT 类
public class FFT {
    private final int n;
    private final int m;
    private final double[] cosTable;
    private final double[] sinTable;

    public FFT(int n) {
        this.n = n;
        this.m = (int) (Math.log(n) / Math.log(2));
        cosTable = new double[n / 2];
        sinTable = new double[n / 2];
        for (int i = 0; i < n / 2; i++) {
            cosTable[i] = Math.cos(-2 * Math.PI * i / n);
            sinTable[i] = Math.sin(-2 * Math.PI * i / n);
        }
    }

    public void fft(Complex[] x) {
        // 位反转
        for (int i = 0; i < n; i++) {
            int j = Integer.reverse(i) >>> (32 - m);
            if (i < j) {
                Complex temp = x[i];
                x[i] = x[j];
                x[j] = temp;
            }
        }

        // FFT 计算
        for (int s = 1; s <= m; s++) {
            int step = 1 << s;
            int halfStep = step >> 1;
            for (int j = 0; j < n; j += step) {
                for (int k = 0; k < halfStep; k++) {
                    Complex t = new Complex(
                            cosTable[k * n / step] * x[j + k + halfStep].real -
                                    sinTable[k * n / step] * x[j + k + halfStep].imag,
                            sinTable[k * n / step] * x[j + k + halfStep].real +
                                    cosTable[k * n / step] * x[j + k + halfStep].imag
                    );
                    Complex u = x[j + k];
                    x[j + k] = new Complex(u.real + t.real, u.imag + t.imag);
                    x[j + k + halfStep] = new Complex(u.real - t.real, u.imag - t.imag);
                }
            }
        }

    }

}
