package com.art.recruitment.artperformance.utils;

import com.blankj.utilcode.util.StringUtils;

import java.math.BigDecimal;


public class ArithUtil {
    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商 v1/v2
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    public static int divInt(double v1, double v2) {
        return (int) div(v1, v2, 0);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商 v1/v2
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static int round(double v) {
        return (int) round(v, 0);
    }

    /**
     * 强制保留两位小数，并进位
     * <ul>
     *      <li>100.00000001 进位后 100.01<li/>
     *      <li>100.001 进位后 100.01<li/>
     *      <li>100.01 进位后 100.01<li/>
     *      <li>100.1 进位后 100.10<li/>
     * <ul/>
     */
    public static String formatTwoDecimalPlacesForCarry(String value) {
        if (StringUtils.isTrimEmpty(value)){
            return "0.00";
        }
        if (value.startsWith(".")) {
            value = "0" + value;
        }
        if (value.contains(".")) {
            String[] mSplit = value.split("\\.");
            if (mSplit != null && mSplit.length > 1) {
                if (mSplit[1].length() > 2) {
                    String mSec = mSplit[1];
                    BigDecimal originValue = new BigDecimal(value);
                    String doubleValue = mSplit[0] + "." + mSec.substring(0, 2);
                    BigDecimal splitValue = new BigDecimal(doubleValue);
                    //如果原值大于截取后的，需要进位
                    if (originValue.compareTo(splitValue) == 1){
                        BigDecimal d2 = new BigDecimal("0.01");
                        return splitValue.add(d2).toPlainString();
                    }else {
                        return doubleValue;
                    }
                } else if (mSplit[1].length() == 2) {
                    return value;
                } else if (mSplit[1].length() == 1) {
                    return mSplit[0] + "." + mSplit[1] + "0";
                } else if (mSplit[1].length() == 0) {
                    return mSplit[0] + "." + "00";
                } else {
                    return value;
                }
            } else {
                return value + "00";
            }
        } else {
            return value + ".00";
        }
    }

    /**
     * 强制保留两位小数，并舍位
     */
    public static String formatTwoDecimalPlacesForRound(String value) {
        if (StringUtils.isTrimEmpty(value)){
            return "0.00";
        }
        if (value.startsWith(".")) {
            value = "0" + value;
        }
        if (value.contains(".")) {
            String[] mSplit = value.split("\\.");
            if (mSplit != null && mSplit.length > 1) {
                if (mSplit[1].length() > 2) {
                    String mSec = mSplit[1];
                    String s = mSec.substring(0, 2);
                    return mSplit[0] + "." + s;
                } else if (mSplit[1].length() == 2) {
                    return value;
                } else if (mSplit[1].length() == 1) {
                    return mSplit[0] + "." + mSplit[1] + "0";
                } else if (mSplit[1].length() == 0) {
                    return mSplit[0] + "." + "00";
                } else {
                    return value;
                }
            } else {
                return value + "00";
            }
        } else {
            return value + ".00";
        }
    }
}
