package com.art.recruitment.artperformance.ui.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.ui.mine.ImageModel;
import com.art.recruitment.artperformance.utils.GlideUtils;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class MineVideoAdapter extends BaseRecyclerViewAdapter<ImageModel> {

    public MineVideoAdapter(Context context, List<ImageModel> data) {
        super(context, data);

        addItemType(ImageModel.TYPE_IMAGE_ADD, R.layout.item_mine_photo_list_add);
        addItemType(ImageModel.TYPE_IMAGE_SELECTED, R.layout.item_appeal_evidence_list_selected);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageModel item) {
        /*if (item.getItemType() == 1){
            ImageView s = helper.getView(R.id.item_appeal_evidence_list_add_image);
            Glide.with(mContext).load(item.getUris()).into(s);
        }else if (item.getItemType() == 1){
            helper.setImageResource(R.id.item_appeal_evidence_list_add_image,R.mipmap.icon_my_add);
        }
        helper.addOnClickListener(R.id.item_appeal_evidence_list_add_image);*/

        switch (item.getItemType()) {

            case ImageModel.TYPE_IMAGE_SELECTED:
                ImageView mImageView = helper.getView(R.id.item_appeal_evidence_list_evidence_image);
                GlideUtils.loadImage(mContext,item.getUris(),mImageView);
                helper.addOnClickListener(R.id.item_appeal_evidence_list_evidence_image).
                        addOnClickListener(R.id.item_appeal_evidence_list_delete_image);
                break;

            case ImageModel.TYPE_IMAGE_ADD:
                helper.addOnClickListener(R.id.item_appeal_evidence_list_add_image);
                break;
        }
    }
}
