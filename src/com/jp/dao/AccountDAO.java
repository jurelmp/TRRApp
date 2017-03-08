/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import com.jp.model.Account;
import java.util.List;

/**
 *
 * @author JurelP
 */
interface AccountDAO {
    
    public Account getAccountById(int id);
    public Account getAccountByCode(String code);
    public List<Account> getAccounts();
    public Account insertAccount(Account account);
    public boolean removeAccount(int id);
    public boolean updateAccount(Account account);
}
