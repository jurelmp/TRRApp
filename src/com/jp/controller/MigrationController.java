/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.controller;

import com.jp.dao.MigrationDAO;
import java.io.File;

/**
 *
 * @author JurelP
 */
public abstract class MigrationController {
    
    protected MigrationDAO migrationDAO;
    
    public MigrationController() {};
    
    public MigrationController(MigrationDAO migrationDAO) {
        this.migrationDAO = migrationDAO;
    }
    
}
