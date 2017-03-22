/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Account;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author JurelP
 */
public class BankManagerSettingsTableModel extends AbstractTableModel {
    
//    private List<Transaction> transactions = new ArrayList<>();
//    private String[] columnNames = {"#", "REF", "DATE", "PAYEE", "DEPOSIT", "PAYMENT", "CLEAR"};
    
    private List<Account> accounts = new ArrayList<>();
    private String[] columnNames = {"BANK CODE", "BANK NAME", "ACTION"};

    @Override
    public int getRowCount() {
        return accounts.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Account account = accounts.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return account.getCode();
            case 1:
                return account.getName();
            case 2:
                return account.isActive();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
        }
        return this.getValueAt(0, columnIndex) == null ? Object.class : getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 2:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Account account = accounts.get(rowIndex);
        account.setActive((Boolean) aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        fireTableDataChanged();
    }

    void clearAccounts() {
        this.accounts.clear();
    }
    
    Account getAccount(int rowIndex) {
        return this.accounts.get(rowIndex);
    }
}
