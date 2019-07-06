package com.art.recruitment.artperformance.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.StatusBean;
import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.ui.dynamic.fragment.DynamicFragment;
import com.art.recruitment.artperformance.ui.group.activity.RealNameActivity;
import com.art.recruitment.artperformance.ui.group.activity.ReleaseRecruitmentActivity;
import com.art.recruitment.artperformance.ui.group.fragment.GroupFragment;
import com.art.recruitment.artperformance.ui.home.fragment.HomeFragment;
import com.art.recruitment.artperformance.ui.mine.activity.MineDataActivity;
import com.art.recruitment.artperformance.ui.mine.fragment.MineFragment;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.view.CustomViewPager;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.artperformance.view.TabEntity;
import com.art.recruitment.common.ActivityManager;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.NetUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

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
//    private String head;

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
        getRecordPermisson();

        mPresenter.loginToHx();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

//        记得在不需要的时候移除listener，如在activity的onDestroy()时
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(String event) {
//        RequestOptions options = new RequestOptions();
//        options.placeholder(R.mipmap.login_logo);
//        head = event;
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

            SPUtils.getInstance().getString(BaseConfig.BaseSPKey.PHONE_NUM);
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(BaseConfig.BaseSPKey.PHONE_NUM))
                    && !TextUtils.isEmpty(SPUtils.getInstance().getString(BaseConfig.BaseSPKey.USER_NAME))
                    && ((SPUtils.getInstance().getInt(BaseConfig.BaseSPKey.SEX)) != -1)) {
                Intent intent = new Intent(MainActivity.this, ReleaseRecruitmentActivity.class);
                intent.putExtra("release_id", 0);
//                Logger.d("head-===>" + head);
//                intent.putExtra("group_head", head);
                startActivity(intent);
            } else {
                perfectInformation();
            }

        }
    }

    //登录聊天服务器成功
    @Override
    public void returnImUserBean(ImUserBean.DataBean tokenBean) {

        String hxusername = tokenBean.getUsername();
        if (!TextUtils.isEmpty(hxusername))
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.HX_USERNAME, hxusername);


        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();

        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

        //注册消息监听
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

//        int unreadMessageCount = EMClient.getInstance().chatManager().getUnreadMessageCount();
//        Logger.d("未读消息个数：：" + unreadMessageCount);
//        if (unreadMessageCount > 0)
//            EventBus.getDefault().post(new ArrayList<EMMessage>());

    }


    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息

//            for (int i = 0; i < messages.size(); i++) {
//
//                EMMessage emMessage = messages.get(i);
//
//                String from = emMessage.getFrom();
//
//                EMMessageBody body = emMessage.getBody();
//
//                String s = body.toString();
//
//                Logger.d("消息内容::" + s + ",from:" + from + ",to:" + emMessage.getTo());
//
//            }

            EventBus.getDefault().post(messages);

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };


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

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
        });

    }


    @Override
    public void onBackPressed() {

//        Logger.d("=====onBackPressed=====");
        ActivityManager.getInstance().exitByDoubleClick();
//        if (!isDoubleClickExit) {
//            //说明可以回退,直接调用super，系统finish掉Activity，在onPause方法中从AppMananger中移除
//            //exitAnim();
//            super.onBackPressed();
//        } else {
        //两次点击才能finish掉activity

//        }
    }


    private static final int GET_RECODE_AUDIO = 0x911;
    private static final int TAKE_PHOTO_REQUEST_CODE = 0x912;
    private static String[] PERMISSION_AUDIO = {
            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA
    };

    private void getRecordPermisson() {

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION_AUDIO, GET_RECODE_AUDIO);
        }
        int camer_permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (camer_permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION_AUDIO, GET_RECODE_AUDIO);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    TAKE_PHOTO_REQUEST_CODE);
        }
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        Logger.d("显示帐号在其他设备登录, 退出聊天账号登录");
                        EMClient.getInstance().logout(true, new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onProgress(int progress, String status) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onError(int code, String message) {
                                // TODO Auto-generated method stub

                            }
                        });

                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)) {
                            //连接不到聊天服务器
                        } else {
                            //当前网络不可用，请检查网络设置

                        }

                    }
                }
            });
        }
    }


}