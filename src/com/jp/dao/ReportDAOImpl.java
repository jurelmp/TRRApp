/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import com.jp.model.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jp.model.Contracts.AccountEntry;
import com.jp.model.Contracts.TransactionEntry;
import com.jp.utils.Utils;

/**
 *
 * @author JurelP
 */
public class ReportDAOImpl implements ReportDAO {
    
    private Database db;
    private Connection conn;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    public ReportDAOImpl() {
        db = Database.getInstance();
        db.connect();
        conn = db.getConnection();
        System.out.println(this.getClass().getName() + " : " + conn);
    }

    @Override
    public ResultSet getReportsByDate(Date date) {
        String sql = "SELECT " + AccountEntry.TABLE_NAME + "." + AccountEntry.COL_NAME + ", " + TransactionEntry.TABLE_NAME + ".*" +
                " FROM " + AccountEntry.TABLE_NAME + " JOIN " + TransactionEntry.TABLE_NAME +
                " ON " + AccountEntry.TABLE_NAME + "." + AccountEntry.COL_ID + " = " + TransactionEntry.TABLE_NAME + "." + TransactionEntry.COL_ACCOUNT_ID +
                " WHERE " + TransactionEntry.TABLE_NAME + "." + TransactionEntry.COL_DATE + " = ? AND " + TransactionEntry.TABLE_NAME + "." + TransactionEntry.COL_IS_CLEAR + " = ?" +
                " ORDER BY " + AccountEntry.TABLE_NAME + "." + AccountEntry.COL_NAME + " ASC";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate(1, Utils.formatSqlDate(date));
            preparedStatement.setBoolean(2, false);
            
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ReportDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
}
