package com.temp.common.util;

import com.temp.common.consts.Constants;
import com.temp.common.model.ResponseData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 格式化返回参数
 */
public class FormatUtil {

    public static ResponseData success() {
        return FormatUtil.success(null);
    }

    public static ResponseData success(Object data) {
        return new ResponseData(Constants.SUCCESS, data);
    }

    public static ResponseData fail() {
        return FormatUtil.fail(null);
    }

    public static ResponseData fail(Object data) {
        return new ResponseData(Constants.FAIL, data);
    }

    public static ResponseData fail(Constants cons, Object data) {
        return new ResponseData(cons, data);
    }

    public static ResponseData fail(Constants cons, String message, Map data) {
        return new ResponseData(cons, message, data);
    }

    public static Map<String, Object> formatList(List list, Integer total) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return map;
    }
}
