package com.temp.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChartUtil {
    public static String[] COLOR_SELECT = {
            "#EBBBA7", "#DF8244", "#4CA9CE", "#E5323E", "#EAFABA", "#EBF9A7", "#DF8244", "#A5E23E", "#B5E27E", "#A5D25E",
            "#053E3E", "#4CA0CE", "#AAACCC", "#CCCE4D", "#B5A20E", "#DF8244",
            "#EBBBA7", "#DF8244", "#4CA9CE", "#E5323E", "#EAFABA", "#EBF9A7", "#DF8244", "#A5E23E", "#B5E27E", "#A5D25E",
            "#053E3E", "#4CA0CE", "#AAACCC", "#CCCE4D", "#B5A20E", "#DF8244",
            "#EBBBA7", "#DF8244", "#4CA9CE", "#E5323E", "#EAFABA", "#EBF9A7", "#DF8244", "#A5E23E", "#B5E27E", "#A5D25E",
            "#053E3E", "#4CA0CE", "#AAACCC", "#CCCE4D", "#B5A20E", "#DF8244",
    };

    public static List<String> getColor(int num)  {
        return new ArrayList<>(Arrays.asList(ChartUtil.COLOR_SELECT).subList(0, num));
    }
}
