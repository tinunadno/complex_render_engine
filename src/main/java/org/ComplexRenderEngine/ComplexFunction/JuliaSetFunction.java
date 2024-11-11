package org.ComplexRenderEngine.ComplexFunction;

import org.ComplexRenderEngine.Complex.Complex;

public class JuliaSetFunction extends ComplexFunction {
    private Complex c = new Complex(0.35, -0.35);
    private double exponent = 2.0;
    @Override
    public Complex executeFunction(Complex z, Complex c_){
        return z.pow(exponent).add(c);
    }
    public void setC(Complex c){
        this.c = c;
    }
    public void setExponent(double exponent){
        this.exponent = exponent;
    }
}
