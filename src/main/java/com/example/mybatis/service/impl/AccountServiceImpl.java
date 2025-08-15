package com.example.mybatis.service.impl;

import com.example.mybatis.dao.AccountDao;
import com.example.mybatis.domain.Account;
import com.example.mybatis.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void deleteById(Integer id) {
        accountDao.deleteById(id);
    }

    @Override
    public List<Account> findByName(String name) {
        return accountDao.findByName(name);
    }

    @Override
    public void updateMoney(Integer id, Integer amount) {
        accountDao.updateMoney(id, amount);
    }

    @Override
    @Transactional
    public void transfer(Integer fromId, Integer toId, Integer amount) {
        // 转账业务逻辑
        Account fromAccount = accountDao.findById(fromId);
        Account toAccount = accountDao.findById(toId);

        if (fromAccount == null || toAccount == null) {
            throw new RuntimeException("账户不存在");
        }

        if (fromAccount.getMoney() < amount) {
            throw new RuntimeException("余额不足");
        }

        // 扣钱
        accountDao.updateMoney(fromId, -amount);
        // 加钱
        accountDao.updateMoney(toId, amount);
    }
}
