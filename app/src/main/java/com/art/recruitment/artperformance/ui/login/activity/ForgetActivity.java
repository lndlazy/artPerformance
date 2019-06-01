package com.art.recruitment.artperformance.ui.login.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;

public class ForgetActivity extends BaseActivity {

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }
}
