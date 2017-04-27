/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.utils;

import com.jp.gui.DecimalFormatTableCellRenderer;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.definition.ReportParameters;

/**
 *
 * @author JurelP
 */
public class Utils {

    public static ImageIcon createIcon(String path) {
        URL url = System.class.getResource(path);

        if (url == null) {
            System.err.println("Unable to load image: " + path);
        }

        return new ImageIcon(url);
    }

    public static Font createFont(String path) {
        URL url = System.class.getResource(path);

        if (url == null) {
            System.err.println("Unable to load font: " + path);
        }

        Font font = null;

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
        } catch (FontFormatException e) {
            System.out.println("Bad format in font file: " + path);
        } catch (IOException e) {
            System.out.println("Unable to read font file: " + path);
        }

        return font;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return dateFormat.format(date);
    }

    public static java.sql.Date formatSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Date getDateNow() {
        return new Date();
    }

    public static String humanDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        return dateFormat.format(date);
    }

    public static DefaultFormatterFactory createDecimalFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return new DefaultFormatterFactory(new NumberFormatter(decimalFormat));
    }

    public static StyleBuilder normalTextBuilder(int size) {
        FontBuilder normalFont = stl.font("Arial", false, false, size);
        StyleBuilder normalText = stl.style(normalFont);
        return normalText;
    }

    public static StyleBuilder normalTextBuilder() {
        return normalTextBuilder(8);
    }
    

    public static StyleBuilder boldTextBuilder() {
        return normalTextBuilder(10).bold();
    }

    public static StyleBuilder topBorderBuilder() {
        return stl.style().setTopBorder(stl.pen1Point());
    }
    
    public static StyleBuilder bottomBorderBuilder() {
        return stl.style().setBottomBorder(stl.pen1Point());
    }
    
    public static StyleBuilder topPaddingBuilder() {
        return stl.style().setBottomPadding(140).setTopBorder(stl.pen1Point());
    }

    public static String formatDecimal(double val) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(val);
    }

    public static class DecimalValueFormatter extends AbstractValueFormatter<String, Number> {

        private static final long serialVersionUID = 1L;

        @Override
        public String format(Number value, ReportParameters reportParameters) {
//            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
//            return decimalFormat.format(value);
            return Utils.formatNegative(value.doubleValue());
        }

    }
    
    public static String formatNegative(double value) {
        
        DecimalFormat decimalFormat = new DecimalFormat("(#,##0.00)");
        DecimalFormat pFormat = new DecimalFormat("#,##0.00");
        
        if (value < 0) {
            return decimalFormat.format(Math.abs(value));
        }
        
        return pFormat.format(value);
    }

    public static DefaultTableCellRenderer getDecimalFromatTableCellRenderer() {

        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

            private static final long serialVersionUID = 1L;
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                if (value instanceof Number) {
                    value = decimalFormat.format(value);
                }

                return super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
            }
        };

        tableCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        return tableCellRenderer;
    }
}
