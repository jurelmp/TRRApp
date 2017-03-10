/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Account;
import com.jp.model.Item;
import java.util.List;

/**
 *
 * @author JurelP
 */
public interface MigrationListener {
    
    void transactionsMigrated(List<Item> items);
    void accountsMigrated(List<Account> accounts);
    
}
