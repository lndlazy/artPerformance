package com.art.recruitment.artperformance.utils;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;


public class ResourceUtils {

    private static Application mApplication = null;

    /**
     * 初始化
     * @param app
     */
    public static void init(Application app){
        mApplication = app;
    }

    public static Resources getResources(){
        return mApplication.getResources();
    }

    public static String getString(@StringRes int stringRes){
        return getResources().getString(stringRes);
    }

    public static String[] getStringArray(@ArrayRes int stringResArray){
        return getResources().getStringArray(stringResArray);
    }

    public static Drawable getDrawable(@DrawableRes int drawableRes){
        return getResources().getDrawable(drawableRes);
    }

    public static int getColor(@ColorRes int colorRes){
        return getResources().getColor(colorRes);
    }
}
