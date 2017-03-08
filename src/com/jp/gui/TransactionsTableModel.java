/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Transaction;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author JurelP
 */
public class TransactionsTableModel extends AbstractTableModel {
    
    private List<Transaction> transactions;
    private String[] columnNames = {"#", "REF", "DATE", "PAYEE", "AMOUNT", "DESC", "TYPE", "CLEAR"};

    @Override
    public int getRowCount() {
        return transactions.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction transaction = transactions.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return transaction.getId();
            case 1:
                return transaction.getReference();
            case 2:
                return transaction.getDate();
            case 3:
                return transaction.getPayee();
            case 4:
                return transaction.getAmount();
            case 5:
                return transaction.getDesc();
            case 6:
                return transaction.getType();
            case 7:
                return transaction.isClear();
        }
        return transaction;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 4:
                return Double.class;
            case 7:
                return Boolean.class;
        }
        return Object.class;
    }
    
    public void setData(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public Object getTransactionRow(int rowIndex) {
        return transactions.get(rowIndex);
    }
    
    public void addRow(Transaction transaction) {
        this.transactions.add(transaction);
//        this.fireTableDataChanged();
    }

    public void rowDeleted(int rowIndex) {
        this.transactions.remove(rowIndex);
    }
    
}
