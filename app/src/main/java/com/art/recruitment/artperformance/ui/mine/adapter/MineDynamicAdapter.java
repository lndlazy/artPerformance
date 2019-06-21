package com.art.recruitment.artperformance.ui.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.MineDynamicBean;
import com.art.recruitment.artperformance.bean.model.NineGridTestModel;
import com.art.recruitment.artperformance.ui.dynamic.activity.PlusImageActivity;
import com.art.recruitment.artperformance.ui.dynamic.contract.MainConstant;
import com.art.recruitment.artperformance.ui.mine.ImageModel;
import com.art.recruitment.artperformance.view.NineGridLayout;
import com.art.recruitment.artperformance.view.NineGridTestLayout;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.utils.UIUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;

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
    protected void convert(BaseViewHolder helper, final MineDynamicBean.ContentBean item) {

//        ImageView headImageView = helper.getView(R.id.dynamic_head_imageview);
//        Glide.with(mContext).load(item.getPublisherAvatar()).into(headImageView);
//        helper.addOnClickListener(R.id.dynamic_comment_imageview);

        //9宫格图片
        NineGridTestLayout view = helper.getView(R.id.mine_dynamic_nineGridTestLayout);
        view.setIsShowAll(false);

        view.setUrlList(new ArrayList<String>());
        view.setFragment(fragment);

        String videoPreview = item.getVideoPreview();

//        if (!TextUtils.isEmpty(videoPreview)) {
//            //视频
//            view.setVisibility(View.VISIBLE);
//            ArrayList<String> videoList = new ArrayList<>();
//            videoList.add(item.getVideoPreview());
//            view.setUrlList(videoList);
//            view.setVideoUrl(item.getVideoPath());
//        }else {
//            //图片
//            if (item.getImagePath() == null) {
//                view.setVisibility(View.GONE);
//            } else {
//                view.setVisibility(View.VISIBLE);
//                view.setUrlList(item.getImagePath());
//            }
//
//        }

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
