package com.art.recruitment.artperformance.ui.dynamic.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.dynamic.DynamicLikesBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicListBean;
import com.art.recruitment.artperformance.ui.dynamic.DynamicType;
import com.art.recruitment.artperformance.ui.dynamic.activity.DynamicDetailActivity;
import com.art.recruitment.artperformance.ui.dynamic.activity.ReleaseDynamicActivity;
import com.art.recruitment.artperformance.ui.dynamic.adapter.DynamicAdapter;
import com.art.recruitment.artperformance.ui.dynamic.contract.DynamicFagmentContract;
import com.art.recruitment.artperformance.ui.dynamic.presenter.DynamicFagmentPresenter;
import com.art.recruitment.artperformance.ui.mine.activity.MineDynamicActivity;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.utils.Defaultcontent;
import com.art.recruitment.artperformance.utils.ShareUtils;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

/**
 * 动态圈列表
 */
public class DynamicFragment extends BaseFragment<DynamicFagmentPresenter, DynamicListBean.ContentBean> implements DynamicFagmentContract {

    @BindView(R.id.dynamic_my_head_imageview)
    CircleImageView mHeadImageview;
    @BindView(R.id.dynamic_release_textview)
    TextView mReleaseTextview;
    @BindView(R.id.dynamic_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.dynamic_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private DynamicAdapter dynamicAdapter;
    private int itemPosition;
    //    private DynamicListBean.DataBean dataBeans;
    private int choosePosition = -1;
    private Dialog shareDialog;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int delete = intent.getIntExtra("delete", 0);
            if (delete == 1) {
                dynamicAdapter.notifyItemRemoved(choosePosition);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return mSmartRefreshLayout;
    }

    @Override
    protected RecyclerView getRecyclerView() {

        if (mRecyclerView.getItemAnimator() != null)
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return mRecyclerView;
    }

    @Override
    protected BaseRecyclerViewAdapter<DynamicListBean.ContentBean> getRecyclerViewAdapter() {
        dynamicAdapter = new DynamicAdapter(mContext, mDataList);
        dynamicAdapter.setFragment(this);
//        dynamicAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        dynamicAdapter.openLoadAnimation();
        dynamicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.dynamic_comment_imageview:
                    case R.id.ll_content://进入详情页面
                        //评论
                        choosePosition = position;
                        Intent intent = new Intent(getContext(), DynamicDetailActivity.class);
                        intent.putExtra("dynamic_id", dynamicAdapter.getData().get(position).getId());
                        startActivity(intent);
                        break;
                    case R.id.dynamic_give_imageview:
                        //点赞
                        itemPosition = position;
                        mPresenter.dynamicLikes(dynamicAdapter.getData().get(position).getId());
                        break;
                    case R.id.dynamic_share_constraintLayout://分享

                        String subUrl = "/dynamiccircle/" + dynamicAdapter.getData().get(position).getId() + "/share";
                        startShare(subUrl);

                        break;
                }

            }
        });
        return dynamicAdapter;
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
    protected void lazyLoad() {
        autoRefresh();
    }

    @Override
    protected void initListRequest(int page) {
        super.initListRequest(page);
        mPresenter.dynamicList(page, BaseConfig.DEFAULT_PAGE_SIZE, Constant.SORT_DESC);
    }

    @Override
    protected void initView() {
        setEmptyErrorViewData(R.mipmap.img_show_empty, "暂无动态");

        EventBus.getDefault().register(this);
        autoRefresh();

        initButtonClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DELETE_DYNAMIC");
        if (getActivity() != null)
            getActivity().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        try {
            if (getActivity() != null)
                getActivity().unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(String event) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_head_error);
        options.error(R.mipmap.ic_head_error);
        Glide.with(mContext).load(event).apply(options).into(mHeadImageview);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(Integer updateType) {

        List<DynamicListBean.ContentBean> data = dynamicAdapter.getData();
        DynamicListBean.ContentBean contentBean = null;
        try {
            contentBean = data.get(choosePosition);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (updateType) {

            case DynamicType.TYPE_ADD_COMMENT://详情里添加了评论

                if (contentBean != null) {
                    int commentNumber = contentBean.getCommentNumber();
                    contentBean.setCommentNumber(commentNumber+1);
                    dynamicAdapter.notifyItemChanged(choosePosition);
                }


                break;
            case DynamicType.TYPE_ADD_LIKE://详情里添加了点赞
                if (contentBean != null) {
                    contentBean.setIsLikes(true);
                    int likes = contentBean.getLikes();
                    contentBean.setLikes(likes + 1);
                    dynamicAdapter.notifyItemChanged(choosePosition);
                }
                break;

        }
//        EventBus.getDefault().post();

    }


    private void initButtonClick() {

        //发布动态圈
        RxView.
                clicks(mReleaseTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), ReleaseDynamicActivity.class));
                    }
                });

        //我的动态圈
        RxView.
                clicks(mHeadImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), MineDynamicActivity.class));
                    }
                });
    }

    //返回的数据
    @Override
    public void returnDynamicListBean(final DynamicListBean.DataBean bean, int page) {

        resetStateWhenLoadDataSuccess(bean.getContent());
    }

    @Override
    public void returnDynamicLikesBean(DynamicLikesBean.DataBean bean) {

        List<DynamicListBean.ContentBean> data = dynamicAdapter.getData();
        data.get(itemPosition).setIsLikes(true);
        data.get(itemPosition).setLikes(bean.getLikes());
        dynamicAdapter.notifyItemChanged(itemPosition);
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }


    /**
     * 分享
     *
     * @param subUrl
     */
    private void startShare(String subUrl) {

        View inflate = View.inflate(getContext(), R.layout.dialog_mine_share, null);
        TextView mCleanTextview = inflate.findViewById(R.id.mine_share_clean_textview);
        ConstraintLayout mWechatConstraintLayout = inflate.findViewById(R.id.share_wechat_constraintLayout);
        ConstraintLayout mCircleFriendsConstraintLayout = inflate.findViewById(R.id.share_circle_of_friends_constraintLayout);
        ConstraintLayout mQQZoneConstraintLayout = inflate.findViewById(R.id.share_qq_zone_constraintLayout);
        ConstraintLayout mQQConstraintLayout = inflate.findViewById(R.id.share_qq_constraintLayout);

        shareDialog = DialogWrapper.
                customViewDialog().
                context(getContext()).
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

        mWechatConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, ShareUtils.SHARE_TITLE_DYNAMIC
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN
                );
                hideDialog();
            }
        });

        mCircleFriendsConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, ShareUtils.SHARE_TITLE_DYNAMIC
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN_CIRCLE
                );
                hideDialog();
            }
        });

        mQQZoneConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, ShareUtils.SHARE_TITLE_DYNAMIC
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.QZONE
                );
                hideDialog();
            }
        });

        mQQConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, ShareUtils.SHARE_TITLE_DYNAMIC
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