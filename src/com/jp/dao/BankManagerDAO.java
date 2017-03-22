/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import com.jp.model.AdditionalFund;
import com.jp.model.OnDateFund;
import com.jp.model.Other;
import java.util.List;

/**
 *
 * @author JurelP
 */
public interface BankManagerDAO {
    
    Other insertOtherFunds(Other other);
    public List<Other> getOthersSummary();
    public void updateOtherFunds(Other selected);
    public void deleteOtherFunds(Other row);
    
    OnDateFund insertOnDateFund(OnDateFund onDateFund);
    public List<OnDateFund> getOnDateFunds();
    public void updateOnDateFund(OnDateFund onDateFund);
    public void deleteOnDateFund(OnDateFund onDateFund);
    
    AdditionalFund insertAdditionalFund(AdditionalFund additionalFund);
    public List<AdditionalFund> getAdditionalFunds();
    public void updateAdditionalFund(AdditionalFund additionalFund);
    public void deleteAdditionalFund(AdditionalFund additionalFund);
}
