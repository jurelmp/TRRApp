/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.utils;

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
        
        if (url == null)
            System.err.println("Unable to load image: " + path);
        
        return new ImageIcon(url);
    }
    
    public static Font createFont(String path) {
        URL url = System.class.getResource(path);
        
        if (url == null)
            System.err.println("Unable to load font: " + path);
        
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
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return new DefaultFormatterFactory(new NumberFormatter(decimalFormat));
    }
    
    public static StyleBuilder normalTextBuilder(int size) {
        FontBuilder normalFont = stl.font("Times New Roman", false, false, size);
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
    
    
    public static String formatDecimal(double val) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(val);
    }
    
    public static class DecimalValueFormatter extends AbstractValueFormatter<String, Number> {

        private static final long serialVersionUID = 1L;

        @Override
        public String format(Number value, ReportParameters reportParameters) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
            return decimalFormat.format(value);
        }
        
    }
}
