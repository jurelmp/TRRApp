/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JurelP
 */
public class Database {
    
    private Connection conn;
    
    private Database() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println(this.getClass().getName() + " / " +e.getMessage());
        }
    }
    
    public static Database getInstance() {
        return DatabaseHolder.INSTANCE;
    }
    
    private static class DatabaseHolder {

        private static final Database INSTANCE = new Database();
    }
    
    public Connection getConnection() {
        return conn;
    }
    
    public PreparedStatement createPreparedStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }
    
    public Statement createStatement() throws SQLException {
        return conn.createStatement();
    }
    
    public void connect() {
        if (conn != null) return;
        
        String connUrl = "jdbc:derby:database/petrolog;create=true";
        
        try {
            conn = DriverManager.getConnection(connUrl);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This will create an Exception after shutting down the database engine.
     */
    public void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            // Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
    }
    
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cant close the connection.");
            }
        }
    }
    
    public void test() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts");
            while (resultSet.next()) {                
                System.out.println(
                resultSet.getInt(Contracts.AccountEntry.COL_ID) +
                resultSet.getString(Contracts.AccountEntry.COL_CODE) + 
                resultSet.getString(Contracts.AccountEntry.COL_NAME) + 
                resultSet.getString(Contracts.AccountEntry.COL_DATE_CREATED) + 
                resultSet.getString(Contracts.AccountEntry.COL_DATE_UPDATED));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
