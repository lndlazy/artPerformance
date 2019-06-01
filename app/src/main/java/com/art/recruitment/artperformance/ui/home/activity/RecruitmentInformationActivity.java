package com.art.recruitment.artperformance.ui.home.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.home.ApplyBean;
import com.art.recruitment.artperformance.bean.home.RecruitmentInforBean;
import com.art.recruitment.artperformance.ui.home.adapter.ImageAdapter;
import com.art.recruitment.artperformance.ui.home.contract.RecruitmentInformaContract;
import com.art.recruitment.artperformance.ui.home.presenter.RecruitmentInformaPresenter;
import com.art.recruitment.artperformance.ui.login.activity.UserAgreementActivity;
import com.art.recruitment.artperformance.ui.mine.activity.ChatActivity;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.artperformance.view.TagCloudView;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class RecruitmentInformationActivity extends BaseActivity<RecruitmentInformaPresenter> implements RecruitmentInformaContract {

    @BindView(R.id.details_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.details_share_imageview)
    ImageView mShareImageview;
    @BindView(R.id.home_title)
    ConstraintLayout mTitle;
    @BindView(R.id.details_title_textview)
    TextView mTitleTextview;
    @BindView(R.id.details_price_textview)
    TextView mPriceTextview;
    @BindView(R.id.details_flowlayout)
    TagCloudView mFlowlayout;
    @BindView(R.id.details_release_people_textview)
    TextView mReleasePeopleTextview;
    @BindView(R.id.details_release_time_textview)
    TextView mReleaseTimeTextview;
    @BindView(R.id.details_deadline_textview)
    TextView mDeadlineTextview;
    @BindView(R.id.details_required_people_textview)
    TextView mRequiredPeopleTextview;
    @BindView(R.id.details_work_time_textview)
    TextView mWorkTimeTextview;
    @BindView(R.id.details_collection_place_textview)
    TextView mCollectionPlaceTextview;
    @BindView(R.id.details_set_time_textview)
    TextView mSetTimeTextview;
    @BindView(R.id.details_other_textview)
    TextView mOtherTextview;
    @BindView(R.id.details_other_requirements_textview)
    TextView mOtherRequirementsTextview;
    @BindView(R.id.home_communicate_imageview)
    ImageView mCommunicateImageview;
    @BindView(R.id.home_communicate_textview)
    TextView mCommunicateTextview;
    @BindView(R.id.home_bottom_button)
    ConstraintLayout mBottomButton;
    @BindView(R.id.details_sign_up_immediately)
    TextView mSignUpImmediately;
    @BindView(R.id.details_other_constraintLayout)
    ConstraintLayout mOtherConstraintLayout;
    @BindView(R.id.recruitment_information_gridview)
    GridView mGridview;
    @BindView(R.id.home_communicate_constraintLayout)
    ConstraintLayout mCommunicateConstraintLayout;
    private Dialog dialog;
    private int position;
    private String home_name;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recruitment_information;
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
        position = getIntent().getIntExtra("position", 0);
        home_name = getIntent().getStringExtra("home_name");
        mPresenter.recuitDetail(position);

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
                clicks(mShareImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(RecruitmentInformationActivity.this, UserAgreementActivity.class);
                        intent.putExtra("web", "recruitment/" + position + "/share");
                        startActivity(intent);
                    }
                });

        RxView.
                clicks(mSignUpImmediately).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        signUpImmediately();
                    }
                });

        RxView.
                clicks(mCommunicateConstraintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent chat = new Intent(RecruitmentInformationActivity.this, ChatActivity.class);
                        chat.putExtra(EaseConstant.EXTRA_USER_ID, home_name);  //对方账号
                        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊模式
                        startActivity(chat);
                    }
                });


    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {

        if (message != null) {
            ToastUtils.showShort(message);
        }

    }

    @Override
    public void returnRecruitInforBean(RecruitmentInforBean.DataBean bean) {

        listSuccess(bean);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < bean.getApplyUsers().size(); i++) {
            list.add(bean.getApplyUsers().get(i).getApplyUserAvatar());
        }
        /*list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559129291054&di=6e18db49281a814fd1c5846769caa9ff&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201608%2F23%2F20160823151123_S2eAf.jpeg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559129291054&di=6e18db49281a814fd1c5846769caa9ff&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201608%2F23%2F20160823151123_S2eAf.jpeg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559129291054&di=6e18db49281a814fd1c5846769caa9ff&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201608%2F23%2F20160823151123_S2eAf.jpeg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559129291054&di=6e18db49281a814fd1c5846769caa9ff&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201608%2F23%2F20160823151123_S2eAf.jpeg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559129291054&di=6e18db49281a814fd1c5846769caa9ff&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201608%2F23%2F20160823151123_S2eAf.jpeg");
*/
        ImageAdapter adapter = new ImageAdapter(this, list);
        mGridview.setAdapter(adapter);
    }

    @Override
    public void returnApplyBean(ApplyBean.DataBean bean) {
        ToastUtils.showShort("报名成功");
    }

    private void listSuccess(RecruitmentInforBean.DataBean bean) {
        mTitleTextview.setText(bean.getTitle());
        mPriceTextview.setText("￥" + bean.getSalary());
        mReleaseTimeTextview.setText("发布时间：" + bean.getReleaseTime());
        mReleasePeopleTextview.setText("发布人：" + bean.getPublisherName());
        mDeadlineTextview.setText("报名截止：" + bean.getApplyEndTime());
        mRequiredPeopleTextview.setText("需要人数：" + bean.getRecruitNumber());
        mWorkTimeTextview.setText("工作时间：" + bean.getWorkingHours());
        mCollectionPlaceTextview.setText("集合地点：" + bean.getGatheringAddress());
        mSetTimeTextview.setText("集合时间：" + bean.getGatheringTime());
        if (!TextUtils.isEmpty(bean.getOtherRequirement())) {
            mOtherRequirementsTextview.setText(bean.getOtherRequirement());
        } else {
            mOtherConstraintLayout.setVisibility(View.GONE);
        }
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < bean.getLabelList().size(); i++) {
            tags.add(bean.getLabelList().get(i));
        }
        mFlowlayout.setTags(tags);
    }

    private void signUpImmediately() {
        View inflate = View.inflate(this, R.layout.dialog_recruit_details, null);
        TextView mCleanTextview = inflate.findViewById(R.id.recruit_details_clean_textview);
        TextView mDetermineTextview = inflate.findViewById(R.id.recruit_details_determine_textview);
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
                mPresenter.apply(position);
            }
        });
    }

}
