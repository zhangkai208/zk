package com.example.spring.dao.impl;

import com.example.spring.dao.BookDao;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BookDaoImpl implements BookDao/*, InitializingBean, DisposableBean */{


    private int connectionNum;
    private String databaseName;
    @Override
    public void save(){
        System.out.println("book dao save..."+connectionNum+","+databaseName);
    }

    public BookDaoImpl(int connectionNum, String databaseName) {
        this.connectionNum = connectionNum;
        this.databaseName = databaseName;
    }
    /*public void setConnectionNum(int connectionNum) {
        this.connectionNum = connectionNum;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }*/
/*public BookDaoImpl(){
        System.out.println("book dao impl constructor...");
    }*/

    /*@Override
    public void destroy() throws Exception {
        System.out.println("book dao destroy...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("book dao init...");
    }
*/

    /*public void init(){
        System.out.println("book dao init...");
    }

    public void destroy(){
        System.out.println("book dao destroy...");
    }*/
}