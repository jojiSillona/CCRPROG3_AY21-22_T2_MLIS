package com.mlis.gui.panels;

import com.mlis.gui.elements.Frame;
import com.mlis.managers.panelmanagers.MainMenuManager;

import javax.swing.*;
import java.awt.event.MouseListener;

public class MainMenu extends JPanel {

    private MainMenuManager mainMenuManager;

    public MainMenu(MainMenuManager manager, Frame frame){
        this.mainMenuManager = manager;
    }
}
