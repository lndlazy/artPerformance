package com.art.recruitment.artperformance.ui.dynamic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.ui.dynamic.contract.MainConstant;
import com.art.recruitment.artperformance.utils.Constant;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.art.recruitment.artperformance.ui.dynamic.contract.MainConstant.MAX_SELECT_PIC_NUM;


/**
 * 添加上传图片适配器
 * <p>
 * 作者： 周旭 on 2017/6/21/0021.
 * 邮箱：374952705@qq.com
 * 博客：http://www.jianshu.com/u/56db5d78044d
 */

public class GridViewAdapter extends android.widget.BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private List<Bitmap> bitmapList;
    private LayoutInflater inflater;
    private int type;


    public void setType(int type) {
        this.type = type;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }



    public GridViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {

        if (type == Constant.CHOOSE_TYPE_VIDEO)
            return 1;

        int count = mList == null ? 1 : mList.size() + 1;
        if (count > MainConstant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return type == Constant.CHOOSE_TYPE_VIDEO ? bitmapList.get(position) : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_release_list, parent, false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.dynamic_release_imageview);


        if (type == Constant.CHOOSE_TYPE_VIDEO) {

            if (bitmapList != null && bitmapList.size() > 0) {
                //有图片
                iv.setImageBitmap(bitmapList.get(0));
            } else {
                iv.setImageResource(R.mipmap.icon_my_add);
            }

        } else {
            if (position < mList.size()) {
                //代表+号之前的需要正常显示图片
                String picUrl = mList.get(position); //图片路径
                Glide.with(mContext).load(picUrl).into(iv);
            } else {
                iv.setImageResource(R.mipmap.icon_my_add);//最后一个显示加号图片
            }
        }


        return convertView;
    }
}  
