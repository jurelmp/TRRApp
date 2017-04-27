/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import javax.swing.text.Document;
import org.apache.batik.ext.awt.RenderingHintsKeyExt;

/**
 *
 * @author JurelP
 */
@SuppressWarnings("serial")
public class PlaceholderTextField extends JTextField {
    
    private String placeholder;

    public PlaceholderTextField() {
    }

    public PlaceholderTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    public PlaceholderTextField(int columns) {
        super(columns);
    }

    public PlaceholderTextField(String text) {
        super(text);
    }

    public PlaceholderTextField(String text, int columns) {
        super(text, columns);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (placeholder.length() == 0 || getText().length() > 0) {
            return;
        }
        
        final Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(getDisabledTextColor());
        graphics2D.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
    }
    
    
    
}
