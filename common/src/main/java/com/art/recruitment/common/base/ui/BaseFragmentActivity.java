package com.art.recruitment.common.base.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.art.recruitment.common.R;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.utils.StatusBarUtil;
import com.art.recruitment.common.utils.SystemBarTintManager;
import com.blankj.utilcode.util.SPUtils;

/**
 * Created by linaidao on 2019/6/21.
 */

public class BaseFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setTranslucentStatusBar();

        //这里注意下 因为在评论区发现有网友调用setRootViewFitsSystemWindows 里面 winContent.getChildCount()=0 导致代码无法继续
        //是因为你需要在setContentView之后才可以调用 setRootViewFitsSystemWindows

//        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
//        StatusBarUtil.setRootViewFitsSystemWindows(this,false);

        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, R.color.white);
        }
    }


//    /**
//     * 设置沉浸式菜单，无标题，屏幕方向等
//     */
//    protected void doBeforeSetcontentView() {
//        // 无标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //竖屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        SetStatusBarColor();
//        SetTranslanteBar();
//    }
//    /**
//     * 着色状态栏（4.4以上系统有效）
//     */
//    protected void SetStatusBarColor() {
//
//        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
//    }
//
//    /**
//     * 着色状态栏（4.4以上系统有效）
//     */
//    protected void SetStatusBarColor(int color) {
//        StatusBarCompat.setStatusBarColor(this, color);
//    }
//
//    /**
//     * 沉浸状态栏（4.4以上系统有效）
//     */
//    protected void SetTranslanteBar() {
//        StatusBarCompat.translucentStatusBar(this);
//    }

    private boolean isFullScreen = false;

//    /**
//     * 设置沉浸式状态栏
//     */
//    protected void setTranslucentStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //5.0及以上
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//
//            isFullScreen = true;
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //4.4到5.0
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//
//            isFullScreen = true;
//        }else {
//            isFullScreen = false;
//        }
//
//
//        //获取windowphone下的decorView
//        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
//        int       count     = decorView.getChildCount();
//        //判断是否已经添加了statusBarView
//        if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
//            decorView.getChildAt(count - 1).setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
//        } else {
//            //新建一个和状态栏高宽的view
//            StatusBarView statusView = createStatusBarView(activity, color, statusBarAlpha);
//            decorView.addView(statusView);
//        }
//        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//        //rootview不会为状态栏留出状态栏空间
//        ViewCompat.setFitsSystemWindows(rootView,true);
//        rootView.setClipToPadding(true);
//    }
//
//    private static StatusBarView createStatusBarView(Activity activity, int color, int alpha) {
//        // 绘制一个和状态栏一样高的矩形
//        StatusBarView statusBarView = new StatusBarView(activity);
//        LinearLayout.LayoutParams params =
//                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
//        statusBarView.setLayoutParams(params);
//        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
//        return statusBarView;
//    }


    public boolean isFullScreen() {
        return isFullScreen;
    }

//    private void setSurfaceViewContent() {
//
//        IToolbar mIToolbar = getIToolbar();
//        Toolbar mToolbar = null;
//
//        if (mIToolbar != null){
//            mToolbar = mIToolbar.getToolbar();
//        }
//
//        if (mIToolbar == null || mToolbar ==null){
//
//            //不设置IToolbar，或不设置view，视为不使用toolbar
//
//        }else { //添加设置的toolbar的view
//            mSurfaceView.addView(mToolbarContainer);
//
//            //交换背景色，基于微弱性能考虑
//            mToolbarContainer.setBackground(mToolbar.getBackground());
//            mToolbar.setBackground(null);
//            mToolbarContainer.addView(mToolbar);
//            //手动添加顶部margin
//            if (isFullScreen){
//                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mToolbar.getLayoutParams();
//                int statusBarHeight = SPUtils.getInstance().getInt(BaseConfig.STATUS_BAR_HEIGHT);
//                //有时状态栏获取不成功，此处如果状态栏像素小于10，认为没有获取到状态栏，给一个默认的状态栏高度
//                statusBarHeight = statusBarHeight <10 ? (int) getResources().getDimension(R.dimen.y46) : statusBarHeight;
//                params.topMargin = statusBarHeight;
//                mToolbar.setLayoutParams(params);
//            }
//        }
//
////        //不为空时添加无网络提示的view，不需要相应方法子类空实现即可
////        if (mNoNetworkTipView != null){
////            mSurfaceView.addView(mNoNetworkTipView);
////        }
//
//        mSurfaceView.addView(mContentContainer);
//    }


}
