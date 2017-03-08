/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Account;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author JurelP
 */
public class AccountsTableModel extends AbstractTableModel {

    private List<Account> accounts;
    private String[] columnNames = {"#", "CODE", "NAME"};
    
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
                return account.getId();
            case 1:
                return account.getCode();
            case 2:
                return account.getName();
        }
        return account;
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
        }
        return Object.class;
    }
    
    public void setData(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    public Object getAccountRow(int rowIndex) {
        return accounts.get(rowIndex);
    }
    
    public void addRow(Account account) {
        this.accounts.add(account);
//        this.fireTableDataChanged();
    }
    
    
    
}
