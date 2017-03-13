/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.controller;

import com.jp.dao.TransactionDAOImpl;
import com.jp.model.Account;
import com.jp.model.Transaction;
import java.util.Date;
import java.util.List;

/**
 *
 * @author JurelP
 */
public class TransactionController {
    
    private TransactionDAOImpl transactionDAOImpl;

    public TransactionController() {
        transactionDAOImpl = new TransactionDAOImpl();
    }
    
    public List<Transaction> getTransactions() {
        return transactionDAOImpl.getTransactions();
    }
    
    public List<Transaction> getTransactions(Account account) {
        return transactionDAOImpl.getTransactionsByAccount(account);
    }
    
    public List<Transaction> getTransactions(Account account, Date date) {
        if (account == null && date == null) {
            return this.getTransactions();
        }
        if (account != null) {
            return this.getTransactions(account);
        }
        return transactionDAOImpl.getTransactionsByAccountAndDate(account, date);
    }
    
    public Transaction getTransactionById(int id) {
        return transactionDAOImpl.getTransactionById(id);
    }
    
    public Transaction insertTransaction(Transaction transaction) {
        int id = transactionDAOImpl.insertTransactionn(transaction);
        return getTransactionById(id);
    }
    
    public boolean updateTransaction(Transaction transaction) {
        return transactionDAOImpl.updateTransaction(transaction);
    }
    
    public boolean removeTransaction(Transaction transaction) {
        return transactionDAOImpl.removeTransaction(transaction);
    }

    public List<Transaction> insertAll(List<Transaction> transactions) {
        return transactionDAOImpl.insertAll(transactions);
    }
    
    public int clearTransaction(Transaction transaction) {
        return transactionDAOImpl.clearTransaction(transaction);
    }
    
}
