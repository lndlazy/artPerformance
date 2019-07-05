package com.art.recruitment.artperformance.ui.mine.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.api.ApiUrls;
import com.art.recruitment.artperformance.bean.mine.MineBean;
import com.art.recruitment.artperformance.ui.login.activity.LoginActivity;
import com.art.recruitment.artperformance.ui.mine.activity.AboutUsActivity;
import com.art.recruitment.artperformance.ui.mine.activity.ChatListActivity;
import com.art.recruitment.artperformance.ui.mine.activity.FeedbackActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineDataActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineDynamicActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineFecruitmentActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineSignUpActivity;
import com.art.recruitment.artperformance.ui.mine.contract.MineContract;
import com.art.recruitment.artperformance.ui.mine.presenter.MinePresenter;
import com.art.recruitment.artperformance.utils.Defaultcontent;
import com.art.recruitment.artperformance.utils.ShareUtils;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MineFragment extends BaseFragment<MinePresenter, MultiItemEntity> implements MineContract {

    @BindView(R.id.mine_message_imageview)
    ImageView mMessageImageview;
    @BindView(R.id.mine_name_textview)
    TextView mNameTextview;

    @BindView(R.id.mine_editing_personal_data_textview)
    TextView mEditingPersonalDataTextview;

    @BindView(R.id.ivNewMsg)
    ImageView ivNewMsg;

    @BindView(R.id.constraintView)
    ConstraintLayout constraintView;

    @BindView(R.id.mine_share_cards_textview)
    TextView mShareCardsTextview;
    @BindView(R.id.mine_recruit_constrainLayout)
    ConstraintLayout mRecruitConstrainLayout;
    @BindView(R.id.mine_dynamic_constrainLayout)
    ConstraintLayout mDynamicConstrainLayout;
    @BindView(R.id.mine_sign_up_constrainLayout)
    ConstraintLayout mSignUpConstrainLayout;
    @BindView(R.id.mine_share_constranintLayout)
    ConstraintLayout mShareConstranintLayout;
    @BindView(R.id.mine_about_us_constranintLayout)
    ConstraintLayout mAboutUsConstranintLayout;
    @BindView(R.id.mine_feedback_constranintLayout)
    ConstraintLayout mFeedbackConstranintLayout;
    @BindView(R.id.mine_wechat_service_constranintLayout)
    ConstraintLayout mWechatServiceConstranintLayout;
    @BindView(R.id.mine_logot)
    TextView mLogot;
    @BindView(R.id.mine_head_portrait_imageview)
    ImageView mHeadPortraitImageview;
    @BindView(R.id.mine_edit_imageview)
    ImageView mEditImageview;
    @BindView(R.id.mine_recruit_imageview)
    ImageView mRecruitImageview;
    @BindView(R.id.mine_dynamic_imageview)
    ImageView mDynamicImageview;
    @BindView(R.id.mine_sign_up_imageview)
    ImageView mSignUpImageview;
    @BindView(R.id.mine_share_imageview)
    ImageView mShareImageview;
    @BindView(R.id.mine_about_us_imageview)
    ImageView mAboutUsImageview;
    @BindView(R.id.mine_feedback_imageview)
    ImageView mFeedbackImageview;
    @BindView(R.id.mine_wechat_service_imageview)
    ImageView mWechatServiceImageview;
    @BindView(R.id.mine_wechat_service_textview)
    TextView mWechatServiceTextview;
    private Dialog dialog;
    private String shareUrl;//分享地址
    private String shareTitle;//分享的标题


    //普通分享
    private static final int SHARE_TYPE_COMMON = 1;
    //名片分享
    private static final int SHARE_TYPE_BUSINESS_CARD = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return null;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return null;
    }

    @Override
    protected BaseRecyclerViewAdapter getRecyclerViewAdapter() {
        return null;
    }

    @Override
    protected boolean enableAdapterLoadMore() {
        return false;
    }

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initView() {
        mPresenter.getPersonalData();

        initButtonClick();

        EventBus.getDefault().register(this);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
            setRedPoint();
    }


    private void setRedPoint() {
        int unreadMessageCount = EMClient.getInstance().chatManager().getUnreadMessageCount();
//        com.orhanobut.logger.Logger.d("未读消息个数：：" + unreadMessageCount);
        ivNewMsg.setVisibility(unreadMessageCount > 0 ? View.VISIBLE : View.INVISIBLE);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(List<EMMessage> messages) {
//        for (int i = 0; i < messages.size(); i++) {
//
//            EMMessage emMessage = messages.get(i);
//
//            String from = emMessage.getFrom();
//
//            EMMessageBody body = emMessage.getBody();
//
//            String s = body.toString();
//
//            com.orhanobut.logger.Logger.d("首页获取消息  消息内容::" + s + ",from:" + from + ",to:" + emMessage.getTo());
//
//        }

//        if (messages.size() > 0) {
        //提示消息到达
        ivNewMsg.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void initButtonClick() {

        RxView.
                clicks(mShareCardsTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        //分享名片
//                        shareDialog(Api.HTTP_URL + "actors/" + (SPUtils.getInstance().getInt(BaseConfig.BaseSPKey.ID)) + "/share");
                        shareDialog(SHARE_TYPE_BUSINESS_CARD);

                    }
                });

        RxView.
                clicks(mAboutUsConstranintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), AboutUsActivity.class));
                    }
                });

        RxView.
                clicks(mMessageImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), ChatListActivity.class));

                    }
                });

        //编辑个人资料
        RxView.
                clicks(constraintView).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), MineDataActivity.class));
                    }
                });

        RxView.
                clicks(mFeedbackConstranintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), FeedbackActivity.class));
                    }
                });

        RxView.
                clicks(mDynamicConstrainLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), MineDynamicActivity.class));
                    }
                });

        //我的招募
        RxView.
                clicks(mRecruitConstrainLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), MineFecruitmentActivity.class));
                    }
                });

        //分享
        RxView.
                clicks(mShareConstranintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
//                        shareDialog(Api.HTTP_URL + ApiUrls.SHARE_APP);
                        shareDialog(SHARE_TYPE_COMMON);
                    }
                });

        //我的报名
        RxView.
                clicks(mSignUpConstrainLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), MineSignUpActivity.class));
                    }
                });

        //退出登录
        RxView.
                clicks(mLogot).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        logout();
                    }
                });


        //聊天列表
        RxView.
                clicks(mMessageImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), ChatListActivity.class));
                    }
                });

    }

    private void logout() {

        SPUtils.getInstance().clear();

        //退出环信
        EMClient.getInstance().logout(true);

        startActivity(new Intent(getContext(), LoginActivity.class));
        if (getActivity() != null)
            getActivity().finish();

    }

    private void shareDialog(int shareType) {
        View inflate = View.inflate(getContext(), R.layout.dialog_mine_share, null);
        TextView mCleanTextview = inflate.findViewById(R.id.mine_share_clean_textview);
        ConstraintLayout mWechatConstraintLayout = inflate.findViewById(R.id.share_wechat_constraintLayout);
        ConstraintLayout mCircleFriendsConstraintLayout = inflate.findViewById(R.id.share_circle_of_friends_constraintLayout);
        ConstraintLayout mQQZoneConstraintLayout = inflate.findViewById(R.id.share_qq_zone_constraintLayout);
        ConstraintLayout mQQConstraintLayout = inflate.findViewById(R.id.share_qq_constraintLayout);

        dialog = DialogWrapper.
                customViewDialog().
                context(getContext()).
                contentView(inflate).
                cancelable(false, false).
                build();

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.mystyle);

        dialog.show();

        mCleanTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideDialog();
            }
        });

        switch (shareType) {

            case SHARE_TYPE_BUSINESS_CARD://名片分享
                shareUrl = Api.HTTP_URL + "actors/" + (SPUtils.getInstance().getInt(BaseConfig.BaseSPKey.ID)) + "/share";
                shareTitle = ShareUtils.ACTOR_SHARE_TITLE;
                break;

            case SHARE_TYPE_COMMON://普通分享
                shareUrl = Api.HTTP_URL + ApiUrls.SHARE_APP;
                shareTitle = ShareUtils.APP_SHARE_TITLE;
                break;

        }


        mWechatConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN
                );
                hideDialog();
            }
        });

        mCircleFriendsConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN_CIRCLE
                );
                hideDialog();
            }
        });

        mQQZoneConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.QZONE
                );
                hideDialog();
            }
        });

        mQQConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.QQ
                );
                hideDialog();
            }
        });
    }

    private void hideDialog() {
        if (dialog != null)
            dialog.cancel();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void returnMineDataBean(MineBean.DataBean bean) {

        mNameTextview.setText(bean.getName());
        mWechatServiceTextview.setText(bean.getWechat());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.login_logo);
        Glide.with(mContext).load(bean.getAvatarView()).apply(options).into(mHeadPortraitImageview);

        if (!TextUtils.isEmpty(bean.getAvatarView()))
            EventBus.getDefault().post(bean.getAvatarView());

        //保存个人资料
        if (!TextUtils.isEmpty(bean.getAvatarView()))
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.HEAD_PIC_URL, bean.getAvatarView());

        if (!TextUtils.isEmpty(bean.getTelephone()))
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.PHONE_NUM, bean.getTelephone());

        if (bean.getGender() != 0)
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.SEX, bean.getGender());

        if (!TextUtils.isEmpty(bean.getName()))
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.USER_NAME, bean.getName());

        if (bean.getAge() != 0)
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.AGE, bean.getAge());

        if (!TextUtils.isEmpty(bean.getWechat()))
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.WECHAT, bean.getWechat());

    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

}