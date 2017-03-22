/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author JurelP
 */
public class Account implements Serializable {
    
    private static final long serialVersionUID = -8219218627533074108L;
    
    private int id;
    private String code;
    private String name;
    private Date dateCreated;
    private Date dateUpdated;
    private boolean active;

    public Account() {
    }

    public Account(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Account(String code, String name, Date dateCreated, Date dateUpdated) {
        this.code = code;
        this.name = name;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public Account(int id, String code, String name, Date dateCreated, Date dateUpdated) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", code=" + code + ", name=" + name + ", dateCreated=" + dateCreated + ", dateUpdated=" + dateUpdated + ", active=" + active + '}';
    }
        
}
