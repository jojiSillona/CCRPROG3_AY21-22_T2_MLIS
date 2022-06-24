package com.mlis.gui.elements;

import com.mlis.gui.images.loader.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private JPanel screen;

    public Frame(int width, int height){

        this.loadIcon();
        this.setLayout(null);
        this.setResizable(false);

        this.setSize(width, height);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void loadIcon(){

        String path = ImageLoader.ICON;

        Image image = ImageLoader.loadImage(path);
        this.setIconImage(image);
    }

    public void setScreen(JPanel panel) {

        if (this.screen != null)
            this.remove(this.screen);

        this.screen = panel;
        this.add(screen);

        repaint();
        revalidate();
    }
}
