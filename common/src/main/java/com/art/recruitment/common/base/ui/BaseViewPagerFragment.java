package com.art.recruitment.common.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.art.recruitment.common.base.BasePresenter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;


public abstract class BaseViewPagerFragment<T extends BasePresenter, R extends MultiItemEntity> extends BaseFragment<T, R> {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getFragmentListData() != null && getFragmentListData().size() > 0) {
            autoRefresh();
            getFragmentListData().get(0).setLoadableWhenInit(true);
        }

    }

    /**
     * 返回子类Fragment的list集合
     * */
    protected abstract List<BaseFragment> getFragmentListData();
}
