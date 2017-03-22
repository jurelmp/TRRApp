/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.model;

import java.util.Date;

/**
 *
 * @author JurelP
 */
public class Transaction {
    
    private int accountId;
    private int id;
    private String reference;
    private String payee;
    private String desc;
    private Date date;
    private double deposit;
    private double payment;
    private boolean clear;
    private Date dateCreated;
    private Date dateUpdated;

    public Transaction() {
    }

    public Transaction(int accountId, String reference, String payee, 
            String desc, Date date, double deposit, double payment, boolean clear, Date dateCreated, Date dateUpdated) {
        this.reference = reference;
        this.payee = payee;
        this.desc = desc;
        this.date = date;
        this.deposit = deposit;
        this.payment = payment;
        this.clear = clear;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.accountId = accountId;
    }

    public Transaction(int accountId, int id, String reference, String payee, 
            String desc, Date date, double deposit, double payment, boolean clear, Date dateCreated, Date dateUpdated) {
        this.id = id;
        this.reference = reference;
        this.payee = payee;
        this.desc = desc;
        this.date = date;
        this.deposit = deposit;
        this.payment = payment;
        this.clear = clear;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.accountId = accountId;
    }

    public Transaction(int id, String ref, String payee, double deposit, double payment,
            String desc, Date date, boolean clear) {
        this.accountId = id;
        this.reference = ref;
        this.payee = payee;
        this.deposit = deposit;
        this.payment = payment;
        this.date = date;
        this.clear = clear;
        this.desc = desc;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
    
    public boolean isClear() {
        return clear;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public String toString() {
        return "Transaction{" + "accountId=" + accountId + ", id=" + id + ", reference=" + reference + ", payee=" + payee + ", desc=" + desc + ", date=" + date + ", deposit=" + deposit + ", payment=" + payment + ", clear=" + clear + ", dateCreated=" + dateCreated + ", dateUpdated=" + dateUpdated + '}';
    }
    
}
