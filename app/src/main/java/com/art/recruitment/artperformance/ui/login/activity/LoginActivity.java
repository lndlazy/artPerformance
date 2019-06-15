package com.art.recruitment.artperformance.ui.login.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.bean.login.TokenBean;
import com.art.recruitment.artperformance.bean.login.TokenRequest;
import com.art.recruitment.artperformance.ui.MainActivity;
import com.art.recruitment.artperformance.ui.login.SocialLoginRequestVO;
import com.art.recruitment.artperformance.ui.login.ThirdLoginEntry;
import com.art.recruitment.artperformance.ui.login.contract.LoginContract;
import com.art.recruitment.artperformance.ui.login.presenter.LoginPresenter;
import com.art.recruitment.artperformance.utils.Constant;
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
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract {

    @BindView(R.id.login_phone_edittext)
    EditText mPhoneEdittext;
    @BindView(R.id.login_password_edittext)
    EditText mPasswordEdittext;
    @BindView(R.id.login_forget_password_textview)
    TextView mForgetPasswordTextview;
    @BindView(R.id.login_sign_textview)
    TextView mSignTextview;
    @BindView(R.id.login_register_account_textview)
    TextView mRegisterAccountTextview;
    @BindView(R.id.login_checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.login_qq_imageview)
    ImageView mQqImageview;
    @BindView(R.id.login_wx_imageview)
    ImageView mWxImageview;
    @BindView(R.id.login_user_agreement_textview)
    TextView mUserAgreementTextview;
    private boolean mIsPhoneOk = false;
    private boolean mIsPasswordOk = false;
    private int mIsThatPage;
    private String thirdLoginName;//三方登录的name
    private String thirdLoginGender;//三方登录的性别
    String socialType = "";//三方登录的类型  wx or qq
    private String openId;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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

        initEditTextLister();
        initButtonClick();
    }

    /**
     * 输入框的监听
     */
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
                if (s.length() > 0) {
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

    }

    /**
     * 按钮的点击状态
     */
    private void setButtonStatus() {
        if (mIsPhoneOk && mIsPasswordOk) {
            mSignTextview.setEnabled(true);
        } else {
            mSignTextview.setEnabled(false);
        }
    }

    /**
     * 按钮的点击事件
     */
    private void initButtonClick() {
        //用户协议
        RxView.
                clicks(mUserAgreementTextview).
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
                clicks(mRegisterAccountTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mIsThatPage = 1;
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        intent.putExtra("mIsThatPage", mIsThatPage);
                        startActivity(intent);
                    }
                });

        RxView.
                clicks(mForgetPasswordTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mIsThatPage = 2;
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        intent.putExtra("mIsThatPage", mIsThatPage);
                        startActivity(intent);
                    }
                });

        RxView.
                clicks(mSignTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
/*                        mIsThatPage = 3;
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        intent.putExtra("mIsThatPage", mIsThatPage);
                        startActivity(intent);*/
                        loginGo();

//                        startActivity(MainActivity.class);
                    }
                });

        RxView.
                clicks(mQqImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        authorization(SHARE_MEDIA.QQ);
                    }
                });

        RxView.
                clicks(mWxImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        authorization(SHARE_MEDIA.WEIXIN);
                    }
                });
    }

    private void loginGo() {
        String mPhone = mPhoneEdittext.getText().toString().trim();
        String mPassword = mPasswordEdittext.getText().toString().trim();

        if (!TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mPassword)) {
            if (FormatUtil.isMobileNO(mPhone)) {
                if (mCheckbox.isChecked()) {
                    closeInoutDecorView(LoginActivity.this);
                    TokenRequest tokenRequest = new TokenRequest();
                    tokenRequest.setPassword(mPassword);
                    tokenRequest.setUsername(mPhone);
//                token.setPassword("password");
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(tokenRequest);
                    mPresenter.getToken(jsonStr);
                } else {
                    ToastUtils.showShort("请勾选协议");
                }
            }
        } else {
            ToastUtils.showShort("手机号或密码错误");
        }
    }

    @Override
    public void returnTokenBean(TokenBean.DataBean tokenBean) {
        setLoginValue(tokenBean);
        mPresenter.imUser();
    }

    @Override
    public void returnImUserBean(ImUserBean.DataBean bean) {
        EMClient.getInstance().login(bean.getUsername(), bean.getPassword(), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                startActivity(MainActivity.class);
                ToastUtils.showShort("登录聊天服务器成功！");
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                startActivity(MainActivity.class);
                finish();
                ToastUtils.showShort("登录聊天服务器失败！" );

                Logger.d("登录聊天服务器失败:::" + message);
            }
        });
    }

    //三方登录返回的数据
    @Override
    public void returnThirdLogin(ThirdLoginEntry.DataBean thirdLoginBean) {

        SPUtils.getInstance().put(BaseConfig.BaseSPKey.TOKEN, thirdLoginBean.getToken());
        SPUtils.getInstance().put(BaseConfig.BaseSPKey.ID, thirdLoginBean.getId());
        SPUtils.getInstance().put(BaseConfig.BaseSPKey.USER_NAME, thirdLoginBean.getUsername());
//        SPUtils.getInstance().put(BaseConfig.BaseSPKey.LOGIN_TIME, "2");
        mPresenter.imUser();

    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {

            if (errorCode == -501) {
                //未绑定  进行绑定
                Intent bindIntent = new Intent(LoginActivity.this, ThirdBindTelActivity.class);
                bindIntent.putExtra("openId", openId);
                bindIntent.putExtra("socialAccount", thirdLoginName);
                bindIntent.putExtra("socialType", socialType);
                startActivity(bindIntent);
            }else

            ToastUtils.showShort(message);
        }
    }

    private void setLoginValue(TokenBean.DataBean tokenBean) {
        SPUtils.getInstance().put(BaseConfig.BaseSPKey.TOKEN, tokenBean.getToken());
        SPUtils.getInstance().put(BaseConfig.BaseSPKey.ID, tokenBean.getId());
        SPUtils.getInstance().put(BaseConfig.BaseSPKey.USER_NAME, tokenBean.getUsername());
//        SPUtils.getInstance().put(BaseConfig.BaseSPKey.LOGIN_TIME, "2");
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                //微博没有
                openId = map.get("openid");
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                thirdLoginName = map.get("name");
                thirdLoginGender = map.get("gender");
                String iconurl = map.get("iconurl");

                Logger.d("name=" + thirdLoginName + ",gender=" + thirdLoginGender);

                switch (share_media) {

                    case WEIXIN:
                        socialType = Constant.THIRD_LOGIN_WX;
                        break;
                    case QQ:
                        socialType = Constant.THIRD_LOGIN_QQ;
                       break;

                }


                SocialLoginRequestVO socialLoginRequestVO = new SocialLoginRequestVO();
                socialLoginRequestVO.setOpenId(openId);
                Gson gson = new Gson();
                String codeStr = gson.toJson(socialLoginRequestVO);

                //拿到信息去请求登录接口。。。
                mPresenter.authenticationLogin(socialType, codeStr);

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 关闭软件盘
     */
    public static void closeInoutDecorView(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}