/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Transaction;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author JurelP
 */
public class ClearTransactionsPanel extends JDialog {

    public ClearTransactionsPanel(JFrame parent) {
        super(parent, "Clear Transactions", false);
        
        setSize(500, 500);
        setMinimumSize(new Dimension(400, 400));
        setLocationRelativeTo(parent);
    }
    
    public void display(boolean v) {
        setVisible(v);
    }
    
    public void setTransactions(List<Transaction> transactions) {
        
    }
    
}
