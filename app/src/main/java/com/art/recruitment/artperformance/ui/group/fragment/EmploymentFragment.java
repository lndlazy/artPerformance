package com.art.recruitment.artperformance.ui.group.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.ApplyListBean;
import com.art.recruitment.artperformance.bean.group.CencelHiringBean;
import com.art.recruitment.artperformance.bean.group.HiringBean;
import com.art.recruitment.artperformance.ui.group.activity.GruopDetailActivity;
import com.art.recruitment.artperformance.ui.group.activity.MineRecruitActivity;
import com.art.recruitment.artperformance.ui.group.adapter.EmploymentAdapter;
import com.art.recruitment.artperformance.ui.group.contract.EmploymentContract;
import com.art.recruitment.artperformance.ui.group.presenter.EmploymentPresenter;
import com.art.recruitment.artperformance.ui.mine.activity.ChatActivity;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

/**
 * 人员录用
 */
public class EmploymentFragment extends BaseFragment<EmploymentPresenter, ApplyListBean.ContentBean> implements EmploymentContract {

    @BindView(R.id.release_employment_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.release_employment_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private MineRecruitActivity mTransferCallback;
    private String mTitleType;
    private EmploymentAdapter mAdapter;
    private String recruitment_id;
    private MineRecruitActivity activity;
    private Dialog retireFullDialog;

    public static EmploymentFragment newInstance(Bundle bundle) {
        EmploymentFragment mFragment = new EmploymentFragment();
        mFragment.setArguments(bundle);
        return mFragment;
    }

    public void setTransferCallback(MineRecruitActivity mTransferCallback) {
        this.mTransferCallback = mTransferCallback;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_employment;
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
        mAdapter = new EmploymentAdapter(mContext, mDataList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.mine_recruit_clean_employment_textview://取消录用
                        mPresenter.cencelHiring(mAdapter.getData().get(position).getRecruitmentId(), mAdapter.getData().get(position).getId());
                        break;

                    case R.id.mine_recruit_chat_imageview:
                        //已录用 聊天

                        if (mAdapter.getData().get(position).getIm() == null) {
                            ToastUtils.showShort("数据错误");
                            return;
                        }

                        userinfo(position);

                        Intent chat = new Intent(getContext(), ChatActivity.class);
                        chat.putExtra(EaseConstant.EXTRA_USER_ID, mAdapter.getData().get(position).getIm().getUsername());  //对方账号
                        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE); //单聊模式
                        startActivity(chat);

                        break;
                    case R.id.mine_recruit_refuse_textview:
                        mPresenter.cencelHiring(mAdapter.getData().get(position).getRecruitmentId(), mAdapter.getData().get(position).getId());
                        break;
                    case R.id.mine_recruit_employment_textview:
                        //录用

                        Logger.d("已录用人数::" + activity.getAlreadyRetireNum() + ",需要录用的人数::" + activity.getRetireNum());
                        if (activity != null && activity.getAlreadyRetireNum() >= activity.getRetireNum()) {
                            showNoticeAlert(position);
                            return;
                        }


                        mPresenter.hiring(mAdapter.getData().get(position).getRecruitmentId()
                                , mAdapter.getData().get(position).getId(), true);
                        break;
                    case R.id.mine_recruit_employment_chat_imageview:
                        //待录用 聊天

                        if (mAdapter.getData().get(position).getIm() == null) {
                            ToastUtils.showShort("数据错误");
                            return;
                        }
                        userinfo(position);

                        Intent chat2 = new Intent(getContext(), ChatActivity.class);
                        chat2.putExtra(EaseConstant.EXTRA_USER_ID, mAdapter.getData().get(position).getIm().getUsername());  //对方账号
//                        chat2.putExtra(EaseConstant.EXTRA_USER_NAME, mAdapter.getData().get(position).getApplyUserName());  //对方账号
                        chat2.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE); //单聊模式
                        startActivity(chat2);
                        break;

                    case R.id.mine_recruit_head_imageview://点击头像 进入演员详情页面
                        Intent intent = new Intent(getContext(), GruopDetailActivity.class);
                        intent.putExtra("group_id", mAdapter.getData().get(position).getApplyUserId());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        return mAdapter;
    }

