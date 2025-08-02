package com.zk.pojo;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-16 11:27
 * @version: 1.0
 **/
@Data
public class UpdatePwd {
    @Pattern(regexp = "^.{5,16}$", message = "密码长度必须在5到16个字符之间")
    private String oldPwd;

    @Pattern(regexp = "^.{5,16}$", message = "密码长度必须在5到16个字符之间")
    private String newPwd;

    @Pattern(regexp = "^.{5,16}$", message = "密码长度必须在5到16个字符之间")
    private String rePwd;
}