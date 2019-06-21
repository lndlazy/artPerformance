package com.art.recruitment.artperformance.ui.dynamic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.dynamic.DynamicListBean;
import com.art.recruitment.artperformance.bean.model.NineGridTestModel;
import com.art.recruitment.artperformance.ui.dynamic.activity.PlusImageActivity;
import com.art.recruitment.artperformance.ui.dynamic.contract.MainConstant;
import com.art.recruitment.artperformance.view.NineGridLayout;
import com.art.recruitment.artperformance.view.NineGridTestLayout;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.utils.UIUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class DynamicAdapter extends BaseRecyclerViewAdapter<DynamicListBean.ContentBean> {

//    private List<NineGridTestModel> mList;

    public DynamicAdapter(Context context, List<DynamicListBean.ContentBean> data) {
        super(context, data);
        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_dynamic_list);
    }

    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    //    public void setList(List<NineGridTestModel> list) {
//        mList = list;
//    }

    @Override
    protected void convert(BaseViewHolder helper, final DynamicListBean.ContentBean item) {
        ImageView headImageView = helper.getView(R.id.dynamic_head_imageview);
        Glide.with(mContext).load(item.getPublisherAvatar()).into(headImageView);
        helper.addOnClickListener(R.id.dynamic_comment_imageview);
        helper.addOnClickListener(R.id.dynamic_share_constraintLayout);//分享

        //9宫格图片
        NineGridTestLayout view = helper.getView(R.id.dynamic_nineGridTestLayout);
        view.setIsShowAll(false);

        view.setUrlList(new ArrayList<String>());
        view.setFragment(fragment);

//        String videoPreview = item.getVideoPreview();
//        if (!TextUtils.isEmpty(videoPreview)) {
//            //视频
//            view.setVisibility(View.VISIBLE);
//            ArrayList<String> videoList = new ArrayList<>();
//            videoList.add(item.getVideoPreview());
//            view.setUrlList(videoList);
//            view.setVideoUrl(item.getVideoPath());
//        }else {


        if (item.getImagePath() != null || !TextUtils.isEmpty(item.getVideoPreview())) {

            view.setVisibility(View.VISIBLE);
            if (item.getImagePath() != null && item.getImagePath().size() > 0) {
                view.setUrlList(item.getImagePath());
//                view.setVideo(false);
            } else if (!TextUtils.isEmpty(item.getVideoPreview())) {
                List<String> pics = new ArrayList<>();
                pics.add(item.getVideoPreview());
//                view.setVideo(true);
                view.setUrlList(pics);
            }

        } else {
            view.setVisibility(View.GONE);
        }

        view.setOnItemClick(new NineGridLayout.OnItemClick() {
            @Override
            public void onclick(int i) {

                if (!TextUtils.isEmpty(item.getVideoPreview())) {
                    Logger.d("播放的视频列表:::" + item.getVideoPath());
                    PictureSelector.create(fragment).externalPictureVideo(item.getVideoPath());
                } else {

                    Intent intent = new Intent(mContext, PlusImageActivity.class);
                    intent.putStringArrayListExtra(MainConstant.IMG_LIST, (ArrayList<String>) item.getImagePath());
                    intent.putExtra(MainConstant.POSITION, i);
                    intent.putExtra("canDelete", false);
                    mContext.startActivity(intent);
                }

            }
        });
//            view.setVideoUrl(null);
//            //图片
//            if (item.getImagePath() == null) {
//                view.setVisibility(View.GONE);
//            } else {
//                view.setVisibility(View.VISIBLE);
//                view.setUrlList(item.getImagePath());
//            }

//        }

//        view.onClickImage();

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
