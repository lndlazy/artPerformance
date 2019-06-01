package com.art.recruitment.artperformance.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.zhihu.matisse.engine.ImageEngine;

/**
 * Created by Administrator on 2018/1/22 0022.
 *
 * @Desc： Matisse图片选择库的图片加载引擎
 */

public class MatisseGlideEngine implements ImageEngine {
    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        GlideUtils.loadImage(context,placeholder,uri,imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {

    }


    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        GlideUtils.loadImage(context,uri,imageView);
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {

    }


    @Override
    public boolean supportAnimatedGif() {
        return false;
    }

}
