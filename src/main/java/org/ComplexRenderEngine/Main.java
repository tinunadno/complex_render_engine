package org.ComplexRenderEngine;

import org.ComplexRenderEngine.RenderEngine.IO.VideoWriter;
import org.ComplexRenderEngine.RenderEngine.RenderWrapper;

public class Main {
    public static final int SCREEN_X_SIZE = 800;
    public static final int SCREEN_Y_SIZE = 400;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(100);
                    VideoWriter.finishAllRecordings();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });
        RenderWrapper renderWrapper = new RenderWrapper();
        renderWrapper.init_render();
    }
}