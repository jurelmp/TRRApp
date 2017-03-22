/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.reports;

import com.jp.model.AccountSummary;
import com.jp.utils.Utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
public class SubReportAccounts {

    private String title = "Accounts";
    private List<TextColumnBuilder<?>> columns = new ArrayList<>();
    private List<AccountSummary> accountSummaries;
    private Date preparedDate, nextBankingDate;

    public SubReportAccounts(List<AccountSummary> accountSummaries, Date preparedDate, Date nextBankingDate) {
        this.preparedDate = preparedDate;
        this.nextBankingDate = nextBankingDate;
        this.accountSummaries = accountSummaries;
        this.title = "Accounts Summary";
        TextColumnBuilder<String> columnCode = col.column("Code", "code", type.stringType()).setStyle(Utils.normalTextBuilder());
        TextColumnBuilder<BigDecimal> columnActual = col.column("Actual\n" + Utils.humanDate(preparedDate), "actual", type.bigDecimalType()).setStyle(Utils.normalTextBuilder());
        TextColumnBuilder<BigDecimal> columnPrelim = col.column("Preliminary\n" + Utils.humanDate(nextBankingDate), "preliminary", type.bigDecimalType()).setStyle(Utils.normalTextBuilder());
        columns.add(columnCode);
        columns.add(columnActual);
        columns.add(columnPrelim);
    }

    public String getTitle() {
        return title;
    }

    public List<TextColumnBuilder<?>> getColumns() {
        return columns;
    }

    public JRDataSource getDataSource() {
        DRDataSource dataSource = new DRDataSource("code",
                "actual",
                "preliminary");

        for (int i = 0; i < accountSummaries.size(); i++) {
            AccountSummary as = accountSummaries.get(i);
            dataSource.add(
                    as.getBankCode(),
                    as.getActual(),
                    as.getPreliminary()
            );
        }

        return dataSource;
    }

}
