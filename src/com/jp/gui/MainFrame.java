/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.gui;

import com.jp.controller.AccountController;
import com.jp.controller.ReportController;
import com.jp.controller.TransactionController;
import com.jp.model.Account;
import com.jp.model.Database;
import com.jp.model.Item;
import com.jp.model.Transaction;
import com.jp.model.TransactionType;
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
    
    private MigrationDialog migrationDialog;

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
        leftPane.setDividerLocation(transactionFormPanel.getPreferredSize().height);
        rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, transactionsPanel,transactionFormPanel);
        rightPane.setOneTouchExpandable(true);
        setRightPaneDividerLocation();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        
        migrationDialog = new MigrationDialog(this);
        
        
        // Initialize the controllers
        accountController = new AccountController();
        transactionController = new TransactionController();
        reportController = new ReportController();
        
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
            @Override
            public void buttonSaveClicked(Transaction transaction, int mode) {
                
                if (transaction == null) {
                    setNotificationMessage("Please select an account.");
                } else {
                    if (mode == TransactionFormActionListener.MODE_INSERT) {
                        transactionsPanel.addRow(transactionController.insertTransaction(transaction));
                    } else if (mode == TransactionFormActionListener.MODE_UPDATE) {
//                        System.out.println(mode);
//                        System.out.println(transaction);
                        transactionController.updateTransaction(transaction);
                        transactionsPanel.refresh();
                    }
                }
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
        
        toolbar.setToolbarActionListener(new ToolbarActionListener() {
            @Override
            public void exportEventOccured() {
                JXDatePicker datePicker = new JXDatePicker(Utils.getDateNow());
                String message = "Select a date:\n";
                Object[] params = {message, datePicker};
                
                
                int option = JOptionPane.showConfirmDialog(MainFrame.this, params, "Export Date", JOptionPane.OK_CANCEL_OPTION);
                
                if (option == JOptionPane.OK_OPTION) {
                    GenerateReports reports = new GenerateReports();
                    Date date = ((JXDatePicker)params[1]).getDate();
                    reports.setResultSets(reportController.getReportsByDate(date));
                    reports.build();
                    OutputReports outputReports = new OutputReports(reports.getDataSource());
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
                    TransactionType type = item.getDeposit() == 0 ? TransactionType.payment : TransactionType.deposit;
                    double amount = type == TransactionType.deposit ? item.getDeposit() : item.getPayment();
                    temp.setReference(item.getRef());
                    temp.setDate(item.getDate());
                    temp.setPayee(item.getPayee());
                    temp.setAmount(amount);
                    temp.setDesc(null);
                    temp.setType(type);
                    temp.setClear(item.isClr());
                    temp.setAccountId(a.getId());
                    temp.setId(0);
                    transactions.add(temp);
                    System.out.println(a);
                }
                
                transactions = transactionController.insertAll(transactions);
                System.out.println(transactions.size() + " transactions inserted.");
                for (Transaction transaction : transactions) {
                    transactionsPanel.addRow(transaction);
                    System.out.println(transaction);
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
        
        accountsPanel.refresh();
        
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
        
        menuFile.add(exitItem);
        dataFile.add(backupItem);
        dataFile.add(restoreBackupItem);
        dataFile.add(migrationItem);
        windowFile.add(transactionFormItem);
        windowFile.add(accountFormItem);
        
        menuBar.add(menuFile);
        menuBar.add(dataFile);
        menuBar.add(windowFile);
        
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
        
        migrationItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        
        return menuBar;
    }
    
    /*
    public final List<Transaction> castItems(List<Item> items) {
        List<Transaction> t = new ArrayList<>();
        
        for (Item item : items) {
            Transaction temp = new Transaction();
            Account a = accountController.getAccountByCode(item.getBankCode());
            TransactionType type = item.getDeposit() == 0 ? TransactionType.payment : TransactionType.deposit;
            double amount = type == TransactionType.deposit ? item.getDeposit() : item.getPayment();
            temp.setReference(item.getRef());
            temp.setDate(item.getDate());
            temp.setPayee(item.getPayee());
            temp.setAmount(amount);
            temp.setDesc(null);
            temp.setType(type);
            temp.setClear(item.isClr());
            temp.setAccountId(a.getId());
            temp.setId(0);
            t.add(temp);
        }
        
        return t;
    } */
}
