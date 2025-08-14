package com.example.spring.service.impl;

import com.example.spring.dao.BookDao;
import com.example.spring.dao.impl.BookDaoImpl;
import com.example.spring.service.BookService;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    @Override
    public void save() {
        System.out.println("book service save...");
        bookDao.save();
    }
}

