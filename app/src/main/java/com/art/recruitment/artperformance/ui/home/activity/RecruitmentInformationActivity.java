package com.art.recruitment.artperformance.ui.home.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.home.ApplyBean;
import com.art.recruitment.artperformance.bean.home.RecruitmentInforBean;
import com.art.recruitment.artperformance.ui.home.adapter.ImageAdapter;
import com.art.recruitment.artperformance.ui.home.contract.RecruitmentInformaContract;
import com.art.recruitment.artperformance.ui.home.presenter.RecruitmentInformaPresenter;
import com.art.recruitment.artperformance.ui.mine.activity.ChatActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineDataActivity;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.utils.Defaultcontent;
import com.art.recruitment.artperformance.utils.ShareUtils;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.artperformance.view.TagCloudView;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 招募详情页面
 */
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
    private String recruitmentId;
    private String home_name;
    private Dialog shareDialog;
    private String shareTitle;
    private String chat_username;
    private ImageAdapter adapter;
    private List<String> list = new ArrayList<>();
    private String publisherName;//发布人昵称
    private String publisherAvatar;//发布人头像
    //    private int home_id;

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

        recruitmentId = getIntent().getStringExtra("recruitmentId");
        home_name = getIntent().getStringExtra("home_name");
//        home_id = getIntent().getIntExtra("home_id", -1);

        Logger.d("recruitmentId::" + recruitmentId + ",home_name::" + home_name);
        mPresenter.recuitDetail(recruitmentId);

        initButtonClick();

    }

    private void initButtonClick() {

        //返回
        RxView.
                clicks(mReturnImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });

        //分享
        RxView.
                clicks(mShareImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        startShare("recruitment/" + recruitmentId + "/share");
//                        Intent intent = new Intent(RecruitmentInformationActivity.this, UserAgreementActivity.class);
//                        intent.putExtra("web", "recruitment/" + recruitmentId + "/share");
//                        startActivity(intent);
                    }
                });

        //立即报名
        RxView.
                clicks(mSignUpImmediately).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        signUpImmediately();
                    }
                });

        //发起聊天  沟通
        RxView.
                clicks(mCommunicateConstraintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        EaseUser easeUser = new EaseUser(chat_username);
                        easeUser.setAvatar(publisherAvatar);
                        easeUser.setNickname(publisherName);
                        EaseUserUtils.contactList.put(chat_username, easeUser);
                        EaseUserUtils.save2sp();

                        Intent chat = new Intent(RecruitmentInformationActivity.this, ChatActivity.class);
                        chat.putExtra(EaseConstant.EXTRA_USER_ID, chat_username);  //对方账号
                        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE); //单聊模式
                        startActivity(chat);
                    }
                });

    }


    /**
     * 分享
     *
     * @param subUrl
     */
    private void startShare(String subUrl) {

        View inflate = View.inflate(this, R.layout.dialog_mine_share, null);
        TextView mCleanTextview = inflate.findViewById(R.id.mine_share_clean_textview);
        ConstraintLayout mWechatConstraintLayout = inflate.findViewById(R.id.share_wechat_constraintLayout);
        ConstraintLayout mCircleFriendsConstraintLayout = inflate.findViewById(R.id.share_circle_of_friends_constraintLayout);
        ConstraintLayout mQQZoneConstraintLayout = inflate.findViewById(R.id.share_qq_zone_constraintLayout);
        ConstraintLayout mQQConstraintLayout = inflate.findViewById(R.id.share_qq_constraintLayout);

        shareDialog = DialogWrapper.
                customViewDialog().
                context(this).
                contentView(inflate).
                cancelable(false, false).
                build();

        Window window = shareDialog.getWindow();
        window.setWindowAnimations(R.style.mystyle);

        shareDialog.show();

        mCleanTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideDialog();
            }
        });

        final String shareUrl = Api.HTTP_URL + subUrl;

        Logger.d("shareTitle::" + shareTitle);

        mWechatConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(RecruitmentInformationActivity.this, shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN
                );
                hideDialog();
            }
        });

        mCircleFriendsConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(RecruitmentInformationActivity.this, shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN_CIRCLE
                );
                hideDialog();
            }
        });

        mQQZoneConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(RecruitmentInformationActivity.this, shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.QZONE
                );
                hideDialog();
            }
        });

        mQQConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(RecruitmentInformationActivity.this, shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.QQ
                );
                hideDialog();
            }
        });


    }

    private void hideDialog() {
        if (shareDialog != null)
            shareDialog.cancel();
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {

        if (!TextUtils.isEmpty(message))
            ToastUtils.showShort(message);
    }

    @Override
    public void returnRecruitInforBean(RecruitmentInforBean.DataBean bean) {

        listSuccess(bean);

        for (int i = 0; i < bean.getApplyUsers().size(); i++) {
            list.add(bean.getApplyUsers().get(i).getApplyUserAvatar());
        }
        adapter = new ImageAdapter(this, list);
        mGridview.setAdapter(adapter);
    }

    @Override
    public void returnApplyBean(ApplyBean.DataBean bean) {
        ToastUtils.showShort("报名成功");

        //报名成功 添加头像
        String headUrl = SPUtils.getInstance().getString(BaseConfig.BaseSPKey.HEAD_PIC_URL);
        Logger.d("头像地址::" + headUrl);
        list.add(headUrl);
        if (adapter != null)
            adapter.notifyDataSetChanged();

    }

    private void listSuccess(RecruitmentInforBean.DataBean bean) {
        shareTitle = bean.getTitle();
        mTitleTextview.setText(bean.getTitle());

        RecruitmentInforBean.DataBean.SimpleImInfo simpleImInfo = bean.getSimpleImInfo();

        if (simpleImInfo != null)
            chat_username = simpleImInfo.getUsername();

        publisherAvatar = bean.getPublisherAvatarView();

        publisherName = bean.getPublisherName();
        mPriceTextview.setText((Constant.TYPE_PRICE_FACE.equals(bean.getSalaryType())) ? "面议" : "￥" + bean.getSalary());
        mReleaseTimeTextview.setText("发布时间：" + bean.getReleaseTime());
        mReleasePeopleTextview.setText("发布人：" + bean.getPublisherName());
        mDeadlineTextview.setText("报名截止：" + bean.getApplyEndTime());
        mRequiredPeopleTextview.setText("需要人数：" + bean.getRecruitNumber() + "人");
        mWorkTimeTextview.setText("工作时间：" + bean.getWorkingHours() + "天");
        mCollectionPlaceTextview.setText("集合地点：" + bean.getGatheringAddress());
        mSetTimeTextview.setText("集合时间：" + bean.getGatheringTime());
        if (!TextUtils.isEmpty(bean.getOtherRequirement())) {
            mOtherRequirementsTextview.setText(bean.getOtherRequirement());
        } else {
            mOtherConstraintLayout.setVisibility(View.GONE);
        }
        List<String> tags = new ArrayList<>();
        if (bean.getLabelList() != null)
            tags.addAll(bean.getLabelList());
        mFlowlayout.setTags(tags);

    }

    private void signUpImmediately() {

//        //判断是否完善了所以信息
//        if (!isImproveInformation()) {
//            Logger.d("本地判断资料未完成");
//            perfectInformation();
//            return;
//        }

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
                mPresenter.apply(recruitmentId);
            }
        });
    }

//    /**
//     * 是否完善了全部资料
//     * 通过是否保存了微信号 进行判断
//     *
//     * @return true 完善， false未完善
//     */
//    private boolean isImproveInformation() {
//
//        String weChat = (String) SaveUtils.get(this, MyInfoSave.WECHAT, "");
//        return !TextUtils.isEmpty(weChat);
//
//    }

    private void perfectInformation() {

        View inflate = View.inflate(this, R.layout.dialog_perfect_information, null);
        TextView mDetermineTextview = inflate.findViewById(R.id.release_perfect_determine_textview);
        dialog = DialogWrapper.
                customViewDialog().
                context(this).
                contentView(inflate).
                cancelable(true, true).
                build();

        dialog.show();

        mDetermineTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完善个人 资料
                Intent m = new Intent(RecruitmentInformationActivity.this, MineDataActivity.class);
                //应聘，报名
                m.putExtra(Constant.EDITOR_TYPE, Constant.EDITOR_TYPE_APPLY);
                startActivity(m);

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
        });

    }

}
