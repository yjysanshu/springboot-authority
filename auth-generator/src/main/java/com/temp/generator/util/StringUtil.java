package com.temp.generator.util;

public class StringUtil {

    /**
     * 格式化数据字段，转成驼峰命名
     * @param fieldName 字段名称
     * @return 把字段转为驼峰命名
     */
    public static String formatField(String fieldName) {
        int index = fieldName.indexOf('_');
        if (index == -1) {
            return fieldName;
        }
        fieldName = fieldName.substring(0, index) +
                fieldName.substring(index + 1, index + 2).toUpperCase() +
                fieldName.substring(index + 2);

        return formatField(fieldName);
    }

    /**
     * 首字母大写
     * @param fieldName 字段名称
     * @return 首字母大写的字符串
     */
    public static String firstCharacterToUpper(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 首字母小写
     * @param fieldName 字段名称
     * @return 首字母小写的字符串
     */
    public static String firstCharacterToLower(String fieldName) {
        return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
    }

    /**
     * 用split分割数组成字符串
     * @param array 数组
     * @param split 分割的字符串
     * @return string
     */
    public static String join(Object[] array, String split) {
        StringBuilder format = new StringBuilder();
        for (Object ignored : array) {
            format.append(split).append("%s");
        }
        return String.format(format.toString(), (Object[]) array);
    }

    /**
     * 去除一个前缀
     * @param str 字符串
     * @return string
     */
    public static String deleteOnePrefix(String str) {
        int index = str.indexOf('_');
        if (index == -1) {
            return str;
        }
        return str.substring(index + 1);
    }
}
