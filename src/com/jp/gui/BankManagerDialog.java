/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.model.AccountSummary;
import com.jp.model.AdditionalFund;
import com.jp.model.OnDateFund;
import com.jp.model.Other;
import com.jp.reports.BankManager;
import com.jp.utils.Utils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author JurelP
 */
public class BankManagerDialog extends JDialog {

    private static final Dimension WINDOW_DIMENSION = new Dimension(700, 600);

//    private JPanel accountsPanel, accountsSummaryPanel;
//
//    private JTable accountsSummaryTable;
//    private JLabel accountsSummaryTotalLabel;
//    private JFormattedTextField accountsSummaryActualTotalField, accountsSummaryPreliminaryTotalField;
    
    private JTabbedPane tabs;
    private JPanel fields;
    private JButton viewReportButton;
    
    private BankManagerAccountsPanel bankManagerAccountsPanel;
    private BankManagerOthersPanel bankManagerOthersPanel;
    private BankManagerAdditionalPanel bankManagerAdditionalPanel;
    
    private JTextField preparedDateField, nextBankingDateField;
    private Date preparedDate, nextBankingDate;
    
    private EventListener eventListener;
    private BMAdditionalEventListener additionalEventListener;
    
    private BankManager bankManagerReportBuilder;

    public BankManagerDialog(JFrame parent) {
        super(parent, "Bank Manager", false);
        setLayout(new BorderLayout());
        
        init();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }

        });

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(WINDOW_DIMENSION);
        setMinimumSize(WINDOW_DIMENSION);
        setLocationRelativeTo(parent);
    }
// Prepared On: 03-15-16
// Next Banking Day: 03-16-17
    private void init() {
        
        bankManagerReportBuilder = new BankManager();

        tabs = new JTabbedPane();
        fields = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        viewReportButton = new JButton("View Report");
        
        preparedDateField = new JTextField(15);
        preparedDateField.setEditable(false);
        nextBankingDateField = new JTextField(15);
        nextBankingDateField.setEditable(false);
        
        bankManagerAccountsPanel = new BankManagerAccountsPanel();
        bankManagerOthersPanel = new BankManagerOthersPanel();
        bankManagerAdditionalPanel = new BankManagerAdditionalPanel();
        
        bankManagerOthersPanel.setFormListener(new BankManagerFormListener() {
            
            @Override
            public void otherActionEmitted(Other other) {
                if (eventListener != null) {
                    eventListener.eventOcurred(other);
                }
            }

            @Override
            public void update(Other selected) {
                if (eventListener != null) {
                    eventListener.updateOccurred(selected);
                }
            }

            @Override
            public void delete(Other row) {
                if (eventListener != null) {
                    eventListener.deleteOccurred(row);
                }
            }
            
        });
        
        bankManagerAdditionalPanel.setbMAdditionalEventListener(new BMAdditionalEventListener() {
            @Override
            public void insert(Object object) {
                if (additionalEventListener != null) {
                    additionalEventListener.insert(object);
                }
            }

            @Override
            public void update(Object object) {
                if (additionalEventListener != null) {
                    additionalEventListener.update(object);
                }
            }

            @Override
            public void delete(Object object) {
                if (additionalEventListener != null) {
                    additionalEventListener.delete(object);
                }
            }
        });
        
        viewReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bankManagerReportBuilder.setPreparedDate(preparedDate);
                bankManagerReportBuilder.setNextBankingDate(nextBankingDate);
                bankManagerReportBuilder.setAccountSummaries(bankManagerAccountsPanel.getAccountSummaries());
                bankManagerReportBuilder.setOthersFunds(bankManagerOthersPanel.getOtherFunds());
                bankManagerReportBuilder.setOnDateFunds(bankManagerAdditionalPanel.getOnDateFunds());
                bankManagerReportBuilder.setAdditionalFunds(bankManagerAdditionalPanel.getAdditionalFunds());
                
                // Sum
                bankManagerReportBuilder.setActual(bankManagerAccountsPanel.getActual());
                bankManagerReportBuilder.setPrelim(bankManagerAccountsPanel.getPreliminary());
                bankManagerReportBuilder.setOthers(bankManagerOthersPanel.getSum());
                bankManagerReportBuilder.setOnDate(bankManagerAdditionalPanel.getOnDateTotal());
                bankManagerReportBuilder.setAdditionalFund(bankManagerAdditionalPanel.getAdditionalTotal());
                
                bankManagerReportBuilder.generate();
                bankManagerReportBuilder.build();
            }
        });
        
        tabs.addTab("Accounts Summary", bankManagerAccountsPanel);
        tabs.addTab("Other Funding Needs", bankManagerOthersPanel);
        tabs.addTab("Additional Funds Available for Deposit", bankManagerAdditionalPanel);
        
        fields.add(new JLabel("Prepared: "));
        fields.add(preparedDateField);
        fields.add(new JLabel("Next Banking Day: "));
        fields.add(nextBankingDateField);
        fields.add(viewReportButton);
        
        add(tabs, BorderLayout.CENTER);
        add(fields, BorderLayout.PAGE_END);
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
        preparedDateField.setText(Utils.humanDate(this.preparedDate));
    }

    public void setNextBankingDate(Date nextBankingDate) {
        this.nextBankingDate = nextBankingDate;
        nextBankingDateField.setText(Utils.humanDate(this.nextBankingDate));
    }
    
    public void setAccountSummaries(List<AccountSummary> summaries) {
        bankManagerAccountsPanel.setData(summaries);
        bankManagerAccountsPanel.refresh();
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }
    
    public void setAdditionalEventListener(BMAdditionalEventListener additionalEventListener) {
        this.additionalEventListener = additionalEventListener;
    }

    void refresh() {
        bankManagerOthersPanel.refresh();
    }

    void updateOther(Other other) {
        bankManagerOthersPanel.update(other);
    }

    void setOthersSummaries(List<Other> othersSummary) {
        bankManagerOthersPanel.setData(othersSummary);
    }

    void insertOnDateFund(OnDateFund odf) {
        bankManagerAdditionalPanel.insertRow(odf);
    }

    void setOnDateFunds(List<OnDateFund> onDateFunds) {
        bankManagerAdditionalPanel.setOnDateFunds(onDateFunds);
    }

    void insertAdditionalFund(AdditionalFund af) {
        bankManagerAdditionalPanel.insertAdditionalFund(af);
    }

    void setAdditionalFunds(List<AdditionalFund> additionalFunds) {
        bankManagerAdditionalPanel.setAdditionalFunds(additionalFunds);
    }

}
