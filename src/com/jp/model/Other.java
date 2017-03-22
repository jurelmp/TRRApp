/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.model;

import java.math.BigDecimal;

/**
 *
 * @author JurelP
 */
public class Other {
    private int id;
    private String details;
    private BigDecimal amount;

    public Other() {
    }

    public Other(int id, String details, BigDecimal amount) {
        this.id = id;
        this.details = details;
        this.amount = amount.setScale(BigDecimal.ROUND_HALF_EVEN);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Other{" + "id=" + id + ", details=" + details + ", amount=" + amount + '}';
    }
    
}
