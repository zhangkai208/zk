package com.example.mybatis.service;

import com.example.mybatis.domain.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findById(Integer id);

    void save(Account account);

    void update(Account account);

    void deleteById(Integer id);

    List<Account> findByName(String name);

    void updateMoney(Integer id, Integer amount);

    void transfer(Integer fromId, Integer toId, Integer amount);
}
