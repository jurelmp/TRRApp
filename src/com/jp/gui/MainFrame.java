/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.controller.AccountController;
import com.jp.controller.BackupController;
import com.jp.controller.BankManagerController;
import com.jp.controller.ReportController;
import com.jp.controller.TransactionController;
import com.jp.model.Account;
import com.jp.model.AdditionalFund;
import com.jp.model.Database;
import com.jp.model.Item;
import com.jp.model.OnDateFund;
import com.jp.model.Other;
import com.jp.model.Transaction;
import com.jp.reports.GenerateReports;
import com.jp.reports.OutputReports;
import com.jp.utils.Utils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author JurelP
 */
public class MainFrame extends JFrame {
    
    private JSplitPane splitPane, leftPane, rightPane;
    
    private Toolbar toolbar;
    private AccountsPanel accountsPanel;
    private AccountFormPanel accountFormPanel;
    private TransactionsPanel transactionsPanel;
    private TransactionFormPanel transactionFormPanel;
    
    private AccountController accountController;
    private TransactionController transactionController;
    private ReportController reportController;
    private BackupController backupController;
    private BankManagerController bankManagerController;
    
    private MigrationDialog migrationDialog;
    private BackupDialog backupProgressDialog;
    private BankManagerDialog bankManagerDialog;
    private BankManagerSettingsDialog bankManagerSettingsDialog;
    
    private Date nextBankingDate;

    public MainFrame() {
        super("Petrologistics");
        setLayout(new BorderLayout());
        
//        AccountDAOImpl accountDAOImpl = new AccountDAOImpl();
        
        // Initialize the components
        toolbar = new Toolbar();
        accountsPanel = new AccountsPanel();
        accountFormPanel = new AccountFormPanel();
        transactionsPanel = new TransactionsPanel();
        transactionFormPanel = new TransactionFormPanel();
        leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, accountFormPanel, accountsPanel);
        leftPane.setOneTouchExpandable(true);
        leftPane.setDividerLocation(accountFormPanel.getMinimumSize().height);
        rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, transactionsPanel,transactionFormPanel);
        rightPane.setOneTouchExpandable(true);
        setRightPaneDividerLocation();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        
        migrationDialog = new MigrationDialog(this);
        backupProgressDialog = new BackupDialog(this);
        bankManagerDialog = new BankManagerDialog(this);
        bankManagerSettingsDialog = new BankManagerSettingsDialog(this);
        
        
        // Initialize the controllers
        accountController = new AccountController();
        transactionController = new TransactionController();
        reportController = new ReportController();
        backupController = new BackupController();
        bankManagerController = new BankManagerController();
        
        loadAccounts();
        
        loadTransactions();
        
        setJMenuBar(createMenuBar());
        
