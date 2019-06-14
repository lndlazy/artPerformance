package com.art.recruitment.artperformance.ui.group.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.GroupListBean;
import com.art.recruitment.artperformance.ui.group.activity.GruopDetailActivity;
import com.art.recruitment.artperformance.ui.group.adapter.GroupAdapter;
import com.art.recruitment.artperformance.ui.group.contract.SearchContract;
import com.art.recruitment.artperformance.ui.group.presenter.SearchPresenter;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.utils.SystemUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.stetho.common.LogUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 演员搜索
 */
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
    private String mSearch;

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

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 3, false));
        mRecyclerView.setLayoutManager(manager);
        return mRecyclerView;
    }

    @Override
    protected BaseRecyclerViewAdapter getRecyclerViewAdapter() {

        groupAdapter = new GroupAdapter(mContext, mDataList);
        groupAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        groupAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.group_photo_imageview:
                        Intent intent = new Intent(getContext(), GruopDetailActivity.class);
                        intent.putExtra("group_id", groupAdapter.getData().get(position).getId());
                        startActivity(intent);
                        break;
                    case R.id.constrainLike://TODO 点赞
//                        mPresenter.actorsLikes(groupAdapter.getData().get(position).getId());
                        break;
                    default:
                        break;
                }
            }
        });

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
                        startSearch();
                    }
                });


        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action

                    startSearch();
                    return true;
                }
                return false;
            }
        });
    }

    private void startSearch() {
        //隐藏软键盘
        closeInoutDecorView(getActivity());
        //开始搜索
        mSearch = mSearchEditText.getText().toString().trim();
        autoRefresh();

    }

    @Override
    protected void lazyLoad() {
        Logger.d("====lazyLoad==== 加载");
        autoRefresh();
    }

    @Override
    protected void initListRequest(int page) {
        super.initListRequest(page);

        Logger.d(" initListRequest 请求参数page:" + page + ",mSearch:" + mSearch);
        mPresenter.actorsList(mSearch, page, BaseConfig.DEFAULT_PAGE_SIZE, Constant.SORT_DESC);
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

    /**
     * 关闭软件盘
     */
    public static void closeInoutDecorView(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
