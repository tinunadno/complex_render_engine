package org.example;

import org.example.RenderEngine.RenderWrapper;

public class Main {
    public static final int SCREEN_X_SIZE = 1920;
    public static final int SCREEN_Y_SIZE =1080;

    public static void main(String[] args) {
        RenderWrapper renderWrapper = new RenderWrapper();
        renderWrapper.init_render();
    }
}