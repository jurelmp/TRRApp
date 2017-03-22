/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.controller;

import com.jp.dao.ReportDAO;
import com.jp.dao.ReportDAOImpl;
import com.jp.dao.ReportExtDAOImpl;
import com.jp.model.AccountSummary;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

/**
 *
 * @author JurelP
 */
public class ReportController {
    
    private ReportDAOImpl reportDAOImpl;

    public ReportController() {
        reportDAOImpl = new ReportDAOImpl();
    }
    
    /**
     * Retrieve the transactions by a specific Date.
     * @param date
     * @return a ResultSet of Transaction
     */
    public ResultSet getReportsByDate(Date date) {
        return reportDAOImpl.getReportsByDate(date);
    }
    
    /**
     * Retrieve the transactions from a range of Date.
     * @param from
     * @param to
     * @return a ResultSet of Transaction
     */
    public ResultSet getReportsByDateRange(Date from, Date to) {
        return reportDAOImpl.getReportsByDateRange(from, to);
    }
    
    public List<AccountSummary> getAccountsSummary(Date preparedDate, Date nextBankingDate) {
        return reportDAOImpl.getAccountsSummary(preparedDate, nextBankingDate);
    }
}
