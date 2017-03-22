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
public class AccountSummary {
    private String bankCode;
    private BigDecimal actual;
    private BigDecimal preliminary;

    public AccountSummary() {
    }

    public AccountSummary(String bankCode, BigDecimal actual, BigDecimal preliminary) {
        this.bankCode = bankCode;
        this.actual = actual;
        this.preliminary = preliminary;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public BigDecimal getActual() {
        return actual;
    }

    public void setActual(BigDecimal actual) {
        this.actual = actual;
    }

    public BigDecimal getPreliminary() {
        return preliminary;
    }

    public void setPreliminary(BigDecimal preliminary) {
        this.preliminary = preliminary;
    }

    @Override
    public String toString() {
        return "AccountSummary{" + "bankCode=" + bankCode + ", actual=" + actual + ", preliminary=" + preliminary + '}';
    }
    
}
