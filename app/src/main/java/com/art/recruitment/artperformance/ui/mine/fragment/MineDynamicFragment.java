package com.art.recruitment.artperformance.ui.mine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.MineDynamicBean;
import com.art.recruitment.artperformance.bean.model.NineGridTestModel;
import com.art.recruitment.artperformance.ui.dynamic.activity.ReleaseDynamicActivity;
import com.art.recruitment.artperformance.ui.group.adapter.GroupAdapter;
import com.art.recruitment.artperformance.ui.mine.activity.MineDynamicActivity;
import com.art.recruitment.artperformance.ui.mine.adapter.MineDynamicAdapter;
import com.art.recruitment.artperformance.ui.mine.contract.MineDynamicContract;
import com.art.recruitment.artperformance.ui.mine.presenter.MineDynamicPresenter;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * 我的动态
 */
public class MineDynamicFragment extends BaseFragment<MineDynamicPresenter, MineDynamicBean.ContentBean> implements MineDynamicContract {
    @BindView(R.id.mine_dynamic_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.mine_diynamic_release_textview)
    TextView mReleaseTextview;

    @BindView(R.id.mine_diynamic_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.mine_diynamic_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private MineDynamicAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_dynamic;
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return mSmartRefreshLayout;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

//    @Override
//    protected BaseRecyclerViewAdapter<MineDynamicBean.ContentBean> getRecyclerViewAdapter() {
//        adapter = new MineDynamicAdapter(mContext, mDataList);
//        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        return adapter;
//    }


    @Override
    protected BaseRecyclerViewAdapter getRecyclerViewAdapter() {
        adapter = new MineDynamicAdapter(mContext, mDataList);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        return adapter;
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
    protected void initListRequest(int page) {
        super.initListRequest(page);
        mPresenter.mineDynamicList(page, BaseConfig.DEFAULT_PAGE_SIZE, "");
    }

    @Override
    protected void initView() {
        setEmptyErrorViewData(R.mipmap.img_show_empty, "暂时没有数据");

        autoRefresh();

        initButtonClicks();
    }

    private void initButtonClicks() {
        RxView.
                clicks(mReturnImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getActivity().finish();
                    }
                });

        RxView.
                clicks(mReleaseTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), ReleaseDynamicActivity.class));
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        autoRefresh();
    }

    @Override
    public void returnMineDynamicBean(MineDynamicBean.DataBean bean) {
        if (bean.getContent() == null || bean.getContent().size() == 0) {
            setEmptyErrorViewData(R.mipmap.img_show_empty, "暂时没有数据");
        } else {
            resetStateWhenLoadDataSuccess(bean.getContent());
        }

    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            resetStateWhenLoadDataFailed(errorCode, message);
        }
    }
}
