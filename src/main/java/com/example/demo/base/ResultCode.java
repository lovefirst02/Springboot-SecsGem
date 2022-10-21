package com.example.demo.base;

public enum ResultCode {
    SUCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultCode(int code){
        this.code = code;
    }
}
