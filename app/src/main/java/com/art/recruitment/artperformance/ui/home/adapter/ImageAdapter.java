package com.art.recruitment.artperformance.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.home.RecruitmentInforBean;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;

    public ImageAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview_home, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemImg = (CircleImageView) convertView.findViewById(R.id.recruitment_information_imageview);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(list.get(position)).into(viewHolder.itemImg);

        return convertView;
    }


    class ViewHolder {
        CircleImageView itemImg;
    }
}
