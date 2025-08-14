package com.example.spring.service.impl;

import com.example.spring.dao.BookDao;
import com.example.spring.dao.UserDao;
import com.example.spring.service.BookService;

public class BookServiceImpl implements BookService {
    /*private BookDao bookDao = new BookDaoImpl();*/
    private BookDao bookDao;

    public BookServiceImpl(BookDao bookDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
    }

    private UserDao userDao;
    @Override
    public void save() {
        System.out.println("book service save...");
        bookDao.save();
        userDao.save();
    }

    /*public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }*/
}

