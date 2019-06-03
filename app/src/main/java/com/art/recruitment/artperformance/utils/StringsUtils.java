package com.art.recruitment.artperformance.utils;

import android.content.Context;
import android.net.Uri;

import com.blankj.utilcode.util.StringUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

    //获取文件内容 md5码 作为文件名
    public static String getMd5Name(Uri uri, Context context) {

        String fileName = null;
        try {
//            File file = new File(pathName);
            InputStream strem = context.getContentResolver().openInputStream(uri);
//            InputStream inputStream = new FileInputStream(file);
            fileName = FileMd5Util.digest(strem);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        Logger.d("文件md5::" + fileName);
        return fileName;
    }

    public static boolean is2Int(String str) {

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
