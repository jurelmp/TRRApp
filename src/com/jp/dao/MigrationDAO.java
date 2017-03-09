/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import java.io.File;
import java.util.LinkedList;

/**
 *
 * @author JurelP
 */
public interface MigrationDAO {
    
    void exportToFile(File file, LinkedList<Object> objects);
    LinkedList<Object> importFromFile(File file);
    
}
