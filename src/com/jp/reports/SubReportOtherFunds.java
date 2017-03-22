/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.reports;

import com.jp.model.AccountSummary;
import com.jp.model.Other;
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
public class SubReportOtherFunds {
    private String title;
    private List<TextColumnBuilder<?>> columns = new ArrayList<>();
    private List<Other> othersFunds;

    public SubReportOtherFunds(List<Other> othersFunds) {
        this.othersFunds = othersFunds;
        this.title = "Others";
        TextColumnBuilder<String> columnOther = col.column("Other", "other", type.stringType()).setStyle(Utils.normalTextBuilder());
        TextColumnBuilder<BigDecimal> columnAmount = col.column("Amount", "amount", type.bigDecimalType()).setStyle(Utils.normalTextBuilder());
        columns.add(columnOther);
        columns.add(columnAmount);
    }

    public String getTitle() {
        return title;
    }

    public List<TextColumnBuilder<?>> getColumns() {
        return columns;
    }

    public JRDataSource getDataSource() {
        DRDataSource dataSource = new DRDataSource("other",
                "amount");

        for (int i = 0; i < othersFunds.size(); i++) {
            Other o = othersFunds.get(i);
            dataSource.add(
                    o.getDetails(),
                    o.getAmount()
            );
        }
        
        return dataSource;
    }
}
