package com.art.recruitment.artperformance.ui.group.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.MineRecruitBean;
import com.art.recruitment.artperformance.bean.mine.PathUrlBean;
import com.art.recruitment.artperformance.ui.group.adapter.MineRecruitViewPagerAdapter;
import com.art.recruitment.artperformance.ui.group.contract.EmploymentContract;
import com.art.recruitment.artperformance.ui.group.contract.MineRecruitContract;
import com.art.recruitment.artperformance.ui.group.fragment.EmploymentFragment;
import com.art.recruitment.artperformance.ui.group.presenter.MineRecruitPresenter;
import com.art.recruitment.artperformance.ui.mine.activity.ChatActivity;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.view.CustomViewPager;
import com.art.recruitment.artperformance.view.TabEntity;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 人员录用
 */
public class MineRecruitActivity extends BaseActivity<MineRecruitPresenter> implements MineRecruitContract {

    @BindView(R.id.mine_recruit_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.mine_recruit_Tablayout)
    CommonTabLayout mTablayout;
    @BindView(R.id.mine_recruit_group_chat_textview)
    TextView mGroupChatTextview;
    @BindView(R.id.mine_recruit_viewpager)
    CustomViewPager mViewpager;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private int[] mIconSelectIds = {
            R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private List<BaseFragment> mFragmentList;
    private EmploymentFragment employmentFragmentInto;
    private EmploymentFragment employmentFragmentOut;
    private String id;
    private String groupID;
    private String retirementName;//招募标题
    private int retireNum;//总录用人数
    private int alreadyRetireNum;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_recruit;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        alreadyRetireNum = 0;
        id = getIntent().getStringExtra("id");
        retireNum = getIntent().getIntExtra("retireNum", 0);
        retirementName = getIntent().getStringExtra("retirementName");

        mTabEntities.add(new TabEntity("已录用", mIconSelectIds[0], mIconUnselectIds[0]));
        mTabEntities.add(new TabEntity("待录用", mIconSelectIds[0], mIconUnselectIds[0]));
        mTablayout.setTabData(mTabEntities);
        mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewpager.setCurrentItem(position);

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        initFragmentList();

        initButtonClick();
    }


    public int getRetireNum() {
        return retireNum;
    }

    public void setRetireNum(int retireNum) {
        this.retireNum = retireNum;
    }

    //增加当前录用人员数量
    public void addAlreadyRetireNum() {
        ++alreadyRetireNum;
    }

    //取消人员录用， 减少当前录用成员数量
    public void decressAlreadyRetireNum() {
        --alreadyRetireNum;
    }

    public int getAlreadyRetireNum() {
        return alreadyRetireNum;
    }

    public void setAlreadyRetireNum(int alreadyRetireNum) {
        this.alreadyRetireNum = alreadyRetireNum;
    }

    private void initButtonClick() {

        //发起群聊
        RxView.
                clicks(mGroupChatTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.chatGroups(id);
                    }
                });

        RxView.
                clicks(mReturnImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });

    }

    private void initFragmentList() {
        mFragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        //已录用
        bundle.putString(BaseConfig.FRAGMENT_TAG_NAME_ADS_TYPE, Constant.APPLY_TYPE_ALREADY);
        bundle.putString("recruitment_id", id);
        employmentFragmentOut = EmploymentFragment.newInstance(bundle);
        employmentFragmentOut.setTransferCallback(this);

        Bundle bundle2 = new Bundle();
        //待录用
        bundle2.putString(BaseConfig.FRAGMENT_TAG_NAME_ADS_TYPE, Constant.APPLY_TYPE_WAIT);
        bundle2.putString("recruitment_id", id);
        employmentFragmentInto = EmploymentFragment.newInstance(bundle2);
        employmentFragmentInto.setTransferCallback(this);
        mFragmentList.add(employmentFragmentOut);
        mFragmentList.add(employmentFragmentInto);

        mViewpager.setOffscreenPageLimit(0);
        mViewpager.setAdapter(new MineRecruitViewPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTablayout.setCurrentTab(position);

                switch (position) {
                    case 0:
                        mGroupChatTextview.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mGroupChatTextview.setVisibility(View.INVISIBLE);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void returnChatGroupsBean(MineRecruitBean.DataBean bean) {

        EaseUser easeUser = new EaseUser(bean.getChatGroupId());
//        easeUser.setAvatar(publisherAvatar);
        easeUser.setNickname(retirementName);
        EaseUserUtils.contactList.put(bean.getChatGroupId(), easeUser);
        EaseUserUtils.save2sp();
//        EaseUserUtils.group_name = retirementName;

        //人员录用， 发起群聊
        Intent chat = new Intent(MineRecruitActivity.this, ChatActivity.class);
        chat.putExtra(EaseConstant.EXTRA_USER_ID, bean.getChatGroupId());  //对方账号
        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP); //群聊
        startActivity(chat);
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }
}
