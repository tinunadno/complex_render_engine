package org.ComplexRenderEngine.ComplexFunction;

import org.ComplexRenderEngine.Complex.Complex;

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
