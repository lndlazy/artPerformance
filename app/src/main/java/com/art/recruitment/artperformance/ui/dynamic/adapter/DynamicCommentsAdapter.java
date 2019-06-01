package com.art.recruitment.artperformance.ui.dynamic.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentsBean;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class DynamicCommentsAdapter extends BaseRecyclerViewAdapter<DynamicCommentsBean.ContentBean> {

    public DynamicCommentsAdapter(Context context, List<DynamicCommentsBean.ContentBean> data) {
        super(context, data);
        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_dynamic_detail_list);

    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicCommentsBean.ContentBean item) {
        ImageView view = helper.getView(R.id.dynamic_detail_head_imageview);
        Glide.with(mContext).load(item.getCommentUserAvatar()).into(view);

        helper.setText(R.id.dynamic_detail_name_textview, item.getCommentUserName())
                .setText(R.id.dynamic_detail_time_textview, item.getCommentTime())
                .setText(R.id.dynamic_detail_coutent_textview, item.getCommentContent());
    }
}
