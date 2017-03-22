/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author JurelP
 */
public class BackupDialog extends JDialog {
    
    private JProgressBar progressBar;

    public BackupDialog(JFrame parent) {
        super(parent, "Backup in progress...", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(parent);
        setSize(new Dimension(400, 100));
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        progressBar.setString("Creating a backup...");
        
        add(progressBar);
        
        pack();
    }

    @Override
    public void setVisible(boolean visible) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!visible) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
//                    progressBar.setValue(0);
//                    progressBar.setString(null);
                }

                if (visible) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }

                BackupDialog.super.setVisible(visible);
            }
        });
    }
    
    
    
}
