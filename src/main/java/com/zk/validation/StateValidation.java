package com.zk.validation;

import com.zk.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-17 10:05
 * @version: 1.0
 **/
public class StateValidation implements ConstraintValidator<State, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;}
        if (value.equals("已发布") || value.equals("草稿")) {
            return true;}
        return false;
    }
}
