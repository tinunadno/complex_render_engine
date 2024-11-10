package org.ComplexRenderEngine.RenderEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriter {
    public void saveBufferedImage(BufferedImage image) {
        try {
            File outputFile = new File("output_image.png");
            ImageIO.write(image, "PNG", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
