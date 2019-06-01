package com.art.recruitment.artperformance.ui.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.FeedbackBean;
import com.art.recruitment.artperformance.bean.mine.FeedbackRequest;
import com.art.recruitment.artperformance.ui.mine.contract.FeedbackContract;
import com.art.recruitment.artperformance.ui.mine.presenter.FeedbackPresenter;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class FeedbackActivity extends BaseActivity<FeedbackPresenter> implements FeedbackContract {

    @BindView(R.id.mine_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.mine_feedback_submission_edittext)
    EditText mSubmissionEdittext;
    @BindView(R.id.mine_feedback_submission_textview)
    TextView mSubmissionTextview;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
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
        initEdittextListener();
    }

    private void initEdittextListener() {
        mSubmissionEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    mSubmissionTextview.setEnabled(true);
                } else {
                    mSubmissionTextview.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                clicks(mSubmissionTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        FeedbackRequest request = new FeedbackRequest();
                        request.setContent(mSubmissionEdittext.getText().toString().trim());
                        Gson gson = new Gson();
                        String jsonStr = gson.toJson(request);
                        mPresenter.applyList(jsonStr);
                    }
                });
    }

    @Override
    public void returnFeedbackBean(FeedbackBean.DataBean bean) {
        ToastUtils.showShort("提交成功");
        finish();
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null){
            ToastUtils.showShort(message);
        }
    }
}
