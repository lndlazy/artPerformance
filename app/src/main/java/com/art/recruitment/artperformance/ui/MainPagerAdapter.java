package com.art.recruitment.artperformance.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.art.recruitment.common.base.ui.BaseFragment;

import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragmentList;

    public MainPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
