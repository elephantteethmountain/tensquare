package com.tensquare.base.controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.Result;
import entity.StatusCode;
/**
 * 公共异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e ){
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}