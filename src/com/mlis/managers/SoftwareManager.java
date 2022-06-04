package com.mlis.managers;

import com.mlis.gui.elements.Frame;
import com.mlis.managers.panelmanagers.MainMenuManager;

public class SoftwareManager {

    private Frame frame;

    public SoftwareManager(){
        this.frame = new Frame(500, 500);
    }

    public void showScreen(){
        MainMenuManager mainMenuManager = new MainMenuManager(this, frame);
        this.frame.setScreen(mainMenuManager.getMenuGui());
    }
}
