package org.ComplexRenderEngine.RenderEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface {
    private JFrame frame;
    private JLabel label;

    public void display(BufferedImage screen){
        if(frame==null){
            frame=new JFrame();
            frame.setTitle("stained_image");
            frame.setSize(screen.getWidth(), screen.getHeight());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            label=new JLabel();
            label.setIcon(new ImageIcon(screen));
            frame.getContentPane().add(label, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
        }else label.setIcon(new ImageIcon(screen));
    }

    public void updateImage(BufferedImage screen){
        label.setIcon(new ImageIcon(screen));
        frame.pack();
        frame.repaint();
    }
}
