package com.example.spring;

import com.example.spring.dao.BookDao;
import com.example.spring.dao.impl.BookDaoImpl;
import com.example.spring.service.BookService;
import com.example.spring.service.impl.BookServiceImpl;



public class Application {

    public static void main(String[] args) {
        /*BookService bookService = new BookServiceImpl();
        bookService.save();*/

        BookDao bookDao = new BookDaoImpl();
        System.out.println(bookDao);
        bookDao.save();
    }

}
