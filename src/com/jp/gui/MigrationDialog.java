/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



class CustomFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getAbsolutePath().endsWith(".mdb");
    }

    @Override
    public String getDescription() {
        return "MS Access Database files (*.mdb)";
    }

}
/**
 *
 * @author JurelP
 */
public class MigrationDialog extends JDialog {
    
    private final Dimension dim = new Dimension(600, 400);
    
    private StringBuilder logsSB;
    
    JButton browseButton, startButton;
    JTextField pathField;
    JTextArea logTextArea;
    private JPanel browsePanel, logPanel;
    private JFileChooser fileChooser;
    
    public MigrationDialog(JFrame parent) {
        super(parent, "Data Migration", true);
        setLayout(new BorderLayout());
        
        logsSB = new StringBuilder();
        
        browseButton = new JButton("Browse");
        startButton = new JButton("Start");
        pathField = new JTextField(30);
        logTextArea = new JTextArea();
        browsePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logPanel = new JPanel(new BorderLayout());
        
        fileChooser = new JFileChooser(new java.io.File("C:\\"));
        
        fileChooser.setDialogTitle("Select database file");
        fileChooser.setFileFilter(new CustomFilter());
        
        
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = fileChooser.showOpenDialog(MigrationDialog.this);
                
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    
                    pathField.setText(file.getAbsolutePath());
                }
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dbPath = pathField.getText().trim();
                
            }
        });
        
        browsePanel.add(pathField);
        browsePanel.add(browseButton);
        browsePanel.add(startButton);
        
        logPanel.add(new JScrollPane(logTextArea), BorderLayout.CENTER);
        
        add(browsePanel, BorderLayout.PAGE_START);
        add(logPanel, BorderLayout.CENTER);
        
        setMinimumSize(dim);
        setSize(dim);
        setLocationRelativeTo(parent);
    }
}

