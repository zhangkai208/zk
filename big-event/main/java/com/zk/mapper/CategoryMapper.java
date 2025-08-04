package com.zk.mapper;

import com.zk.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-16 14:15
 * @version: 1.0
 **/
@Mapper
public interface CategoryMapper {
    @Insert("insert into category (category_name,category_alias,create_user,create_time,update_time)" +
            " values (#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    @Select("select * from category where create_user=#{id}")
    List<Category> list(Integer id);

    // CategoryMapper.java
    @Select("select * from category where id=#{id}")
    Category findById(Integer id);

    @Delete("delete from category where id=#{id}")
    void delete(Integer id);

    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id=#{id}")
    void update(Category category);
}