package com.temp.common.consts;

public enum Constants {

    SUCCESS(0, "成功"),
    FAIL(-1, "失败"),
    SYSTEM_ERROR(1, "系统异常，请稍后重试"),
    LOGIN_ERROR(2, "需要登录"),

    //登录异常
    LOGIN_FAIL(10000, "账户登录失败"),
    LOGIN_USER_DISABLE(10001, "登录失败"),
    LOGIN_PARAMS_ERROR(10002, "用户名或密码输入错误，登录失败"),

    //参数异常errorCode
    PARAMS_ERROR(20000, "参数异常，请重新尝试"),

    //数据库操作异常
    DB_DELETE_ERROR(30001, "没有删除数据，删除失败");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Constants(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
