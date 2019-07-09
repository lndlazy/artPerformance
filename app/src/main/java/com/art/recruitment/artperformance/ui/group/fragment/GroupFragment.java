package com.art.recruitment.artperformance.ui.group.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.ActorLikesBean;
import com.art.recruitment.artperformance.bean.group.CityBean;
import com.art.recruitment.artperformance.bean.group.GroupListBean;
import com.art.recruitment.artperformance.bean.model.UserEntity;
import com.art.recruitment.artperformance.ui.group.activity.GruopDetailActivity;
import com.art.recruitment.artperformance.ui.group.activity.SearchActivity;
import com.art.recruitment.artperformance.ui.group.adapter.GroupAdapter;
import com.art.recruitment.artperformance.ui.group.contract.GroupFragmentContract;
import com.art.recruitment.artperformance.ui.group.presenter.GroupFragmentPresenter;
import com.art.recruitment.artperformance.ui.home.adapter.ContactAdapter;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.utils.SystemUtil;
import com.art.recruitment.common.utils.UIUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * 演员展示
 */
public class GroupFragment extends BaseFragment<GroupFragmentPresenter, GroupListBean.ContentBean> implements GroupFragmentContract {

    private static final String SORT_1 = "likes,desc";
    private static final String SORT_2 = "id,desc";
    @BindView(R.id.group_search_imageview)
    ImageView mSearchImageview;
    @BindView(R.id.group_city_textview)
    TextView mCityTextview;
    @BindView(R.id.group_city_imageview)
    ImageView mCityImageview;
    @BindView(R.id.group_city_constraintLayout)
    ConstraintLayout mCityConstraintLayout;
    @BindView(R.id.group_age_textview)
    TextView mAgeTextview;
    @BindView(R.id.group_age_imageview)
    ImageView mAgeImageview;
    @BindView(R.id.group_age_constraintLayout)
    ConstraintLayout mAgeConstraintLayout;
    @BindView(R.id.group_gender_textview)
    TextView mGenderTextview;
    @BindView(R.id.group_gender_imageview)
    ImageView mGenderImageview;
    @BindView(R.id.group_gender_constraintLayout)
    ConstraintLayout mGenderConstraintLayout;
    @BindView(R.id.group_age_choice_constranintLayout)
    ConstraintLayout mAgeChoiceConstranintLayout;
    @BindView(R.id.group_man_selected_imageview)
    ImageView mManSelectedImageview;
    @BindView(R.id.group_man_selected_constraintLayout)
    ConstraintLayout mManSelectedConstraintLayout;
    @BindView(R.id.group_woman_textview)
    TextView mWomanTextview;
    @BindView(R.id.group_woman_selected_imageview)
    ImageView mWomanSelectedImageview;
    @BindView(R.id.group_woman_selected_constraintLayout)
    ConstraintLayout mWomanSelectedConstraintLayout;
    @BindView(R.id.group_gender_selected_constraintLayout)
    ConstraintLayout mGenderSelectedConstraintLayout;
    @BindView(R.id.group_age_choice_view)
    View mAgeChoiceView;
    @BindView(R.id.group_grnder_choice_view)
    View mGrnderChoiceView;
    @BindView(R.id.group_low_age_edittext)
    EditText mLowAgeEdittext;
    @BindView(R.id.group_high_age_edittext)
    EditText mHighAgeEdittext;
    @BindView(R.id.group_age_determine_textview)
    TextView mAgeDetermineTextview;
    @BindView(R.id.group_indexableLayout)
    IndexableLayout mIndexableLayout;
    @BindView(R.id.group_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.group_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private ContactAdapter mAdapter;
    private List<UserEntity> list;
    private GroupAdapter groupAdapter;
    //    private int maxAge = -1;
//    private int minAge = -1;
    private int cityId = -1;
    private int gender = -1;
    private boolean pages;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group;
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
                    case R.id.constrainLike:
                        mPresenter.actorsLikes(groupAdapter.getData().get(position).getId());
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
    protected void lazyLoad() {
        autoRefresh();
    }

    @Override
    protected void initView() {
        setEmptyErrorViewData(R.mipmap.img_show_empty, "暂无群演展示~");

        autoRefresh();

        initButtonClick();

        initAdapter();
        mCityTextview.setText("城市");
    }

    @Override
    protected void initListRequest(int page) {
        super.initListRequest(page);

        String maxAge = TextUtils.isEmpty(mHighAgeEdittext.getText().toString().trim()) ? "-1"
                : mHighAgeEdittext.getText().toString().trim();
        String minAge = TextUtils.isEmpty(mLowAgeEdittext.getText().toString().trim()) ? "-1"
                : mLowAgeEdittext.getText().toString().trim();


        mPresenter.actorsList(maxAge, minAge, cityId, gender, page, BaseConfig.DEFAULT_PAGE_SIZE,  SORT_1, SORT_2);


//        try {
//            String decode = URLDecoder.decode(SORT_DESC, "utf-8");
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

    private void initButtonClick() {

        RxView.
                clicks(mAgeDetermineTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        String mLowAge = mLowAgeEdittext.getText().toString().trim();
                        String mHighAge = mHighAgeEdittext.getText().toString().trim();

                        if (!TextUtils.isEmpty(mLowAge) && !TextUtils.isEmpty(mHighAge)) {
                            if (isNumeric(mLowAge) && isNumeric(mHighAge)) {
                                if (Integer.parseInt(mLowAge) < Integer.parseInt(mHighAge)) {
                                    mAgeChoiceConstranintLayout.setVisibility(View.GONE);
                                    ageColor(true);
//                                    maxAge = Integer.parseInt(mHighAge);
//                                    minAge = Integer.parseInt(mLowAge);
                                    hideAgeChooseView();

                                    SystemUtil.closeInoutDecorView(getActivity());
                                    autoRefresh();
                                } else {
                                    mLowAgeEdittext.setText("");
                                    mHighAgeEdittext.setText("");
                                    ToastUtils.showShort("最高年龄不可低于最低年龄哦");
                                    ageColor(false);
                                }
                            } else {
                                ToastUtils.showShort("只可以输入整数，请重新输入");
                                ageColor(false);
                            }
                        } else {
                            ToastUtils.showShort("年龄不能为空");
                            ageColor(false);
                        }
                    }
                });

        //选择城市
        RxView.
                clicks(mCityConstraintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (mIndexableLayout.getVisibility() == View.GONE) {
                            mIndexableLayout.setVisibility(View.VISIBLE);
                            hideAgeChooseView();
                            mGenderSelectedConstraintLayout.setVisibility(View.GONE);
                            mGrnderChoiceView.setVisibility(View.GONE);
                            mPresenter.cityList();
                        } else {
                            mIndexableLayout.setVisibility(View.GONE);
                        }
                    }
                });

        //弹出年龄选择框
        RxView.
                clicks(mAgeConstraintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (mAgeChoiceConstranintLayout.getVisibility() == View.GONE) {
                            showAgeChooseView();
                        } else {
                            hideAgeChooseView();
                            autoRefresh();
                        }
                    }
                });

        RxView.
                clicks(mGenderConstraintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (mGenderSelectedConstraintLayout.getVisibility() == View.GONE) {
                            mGenderSelectedConstraintLayout.setVisibility(View.VISIBLE);
                            mGrnderChoiceView.setVisibility(View.VISIBLE);
                            hideAgeChooseView();
                            mIndexableLayout.setVisibility(View.GONE);
                        } else {
                            mGenderSelectedConstraintLayout.setVisibility(View.GONE);
                            mGrnderChoiceView.setVisibility(View.GONE);
                            autoRefresh();
                        }
                    }
                });

        RxView.
                clicks(mManSelectedConstraintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (mManSelectedImageview.getVisibility() == View.GONE) {
                            mManSelectedImageview.setVisibility(View.VISIBLE);
                            mWomanSelectedImageview.setVisibility(View.GONE);

                            mGenderSelectedConstraintLayout.setVisibility(View.GONE);
                            mGrnderChoiceView.setVisibility(View.GONE);
                            hideAgeChooseView();
                            mIndexableLayout.setVisibility(View.GONE);
                            genderColor(true);
                            gender = 1;
                            autoRefresh();
                        } else {
                            mManSelectedImageview.setVisibility(View.GONE);
                            mWomanSelectedImageview.setVisibility(View.GONE);
                            genderColor(false);
                            gender = -1;
                        }

                    }
                });

        RxView.
                clicks(mWomanSelectedConstraintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (mWomanSelectedImageview.getVisibility() == View.GONE) {
                            mWomanSelectedImageview.setVisibility(View.VISIBLE);
                            mManSelectedImageview.setVisibility(View.GONE);

                            mGenderSelectedConstraintLayout.setVisibility(View.GONE);
                            mGrnderChoiceView.setVisibility(View.GONE);
                            hideAgeChooseView();
                            mIndexableLayout.setVisibility(View.GONE);
                            genderColor(true);
                            gender = 2;
                            autoRefresh();
                        } else {
                            mWomanSelectedImageview.setVisibility(View.GONE);
                            mManSelectedImageview.setVisibility(View.GONE);
                            genderColor(false);
                            gender = -1;
                        }

                    }
                });

        RxView.
                clicks(mAgeChoiceView).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mGenderSelectedConstraintLayout.setVisibility(View.GONE);
                        mGrnderChoiceView.setVisibility(View.GONE);
                        hideAgeChooseView();
                        mIndexableLayout.setVisibility(View.GONE);
                    }
                });

        RxView.
                clicks(mGrnderChoiceView).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mGenderSelectedConstraintLayout.setVisibility(View.GONE);
                        mGrnderChoiceView.setVisibility(View.GONE);
                        hideAgeChooseView();
                        mIndexableLayout.setVisibility(View.GONE);
                        autoRefresh();
                    }
                });

        RxView.
                clicks(mSearchImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getContext(), SearchActivity.class));
                    }
                });
    }

    private void hideAgeChooseView() {
        mAgeChoiceConstranintLayout.setVisibility(View.GONE);
        mAgeChoiceView.setVisibility(View.GONE);
    }

    private void showAgeChooseView() {
        mAgeChoiceConstranintLayout.setVisibility(View.VISIBLE);
        mAgeChoiceView.setVisibility(View.VISIBLE);
        mGenderSelectedConstraintLayout.setVisibility(View.GONE);
        mGrnderChoiceView.setVisibility(View.GONE);
        mIndexableLayout.setVisibility(View.GONE);
    }

    /**
     * 城市按钮状态
     */
    public void cityColor(boolean tvScreenIsSelect) {
        mCityTextview.setTextColor(tvScreenIsSelect ? UIUtils.getColor(R.color.color_fd7b25) : UIUtils.getColor(R.color.color_33353a));
        mCityImageview.setImageDrawable(tvScreenIsSelect ? UIUtils.getDrawable(R.mipmap.icon_arrowdown_o) : UIUtils.getDrawable(R.mipmap.icon_arrowdown_b));
    }

    /**
     * 年龄按钮状态
     */
    private void ageColor(boolean tvScreenIsSelect) {
        mAgeTextview.setTextColor(tvScreenIsSelect ? UIUtils.getColor(R.color.color_fd7b25) : UIUtils.getColor(R.color.color_33353a));
        mAgeImageview.setImageDrawable(tvScreenIsSelect ? UIUtils.getDrawable(R.mipmap.icon_arrowdown_o) : UIUtils.getDrawable(R.mipmap.icon_arrowdown_b));
    }

    /**
     * 性别按钮状态
     */
    public void genderColor(boolean tvScreenIsSelect) {
        mGenderTextview.setTextColor(tvScreenIsSelect ? UIUtils.getColor(R.color.color_fd7b25) : UIUtils.getColor(R.color.color_33353a));
        mGenderImageview.setImageDrawable(tvScreenIsSelect ? UIUtils.getDrawable(R.mipmap.icon_arrowdown_o) : UIUtils.getDrawable(R.mipmap.icon_arrowdown_b));
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
//            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void initAdapter() {
        mAdapter = new ContactAdapter(getContext());
        mIndexableLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        mIndexableLayout.setAdapter(mAdapter);
        mIndexableLayout.setOverlayStyle_Center();

        mIndexableLayout.setCompareMode(IndexableLayout.MODE_FAST);

        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {

//                Logger.d("originalPosition::" + originalPosition + ",currentPosition::" + currentPosition);
//
//                Logger.d("nick" + entity.getNick() + ",::" + entity.getCityCode()
//                        + ",," + list.get(originalPosition).getNick()
//                        + ",,," + list.get(currentPosition).getNick());

//                for (int i = 0; i < list.size(); i++) {
//                    list.get(i).setSelect(false);
//                }
//                list.get(currentPosition).setSelect(true);
//                list.get(originalPosition).setSelect(true);
                mCityTextview.setText(entity.getNick());
                cityId = entity.getCityCode();
                cityColor(true);
                autoRefresh();
                mIndexableLayout.setVisibility(View.GONE);
                hideAgeChooseView();
                mGenderSelectedConstraintLayout.setVisibility(View.GONE);
                mGrnderChoiceView.setVisibility(View.GONE);
//                mAdapter.notifyDataSetChanged();
//
//                for (int i = 0; i < list.size(); i++) {
//                    list.get(i).setSelect(false);
//                }
//
//                list.get(originalPosition).setSelect(true);
//
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(originalPosition).isSelect()) {
//                        cityColor(true);
//                        cityId = list.get(i).getCityCode();
//
//                        autoRefresh();
//                        String nick = list.get(i).getNick();
//
//                        mCityTextview.setText(nick);
//                        Logger.d("选择的城市code::" + cityId + ",城市名称::" + nick);
//
//                        mIndexableLayout.setVisibility(View.GONE);
//                        mAgeChoiceConstranintLayout.setVisibility(View.GONE);
//                        mAgeChoiceView.setVisibility(View.GONE);
//                        mGenderSelectedConstraintLayout.setVisibility(View.GONE);
//                        mGrnderChoiceView.setVisibility(View.GONE);
//                    } else {
//                        cityColor(false);
//                    }
//                }

            }
        });
    }

    //城市数据
    @Override
    public void returbCityListBean(List<CityBean.DataBean> bean) {

        list = new ArrayList<>();

        for (int i = 0; i < bean.size(); i++) {
            UserEntity contactEntity = new UserEntity(bean.get(i).getCityName(), bean.get(i).getCityCode());
//            contactEntity.setCityName();
            list.add(contactEntity);
        }
        mAdapter.setDatas(list);
    }

    @Override
    public void returbGroupListBean(final GroupListBean.DataBean bean) {

        resetStateWhenLoadDataSuccess(bean.getContent());

    }

    @Override
    public void returbActorLikesBean(ActorLikesBean.DataBean bean) {
        autoRefresh();
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            resetStateWhenLoadDataFailed(errorCode, message);
        }
    }


}