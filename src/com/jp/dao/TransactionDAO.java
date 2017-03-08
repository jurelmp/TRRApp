/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import com.jp.model.Account;
import com.jp.model.Transaction;
import java.util.Date;
import java.util.List;

/**
 *
 * @author JurelP
 */
interface TransactionDAO {
    
    public List<Transaction> getTransactions();
    public List<Transaction> getTransactionsByAccount(Account account);
    public List<Transaction> getTransactionsByAccountAndDate(Account account, Date date);
    public Transaction getTransactionById(int id);
    public int insertTransactionn(Transaction transaction);
    public boolean updateTransaction(Transaction transaction);
    public boolean removeTransaction(Transaction transaction);
}
