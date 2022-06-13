package com.mlis.gui.elements;

import javax.swing.*;

public class Frame extends JFrame {

    private JPanel screen;

    public Frame(int width, int height){
        this.setLayout(null);
        this.setResizable(false);

        this.setSize(width, height);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
