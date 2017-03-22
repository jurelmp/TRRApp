/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.model;

/**
 *
 * @author JurelP
 */
public enum TransactionType {
    payment("Payment"),
    deposit("Deposit"),
    both("P/D");
    
    private String text;
    
    TransactionType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
