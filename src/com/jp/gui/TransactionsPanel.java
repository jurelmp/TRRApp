/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Account;
import com.jp.model.Transaction;
import com.jp.utils.Utils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author JurelP
 */
public class TransactionsPanel extends JPanel {
    
    private Account currentAccount;
    
    private JTable transactionsTable;
    
    private PlaceholderTextField filterField;
    
    private TransactionsTableModel transactionsTableModel;
    
    private TransactionsSelectionListener transactionsSelectionListener;
    private TransactionClearActionListener transactionClearActionListener;
    
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
        transactionsTable.setAutoCreateRowSorter(true);
        
        filterField = new PlaceholderTextField();
        filterField.setPlaceholder("Filter by Item No / Ref / Date / Payee / Deposit / Payment.");
        filterField.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Listeners
        transactionsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (transactionsTable.getSelectedRow() > -1) {
//                    System.out.println(accountsTable.getValueAt(accountsTable.getSelectedRow(), -1));
                    
//                    Account selected = (Account) accountsTable.getValueAt(accountsTable.getSelectedRow(), -1);
                    Transaction selected = (Transaction) transactionsTableModel.getTransactionRow(transactionsTable.convertRowIndexToModel(transactionsTable.getSelectedRow()));
                    
                    if (transactionsSelectionListener != null) {
                        transactionsSelectionListener.transactionSelected(selected, transactionsTable.getSelectedRow());
                    }
//                    System.out.println(e.getValueIsAdjusting());
//                    if (e.getValueIsAdjusting()) {
//                        // TODO 
//                        System.out.println(e.getSource());
//                        ListSelectionModel model = (ListSelectionModel) e.getSource();
//                        
//                    }
                }
            }
        });
        
        transactionsTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (transactionsTable.getSelectedRow() > -1) {
                    Transaction selected = (Transaction) transactionsTableModel.getTransactionRow(transactionsTable.convertRowIndexToModel(transactionsTable.getSelectedRow()));
//                    System.out.println(selected);
                    if (transactionClearActionListener != null) {
                        transactionClearActionListener.valueChanged(selected);
//                        refresh();
                    }
                }
                
            }
        });
        
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterFieldActionPerformed(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterFieldActionPerformed(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterFieldActionPerformed(e);
            }
        });
        
//        filterField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                filterFieldActionPerformed(e);
//            }
//        });
        
//        TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
//            
//            private static final long serialVersionUID = 1L;
//            
//            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
//
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, 
//                    boolean isSelected, boolean hasFocus, int row, int column) {
//                
//                if (value instanceof Date) {
//                    value = sdf.format(value);
//                }
//                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            }
//        };
        
        transactionsTable.getColumnModel().getColumn(0).setWidth(2);
        transactionsTable.getColumnModel().getColumn(0).setMaxWidth(2);
        transactionsTable.getColumnModel().getColumn(2).setCellRenderer(new DateFormatTableCellRenderer());
        transactionsTable.getColumnModel().getColumn(4).setCellRenderer(Utils.getDecimalFromatTableCellRenderer());
        transactionsTable.getColumnModel().getColumn(5).setCellRenderer(Utils.getDecimalFromatTableCellRenderer());
        
        // Add the components to the panel
        add(filterField, BorderLayout.PAGE_START);
        add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    }
    
    private void filterFieldActionPerformed(DocumentEvent event) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(((AbstractTableModel) transactionsTable.getModel()));
        sorter.setRowFilter(RowFilter.regexFilter(filterField.getText()));
        transactionsTable.setRowSorter(sorter);
    }
    
    public void setAccount(Account account) {
        this.currentAccount = account;
    }
    
    public void setData(List<Transaction> transactions) {
        transactionsTableModel.setData(transactions);
//        transactionsTable.repaint();
        this.refresh();
    }
    
    public void addRow(Transaction t) {
        transactionsTableModel.addRow(t);
        ListSelectionModel listSelectionModel = transactionsTable.getSelectionModel();
        
        refresh();
        
        int row = transactionsTable.getRowCount();
        
        listSelectionModel.addSelectionInterval(row - 1, row - 1);
        
        transactionsTable.scrollRectToVisible(transactionsTable.getCellRect(row - 1, 0, true));
    }
    
    public void refresh() {
        transactionsTableModel.fireTableDataChanged();
    }
    
    public void setTransactionsSelectionListener(TransactionsSelectionListener listener) {
        this.transactionsSelectionListener = listener;
    }
    
    public void setTransactionClearActionListener(TransactionClearActionListener listener) {
        this.transactionClearActionListener = listener;
    }

    public void rowDeleted(int rowIndex) {
        clearSelection();
        transactionsTableModel.rowDeleted(rowIndex);
        
        refresh();
    }
    
    public void clearSelection() {
        transactionsTable.clearSelection();
    }
}
