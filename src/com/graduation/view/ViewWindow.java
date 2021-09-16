package com.graduation.view;

import com.graduation.client.GameClient;
import com.graduation.utils.GameTextParser;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ViewWindow {

    public static ViewWindow instance = new ViewWindow();
    private JFrame window;
    private JLabel map;
    private JLabel inputLabel;
    private JPanel inputPanel;
    public JTextField inputField;
    private String input;
    private GameTextParser gameTextParser;
    private GameClient gameClient;
    private static String banner;
    private JPanel statPanel;
    private TitledBorder tb;
    private TitledBorder eb;
    private JLabel statLabel;
    private JLabel itemLabel;
    private JPanel schoolMapPanel;
    private JLabel schoolMapLabel;

    private ViewWindow() {
        try {
            this.gameTextParser = new GameTextParser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setUpInputPanel();
        setUpStatPanel();
        setUpDescriptionPanel();
        buildGUI();
    }

    public void updateGame(){
        window.repaint();
    }

    public static ViewWindow getInstance(){
        if (instance == null){
            instance = new ViewWindow();
        }
        return instance;
    }

    public void updateInputPanel(String text){
        inputLabel.setText(text);
        inputPanel.repaint();
    }

    public void updateStatPanel(String text){
        statLabel.setText(text);
        statPanel.repaint();
    }

    public void updateItemPanel(String text){
        itemLabel.setText(text);
        statPanel.repaint();
    }

    public void updateSchoolPanel(String text){
        schoolMapLabel.setText(text);
        schoolMapPanel.repaint();
    }

    public void updateWindow(String text){
        map.setText(text);
        window.repaint();
    }

    private void buildGUI(){
        this.window = new JFrame("Graduation Day");
        this.window.setLayout(new BorderLayout());
        this.window.add(inputPanel, BorderLayout.SOUTH);
        this.window.add(statPanel, BorderLayout.EAST);
        this.window.add(schoolMapPanel, BorderLayout.WEST);
        this.window.setPreferredSize(new Dimension(1100, 1000));
        this.window.setVisible(true);
        this.window.setResizable(false);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.pack();
    }

    private void setUpDescriptionPanel() {
        this.schoolMapPanel = new JPanel();
        this.schoolMapLabel = new JLabel();
        schoolMapLabel.setText(welcome());
        schoolMapPanel.setPreferredSize(new Dimension(700,600));
        schoolMapPanel.setBackground(new Color(255, 255, 255));
        schoolMapPanel.setBorder(BorderFactory.createLineBorder(new Color(110, 16, 5)));
        schoolMapPanel.add(schoolMapLabel);

    }

    private void setUpInputPanel() {
        this.inputPanel = new JPanel();
        this.inputLabel = new JLabel();
        inputLabel.setText(readTXT("Instructions.txt"));
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(110, 16, 5)));
        inputPanel.setPreferredSize(new Dimension(1100,400));
        setUpInputField();
        inputPanel.add(inputField, BorderLayout.SOUTH);
        inputPanel.add(inputLabel,BorderLayout.CENTER);

    }

    private void setStats() {
        statLabel.setText("");
        statLabel.setBorder(BorderFactory.createTitledBorder("Stat"));
    }
    private void setItems() {
        itemLabel.setText("");
        itemLabel.setBorder(BorderFactory.createTitledBorder("Items"));
    }

    private void setUpStatPanel() {
        this.statPanel = new JPanel();
        this.statLabel = new JLabel();
        this.itemLabel = new JLabel();
        statPanel.setLayout( new BorderLayout());
        statPanel.setPreferredSize(new Dimension(300,600));
        statPanel.setLayout(new GridLayout(0,1));
        setStats();
        setItems();
        statPanel.add(statLabel, BorderLayout.NORTH);
        statPanel.add(itemLabel, BorderLayout.CENTER);
        statPanel.setBackground(new Color(0, 0, 0));
        this.tb = new TitledBorder("Stats");
        this.eb = new TitledBorder("Your Items");
        tb.setTitleColor(Color.GREEN);
        eb.setTitleColor(Color.GREEN);
        statLabel.setBorder(tb);
        itemLabel.setBorder(eb);

    }

    public String setUpInputField(){
        this.inputField = new JTextField(50);
        inputField.setBorder(BorderFactory.createTitledBorder("Enter your command: \n " +""));

        inputField.addActionListener(e -> {

            setInput(inputField.getText());
            try {
                gameTextParser.parseText(getInput());

            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            inputField.setText("");

        });
        return getInput();
    }

    private String welcome(){
        return readTXT("introtext.txt");
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public static String readTXT(String name) {

        try {
            banner = new String(Files.readAllBytes(Path.of("Banner/" + name )));
        } catch (IOException e) {

        }
        return banner;
    }
}