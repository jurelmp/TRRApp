/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Other;
import com.jp.utils.Utils;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author JurelP
 */
public class BankManagerOthersPanel extends JPanel {
    
    private JPanel formPanel, summaryPanel;
    private JTable othersTable;
    private JLabel summaryTotalLabel;
    private JTextField detailsField;
    private JFormattedTextField amountField, summaryTotalField;
    private JButton addButton;
    
    private DecimalFormat decimalFormat;
    
    private OthersTableModel tableModel;
    
    private BankManagerFormListener bankManagerFormListener;
    
    private JPopupMenu popupMenu;
    private JMenuItem deleteItem;

    public BankManagerOthersPanel() {
        setLayout(new BorderLayout());
        
        init();
    }
    
    private void init() {
        decimalFormat = new DecimalFormat("#,##0.00");
        
        popupMenu = new JPopupMenu();
        deleteItem = new JMenuItem("Delete");
        popupMenu.add(deleteItem);
        
        formPanel = new JPanel(new GridLayout(1, 3));
        summaryPanel = new JPanel(new GridLayout(1, 2));
        tableModel = new OthersTableModel();
        othersTable = new JTable(tableModel);
        othersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        othersTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        
        detailsField = new JTextField();
        amountField = new JFormattedTextField();
        amountField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(decimalFormat)));
        amountField.setHorizontalAlignment(JTextField.TRAILING);
        addButton = new JButton("Save");
        
        summaryTotalLabel = new JLabel("Total");
        summaryTotalField = new JFormattedTextField();
        summaryTotalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(decimalFormat)));
        summaryTotalField.setHorizontalAlignment(JTextField.TRAILING);
        summaryTotalField.setEditable(false);
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Other other = new Other();
                String details = detailsField.getText().trim();
                double amount = amountField.getValue() != null ? ((Number) amountField.getValue()).doubleValue(): 0.00;
                other.setDetails(details);
                other.setAmount(new BigDecimal(amount));
                
                if (bankManagerFormListener != null) {
                    bankManagerFormListener.otherActionEmitted(other);
                    setTotalField();
                    clearFields();
                }
            }
        });
        
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (othersTable.getSelectedRow() > -1) {
                    Other selected = tableModel.getRow(othersTable.getSelectedRow());
                    if (bankManagerFormListener != null) {
                        bankManagerFormListener.update(selected);
                        setTotalField();
                    }
                }
            }
        });
        
        othersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = othersTable.rowAtPoint(e.getPoint());
                
                othersTable.getSelectionModel().setSelectionInterval(row, row);
                
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(othersTable, e.getX(), e.getY());
                }
            }
        
        });
        
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = othersTable.getSelectedRow();
                
                
                if (bankManagerFormListener != null) {
                    bankManagerFormListener.delete(tableModel.getRow(row));
                    tableModel.deleteRow(row);
                    setTotalField();
                }
            }
        });
        
        othersTable.getColumnModel().getColumn(1).setCellRenderer(Utils.getDecimalFromatTableCellRenderer());
        
        formPanel.add(detailsField);
        formPanel.add(amountField);
        formPanel.add(addButton);
        
        summaryPanel.add(summaryTotalLabel);
        summaryPanel.add(summaryTotalField);
        
        add(formPanel, BorderLayout.PAGE_START);
        add(new JScrollPane(othersTable), BorderLayout.CENTER);
        add(summaryPanel, BorderLayout.PAGE_END);
    }
    
    public void setFormListener(BankManagerFormListener bankManagerFormListener) {
        this.bankManagerFormListener = bankManagerFormListener;
    }

    void refresh() {
        tableModel.fireTableDataChanged();
        setTotalField();
    }
    
    private void setTotalField() {
        summaryTotalField.setValue(tableModel.getSumOthers());
    }

    private void clearFields() {
        detailsField.setText(null);
        amountField.setText(null);
    }
    
    void update(Other other) {
        tableModel.insertOther(other);
        refresh();
    }

    void setData(List<Other> othersSummary) {
        tableModel.setData(othersSummary);
        setTotalField();
//        refresh();
    }
    
    List<Other> getOtherFunds() {
        return tableModel.getOtherFunds();
    }
    
    public double getSum() {
        return tableModel.getSumOthers().doubleValue();
    }
    
    private class OthersTableModel extends AbstractTableModel {
        
        private List<Other> otherFunds = new ArrayList<>();
        private String[] columnNames = {"Details", "Amount"};

        @Override
        public int getRowCount() {
            return otherFunds.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Other o = otherFunds.get(rowIndex);
            
            switch (columnIndex) {
                case 0:
                    return o.getDetails();
                case 1:
                    return o.getAmount();
                default:
                    return o;
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return Double.class;
                default:
                    return Object.class;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Other other = otherFunds.get(rowIndex);
            if (aValue instanceof String) {
                otherFunds.get(rowIndex).setDetails((String) aValue);
            } else if (aValue instanceof Double) {
                double v = (Double) aValue;
                otherFunds.get(rowIndex).setAmount(new BigDecimal(v));
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        private void insertOther(Other other) {
            this.otherFunds.add(other);
        }

        private void setData(List<Other> othersSummary) {
            this.otherFunds = othersSummary;
        }
        
        public BigDecimal getSumOthers() {
            BigDecimal sum = new BigDecimal(0);
            for (int i = 0; i < otherFunds.size(); i++) {
                sum = sum.add(otherFunds.get(i).getAmount());
            }
            return sum;
        }
        
        public Other getRow(int rowIndex) {
            return otherFunds.get(rowIndex);
        }
        
        public void deleteRow(int rowIndex) {
            fireTableRowsDeleted(rowIndex, rowIndex);
            this.otherFunds.remove(rowIndex);
        }

        public List<Other> getOtherFunds() {
            return otherFunds;
        }
    }
    
}
