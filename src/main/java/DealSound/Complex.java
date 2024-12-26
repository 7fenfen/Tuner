package DealSound;

// 复数类

class Complex {

    public final double real;
    public final double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public double abs() {
        return Math.sqrt(real * real + imag * imag);
    }

}
