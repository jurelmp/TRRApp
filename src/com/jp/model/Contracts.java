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
public class Contracts {
    
    public static class AccountEntry {
        public static final String TABLE_NAME = "ACCOUNTS";
        
        public static final String COL_ID = "ID";
        public static final String COL_CODE = "CODE";
        public static final String COL_NAME = "NAME";
        public static final String COL_DATE_CREATED = "DATE_CREATED";
        public static final String COL_DATE_UPDATED = "DATE_UPDATED";
    }
    
    public static class TransactionEntry {
        public static final String TABLE_NAME = "TRANSACTIONS";
        
        public static final String COL_ACCOUNT_ID = "ACCOUNT_ID";
        public static final String COL_ID = "ID";
        public static final String COL_REF_NO = "REFERENCE_NO";
        public static final String COL_DATE = "DATE";
        public static final String COL_PAYEE = "PAYEE";
        public static final String COL_AMOUNT = "AMOUNT";
        public static final String COL_DESCRIPTION = "DESCRIPTION";
        public static final String COL_TYPE = "TYPE";
        public static final String COL_IS_CLEAR = "IS_CLEAR";
        public static final String COL_DATE_CREATED = "DATE_CREATED";
        public static final String COL_DATE_UPDATED = "DATE_UPDATED";
    }
}
