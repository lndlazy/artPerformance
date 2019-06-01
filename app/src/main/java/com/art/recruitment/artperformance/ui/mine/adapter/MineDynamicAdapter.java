package com.art.recruitment.artperformance.ui.mine.adapter;

import android.content.Context;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.MineDynamicBean;
import com.art.recruitment.artperformance.bean.model.NineGridTestModel;
import com.art.recruitment.artperformance.ui.mine.ImageModel;
import com.art.recruitment.artperformance.view.NineGridTestLayout;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MineDynamicAdapter extends BaseRecyclerViewAdapter<MineDynamicBean.ContentBean> {

    private List<NineGridTestModel> mList;

    public MineDynamicAdapter(Context context, List<MineDynamicBean.ContentBean> data) {
        super(context, data);
        addItemType(ImageModel.TYPE_IMAGE_ADD, R.layout.item_mine_dynamic_list);
    }

    public void setList(List<NineGridTestModel> list) {
        mList = list;
    }


    @Override
    protected void convert(BaseViewHolder helper, MineDynamicBean.ContentBean item) {
        helper.setText(R.id.mine_dynamic_date_textview, item.getPublishDate())
                .setText(R.id.mine_dynamic_time_textview, item.getPublishTime())
              .setText(R.id.mine_dynamic_expandableTextView, item.getContent());

        NineGridTestLayout view = helper.getView(R.id.mine_dynamic_nineGridTestLayout);

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

    }
}
