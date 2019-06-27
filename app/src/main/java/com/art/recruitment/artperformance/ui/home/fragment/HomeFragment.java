package com.art.recruitment.artperformance.ui.home.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.home.BannerBean;
import com.art.recruitment.artperformance.bean.home.CitiSearch;
import com.art.recruitment.artperformance.bean.home.LogoBean;
import com.art.recruitment.artperformance.bean.home.RecruitListBean;
import com.art.recruitment.artperformance.ui.home.activity.CityActivity;
import com.art.recruitment.artperformance.ui.home.activity.RecruitmentInformationActivity;
import com.art.recruitment.artperformance.ui.home.adapter.HomeAdapter;
import com.art.recruitment.artperformance.ui.home.contract.HomeContract;
import com.art.recruitment.artperformance.ui.home.presenter.HomePresenter;
import com.art.recruitment.artperformance.ui.login.activity.UserAgreementActivity;
import com.art.recruitment.artperformance.ui.mine.activity.ChatListActivity;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.utils.Defaultcontent;
import com.art.recruitment.artperformance.utils.PermissionTipUtils;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.artperformance.view.PermissionRationalDialog;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.utils.SystemUtil;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.SettingService;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 首页 招募列表
 */
public class HomeFragment extends BaseFragment<HomePresenter, RecruitListBean.ContentBean> implements HomeContract {

    @BindView(R.id.home_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.home_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.home_city_textview)
    TextView mCityTextview;
    @BindView(R.id.home_city_imageview)
    ImageView mCityImageview;

    @BindView(R.id.home_city_constrainLayout)
    ConstraintLayout mCityConstrainLayout;

    @BindView(R.id.home_message_imageview)
    ImageView mMessageImageview;
    @BindView(R.id.home_banner)
    Banner mBanner;
    @BindView(R.id.home_tolbar_constraintLayout)
    ConstraintLayout mTolbarConstraintLayout;
    @BindView(R.id.home_sort_constraintLayout)
    ConstraintLayout mSortConstraintLayout;
    @BindView(R.id.home_sort_textview)
    TextView mSortTextview;
    @BindView(R.id.home_sort_next_imageview)
    ImageView mSortNextImageview;

    @BindView(R.id.home_desc_radioButton)
    TextView mDescRadioButton;

    @BindView(R.id.home_esc_radioButton)
    TextView mEscRadioButton;

    @BindView(R.id.home_desc_imageView)
    ImageView mDescImageView;
    @BindView(R.id.home_esc_imiageView)
    ImageView mEscImiageView;

    @BindView(R.id.rlSortView)
    RelativeLayout rlSortView;

    @BindView(R.id.r1)
    RelativeLayout r1;

    @BindView(R.id.r2)
    RelativeLayout r2;

    @BindView(R.id.ivNewMsg)
    ImageView ivNewMsg;

    @BindView(R.id.home_search_edittext)
    EditText mSearchEdittext;

    private HomeAdapter homeAdapter;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private MyImageLoader mMyImageLoader;
    private String mSort = "";
    private Dialog dialog;
    private int mCityCode = -1;
    //    private String mSearch;
    private Dialog mPermissionSettingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return mSmartRefreshLayout;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected BaseRecyclerViewAdapter getRecyclerViewAdapter() {
        homeAdapter = new HomeAdapter(mContext, mDataList);
        homeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                //招募详情页面
                Intent intent = new Intent(getContext(), RecruitmentInformationActivity.class);
                intent.putExtra("recruitmentId", homeAdapter.getData().get(position).getId());
                intent.putExtra("home_name", homeAdapter.getData().get(position).getPublisherName());
//                intent.putExtra("home_id", homeAdapter.getData().get(position).getPublisher());
                startActivity(intent);

            }
        });
        return homeAdapter;
    }

    @Override
    protected boolean enableAdapterLoadMore() {
        return true;
    }

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initView() {

        setEmptyErrorViewData(R.mipmap.img_show_empty, "暂时没有数据");

        mCityCode = SPUtils.getInstance().getInt(BaseConfig.BaseSPKey.CITY_CODE);

        com.orhanobut.logger.Logger.d("缓存中的CITY_CODE:" + mCityCode);
        String cityName = SPUtils.getInstance().getString(BaseConfig.BaseSPKey.CITY_NAME);

        if (!TextUtils.isEmpty(cityName) && mCityCode != -1)
            mCityTextview.setText(cityName);

        requestPermissionAndSelectImage();

//        initMap();

        autoRefresh();

        initButtonClick();

        initEditTextLisnter();

        EventBus.getDefault().register(this);

        mPresenter.getLogoUrl();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
            setRedPoint();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(List<EMMessage> messages) {
        //消息到达
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

        ivNewMsg.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initListRequest(int page) {
        super.initListRequest(page);

        Logger.d("请求参数:mCityCode:" + mCityCode + ",mSearch:" + mSearchEdittext.getText().toString().trim() + ",page:" + page + ",mSort:" + mSort);

        mPresenter.recuitList(mCityCode, mSearchEdittext.getText().toString().trim(), page, BaseConfig.DEFAULT_PAGE_SIZE, mSort);
    }

    private void initEditTextLisnter() {
        mSearchEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
//                    mSearch = mSearchEdittext.getText().toString().trim();
//                    mSearch = trim;
                    autoRefresh();
                    SystemUtil.closeInoutDecorView(getActivity());
                    return true;
                }
                return false;
            }
        });
    }

    private void initMap() {
        mLocationClient = new AMapLocationClient(getContext());
        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setMockEnable(false);

        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    private void initButtonClick() {
        //选择城市
        RxView.
                clicks(mCityConstrainLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivityForResult(new Intent(getContext(), CityActivity.class), 100);
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

        RxView.
                clicks(mSortConstraintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mSortConstraintLayout.setVisibility(View.GONE);
                        rlSortView.setVisibility(View.VISIBLE);
                    }
                });

        RxView.
                clicks(mSortNextImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mSortConstraintLayout.setVisibility(View.VISIBLE);
                        rlSortView.setVisibility(View.GONE);
                    }
                });

        RxView.
                clicks(r1).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mDescImageView.setVisibility(View.VISIBLE);
                        mEscImiageView.setVisibility(View.INVISIBLE);

                        mSortConstraintLayout.setVisibility(View.VISIBLE);
                        rlSortView.setVisibility(View.GONE);
                        mSort = Constant.SALARY_DESC;
                        autoRefresh();
                    }
                });

        RxView.
                clicks(r2).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mDescImageView.setVisibility(View.INVISIBLE);
                        mEscImiageView.setVisibility(View.VISIBLE);

                        mSortConstraintLayout.setVisibility(View.VISIBLE);
                        rlSortView.setVisibility(View.GONE);
