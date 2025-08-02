package com.zk.mapper;


import com.zk.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("Insert into article (title, content, cover_img, state, category_id, create_user,create_time, update_time)" +
            "values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    List<Article> list(Integer id, Integer categoryId, String state);

    @Update("Update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time= now() where id=#{id}")
    void update(Article article);

    @Select("SELECT * FROM article WHERE id=#{id}")
    Article findById(Integer id);

    @Delete("delete from article where id=#{id}")
    void delete(Integer id);
}








