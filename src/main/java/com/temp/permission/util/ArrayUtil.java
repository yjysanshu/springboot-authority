package com.temp.permission.util;

public class ArrayUtil {

    /**
     * 查找数组中是否包含当前值
     * @param array 数组
     * @param search 待查找的值
     * @return true-值存在数组中
     */
    public static Boolean contains(String[] array, String search) {
        for (String str : array) {
            if (search.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
