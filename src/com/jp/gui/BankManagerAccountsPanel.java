/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.AccountSummary;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author JurelP
 */
public class BankManagerAccountsPanel extends JPanel {
    
    private JTable accountsTable;
    private JPanel summary;
    
    private JLabel accountsSummaryTotalLabel;
    private JFormattedTextField accountsSummaryActualTotalField, accountsSummaryPreliminaryTotalField;
    
    private DecimalFormat decimalFormat;
    
    AccountsSummaryTableModel tableModel;
    
    public BankManagerAccountsPanel() {
        setLayout(new BorderLayout());
        
        init();
    }
    
    private void init() {
        
        decimalFormat = new DecimalFormat("#,###.00");
        
        tableModel = new AccountsSummaryTableModel();
        accountsTable = new JTable(tableModel);
        accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountsTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        summary = new JPanel(new GridLayout(1, 3));
        accountsSummaryTotalLabel = new JLabel("Total");
        accountsSummaryActualTotalField = new JFormattedTextField();
        accountsSummaryActualTotalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(decimalFormat)));
        accountsSummaryActualTotalField.setHorizontalAlignment(JTextField.TRAILING);
        accountsSummaryActualTotalField.setEditable(false);
        accountsSummaryPreliminaryTotalField = new JFormattedTextField();
        accountsSummaryPreliminaryTotalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(decimalFormat)));
        accountsSummaryPreliminaryTotalField.setHorizontalAlignment(JTextField.TRAILING);
        accountsSummaryPreliminaryTotalField.setEditable(false);
        summary.add(accountsSummaryTotalLabel);
        summary.add(accountsSummaryActualTotalField);
        summary.add(accountsSummaryPreliminaryTotalField);
        
        
        add(new JScrollPane(accountsTable), BorderLayout.CENTER);
        add(summary, BorderLayout.PAGE_END);
    }
    
    private void setValues() {
        accountsSummaryActualTotalField.setValue(tableModel.getSumActual());
        accountsSummaryPreliminaryTotalField.setValue(tableModel.getSumPreliminary());
    }
    
    public void setData(List<AccountSummary> summaries) {
        tableModel.setData(summaries);
    }
    
    public void refresh() {
        setValues();
    }
    
    List<AccountSummary> getAccountSummaries() {
        return tableModel.getAccountSummaries();
    }
    
    public double getActual() {
        return tableModel.getSumActual().doubleValue();
    }
    
    public double getPreliminary() {
        return tableModel.getSumPreliminary().doubleValue();
    }
    
    private class AccountsSummaryTableModel extends AbstractTableModel {
        
        private List<AccountSummary> accountSummaries = new ArrayList<>();
        private String[] columnNames = {"Bank Code", "Actual", "Preliminary"};

        @Override
        public int getRowCount() {
            return accountSummaries.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            AccountSummary summary = accountSummaries.get(rowIndex);
            
            switch (columnIndex) {
                case 0:
                    return summary.getBankCode();
                case 1:
                    return summary.getActual();
                case 2:
                    return summary.getPreliminary();
                default:
                    return summary;
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
                    return Double.class;
                case 2:
                    return Double.class;
                default:
                    return Object.class;
            }
        }
        
        private void setData(List<AccountSummary> summaries) {
            accountSummaries = summaries;
        }
        
        public BigDecimal getSumActual() {
            BigDecimal sum = new BigDecimal(0);
            for (int i = 0; i < accountSummaries.size(); i++) {
                sum = sum.add(accountSummaries.get(i).getActual());
//                System.out.println(sum);
            }
            return sum;
        }

        public BigDecimal getSumPreliminary() {
            BigDecimal sum = new BigDecimal(0);
            for (int i = 0; i < accountSummaries.size(); i++) {
                sum = sum.add(accountSummaries.get(i).getPreliminary());
//                System.out.println(sum);
            }
            return sum;
        }

        public List<AccountSummary> getAccountSummaries() {
            return accountSummaries;
        }

    }
}
