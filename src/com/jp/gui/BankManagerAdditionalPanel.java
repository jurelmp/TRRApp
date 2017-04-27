/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.AdditionalFund;
import com.jp.model.OnDateFund;
import com.jp.utils.Utils;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author JurelP
 */
public class BankManagerAdditionalPanel extends JPanel {
    
    private JPanel onDateFundsPanel, onDateFundsSummaryPanel, onDateFundsFormPanel;
    private JTable onDateFundsTable;
    private JLabel onDateFundsTotalLabel;
    private JFormattedTextField onDateFundsTotalField, onDateFundsAmountField;
    private JButton onDateFundsSaveButton;
    private JTextField onDateFundsDetailsField;
    private OnDateFundsTableModel onDateFundsTableModel;
    private JPopupMenu onDateFundsPopup, additionalFundsPopup;
    private JMenuItem onDateFundDelete, additionalFundDelete;
    
    private JPanel additionalFundsPanel, additionalFundsSummaryPanel;
    private JTable additionalFundsTable;
    private JLabel additionalFundsLabel;
    private JFormattedTextField additionalFundsTotalField;
    private AdditionalFundsTableModel additionalFundsTableModel;
    
    private BMAdditionalEventListener bMAdditionalEventListener;
    private JPanel additionalFundsFormPanel;
    private JTextField additionalFundsDetailsField;
    private JFormattedTextField additionalFundsAmountField;
    private JButton additionalFundsSaveButton;
    
