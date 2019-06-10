package com.art.recruitment.artperformance.ui.dynamic.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.dynamic.DynamicLikesBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicListBean;
import com.art.recruitment.artperformance.bean.model.NineGridTestModel;
import com.art.recruitment.artperformance.ui.dynamic.activity.DynamicDetailActivity;
import com.art.recruitment.artperformance.ui.dynamic.activity.ReleaseDynamicActivity;
import com.art.recruitment.artperformance.ui.dynamic.adapter.DynamicAdapter;
import com.art.recruitment.artperformance.ui.dynamic.contract.DynamicFagmentContract;
import com.art.recruitment.artperformance.ui.dynamic.presenter.DynamicFagmentPresenter;
import com.art.recruitment.artperformance.ui.group.adapter.GroupAdapter;
import com.art.recruitment.artperformance.ui.mine.activity.MineDynamicActivity;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

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
    private DynamicListBean.DataBean dataBeans;
    private int deletePosition;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int delete = intent.getIntExtra("delete", 0);
            if (delete == 1) {
                dynamicAdapter.notifyItemRemoved(deletePosition);
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
        return mRecyclerView;
    }

    @Override
    protected BaseRecyclerViewAdapter<DynamicListBean.ContentBean> getRecyclerViewAdapter() {
        dynamicAdapter = new DynamicAdapter(mContext, mDataList);
        dynamicAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
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
        mPresenter.dynamicList(page, 20, Constant.SORT_DESC);
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
        getActivity().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getActivity().unregisterReceiver(receiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(String event){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.login_logo);
        Glide.with(mContext).load(event).apply(options).into(mHeadImageview);
    }

    private void initButtonClick() {

        RxView.
                clicks(mReleaseTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                       startActivity(new Intent(getContext(), ReleaseDynamicActivity.class));
                    }
                });

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

    @Override
    public void returnDynamicListBean(final DynamicListBean.DataBean bean) {
        dataBeans = bean;

        dynamicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.dynamic_comment_imageview:
                        deletePosition = position;
                        Intent intent = new Intent(getContext(), DynamicDetailActivity.class);
                        intent.putExtra("dynamic_id", bean.getContent().get(position).getId());
                        startActivity(intent);
                        break;
                    case R.id.dynamic_give_imageview:
                        itemPosition = position;
                        mPresenter.dynamicLikes(bean.getContent().get(position).getId());
                        break;
                    default:
                        break;
                }

            }
        });

        resetStateWhenLoadDataSuccess(bean.getContent());
    }

    @Override
    public void returnDynamicLikesBean(DynamicLikesBean.DataBean bean) {
        dataBeans.getContent().get(itemPosition).setIsLikes(true);
        dataBeans.getContent().get(itemPosition).setLikes(bean.getLikes());
        dynamicAdapter.notifyItemChanged(itemPosition);
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }
}