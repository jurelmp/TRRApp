/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author JurelP
 */
public class MigrationTransactionDAOImpl implements MigrationDAO {

    @Override
    public void exportToFile(File file, LinkedList<Object> objects) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            Object[] objectsArr = objects.toArray(new Object[objects.size()]);

            objectOutputStream.writeObject(objects);

            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<Object> importFromFile(File file) {
        LinkedList<Object> objects = new LinkedList<>();
        
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Object[] objArr = (Object[]) objectInputStream.readObject();

            objects.addAll(Arrays.asList(objArr));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objects;
    }
 
}
