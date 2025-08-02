package com.zk.anno;

import com.zk.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-17 10:00
 * @version: 1.0
 **/
@Documented
@Constraint(
        validatedBy = {StateValidation.class}
)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface State {
    String message() default "State 只能是已发布或草稿";
    //指定分组
    Class<?>[] groups() default {};
    //指定负载
    Class<? extends Payload>[] payload() default {};
}