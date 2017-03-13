/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import com.jp.model.Contracts;
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
    
    protected Database db;
    protected Connection conn;
    protected PreparedStatement preparedStatement;
    protected Statement statement;
    protected ResultSet resultSet;

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
                " ORDER BY " + TransactionEntry.TABLE_NAME + "." + TransactionEntry.COL_DATE + " ASC";
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
    
    public ResultSet getReportsByDateRange(Date from, Date to) {
        String sql = "SELECT " + Contracts.AccountEntry.TABLE_NAME + "." + Contracts.AccountEntry.COL_NAME + ", " + Contracts.TransactionEntry.TABLE_NAME + ".*" +
                " FROM " + Contracts.AccountEntry.TABLE_NAME + " JOIN " + Contracts.TransactionEntry.TABLE_NAME +
                " ON " + Contracts.AccountEntry.TABLE_NAME + "." + Contracts.AccountEntry.COL_ID + " = " + Contracts.TransactionEntry.TABLE_NAME + "." + Contracts.TransactionEntry.COL_ACCOUNT_ID +
                " WHERE " + Contracts.TransactionEntry.TABLE_NAME + "." + Contracts.TransactionEntry.COL_IS_CLEAR + " = ? AND " + Contracts.TransactionEntry.TABLE_NAME + "." + Contracts.TransactionEntry.COL_DATE + " BETWEEN ? AND ?" +
                " ORDER BY " + Contracts.TransactionEntry.TABLE_NAME + "." + Contracts.TransactionEntry.COL_DATE + " ASC";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setDate(2, Utils.formatSqlDate(from));
            preparedStatement.setDate(3, Utils.formatSqlDate(to));
            
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ReportDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
}
