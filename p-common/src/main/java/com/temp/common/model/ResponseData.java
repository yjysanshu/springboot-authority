package com.temp.common.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.temp.common.consts.Constants;

public class ResponseData {
    private int code;
    private String message;
    private Object data;

    public ResponseData() {}

    public ResponseData(Constants constants) {
        this.code = constants.getCode();
        this.message = constants.getMsg();
    }

    public ResponseData(Constants cons, Object data) {
        this.code = cons.getCode();
        this.message = cons.getMsg();
        this.data = data;
    }

    public ResponseData(Constants cons, String message, Object data) {
        this.code = cons.getCode();
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJSONString() {
        JSONObject json = (JSONObject) JSON.toJSON(this);
        return json.toJSONString();
    }
}
