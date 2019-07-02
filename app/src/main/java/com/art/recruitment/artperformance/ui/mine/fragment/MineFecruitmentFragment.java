package com.art.recruitment.artperformance.ui.mine.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.MineFecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.RecruitmentOptBean;
import com.art.recruitment.artperformance.ui.group.activity.MineRecruitActivity;
import com.art.recruitment.artperformance.ui.group.activity.ReleaseRecruitmentActivity;
import com.art.recruitment.artperformance.ui.mine.adapter.MineFecruitmentCommAdapter;
import com.art.recruitment.artperformance.ui.mine.contract.MineFecruitmentContract;
import com.art.recruitment.artperformance.ui.mine.presenter.MineFecruitmentPresenter;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 我的招募列表
 */
public class MineFecruitmentFragment extends BaseFragment<MineFecruitmentPresenter, MineFecruitmentBean.ContentBean> implements MineFecruitmentContract {

    @BindView(R.id.mine_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.mine_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mine_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private MineFecruitmentCommAdapter commAdapter;
    private MineFecruitmentBean.ContentBean contentBean;
    //    private MineFecruitmentAdapter adapter;
//    private int editorPosition;

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

        commAdapter = new MineFecruitmentCommAdapter(getActivity(), mDataList);
        commAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        commAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.mine_next_frame_textview://上架  下架

                        contentBean = commAdapter.getData().get(position);

                        mPresenter.recruitmentOpt(commAdapter.getData().get(position).getId(),
                                commAdapter.getData().get(position).getFrontendFlag() == 1 ? Constant.MSG_ACTION_DOWN
                                        : Constant.MSG_ACTION_UP
                        );
                        contentBean.setFrontendFlag(contentBean.getFrontendFlag() == 1 ? 0 : 1);
                        commAdapter.notifyItemChanged(position);

                        break;
                    case R.id.mine_editorial_recruitment_information_textview:

                        //编辑招募信息
//                            editorPosition = position;
                        Intent intent1 = new Intent(getContext(), ReleaseRecruitmentActivity.class);
                        intent1.putExtra("release_id", 1);
                        intent1.putExtra("pos", position);
                        intent1.putExtra("id_id", commAdapter.getData().get(position));
                        startActivity(intent1);

                        break;
                    case R.id.mine_fecruitment_personnel_textview:
                        //人员录用
                        Intent intent = new Intent(getContext(), MineRecruitActivity.class);
                        intent.putExtra("retirementName", commAdapter.getData().get(position).getTitle());
                        intent.putExtra("retireNum", commAdapter.getData().get(position).getApplyNumber());
                        intent.putExtra("id", commAdapter.getData().get(position).getId());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        return commAdapter;
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
        mPresenter.mineRecruitmentList(page, BaseConfig.DEFAULT_PAGE_SIZE, Constant.SORT_DESC);
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
    public void returnMineFecruitmentBean(final MineFecruitmentBean.DataBean bean, int page) {

//        Logger.d("我的招募列表====");
        if (page == 0) {
//            commAdapter = new MineFecruitmentCommAdapter(getContext(), bean.getContent());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//            mRecyclerView.setAdapter(commAdapter);
        }

//        commAdapter.setOnViewClickListener(new MineFecruitmentAdapter.OnItemClickListener() {
//            @Override
//            public void onArrowClickListener(int position, View view, String string) {
//                switch (view.getId()) {
//                    case R.id.mine_next_frame_textview:
//                        mPresenter.recruitmentOpt(bean.getContent().get(position).getId(), string);
//                        break;
//                    case R.id.mine_editorial_recruitment_information_textview:
//                        Intent intent1 = new Intent(getContext(), ReleaseRecruitmentActivity.class);
//                        intent1.putExtra("release_id", 1);
//                        intent1.putExtra("pos", position);
//                        intent1.putExtra("id_id", bean.getContent().get(position));
//                        startActivity(intent1);
//                        break;
//                    case R.id.mine_fecruitment_personnel_textview:
//                        Intent intent = new Intent(getContext(), MineRecruitActivity.class);
//                        intent.putExtra("id", bean.getContent().get(position).getId());
//                        startActivity(intent);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });

        resetStateWhenLoadDataSuccess(bean.getContent());
    }

    @Override
    public void returnRecruitmentOptBean(RecruitmentOptBean.DataBean bean) {


    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            resetStateWhenLoadDataFailed(errorCode, message);
        }
    }
}
