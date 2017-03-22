/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.controller;

import com.jp.model.Database;
import com.jp.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
    
    public boolean createBackup() {
        boolean flag = false;
        try {
            //        File file = new File("");
            //        String sourcePath = file.getAbsolutePath();
            //        System.out.println(sourcePath);
            //        Date date = new Date();
            //        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy_HH-mm-SS");
            //        String backupDirectory = sourcePath + "/database/backups/" + dateFormat.format(date);
            //        
            //	String sql = "CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)";
            //	CallableStatement cs = conn.prepareCall(sql);
            //	cs.setString(1, backupDirectory);
            //	cs.execute();
            //	cs.close();

            File file = new File("");
            String sourcePath = file.getAbsolutePath();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy_HH-mm-SS");
            String backupVer = dateFormat.format(Utils.getDateNow());
            String backupDirectory = sourcePath + "/database/backups/" + backupVer;

            String sql = "CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)";
            CallableStatement callableStatement = conn.prepareCall(sql);
            callableStatement.setString(1, backupDirectory);
            
            boolean result = !callableStatement.execute();
            
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    
}
