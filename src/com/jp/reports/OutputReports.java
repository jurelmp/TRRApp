/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.reports;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author JurelP
 */
public class OutputReports {
    
    private JRDataSource dataSource;

    public OutputReports() {
    }

    public OutputReports(JRDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(JRDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void buildReport() {
        try {
            StyleBuilder boldStyle = stl.style().bold();
            StyleBuilder boldCenteredStyle = stl.style(boldStyle)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
            StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
                    .setBorder(stl.pen1Point())
                    .setBackgroundColor(Color.LIGHT_GRAY);
            
            TextColumnBuilder<String> accountName = col.column("Account", "name", type.stringType()).setStyle(boldStyle);
            TextColumnBuilder<String> transactionNo = col.column("#", "id", type.stringType());
            TextColumnBuilder<String> referenceNo = col.column("Ref", "reference_no", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
            TextColumnBuilder<Date> date = col.column("Date", "date", type.dateType());
            TextColumnBuilder<String> payeeName = col.column("Payee", "payee", type.stringType());
            TextColumnBuilder<BigDecimal> depositAmount = col.column("Deposit", "deposit", type.bigDecimalType());
            TextColumnBuilder<BigDecimal> paymentAmount = col.column("Payment", "payment", type.bigDecimalType());
            TextColumnBuilder<String> description = col.column("Description", "description", type.stringType());
            
            report()
                    .setColumnTitleStyle(columnTitleStyle)
                    .setSubtotalStyle(boldStyle)
                    .columns(
                            accountName,
                            transactionNo,
                            referenceNo,
                            date,
                            payeeName,
                            depositAmount,
                            paymentAmount,
                            description)
                    .groupBy(accountName)
                    .subtotalsAtSummary(sbt.sum(depositAmount), sbt.sum(paymentAmount))
                    .subtotalsAtFirstGroupFooter(sbt.sum(depositAmount), sbt.sum(paymentAmount))
                    .setDataSource(this.dataSource)
                    .title(cmp.text("Petrologistics Report").setStyle(boldCenteredStyle))
                    .pageFooter(cmp.pageXofY())
                    .show(false);
        } catch (DRException ex) {
            Logger.getLogger(OutputReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
