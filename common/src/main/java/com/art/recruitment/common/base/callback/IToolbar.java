package com.art.recruitment.common.base.callback;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.common.base.config.ToolbarConfig;


public interface IToolbar {

    Toolbar getToolbar();

    ToolbarConfig getToolbarConfig();

    String getTitle();

    void getTitleTextView(TextView mTitleTextView);

    void getTitleLeftView(ImageView mImageView);

    Drawable getToolbarRightDrawable();

    void onTitleClicked(String tittle);

    void onRightImageClicked();
}
