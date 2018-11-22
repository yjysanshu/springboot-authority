package com.temp.permission.util;

public class ConsoleUtil {
    public static void formatPrint() {
        formatPrint("NULL");
    }

    public static void formatPrint(Object info) {
        System.out.println("[INFO] *****************************************************");
        System.out.println("[INFO]  " + info);
        System.out.println("[INFO] *****************************************************");
        System.out.println();
    }
}
