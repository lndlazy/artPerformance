package com.art.recruitment.common.base;

import android.content.Context;

import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxManager;


public abstract class BasePresenter<T extends BaseView>{

    private BaseActivity mActivity;
    private BaseFragment mFragment;

    public Context mContext;



    public T mView;  //View层
    public RxManager mRxManager = new RxManager();  //管理Subscription,解除Rxjava订阅

    public void setVM(T v) {
        this.mView = v;
    }

    public void setActivity(BaseActivity activity){
        this.mActivity = activity;
    }

    public void setFragment(BaseFragment fragment){
        this.mFragment = fragment;
    }

    public BaseActivity getActivity(){
        return mActivity;
    }

    public BaseFragment getFragment(){
        return mFragment;
    }

    public RxManager getRxManager(){
        return mRxManager;
    }

    public void onDestroy() {
        mActivity=null;
        mFragment=null;
        mView=null;
        mRxManager.clear();
    }
}
