package org.example.RenderEngine;



import org.example.Complex.Complex;
import org.example.ComplexFunction.ComplexFunction;
import org.example.RenderEngine.Gradient.ColorGradient;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class BIRender {
    private ComplexFunction complexFunction;
    private BufferedImage screen;

    private double scale;
    private int iterationCount;
    private double range;

    Complex displacement;
    private ColorGradient colorGradient;

    public BIRender(BufferedImage screen, ComplexFunction complexFunction, double scale, int iterationCount, Complex displacement, double range, ColorGradient colorGradient) {
        this.complexFunction = complexFunction;
        this.screen = screen;
        this.scale = scale;
        this.iterationCount = iterationCount;
        this.displacement = displacement;
        this.range = range;
        this.colorGradient = colorGradient;
    }

    public BufferedImage renderImage() {
        double[][] preRender = new double[screen.getHeight()][screen.getWidth()];
        for (int i = 0; i < screen.getHeight(); i++) {
            for (int j = 0; j < screen.getWidth(); j++) {
                Complex c = new Complex(
                        (j - (screen.getWidth() >> 1)) / scale,
                        (i - (screen.getHeight() >> 1)) / scale
                );
                c = c.add(displacement);
                Complex z = c.clone();

                double minModule = 9;

                int iter = 0;

                for (int k = 0; k < iterationCount; k++) {
                    z = complexFunction.executeFunction(z, c);
                    if (minModule > z.module()) minModule = z.module();
                    if (z.module() > range){
                        iter = k;
                        break;
                    }
                }

                if (z.module() <= range) preRender[i][j] = minModule*5;
                else preRender[i][j] =  iter/(double)iterationCount;

            }
        }
        postProcess(preRender);
        return screen;
    }

    private void postProcess(double[][] preRenderedImage) {
        double max = 0;
        for (int i = 0; i < screen.getHeight(); i++) {
            for (int j = 0; j < screen.getWidth(); j++) {
                double temp = preRenderedImage[i][j];
                if(temp > max)
                    max = temp;
            }
        }
        colorGradient.setMaxValue(max);
        for (int i = 0; i < screen.getHeight(); i++) {
            for (int j = 0; j < screen.getWidth(); j++) {
                screen.setRGB(j, i, colorGradient.valueToGradient(preRenderedImage[i][j]));
            }
        }
    }

    public void setDisplacement(Complex displacement){
        this.displacement = displacement;
    }

    public void setScale(double scale){
        this.scale = scale;
    }

    public void setFunction(ComplexFunction complexFunction) {
        this.complexFunction = complexFunction;
    }
}
