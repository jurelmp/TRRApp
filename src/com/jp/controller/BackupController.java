/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.controller;

import com.jp.model.Database;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author JurelP
 */
public class BackupController {
    
    private Database db;
    private Connection conn;
    private CallableStatement callableStatement;

    public BackupController() {
        db = Database.getInstance();
        conn = db.getConnection();
        System.out.println(this.getClass().getName() + " : " + conn);
    }
    
    public void restoreBackup() {
        
    }
    
//    public void createBackup(String path) {
//        
//        try {
//            String paramName = "BACKUPDIR";
//            String storedProc = "CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)";
//            callableStatement = conn.prepareCall(storedProc);
//            callableStatement.setString(1, path);
//            System.out.println(callableStatement.execute());
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public void createBackup(String path) {
        try {
            String source = "/database/petrolog";
            
            URL url = this.getClass().getResource(source);
            System.out.println(url);
            File fileSource = new File(url.toURI());
            
            System.out.println(fileSource.toPath());
            
//        try {
//            File fileSource = new File(new URI(source));
//            File fileDest = new File(new URI(path));
//            FileUtils.copyDirectory(new File(source), new File(path));
//        } catch (IOException | URISyntaxException ex) {
//            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (URISyntaxException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