    /**
     * @param position
     */
    private void showNoticeAlert(final int position) {

        View inflate = View.inflate(getContext(), R.layout.dialog_retirement, null);

        TextView tv_cancle = inflate.findViewById(R.id.tv_cancle);
        TextView tv_add = inflate.findViewById(R.id.tv_add);

        retireFullDialog = DialogWrapper.
                customViewDialog().
                context(getContext()).
                contentView(inflate).
                cancelable(true, true).
                build();

        retireFullDialog.show();

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (retireFullDialog != null && retireFullDialog.isShowing())
                    retireFullDialog.dismiss();
            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (retireFullDialog != null && retireFullDialog.isShowing())
                    retireFullDialog.dismiss();

                mPresenter.hiring(mAdapter.getData().get(position).getRecruitmentId()
                        , mAdapter.getData().get(position).getId(), true);
            }
        });
    }

    private void userinfo(int position) {
        EaseUser easeUser = new EaseUser(mAdapter.getData().get(position).getIm().getUsername());
        easeUser.setAvatar(mAdapter.getData().get(position).getApplyUserAvatar());
        easeUser.setNickname(mAdapter.getData().get(position).getApplyUserName());
        EaseUserUtils.contactList.put(mAdapter.getData().get(position).getIm().getUsername(), easeUser);
        EaseUserUtils.save2sp();
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
        activity = (MineRecruitActivity) getActivity();

        setEmptyErrorViewData(R.mipmap.img_show_empty, "暂时没有数据");

        if (getArguments() == null)
            return;

        mTitleType = getArguments().getString(BaseConfig.FRAGMENT_TAG_NAME_ADS_TYPE);

        if (mAdapter != null)
            mAdapter.setmTitleType(mTitleType);

        recruitment_id = getArguments().getString("recruitment_id");
//        if (mTitleType == 1){
//
//        }else if (mTitleType == 2){
//
//        }

        mPresenter.applyList(recruitment_id, mTitleType);
    }

    @Override
    protected void lazyLoad() {
        autoRefresh();
    }

    @Override
    protected void initListRequest(int page) {
        super.initListRequest(page);

//        com.orhanobut.logger.Logger.d("请求参数:mCityCode:" + mCityCode + ",mSearch:" + mSearch + ",page:" + page + ",mSort:" + mSort);
        mPresenter.applyList(recruitment_id, mTitleType);
//        mPresenter.recuitList(mCityCode, mSearch, page, BaseConfig.DEFAULT_PAGE_SIZE, mSort);
    }


    @Override
    public void returnApplyListBean(final ApplyListBean.DataBean bean) {

        if (bean == null)
            return;

        switch (mTitleType) {

            case Constant.APPLY_TYPE_ALREADY://已录用

                if (bean.getContent() != null) {
                    //已经录用的个数
                    int size = bean.getContent().size();

                    Logger.d("已录用的人数::" + size);
                    if (activity != null)
                        activity.setAlreadyRetireNum(size);
                }

                break;

            case Constant.APPLY_TYPE_WAIT://待录用

                break;

        }

        resetStateWhenLoadDataSuccess(bean.getContent());

    }

    @Override
    public void returnHiringBean(HiringBean.DataBean bean) {

        ToastUtils.showShort("录用成功");

        if (activity != null)
            activity.addAlreadyRetireNum();
        mPresenter.applyList(recruitment_id, mTitleType);

    }

    @Override
    public void returnCencelHiringBean(CencelHiringBean.DataBean bean) {

        //取消录用成功
        ToastUtils.showShort("取消成功");
        if (activity != null)
            activity.decressAlreadyRetireNum();
        mPresenter.applyList(recruitment_id, mTitleType);
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            resetStateWhenLoadDataFailed(errorCode, message);
        }
    }
}
