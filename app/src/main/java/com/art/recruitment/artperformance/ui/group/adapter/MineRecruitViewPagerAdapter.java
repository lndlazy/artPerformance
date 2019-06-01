package com.art.recruitment.artperformance.ui.group.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.art.recruitment.common.base.adapter.BaseFragmentPagerAdapter;
import com.art.recruitment.common.base.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @company: Coolbit
 * created by {Android-Dev03} on 2018/4/8 9:39
 *
 * @desc:
 */

public class MineRecruitViewPagerAdapter extends FragmentPagerAdapter {

    protected List<BaseFragment> fragmentSets;

    public MineRecruitViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentSets){
        super(fm);
        this.fragmentSets = fragmentSets;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentSets.get(i);
    }

    @Override
    public int getCount() {
        return fragmentSets.size();
    }
}
