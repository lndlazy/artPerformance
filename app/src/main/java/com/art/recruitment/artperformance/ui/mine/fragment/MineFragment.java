package com.art.recruitment.artperformance.ui.mine.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.MineBean;
import com.art.recruitment.artperformance.ui.dynamic.activity.ReleaseDynamicActivity;
import com.art.recruitment.artperformance.ui.group.activity.MineRecruitActivity;
import com.art.recruitment.artperformance.ui.login.activity.LoginActivity;
import com.art.recruitment.artperformance.ui.mine.MyInfoSave;
import com.art.recruitment.artperformance.ui.mine.activity.AboutUsActivity;
import com.art.recruitment.artperformance.ui.mine.activity.ChatActivity;
import com.art.recruitment.artperformance.ui.mine.activity.ChatListActivity;
import com.art.recruitment.artperformance.ui.mine.activity.FeedbackActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineDataActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineDynamicActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineFecruitmentActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineSignUpActivity;
import com.art.recruitment.artperformance.ui.mine.contract.MineContract;
import com.art.recruitment.artperformance.ui.mine.presenter.MinePresenter;
import com.art.recruitment.artperformance.utils.Defaultcontent;
import com.art.recruitment.artperformance.utils.SaveUtils;
import com.art.recruitment.artperformance.utils.ShareUtils;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.utils.UIUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public class MineFragment extends BaseFragment<MinePresenter, MultiItemEntity> implements MineContract {
    @BindView(R.id.mine_message_imageview)
    ImageView mMessageImageview;
    @BindView(R.id.mine_name_textview)
    TextView mNameTextview;
    @BindView(R.id.mine_editing_personal_data_textview)
    TextView mEditingPersonalDataTextview;
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
    }

    private void initButtonClick() {

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

        RxView.
                clicks(mEditingPersonalDataTextview).
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

        RxView.
                clicks(mRecruitConstrainLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), MineFecruitmentActivity.class));
                    }
                });

        RxView.
                clicks(mShareConstranintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        shareDialog();
                    }
                });

        RxView.
                clicks(mSignUpConstrainLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), MineSignUpActivity.class));
                    }
                });

        RxView.
                clicks(mLogot).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        SPUtils.getInstance().put(BaseConfig.BaseSPKey.TOKEN, "");
                        SPUtils.getInstance().put(BaseConfig.BaseSPKey.ID, "");
                        SPUtils.getInstance().put(BaseConfig.BaseSPKey.USER_NAME, "");
                        SPUtils.getInstance().put(BaseConfig.BaseSPKey.LOGIN_TIME, "");

                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
    }

    private void shareDialog() {
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
                dialog.cancel();
            }
        });

        mWechatConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN
                );
            }
        });

        mCircleFriendsConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN_CIRCLE
                );
            }
        });

        mQQZoneConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.QZONE
                );
            }
        });

        mQQConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.QQ
                );
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void returnMineDataBean(MineBean.DataBean bean) {

        mNameTextview.setText(bean.getUsername());
        mWechatServiceTextview.setText(bean.getWechat());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.login_logo);
        Glide.with(mContext).load(bean.getAvatarView()).apply(options).into(mHeadPortraitImageview);

        if (!TextUtils.isEmpty(bean.getAvatarView()))
        EventBus.getDefault().post(bean.getAvatarView());

        //保存个人资料
        SaveUtils.put(getContext(), MyInfoSave.HEAD_PIC_URL, bean.getAvatar());
        SaveUtils.put(getContext(), MyInfoSave.PHONE_NUM, bean.getTelephone());
        SaveUtils.put(getContext(), MyInfoSave.SEX, bean.getGender());
        SaveUtils.put(getContext(), MyInfoSave.USER_NAME, bean.getUsername());
        SaveUtils.put(getContext(), MyInfoSave.AGE, bean.getAge());

        if (!TextUtils.isEmpty(bean.getWechat()))
        SaveUtils.put(getContext(), MyInfoSave.WECHAT, bean.getWechat());

    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

}