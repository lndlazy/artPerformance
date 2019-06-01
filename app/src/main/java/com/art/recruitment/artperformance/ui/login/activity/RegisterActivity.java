package com.art.recruitment.artperformance.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.bean.login.RegisterBean;
import com.art.recruitment.artperformance.bean.login.RegisterRequest;
import com.art.recruitment.artperformance.bean.login.ResetPasswordBean;
import com.art.recruitment.artperformance.bean.login.ResetPasswordRequest;
import com.art.recruitment.artperformance.bean.login.VerificationCodeBean;
import com.art.recruitment.artperformance.bean.login.VerificationCodeRequest;
import com.art.recruitment.artperformance.ui.MainActivity;
import com.art.recruitment.artperformance.ui.login.contract.RegisterContract;
import com.art.recruitment.artperformance.ui.login.presenter.RegisterPresenter;
import com.art.recruitment.artperformance.utils.TimeCountDownHelper;
import com.art.recruitment.common.base.BasePresenter;
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
import com.hyphenate.exceptions.HyphenateException;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract {

    @BindView(R.id.register_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.register_logo_textview)
    TextView mLogoTextview;
    @BindView(R.id.register_phone_edittext)
    EditText mPhoneEdittext;
    @BindView(R.id.register_password_edittext)
    EditText mPasswordEdittext;
    @BindView(R.id.register_verification_code_editext)
    EditText mVerificationCodeEditext;
    @BindView(R.id.register_checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.register_agreement_textview)
    TextView mAgreementTextview;
    @BindView(R.id.register_textview)
    TextView mRegisterTextview;
    @BindView(R.id.register_password_view)
    View mPasswordView;
    @BindView(R.id.register_verification_code_textview)
    TextView mVerificationCodeTextview;

    private int mIsThatPage;
    private TimeCountDownHelper mTimeCountDownHelper;
    private long mCountDown;
    private boolean mIsPhoneOk;
    private boolean mIsPasswordOk;
    private boolean mIsVerificationOk;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
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
        mIsThatPage = getIntent().getIntExtra("mIsThatPage", 0);

        initRegisterOrForget();
        initEditTextLister();
        initButtonClick();

    }

    private void initEditTextLister() {

        mPhoneEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == Global.PHONE_NUMBER_SIZE) {
                    mIsPhoneOk = true;
                } else {
                    mIsPhoneOk = false;
                }
                setButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPasswordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    mIsPasswordOk = true;
                } else {
                    mIsPasswordOk = false;
                }
                setButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mVerificationCodeEditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4){
                    mIsVerificationOk = true;
                } else {
                    mIsVerificationOk = false;
                }
                setButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     *按钮的点击状态
     */
    private void setButtonStatus() {

        if (mIsThatPage == 3) {
            if (mIsPhoneOk && mIsVerificationOk){
                mRegisterTextview.setEnabled(true);
            } else {
                mRegisterTextview.setEnabled(false);
            }
        } else {
            if (mIsPhoneOk && mIsPasswordOk && mIsVerificationOk){
                mRegisterTextview.setEnabled(true);
            } else {
                mRegisterTextview.setEnabled(false);
            }
        }
    }

    private void initButtonClick() {

        RxView.
                clicks(mReturnImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });

        RxView.
                clicks(mVerificationCodeTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        if (!mPhoneEdittext.getText().toString().trim().isEmpty()){
                            if (mPhoneEdittext.getText().toString().trim().length() == Global.PHONE_NUMBER_SIZE){
                                VerificationCodeRequest verificationCodeRequest = new VerificationCodeRequest();
                                verificationCodeRequest.setMobilePhone(mPhoneEdittext.getText().toString().trim());

                                if (mIsThatPage == 1) {
                                    verificationCodeRequest.setType("1");
                                    Gson gson = new Gson();
                                    String codeStr = gson.toJson(verificationCodeRequest);
                                    mPresenter.getVerificationCode(codeStr);
                                } else if (mIsThatPage == 2) {
                                    verificationCodeRequest.setType("2");
                                    Gson gson = new Gson();
                                    String codeStr = gson.toJson(verificationCodeRequest);
                                    mPresenter.getVerificationCode(codeStr);
                                }

                                countDown();
                            } else {
                                ToastUtils.showShort("手机号错误");
                            }
                        } else {
                            ToastUtils.showShort("手机号不能为空");
                        }

                    }
                });

        RxView.
                clicks(mRegisterTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        initRegister();
                    }
                });

    }

    private void initRegister() {
        String mPhone = mPhoneEdittext.getText().toString().trim();
        String mVerification = mVerificationCodeEditext.getText().toString().trim();
        String mPassword = mPasswordEdittext.getText().toString().trim();

        if (!TextUtils.isEmpty(mPhone) && mPhone.length() == Global.PHONE_NUMBER_SIZE && FormatUtil.isMobileNO(mPhone)) {
            if (!TextUtils.isEmpty(mVerification)) {
                if (mCheckbox.isChecked()){
                    if (mIsThatPage == 1) {
                        RegisterRequest registerRequest = new RegisterRequest();
                        registerRequest.setMobileNo(mPhone);
                        registerRequest.setPassword(mPassword);
                        registerRequest.setVerificationCode(mVerification);
                        Gson gson = new Gson();
                        String registerStr = gson.toJson(registerRequest);
                        mPresenter.registerAccount(registerStr);
                    } else if (mIsThatPage == 2) {
                        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
                        resetPasswordRequest.setMobilePhone(mPhone);
                        resetPasswordRequest.setNewPassword(mPassword);
                        resetPasswordRequest.setVerificationCode(mVerification);
                        Gson gson = new Gson();
                        String registerStr = gson.toJson(resetPasswordRequest);

                        mPresenter.resetPassword(registerStr);
                    }else if (mIsThatPage == 3) {
                        ToastUtils.showShort("绑定手机");

                    }
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

    private void initRegisterOrForget() {
        if (mIsThatPage == 1) {
            mLogoTextview.setText(getString(R.string.register_new_user_register));
            mPhoneEdittext.setHint(getString(R.string.login_phone_hint));
            mPasswordEdittext.setHint(R.string.login_password_hint);
            mRegisterTextview.setText(R.string.register_register);
            mPasswordEdittext.setVisibility(View.VISIBLE);
            mPasswordView.setVisibility(View.VISIBLE);
        } else if (mIsThatPage == 2) {
            mLogoTextview.setText(getString(R.string.forget_reset_password));
            mPhoneEdittext.setHint(getString(R.string.login_phone_hint));
            mPasswordEdittext.setHint(R.string.forget_new_password_hint);
            mRegisterTextview.setText(R.string.forget_complete);
            mPasswordEdittext.setVisibility(View.VISIBLE);
            mPasswordView.setVisibility(View.VISIBLE);
        } else if (mIsThatPage == 3) {
            mLogoTextview.setText(getString(R.string.bind_phone));
            mPhoneEdittext.setHint(getString(R.string.enter_mobile_phone_number));
            mRegisterTextview.setText(R.string.determine);
            mPasswordEdittext.setVisibility(View.GONE);
            mPasswordView.setVisibility(View.GONE);
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

    @Override
    public void returnRegisterBean(RegisterBean.DataBean registerBean) {
        setLoginValue(registerBean);
        mPresenter.imUser();
    }

    @Override
    public void returnResetPasswordBean(ResetPasswordBean.DataBean resetPasswordBean) {
        finish();
    }

    @Override
    public void returnImUserBean(ImUserBean.DataBean bean) {
        EMClient.getInstance().login(mPhoneEdittext.getText().toString().trim(),mPasswordEdittext.getText().toString().trim(),new EMCallBack() {//回调
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
                ToastUtils.showShort( "登录聊天服务器失败！");
            }
        });
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

    private void setLoginValue(RegisterBean.DataBean registerBean) {
//        SPUtils.getInstance().put(BaseConfig.BaseSPKey.NAME, registerBean.getData().getName());
        SPUtils.getInstance().put(BaseConfig.BaseSPKey.TOKEN, registerBean.getToken());
        SPUtils.getInstance().put(BaseConfig.BaseSPKey.ID, registerBean.getId());
        SPUtils.getInstance().put(BaseConfig.BaseSPKey.USER_NAME, registerBean.getUsername());
//        SPUtils.getInstance().put(BaseConfig.BaseSPKey.TELE_PHONE, registerBean.getData().getTelephone());
//        SPUtils.getInstance().put(BaseConfig.BaseSPKey.PHOTO, registerBean.getData().getPhoto());

    }
}
