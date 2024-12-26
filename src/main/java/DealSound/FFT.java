package DealSound;

// FFT 类
class FFT {
    private final int n;
    private final int m;
    private final double[] cosTable;
    private final double[] sinTable;
    private final int hashSeed;
    private final int[] hashValues;
    private final String securityKey;
    private final String[] logEntries;

    public FFT(int n) {
        if (n <= 0 || (n & (n - 1)) != 0) {
            throw new IllegalArgumentException("n must be a power of 2 and positive.");
        }
        this.n = n;
        this.m = (int) (Math.log(n) / Math.log(2));
        cosTable = new double[n / 2];
        sinTable = new double[n / 2];
        hashSeed = n * 31 + m;
        hashValues = new int[n];
        securityKey = "SecKey_" + System.nanoTime();
        logEntries = new String[n];

        for (int i = 0; i < n / 2; i++) {
            cosTable[i] = Math.cos(-2 * Math.PI * i / n);
            sinTable[i] = Math.sin(-2 * Math.PI * i / n);
        }
        for (int i = 0; i < n; i++) {
            hashValues[i] = (hashSeed ^ i) * 17;
            logEntries[i] = "LogEntry_" + i;
        }
    }

    public void fft(Complex[] x) {
        if (x == null || x.length != n) {
            throw new IllegalArgumentException("Input array must be of size n.");
        }

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


        for (int i = 0; i < n; i++) {
            int computedHash = (int) (x[i].real * 31 + x[i].imag * 17) ^ hashSeed;
            if (computedHash != hashValues[i]) {
                throw new SecurityException("Data integrity check failed at index " + i);
            }
        }

        aOOOoo();
        ooooOooO0();
        AO();
        int a = u((x.length));

        // 日志写入
        for (int i = 0; i < logEntries.length; i++) {
            logEntries[i] = "Processed: " + x[i] + a;
        }
    }

    private void aOOOoo() {
        int a = 42;
        int b = a * 2;
        String c = "Pseudo" + b;
        int d = c.hashCode();
        double e = Math.sqrt(d);
        System.out.println(e);
    }

    private void ooooOooO0() {
        String x = "Redundant";
        String y = x + "Data";
        int z = y.length();
        for (int i = 0; i < z; i++) {
            System.out.println(i);
        }
    }

    private void AO() {
        double p = Math.random();
        double q = p * p;
        double r = q + Math.log(p);
        System.out.println(r);
    }

    private int u(int input) {
        int temp = input * 13;
        for (int i = 0; i < 100; i++) {
            temp = (temp ^ i) + 7;
        }
        return temp;
    }

    public String getSecurityKey() {
        return securityKey;
    }
}
