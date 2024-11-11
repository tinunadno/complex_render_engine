package org.ComplexRenderEngine.RenderEngine.IO;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.ComplexRenderEngine.Main;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class VideoWriter {
    private static ArrayList<VideoWriter> recordingWriters = new ArrayList<>();
    private static boolean isTerminating = false;

    public static void finishAllRecordings(){
        System.out.println("saving all renders");
        isTerminating = true;
        for(VideoWriter videoWriter : recordingWriters){
            videoWriter.stopRecording();
        }
    }

    private static void removeIndividualRecorderFromRunning(VideoWriter videoWriter){
        if(!isTerminating){
            recordingWriters.remove(videoWriter);
        }
    }

    private FFmpegFrameRecorder recorder;
    public void initRecording(){
        recordingWriters.add(this);
        String outputFile = "output_video.avi";  // Используем формат AVI для без потерь
        int width = Main.SCREEN_X_SIZE;
        int height = Main.SCREEN_Y_SIZE;
        int frameRate = 30;

        recorder = new FFmpegFrameRecorder(outputFile, width, height);
        recorder.setFrameRate(frameRate);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_FFV1);  // Кодек FFV1 для видео без потерь
        recorder.setFormat("avi");
        try {
            recorder.setVideoOption("preset", "veryslow"); // Чем медленнее, тем лучше качество
            recorder.setVideoOption("crf", "0");  // Без потерь
            recorder.setVideoBitrate(0);  // Без ограничения по битрейту (для FFV1)

            recorder.start();
        }catch (FFmpegFrameRecorder.Exception e){
            e.printStackTrace();
        }
    }

    public void recordFrame(BufferedImage renderedImage){
        Mat mat = bufferedImageToMat(renderedImage);

        Frame frame = convertMatToFrame(mat);

        // Записываем кадр в видео
        try{
            recorder.record(frame);
        }catch (FFmpegFrameRecorder.Exception e){
            e.printStackTrace();
        }
    }

    public void stopRecording(){
        try{
            removeIndividualRecorderFromRunning(this);
            recorder.stop();
            recorder.release();
        }catch (FFmpegFrameRecorder.Exception e){
            e.printStackTrace();
        }
    }

    private Mat bufferedImageToMat(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        // Создаем Mat с тремя каналами (RGB), каждый канал - 8 бит
        Mat mat = new Mat(height, width, opencv_core.CV_8UC3);  // CV_8UC3 - 8 бит на канал, 3 канала (RGB)

        // Копируем данные из BufferedImage в Mat
        byte[] pixels = new byte[width * height * 3]; // Массив для всех пикселей (RGB)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = img.getRGB(x, y);
                byte red = (byte) ((color >> 16) & 0xff);  // Красный канал
                byte green = (byte) ((color >> 8) & 0xff);  // Зеленый канал
                byte blue = (byte) (color & 0xff);  // Синий канал

                // Индекс для массива пикселей
                int index = (y * width + x) * 3;
                pixels[index] = red;   // Красный
                pixels[index + 1] = green; // Зеленый
                pixels[index + 2] = blue;  // Синий
            }
        }

        // Используем mat.data() для записи массива в Mat
        mat.data().put(pixels);  // Записываем все пиксели сразу в Mat

        return mat;
    }


    private Frame convertMatToFrame(Mat mat) {
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
