package com.ivp.xch.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestURI {
    // 前台页面的 提交方式 一般也就使用 GET或者 POST这两种
    public enum RequestMethod {
        GET, POST, PUT, DELETE
    };

    // 请求地址 支持一个方法处理多个 URL
    String[] urlPatterns();

    // 这是提交方式 默认是GET 方式
    RequestMethod method() default RequestMethod.GET;

}

