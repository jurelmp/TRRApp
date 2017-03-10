package com.jp.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author JurelP
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;
    private Date date;
    private String ref;
    private String payee;
    private double deposit;
    private double payment;
    private String bankCode;
    private boolean clr;
    private String rec;
    private String memo;
    
    public Item() {}

    public Item(int id, Date date, String ref, String payee, double deposit, double payment, String bankCode, boolean clr, String rec, String memo) {
        this.id = id;
        this.date = date;
        this.ref = ref;
        this.payee = payee;
        this.deposit = deposit;
        this.payment = payment;
        this.bankCode = bankCode;
        this.clr = clr;
        this.rec = rec;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public boolean isClr() {
        return clr;
    }

    public void setClr(boolean clr) {
        this.clr = clr;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", date=" + date + ", ref=" + ref + ", payee=" + payee + ", deposit=" + deposit + ", payment=" + payment + ", bankCode=" + bankCode + ", clr=" + clr + ", rec=" + rec + ", memo=" + memo + '}';
    }
}
