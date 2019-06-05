package com.art.recruitment.artperformance.ui.mine.activity;

import android.support.annotation.Nullable;
import android.os.Bundle;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;

public class MineSignUpActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_sign_up;
    }

    @Override
    protected IToolbar getIToolbar() {
        return null;
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
