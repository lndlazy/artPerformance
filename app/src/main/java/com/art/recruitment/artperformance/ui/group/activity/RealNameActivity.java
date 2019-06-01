package com.art.recruitment.artperformance.ui.group.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.RealNameBean;
import com.art.recruitment.artperformance.bean.group.RealNameRequest;
import com.art.recruitment.artperformance.ui.group.contract.RealNameContract;
import com.art.recruitment.artperformance.ui.group.presenter.RealNamePresenter;
import com.art.recruitment.artperformance.ui.login.activity.LoginActivity;
import com.art.recruitment.artperformance.ui.login.activity.RegisterActivity;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.view.Global;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 实名认证
 */
public class RealNameActivity extends BaseActivity<RealNamePresenter> implements RealNameContract {

    @BindView(R.id.group_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.group_real_name_edittext)
    EditText mRealNameEdittext;
    @BindView(R.id.group_ID_number_editext)
    EditText mIDNumberEditext;
    @BindView(R.id.group_submission_textview)
    TextView mSubmissionrTextview;
    private boolean mIsRealNameOk = false;
    private boolean mIsIDNumberOk = false;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_real_name;
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
        initEditTextListener();
        initButtonClick();
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
                clicks(mSubmissionrTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        String mRealName = mRealNameEdittext.getText().toString().trim();
                        String mIDNumber = mIDNumberEditext.getText().toString().trim();
                        RealNameRequest realNameRequest = new RealNameRequest();
                        realNameRequest.setIdCard(mIDNumber);
                        realNameRequest.setRealName(mRealName);
                        Gson gson = new Gson();
                        String codeStr = gson.toJson(realNameRequest);
                        mPresenter.realName(codeStr);
                    }
                });
    }

    private void initEditTextListener() {
        mRealNameEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mIsRealNameOk = true;
                } else {
                    mIsRealNameOk = false;
                }
                setButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mIDNumberEditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == Global.ID_NUMBER_SIZE) {
                    mIsIDNumberOk = true;
                } else {
                    mIsIDNumberOk = false;
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

        if (mIsRealNameOk && mIsIDNumberOk){
            mSubmissionrTextview.setEnabled(true);
        } else {
            mSubmissionrTextview.setEnabled(false);
        }
    }

    @Override
    public void returnRealNameBean(RealNameBean.DataBean bean) {
        ToastUtils.showShort("实名认证成功");
        finish();
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null){
            ToastUtils.showShort(message);
        }
    }
}
