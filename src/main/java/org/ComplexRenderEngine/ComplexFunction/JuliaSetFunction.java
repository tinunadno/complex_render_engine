package org.ComplexRenderEngine.ComplexFunction;

import org.ComplexRenderEngine.Complex.Complex;

public class JuliaSetFunction extends ComplexFunction {
    private Complex c = new Complex(0.35, -0.35);
    @Override
    public Complex executeFunction(Complex z, Complex c_){
        return z.pow(2).add(c);
    }
}
