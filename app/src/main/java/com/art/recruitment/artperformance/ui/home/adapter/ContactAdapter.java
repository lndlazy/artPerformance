package com.art.recruitment.artperformance.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.model.UserEntity;

import me.yokeyword.indexablerv.IndexableAdapter;


/**
 * Created by YoKey on 16/10/8.
 */
public class ContactAdapter extends IndexableAdapter<UserEntity> {
    private LayoutInflater mInflater;

    public ContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_contact, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(final RecyclerView.ViewHolder holder, UserEntity entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tvName.setText(entity.getNick());
        /*if (entity.isSelect()) {
            vh.mSelected.setVisibility(View.VISIBLE);
        } else {
            vh.mSelected.setVisibility(View.GONE);
        }*/
    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView mSelected;

        public ContentVH(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            mSelected = itemView.findViewById(R.id.group_selected_imageview);
        }
    }

    private ItemOnClickListener mItemOnClickListener;

    public void setmItemOnClickListener(ItemOnClickListener listener){
        this.mItemOnClickListener = listener;
    }
    public interface ItemOnClickListener{

        void itemOnClickListener(ImageView imageView);
    }
}
