package com.mlis.managers.panelmanagers;

import com.mlis.gui.elements.Frame;
import com.mlis.gui.panels.MainMenu;
import com.mlis.managers.SoftwareManager;

public class MainMenuManager {

    private SoftwareManager softwareManager;
    private MainMenu menuGui;

    public MainMenuManager(SoftwareManager sManager, Frame frame){
        this.softwareManager = sManager;
        this.menuGui = new MainMenu(this, frame);
    }

    public MainMenu getMenuGui() {
        return menuGui;
    }
}
