package com.art.recruitment.artperformance.ui.mine.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MineFecruitmentActivity extends BaseActivity {

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_fecruitment;
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
        EventBus.getDefault().register(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Boolean isRefush) {
    /* Do something */

//        Logger.d("isRefush::" + isRefush);

        finish();

//        if (commAdapter != null)
//            commAdapter.notifyItemChanged(editorPosition);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }



}
