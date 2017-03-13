/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.utils.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author JurelP
 */
public class Toolbar extends JToolBar implements ActionListener{
    
    private JButton saveButton, refreshButton, searchButton, exportButton, importButton, clearButton;
    
    private ToolbarActionListener toolbarActionListener;
    
    public Toolbar() {
        setBorder(BorderFactory.createEtchedBorder());
        
//        saveButton = new JButton("SAVE");
//        refreshButton = new JButton("REFRESH");
//        searchButton = new JButton("SEARCH");
        importButton = new JButton("Import", Utils.createIcon("/com/jp/images/Import24.gif"));
        importButton.setMnemonic(KeyEvent.VK_I);
//        clearButton = new JButton("To Clear", Utils.createIcon("/com/jp/images/Preferences24.gif"));
//        clearButton.setMnemonic(KeyEvent.VK_C);
        exportButton = new JButton("Next Banking Day", Utils.createIcon("/com/jp/images/Export24.gif"));
        exportButton.setMnemonic(KeyEvent.VK_E);
        
        exportButton.addActionListener(this);
        importButton.addActionListener(this);

//        add(saveButton);
//        add(refreshButton);
//        add(searchButton);
        add(exportButton);
        add(importButton);
    }
    
    public void setToolbarActionListener(ToolbarActionListener listener) {
        this.toolbarActionListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        
        if (clicked == exportButton) {
            if (toolbarActionListener != null) {
                toolbarActionListener.exportEventOccured();
            }
        }
        
        if (clicked == importButton) {
            if (toolbarActionListener != null) {
                
            }
        }
    }
}
