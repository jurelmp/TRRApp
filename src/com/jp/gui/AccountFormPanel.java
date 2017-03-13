/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Account;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author JurelP
 */
public class AccountFormPanel extends JPanel implements ActionListener {
    
    private Account currentAccount;

    private JLabel codeLabel, nameLabel;
    private JTextField codeField, nameField;
    private JButton saveButton, newButton, updateButton, cancelButton;
    
    private AccountFormActionListener accountFormActionListener;
    
    private boolean isInsert = false;

    public AccountFormPanel() {
        
        Dimension dimension = getPreferredSize();
        dimension.height = 150;
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        
        currentAccount = new Account();
        
        saveButton = new JButton("Save");
        newButton = new JButton("New Account");
        updateButton = new JButton("Update");
        cancelButton = new JButton("Cancel");
        
        codeLabel = new JLabel("Code");
        nameLabel = new JLabel("Name");
        
        codeField = new JTextField(15);
        nameField = new JTextField(15);
        
        saveButton.addActionListener(this);
        newButton.addActionListener(this);
        updateButton.addActionListener(this);
        cancelButton.addActionListener(this);
        
        initMode();
        
        add(codeLabel);
        add(codeField);
        
        add(nameLabel);
        add(nameField);
        
        add(newButton);
        add(updateButton);
        add(saveButton);
        add(cancelButton);
        
    }
    
    public void setAccountForm(Account account) {
        this.currentAccount = account;
        populateFields();
        accountFormUpdated();
    }

    private void accountFormUpdated() {
        updateButton.setEnabled(true);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
        newButton.setEnabled(true);
    }

    private void populateFields() {
        codeField.setText(currentAccount.getCode());
        nameField.setText(currentAccount.getName());
    }
    
    private void unpopulateFields() {
        codeField.setText(null);
        nameField.setText(null);
    }
    
    private void updateMode() {
        newButton.setEnabled(false);
        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);
        updateButton.setEnabled(false);
        isInsert = false;
    }
    
    private void insertMode() {
        currentAccount = new Account();
        saveButton.setEnabled(true);
        newButton.setEnabled(false);
        cancelButton.setEnabled(true);
        unpopulateFields();
        isInsert = true;
    }
    
    private void initMode() {
        cancelButton.setEnabled(false);
        saveButton.setEnabled(false);
        updateButton.setEnabled(false);
        newButton.setEnabled(true);
        isInsert = false;
    }
    
    public void setAccountFormActionListener(AccountFormActionListener listener) {
        this.accountFormActionListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        
        if (clicked == saveButton) {
//            System.out.println("SAVE");
            getValues();
            if (accountFormActionListener != null) {
                if (isInsert) {
                    accountFormActionListener.saveAccountEventOccurred(currentAccount);
                } else {
                    accountFormActionListener.updateAccountEventOccurred(currentAccount);
                }
            }
            initMode();
            unpopulateFields();
        } else if (clicked == newButton) {
//            System.out.println("NEW ACCOUNT");
            insertMode();
        } else if (clicked == updateButton) {
//            System.out.println("UPDATE");
            updateMode();
        } else if (clicked == cancelButton) {
            initMode();
            unpopulateFields();
//            System.out.println("CANCEL");
        }
    }

    private void getValues() {
        currentAccount.setCode(codeField.getText().trim());
        currentAccount.setName(nameField.getText().trim());
    }
}
