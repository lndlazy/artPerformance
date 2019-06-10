package com.art.recruitment.artperformance.ui.group.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.GroupListBean;
import com.art.recruitment.artperformance.ui.group.adapter.GroupAdapter;
import com.art.recruitment.artperformance.ui.group.contract.SearchContract;
import com.art.recruitment.artperformance.ui.group.presenter.SearchPresenter;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class SearchFragment extends BaseFragment<SearchPresenter, GroupListBean.ContentBean> implements SearchContract {
    @BindView(R.id.group_search_imageview)
    ImageView mSearchImageview;
    @BindView(R.id.group_search_editText)
    EditText mSearchEditText;
    @BindView(R.id.group_search_delete_imageView)
    ImageView mDeleteImageView;
    @BindView(R.id.group_search_constraintLayout)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.group_search_clean_textview)
    TextView mCleanTextview;
    @BindView(R.id.group_search_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.group_search_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private GroupAdapter groupAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
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
        groupAdapter = new GroupAdapter(mContext, mDataList);
        groupAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        return groupAdapter;
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
        setEmptyErrorViewData(R.mipmap.img_search_empty, "没有找到您搜索的内容~");

        initButtonClick();
    }

    private void initButtonClick() {
        RxView.
                clicks(mCleanTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getActivity().finish();
                    }
                });

        RxView.
                clicks(mDeleteImageView).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mSearchEditText.setText("");
                    }
                });

        RxView.
                clicks(mSearchImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        String mSearch = mSearchEditText.getText().toString().trim();
                        mPresenter.actorsList(mSearch, 0, 20, Constant.SORT_DESC);
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        autoRefresh();
    }

    @Override
    public void returbGroupListBean(GroupListBean.DataBean bean) {
        resetStateWhenLoadDataSuccess(bean.getContent());
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            resetStateWhenLoadDataFailed(errorCode, message);
        }
    }
}
