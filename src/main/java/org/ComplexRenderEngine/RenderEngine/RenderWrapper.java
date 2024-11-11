package org.ComplexRenderEngine.RenderEngine;

import org.ComplexRenderEngine.Complex.Complex;
import org.ComplexRenderEngine.ComplexFunction.ComplexFunction;
import org.ComplexRenderEngine.ComplexFunction.MondelbrotSetFunction;
import org.ComplexRenderEngine.Main;
import org.ComplexRenderEngine.RenderEngine.Gradient.ColorGradient;
import org.ComplexRenderEngine.RenderEngine.Gradient.ColorGradient1;
import org.ComplexRenderEngine.RenderEngine.IO.VideoWriter;

import java.awt.image.BufferedImage;

public class RenderWrapper {

    private ComplexFunction complexFunction;
    private BIRender biRender;
    private UserInterface userInterface;

    public RenderWrapper() {
        BufferedImage screen = new BufferedImage(Main.SCREEN_X_SIZE, Main.SCREEN_Y_SIZE, BufferedImage.TYPE_INT_RGB);

        complexFunction = new MondelbrotSetFunction();
        ColorGradient colorGradient = new ColorGradient1();
        colorGradient.setMaxValue(10);
        biRender = new BIRender(screen, complexFunction, 100, 50, new Complex(), 5, colorGradient);

        userInterface = new UserInterface();

    }

    public void init_render() {

        VideoWriter videoWriter = new VideoWriter();

        videoWriter.initRecording();

        userInterface.display(biRender.renderImage());
        Complex displacement = new Complex(0, 0);
        double scale = 100;
        double step = 0.01;
        for (double i = 2.0; i < 4.01; i += step) {
            if (displacement.getReal() > -1.5) {
                displacement.setReal(displacement.getReal() - 0.1);
                biRender.setDisplacement(displacement);
            }
            if (scale < 2000) {
                scale += 20;
                biRender.setScale(scale);
            }

            BufferedImage renderedImage = biRender.renderImage();

            videoWriter.recordFrame(renderedImage);

            userInterface.updateImage(renderedImage);
        }

        videoWriter.stopRecording();
        System.out.println("rendering done");
    }
}