    public BankManagerAdditionalPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        init();
    }
    
    private void init() {
        onDateFundsPanel = new JPanel(new BorderLayout());
        onDateFundsFormPanel = new JPanel(new GridLayout(1, 4));
        onDateFundsSummaryPanel = new JPanel(new GridLayout(1, 2));
        onDateFundsDetailsField = new JTextField();
        onDateFundsAmountField = new JFormattedTextField();
        onDateFundsAmountField.setFormatterFactory(Utils.createDecimalFormat());
        onDateFundsAmountField.setHorizontalAlignment(JTextField.TRAILING);
        onDateFundsSaveButton = new JButton("Save");
        onDateFundsFormPanel.add(new JLabel("On Date Funds: "));
        onDateFundsFormPanel.add(onDateFundsDetailsField);
        onDateFundsFormPanel.add(onDateFundsAmountField);
        onDateFundsFormPanel.add(onDateFundsSaveButton);
        onDateFundsTableModel = new OnDateFundsTableModel();
        onDateFundsTable = new JTable(onDateFundsTableModel);
        onDateFundsTotalLabel = new JLabel("On Date Funds Total");
        onDateFundsTotalField = new JFormattedTextField();
        onDateFundsTotalField.setEditable(false);
        onDateFundsTotalField.setFormatterFactory(Utils.createDecimalFormat());
        onDateFundsTotalField.setHorizontalAlignment(JTextField.TRAILING);
        onDateFundsSummaryPanel.add(onDateFundsTotalLabel);
        onDateFundsSummaryPanel.add(onDateFundsTotalField);
        onDateFundsPopup = new JPopupMenu("Action");
        onDateFundDelete = new JMenuItem("Delete");
        onDateFundsPopup.add(onDateFundDelete);
        
        additionalFundsPanel = new JPanel(new BorderLayout());
        additionalFundsFormPanel = new JPanel(new GridLayout(1, 4));
        additionalFundsDetailsField = new JTextField();
        additionalFundsAmountField = new JFormattedTextField();
        additionalFundsAmountField.setFormatterFactory(Utils.createDecimalFormat());
        additionalFundsAmountField.setHorizontalAlignment(JTextField.TRAILING);
        additionalFundsSaveButton = new JButton("Save");
        additionalFundsFormPanel.add(new JLabel("Additional Funds: "));
        additionalFundsFormPanel.add(additionalFundsDetailsField);
        additionalFundsFormPanel.add(additionalFundsAmountField);
        additionalFundsFormPanel.add(additionalFundsSaveButton);
        additionalFundsSummaryPanel = new JPanel(new GridLayout(1, 2));
        additionalFundsTableModel = new AdditionalFundsTableModel();
        additionalFundsTable = new JTable(additionalFundsTableModel);
        additionalFundsLabel = new JLabel("Total");
        additionalFundsTotalField = new JFormattedTextField();
        additionalFundsTotalField.setFormatterFactory(Utils.createDecimalFormat());
        additionalFundsTotalField.setHorizontalAlignment(JTextField.TRAILING);
        additionalFundsTotalField.setEditable(false);
        additionalFundsPopup = new JPopupMenu();
        additionalFundDelete = new JMenuItem("Delete");
        additionalFundsPopup.add(additionalFundDelete);
        
        
        onDateFundsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = onDateFundsTable.rowAtPoint(e.getPoint());
                
                onDateFundsTable.getSelectionModel().setSelectionInterval(row, row);
                
                if (e.getButton() == MouseEvent.BUTTON3) {
                    onDateFundsPopup.show(onDateFundsTable, e.getX(), e.getY());
                }
            }
            
        });
        
        additionalFundsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = additionalFundsTable.rowAtPoint(e.getPoint());
                
                additionalFundsTable.getSelectionModel().setSelectionInterval(row, row);
                
                if (e.getButton() == MouseEvent.BUTTON3) {
                    additionalFundsPopup.show(additionalFundsTable, e.getX(), e.getY());
                }
            }
            
        });
        
        onDateFundDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = onDateFundsTable.getSelectedRow();
                
                if (bMAdditionalEventListener != null) {
                    bMAdditionalEventListener.delete(onDateFundsTableModel.getRow(row));
                    onDateFundsTableModel.deleteRow(row);
                    setOnDateSum();
                }
            }
        });
        
        additionalFundDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = additionalFundsTable.getSelectedRow();
                
                if (bMAdditionalEventListener != null) {
                    bMAdditionalEventListener.delete(additionalFundsTableModel.getRow(row));
                    additionalFundsTableModel.deleteRow(row);
                    setAdditionalSum();
                }
            }
        });
        
        onDateFundsTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (onDateFundsTable.getSelectedRow() > -1) {
                    OnDateFund selected = onDateFundsTableModel.getRow(onDateFundsTable.getSelectedRow());
                    if (bMAdditionalEventListener != null) {
                        bMAdditionalEventListener.update(selected);
                        setOnDateSum();
                    }
                }
            }
        });
        
        additionalFundsTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (additionalFundsTable.getSelectedRow() > -1) {
                    AdditionalFund selected = additionalFundsTableModel.getRow(additionalFundsTable.getSelectedRow());
                    if (bMAdditionalEventListener != null) {
                        bMAdditionalEventListener.update(selected);
                        setAdditionalSum();
                    }
                }
            }
        });
        
        onDateFundsSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnDateFund odf = new OnDateFund();
                
                String d = onDateFundsDetailsField.getText().trim();
                double a = onDateFundsAmountField.getValue() != null ? ((Number) onDateFundsAmountField.getValue()).doubleValue() : 0.00;
                
                odf.setDetails(d);
                odf.setAmount(a);
                
                if (bMAdditionalEventListener != null) {
                    bMAdditionalEventListener.insert(odf);
                    refreshOnDate();
                    onDateClearFields();
                }
            }
        });
        
        additionalFundsSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdditionalFund af = new AdditionalFund();
                
                String d = additionalFundsDetailsField.getText().trim();
                double a = additionalFundsAmountField.getValue() != null ? ((Number) additionalFundsAmountField.getValue()).doubleValue() : 0.00;
                
                af.setDetails(d);
                af.setAmount(a);
                
                if (bMAdditionalEventListener != null) {
                    bMAdditionalEventListener.insert(af);
                    refreshAdditional();
                    additionalClearFields();
                }
            }
        });
        
        onDateFundsTable.getColumnModel().getColumn(1).setCellRenderer(Utils.getDecimalFromatTableCellRenderer());
        additionalFundsTable.getColumnModel().getColumn(1).setCellRenderer(Utils.getDecimalFromatTableCellRenderer());
        
        additionalFundsSummaryPanel.add(additionalFundsLabel);
        additionalFundsSummaryPanel.add(additionalFundsTotalField);
        
        onDateFundsPanel.add(onDateFundsFormPanel, BorderLayout.PAGE_START);
        onDateFundsPanel.add(new JScrollPane(onDateFundsTable), BorderLayout.CENTER);
        onDateFundsPanel.add(onDateFundsSummaryPanel, BorderLayout.PAGE_END);
        
        additionalFundsPanel.add(additionalFundsFormPanel, BorderLayout.PAGE_START);
        additionalFundsPanel.add(new JScrollPane(additionalFundsTable), BorderLayout.CENTER);
        additionalFundsPanel.add(additionalFundsSummaryPanel, BorderLayout.PAGE_END);
        
        add(onDateFundsPanel);
        add(additionalFundsPanel);
    }
    
    public void setbMAdditionalEventListener(BMAdditionalEventListener bMAdditionalEventListener) {
        this.bMAdditionalEventListener = bMAdditionalEventListener;
    }
    
    void insertRow(OnDateFund odf) {
        onDateFundsTableModel.insertRow(odf);
        refreshOnDate();
    }
    
    void refreshOnDate() {
        onDateFundsTableModel.fireTableDataChanged();
        setOnDateSum();
    }
    
    void refreshAdditional() {
        additionalFundsTableModel.fireTableDataChanged();
        setAdditionalSum();
    }
    
    void setOnDateFunds(List<OnDateFund> onDateFunds) {
        onDateFundsTableModel.setData(onDateFunds);
        setOnDateSum();
    }
    
    void setOnDateSum() {
        onDateFundsTotalField.setValue(onDateFundsTableModel.getSum());
    }
    
    void setAdditionalSum() {
        additionalFundsTotalField.setValue(additionalFundsTableModel.getSum());
    }

    void insertAdditionalFund(AdditionalFund af) {
        additionalFundsTableModel.insertRow(af);
        refreshAdditional();
    }

    void setAdditionalFunds(List<AdditionalFund> additionalFunds) {
        additionalFundsTableModel.setData(additionalFunds);
        setAdditionalSum();
    }
    
    List<OnDateFund> getOnDateFunds() {
        return onDateFundsTableModel.getOnDateFunds();
    }
    
    List<AdditionalFund> getAdditionalFunds() {
        return additionalFundsTableModel.getAdditionalFunds();
    }
    
    public double getOnDateTotal() {
        return onDateFundsTableModel.getSum();
    }
    
    public double getAdditionalTotal() {
        return additionalFundsTableModel.getSum();
    }
    
    private void onDateClearFields() {
        onDateFundsDetailsField.setText("");
        onDateFundsAmountField.setValue(0);
    }
    
    private void additionalClearFields() {
        additionalFundsDetailsField.setText("");
        additionalFundsAmountField.setValue(0);
    }
    
    private class OnDateFundsTableModel extends AbstractTableModel {
        
        private List<OnDateFund> onDateFunds = new ArrayList<>();
        private String[] columnNames = {"Details", "Amount"};
        
        @Override
        public int getRowCount() {
            return onDateFunds.size();
        }
        
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            OnDateFund odf = onDateFunds.get(rowIndex);
            
            switch (columnIndex) {
                case 0:
                    return odf.getDetails();
                case 1:
                    return odf.getAmount();
                default:
                    return odf;
            }
        }
        
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            OnDateFund odf = onDateFunds.get(rowIndex);
            
            if (aValue instanceof String) {
                odf.setDetails((String) aValue);
            } else if (aValue instanceof Double) {
                odf.setAmount((Double) aValue);
            }
            
            fireTableCellUpdated(rowIndex, columnIndex);
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
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
        
        private void insertRow(OnDateFund odf) {
            this.onDateFunds.add(odf);
        }
        
        private void setData(List<OnDateFund> onDateFunds) {
            this.onDateFunds = onDateFunds;
        }
        
        private OnDateFund getRow(int rowIndex) {
            return onDateFunds.get(rowIndex);
        }
        
        private void deleteRow(int row) {
            fireTableRowsDeleted(row, row);
            this.onDateFunds.remove(row);
        }
        
        public double getSum() {
            double sum = 0;
            
            for (OnDateFund odf : onDateFunds) {
                sum += odf.getAmount();
            }
            
            return sum;
        }

        public List<OnDateFund> getOnDateFunds() {
            return onDateFunds;
        }
        
    }
    
    private class AdditionalFundsTableModel extends AbstractTableModel {

        private List<AdditionalFund> additionalFunds = new ArrayList<>();
        private String[] columnNames = {"Details", "Amount"};
        
        @Override
        public int getRowCount() {
            return additionalFunds.size();
        }
        
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            AdditionalFund odf = additionalFunds.get(rowIndex);
            
            switch (columnIndex) {
                case 0:
                    return odf.getDetails();
                case 1:
                    return odf.getAmount();
                default:
                    return odf;
            }
        }
        
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            AdditionalFund odf = additionalFunds.get(rowIndex);
            
            if (aValue instanceof String) {
                odf.setDetails((String) aValue);
            } else if (aValue instanceof Double) {
                odf.setAmount((Double) aValue);
            }
            
            fireTableCellUpdated(rowIndex, columnIndex);
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
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
        
        private void insertRow(AdditionalFund odf) {
            this.additionalFunds.add(odf);
        }
        
        private void setData(List<AdditionalFund> onDateFunds) {
            this.additionalFunds = onDateFunds;
        }
        
        private AdditionalFund getRow(int rowIndex) {
            return additionalFunds.get(rowIndex);
        }
        
        private void deleteRow(int row) {
            fireTableRowsDeleted(row, row);
            this.additionalFunds.remove(row);
        }
        
        public double getSum() {
            double sum = 0;
            
            for (AdditionalFund odf : additionalFunds) {
                sum += odf.getAmount();
            }
            
            return sum;
        }

        public List<AdditionalFund> getAdditionalFunds() {
            return additionalFunds;
        }
        
    }
}
