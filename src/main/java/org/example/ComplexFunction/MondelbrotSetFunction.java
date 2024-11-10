package org.example.ComplexFunction;

import org.example.Complex.Complex;

public class MondelbrotSetFunction extends ComplexFunction {
    private double exponent=2;

    @Override
    public Complex executeFunction(Complex z, Complex c) {
        return z.pow(exponent).add(c);
    }

    public void setExponent(double exponent) {
        this.exponent = exponent;
    }
}
