package com.example.spring.dao.impl;

import com.example.spring.dao.BookDao;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;

public class BookDaoImpl implements BookDao/*, InitializingBean, DisposableBean */{

    private int[] array;
    private List<String> list;
    private Set<String> set;
    private Map<String, String> map;
    private Properties properties;

    public void setArray(int[] array) {
        this.array = array;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /*private int connectionNum;
        private String databaseName;*/
    @Override
    public void save(){
        System.out.println("book dao save ...");
        System.out.println("遍历数组" + Arrays.toString(array));
        System.out.println("遍历List" + list);
        System.out.println("遍历Set" + set);
        System.out.println("遍历Map" + map);
        System.out.println("遍历Properties" + properties);
        /*System.out.println("book dao save..."*//*+connectionNum+","+databaseName*//*);*/
    }

    /*public BookDaoImpl(int connectionNum, String databaseName) {
        this.connectionNum = connectionNum;
        this.databaseName = databaseName;
    }*/
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