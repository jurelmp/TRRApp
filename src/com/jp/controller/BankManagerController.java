/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.controller;

import com.jp.dao.BankManagerDAO;
import com.jp.dao.BankManagerDAOImpl;
import com.jp.model.AdditionalFund;
import com.jp.model.OnDateFund;
import com.jp.model.Other;
import java.util.List;

/**
 *
 * @author JurelP
 */
public class BankManagerController {
    
    private BankManagerDAO bankManagerDAO;

    public BankManagerController() {
        bankManagerDAO = new BankManagerDAOImpl();
    }
    
    public Other insertOtherFunds(Other other) {
        return bankManagerDAO.insertOtherFunds(other);
    }
    
    public List<Other> getOthersSummary() {
        return bankManagerDAO.getOthersSummary();
    }

    public void updateOtherFunds(Other selected) {
        bankManagerDAO.updateOtherFunds(selected);
    }

    public void deleteOtherFunds(Other row) {
        bankManagerDAO.deleteOtherFunds(row);
    }
    
    public OnDateFund insertOnDateFund(OnDateFund onDateFund) {
        return bankManagerDAO.insertOnDateFund(onDateFund);
    }
    
    public List<OnDateFund> getOnDateFunds() {
        return bankManagerDAO.getOnDateFunds();
    }
    
    public void updateOnDateFund(OnDateFund onDateFund) {
        bankManagerDAO.updateOnDateFund(onDateFund);
    }
    
    public void deleteOnDateFund(OnDateFund onDateFund) {
        bankManagerDAO.deleteOnDateFund(onDateFund);
    }
    
    public AdditionalFund insertAdditionalFund(AdditionalFund additionalFund) {
        return bankManagerDAO.insertAdditionalFund(additionalFund);
    }
    
    public List<AdditionalFund> getAdditionalFunds() {
        return bankManagerDAO.getAdditionalFunds();
    }
    
    public void updateAdditionalFund(AdditionalFund additionalFund) {
        bankManagerDAO.updateAdditionalFund(additionalFund);
    }
    
    public void deleteAdditionalFund(AdditionalFund additionalFund) {
        bankManagerDAO.deleteAdditionalFund(additionalFund);
    }
}
