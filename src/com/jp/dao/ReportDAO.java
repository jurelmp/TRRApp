/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author JurelP
 */
public interface ReportDAO {
    
    public ResultSet getReportsByDate(Date date);
    
}
