package com.art.recruitment.artperformance.ui.group.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.ApplyListBean;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.utils.StringsUtils;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class EmploymentAdapter extends BaseRecyclerViewAdapter<ApplyListBean.ContentBean> {

    private String mTitleType;

    public EmploymentAdapter(Context context, List<ApplyListBean.ContentBean> data) {
        super(context, data);
        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_employment_list);
    }

    public void setmTitleType(String mTitleType) {
        this.mTitleType = mTitleType;
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyListBean.ContentBean item) {
        ConstraintLayout wait  = helper.getView(R.id.release_employment_wait_constraintLayout);
        ConstraintLayout already = helper.getView(R.id.release_employment_already_constraintLayout);

        if (TextUtils.isEmpty(mTitleType))
            return;

        switch (mTitleType) {

            case Constant.APPLY_TYPE_ALREADY:
                //已录用
                wait.setVisibility(View.VISIBLE);
                already.setVisibility(View.GONE);
                break;

            case Constant.APPLY_TYPE_WAIT:
                //待录用
                wait.setVisibility(View.GONE);
                already.setVisibility(View.VISIBLE);
                break;

        }

        helper.setText(R.id.mine_recruit_nme_textview, item.getApplyUserName());

        helper.setText(R.id.mine_age, (item.getApplyUserGender() == Constant.GENDER_MALE ? "男" : "女")
                + "    " + item.getApplyUserAge() + "岁");

        SimpleDraweeView headView = helper.getView(R.id.mine_recruit_head_imageview);
        headView.setImageURI(Uri.parse(item.getApplyUserAvatar()));


        //已录用 release_employment_wait_constraintLayout
        helper.addOnClickListener(R.id.mine_recruit_clean_employment_textview);
        helper.addOnClickListener(R.id.mine_recruit_chat_imageview);
        helper.addOnClickListener(R.id.mine_recruit_refuse_textview);
        helper.addOnClickListener(R.id.mine_recruit_employment_textview);
        helper.addOnClickListener(R.id.mine_recruit_employment_chat_imageview);
        helper.addOnClickListener(R.id.mine_recruit_head_imageview);

    }
}
