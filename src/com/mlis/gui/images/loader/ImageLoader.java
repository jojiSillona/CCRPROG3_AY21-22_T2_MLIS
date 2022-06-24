package com.mlis.gui.images.loader;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageLoader {

    //ICON
    public static final String ICON = "/com/mlis/gui/images/mlis_icon.png";

    //LOGO
    public static final String LOGO = "/com/mlis/gui/images/mlis_logo.png";

    //MAIN IMAGE LOADER METHODS
    public static final Image loadImage(String path){
        URL resource = ImageLoader.class.getResource(path);
        Image image = Toolkit.getDefaultToolkit().getImage(resource);
        return image;
    }

    public static final ImageIcon loadImageIcon(String path){
        Image image = loadImage(path);
        ImageIcon imageIcon = new ImageIcon(image);
        return imageIcon;
    }

    public static Image loadImage(String path, int width, int height){
        Image image = loadImage(path);
        image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return image;
    }

    public static ImageIcon loadImageIcon(String path, int width, int height){
        Image image = loadImage(path, width, height);
        ImageIcon imageIcon = new ImageIcon(image);
        return imageIcon;
    }

    public static Image loadImage(String path, int width){
        Image image = loadImage(path);
        ImageIcon imageIcon = new ImageIcon(image);
        float origWidth = imageIcon.getIconWidth();
        float origHeight = imageIcon.getIconHeight();

        float ratio = width / origWidth;
        int height = (int)(origHeight * ratio);

        image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return image;
    }

    public static ImageIcon loadImageIcon(String path, int width){
        Image image = loadImage(path, width);
        ImageIcon imageIcon = new ImageIcon(image);
        return imageIcon;
    }
}
