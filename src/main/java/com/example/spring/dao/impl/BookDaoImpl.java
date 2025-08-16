package com.example.spring.dao.impl;

import com.example.spring.dao.BookDao;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
@Repository("bookDao")
@Scope("singleton")
public class BookDaoImpl implements BookDao/*, InitializingBean, DisposableBean */{
/*
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
    }*/
    /*@Value("10")
    private int connectionNum;
    @Value("${name}")
    private String databaseName;*/

    @Override
    public void save(){
        /*System.out.println("book dao save ...");
        System.out.println("遍历数组" + Arrays.toString(array));
        System.out.println("遍历List" + list);
        System.out.println("遍历Set" + set);
        System.out.println("遍历Map" + map);
        System.out.println("遍历Properties" + properties);*/
        /*System.out.println("book dao save..."+connectionNum+","+databaseName);*/

        Long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            System.out.println("book dao save...");
        }

        Long end = System.currentTimeMillis();

        System.out.println("执行时间：" + (end - start));
        /*System.out.println(System.currentTimeMillis());
        System.out.println("book dao save...");*/
    }

    public  int select(){
        System.out.println("book dao select...");
        int i = 1/0;
        return 100;
    }
    public  void  update(){
        System.out.println("book dao update...");
    }
    public void  delete(){
        System.out.println("book dao delete...");
    }

    @Override
    public String findName(int id) {
        System.out.println("id:"+id);
        return "null";
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
    /*@PostConstruct
    public void init(){
        System.out.println("book dao init...");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("book dao destroy...");
    }*/
}