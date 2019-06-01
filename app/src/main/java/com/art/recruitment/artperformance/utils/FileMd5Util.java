package com.art.recruitment.artperformance.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * The type FileMd5Util.
 *
 * @author Miracle.XJH
 * @date 2019年03月01日 19时37分48秒
 */
public class FileMd5Util {

    /**
     * Digest string.
     *
     * @param is the is
     * @return the string
     * @author Miracle.XJH
     * @date 2019年03月01日 19时37分48秒
     */
    public static String digest(InputStream is) {
        String digest = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] buf = new byte[8192];
            int len;
            while ((len = is.read(buf)) != -1) {
                messageDigest.update(buf, 0, len);
            }
            digest = bytesToHexString(messageDigest.digest());
            messageDigest.reset();

        } catch (Exception e) {
            Log.e("文件MD5获取失败", e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e("文件MD5获取流关闭失败", e.getMessage());
                }
            }
        }
        return digest;
    }

    /**
     * Bytes to hex string string.
     *
     * @param src the src
     * @return the string
     * @author Miracle.XJH
     * @date 2018年11月28日 16时37分06秒
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
