package com.wll.enums;

/**
 * @time 2025/4/24 16:45 周四
 */
public enum HTTPStatus {
    OK(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CLIENT_ERROR(450),
    SERVER_ERROR(500),
    ;


    private final int code;

    HTTPStatus(int code) {

        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
