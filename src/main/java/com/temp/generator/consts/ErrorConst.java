package com.temp.generator.consts;

import java.util.HashMap;
import java.util.Map;

public class ErrorConst {

    public static int SUCCESS = 0;
    public static int SYSTEM_EXCEPTION = 1;
    public static int LOGIN_EXCEPTION = 2;

    //登录异常
    public static int LOGIN_FAIL = 10000;
    public static int LOGIN_USER_DISABLE = 10001;
    public static int LOGIN_PARAMS_ERROR = 10002;

    //参数异常errorCode
    public static int PARAMS_EXCEPTION = 20000;

    //异常message
    public static Map<Integer, String> messageMap = new HashMap<>();
    static {
        messageMap.put(SUCCESS, "成功");
        messageMap.put(SYSTEM_EXCEPTION, "系统异常，请稍后重试！");
        messageMap.put(LOGIN_EXCEPTION, "需要登录！");
        messageMap.put(PARAMS_EXCEPTION, "你的请求参数异常！");
        messageMap.put(LOGIN_FAIL, "登录失败!");
        messageMap.put(LOGIN_USER_DISABLE, "账户被禁用，登录失败，请联系管理员!");
        messageMap.put(LOGIN_PARAMS_ERROR, "用户名或密码输入错误，登录失败!");
    }
}
