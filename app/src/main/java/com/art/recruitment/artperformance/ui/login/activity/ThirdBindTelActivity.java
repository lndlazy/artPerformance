package com.art.recruitment.artperformance.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.bean.login.VerificationCodeBean;
import com.art.recruitment.artperformance.bean.login.VerificationCodeRequest;
import com.art.recruitment.artperformance.ui.MainActivity;
import com.art.recruitment.artperformance.ui.login.ThirdBindRequestEntry;
import com.art.recruitment.artperformance.ui.login.ThirdBindResultEntry;
import com.art.recruitment.artperformance.ui.login.contract.ThirdBindContract;
import com.art.recruitment.artperformance.ui.login.presenter.ThirdBindPresenter;
import com.art.recruitment.artperformance.utils.TimeCountDownHelper;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.utils.FormatUtil;
import com.art.recruitment.common.view.Global;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 第三方账号 绑定手机号页面
 * Created by linaidao on 2019/6/15.
 */

public class ThirdBindTelActivity extends BaseActivity<ThirdBindPresenter> implements ThirdBindContract {


    @BindView(R.id.register_return_imageview)
    ImageView mReturnImageview;

    @BindView(R.id.register_phone_edittext)
    EditText mPhoneEdittext;

    //验证码
    @BindView(R.id.register_verification_code_editext)
    EditText mVerificationCodeEditext;

    @BindView(R.id.register_checkbox)
    CheckBox mCheckbox;

    //用户协议
    @BindView(R.id.register_agreement_textview)
    TextView mAgreementTextview;

    @BindView(R.id.register_textview)
    TextView mRegisterTextview;

    //验证码
    @BindView(R.id.register_verification_code_textview)
    TextView mVerificationCodeTextview;

    private TimeCountDownHelper mTimeCountDownHelper;
    private long mCountDown;
    private String openId;
    private String socialAccount;
    private String socialType;


    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_thirdbind_tel;
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

        initButtonClick();

        //第三方openId
        openId = getIntent().getStringExtra("openId");
        //第三方账号 微信号或者qq号
        socialAccount = getIntent().getStringExtra("socialAccount");
        //第三方类型 wechat  or   qq
        socialType = getIntent().getStringExtra("socialType");

    }


    private void initButtonClick() {

        //协议
        RxView.
                clicks(mAgreementTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(mContext, UserAgreementActivity.class);
                        intent.putExtra("web", "register/agreement");
                        startActivity(intent);
                    }
                });


        RxView.
                clicks(mReturnImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });

        //点击获取验证码
        RxView.
                clicks(mVerificationCodeTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        if (!mPhoneEdittext.getText().toString().trim().isEmpty()) {
                            if (mPhoneEdittext.getText().toString().trim().length() == Global.PHONE_NUMBER_SIZE) {
                                VerificationCodeRequest verificationCodeRequest = new VerificationCodeRequest();
                                verificationCodeRequest.setMobilePhone(mPhoneEdittext.getText().toString().trim());

                                verificationCodeRequest.setType("3");
                                Gson gson = new Gson();
                                String codeStr = gson.toJson(verificationCodeRequest);
                                mPresenter.getVerificationCode(codeStr);

                                countDown();
                            } else {
                                ToastUtils.showShort("手机号错误");
                            }
                        } else {
                            ToastUtils.showShort("手机号不能为空");
                        }

                    }
                });

        //确认绑定
        RxView.
                clicks(mRegisterTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        initBind();
                    }
                });

    }

    private void initBind() {
        String mPhone = mPhoneEdittext.getText().toString().trim();
        String mVerification = mVerificationCodeEditext.getText().toString().trim();

        if (!TextUtils.isEmpty(mPhone) && mPhone.length() == Global.PHONE_NUMBER_SIZE && FormatUtil.isMobileNO(mPhone)) {
            if (!TextUtils.isEmpty(mVerification)) {
                if (mCheckbox.isChecked()) {

                    ThirdBindRequestEntry requestEntry = new ThirdBindRequestEntry();
                    requestEntry.setMobileNo(mPhone);
                    requestEntry.setOpenId(openId);
                    //第三方账号， 微信或者qq号
                    requestEntry.setSocialAccount(socialAccount);
                    requestEntry.setVerificationCode(mVerification);
                    Gson gson = new Gson();
                    String bindStr = gson.toJson(requestEntry);
                    mPresenter.thirdBind(socialType, bindStr);

                } else {
                    ToastUtils.showShort("请勾选协议");
                }

            } else {
                ToastUtils.showShort("验证码不能为空");
            }
        } else {
            ToastUtils.showShort("手机号错误");
        }
    }


    private void countDown() {
        if (mTimeCountDownHelper == null) {
            mTimeCountDownHelper = new TimeCountDownHelper();
            mTimeCountDownHelper.
                    countInterval(1000).
                    timeSpan(60).
                    timeUnit(TimeUnit.MILLISECONDS).
                    listener(new TimeCountDownHelper.TimeCountListener() {
                        @Override
                        public void onTimeCountDown(long time) {
                            mCountDown = time;
                            if (mVerificationCodeTextview != null) {
                                mVerificationCodeTextview.setText(time + " s" + "重新发送");
                                mVerificationCodeTextview.setEnabled(false);
                                if (mCountDown == 0) {
                                    mVerificationCodeTextview.setText("重新发送");
                                    mCountDown = 60;
                                    mVerificationCodeTextview.setEnabled(true);
                                }
                            }
                        }
                    });
        }
        mTimeCountDownHelper.start();
    }

    @Override
    public void returnVerificationCodeBean(VerificationCodeBean.DataBean verificationCodeBean) {
        ToastUtils.showShort("验证码发送成功");
    }

    //绑定成功返回的数据
    @Override
    public void returnBindInfo(ThirdBindResultEntry.DataBean bindResult) {

        if (bindResult.getTokenInfo() == null)
            return;

        SPUtils.getInstance().put(BaseConfig.BaseSPKey.TOKEN, bindResult.getTokenInfo().getToken());

        if (!TextUtils.isEmpty(bindResult.getTokenInfo().getAvatar()))
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.HEAD_PIC_URL, bindResult.getTokenInfo().getAvatar());

        SPUtils.getInstance().put(BaseConfig.BaseSPKey.SEX, bindResult.getTokenInfo().getGender());

        SPUtils.getInstance().put(BaseConfig.BaseSPKey.ID, bindResult.getTokenInfo().getId());

        if (!TextUtils.isEmpty(bindResult.getTokenInfo().getName()))
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.USER_NAME, bindResult.getTokenInfo().getName());

        setLoginValue(bindResult);
        mPresenter.imUser();

    }

    @Override
    public void returnImUserBean(ImUserBean.DataBean bean) {

        String hxusername = bean.getUsername();
        if (!TextUtils.isEmpty(hxusername))
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.HX_USERNAME, hxusername);


        EMClient.getInstance().login(bean.getUsername(), bean.getPassword(), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                startActivity(MainActivity.class);
                finish();
                ToastUtils.showShort("登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                startActivity(MainActivity.class);
                finish();
//                ToastUtils.showShort("登录聊天服务器失败！");
            }
        });
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

    private void setLoginValue(ThirdBindResultEntry.DataBean bindResult) {

        SPUtils.getInstance().put(BaseConfig.BaseSPKey.ID, bindResult.getTokenInfo().getId());

        if (!TextUtils.isEmpty(bindResult.getTokenInfo().getName()))
        SPUtils.getInstance().put(BaseConfig.BaseSPKey.USER_NAME, bindResult.getTokenInfo().getName());

    }


}
