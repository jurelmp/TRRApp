/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import com.jp.model.Contracts;
import com.jp.utils.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JurelP
 */
public class ReportExtDAOImpl extends ReportDAOImpl {

    public ReportExtDAOImpl() {
        super();
    }
    
    public ResultSet getReportsByDateRange(Date from, Date to) {
        String sql = "SELECT " + Contracts.AccountEntry.TABLE_NAME + "." + Contracts.AccountEntry.COL_NAME + ", " + Contracts.TransactionEntry.TABLE_NAME + ".*" +
                " FROM " + Contracts.AccountEntry.TABLE_NAME + " JOIN " + Contracts.TransactionEntry.TABLE_NAME +
                " ON " + Contracts.AccountEntry.TABLE_NAME + "." + Contracts.AccountEntry.COL_ID + " = " + Contracts.TransactionEntry.TABLE_NAME + "." + Contracts.TransactionEntry.COL_ACCOUNT_ID +
                " WHERE " + Contracts.TransactionEntry.TABLE_NAME + "." + Contracts.TransactionEntry.COL_IS_CLEAR + " = ? AND " + Contracts.TransactionEntry.TABLE_NAME + "." + Contracts.TransactionEntry.COL_DATE + " BETWEEN ? AND ?" +
                " ORDER BY " + Contracts.AccountEntry.TABLE_NAME + "." + Contracts.AccountEntry.COL_NAME + " ASC";
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
