package com.temp.permission.util;

import com.temp.permission.consts.ErrorConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/7.
 */
public class FormatUtil {

    public static Map success() {
        return FormatUtil.success(null);
    }

    public static Map success(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", ErrorConst.NO_EXCEPTION);
        map.put("message", ErrorConst.messageMap.get(ErrorConst.NO_EXCEPTION));
        if (data != null)
            map.put("data", data);
        else
            map.put("data", new ArrayList<>());

        return map;
    }

    public static Map fail() {
        return FormatUtil.fail(null);
    }

    public static Map fail(Map data) {
        return FormatUtil.fail(ErrorConst.SYSTEM_EXCEPTION, data);
    }

    public static Map fail(int errorCode, Map data) {
        return FormatUtil.fail(errorCode, ErrorConst.messageMap.get(errorCode), data);
    }

    public static Map fail(int errorCode, String message, Map data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", errorCode);
        map.put("message", message);
        map.put("data", data);
        return map;
    }

    public static Map formatList(List list, Integer total) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return map;
    }
}
