package com.temp.permission.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HeaderUtil {

    /**
     * 获取头信息
     * @param headerName
     * @return
     */
    public static String getHeader(HttpServletRequest request, String headerName) {
        return request.getHeader(headerName);
    }

    /**
     * 获取所有的头信息
     * @param request
     * @return
     */
    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
