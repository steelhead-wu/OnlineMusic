package com.wll.utils;

import lombok.Data;

/**
 * @Author 祝英台炸油条
 * @Time : 2022/6/4 19:04
 **/
@Data
public class R {

    private int code;

    private String message;

    private String type;

    private Boolean success;

    private Object data;

    public static R success() {
        R r = new R();
        r.setCode(200);
        r.setMessage("执行成功");
        r.setSuccess(true);
        r.setType("success");
        r.setData(null);
        return r;
    }

    public static R success(Object data) {
        R r = new R();
        r.setCode(200);
        r.setMessage("执行成功");
        r.setSuccess(true);
        r.setType("success");
        r.setData(data);
        return r;
    }

    public static R success(String message) {
        R r = new R();
        r.setCode(200);
        r.setMessage(message);
        r.setSuccess(true);
        r.setType("success");
        r.setData(null);
        return r;
    }

    public static R success(String message, Object data) {
        R r = success(message);
        r.setData(data);
        return r;
    }

    public static R warning(String message) {
        R r = error(message);
        r.setType("warning");
        return r;
    }

    public static R error(String message) {
        R r = new R();
        r.setSuccess(false);
        r.setType("error");
        r.setCode(501);
        r.setMessage(message);
        return r;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setType("error");
        r.setCode(501);
        return r;
    }


    public static R fatal(String message) {
        R r = error(message);
        r.setCode(500);
        return r;
    }
}
