/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Account;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author JurelP
 */
public class BankManagerSettingsDialog extends JDialog {
    
    private static final Dimension WINDOW_DIMENSION = new Dimension(500, 500);
    private JTable tableAccounts;
    private BankManagerSettingsTableModel tableModel;
    
    private BankManagerSettingsDialogActionListener listener;
    
    private List<Account> accounts;

    public BankManagerSettingsDialog(JFrame parent) {
        super(parent, "Bank Manager Settings", true);
        setLayout(new BorderLayout());
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearAccounts();
                dispose();
            }
            
        });
        
        tableModel = new BankManagerSettingsTableModel();
        tableAccounts = new JTable(tableModel);
        tableAccounts.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        tableAccounts.setRowHeight(20);
        tableAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                if (tableAccounts.getSelectedRow() > -1) {
                    Account selected = (Account) tableModel.getAccount(tableAccounts.convertRowIndexToModel(tableAccounts.getSelectedRow()));
                    
                    if (listener != null) {
                        listener.setAccountActive(selected);
                    }
                }
            }
        });
        
        add(new JScrollPane(tableAccounts), BorderLayout.CENTER);
        add(new JLabel("MARK ALL THE ACCOUNT/S TO BE INCLUDED IN THE REPORT."), BorderLayout.PAGE_START);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(WINDOW_DIMENSION);
        setMinimumSize(WINDOW_DIMENSION);
        setLocationRelativeTo(parent);
    }
    
    public void setAccounts(List<Account> accounts) {
        tableModel.setAccounts(accounts);
    }
    
    public void clearAccounts() {
        tableModel.clearAccounts();
    }

    public void setListener(BankManagerSettingsDialogActionListener listener) {
        this.listener = listener;
    }
    
}
