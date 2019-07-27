package com.temp.permission.model.response;

import com.temp.permission.util.JSONUtil;

import java.io.IOException;
import java.util.Map;

public class Result {
    private Integer code;
    private String message;
    private Map<String, Object> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() throws IOException {
        return JSONUtil.mapToJson(this.data);
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String toJson() {
        try {
            return "{\"code\": " + code +
                    ", \"message\": \"" + message +
                    "\", \"data\": " + this.getData() +
                    "}";
        } catch (IOException e) {
            return "{\"code\": " + code +
                    ", \"message\": \"" + message +
                    "\"}";
        }
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
