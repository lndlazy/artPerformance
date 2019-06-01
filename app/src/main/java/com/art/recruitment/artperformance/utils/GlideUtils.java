package com.art.recruitment.artperformance.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.art.recruitment.common.utils.GlideOptionUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * @company: Coolbit
 * created by {Android-Dev02} on 2018/4/9 20:04
 *
 * @desc: 
 */

public class GlideUtils {

    private GlideUtils() {
    }

    public static void loadImage(Context context,String url,ImageView imageView){
//        applyDefaultRequestOptions(GlideOptionUtils.getSimpleRequestOptions()).

        Glide.
                with(context).
                load(url).
                apply(GlideOptionUtils.getSimpleRequestOptions()).
                into(imageView);
    }

    public static void loadImage(Context context,Uri uri,ImageView imageView){
        Glide.
                with(context).
//                applyDefaultRequestOptions(GlideOptionUtils.getSimpleRequestOptions()).
                load(uri).
                apply(GlideOptionUtils.getSimpleRequestOptions()).
                into(imageView);
    }

    public static void loadImage(Context context, Drawable placeHolder, Uri url, ImageView imageView){
//        applyDefaultRequestOptions(GlideOptionUtils.getPlaceHolderRequestOptions(placeHolder)).

        Glide.
                with(context).
                load(url).
                apply(GlideOptionUtils.getPlaceHolderRequestOptions(placeHolder)).
                into(imageView);
    }

    public static void loadImage(Context context, Drawable placeHolder, String url, ImageView imageView){
        Glide.
                with(context).
                applyDefaultRequestOptions(GlideOptionUtils.getPlaceHolderRequestOptions(placeHolder)).
                load(url).
                into(imageView);
    }

    public static void loadImage(Context context, @DrawableRes int placeHolder, @DrawableRes int error, String url, ImageView imageView){
        Glide.
                with(context).
                applyDefaultRequestOptions(GlideOptionUtils.getPlaceHolderErrorRequestOptions(placeHolder, error)).
                load(url).
                into(imageView);
    }


}