//        accountsPanel.setFirstSelected();
        
        // Adding Listeners
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(
                        MainFrame.this, 
                        "Do you really want to exit the application?",
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION);
                
                if (option == JOptionPane.OK_OPTION) {
                    Database.getInstance().shutdown();
                    dispose();
                    System.gc();
                }
            }
            
        });
        
        accountsPanel.setAccountSelectionListener(new AccountsSelectionListener() {
            @Override
            public void accountSelected(Account account) {
                accountFormPanel.setAccountForm(account);
                transactionsPanel.setAccount(account);
                transactionsPanel.clearSelection();
                transactionFormPanel.setAccount(account);
                transactionsPanel.setData(transactionController.getTransactions(account));
            }
        });
        
        accountFormPanel.setAccountFormActionListener(new AccountFormActionListener() {
            @Override
            public void saveAccountEventOccurred(Account account) {
                accountsPanel.addRow(accountController.insertAccount(account));
//                loadAccounts();
            }

            @Override
            public void updateAccountEventOccurred(Account account) {
                accountController.updateAccount(account);
                accountsPanel.refresh();
            }
        });
        
        transactionFormPanel.setTransactionFormActionListener(new TransactionFormActionListener() {
            
//            @Override
//            public void buttonSaveClicked(Transaction transaction, int mode) {
//                
//                if (transaction == null) {
//                    setNotificationMessage("Please select an account.");
//                } else {
//                    if (mode == TransactionFormActionListener.MODE_INSERT) {
//                        transactionsPanel.addRow(transactionController.insertTransaction(transaction));
//                    } else if (mode == TransactionFormActionListener.MODE_UPDATE) {
//                        transactionController.updateTransaction(transaction);
//                        transactionsPanel.refresh();
//                    }
//                }
//            }

            @Override
            public void buttonSaveClicked(Transaction transaction) {
                transactionsPanel.addRow(transactionController.insertTransaction(transaction));
                System.out.println(transaction);
            }

            @Override
            public void buttonUpdateClicked(Transaction transaction) {
                transactionController.updateTransaction(transaction);
                transactionsPanel.refresh();
                System.out.println(transaction);
            }

            @Override
            public void buttonRemoveClicked(Transaction transaction, int rowIndex) {
                if (transaction != null) {
                    int option = JOptionPane.showConfirmDialog(MainFrame.this,
                            "Are you sure you want to remove this transaction?",
                            "Remove Confirmation", 
                            JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        transactionController.removeTransaction(transaction);
                        transactionsPanel.rowDeleted(rowIndex);
                        
//                        transactionsPanel.refresh();
                    }
                }
            }
        });
        
        transactionsPanel.setTransactionsSelectionListener(new TransactionsSelectionListener() {
            @Override
            public void transactionSelected(Transaction transaction, int rowIndex) {
                transactionFormPanel.setTransaction(transaction, rowIndex);
            }
        });
        
        transactionsPanel.setTransactionClearActionListener(new TransactionClearActionListener() {
            @Override
            public void valueChanged(Transaction transaction) {
//                System.out.println(transaction);
                transactionController.clearTransaction(transaction);
            }
        });
        
        toolbar.setToolbarActionListener(new ToolbarActionListener() {
            @Override
            public void exportEventOccured() {
                JXDatePicker datePicker = new JXDatePicker(Utils.getDateNow());
                String message = "Select a date:\n";
                Object[] params = {message, datePicker};
                
                
                int option = JOptionPane.showConfirmDialog(MainFrame.this, params, "Export Date", JOptionPane.OK_CANCEL_OPTION);
                
                if (option == JOptionPane.OK_OPTION) {
                    GenerateReports reports = new GenerateReports();
                    nextBankingDate = ((JXDatePicker)params[1]).getDate();
                    
                    
                    /** Test Date */
//                    Date p;
//                    Date n;
//                    try {
//                        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
//                        String date1Str = "15-03-2017";
//                        String date2Str = "16-03-2017";
//                        p = sdf.parse(date1Str);
//                        n = sdf.parse(date2Str);
//                    } catch (Exception e) {
//                    }
                    /** */
                    
                    reports.setResultSets(reportController.getReportsByDateRange(Utils.getDateNow(), nextBankingDate));
                    reports.build();
                    OutputReports outputReports = new OutputReports(reports.getDataSource());
                    outputReports.setRangeDate(new Date(), nextBankingDate);
                    outputReports.buildReport();
                }
            }
        });
        
        migrationDialog.setMigrationListener(new MigrationListener() {
            @Override
            public void transactionsMigrated(List<Item> items) {
                List<Transaction> transactions = new ArrayList<>();
                
                for (Item item : items) {
                    Transaction temp = new Transaction();
                    Account a = accountController.getAccountByCode(item.getBankCode());
                    
                    if (a == null) {
                        a = accountController.getAccountByCode("OTHER");
                    }
                    
                    temp.setReference(item.getRef());
                    temp.setDate(item.getDate());
                    temp.setPayee(item.getPayee());
                    temp.setDeposit(item.getDeposit());
                    temp.setPayment(item.getPayment());
                    temp.setDesc(item.getRec());
                    temp.setClear(item.isClr());
                    temp.setAccountId(a.getId());
                    temp.setId(0);
                    transactions.add(temp);
                }
                
                transactions = transactionController.insertAll(transactions);
                System.out.println(transactions.size() + " transactions inserted.");
                for (Transaction transaction : transactions) {
                    transactionsPanel.addRow(transaction);
//                    System.out.println(transaction);
                }
            }

            @Override
            public void accountsMigrated(List<Account> accounts) {
                
                accounts = accountController.insertAll(accounts);
                System.out.println(accounts.size() + " accounts inserted.");
                for (Account account : accounts) {
                    accountsPanel.addRow(account);
                    System.out.println(account);
                }
            }
        });
        
        bankManagerSettingsDialog.setListener(new BankManagerSettingsDialogActionListener() {
            @Override
            public void setAccountActive(Account account) {
                accountController.setAccountActive(account);
            }
        });
        
        bankManagerDialog.setEventListener(new EventListener() {
            
            @Override
            public void eventOcurred(Object model) {
                
                if (model instanceof Other) {
                    Other other = bankManagerController.insertOtherFunds((Other) model);
                    bankManagerDialog.updateOther(other);
//                    System.out.println(other);
                } 
            }

            @Override
            public void updateOccurred(Other selected) {
                if (selected instanceof Other) {
                    bankManagerController.updateOtherFunds(selected);
                }
            }

            @Override
            public void deleteOccurred(Other row) {
                bankManagerController.deleteOtherFunds(row);
            }
            
        });
        
        bankManagerDialog.setAdditionalEventListener(new BMAdditionalEventListener() {
            @Override
            public void insert(Object object) {
                if (object instanceof OnDateFund) {
                    OnDateFund odf = (OnDateFund) object;
                    odf = bankManagerController.insertOnDateFund(odf);
                    bankManagerDialog.insertOnDateFund(odf);
                }
                
                if (object instanceof AdditionalFund) {
                    AdditionalFund af = (AdditionalFund) object;
                    af = bankManagerController.insertAdditionalFund(af);
                    bankManagerDialog.insertAdditionalFund(af);
                }
                
            }

            @Override
            public void update(Object object) {
                if (object instanceof OnDateFund) {
                    OnDateFund odf = (OnDateFund) object;
                    bankManagerController.updateOnDateFund(odf);
                }
                
                if (object instanceof AdditionalFund) {
                    AdditionalFund af = (AdditionalFund) object;
                    bankManagerController.updateAdditionalFund(af);
                }
            }

            @Override
            public void delete(Object object) {
                if (object instanceof OnDateFund) {
                    OnDateFund odf = (OnDateFund) object;
                    bankManagerController.deleteOnDateFund(odf);
                }
                
                if (object instanceof AdditionalFund) {
                    AdditionalFund af = (AdditionalFund) object;
                    bankManagerController.deleteAdditionalFund(af);
                }
            }
        });
        
        
        
        accountsPanel.refresh();
        
        accountsPanel.setTableSelection(0);
        
        // Place the components to the frame
        add(toolbar, BorderLayout.PAGE_START);
        add(splitPane, BorderLayout.CENTER);
        
        setMinimumSize(new Dimension(950, 600));
