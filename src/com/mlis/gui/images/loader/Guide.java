package com.mlis.gui.images.loader;

import java.awt.*;

public class Guide {

    public static void centerHorizontally(Component parent, Component child){

        int parentWidth = parent.getWidth();
        int childWidth = child.getWidth();

        int parentCenter = parentWidth / 2;
        int childCenter = childWidth / 2;

        int posX = parentCenter - childCenter;
        child.setLocation(posX, child.getY());
    }
}
