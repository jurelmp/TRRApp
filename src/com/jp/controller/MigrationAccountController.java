/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.controller;

import com.jp.dao.MigrationAccountDAOImpl;
import com.jp.model.Account;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author JurelP
 */
public class MigrationAccountController extends MigrationController {
    
    public MigrationAccountController() {
        super(new MigrationAccountDAOImpl());
    }
    
    public void exportToFile(File file, List<Account> accounts) {
        LinkedList<Object> objects = new LinkedList<>(accounts);
        
        for (Account account : accounts) {
            objects.add((Object) account);
        }
        
        migrationDAO.exportToFile(file, objects);
    }
    
    public List<Account> importFromFile(File file) {
        List<Account> accounts = new ArrayList<>();
        LinkedList<Object> objects = migrationDAO.importFromFile(file);
        
        for (Object obj : objects) {
            accounts.add((Account) obj);
        }
        return accounts;
    }
}