//        setSize(1000, 650);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void setRightPaneDividerLocation() throws HeadlessException {
        rightPane.setDividerLocation(Toolkit.getDefaultToolkit().getScreenSize().height - transactionFormPanel.getPreferredSize().height);
    }

    private final void loadAccounts() {
        
        accountsPanel.setData(accountController.getAccounts());
//        accountsPanel.setFirstSelected();
    }
    
    private final void loadTransactions() {
        transactionsPanel.setData(transactionController.getTransactions());
    }
    
    public void setNotificationMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuFile = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        JMenu dataFile = new JMenu("Data");
        JMenuItem backupItem = new JMenuItem("Backup");
        JMenuItem restoreBackupItem = new JMenuItem("Restore");
        JMenuItem migrationItem = new JMenuItem("Migration");
        
        JMenu windowFile = new JMenu("Window");
        JCheckBoxMenuItem transactionFormItem = new JCheckBoxMenuItem("Transaction Form");
        JCheckBoxMenuItem accountFormItem = new JCheckBoxMenuItem("Account Form");
        
        JMenu bankManagerFile = new JMenu("Bank Manager");
        JMenuItem openBankManagerItem = new JMenuItem("Open");
        JMenuItem settingsBankManagerItem = new JMenuItem("Settings");
        
        menuFile.add(exitItem);
        dataFile.add(backupItem);
//        dataFile.add(restoreBackupItem);
        dataFile.add(migrationItem);
        windowFile.add(transactionFormItem);
        windowFile.add(accountFormItem);
        bankManagerFile.add(openBankManagerItem);
        bankManagerFile.add(settingsBankManagerItem);
        
        menuBar.add(menuFile);
        menuBar.add(dataFile);
        menuBar.add(windowFile);
        menuBar.add(bankManagerFile);
        
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                WindowListener[] listeners = getWindowListeners();
                    
                for (WindowListener listener : listeners){
                    listener.windowClosing(new WindowEvent(MainFrame.this, 0));
                }
            }
        });
        
        migrationItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                migrationDialog.setVisible(true);
            }
        });
        
        backupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBackup();
            }
        });
        
        openBankManagerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
//                    String date1Str = "15-03-2017";
//                    String date2Str = "16-03-2017";
//                    Date p = sdf.parse(date1Str);
//                    Date n = sdf.parse(date2Str);

                    Date p = Utils.getDateNow();
                    Date n = nextBankingDate != null ? nextBankingDate : Utils.getDateNow();
                    
                    bankManagerDialog.setAccountSummaries(reportController.getAccountsSummary(p, n));
                    bankManagerDialog.setOthersSummaries(bankManagerController.getOthersSummary());
                    bankManagerDialog.setOnDateFunds(bankManagerController.getOnDateFunds());
                    bankManagerDialog.setAdditionalFunds(bankManagerController.getAdditionalFunds());
                    bankManagerDialog.setPreparedDate(p);
                    bankManagerDialog.setNextBankingDate(n);
                    bankManagerDialog.setVisible(true);
                } catch (Exception exc) {
                    
                }
                
            }
        });
        
        settingsBankManagerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bankManagerSettingsDialog.setAccounts(accountController.getAccounts());
                bankManagerSettingsDialog.setVisible(true);
            }
        });
        
        migrationItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        backupItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
//        restoreBackupItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        openBankManagerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        settingsBankManagerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        
        return menuBar;
    }
    
    private void createBackup() {
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                backupProgressDialog.setVisible(true);
                backupController.createBackup();
                return null;
            }

            @Override
            protected void done() {
                backupProgressDialog.setVisible(false);
                System.out.println("Done creating a backup.");
            }

            @Override
            protected void process(List<Void> chunks) {
                System.out.println("Processing...");
            }
        };
        
        int option = JOptionPane.showConfirmDialog(this,
                "Want to create a latest database backup?\n"
                        + "Note: Database backups are stored in \n" 
                        + new File("").getAbsolutePath() + "\\database\\backups",
                "Database Backup", 
                JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            worker.execute();
        }
    }
}
