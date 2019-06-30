package com.art.recruitment.artperformance.ui.mine.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.CancelRecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.MineRecruitBean;
import com.art.recruitment.artperformance.bean.mine.MineSignUpBean;
import com.art.recruitment.artperformance.ui.home.activity.RecruitmentInformationActivity;
import com.art.recruitment.artperformance.ui.mine.activity.ChatActivity;
import com.art.recruitment.artperformance.ui.mine.adapter.MineSignUpMAdapter;
import com.art.recruitment.artperformance.ui.mine.contract.MineSignUpContract;
import com.art.recruitment.artperformance.ui.mine.presenter.MineSignUpPresenter;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

//我的报名列表
public class MineSignUpFragment extends BaseFragment<MineSignUpPresenter, MineSignUpBean.ContentBean> implements MineSignUpContract {

    @BindView(R.id.mine_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.mine_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mine_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    //    private MineSignUpAdapter adapter;
    private MineSignUpMAdapter mAdapter;
    private String publisherAvatar;

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
        mAdapter = new MineSignUpMAdapter(getContext(), mDataList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.mine_sign_clean_textview:
                        //取消报名
                        mPresenter.cancelRecruitment(mAdapter.getData().get(position).getId(), mAdapter.getData().get(position).getRecruitmentId());
                        break;
                    case R.id.mine_sign_chat_imageview:
                        //聊天
                        //通过recruitmentId获取groupId
//                        mPresenter.chatGroups(mAdapter.getData().get(position).getRecruitmentId());
                        if (mAdapter.getData().get(position) != null
                                && mAdapter.getData().get(position).getImSimpleInfo() != null) {

                            EaseUser easeUser = new EaseUser(mAdapter.getData().get(position).getImSimpleInfo().getUsername());
                            easeUser.setAvatar(publisherAvatar);
                            easeUser.setNickname(mAdapter.getData().get(position).getPublisherName());
                            EaseUserUtils.contactList.put(mAdapter.getData().get(position).getImSimpleInfo().getUsername(), easeUser);
                            EaseUserUtils.save2sp();

                            Intent chat = new Intent(getContext(), ChatActivity.class);
                            chat.putExtra(EaseConstant.EXTRA_USER_ID, mAdapter.getData().get(position).getImSimpleInfo().getUsername());  //对方账号
//                        chat.putExtra(EaseConstant.EXTRA_USER_NAME, mAdapter.getData().get(position).getApplyUserName());  //对方账号
                            chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE); //单聊模式
                            startActivity(chat);
                        } else {
                            ToastUtils.showShort("数据错误");
                        }

                        break;

                    case R.id.enter_next:
                        //进入详情页面
                        Intent intent = new Intent(getContext(), RecruitmentInformationActivity.class);
                        intent.putExtra("recruitmentId", mAdapter.getData().get(position).getRecruitmentId());
                        intent.putExtra("home_name", mAdapter.getData().get(position).getPublisherName());
                        startActivity(intent);

                        break;
                }
            }
        });
        return mAdapter;
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
        mPresenter.applyList(page, BaseConfig.DEFAULT_PAGE_SIZE, Constant.SORT_DESC);
    }

    @Override
    protected void initView() {
        setEmptyErrorViewData(R.mipmap.img_show_empty, "暂时没有数据");
        autoRefresh();

        RxView.
                clicks(mReturnImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (getActivity() != null)
                            getActivity().finish();
                    }
                });

    }

    @Override
    protected void lazyLoad() {
        autoRefresh();
    }

    @Override
    public void returnApplyListBean(final MineSignUpBean.DataBean bean, int page) {

//        adapter = new MineSignUpAdapter(getContext(), bean.getContent());
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

//        mRecyclerView.setAdapter(adapter);
//        adapter.setOnViewClickListener(new MineSignUpAdapter.OnItemClickListener() {
//            @Override
//            public void onArrowClickListener(int position, View view) {
//                switch (view.getId()){
//                    case R.id.mine_sign_clean_textview:
//                        mPresenter.cancelRecruitment(bean.getContent().get(position).getId(), bean.getContent().get(position).getRecruitmentId());
//                        break;
//                    case R.id.mine_sign_chat_imageview:
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });

        if (page == 0) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }


        publisherAvatar = bean.getPublisherAvatarView();

        resetStateWhenLoadDataSuccess(bean.getContent());

    }

    @Override
    public void returnCancelRecruitmentBean(CancelRecruitmentBean.DataBean bean) {
        ToastUtils.showShort("取消报名成功");
        autoRefresh();
    }

    @Override
    public void returnChatGroupsBean(MineRecruitBean.DataBean bean) {
//        Intent chat = new Intent(getContext(), ChatActivity.class);
//        chat.putExtra(EaseConstant.EXTRA_USER_ID, bean.getChatGroupId());  //对方账号
//        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP); //群聊模式
//        startActivity(chat);
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            resetStateWhenLoadDataFailed(errorCode, message);
        }
    }
}
