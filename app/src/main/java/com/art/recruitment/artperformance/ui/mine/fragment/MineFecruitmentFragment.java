package com.art.recruitment.artperformance.ui.mine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.MineFecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.RecruitmentOptBean;
import com.art.recruitment.artperformance.ui.dynamic.adapter.DynamicCommentsAdapter;
import com.art.recruitment.artperformance.ui.group.activity.MineRecruitActivity;
import com.art.recruitment.artperformance.ui.group.activity.ReleaseRecruitmentActivity;
import com.art.recruitment.artperformance.ui.mine.adapter.MineFecruitmentAdapter;
import com.art.recruitment.artperformance.ui.mine.adapter.MineSignUpAdapter;
import com.art.recruitment.artperformance.ui.mine.contract.MineFecruitmentContract;
import com.art.recruitment.artperformance.ui.mine.presenter.MineFecruitmentPresenter;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MineFecruitmentFragment extends BaseFragment<MineFecruitmentPresenter, MineFecruitmentBean.ContentBean> implements MineFecruitmentContract {
    @BindView(R.id.mine_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.mine_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mine_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private MineFecruitmentAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_fecruitment;
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
        mPresenter.mineRecruitmentList(page, 20, "desc");
    }

    @Override
    protected void initView() {
        setEmptyErrorViewData(R.mipmap.img_show_empty, "暂时没有数据");
        autoRefresh();
        initButtonClick();
    }

    private void initButtonClick() {
        RxView.
                clicks(mReturnImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getActivity().finish();
                    }
                });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void returnMineFecruitmentBean(final MineFecruitmentBean.DataBean bean) {

        if (adapter==null) {
            adapter = new MineFecruitmentAdapter(getContext(), bean.getContent());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }

        adapter.setOnViewClickListener(new MineFecruitmentAdapter.OnItemClickListener() {
            @Override
            public void onArrowClickListener(int position, View view, String string) {
                switch (view.getId()) {
                    case R.id.mine_next_frame_textview:
                        mPresenter.recruitmentOpt(bean.getContent().get(position).getId(), string);
                        break;
                    case R.id.mine_editorial_recruitment_information_textview:
                        Intent intent1 = new Intent(getContext(), ReleaseRecruitmentActivity.class);
                        intent1.putExtra("release_id", 1);
                        intent1.putExtra("pos", position);
                        intent1.putExtra("id_id", bean.getContent().get(position));
                        startActivity(intent1);
                        break;
                    case R.id.mine_fecruitment_personnel_textview:
                        Intent intent = new Intent(getContext(), MineRecruitActivity.class);
                        intent.putExtra("id", bean.getContent().get(position).getId());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

        resetStateWhenLoadDataSuccess(bean.getContent());
    }

    @Override
    public void returnRecruitmentOptBean(RecruitmentOptBean.DataBean bean) {

    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null){
            resetStateWhenLoadDataFailed(errorCode, message);
        }
    }
}
