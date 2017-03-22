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
public class OnDateFund {
    private int id;
    private String details;
    private double amount;

    public OnDateFund() {
    }

    public OnDateFund(int id, String details, double amount) {
        this.id = id;
        this.details = details;
        this.amount = amount;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OnDateFund{" + "id=" + id + ", details=" + details + ", amount=" + amount + '}';
    }
    
}
