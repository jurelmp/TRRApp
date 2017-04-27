/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author JurelP
 */
public class DecimalFormatTableCellRenderer extends DefaultTableCellRenderer {
    
    private static final long serialVersionUID = 1L;
    
    DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        if (value instanceof Number) {
            value = decimalFormat.format(value);
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
    
}
