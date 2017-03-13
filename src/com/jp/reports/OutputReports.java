/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.reports;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.dynamicreports.examples.Templates;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.BooleanColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.grid.ColumnTitleGroupBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.group.GroupBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.BooleanComponentType;
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
            FontBuilder normalFont = stl.font("Arial", false, false, 8);
            StyleBuilder topBorder = stl.style().setTopBorder(stl.pen1Point());
            StyleBuilder normalText = stl.style(normalFont);
            StyleBuilder boldStyle = stl.style().bold();
            StyleBuilder boldCenteredStyle = stl.style(boldStyle)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
            StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
                    .setBorder(stl.pen1Point())
                    /**.setBackgroundColor(Color.LIGHT_GRAY)*/;
            
            TextColumnBuilder<String> accountName = col.column("Account", "name", type.stringType());
            TextColumnBuilder<String> transactionNo = col.column("#", "id", type.stringType());
            TextColumnBuilder<String> referenceNo = col.column("Ref", "reference_no", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
            TextColumnBuilder<Date> date = col.column("Date", "date", type.dateType()).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
            TextColumnBuilder<String> payeeName = col.column("Payee", "payee", type.stringType());
            TextColumnBuilder<BigDecimal> depositAmount = col.column("Deposit", "deposit", type.bigDecimalType());
            TextColumnBuilder<BigDecimal> paymentAmount = col.column("Payment", "payment", type.bigDecimalType());
//            TextColumnBuilder<String> description = col.column("Description", "description", type.stringType());
//            TextColumnBuilder<Boolean> isClear = col.column("Clr", "is_clear", type.booleanType());
            BooleanColumnBuilder isClear = col.booleanColumn("Clr", "is_clear").setComponentType(BooleanComponentType.IMAGE_CHECKBOX_1);
            ColumnGroupBuilder columnGroup = grp.group(transactionNo);
            
            
            report()
//                    .setTemplate(Templates.reportTemplate)
                    .setColumnTitleStyle(columnTitleStyle)
                    .setDefaultFont(normalFont)
                    .setSubtotalStyle(boldStyle)
                    .columns(
                            accountName,
                            transactionNo,
                            referenceNo,
                            date,
                            payeeName,
                            depositAmount,
                            paymentAmount,
                            isClear)
                    .groupBy(date, accountName)
                    .subtotalsAtFirstGroupFooter(sbt.text("Date Subtotal", payeeName).setStyle(topBorder), 
                            sbt.sum(depositAmount).setStyle(topBorder), 
                            sbt.sum(paymentAmount).setStyle(topBorder))
                    .subtotalsAtSummary(sbt.text("Grand Total", payeeName).setStyle(topBorder), 
                            sbt.sum(depositAmount).setStyle(topBorder), 
                            sbt.sum(paymentAmount).setStyle(topBorder))
                    .setDataSource(this.dataSource)
                    .title(cmp.text("Petrologistics Report").setStyle(boldCenteredStyle))
                    .pageFooter(cmp.pageXofY())
                    .show(false);
        } catch (DRException ex) {
            Logger.getLogger(OutputReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
