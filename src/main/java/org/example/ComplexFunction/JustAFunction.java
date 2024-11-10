package org.example.ComplexFunction;

import org.example.Complex.Complex;

public class JustAFunction extends ComplexFunction{

    @Override
    public Complex executeFunction(Complex z, Complex c) {
        return c.pow(z.tan());
    }
}
