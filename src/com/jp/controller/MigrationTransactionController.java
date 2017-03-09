/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.controller;

import com.jp.dao.MigrationTransactionDAOImpl;
import com.jp.model.Transaction;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author JurelP
 */
public class MigrationTransactionController extends MigrationController {
    
    public MigrationTransactionController() {
        super(new MigrationTransactionDAOImpl());
    }
    
    public void exportToFile(File file, List<Transaction> transactions) {
        LinkedList<Object> objects = new LinkedList<>(transactions);
        
        for (Transaction transaction : transactions) {
            objects.add((Object) transaction);
        }
        
        migrationDAO.exportToFile(file, objects);
    }
    
    public List<Transaction> importFromFile(File file) {
        List<Transaction> transactions = new ArrayList<>();
        LinkedList<Object> objects = migrationDAO.importFromFile(file);
        
        for (Object obj : objects) {
            transactions.add((Transaction) obj);
        }
        return transactions;
    }
}
