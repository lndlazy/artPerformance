package com.art.recruitment.artperformance.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.ActorLikesBean;
import com.art.recruitment.artperformance.bean.group.CityBean;
import com.art.recruitment.artperformance.bean.group.GroupListBean;
import com.art.recruitment.artperformance.bean.model.UserEntity;
import com.art.recruitment.artperformance.ui.group.contract.GroupFragmentContract;
import com.art.recruitment.artperformance.ui.group.presenter.GroupFragmentPresenter;
import com.art.recruitment.artperformance.ui.home.adapter.ContactAdapter;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableHeaderAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;

public class CityActivity extends BaseActivity<GroupFragmentPresenter> implements GroupFragmentContract {

    @BindView(R.id.mine_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.indexableLayout)
    IndexableLayout indexableLayout;
    private Intent intent;
    private ContactAdapter mAdapter;
    private int locationCityCode;
    private String locationCityName;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city;
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
        mPresenter.cityList();
        intent = getIntent();

//        intent.getStringExtra("");
//        intent.getIntExtra("")

        initAdapter();
        onlisten();

        initButtonClick();
    }

    private void initButtonClick() {
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


    public void initAdapter() {
        mAdapter = new ContactAdapter(this);
        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        indexableLayout.setAdapter(mAdapter);
        indexableLayout.setOverlayStyle_Center();

        locationCityCode = SPUtils.getInstance().getInt(BaseConfig.BaseSPKey.LOCATION_CITY_CODE);
        locationCityName = SPUtils.getInstance().getString(BaseConfig.BaseSPKey.LOCATION_CITY_NAME);

        if (locationCityCode !=-1 && !TextUtils.isEmpty(locationCityName)) {
            addHeadView(locationCityCode, locationCityName);
        }

        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);

    }

    private void addHeadView(int locationCityCode, String locationCityName) {
        List<UserEntity> datas = new ArrayList<>();
        UserEntity entity = new UserEntity(locationCityName, locationCityCode);
        datas.add(entity);
        SimpleHeaderAdapter<UserEntity> userEntitySimpleHeaderAdapter =
                new SimpleHeaderAdapter<>(mAdapter, null, "当前定位城市", datas);
        indexableLayout.addHeaderAdapter(userEntitySimpleHeaderAdapter);
    }

    public void onlisten() {
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {

                if (originalPosition >= 0) {
                    intent.putExtra("city", entity.getNick());
                    intent.putExtra("code", entity.getCityCode());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {

                    intent.putExtra("city", locationCityName);
                    intent.putExtra("code", locationCityCode);
                    setResult(RESULT_OK, intent);
                    finish();
//                    ToastUtils.showShort("选中Header/Footer:" + entity.getNick() + "  当前位置:" + currentPosition);
                }
            }
        });
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

    @Override
    public void returbCityListBean(List<CityBean.DataBean> bean) {
        List<UserEntity> list = new ArrayList<>();

        for (int i = 0; i < bean.size(); i++) {
            UserEntity contactEntity = new UserEntity(bean.get(i).getCityName(), bean.get(i).getCityCode());
            list.add(contactEntity);
        }

        mAdapter.setDatas(list);
    }

    @Override
    public void returbGroupListBean(GroupListBean.DataBean bean) {

    }

    @Override
    public void returbActorLikesBean(ActorLikesBean.DataBean bean) {

    }
}