//                        mSort = "esc";
                        mSort = Constant.SALARY_ASC;

                        autoRefresh();
                    }
                });
    }


    @Override
    protected void lazyLoad() {
        autoRefresh();
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            resetStateWhenLoadDataFailed(errorCode, message);
        }
    }

    @Override
    public void returnBannerBean(List<BannerBean.DataBean> bean) {
        initBanner(bean);
    }

    @Override
    public void returnRecruitListBean(final RecruitListBean.DataBean bean, int page) {

        if (page == 0) {
            mPresenter.getBanner();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            homeAdapter.setHeaderView(mBanner);
        }

        resetStateWhenLoadDataSuccess(bean.getContent());

        setRedPoint();

    }

    private void setRedPoint() {
        int unreadMessageCount = EMClient.getInstance().chatManager().getUnreadMessageCount();
//        com.orhanobut.logger.Logger.d("未读消息个数：：" + unreadMessageCount);

        if (ivNewMsg != null)
            ivNewMsg.setVisibility(unreadMessageCount > 0 ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    public void returnRecruitListBean(final RecruitListBean.DataBean bean) {
//        mPresenter.getBanner();
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
////        homeAdapter.setData(bean.getContent());
//        /*homeAdapter.setHeaderView(mBanner);
//        mRecyclerView.setAdapter(homeAdapter);
//
//        homeAdapter.setOnViewClickListener(new HomeAdapter.OnItemClickListener() {
//            @Override
//            public void onArrowClickListener(int position) {
//                Intent intent = new Intent(getContext(), RecruitmentInformationActivity.class);
//                intent.putExtra("position", bean.getContent().get(position).getId());
//                startActivity(intent);
//            }
//        });*/
//        homeAdapter.setHeaderView(mBanner);
//        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                switch (view.getId()) {
//                    case R.id.home_arrow_imageview:
//                        Intent intent = new Intent(getContext(), RecruitmentInformationActivity.class);
//                        intent.putExtra("position", bean.getContent().get(position).getId());
//                        intent.putExtra("home_name", bean.getContent().get(position).getPublisherName());
//                        startActivity(intent);
//                        break;
//                }
//            }
//        });
//
//        resetStateWhenLoadDataSuccess(bean.getContent());
    }

    @Override
    public void returnCitiSearchBean(CitiSearch.DataBean bean, boolean isAutoLocation) {


        if (isAutoLocation) {
            //主动定位，询问是否切换地址

            //缓存的位置跟上次不一致
            if (SPUtils.getInstance().getInt(BaseConfig.BaseSPKey.CITY_CODE) != bean.getCityCode()) {
                initCity(bean);
            }

            //保存定位的城市信息
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.LOCATION_CITY_CODE, bean.getCityCode());

            if (!TextUtils.isEmpty(bean.getCityName()))
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.LOCATION_CITY_NAME, bean.getCityName());

        } else {
            mCityCode = bean.getCityCode();
            autoRefresh();

            //保存城市code
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.CITY_CODE, bean.getCityCode());
        }

    }

    @Override
    public void returnLogoUrl(LogoBean.DataBean bean) {
        if (bean != null) {

            if (!TextUtils.isEmpty(bean.getUrl()))
            SPUtils.getInstance().put(BaseConfig.BaseSPKey.LOGO_URL, bean.getUrl());
            Defaultcontent.imageurl = bean.getUrl();
        }
    }

    private void initBanner(final List<BannerBean.DataBean> bean) {
        List<String> strings = new ArrayList<>();

        mMyImageLoader = new MyImageLoader();
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        mBanner.setImageLoader(mMyImageLoader);
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        mBanner.setDelayTime(2000);
        mBanner.isAutoPlay(true);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        for (int i = 0; i < bean.size(); i++) {
            strings.add(bean.get(i).getImageUrl());
        }

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getContext(), UserAgreementActivity.class);
                intent.putExtra("web", "home/banner/" + bean.get(position).getId());
                startActivity(intent);
            }
        });
        mBanner.setImages(strings)
                .start();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK
                && data != null && data.getExtras() != null) {
            String city = data.getExtras().getString("city");

            //保存城市名称
            if (!TextUtils.isEmpty(city))
                SPUtils.getInstance().put(BaseConfig.BaseSPKey.CITY_NAME, city);

            mCityTextview.setText(city);
            mPresenter.citiSearch(city, false);

        }
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {

                    com.orhanobut.logger.Logger.d("定位成功::" + amapLocation.getCity());
//                    mCityTextview.setText(amapLocation.getCity());
//                    mPresenter.citiSearch(amapLocation.getCity());

                    if (!TextUtils.isEmpty(amapLocation.getCity())) {
                        mPresenter.citiSearch(amapLocation.getCity(), true);
                    }

                } else {
                    if (amapLocation.getErrorCode() == 12) {
                        initlLocation();
                    }
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    private void initCity(final CitiSearch.DataBean bean) {
        View inflate = View.inflate(getContext(), R.layout.dialog_cancel_or_ok, null);
        TextView mDialogTitle = inflate.findViewById(R.id.dialog_title_tv);
        TextView mCleanImageView = inflate.findViewById(R.id.release_dialogcancel_textview);
        TextView mDetermineTextview = inflate.findViewById(R.id.release_dialog_ok_textview);
        dialog = DialogWrapper.
                customViewDialog().
                context(getContext()).
                contentView(inflate).
                cancelable(false, false).
                build();

        dialog.show();
        mDialogTitle.setText("系统定位到您在 " + bean.getCityName() + "，需要切换城市吗？");
        mCleanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        mDetermineTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCityTextview.setText(bean.getCityName());

                mCityCode = bean.getCityCode();
                SPUtils.getInstance().put(BaseConfig.BaseSPKey.CITY_CODE, bean.getCityCode());

                if (!TextUtils.isEmpty(bean.getCityName()))
                SPUtils.getInstance().put(BaseConfig.BaseSPKey.CITY_NAME, bean.getCityName());
                autoRefresh();

                dialog.cancel();

            }
        });
    }

    private void initlLocation() {
        View inflate = View.inflate(getContext(), R.layout.dialog_cancel_or_ok, null);
        TextView mDialogTitle = inflate.findViewById(R.id.dialog_title_tv);
        TextView mCleanImageView = inflate.findViewById(R.id.release_dialogcancel_textview);
        TextView mDetermineTextview = inflate.findViewById(R.id.release_dialog_ok_textview);
        dialog = DialogWrapper.
                customViewDialog().
                context(getContext()).
                contentView(inflate).
                cancelable(false, false).
                build();

        dialog.show();
        mDialogTitle.setText("是否开启定位？");
        mCleanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        mDetermineTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                requestPermissionAndSelectImage();
            }
        });
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

    /**
     * 请求定位权限
     */
    private void requestPermissionAndSelectImage() {
        AndPermission.
                with(this).
                permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION).
                rationale(new PermissionRationalDialog()).
                onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(getContext(), permissions)) {
                            List<String> permissionNames = Permission.transformText(getContext(), permissions);

                            final SettingService mSettingService = AndPermission.permissionSetting(getContext());
                            mPermissionSettingDialog = DialogWrapper.
                                    tipDialog().
                                    context(getContext()).
                                    buttonType(DialogWrapper.BUTTON_DOUBLE).
                                    title("权限提示").
                                    message(PermissionTipUtils.getTipMessage(permissionNames)).
                                    leftButtonText("放弃").
                                    rightButtonText("立即开启").
                                    buttonClickListener(new DialogWrapper.TipTypeButtonClickListener() {
                                        @Override
                                        public void onLeftButtonClicked(TextView view) {

                                            if (mPermissionSettingDialog != null) {
                                                mPermissionSettingDialog.dismiss();
                                            }

                                            mSettingService.cancel();
                                        }

                                        @Override
                                        public void onSingleButtonClicked(TextView view) {

                                        }

                                        @Override
                                        public void onRightButtonClicked(TextView view) {
                                            if (mPermissionSettingDialog != null) {
                                                mPermissionSettingDialog.dismiss();
                                            }
                                            mSettingService.execute();
                                        }
                                    }).
                                    build();

                            mPermissionSettingDialog.show();
                        }
                    }
                }).
                onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        initMap();
//                        autoRefresh();
                    }
                }).
                start();

    }

}
