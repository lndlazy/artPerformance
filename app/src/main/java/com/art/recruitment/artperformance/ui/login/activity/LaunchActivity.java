package com.art.recruitment.artperformance.ui.login.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.login.StartUpBean;
import com.art.recruitment.artperformance.ui.MainActivity;
import com.art.recruitment.artperformance.ui.login.contract.LaunchContract;
import com.art.recruitment.artperformance.ui.login.presenter.LaunchPresenter;
import com.art.recruitment.artperformance.utils.ResourceUtils;
import com.art.recruitment.artperformance.utils.TimeCountDownHelper;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class LaunchActivity extends BaseActivity<LaunchPresenter> implements LaunchContract {

    @BindView(R.id.skip_textview)
    TextView skipTextView;
    @BindView(R.id.launch_constraintLayout)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.launch_imageview)
    ImageView launchImageview;
    private TimeCountDownHelper mTimeCountDownHelper;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        initTime();

        mPresenter.startUP();

        initButton();
    }

    public void initTime() {
        mTimeCountDownHelper = new TimeCountDownHelper();
        mTimeCountDownHelper
                .countInterval(1000)
                .timeUnit(TimeUnit.MILLISECONDS)
                .timeSpan(3)
                .listener(new TimeCountDownHelper.TimeCountListener() {
                    @Override
                    public void onTimeCountDown(final long time) {
                        skipTextView.setText("跳过" + time);
                        skipTextView.setBackground(ResourceUtils.getDrawable(R.drawable.shape_launch_textview_background));
                        if (time == 0) {
                            if (SPUtils.getInstance().getString(BaseConfig.BaseSPKey.LOGIN_TIME).equals("2")) {
                                startActivity(MainActivity.class);
                                finish();

                            } else {
                                SPUtils.getInstance().put(BaseConfig.BaseSPKey.LOGIN_TIME, "2");
                                startActivity(LoginActivity.class);
                                finish();
                            }
                        }
                    }
                }).start();
    }

    public void initButton() {
        RxView.
                clicks(skipTextView).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mTimeCountDownHelper.release();

                        if (SPUtils.getInstance().getString(BaseConfig.BaseSPKey.LOGIN_TIME).equals("2")) {
                            startActivity(MainActivity.class);
                            finish();

                        } else {
                            startActivity(LoginActivity.class);
                            finish();
                        }
                    }
                });
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void returnStartUpBean(StartUpBean.DataBean bean) {
        try {
//            if (isDestroyed())
                Glide.with(this).load(bean.getImageUrl()).into(launchImageview);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

}
