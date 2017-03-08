/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.controller;

import com.jp.dao.ReportDAOImpl;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author JurelP
 */
public class ReportController {
    
    private ReportDAOImpl reportDAOImpl;

    public ReportController() {
        reportDAOImpl = new ReportDAOImpl();
    }
    
    public ResultSet getReportsByDate(Date date) {
        return reportDAOImpl.getReportsByDate(date);
    }
}
