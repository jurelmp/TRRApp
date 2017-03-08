/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Transaction;

/**
 *
 * @author JurelP
 */
public interface TransactionFormActionListener {
    
    public static final int MODE_INSERT = 1;
    public static final int MODE_UPDATE = 2;
    
    void buttonSaveClicked(Transaction transaction, int mode);
    void buttonRemoveClicked(Transaction transaction, int rowIndex);
    
}
