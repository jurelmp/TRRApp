/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import com.jp.model.AdditionalFund;
import com.jp.model.Database;
import com.jp.model.Other;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jp.model.Contracts.OtherEntry;
import com.jp.model.Contracts.OnDateFundEntry;
import com.jp.model.Contracts.AdditionalEntry;
import com.jp.model.OnDateFund;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JurelP
 */
public class BankManagerDAOImpl implements BankManagerDAO {
    
    private Database db;
    private Connection conn;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    public BankManagerDAOImpl() {
        db = Database.getInstance();
        db.connect();
        conn = db.getConnection();
        System.out.println(this.getClass().getName() + " : " + conn);
    }

    @Override
    public Other insertOtherFunds(Other other) {
        try {
            String sql = "INSERT INTO " + OtherEntry.TABLE_NAME + 
                    " (" + OtherEntry.COL_DETAILS + ", "
                    + OtherEntry.COL_AMOUNT + ") VALUES (?, ?)";
            preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, other.getDetails());
            preparedStatement.setBigDecimal(2, other.getAmount());
            
            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    other.setId(rs.getInt(1));
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return other;
    }
    
    @Override
    public List<Other> getOthersSummary() {
        List<Other> lists = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + OtherEntry.TABLE_NAME;
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Other o = new Other();
                o.setId(resultSet.getInt(OtherEntry.COL_ID));
                o.setDetails(resultSet.getString(OtherEntry.COL_DETAILS));
                o.setAmount(resultSet.getBigDecimal(OtherEntry.COL_AMOUNT));
                lists.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }

    @Override
    public void updateOtherFunds(Other selected) {
        try {
            String sql = "UPDATE " + OtherEntry.TABLE_NAME +
                    " SET " + OtherEntry.COL_DETAILS + " = ?, " +
                    OtherEntry.COL_AMOUNT + " = ? WHERE " + OtherEntry.COL_ID + " = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, selected.getDetails());
            preparedStatement.setBigDecimal(2, selected.getAmount());
            preparedStatement.setInt(3, selected.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteOtherFunds(Other row) {
        try {
            String sql = "DELETE FROM " + OtherEntry.TABLE_NAME + " WHERE " + OtherEntry.COL_ID + " = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, row.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public OnDateFund insertOnDateFund(OnDateFund onDateFund) {
        try {
            String sql = "INSERT INTO " + OnDateFundEntry.TABLE_NAME + 
                    " (" + OnDateFundEntry.COL_DETAILS + ", "
                    + OnDateFundEntry.COL_AMOUNT + ") VALUES (?, ?)";
            preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, onDateFund.getDetails());
            preparedStatement.setDouble(2, onDateFund.getAmount());
            
            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    onDateFund.setId(rs.getInt(1));
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return onDateFund;
    }

    @Override
    public List<OnDateFund> getOnDateFunds() {
        List<OnDateFund> lists = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + OnDateFundEntry.TABLE_NAME;
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                OnDateFund o = new OnDateFund();
                o.setId(resultSet.getInt(OnDateFundEntry.COL_ID));
                o.setDetails(resultSet.getString(OnDateFundEntry.COL_DETAILS));
                o.setAmount(resultSet.getDouble(OnDateFundEntry.COL_AMOUNT));
                lists.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }

    @Override
    public void updateOnDateFund(OnDateFund onDateFund) {
        try {
            String sql = "UPDATE " + OnDateFundEntry.TABLE_NAME +
                    " SET " + OnDateFundEntry.COL_DETAILS + " = ?, " +
                    OnDateFundEntry.COL_AMOUNT + " = ? WHERE " + OnDateFundEntry.COL_ID + " = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, onDateFund.getDetails());
            preparedStatement.setDouble(2, onDateFund.getAmount());
            preparedStatement.setInt(3, onDateFund.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteOnDateFund(OnDateFund onDateFund) {
        try {
            String sql = "DELETE FROM " + OnDateFundEntry.TABLE_NAME + " WHERE " + OnDateFundEntry.COL_ID + " = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, onDateFund.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public AdditionalFund insertAdditionalFund(AdditionalFund additionalFund) {
        try {
            String sql = "INSERT INTO " + AdditionalEntry.TABLE_NAME + 
                    " (" + AdditionalEntry.COL_DETAILS + ", "
                    + AdditionalEntry.COL_AMOUNT + ") VALUES (?, ?)";
            preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, additionalFund.getDetails());
            preparedStatement.setDouble(2, additionalFund.getAmount());
            
            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    additionalFund.setId(rs.getInt(1));
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return additionalFund;
    }

    @Override
    public List<AdditionalFund> getAdditionalFunds() {
        List<AdditionalFund> lists = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + AdditionalEntry.TABLE_NAME;
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                AdditionalFund o = new AdditionalFund();
                o.setId(resultSet.getInt(AdditionalEntry.COL_ID));
                o.setDetails(resultSet.getString(AdditionalEntry.COL_DETAILS));
                o.setAmount(resultSet.getDouble(AdditionalEntry.COL_AMOUNT));
                lists.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }

    @Override
    public void updateAdditionalFund(AdditionalFund additionalFund) {
        try {
            String sql = "UPDATE " + AdditionalEntry.TABLE_NAME +
                    " SET " + AdditionalEntry.COL_DETAILS + " = ?, " +
                    AdditionalEntry.COL_AMOUNT + " = ? WHERE " + AdditionalEntry.COL_ID + " = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, additionalFund.getDetails());
            preparedStatement.setDouble(2, additionalFund.getAmount());
            preparedStatement.setInt(3, additionalFund.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteAdditionalFund(AdditionalFund additionalFund) {
        try {
            String sql = "DELETE FROM " + AdditionalEntry.TABLE_NAME + " WHERE " + AdditionalEntry.COL_ID + " = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, additionalFund.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BankManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
