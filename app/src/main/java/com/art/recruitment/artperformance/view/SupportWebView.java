package com.art.recruitment.artperformance.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @company: Coolbit
 * created by {Android-Dev02} on 2018/4/30 17:39
 * @desc:
 */

public class SupportWebView extends WebView {
    private Context mContext;

    public SupportWebView(Context context) {
        super(context);
        mContext = context;
        disableZoomController();
    }

    public SupportWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        disableZoomController();
    }

    public SupportWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        disableZoomController();
    }

    // 使得控制按钮不可用
    @SuppressLint("SetJavaScriptEnabled")
    private void disableZoomController() {
        // 如果不添加下边这句，可能会报：
        // [INFO:CONSOLE(2)]
        // "Uncaught TypeError: Cannot call method 'getItem' of null", source:
        // http://www.baidu.com/ (2)
        // 原因：http://wazai.net/2969/android-webview-error-uncaught-typeerror-cannot-call-method-getitem-of-null-at
        this.getSettings().setDomStorageEnabled(true); // 开启DOM storage API 功能

        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setDefaultTextEncodingName("utf-8");
        this.getSettings().setLoadWithOverviewMode(true);
        this.getSettings().setSupportZoom(false);
        this.getSettings().setSaveFormData(true);
        this.getSettings().setAllowFileAccess(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        this.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 设置缓存模式
        this.getSettings().setAppCacheEnabled(true);
        this.getSettings().setDatabaseEnabled(true); // 开启database storage API功能
        this.setHorizontalScrollBarEnabled(false);
    }
}
