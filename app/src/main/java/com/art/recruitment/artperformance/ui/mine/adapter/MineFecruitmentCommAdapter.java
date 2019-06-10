package com.art.recruitment.artperformance.ui.mine.adapter;

import android.content.Context;
import android.view.View;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.home.RecruitListBean;
import com.art.recruitment.artperformance.bean.mine.MineFecruitmentBean;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.view.TagCloudView;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.utils.UIUtils;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linaidao on 2019/6/5.
 */

public class MineFecruitmentCommAdapter extends BaseRecyclerViewAdapter<MineFecruitmentBean.ContentBean> {

    public MineFecruitmentCommAdapter(Context context, List<MineFecruitmentBean.ContentBean> data) {
        super(context, data);
        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_fecruitment_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineFecruitmentBean.ContentBean item) {

        helper.setText(R.id.mine_fecruitment_time_textview, item.getReleaseTime());//发布时间

        helper.setText(R.id.mine_sign_time_textview, "集合时间:" + item.getGatheringTime());//集合时间
        helper.setText(R.id.mine_sign_adress_textview, "集合地点:" + item.getGatheringAddress());//集合地点

//        helper.setText(R.id.mine_fecruitment_state_textview,
//                (item.getHireNumber() >= item.getRecruitNumber()) ? "人员已满" : "正在招募");//招募状态
        helper.setText(R.id.mine_fecruitment_state_textview, item.getRecruitmentStateText());//招募状态

        helper.setText(R.id.mine_fecruitment_title_textview, item.getTitle());//招募标题

//        if (item.getSalaryType() == Constant.TYPE_PRICE_FACE_INT) {
//
//        }
        helper.setText(R.id.mine_fecruitment_price_textview,
                (item.getSalaryType() == Constant.TYPE_PRICE_FACE_INT) ? "面议" : "￥ " + item.getSalary());//价格

        TagCloudView tagCloudView = helper.getView(R.id.details_flowlayout);

        if (tagCloudView != null) {

            if (item.getLabels() != null && item.getLabels().length() > 0) {
                tagCloudView.setVisibility(View.VISIBLE);
            } else {
                tagCloudView.setVisibility(View.GONE);
            }

            tagCloudView.setTags(new ArrayList<>(item.getLabelList()));
        }

        helper.setText(R.id.mine_next_frame_textview, (item.getFrontendFlag() == 1) ? "下架" : "上架");

        helper.addOnClickListener(R.id.mine_next_frame_textview);//上架 下架
        helper.addOnClickListener(R.id.mine_editorial_recruitment_information_textview);//编辑招募信息
        helper.addOnClickListener(R.id.mine_fecruitment_personnel_textview);//人员录用

//        if (item.getFrontendFlag() == 1) {
//
//            helper.setText(R.id.mine_next_frame_textview, UIUtils.getString(R.string.mine_next_frame));
////            holder.mNextFrameTextview.setText(UIUtils.getString(R.string.mine_up_frame));
////            upOrNext = "up";
//        } else if (item.getFrontendFlag() == 0) {
//            helper.setText(R.id.mine_next_frame_textview, UIUtils.getString(R.string.mine_next_frame));
////            holder.mNextFrameTextview.setText(UIUtils.getString(R.string.mine_next_frame));
////            upOrNext = "down";
//        }

//        helper.mStateTextView.setText(beans.get(position).getRecruitmentStateText());
//        holder.mTimeTextView.setText(beans.get(position).getReleaseTime());
//        holder.mTitleTextView.setText(beans.get(position).getTitle());
//        holder.mSignTimeTextView.setText(beans.get(position).getGatheringTime());
//        holder.mSignAdressTextview.setText(beans.get(position).getGatheringAddress());
//        holder.mPriceTextView.setText("￥ " + beans.get(position).getSalary());


//        helper.setText(R.id.home_title_textview, item.getTitle());
//        helper.setText(R.id.home_price_textview, "￥" + item.getSalary());
//        helper.setText(R.id.home_time_textview, "集合时间：" + item.getGatheringTime());
//        helper.setText(R.id.home_address_textview, "集合地点：" + item.getGatheringAddress());
//        helper.setText(R.id.home_people_textview, "发布人：" + item.getPublisherName());
//
//        List<String> tags = new ArrayList<>();
//        for (int i = 0; i < item.getLabels().length(); i++) {
//            tags.add(item.getLabels().get(i));
//        }
//        ArrayList<String> strings = new ArrayList<>(item.getLabelList());


//
////        helper.addOnClickListener(R.id.home_arrow_imageview);
//        helper.addOnClickListener(R.id.constraint_content);

    }

}