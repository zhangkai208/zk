package com.example.mybatis.dao;

import com.example.mybatis.domain.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountDao {

    @Select("SELECT * FROM users")
    List<Account> findAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    Account findById(Integer id);

    @Insert("INSERT INTO users (id, name, money) VALUES (#{id}, #{name}, #{money})")
    void save(Account account);

    @Update("UPDATE users SET name = #{name}, money = #{money} WHERE id = #{id}")
    void update(Account account);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteById(Integer id);

    @Select("SELECT * FROM users WHERE name = #{name}")
    List<Account> findByName(String name);

    @Update("UPDATE users SET money = money + #{amount} WHERE id = #{id}")
    void updateMoney(@Param("id") Integer id, @Param("amount") Integer amount);
}
