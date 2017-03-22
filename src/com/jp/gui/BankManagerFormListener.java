/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.Other;

/**
 *
 * @author JurelP
 */
public interface BankManagerFormListener {
    
    void otherActionEmitted(Other other);

    public void update(Other selected);

    public void delete(Other row);
    
}
