package com.art.recruitment.artperformance.ui.dynamic.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentsBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicDetailBean;
import com.art.recruitment.artperformance.ui.dynamic.adapter.DynamicAdapter;
import com.art.recruitment.artperformance.ui.dynamic.adapter.DynamicCommentsAdapter;
import com.art.recruitment.artperformance.ui.dynamic.contract.DynamicDataContract;
import com.art.recruitment.artperformance.ui.dynamic.presenter.DynamicDataPresenter;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.artperformance.view.ExpandableTextView;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class DynamicDetailActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_dynamic_detail;
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

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    /*@BindView(R.id.dynamic_datail_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.dynamic_detail_head_imageview)
    ImageView mHeadImageview;
    @BindView(R.id.dynamic_detail_name_textview)
    TextView mNameTextview;
    @BindView(R.id.dynamic_detail_time_textview)
    TextView mTimeTextview;
    @BindView(R.id.dynamic_detail_expandableTextView)
    ExpandableTextView mExpandableTextView;
    @BindView(R.id.dynamic_detail_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.dynamic_give_imageview)
    ImageView mGiveImageview;
    @BindView(R.id.dynamic_detail_give_constraintLayout)
    ConstraintLayout mGiveConstraintLayout;
    @BindView(R.id.dynamic_comment_imageview)
    ImageView mCommentImageview;
    @BindView(R.id.dynamic_detail_comment_constraintLayout)
    ConstraintLayout mCommentConstraintLayout;
    @BindView(R.id.dynamic_share_imageview)
    ImageView mShareImageview;
    @BindView(R.id.dynamic_detail_share_constraintLayout)
    ConstraintLayout mShareConstraintLayout;
    @BindView(R.id.dynamic_detail_comment_recyclerView)
    RecyclerView mCommentRecyclerView;
    @BindView(R.id.dynamic_detail_send_edittext)
    EditText mSendEdittext;
    @BindView(R.id.dynamic_detail_send_textview)
    TextView mSendTextview;
    @BindView(R.id.dynamic_give_textview)
    TextView mGiveTextview;
    @BindView(R.id.dynamic_comment_textview)
    TextView mCommentTextview;
    @BindView(R.id.dynamic_detail_delete_imageview)
    ImageView mDeleteImageview;
    private int dynamic_id;
    private Dialog dialog;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dynamic_detail;
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
        dynamic_id = getIntent().getIntExtra("dynamic_id", 0);
        mPresenter.dynamicDetail(dynamic_id);

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
                clicks(mDeleteImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        deleteDialog();
                    }
                });
    }

    private void deleteDialog() {
        View inflate = View.inflate(this, R.layout.dialog_delete_dynamic, null);
        TextView mCleanTextview = inflate.findViewById(R.id.dialog_delete_clean_textview);
        TextView mDetermineTextview = inflate.findViewById(R.id.dialog_delete_determine_textview);

        dialog = DialogWrapper.
                customViewDialog().
                context(this).
                contentView(inflate).
                cancelable(false, false).
                build();

        dialog.show();

        mCleanTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        mDetermineTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

                mPresenter.deleteDynamic(dynamic_id);
            }
        });
    }

    @Override
    public void returnDynamicDataBean(DynamicDetailBean.DataBean bean) {
        mPresenter.dynamicComments(dynamic_id, 0, 20, "desc");

        Glide.with(mContext).load(bean.getPublisherAvatar()).into(mHeadImageview);
        mNameTextview.setText(bean.getPublisherName());
        mTimeTextview.setText(bean.getCreateTime());
        mExpandableTextView.setText(bean.getContent());
        mGiveTextview.setText(bean.getLikes() + "");
        mCommentTextview.setText(bean.getCommentNumber() + "");

    }

    @Override
    public void returnDynamicCommentsBean(DynamicCommentsBean.DataBean bean) {
    }

    @Override
    public void returnDeleteDynamicBean(String bean) {
        ToastUtils.showShort(bean.toString());
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }*/
}
