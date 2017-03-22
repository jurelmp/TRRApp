/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.reports;

import com.jp.utils.Utils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.BooleanColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.group.GroupBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.BooleanComponentType;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author JurelP
 */
public class OutputReports {
    
    private JRDataSource dataSource;
    private Date fromDate;
    private Date toDate;

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
            FontBuilder normalFont = stl.font("Times New Roman", false, false, 8);
            StyleBuilder normalText = stl.style(normalFont);
            FontBuilder hiddenFont = stl.font("Times New Roman", false, false, 0);
            StyleBuilder topBorder = stl.style().setTopBorder(stl.pen1Point());
            StyleBuilder noBottomPadding = stl.style().setBottomPadding(0);
            
            StyleBuilder hiddenStyle = stl.style(hiddenFont);
            StyleBuilder boldStyle = stl.style().bold();
            StyleBuilder boldCenteredStyle = stl.style(boldStyle)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
            StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
                    .setBorder(stl.pen1Point())
                    /**.setBackgroundColor(Color.LIGHT_GRAY)*/;
            
            TextColumnBuilder<String> dateBankCode = col.column("Account", new SimpleTextExpression());
            
            TextColumnBuilder<String> accountName = col.column("Account", "name", type.stringType()).setStyle(boldStyle).setStyle(noBottomPadding).setStyle(hiddenStyle);
            TextColumnBuilder<String> transactionNo = col.column("#", "id", type.stringType()).setStyle(noBottomPadding);
            TextColumnBuilder<String> referenceNo = col.column("Ref", "reference_no", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setWidth(70).setStyle(noBottomPadding);
            TextColumnBuilder<Date> date = col.column("Date", "date", type.dateType()).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setStyle(boldStyle).setStyle(noBottomPadding).setStyle(hiddenStyle);
            TextColumnBuilder<String> payeeName = col.column("To/From", "payee", type.stringType()).setWidth(200).setStyle(noBottomPadding);
            TextColumnBuilder<BigDecimal> depositAmount = col.column("Deposit", "deposit", type.bigDecimalType()).setStyle(noBottomPadding);
            TextColumnBuilder<BigDecimal> paymentAmount = col.column("Payment", "payment", type.bigDecimalType()).setStyle(noBottomPadding);
//            TextColumnBuilder<String> description = col.column("Description", "description", type.stringType());
//            TextColumnBuilder<Boolean> isClear = col.column("Clr", "is_clear", type.booleanType());
            BooleanColumnBuilder isClear = col.booleanColumn("Clr", "is_clear").setComponentType(BooleanComponentType.IMAGE_CHECKBOX_1).setWidth(20).setStyle(noBottomPadding);
            
            
            report()
//                    .setTemplate(Templates.reportTemplate)
                    .addPageHeader(cmp.text("Prepared on: " + Utils.humanDate(fromDate) + "\t\t\tFor Next Banking Day: " + Utils.humanDate(toDate)))
                    .setColumnTitleStyle(columnTitleStyle)
                    .setDefaultFont(normalFont)
                    .setSubtotalStyle(boldStyle)
                    .columns(
                            accountName,
                            referenceNo,
                            date,
                            dateBankCode,
                            payeeName,
                            depositAmount,
                            paymentAmount,
                            isClear)
                    
                    .groupBy(date, accountName, dateBankCode.setStyle(topBorder))
                    .subtotalsAtFirstGroupFooter(sbt.text("Date Subtotal", payeeName).setStyle(topBorder), 
                            sbt.sum(depositAmount).setStyle(topBorder), 
                            sbt.sum(paymentAmount).setStyle(topBorder))
                    .subtotalsAtSummary(sbt.text("Grand Total", payeeName).setStyle(topBorder), 
                            sbt.sum(depositAmount).setStyle(topBorder), 
                            sbt.sum(paymentAmount).setStyle(topBorder))
                    .setDataSource(this.dataSource)
//                    .title(cmp.text("Prepared on: " + Utils.humanDate(fromDate) + "\t\t\tFor Next Banking Day: " + Utils.humanDate(toDate)))
                    .pageFooter(cmp.pageXofY())
                    .show(false);
        } catch (DRException ex) {
            Logger.getLogger(OutputReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setRangeDate(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    private class SimpleTextExpression extends AbstractSimpleExpression<String> {

        private static final long serialVersionUID = 1L;

        @Override
        public String evaluate(ReportParameters reportParameters) {
            return Utils.humanDate(reportParameters.getValue("date")) + "  " + reportParameters.getValue("name");
        }
        
    }
}
