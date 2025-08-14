package com.example.spring.service.impl;

import com.example.spring.dao.BookDao;
import com.example.spring.service.BookService;

public class BookServiceImpl implements BookService {
    /*private BookDao bookDao = new BookDaoImpl();*/
    private BookDao bookDao;
    @Override
    public void save() {
        System.out.println("book service save...");
        bookDao.save();
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}

