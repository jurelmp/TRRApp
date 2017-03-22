/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author JurelP
 */
public class TransactionsTableModel extends AbstractTableModel {
    
    private List<Transaction> transactions = new ArrayList<>();
    private String[] columnNames = {"#", "REF", "DATE", "PAYEE", "DEPOSIT", "PAYMENT", "CLEAR"};

    @Override
    public int getRowCount() {
//        if (transactions == null || transactions.isEmpty()) {
//            return -1;
//        }
        return transactions.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (transactions == null || transactions.isEmpty()) {
            return null;
        }
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
                return transaction.getDeposit();
            case 5:
                return transaction.getPayment();
            case 6:
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
            case 5:
                return Double.class;
            case 6:
                return Boolean.class;
        }
        return this.getValueAt(0, columnIndex) == null ? Object.class : getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 6:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        super.setValueAt(aValue, rowIndex, columnIndex);
        Transaction transaction = transactions.get(rowIndex);
        transaction.setClear((Boolean) aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public void setData(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public Object getTransactionRow(int rowIndex) {
        return transactions.get(rowIndex);
    }
    
    public void addRow(Transaction transaction) {
        this.transactions.add(0, transaction);
//        this.fireTableDataChanged();
    }

    public void rowDeleted(int rowIndex) {
        fireTableRowsDeleted(rowIndex, rowIndex);
        this.transactions.remove(rowIndex);
    }
    
}
