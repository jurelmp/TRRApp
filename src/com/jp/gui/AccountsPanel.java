/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Account;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author JurelP
 */
public class AccountsPanel extends JPanel {
    
    private JTable accountsTable;
    private AccountsTableModel accountsTableModel;
    
    private AccountsSelectionListener accountsSelectionListener;
    
    public AccountsPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 400;
        setPreferredSize(dim);
        setMinimumSize(dim);
        setLayout(new BorderLayout());
        Border innerBorder = BorderFactory.createTitledBorder("Accounts");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        
        // Initialize components
        accountsTableModel = new AccountsTableModel();
        accountsTable = new JTable(accountsTableModel);
        accountsTable.setRowHeight(20);
        accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountsTable.setRowSelectionAllowed(true);
//        accountsTable.setAutoCreateRowSorter(true);
        
        // Listeners
        accountsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (accountsTable.getSelectedRow() > -1) {
//                    System.out.println(accountsTable.getValueAt(accountsTable.getSelectedRow(), -1));
                    
//                    Account selected = (Account) accountsTable.getValueAt(accountsTable.getSelectedRow(), -1);
                    Account selected = (Account) accountsTableModel.getAccountRow(accountsTable.getSelectedRow());
                    
                    if (accountsSelectionListener != null) {
                        accountsSelectionListener.accountSelected(selected);
                    }
                }
            }
        });
        
        // Add components to panel
        add(new JScrollPane(accountsTable), BorderLayout.CENTER);
        
        
    }
    
    public void setData(List<Account> accounts) {
        accountsTableModel.setData(accounts);
    }
    
    public void refresh() {
        accountsTableModel.fireTableDataChanged();
    }
    
    public void addRow(Account account) {
        accountsTableModel.addRow(account);
        refresh();
    }
    
    public void setAccountSelectionListener(AccountsSelectionListener listener) {
        this.accountsSelectionListener = listener;
    }

//    void setFirstSelected() {
//        this.accountsTable.setRowSelectionInterval(0, 0);
//    }

    void setTableSelection(int i) {
        
        ListSelectionModel listSelectionModel = accountsTable.getSelectionModel();
        listSelectionModel.addSelectionInterval(i, i);
//        this.accountsTable.addRowSelectionInterval(i, i);
//        this.accountsTable.getSelectionModel().addSelectionInterval(i, i);
//        this.accountsTable.getSelectionModel().setSelectionInterval(i, i);
    }
}
