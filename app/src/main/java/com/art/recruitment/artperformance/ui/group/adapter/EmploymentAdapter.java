package com.art.recruitment.artperformance.ui.group.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.ApplyListBean;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class EmploymentAdapter extends BaseRecyclerViewAdapter<ApplyListBean.ContentBean> {

    private int mTitleType;

    public EmploymentAdapter(Context context, List<ApplyListBean.ContentBean> data, int mTitleType) {
        super(context, data);
        this.mTitleType = mTitleType;
        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_employment_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyListBean.ContentBean item) {
        ConstraintLayout wait = helper.getView(R.id.release_employment_wait_constraintLayout);
        ConstraintLayout already = helper.getView(R.id.release_employment_already_constraintLayout);

        if (mTitleType == 1){
            wait.setVisibility(View.VISIBLE);
            already.setVisibility(View.GONE);
        } else if (mTitleType == 2) {
            wait.setVisibility(View.GONE);
            already.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.mine_recruit_nme_textview, item.getApplyUserName())
                .setText(R.id.mine_recruit_head_imageview, item.getApplyUserAvatar())
                .setText(R.id.mine_recruit_age_textview, item.getApplyUserAge());

        helper.addOnClickListener(R.id.mine_recruit_clean_employment_textview);
        helper.addOnClickListener(R.id.mine_recruit_chat_imageview);
        helper.addOnClickListener(R.id.mine_recruit_refuse_textview);
        helper.addOnClickListener(R.id.mine_recruit_employment_textview);
        helper.addOnClickListener(R.id.mine_recruit_employment_chat_imageview);

    }
}
