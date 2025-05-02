package com.wll.utils;

import com.wll.enums.HTTPStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Result {

    private int code;

    private String message;


    private Object data;


    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result() {
    }

    public static Result success(String message, Object data) {
        return new Result(HTTPStatus.OK.getCode(), message, data);
    }

    public static Result success(Object data) {
        return new Result(HTTPStatus.OK.getCode(), "ok", data);
    }

    public static Result unAuthorization(String message, Object data) {
        return new Result(HTTPStatus.UNAUTHORIZED.getCode(), message, data);
    }

    public static Result notFound(String message, Object data) {
        return new Result(HTTPStatus.NOT_FOUND.getCode(), message, data);
    }

    public static Result serverError(String message, Object data) {
        return new Result(HTTPStatus.SERVER_ERROR.getCode(), message, data);
    }

    public static Result clientError(String message, Object data) {
        return new Result(HTTPStatus.CLIENT_ERROR.getCode(), message, data);
    }

    public static Result other(int code, String message, Object data) {
        return new Result(code, message, data);
    }

}
