package com.onedroid;

public class CDouble {

    public double a, b;

    public CDouble(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public void plus(CDouble in1) {
        a += in1.a;
        b += in1.b;
    }

    public static CDouble plus(CDouble in1, CDouble in2) {
        return new CDouble(in1.a + in2.a, in1.b + in2.b);
    }

    public void minus(CDouble in1) {
        a -= in1.a;
        b -= in1.b;
    }

    public static CDouble minus(CDouble in1, CDouble in2) {
        return new CDouble(in1.a - in2.a, in1.b - in2.b);
    }

    public void multiply(CDouble in1) {
        CDouble v = multiply(this, in1);
        a = v.a;
        b = v.b;
    }

    public static CDouble multiply(CDouble in1, CDouble in2) {
        return new CDouble(in1.a * in2.a - in1.b * in2.b, in1.b * in2.a + in1.a * in2.b);
    }

    public void divide(CDouble in1) {
        CDouble v = divide(this, in1);
        a = v.a;
        b = v.b;
    }

    public static CDouble divide(CDouble in1, CDouble in2) {
        return new CDouble((in1.a * in2.a + in1.b * in2.b) / (in2.a * in2.a + in2.b * in2.b), (in1.b * in2.a - in1.a * in2.b) / (in2.a * in2.a + in2.b * in2.b));
    }

    public double getModule() {
        return Math.sqrt(a * a + b * b);
    }

    public double getFi() {
        return Math.atan2(b, a);
    }

    public static CDouble Ln(CDouble in1) {
        return new CDouble(Math.log(in1.getModule()), in1.getFi());
    }

    public static CDouble sin(CDouble in1) {
        return new CDouble(Math.sin(in1.a) * Math.cosh(in1.b), Math.cos(in1.a) * Math.sinh(in1.b));
    }

    public static CDouble cos(CDouble in1) {
        return new CDouble(Math.cos(in1.a) * Math.cosh(in1.b), -Math.sin(in1.a) * Math.sinh(in1.b));
    }

    public static CDouble exp(CDouble in2) {
        double ex = Math.pow(Math.E, in2.a);
        return new CDouble(ex * Math.cos(in2.b), ex * Math.sin(in2.b));
    }

    public void pow(CDouble in1) {
        CDouble v = pow(this, in1);
        a = v.a;
        b = v.b;
    }

    public static CDouble pow(CDouble in1, CDouble in2) {
        CDouble v1 = Ln(in1);
        CDouble v = plus(multiply(new CDouble(v1.a, 0), in2), multiply(new CDouble(0, v1.b), in2));
        return exp(v);
    }

    public String toString() {
        return a + (b < 0 ? "" : "+") + b + "i";
    }
}
