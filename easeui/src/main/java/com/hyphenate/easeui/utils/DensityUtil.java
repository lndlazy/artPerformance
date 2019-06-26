package com.hyphenate.easeui.utils;

import android.content.Context;

/**
 * Created by linaidao on 2019/6/26.
 */

public class DensityUtil {

    /**
     * dip转像素
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 像素转dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
