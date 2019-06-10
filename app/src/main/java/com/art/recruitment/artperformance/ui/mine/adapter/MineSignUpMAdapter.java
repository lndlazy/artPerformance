package com.art.recruitment.artperformance.ui.mine.adapter;

import android.content.Context;
import android.view.View;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.home.RecruitListBean;
import com.art.recruitment.artperformance.bean.mine.MineSignUpBean;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.view.TagCloudView;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linaidao on 2019/6/10.
 */

public class MineSignUpMAdapter extends BaseRecyclerViewAdapter<MineSignUpBean.ContentBean> {

    public MineSignUpMAdapter(Context context, List<MineSignUpBean.ContentBean> data) {
        super(context, data);

        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_mine_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineSignUpBean.ContentBean item) {

        helper.setText(R.id.mine_sign_up_state_textview, item.getHireState());//报名状态
        helper.setText(R.id.mine_sign_up_time_textview, item.getModifyTime());//时间
        helper.setText(R.id.mine_sign_title_textview, item.getRecruitmentTitle());//标题

        helper.setText(R.id.mine_sign_price_textview,
                (item.getSalaryType() == Constant.TYPE_PRICE_FACE_INT) ? "面议" : "¥" + item.getSalary());//价格

        //tag
        if (item.getLabels() != null) {
            TagCloudView tagCloudView = helper.getView(R.id.details_flowlayout);
            List<String> tags = new ArrayList<>();
            tags.addAll(item.getLabels());
            tagCloudView.setTags(tags);
        }

        helper.setText(R.id.mine_sign_time_textview, "集合时间:" + item.getGatheringTime());//集合时间
        helper.setText(R.id.mine_sign_adress_textview, "集合地点:" + item.getGatheringAddress());//集合地点
        helper.setText(R.id.mine_sign_people_textview, "发布人:" + item.getPublisherName());//发布人

        helper.setVisible(R.id.mine_sign_clean_textview, "已录用".equals(item.getHireState()));

        helper.addOnClickListener(R.id.enter_next);//进入详情页面
        helper.addOnClickListener(R.id.mine_sign_chat_imageview);//聊天页面
        helper.addOnClickListener(R.id.mine_sign_clean_textview);//取消报名

    }
}
