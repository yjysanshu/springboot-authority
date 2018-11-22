package com.temp.permission.util;

import com.temp.permission.consts.CharacterConst;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class CodeUtil {

    /**
     * 创建一个唯一的ID
     * @return string
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll(CharacterConst.CHARACTER_LINE, CharacterConst.CHARACTER_NULL);
    }

    public static String createRepairOrderCode() {
        return "RO" + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + (new Random(10000).nextInt());
    }
}
