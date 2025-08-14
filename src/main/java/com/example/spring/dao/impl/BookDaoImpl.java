package com.example.spring.dao.impl;

import com.example.spring.dao.BookDao;

public class BookDaoImpl implements BookDao {
    public BookDaoImpl(){
        System.out.println("book dao impl constructor...");
    }
    @Override
    public void save() {
        System.out.println("book dao save...");
    }
}