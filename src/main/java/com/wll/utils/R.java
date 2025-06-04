package com.wll.utils;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class R {

    private int code;

    private String message;


    private Object data;

    // coordinated with Froala
    private String link;


    public R(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public R() {
    }


    @Override
    public String toString() {
        return """
                {
                code:%d
                message:%s
                }
                """.formatted(code, message)
                ;
    }

    public static R unAuthorization() {
        return new R(401, "未登录", null);
    }

    public static R success() {
        R r = new R();
        r.setCode(200);
        r.setMessage("执行成功");
        r.setData(null);
        return r;
    }

    public static R success(Object data) {
        R r = new R();
        r.setCode(200);
        r.setMessage("执行成功");
        r.setData(data);
        return r;
    }

    public static R successWithLink(Object data, String link) {
        R r = R.success(data);
        r.setLink(link);
        return r;
    }

    public static R success(String message) {
        R r = new R();
        r.setCode(200);
        r.setMessage(message);
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

        return r;
    }

    public static R error(String message) {
        R r = new R();

        r.setCode(501);
        r.setMessage(message);
        return r;
    }

    public static R error() {
        R r = new R();


        r.setCode(501);
        return r;
    }


    public static R fatal(String message) {
        R r = error(message);
        r.setCode(500);
        return r;
    }
}
