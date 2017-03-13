/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Account;
import com.jp.model.Transaction;
import com.jp.model.TransactionType;
import com.jp.utils.Utils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author JurelP
 */
public class TransactionFormPanel extends JPanel implements ActionListener{
    
    private JPanel formPanel, buttonsPanel;
    private JButton newButton, saveButton, updateButton, removeButton, cancelButton; 
    private JLabel transactionNoLabel, refLabel, dateLabel, payeeLabel, amountLabel, actionLabel, clearLabel, descLabel;
    private JTextField transactionNoField, refField, dateField, payeeField;
    private JFormattedTextField amountField;
    private JTextArea descField;
    private ButtonGroup typeBtnGroup;
    private JRadioButton depositRdioBtn, paymentRdioBtn;
    private JCheckBox clearCheckBox;
    
    private JXDatePicker datePicker;
    
    private DecimalFormat amountDecimalFormat;
   
    private TransactionFormActionListener listener;
    private Account currentAccount;
    
    private Transaction currentTransaction;
    private boolean update = true;
    private int currentRowIndex;
    
    private final Font font = new Font(Font.SANS_SERIF, 0, 14);
    
    public TransactionFormPanel() {
        Dimension dim = getPreferredSize();
        dim.height = 200;
        setPreferredSize(dim);
        setMinimumSize(dim);
        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new BorderLayout());
        
        // Initialize the components
        amountDecimalFormat = new DecimalFormat("#,###.00");
        // Panes
        formPanel = new JPanel(new GridBagLayout());
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        // Buttons
        newButton = new JButton("New");
        saveButton = new JButton("Save");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");
        cancelButton = new JButton("Cancel");
        Dimension buttonSize = removeButton.getPreferredSize();
//        buttonSize.width = 20;
        newButton.setMaximumSize(buttonSize);
        saveButton.setMaximumSize(buttonSize);
        updateButton.setMaximumSize(buttonSize);
        cancelButton.setMaximumSize(buttonSize);
        removeButton.setMaximumSize(buttonSize);
        // Labels
        transactionNoLabel = new JLabel("#");
        refLabel = new JLabel("Ref");
        dateLabel = new JLabel("Date");
        payeeLabel = new JLabel("Payee");
        amountLabel = new JLabel("Amount");
        actionLabel = new JLabel("Action");
        clearLabel = new JLabel("Clear");
        descLabel = new JLabel("Remarks (Optional)");
        // Fields
        transactionNoField = new JTextField(15);
        transactionNoField.setEnabled(false);
        refField = new JTextField(15);
        dateField = new JTextField(15);
        datePicker = new JXDatePicker(Utils.getDateNow());
        payeeField = new JTextField(15);
        amountField = new JFormattedTextField();
        amountField.setColumns(15);
        amountField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(amountDecimalFormat)));
        amountField.setHorizontalAlignment(JTextField.TRAILING);
        depositRdioBtn = new JRadioButton(TransactionType.deposit.name());
        paymentRdioBtn = new JRadioButton(TransactionType.payment.name());
        depositRdioBtn.setActionCommand("deposit");
        paymentRdioBtn.setActionCommand("payment");
        depositRdioBtn.setSelected(true);
        typeBtnGroup = new ButtonGroup();
        typeBtnGroup.add(depositRdioBtn);
        typeBtnGroup.add(paymentRdioBtn);
        clearCheckBox = new JCheckBox("Clear");
        descField = new JTextArea(5, 15);
        
        enableFields(false);
        setFonts();
        
        // Listeners
