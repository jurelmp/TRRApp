/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.controller;

import com.jp.dao.AccountDAOImpl;
import com.jp.model.Account;
import java.util.List;

/**
 *
 * @author JurelP
 */
public class AccountController {
    
    private AccountDAOImpl accountDAOImpl;

    public AccountController() {
        accountDAOImpl = new AccountDAOImpl();
    }
    
    public Account getAccountById(int id) {
        return accountDAOImpl.getAccountById(id);
    }
    
    public Account getAccountByCode(String code) {
        return accountDAOImpl.getAccountByCode(code);
    }
    
    public List<Account> getAccounts() {
        return accountDAOImpl.getAccounts();
    }
    
    public Account insertAccount(Account account) {
        return accountDAOImpl.insertAccount(account);
    }
    
    public boolean updateAccount(Account account) {
        return accountDAOImpl.updateAccount(account);
    }
    
    public boolean removeAccount(int id) {
        return accountDAOImpl.removeAccount(id);
    }
    
    public List<Account> insertAll(List<Account> accounts) {
        return accountDAOImpl.insertAll(accounts);
    }
}
