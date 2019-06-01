package com.art.recruitment.artperformance.ui.group.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.ActorIdBean;
import com.art.recruitment.artperformance.bean.group.ActorLikesBean;
import com.art.recruitment.artperformance.bean.group.GroupDetailBean;
import com.art.recruitment.artperformance.ui.group.contract.GroupDetailContract;
import com.art.recruitment.artperformance.ui.group.presenter.GroupDetailPresenter;
import com.art.recruitment.artperformance.ui.mine.activity.ChatActivity;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.utils.UIUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.jakewharton.rxbinding2.view.RxView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

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

    private MyImageLoader mMyImageLoader;
    private GroupDetailBean.DataBean beans;
    private int group_id;

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
    }

    private void initBanner(GroupDetailBean.DataBean bean) {
        mMyImageLoader = new MyImageLoader();
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        mBanner.setImageLoader(mMyImageLoader);
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        mBanner.setDelayTime(2000);
        mBanner.isAutoPlay(true);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImages(bean.getPhotoView())
                .start();
    }


    @Override
    public void returnGroupDetailBean(GroupDetailBean.DataBean bean) {
        beans = bean;
        Glide.with(mContext).load(bean.getPrimaryPhotoView().get(0)).into(mPhotoImageview);
        mNameTextview.setText(bean.getName());
        mCountTextview.setText(bean.getLikes() + "");
        mGenderTextview.setText(bean.getGenderText());
        mAgeTextview.setText(bean.getAge() + "");
        mCityTextview.setText(bean.getCityName());
        mHeightTextview.setText(bean.getHeight() + "");
        mWeightTextview.setText(bean.getBodyWeight() + "");
        mThreeCirclesTextview.setText(bean.getBust() + "    " + bean.getWaist() + "    " + bean.getHips());
        mWeChatTextview.setText(bean.getWechat());
        mTelephoneTextview.setText(bean.getTelephone());
        mOtherTextview.setText(bean.getPersonalExperience());
        Glide.with(mContext).load(bean.getPersonalIntroductionVideoView()).into(mVideoImageView);

        if (bean.isIsLikes()) {
            mZanImageview.setImageDrawable(UIUtils.getDrawable(R.mipmap.icon_circle_like_p));
        } else {
            mZanImageview.setImageDrawable(UIUtils.getDrawable(R.mipmap.icon_circle_like));
        }

        initBanner(bean);
    }

    @Override
    public void returnActorIdBean(ActorIdBean.DataBean bean) {
        Intent chat = new Intent(this, ChatActivity.class);
        chat.putExtra(EaseConstant.EXTRA_USER_ID, bean.getUsername());  //对方账号
        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊模式
        startActivity(chat);
    }

    @Override
    public void returbActorLikesBean(ActorLikesBean.DataBean bean) {
        mPresenter.recuitDetail(group_id);
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null){
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
}
