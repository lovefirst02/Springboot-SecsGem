package com.example.demo.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Result {

    private int code;

    private String message = "sucess";

    private Object data;

    public int getCode() {
        return code;
    }

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
