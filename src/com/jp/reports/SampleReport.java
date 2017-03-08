/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.reports;

import java.awt.Color;
import java.math.BigDecimal;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author JurelP
 */
public class SampleReport {
    
    public void build() {
            
        /** try {
            report()//create new report design
                    .columns(...) //adds columns
                    .groupBy(...) //adds groups
                    .subtotalsAtSummary(...) //adds subtotals
                    ...
                    //set datasource
                    .setDataSource(...)
                    //export report
                    .toPdf(...) //export report to pdf
                    .toXls(...) //export report to excel
                    ...
                    //other outputs
                    .toJasperPrint() //creates jasperprint object
                    .show() //shows report
                    .print() //prints report
                    ...
        } catch (DRException e) {
            e.printStackTrace();
        } */
    }
    
    private static JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice");
        dataSource.add("Notebook", 1, new BigDecimal(500));
        dataSource.add("DVD", 5, new BigDecimal(500));
        dataSource.add("DVD", 1, new BigDecimal(30));
        dataSource.add("DVD", 5, new BigDecimal(28));
        dataSource.add("Book", 3, new BigDecimal(32));
        dataSource.add("Book", 1, new BigDecimal(11));
        dataSource.add("Book", 5, new BigDecimal(15));
        dataSource.add("Book", 3, new BigDecimal(9));
        return dataSource;
    }
    
    public static void simpleReport() {
        try {
            StyleBuilder boldStyle = stl.style().bold();
            StyleBuilder boldCenteredStyle = stl.style(boldStyle)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
            StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
                    .setBorder(stl.pen1Point())
                    .setBackgroundColor(Color.LIGHT_GRAY);
            
            report()
                    .setColumnTitleStyle(columnTitleStyle)
                    .highlightDetailEvenRows()
                    .columns(
                            col.column("Item", "item", type.stringType()),
                            col.column("Quantity", "quantity", type.integerType()),
                            col.column("Unit Price", "unitprice", type.bigDecimalType()))
                    .setDataSource(createDataSource())
                    .title(cmp.text("Getting Started").setStyle(boldCenteredStyle))
                    .pageFooter(cmp.pageXofY())
                    .show();
        } catch (DRException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        simpleReport();
    }
}
