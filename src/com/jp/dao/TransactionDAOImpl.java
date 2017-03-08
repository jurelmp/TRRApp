/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import com.jp.model.Account;
import com.jp.model.Database;
import com.jp.model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.jp.model.Contracts.TransactionEntry;
import com.jp.model.TransactionType;
import com.jp.utils.Utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JurelP
 */
public class TransactionDAOImpl implements TransactionDAO{
    
    private Database db;
    private Connection conn;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    public TransactionDAOImpl() {
        db = Database.getInstance();
        db.connect();
        conn = db.getConnection();
        System.out.println(this.getClass().getName() + " : " + conn);
    }

    @Override
    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            statement = conn.createStatement();
            String query = "SELECT * FROM " + TransactionEntry.TABLE_NAME + " ORDER BY " + TransactionEntry.COL_DATE;
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {                
                Transaction transaction = extractData();
                transactions.add(transaction);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByAccount(Account account) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM " + TransactionEntry.TABLE_NAME +
                    " WHERE " + TransactionEntry.COL_ACCOUNT_ID + " = ?" +
                    " ORDER BY " + TransactionEntry.COL_DATE);
            preparedStatement.setInt(1, account.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactions.add(extractData());
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transactions;
    }
    
    @Override
    public List<Transaction> getTransactionsByAccountAndDate(Account account, Date date) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM " + TransactionEntry.TABLE_NAME +
                    " WHERE " + TransactionEntry.COL_ACCOUNT_ID + " = ?" +
                    " ORDER BY " + TransactionEntry.COL_DATE);
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setString(2, Utils.formatDate(date));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactions.add(extractData());
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transactions;
    }

    @Override
    public Transaction getTransactionById(int id) {
        Transaction t = new Transaction();
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM " + TransactionEntry.TABLE_NAME +
                    " WHERE " + TransactionEntry.COL_ID + " = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {                
                t = extractData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

    @Override
    public int insertTransactionn(Transaction transaction) {
        int id = -1;
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO " +
                    TransactionEntry.TABLE_NAME + " (" + 
                    TransactionEntry.COL_ACCOUNT_ID + ", " +
                    TransactionEntry.COL_REF_NO + ", " + 
                    TransactionEntry.COL_DATE + ", " + 
                    TransactionEntry.COL_PAYEE + ", " + 
                    TransactionEntry.COL_AMOUNT + ", " + 
                    TransactionEntry.COL_DESCRIPTION + "," + 
                    TransactionEntry.COL_TYPE + "," + 
                    TransactionEntry.COL_IS_CLEAR + "," + 
                    TransactionEntry.COL_DATE_CREATED + "," + 
                    TransactionEntry.COL_DATE_UPDATED + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            int col = 1;
            preparedStatement.setInt(col++, transaction.getAccountId());
            preparedStatement.setString(col++, transaction.getReference());
            preparedStatement.setDate(col++, Utils.formatSqlDate(transaction.getDate()));
            preparedStatement.setString(col++, transaction.getPayee());
            preparedStatement.setDouble(col++, transaction.getAmount());
            preparedStatement.setString(col++, transaction.getDesc());
            preparedStatement.setString(col++, transaction.getType().name());
            preparedStatement.setBoolean(col++, transaction.isClear());
            preparedStatement.setDate(col++, Utils.formatSqlDate(Utils.getDateNow()));
            preparedStatement.setDate(col, Utils.formatSqlDate(Utils.getDateNow()));
            
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            
            if (rs.next())
                id = rs.getInt(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        boolean flag = false;
        
        if (transaction == null)
            return flag;
        
        try {
            preparedStatement = conn.prepareStatement("UPDATE " + TransactionEntry.TABLE_NAME +
                    " SET " + TransactionEntry.COL_REF_NO + " = ?, " +
                    TransactionEntry.COL_DATE + " = ?, " +
                    TransactionEntry.COL_PAYEE + " = ?, " +
                    TransactionEntry.COL_AMOUNT + " = ?, " +
                    TransactionEntry.COL_DESCRIPTION + " = ?, " +
                    TransactionEntry.COL_TYPE + " = ?, " +
                    TransactionEntry.COL_IS_CLEAR + " = ?, " + 
                    TransactionEntry.COL_DATE_UPDATED + " = ? WHERE " + TransactionEntry.COL_ID + " = ?");
            
            int col = 1;
            preparedStatement.setString(col++, transaction.getReference());
            preparedStatement.setDate(col++, Utils.formatSqlDate(transaction.getDate()));
            preparedStatement.setString(col++, transaction.getPayee());
            preparedStatement.setDouble(col++, transaction.getAmount());
            preparedStatement.setString(col++, transaction.getDesc());
            preparedStatement.setString(col++, transaction.getType().name());
            preparedStatement.setBoolean(col++, transaction.isClear());
            preparedStatement.setDate(col++, Utils.formatSqlDate(Utils.getDateNow()));
            preparedStatement.setInt(col, transaction.getId());
            
            flag = preparedStatement.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    @Override
    public boolean removeTransaction(Transaction transaction) {
        boolean flag = false;
        
        if (transaction == null)
            return flag;
        
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM " + TransactionEntry.TABLE_NAME + 
                    " WHERE " + TransactionEntry.COL_ID + " = ?");
            preparedStatement.setInt(1, transaction.getId());
            
            flag = preparedStatement.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag;
    }
    
    private Transaction extractData() throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getInt(TransactionEntry.COL_ID));
        transaction.setReference(resultSet.getString(TransactionEntry.COL_REF_NO));
        transaction.setDate(resultSet.getDate(TransactionEntry.COL_DATE));
        transaction.setPayee(resultSet.getString(TransactionEntry.COL_PAYEE));
        transaction.setAmount(resultSet.getDouble(TransactionEntry.COL_AMOUNT));
        transaction.setDesc(resultSet.getString(TransactionEntry.COL_DESCRIPTION));
        transaction.setType(TransactionType.valueOf(resultSet.getString(TransactionEntry.COL_TYPE)));
        transaction.setClear(resultSet.getBoolean(TransactionEntry.COL_IS_CLEAR));
        transaction.setDateCreated(resultSet.getDate(TransactionEntry.COL_DATE_CREATED));
        transaction.setDateUpdated(resultSet.getDate(TransactionEntry.COL_DATE_UPDATED));
        return transaction;
    }
}
