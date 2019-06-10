package com.art.recruitment.artperformance.ui.dynamic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.dynamic.DynamicListBean;
import com.art.recruitment.artperformance.bean.model.NineGridTestModel;
import com.art.recruitment.artperformance.view.NineGridTestLayout;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.utils.UIUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DynamicAdapter extends BaseRecyclerViewAdapter<DynamicListBean.ContentBean> {

//    private List<NineGridTestModel> mList;

    public DynamicAdapter(Context context, List<DynamicListBean.ContentBean> data) {
        super(context, data);
        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_dynamic_list);
    }

//    public void setList(List<NineGridTestModel> list) {
//        mList = list;
//    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicListBean.ContentBean item) {
        ImageView headImageView = helper.getView(R.id.dynamic_head_imageview);
        Glide.with(mContext).load(item.getPublisherAvatar()).into(headImageView);
        helper.addOnClickListener(R.id.dynamic_comment_imageview);

        //9宫格图片
        NineGridTestLayout view = helper.getView(R.id.dynamic_nineGridTestLayout);
        view.setIsShowAll(false);

        view.setUrlList(new ArrayList<String>());

        if (item.getImagePath() == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setUrlList(item.getImagePath());
        }

        helper.setImageDrawable(R.id.dynamic_give_imageview, UIUtils.getDrawable(
                item.isIsLikes() ? R.mipmap.icon_circle_like_p : R.mipmap.icon_circle_like));

        helper.addOnClickListener(R.id.dynamic_give_imageview);
        helper.addOnClickListener(R.id.ll_content);

        helper.setText(R.id.dynamic_give_textview,
                (item.getLikes() > 0) ? item.getLikes() + "" : UIUtils.getString(R.string.dynamic_give_thumbs_up));

        helper.setText(R.id.dynamic_comment_textView,
                (item.getCommentNumber() > 0) ? item.getCommentNumber() + "" : UIUtils.getString(R.string.dynamic_comment));

        helper.setText(R.id.dynamic_name_textview, item.getPublisherName())
                .setText(R.id.dynamic_time_textview, item.getTimeAgo())
                .setText(R.id.dynamic_content_expandableTextView, item.getContent());

    }
}
