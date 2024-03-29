package com.art.recruitment.common.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.art.recruitment.common.base.ui.BaseFragment;

import java.util.List;


public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    protected List<BaseFragment> fragmentSets;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentSets){
        super(fm);
        this.fragmentSets = fragmentSets;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentSets.get(position);
    }

    @Override
    public int getCount() {
        return fragmentSets.size();
    }
}
