package com.art.recruitment.artperformance.ui.mine.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.CancelRecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.MineSignUpBean;
import com.art.recruitment.artperformance.ui.mine.adapter.MineSignUpAdapter;
import com.art.recruitment.artperformance.ui.mine.contract.MineSignUpContract;
import com.art.recruitment.artperformance.ui.mine.presenter.MineSignUpPresenter;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

public class MineSignUpFragment extends BaseFragment<MineSignUpPresenter, MineSignUpBean.ContentBean> implements MineSignUpContract {

    @BindView(R.id.mine_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.mine_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mine_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private MineSignUpAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_sign_up;
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
        return null;
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
        mPresenter.applyList(page, 20, "desc");
    }

    @Override
    protected void initView() {
        setEmptyErrorViewData(R.mipmap.img_show_empty, "暂时没有数据");

        autoRefresh();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void returnApplyListBean(final MineSignUpBean.DataBean bean) {

        adapter = new MineSignUpAdapter(getContext(), bean.getContent());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setAdapter(adapter);
        adapter.setOnViewClickListener(new MineSignUpAdapter.OnItemClickListener() {
            @Override
            public void onArrowClickListener(int position, View view) {
                switch (view.getId()){
                    case R.id.mine_sign_clean_textview:
                        mPresenter.cancelRecruitment(bean.getContent().get(position).getId(), bean.getContent().get(position).getRecruitmentId());
                        break;
                    case R.id.mine_sign_chat_imageview:
                        break;
                    default:
                        break;
                }
            }
        });

        resetStateWhenLoadDataSuccess(bean.getContent());

    }

    @Override
    public void returnCancelRecruitmentBean(CancelRecruitmentBean.DataBean bean) {
        ToastUtils.showShort("取消报名成功");
        autoRefresh();
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            resetStateWhenLoadDataFailed(errorCode, message);
        }
    }
}
