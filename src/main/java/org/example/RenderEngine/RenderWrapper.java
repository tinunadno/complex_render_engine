package org.example.RenderEngine;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.example.Complex.Complex;
import org.example.ComplexFunction.ComplexFunction;
import org.example.ComplexFunction.MondelbrotSetFunction;
import org.example.Main;
import org.example.RenderEngine.Gradient.ColorGradient;
import org.example.RenderEngine.Gradient.ColorGradient1;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class RenderWrapper {

    private ComplexFunction complexFunction;
    private BIRender biRender;
    private UserInterface userInterface;

    public RenderWrapper(){
        BufferedImage screen=new BufferedImage(Main.SCREEN_X_SIZE, Main.SCREEN_Y_SIZE, BufferedImage.TYPE_INT_RGB);

        complexFunction = new MondelbrotSetFunction();
        ColorGradient colorGradient = new ColorGradient1();
        colorGradient.setMaxValue(10);
        biRender = new BIRender(screen, complexFunction, 100, 50, new Complex(), 5, colorGradient);

        userInterface = new UserInterface();

    }
    public void init_render(){

        String outputFile = "output_video.avi";  // Используем формат AVI для без потерь
        int width = Main.SCREEN_X_SIZE;
        int height = Main.SCREEN_Y_SIZE;
        int frameRate = 30;

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, width, height);
        recorder.setFrameRate(frameRate);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_FFV1);  // Кодек FFV1 для видео без потерь
        recorder.setFormat("avi");

        try {
            // Настройки кодека для без потерь
            recorder.setVideoOption("preset", "veryslow"); // Чем медленнее, тем лучше качество
            recorder.setVideoOption("crf", "0");  // Без потерь
            recorder.setVideoBitrate(0);  // Без ограничения по битрейту (для FFV1)

            recorder.start();

            userInterface.display(biRender.renderImage());
            Complex displacement = new Complex(0, 0);
            double scale = 100;
            for (double i = 2.0; i < 2.1; i += 0.01) {
                if (i < 2.5) {
                    displacement.setReal(displacement.getReal() - 0.01);
                    biRender.setDisplacement(displacement);
                }
                if (i > 2.5 && i < 2.7) {
                    scale += 20;
                    biRender.setScale(scale);
                }
                ((MondelbrotSetFunction) complexFunction).setExponent(i);
                BufferedImage renderedImage = biRender.renderImage();

                Mat mat = bufferedImageToMat(renderedImage);

                Frame frame = convertMatToFrame(mat);

                // Записываем кадр в видео
                recorder.record(frame);

                userInterface.updateImage(renderedImage);
            }
            recorder.stop();
            recorder.release();
        }catch (FFmpegFrameRecorder.Exception e){
            e.printStackTrace();
        }
    }
    private static Mat bufferedImageToMat(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        // Создаем Mat с тремя каналами (RGB), каждый канал - 8 бит
        Mat mat = new Mat(height, width, opencv_core.CV_8UC3);  // CV_8UC3 - 8 бит на канал, 3 канала (RGB)

        // Копируем данные из BufferedImage в Mat
        byte[] pixels = new byte[width * height * 3]; // Массив для всех пикселей (RGB)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = img.getRGB(x, y);
                byte blue = (byte) (color & 0xff);  // Синий канал
                byte green = (byte) ((color >> 8) & 0xff);  // Зеленый канал
                byte red = (byte) ((color >> 16) & 0xff);  // Красный канал

                // Индекс для массива пикселей
                int index = (y * width + x) * 3;
                pixels[index] = blue;
                pixels[index + 1] = green;
                pixels[index + 2] = red;
            }
        }

        // Используем mat.data() для записи массива в Mat
        mat.data().put(pixels);  // Записываем все пиксели сразу в Mat

        return mat;
    }
    private static Frame convertMatToFrame(Mat mat) {
        // Получаем данные из Mat
        int width = mat.cols();
        int height = mat.rows();

        // Преобразуем из BGR в RGB (если изображение в формате BGR)
        Mat rgbMat = new Mat();
        opencv_imgproc.cvtColor(mat, rgbMat, opencv_imgproc.COLOR_BGR2RGB); // Конвертация в RGB

        // Создаем Frame для RGB данных
        Frame frame = new Frame(width, height, Frame.DEPTH_UBYTE, 3);

        // Получаем данные из RGB Mat
        byte[] matData = new byte[width * height * 3]; // 3 - для RGB каналов
        rgbMat.data().get(matData);  // Копируем данные из ByteBuffer в массив

        // Заполняем ByteBuffer для Frame.image[0]
        ByteBuffer byteBuffer = ByteBuffer.wrap(matData);
        frame.image[0] = byteBuffer;  // Присваиваем ByteBuffer в image[0]

        return frame;
    }
}
