package com.jibres.android.utility;

import java.text.DecimalFormat;

public class changeNumber {
    public static String toEn(String Numbers) {
        String[][] mChars = new String[][]{
                {"0", "۰"},
                {"1", "۱"},
                {"2", "۲"},
                {"3", "۳"},
                {"4", "۴"},
                {"5", "۵"},
                {"6", "۶"},
                {"7", "۷"},
                {"8", "۸"},
                {"9", "۹"}
        };
        for (String[] num : mChars) {

            Numbers = Numbers.replace(num[1], num[0]);

        }

        return Numbers;
    }

    public static String toFa(String Numbers) {
        String[][] mChars = new String[][]{
                {"0", "۰"},
                {"1", "۱"},
                {"2", "۲"},
                {"3", "۳"},
                {"4", "۴"},
                {"5", "۵"},
                {"6", "۶"},
                {"7", "۷"},
                {"8", "۸"},
                {"9", "۹"}
        };
        for (String[] num : mChars) {

            Numbers = Numbers.replace(num[0], num[1]);

        }

        return Numbers;
    }

    public static String splitDigits(int number) {
        return new DecimalFormat("###,###,###").format(number);
    }
}
