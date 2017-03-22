/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.reports;

import com.jp.model.AdditionalFund;
import com.jp.model.OnDateFund;
import com.jp.utils.Utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author JurelP
 */
public class SubReportAdditionalFunds {
    private String title;
    private List<TextColumnBuilder<?>> columns = new ArrayList<>();
    private List<AdditionalFund> additionalFunds;

    public SubReportAdditionalFunds(List<AdditionalFund> additionalFunds) {
        this.additionalFunds = additionalFunds;
        this.title = "Additional";
        TextColumnBuilder<String> columnDetails = col.column("Details", "details", type.stringType()).setStyle(Utils.normalTextBuilder());
        TextColumnBuilder<Double> columnAmount = col.column("Amount", "amount", type.doubleType()).setStyle(Utils.normalTextBuilder());
        columns.add(columnDetails);
        columns.add(columnAmount);
    }

    public String getTitle() {
        return title;
    }

    public List<TextColumnBuilder<?>> getColumns() {
        return columns;
    }

    public JRDataSource getDataSource() {
        DRDataSource dataSource = new DRDataSource("details",
                "amount");

        for (int i = 0; i < additionalFunds.size(); i++) {
            AdditionalFund af = additionalFunds.get(i);
            dataSource.add(
                    af.getDetails(),
                    af.getAmount()
            );
        }
        
        return dataSource;
    }
}
