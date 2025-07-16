package com.zk.pojo;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;


@Data
public class User {
    private Integer id;//主键ID

    @Pattern(regexp = "^.{5,16}$")
    private String username;//用户名
    @JsonIgnore

    @Pattern(regexp = "^.{5,16}$")
    private String password;//密码

    @Pattern(regexp = "^.{1,10}$")
    private String nickname;//昵称

    @Email
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
