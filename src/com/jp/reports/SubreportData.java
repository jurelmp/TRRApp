/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.reports;

import com.jp.model.AccountSummary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author JurelP
 */
public class SubreportData {
    private String title;
    List<TextColumnBuilder<?>> columns;
    private JRDataSource data;

    
    public SubreportData() {
//        Map<String, Class> map = new HashMap<>();
//        map.put("code", String.class);
//        columns.add(map);
    }

    public SubreportData(String title, List<TextColumnBuilder<?>> columns, JRDataSource data) {
        this.title = title;
        this.columns = columns;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TextColumnBuilder<?>> getColumns() {
        return columns;
    }

    public void setColumns(List<TextColumnBuilder<?>> columns) {
        this.columns = columns;
    }

    public JRDataSource getData() {
        return data;
    }

    public void setData(JRDataSource data) {
        this.data = data;
    }

   
}
