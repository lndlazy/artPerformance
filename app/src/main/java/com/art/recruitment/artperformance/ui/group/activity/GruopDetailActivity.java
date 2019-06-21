package com.art.recruitment.artperformance.ui.group.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.ActorIdBean;
import com.art.recruitment.artperformance.bean.group.ActorLikesBean;
import com.art.recruitment.artperformance.bean.group.GroupDetailBean;
import com.art.recruitment.artperformance.ui.dynamic.activity.PlusImageActivity;
import com.art.recruitment.artperformance.ui.dynamic.contract.MainConstant;
import com.art.recruitment.artperformance.ui.group.contract.GroupDetailContract;
import com.art.recruitment.artperformance.ui.group.presenter.GroupDetailPresenter;
import com.art.recruitment.artperformance.ui.login.activity.UserAgreementActivity;
import com.art.recruitment.artperformance.ui.mine.activity.ChatActivity;
import com.art.recruitment.artperformance.utils.Defaultcontent;
import com.art.recruitment.artperformance.utils.ShareUtils;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.utils.UIUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.PictureSelector;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 演员详情页面
 */
public class GruopDetailActivity extends BaseActivity<GroupDetailPresenter> implements GroupDetailContract {

    @BindView(R.id.group_detail_photo_imageview)
    ImageView mPhotoImageview;
    @BindView(R.id.group_detail_name_textview)
    TextView mNameTextview;
    @BindView(R.id.group_detail_count_textview)
    TextView mCountTextview;
    @BindView(R.id.group_detail_zan_imageview)
    ImageView mZanImageview;
    @BindView(R.id.group_detail_gender)
    TextView mGender;
    @BindView(R.id.group_detail_gender_textview)
    TextView mGenderTextview;
    @BindView(R.id.group_detail_age)
    TextView mAge;
    @BindView(R.id.group_detail_age_textview)
    TextView mAgeTextview;
    @BindView(R.id.group_detail_height)
    TextView mHeight;
    @BindView(R.id.group_detail_height_textview)
    TextView mHeightTextview;
    @BindView(R.id.group_detail_weight)
    TextView mWeight;
    @BindView(R.id.group_detail_weight_textview)
    TextView mWeightTextview;
    @BindView(R.id.group_detail_three_circles)
    TextView mThreeCircles;
    @BindView(R.id.group_detail_three_circles_textview)
    TextView mThreeCirclesTextview;
    @BindView(R.id.group_detail_weChat)
    TextView mWeChat;
    @BindView(R.id.group_detail_weChat_textview)
    TextView mWeChatTextview;
    @BindView(R.id.group_detail_telephone)
    TextView mTelephone;
    @BindView(R.id.group_detail_telephone_textview)
    TextView mTelephoneTextview;
    @BindView(R.id.group_detail_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.group_detail_message_imageview)
    ImageView mMessageImageview;
    @BindView(R.id.group_detail_message)
    ImageView mMessage;
    @BindView(R.id.group_detail_instant_communication)
    TextView mInstantCommunication;
    @BindView(R.id.group_detail_city_textview)
    TextView mCityTextview;
    @BindView(R.id.group_detail_other_textview)
    TextView mOtherTextview;
    @BindView(R.id.group_detail_banner)
    Banner mBanner;
    @BindView(R.id.group_detail_city)
    TextView mDetailCity;
    @BindView(R.id.group_detail_button)
    ConstraintLayout mDetailButton;
    @BindView(R.id.group_detail_video_imageview)
    ImageView mVideoImageView;

//    @BindView(R.id.iv_play)
//    ImageView iv_play;

    private MyImageLoader mMyImageLoader;
    private GroupDetailBean.DataBean beans;
    private int group_id;
    private Dialog shareDialog;
    private String videoPlayUrl;
    String shareTitle;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gruop_detail;
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
        group_id = getIntent().getIntExtra("group_id", 0);

        mPresenter.recuitDetail(group_id);

