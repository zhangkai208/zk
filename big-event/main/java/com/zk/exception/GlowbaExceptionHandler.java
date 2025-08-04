package com.zk.exception;



import com.zk.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-15 11:05
 * @version: 1.0
 **/
@RestControllerAdvice
public class GlowbaExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e) {
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}