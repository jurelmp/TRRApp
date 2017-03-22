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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
    private JLabel transactionNoLabel, refLabel, dateLabel, payeeLabel, depositLabel, paymentLabel, clearLabel, descLabel;
    private JTextField transactionNoField, refField, dateField, payeeField;
    private JFormattedTextField depositField, paymentField;
    private JTextArea descField;
//    private ButtonGroup typeBtnGroup;
//    private JCheckBox depositRdioBtn, paymentRdioBtn;
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
        depositLabel = new JLabel("Deposit");
        paymentLabel = new JLabel("Payment");
        clearLabel = new JLabel("Clear");
        descLabel = new JLabel("Remarks (Optional)");
        // Fields
        transactionNoField = new JTextField(15);
        transactionNoField.setEditable(false);
        refField = new JTextField(15);
        dateField = new JTextField(15);
        datePicker = new JXDatePicker(Utils.getDateNow());
        payeeField = new JTextField(15);
        payeeField.setHorizontalAlignment(JTextField.LEFT);
        depositField = new JFormattedTextField();
        depositField.setColumns(15);
        depositField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(amountDecimalFormat)));
        depositField.setHorizontalAlignment(JTextField.TRAILING);
        paymentField = new JFormattedTextField();
        paymentField.setColumns(15);
        paymentField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(amountDecimalFormat)));
        paymentField.setHorizontalAlignment(JTextField.TRAILING);
//        depositRdioBtn = new JCheckBox(TransactionType.deposit.name());
//        paymentRdioBtn = new JCheckBox(TransactionType.payment.name());
//        depositRdioBtn.setActionCommand("deposit");
//        paymentRdioBtn.setActionCommand("payment");
//        depositRdioBtn.setSelected(true);
//        paymentRdioBtn.setSelected(true);
//        typeBtnGroup = new ButtonGroup();
//        typeBtnGroup.add(depositRdioBtn);
//        typeBtnGroup.add(paymentRdioBtn);
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
        formPanel.add(depositLabel, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(depositField, gbc);
        
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
        formPanel.add(paymentLabel, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(paymentField, gbc);
        
        // Row
//        gbc.gridy++;
//        formPanel.add(paymentRdioBtn, gbc);
        
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
        double deposit = depositField.getValue() != null ? ((Number) depositField.getValue()).doubleValue() : 0.00;
        double payment = paymentField.getValue() != null ? ((Number) paymentField.getValue()).doubleValue() : 0.00;
        String desc = descField.getText().trim();
        boolean clear = clearCheckBox.isSelected();
        
//        if (currentAccount == null && currentTransaction == null) {
//            return null;
//        }
//        
//        if (update) {
//            return new Transaction( 0, ref, payee, deposit, payment, desc, date, clear);
//        }
//        
//        if (currentAccount != null) {
//            return new Transaction( currentAccount.getId(), ref, payee, deposit, payment, desc, date, clear);
//        }
//        
//        return null;
        
        return new Transaction( 0, ref, payee, deposit, payment, desc, date, clear);
    }
        
    private void updateCurrentTransaction() {
//        Transaction t = extractDataFromForm();

        String ref = refField.getText().trim();
        Date date = new Date(datePicker.getDate().getTime());
        String payee = payeeField.getText().trim();
        double deposit = depositField.getValue() != null ? ((Number) depositField.getValue()).doubleValue() : 0.00;
        double payment = paymentField.getValue() != null ? ((Number) paymentField.getValue()).doubleValue() : 0.00;
        String desc = descField.getText().trim();
        boolean clear = clearCheckBox.isSelected();
        
         if (update) {
            currentTransaction.setReference(ref);
            currentTransaction.setDate(date);
            currentTransaction.setPayee(payee);
            currentTransaction.setDeposit(deposit);
            currentTransaction.setPayment(payment);
            currentTransaction.setDesc(desc);
            currentTransaction.setClear(clear);
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
            depositField.setValue(currentTransaction.getDeposit());
            paymentField.setValue(currentTransaction.getPayment());
            descField.setText(currentTransaction.getDesc());
            clearCheckBox.setSelected(currentTransaction.isClear());
            datePicker.setDate(currentTransaction.getDate());
        }
    }
    
    public void enableFields(boolean flag) {
//        transactionNoField.setEnabled(flag);
        refField.setEditable(flag);
        payeeField.setEditable(flag);
        depositField.setEditable(flag);
        paymentField.setEditable(flag);
        descField.setEditable(flag);
        datePicker.setEditable(flag);
        clearCheckBox.setEnabled(flag);
    }
    
    private void clearFields() {
        transactionNoField.setText(null);
        refField.setText(null);
        payeeField.setText(null);
        depositField.setText(null);
        paymentField.setText(null);
        descField.setText(null);
        datePicker.setDate(Utils.getDateNow());
        clearCheckBox.setSelected(false);
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
                   if (currentAccount.getId() == 0) {
                       JOptionPane.showMessageDialog(this, 
                               "Please select an account.", 
                               "Error Saving Item", 
                               JOptionPane.ERROR_MESSAGE);
                   } else {
                       Transaction transTemp = extractDataFromForm();
                       transTemp.setAccountId(currentAccount.getId());
                       listener.buttonSaveClicked(transTemp);
                   }
                   
               } else {
                   updateCurrentTransaction();
                   listener.buttonUpdateClicked(currentTransaction);
               }
//               if (!update) {
//                   listener.buttonSaveClicked(extractDataFromForm(), TransactionFormActionListener.MODE_INSERT);
//               } else {
//                   getUpdatedValues();
//                   listener.buttonSaveClicked(currentTransaction, TransactionFormActionListener.MODE_UPDATE);
//                   update = false;
//               }
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
        depositField.setFont(font);
        paymentField.setFont(font);
        descField.setFont(font);
        clearCheckBox.setFont(font);
    }
}
