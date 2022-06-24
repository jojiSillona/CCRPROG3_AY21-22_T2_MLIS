package com.mlis.gui.panels;

import com.mlis.gui.elements.Frame;
import com.mlis.gui.images.loader.Guide;
import com.mlis.gui.images.loader.ImageLoader;
import com.mlis.managers.panelmanagers.MainMenuManager;

import javax.swing.*;
import java.awt.event.MouseListener;

public class MainMenu extends JPanel {

    private MainMenuManager mainMenuManager;

    private JLabel logo;
    private JButton patient;
    private JButton services;
    private JButton labResults;

    public MainMenu(MainMenuManager manager, Frame frame){

        mainMenuManager = manager;
        setVisible(true);
        setSize(frame.getSize());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        initComponents();

        add(logo);
        add(patient);
        add(services);
        add(labResults);
    }

    private void initComponents(){

        //LOGO
        String logoPath = ImageLoader.LOGO;

        ImageIcon logoImageIcon = ImageLoader.loadImageIcon(logoPath, 400);
        logo = new JLabel(logoImageIcon);
        logo.setVisible(true);
        logo.setSize(logoImageIcon.getIconWidth(), logoImageIcon.getIconHeight());
        logo.setAlignmentX(CENTER_ALIGNMENT);

        //PATIENT BUTTON
        patient = new JButton("Manage Patient Records");
        patient.setAlignmentX(CENTER_ALIGNMENT);

        //SERVICES BUTTON
        services = new JButton("Manage Services");
        services.setAlignmentX(CENTER_ALIGNMENT);

        //LAB RESULTS BUTTON
        labResults = new JButton("Manage Laboratory Requests");
        labResults.setAlignmentX(CENTER_ALIGNMENT);
    }
}
