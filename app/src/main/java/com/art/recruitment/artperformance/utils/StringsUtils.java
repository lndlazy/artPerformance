package com.art.recruitment.artperformance.utils;

import com.blankj.utilcode.util.StringUtils;

import java.math.BigDecimal;

public class StringsUtils {

    public static String getSecretNo(String no) {
        if (com.blankj.utilcode.util.StringUtils.isTrimEmpty(no)) {
            return "";
        }
        if (no.length() > 3) {
            return "***" + no.substring(no.length() - 3);
        } else {
            return "***" + no;
        }
    }

    public static boolean checkZeroAndNull(String params) {
        if (!StringUtils.isTrimEmpty(params)) {
            BigDecimal zero = new BigDecimal("0.00");
            BigDecimal firstDecimal = new BigDecimal(params);
            if (firstDecimal.compareTo(zero) == 0 || firstDecimal.compareTo(zero) == -1) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
