package com.art.recruitment.artperformance.ui.mine.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.MineDynamicBean;
import com.art.recruitment.artperformance.bean.model.NineGridTestModel;
import com.art.recruitment.artperformance.ui.mine.ImageModel;
import com.art.recruitment.artperformance.view.NineGridTestLayout;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.utils.UIUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MineDynamicAdapter extends BaseRecyclerViewAdapter<MineDynamicBean.ContentBean> {

//    private List<NineGridTestModel> mList;
    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
    public MineDynamicAdapter(Context context, List<MineDynamicBean.ContentBean> data) {
        super(context, data);
        addItemType(ImageModel.TYPE_IMAGE_ADD, R.layout.item_mine_dynamic_list);
    }

//    public void setList(List<NineGridTestModel> list) {
//        mList = list;
//    }


    @Override
    protected void convert(BaseViewHolder helper, MineDynamicBean.ContentBean item) {

//        ImageView headImageView = helper.getView(R.id.dynamic_head_imageview);
//        Glide.with(mContext).load(item.getPublisherAvatar()).into(headImageView);
//        helper.addOnClickListener(R.id.dynamic_comment_imageview);

        //9宫格图片
        NineGridTestLayout view = helper.getView(R.id.mine_dynamic_nineGridTestLayout);
        view.setIsShowAll(false);

        view.setUrlList(new ArrayList<String>());
        view.setFragment(fragment);

        String videoPreview = item.getVideoPreview();

        if (!TextUtils.isEmpty(videoPreview)) {
            //视频
            view.setVisibility(View.VISIBLE);
            ArrayList<String> videoList = new ArrayList<>();
            videoList.add(item.getVideoPreview());
            view.setUrlList(videoList);
            view.setVideoUrl(item.getVideoPath());
        }else {
            //图片
            if (item.getImagePath() == null) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
                view.setUrlList(item.getImagePath());
            }

        }

//        helper.setImageDrawable(R.id.dynamic_give_imageview, UIUtils.getDrawable(
//                item.isIsLikes() ? R.mipmap.icon_circle_like_p : R.mipmap.icon_circle_like));

//        helper.addOnClickListener(R.id.dynamic_give_imageview);
//        helper.addOnClickListener(R.id.ll_content);

//        helper.setText(R.id.dynamic_give_textview,
//                (item.getLikes() > 0) ? item.getLikes() + "" : UIUtils.getString(R.string.dynamic_give_thumbs_up));

//        helper.setText(R.id.dynamic_comment_textView,
//                (item.getCommentNumber() > 0) ? item.getCommentNumber() + "" : UIUtils.getString(R.string.dynamic_comment));

//        helper.setText(R.id.dynamic_name_textview, item.getPublisherName())
//                .setText(R.id.dynamic_time_textview, item.getTimeAgo())
//                .setText(R.id.dynamic_content_expandableTextView, item.getContent());



        helper.setText(R.id.mine_dynamic_date_textview, item.getPublishDate())
                .setText(R.id.mine_dynamic_time_textview, item.getPublishTime())
              .setText(R.id.mine_dynamic_expandableTextView, item.getContent());
//
//        NineGridTestLayout view = helper.getView(R.id.mine_dynamic_nineGridTestLayout);
//
//        mList = new ArrayList<>();
//        NineGridTestModel model1 = new NineGridTestModel();
//        for (int i = 0; i < item.getImagePath().size(); i++) {
//
//            if (item.getImagePath().size() > 0){
//                model1.urlList.add(item.getImagePath().get(i));
//            }
//        }
//        mList.add(model1);
//        for (int i = 0; i < mList.size(); i++) {
//            view.setIsShowAll(mList.get(i).isShowAll);
//            view.setUrlList(mList.get(i).urlList);
//        }

    }
}
