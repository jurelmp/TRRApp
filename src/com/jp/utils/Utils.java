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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;

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
}