//        saveButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (listener != null) {
//                    listener.buttonSaveClicked(extractDataFromForm());
//                }
//            }
//        });
        newButton.addActionListener(this);
        saveButton.addActionListener(this);
        updateButton.addActionListener(this);
        removeButton.addActionListener(this);
        cancelButton.addActionListener(this);
        
        enableButtons(true, false, false, false, false);
        
        // Layouts and add the components to the panel
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Row
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(2, 8, 2, 8);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(transactionNoLabel, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(transactionNoField, gbc);
        
        // Row
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(refLabel, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(refField, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(dateLabel, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;
//        formPanel.add(dateField, gbc);
        formPanel.add(datePicker, gbc);
        
        // Row
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(payeeLabel, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(payeeField, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(amountLabel, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(amountField, gbc);
        
        // Row
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(descLabel, gbc);
        gbc.gridx++;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(new JScrollPane(descField), gbc);
        gbc.gridx++;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(actionLabel, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(depositRdioBtn, gbc);
        
        // Row
        gbc.gridy++;
        formPanel.add(paymentRdioBtn, gbc);
        
        // Row
        gbc.gridy++;
        formPanel.add(clearCheckBox, gbc);
        
        // Adding buttons and set same width to buttons pane
        buttonsPanel.add(newButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(cancelButton);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.EAST);
    }
    
    public void setTransactionFormActionListener(TransactionFormActionListener l) {
        this.listener = l;
    }
    
    private Transaction extractDataFromForm() {
        
        String ref = refField.getText().trim();
        Date date = new Date(datePicker.getDate().getTime());
        String payee = payeeField.getText().trim();
//        double amount = amountDecimalFormat.parse(amountField.getText()).doubleValue();
        double amount = amountField.getValue()!= null ? ((Number) amountField.getValue()).doubleValue() : 0.0;
        String desc = descField.getText().trim();
        TransactionType type = TransactionType.valueOf(typeBtnGroup.getSelection().getActionCommand());
        boolean clear = clearCheckBox.isSelected();
        
        if (currentAccount == null && currentTransaction == null) {
            return null;
        }
        
        if (update) {
            return new Transaction( 0, ref, payee, amount, desc, date, type, clear);
        }
        
        if (currentAccount != null) {
            return new Transaction( currentAccount.getId(), ref, payee, amount, desc, date, type, clear);
        }
        
        return null;
    }
        
    private void getUpdatedValues() {
        Transaction t = extractDataFromForm();
        
         if (update) {
            currentTransaction.setReference(t.getReference());
            currentTransaction.setDate(Utils.formatSqlDate(t.getDate()));
            currentTransaction.setAmount(t.getAmount());
            currentTransaction.setDesc(t.getDesc());
            currentTransaction.setType(t.getType());
            currentTransaction.setClear(t.isClear());
        }
    }

    void setAccount(Account account) {
        this.currentAccount = account;
    }
    
    void setTransaction(Transaction t, int rowIndex) {
        this.currentTransaction = t;
        this.currentRowIndex = rowIndex;
        update = true;
        enableButtons(true, false, true, true, false);
        populateFields();
    }
    
    private void populateFields() {
        if (currentTransaction != null) {
            transactionNoField.setText(String.valueOf(currentTransaction.getId()));
            refField.setText(currentTransaction.getReference());
            payeeField.setText(currentTransaction.getPayee());
            amountField.setValue(currentTransaction.getAmount());
            descField.setText(currentTransaction.getDesc());
            clearCheckBox.setSelected(currentTransaction.isClear());
            depositRdioBtn.setSelected(currentTransaction.getType() == TransactionType.deposit);
            paymentRdioBtn.setSelected(currentTransaction.getType() == TransactionType.payment);
            datePicker.setDate(currentTransaction.getDate());
        }
    }
    
    public void enableFields(boolean flag) {
//        transactionNoField.setEnabled(flag);
        refField.setEnabled(flag);
        payeeField.setEnabled(flag);
        amountField.setEnabled(flag);
        descField.setEnabled(flag);
        datePicker.setEnabled(flag);
        clearCheckBox.setEnabled(flag);
        depositRdioBtn.setEnabled(flag);
        paymentRdioBtn.setEnabled(flag);
    }
    
    private void clearFields() {
        transactionNoField.setText(null);
        refField.setText(null);
        payeeField.setText(null);
        amountField.setText(null);
        descField.setText(null);
        datePicker.setDate(Utils.getDateNow());
        clearCheckBox.setSelected(false);
        depositRdioBtn.setSelected(true);
    }
    
    private void enableButtons(boolean... flags) {
        int index = 0;
        newButton.setEnabled(flags[index++]);
        saveButton.setEnabled(flags[index++]);
        updateButton.setEnabled(flags[index++]);
        removeButton.setEnabled(flags[index++]);
        cancelButton.setEnabled(flags[index]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       JButton clicked = (JButton) e.getSource();
       
       if (clicked == newButton) {
           enableFields(true);
           enableButtons(false, true, false, false, true);
           update = false;
           clearFields();
       } else if (clicked == saveButton) {
           if (listener != null) {
               
               if (!update) {
                   listener.buttonSaveClicked(extractDataFromForm(), TransactionFormActionListener.MODE_INSERT);
               } else {
                   getUpdatedValues();
                   listener.buttonSaveClicked(currentTransaction, TransactionFormActionListener.MODE_UPDATE);
                   update = false;
               }
               enableFields(false);
//               update = true;
               enableButtons(true, false, false, false, false);
           }
       } else if (clicked == updateButton) {
           update = true;
           enableFields(true);
           enableButtons(false, true, false, true, true);
       } else if (clicked == removeButton) {
           if (listener != null) {
               listener.buttonRemoveClicked(currentTransaction, currentRowIndex);
               
               enableButtons(true, false, false, false, false);
           }
           enableFields(false);
       } else if (clicked == cancelButton) {
           enableFields(false);
           update = true;
           currentTransaction = null;
           enableButtons(true, false, false, false, false);
           clearFields();
       }
    }
    
    private void setFonts() {
        transactionNoField.setFont(font);
        refField.setFont(font);
        dateField.setFont(font);
        payeeField.setFont(font);
        amountField.setFont(font);
        descField.setFont(font);
        depositRdioBtn.setFont(font);
        paymentRdioBtn.setFont(font);
        clearCheckBox.setFont(font);
    }
}
