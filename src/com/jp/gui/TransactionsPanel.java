/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Account;
import com.jp.model.Transaction;
import java.awt.BorderLayout;
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
public class TransactionsPanel extends JPanel {
    
    private Account currentAccount;
    
    private JTable transactionsTable;
    
    private TransactionsTableModel transactionsTableModel;
    
    private TransactionsSelectionListener transactionsSelectionListener;
    
    public TransactionsPanel() {
        setLayout(new BorderLayout());
        Border innerBorder = BorderFactory.createTitledBorder("Transactions");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        
        // Initialize the components
        transactionsTableModel = new TransactionsTableModel();
        transactionsTable = new JTable(transactionsTableModel);
        transactionsTable.setRowHeight(20);
        transactionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Listeners
        transactionsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (transactionsTable.getSelectedRow() > -1) {
//                    System.out.println(accountsTable.getValueAt(accountsTable.getSelectedRow(), -1));
                    
//                    Account selected = (Account) accountsTable.getValueAt(accountsTable.getSelectedRow(), -1);
                    Transaction selected = (Transaction) transactionsTableModel.getTransactionRow(transactionsTable.getSelectedRow());
                    
                    if (transactionsSelectionListener != null) {
                        transactionsSelectionListener.transactionSelected(selected, transactionsTable.getSelectedRow());
                    }
                }
            }
        });
        
        // Add the components to the panel
        add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    }
    
    public void setAccount(Account account) {
        this.currentAccount = account;
    }
    
    public void setData(List<Transaction> transactions) {
        transactionsTableModel.setData(transactions);
        this.refresh();
    }
    
    public void addRow(Transaction t) {
        transactionsTableModel.addRow(t);
        refresh();
    }
    
    public void refresh() {
        transactionsTableModel.fireTableDataChanged();
    }
    
    public void setTransactionsSelectionListener(TransactionsSelectionListener listener) {
        this.transactionsSelectionListener = listener;
    }

    public void rowDeleted(int rowIndex) {
        transactionsTableModel.rowDeleted(rowIndex);
        refresh();
    }
}
