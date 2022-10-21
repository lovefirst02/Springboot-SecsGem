package com.example.demo.base;

public class ResultResponse {
    private static final String DEFAULT_SUCESS_MESSAGE = "SUCESS";

    public static Result getSucessResult(){
        return new Result()
                .setCode(ResultCode.SUCESS)
                .setMessage(DEFAULT_SUCESS_MESSAGE);
    }

    public static Result getSucessResult(Object data){
        return new Result()
                .setCode(ResultCode.SUCESS)
                .setMessage(DEFAULT_SUCESS_MESSAGE)
                .setData(data);
    }

    public static Result getFailResult(String message){
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
}
