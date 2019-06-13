package com.art.recruitment.artperformance.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.StatusBean;
import com.art.recruitment.artperformance.ui.dynamic.fragment.DynamicFragment;
import com.art.recruitment.artperformance.ui.group.activity.RealNameActivity;
import com.art.recruitment.artperformance.ui.group.activity.ReleaseRecruitmentActivity;
import com.art.recruitment.artperformance.ui.group.fragment.GroupFragment;
import com.art.recruitment.artperformance.ui.home.fragment.HomeFragment;
import com.art.recruitment.artperformance.ui.mine.MyInfoSave;
import com.art.recruitment.artperformance.ui.mine.activity.MineDataActivity;
import com.art.recruitment.artperformance.ui.mine.fragment.MineFragment;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.utils.SaveUtils;
import com.art.recruitment.artperformance.view.CustomViewPager;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.artperformance.view.TabEntity;
import com.art.recruitment.common.ActivityManager;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.art.recruitment.common.utils.UIUtils.getActivity;

/**
 * main页面
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract {

    @BindView(R.id.activity_main_viewpager)
    CustomViewPager mViewpager;
    @BindView(R.id.activity_main_view_container)
    FrameLayout mViewContainer;
    @BindView(R.id.m_main_tab_layout)
    CommonTabLayout mMainTabLayout;
    @BindView(R.id.activity_main_publish_image_view_main)
    ImageView mPublishImageViewMain;
    @BindView(R.id.activity_main_publish_container)
    FrameLayout mPublishContainer;
    @BindView(R.id.gray_layout_main)
    View grayLayoutMain;
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home
            , R.mipmap.tab_show
            , R.mipmap.tab_status
            , R.mipmap.tab_status
            , R.mipmap.tab_my};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_s
            , R.mipmap.tab_show_s
            , R.mipmap.tab_status_s
            , R.mipmap.tab_status_s
            , R.mipmap.tab_my_s};
    private HomeFragment mHomeFragment;
    private GroupFragment mGroupFragment;
    private DynamicFragment mDynamicFragment;
    private MineFragment mMineFragment;
    private List<BaseFragment> mFragmentList = new ArrayList<>(4);
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String head;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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

        EventBus.getDefault().register(this);
        initViewPager();
        initCommonTabLayout();
        initButtonClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(String event) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.login_logo);
        head = event;
    }

    //发布招募
    private void initButtonClick() {
        RxView.clicks(mPublishImageViewMain).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.status();
                    }
                });
    }

    private void initViewPager() {
        mHomeFragment = new HomeFragment();
        mGroupFragment = new GroupFragment();
        mDynamicFragment = new DynamicFragment();
        mMineFragment = new MineFragment();

        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mGroupFragment);
        mFragmentList.add(mDynamicFragment);
        mFragmentList.add(mMineFragment);

        MainPagerAdapter mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewpager.setAdapter(mMainPagerAdapter);
        mViewpager.setOffscreenPageLimit(mFragmentList.size());
        mViewpager.setCanScroll(false);
        mViewpager.enablePagerChangeAnimation(false);
    }

    private void initCommonTabLayout() {
        String[] coinNameArray = getResources().getStringArray(R.array.mainTab);
        for (int i = 0; i < coinNameArray.length; i++) {
            mTabEntities.add(new TabEntity(coinNameArray[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mMainTabLayout.setTabData(mTabEntities);
        mMainTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                switch (position) {
                    case 0:
                        mViewpager.setCurrentItem(position);
                        grayLayoutMain.setVisibility(View.GONE);
                        break;
                    case 1:
                        mViewpager.setCurrentItem(position);
                        grayLayoutMain.setVisibility(View.GONE);
                        break;
                    case 2:
                        grayLayoutMain.setVisibility(View.GONE);
                        break;
                    case 3:
                    case 4:
                        mViewpager.setCurrentItem(position - 1);
                        grayLayoutMain.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void returbStatusBean(StatusBean.DataBean bean) {

        //先判断是否实名认证
        if (bean.getRealNameFlag() == 0) {
            //未实名认证， 开始认证
            startActivity(RealNameActivity.class);
        } else if (bean.getRealNameFlag() == 1) {
            //已经实名认证， 判断是否填写过 基本三项信息

            if (!TextUtils.isEmpty((String)SaveUtils.get(this, MyInfoSave.PHONE_NUM, ""))
                    && !TextUtils.isEmpty((String)SaveUtils.get(this, MyInfoSave.USER_NAME, ""))
                    && ((int)SaveUtils.get(this, MyInfoSave.SEX, -1) != -1)) {
                Intent intent = new Intent(MainActivity.this, ReleaseRecruitmentActivity.class);
                intent.putExtra("release_id", 0);
                Logger.d("head-===>" + head);
                intent.putExtra("group_head", head);
                startActivity(intent);
            }else {
                perfectInformation();
            }

        }
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }


    private Dialog dialog;

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
                Intent m = new Intent(MainActivity.this, MineDataActivity.class);
                m.putExtra(Constant.EDITOR_TYPE, Constant.EDITOR_TYPE_RELEASE);
                startActivity(m);

                if (dialog!=null && dialog.isShowing())
                    dialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
//        if (!isDoubleClickExit) {
//            //说明可以回退,直接调用super，系统finish掉Activity，在onPause方法中从AppMananger中移除
//            //exitAnim();
//            super.onBackPressed();
//        } else {
            //两次点击才能finish掉activity
            ActivityManager.getInstance().exitByDoubleClick(System.currentTimeMillis());
//        }
    }



}