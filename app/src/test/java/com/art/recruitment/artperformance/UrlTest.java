package com.art.recruitment.artperformance;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UrlTest {
    @Test
    public void testSpecialUrl() {
        URL url = null;
        try {
            String mContent = URLEncoder.encode("PUT%0A%0Aimage%2Fjpeg%0AMon%2C+27+May+2019+10%3A50%3A36+GMT%0A%2Fyizhan-app-dev%2Ffilem12", "UTF-8");
            url = new URL("http://47.94.8.204:18080/oss/signature?content=" + mContent);
            System.out.println(url.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Authorization", "4fe80efd771a4dad94518116354c21e4");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
                                    /*PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                                    // 发送请求参数
                                    printWriter.write(content);
                                    printWriter.flush();*/
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                bos.write(arr, 0, len);
                bos.flush();
            }
            bos.close();
            System.out.println(bos.toString("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