        initBottonClick();
//        iv_play.setVisibility(View.INVISIBLE);
    }

    private void initBottonClick() {

        RxView.
                clicks(mReturnImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });

        //聊天
        RxView.
                clicks(mDetailButton).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.actorID(beans.getId());
                    }
                });

        RxView.
                clicks(mZanImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.actorsLikes(group_id);
                    }
                });

        //点击播放视频
        RxView.
                clicks(mVideoImageView).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        if (!TextUtils.isEmpty(videoPlayUrl))
                            PictureSelector.create(GruopDetailActivity.this).externalPictureVideo(videoPlayUrl);

                    }
                });

        //分享
        RxView.
                clicks(mMessageImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        shareDialog(Api.HTTP_URL + "actors/" + group_id + "/share");
                    }
                });
    }

    private void initBanner(final GroupDetailBean.DataBean bean) {
        mMyImageLoader = new MyImageLoader();
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        mBanner.setImageLoader(mMyImageLoader);
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        mBanner.setDelayTime(2000);
        mBanner.isAutoPlay(true);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);


        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Logger.d("=====OnBannerListener=====  ?????  " + position);
                //预览图片
                Intent intent = new Intent(mContext, PlusImageActivity.class);
                intent.putStringArrayListExtra(MainConstant.IMG_LIST, (ArrayList<String>) bean.getPhotoView());
                intent.putExtra(MainConstant.POSITION, position);
                intent.putExtra("canDelete", false);
                mContext.startActivity(intent);

            }
        });
        mBanner.setImages(bean.getPhotoView())
                .start();
    }


    @Override
    public void returnGroupDetailBean(GroupDetailBean.DataBean bean) {
        beans = bean;
        Glide.with(mContext).load(bean.getPrimaryPhotoView().get(0)).into(mPhotoImageview);
        mNameTextview.setText(bean.getName());

        shareTitle = bean.getName() + "-演员已就位";
        mCountTextview.setText(bean.getLikes() + "");
        mGenderTextview.setText(bean.getGenderText());
        mAgeTextview.setText(bean.getAge() + "");
        mCityTextview.setText(bean.getCityName());
        mHeightTextview.setText(bean.getHeight() + "");
        mWeightTextview.setText(bean.getBodyWeight() + "");
        mThreeCirclesTextview.setText(bean.getBust() + "    " + bean.getWaist() + "    " + bean.getHips());
        //微信号
        mWeChatTextview.setText(bean.getWechatHiddenFlag() == 1 ? bean.getWechat() : "******");
        //手机号
        mTelephoneTextview.setText(bean.getTelephoneHiddenFlag() == 1 ? bean.getTelephone() : "***********");
        mOtherTextview.setText(bean.getPersonalExperience());
        Glide.with(mContext).load(bean.getPersonalIntroductionVideoPreviewView()).into(mVideoImageView);
        videoPlayUrl = bean.getPersonalIntroductionVideoView();

//        iv_play.setVisibility(TextUtils.isEmpty(videoPlayUrl)? View.INVISIBLE : View.VISIBLE);

        if (bean.isIsLikes()) {
            mZanImageview.setImageDrawable(UIUtils.getDrawable(R.mipmap.icon_circle_like_p));
        } else {
            mZanImageview.setImageDrawable(UIUtils.getDrawable(R.mipmap.icon_circle_like));
        }

        initBanner(bean);
    }

    @Override
    public void returnActorIdBean(ActorIdBean.DataBean bean) {
        Logger.d("username:::" + bean.getUsername());
        Intent chat = new Intent(this, ChatActivity.class);
        chat.putExtra(EaseConstant.EXTRA_USER_ID, bean.getUsername());  //对方账号
//        chat.putExtra(EaseConstant.EXTRA_USER_ID, bean.getuse());  //对方账号
        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE); //单聊模式
        startActivity(chat);
    }

    @Override
    public void returbActorLikesBean(ActorLikesBean.DataBean bean) {
        mPresenter.recuitDetail(group_id);
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

    /**
     * 图片加载类
     */
    private class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext()).load(path).into(imageView);
        }
    }

    //    Api.HTTP_URL + "actors/" + (SPUtils.getInstance().getInt(BaseConfig.BaseSPKey.ID)) + "/share";
    private void shareDialog(final String shareUrl) {
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


        mWechatConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(GruopDetailActivity.this, shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN
                );
                hideDialog();
            }
        });

        mCircleFriendsConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(GruopDetailActivity.this, shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN_CIRCLE
                );
                hideDialog();
            }
        });

        mQQZoneConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(GruopDetailActivity.this, shareUrl, shareTitle
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.QZONE
                );
                hideDialog();
            }
        });

        mQQConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(GruopDetailActivity.this, shareUrl, shareTitle
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


}
