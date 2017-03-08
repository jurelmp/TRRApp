/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import com.jp.model.Account;
import com.jp.model.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jp.model.Contracts.AccountEntry;
import com.jp.utils.Utils;

/**
 *
 * @author JurelP
 */
public class AccountDAOImpl implements AccountDAO{
    
    private Database db;
    private Connection conn;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    public AccountDAOImpl() {
        db = Database.getInstance();
        db.connect();
        conn = db.getConnection();
        System.out.println(this.getClass().getName() + " : " + conn);
    }

    @Override
    public Account getAccountById(int id) {
        Account account = new Account();
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM " + 
                    AccountEntry.TABLE_NAME + " WHERE " + AccountEntry.COL_ID + " = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
            if (resultSet.next()) {
                account.setId(resultSet.getInt(AccountEntry.COL_ID));
                account.setCode(resultSet.getString(AccountEntry.COL_CODE));
                account.setName(resultSet.getString(AccountEntry.COL_NAME));
                account.setDateCreated(resultSet.getDate(AccountEntry.COL_DATE_CREATED));
                account.setDateUpdated(resultSet.getDate(AccountEntry.COL_DATE_UPDATED));
//                System.out.println(resultSet.getString(Contracts.AccountEntry.COL_CODE));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return account;
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + AccountEntry.TABLE_NAME);
            while (resultSet.next()) {                
                Account acc = new Account();
                acc.setId(resultSet.getInt(AccountEntry.COL_ID));
                acc.setCode(resultSet.getString(AccountEntry.COL_CODE));
                acc.setName(resultSet.getString(AccountEntry.COL_NAME));
                acc.setDateCreated(resultSet.getDate(AccountEntry.COL_DATE_CREATED));
                acc.setDateUpdated(resultSet.getDate(AccountEntry.COL_DATE_UPDATED));
                accounts.add(acc);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return accounts;
    }

    @Override
    public Account insertAccount(Account account) {
        account.setDateCreated(Utils.getDateNow());
        account.setDateUpdated(Utils.getDateNow());
        
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO " + AccountEntry.TABLE_NAME +
                    " (" + AccountEntry.COL_CODE + ", " +
                    AccountEntry.COL_NAME + ", " +
                    AccountEntry.COL_DATE_CREATED + ", " +
                    AccountEntry.COL_DATE_UPDATED + ") VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, account.getCode());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setDate(3, Utils.formatSqlDate(account.getDateCreated()));
            preparedStatement.setDate(4, Utils.formatSqlDate(account.getDateUpdated()));
            
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                account = getAccountByCode(account.getCode());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }

    @Override
    public boolean removeAccount(int id) {
        return false;
    }

    @Override
    public boolean updateAccount(Account account) {
        int result = 0;
        account.setDateUpdated(Utils.getDateNow());
        try { 
            preparedStatement = conn.prepareStatement("UPDATE " + AccountEntry.TABLE_NAME +
                    " SET " + AccountEntry.COL_CODE + " = ?, " +
                    AccountEntry.COL_NAME + " = ?, " +
                    AccountEntry.COL_DATE_UPDATED + " = ? WHERE " + 
                    AccountEntry.COL_ID + " = ?");
            preparedStatement.setString(1, account.getCode());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setDate(3, Utils.formatSqlDate(account.getDateUpdated()));
            preparedStatement.setInt(4, account.getId());
            
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result > 0;
    }
    
    @Override
    public Account getAccountByCode(String code) {
        Account account = new Account();
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM " + 
                    AccountEntry.TABLE_NAME + " WHERE " + AccountEntry.COL_CODE + " = ?");
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                account.setId(resultSet.getInt(AccountEntry.COL_ID));
                account.setCode(resultSet.getString(AccountEntry.COL_CODE));
                account.setName(resultSet.getString(AccountEntry.COL_NAME));
                account.setDateCreated(resultSet.getDate(AccountEntry.COL_DATE_CREATED));
                account.setDateUpdated(resultSet.getDate(AccountEntry.COL_DATE_UPDATED));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return account;
    }
    
}
