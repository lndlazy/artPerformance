package com.art.recruitment.artperformance.ui.group.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.GroupListBean;
import com.art.recruitment.artperformance.ui.home.adapter.HomeAdapter;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.utils.UIUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class GroupAdapter extends BaseRecyclerViewAdapter<GroupListBean.ContentBean> {

    public GroupAdapter(Context context, List data) {
        super(context, data);
        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_group_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupListBean.ContentBean item) {
        helper.setText(R.id.group_name_textview, item.getName())
                .setText(R.id.group_number_textview, item.getLikes() + "");
        ImageView s = helper.getView(R.id.group_photo_imageview);

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.error(item.getGender() == Constant.GENDER_MALE ? R.mipmap.ic_male_error : R.mipmap.ic_female_error);
        Glide.with(mContext).load(item.getPrimaryPhotoView().get(0)).apply(options).into(s);

        helper.addOnClickListener(R.id.group_photo_imageview);
        helper.addOnClickListener(R.id.constrainLike);

        helper.setImageDrawable(R.id.group_like_imageview, UIUtils.getDrawable(
                item.isIsLikes() ? R.mipmap.icon_circle_like_p : R.mipmap.icon_circle_like));

//        if (item.isIsLikes()) {
//            helper.setImageDrawable(R.id.group_like_imageview, UIUtils.getDrawable(
//                    item.isIsLikes()? R.mipmap.icon_circle_like_p: R.mipmap.icon_circle_like));
//        } else {
//            helper.setImageDrawable(R.id.group_like_imageview, UIUtils.getDrawable(R.mipmap.icon_circle_like));
//        }
        helper.setText(R.id.group_woman_age_textview, item.getAge() + "");

        helper.setBackgroundRes(R.id.group_woman_constraintlayout,
                item.getGender() == Constant.GENDER_MALE ? R.drawable.shape_group_man_background
                        : R.drawable.shape_group_woman_background);

    }


}
