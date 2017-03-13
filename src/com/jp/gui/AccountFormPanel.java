/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Account;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.batik.ext.swing.GridBagConstants;

/**
 *
 * @author JurelP
 */
public class AccountFormPanel extends JPanel implements ActionListener {
    
    private Account currentAccount;
    
    private JPanel formPanel, buttonsPanel;

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
        setLayout(new BorderLayout());
        
        Font font = new Font(Font.SANS_SERIF, 0, 14);
        
        currentAccount = new Account();
        
        formPanel = new JPanel(new GridBagLayout());
        buttonsPanel = new JPanel(new GridLayout(0, 4));
//        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        
        saveButton = new JButton("Save");
        newButton = new JButton("New");
        updateButton = new JButton("Update");
        cancelButton = new JButton("Cancel");
        
        codeLabel = new JLabel("Code");
        nameLabel = new JLabel("Name");
        
        codeField = new JTextField(20);
        nameField = new JTextField(20);
        codeField.setFont(font);
        nameField.setFont(font);
        
        saveButton.addActionListener(this);
        newButton.addActionListener(this);
        updateButton.addActionListener(this);
        cancelButton.addActionListener(this);
        
        initMode();
        
        layoutForm();
        
        buttonsPanel.add(newButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.PAGE_END);
        
    }
    
    private void layoutForm() {
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(codeLabel, gbc);
        gbc.gridy++;
        formPanel.add(nameLabel, gbc);
        
        gbc.gridx++;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(codeField, gbc);
        gbc.gridy++;
        formPanel.add(nameField, gbc);
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
