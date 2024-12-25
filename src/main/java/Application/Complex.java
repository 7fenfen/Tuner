package Application;

// 复数类

public class Complex {

    public double real;
    public double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public double abs() {
        return Math.sqrt(real * real + imag * imag);
    }

}