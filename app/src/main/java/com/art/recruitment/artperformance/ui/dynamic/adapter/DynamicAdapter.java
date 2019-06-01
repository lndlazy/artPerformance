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

    private List<NineGridTestModel> mList;

    public DynamicAdapter(Context context, List<DynamicListBean.ContentBean> data) {
        super(context, data);
        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_dynamic_list);
    }

    public void setList(List<NineGridTestModel> list) {
        mList = list;
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicListBean.ContentBean item) {
        ImageView headImageView = helper.getView(R.id.dynamic_head_imageview);
        Glide.with(mContext).load(item.getPublisherAvatar()).into(headImageView);
        helper.addOnClickListener(R.id.dynamic_comment_imageview);

        NineGridTestLayout view = helper.getView(R.id.dynamic_nineGridTestLayout);

        mList = new ArrayList<>();
        NineGridTestModel model1 = new NineGridTestModel();
        for (int i = 0; i < item.getImagePath().size(); i++) {

            if (item.getImagePath().size() > 0){
                model1.urlList.add(item.getImagePath().get(i));
            }
        }
        mList.add(model1);
        for (int i = 0; i < mList.size(); i++) {
            view.setIsShowAll(mList.get(i).isShowAll);
            view.setUrlList(mList.get(i).urlList);
        }


        if (item.isIsLikes()) {
            helper.setImageDrawable(R.id.dynamic_give_imageview, UIUtils.getDrawable(R.mipmap.icon_circle_like_p));
        } else {
            helper.setImageDrawable(R.id.dynamic_give_imageview, UIUtils.getDrawable(R.mipmap.icon_circle_like));
        }
        helper.addOnClickListener(R.id.dynamic_give_imageview);

        if (item.getLikes() > 0){
            helper.setText(R.id.dynamic_give_textview, item.getLikes() + "");
        } else {
            helper.setText(R.id.dynamic_give_textview, UIUtils.getString(R.string.dynamic_give_thumbs_up));
        }

        if (item.getCommentNumber() > 0){
            helper.setText(R.id.dynamic_comment_textView, item.getCommentNumber() + "");
        } else {
            helper.setText(R.id.dynamic_comment_textView, UIUtils.getString(R.string.dynamic_comment));
        }

        helper.setText(R.id.dynamic_name_textview, item.getPublisherName())
                .setText(R.id.dynamic_time_textview, item.getTimeAgo())
                .setText(R.id.dynamic_content_expandableTextView, item.getContent());

    }
}
