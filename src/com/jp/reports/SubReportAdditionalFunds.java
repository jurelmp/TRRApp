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
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
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
    
    public JasperReportBuilder getReport() {
        JasperReportBuilder report = report();
        
        TextColumnBuilder<String> columnDetails = col.column("", "details", type.stringType()).setStyle(Utils.normalTextBuilder());
        TextColumnBuilder<Double> columnAmount = col.column("Amount", "amount", type.doubleType()).setValueFormatter(new Utils.DecimalValueFormatter()).setStyle(Utils.normalTextBuilder());
           
        report.setColumnTitleStyle(Utils.boldTextBuilder().setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
//        report.title(cmp.text("Additional Funds Available for Deposit"));
        report.addColumn(
                columnDetails,
                columnAmount
        );
        
        report.subtotalsAtSummary(sbt.text("", columnDetails).setStyle(Utils.boldTextBuilder()).setStyle(Utils.topBorderBuilder()),
                sbt.sum(columnAmount).setValueFormatter(new Utils.DecimalValueFormatter()).setStyle(Utils.boldTextBuilder()).setStyle(Utils.topBorderBuilder())
        );
        report.setDataSource(this.getDataSource());
        
        return report;
    }
}
