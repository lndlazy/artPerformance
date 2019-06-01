package com.art.recruitment.artperformance.ui.dynamic.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.ui.mine.ImageModel;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ReleaseDynamicAdapter extends BaseRecyclerViewAdapter<ImageModel> {

    public ReleaseDynamicAdapter(Context context, List<ImageModel> data) {
        super(context, data);

        addItemType(ImageModel.TYPE_IMAGE_ADD, R.layout.item_release_list);
        addItemType(ImageModel.TYPE_IMAGE_SELECTED, R.layout.item_release_list);

    }

    @Override
    protected void convert(BaseViewHolder helper, ImageModel item) {
        if (item.getItemType() == 1){
            ImageView s = helper.getView(R.id.dynamic_release_imageview);
            Glide.with(mContext).load(item.getUris()).into(s);
        }else if (item.getItemType() == 1){
            helper.setImageResource(R.id.dynamic_release_imageview,R.mipmap.icon_my_add);
        }
        helper.addOnClickListener(R.id.dynamic_release_imageview);

    }
}
