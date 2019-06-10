package com.art.recruitment.artperformance.ui.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.home.RecruitListBean;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.view.TagCloudView;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.art.recruitment.common.http.Api.TYPE_HEADER;

public class HomeAdapter extends BaseRecyclerViewAdapter<RecruitListBean.ContentBean> {

    public HomeAdapter(Context context, List<RecruitListBean.ContentBean> data) {
        super(context, data);
        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_home_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecruitListBean.ContentBean item) {

        helper.setText(R.id.home_title_textview, item.getTitle());
//        if (item.getSalaryType() == Constant.TYPE_PRICE_SURE_INT) {
        //
        helper.setText(R.id.home_price_textview, (item.getSalaryType() == Constant.TYPE_PRICE_FACE_INT)
                ? "面议" : ("￥" + item.getSalary()));

//        } else {
//            helper.setText(R.id.home_price_textview, );
//
//        }

        helper.setText(R.id.home_time_textview, "集合时间：" + item.getGatheringTime());
        helper.setText(R.id.home_address_textview, "集合地点：" + item.getGatheringAddress());
        helper.setText(R.id.home_people_textview, "发布人：" + item.getPublisherName());

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < item.getLabels().size(); i++) {
            tags.add(item.getLabels().get(i));
        }
        TagCloudView cloudView = helper.getView(R.id.home_flowlayout);
        cloudView.setTags(tags);

        if (item.getLabels().size() > 0) {
            cloudView.setVisibility(View.VISIBLE);
        } else {
            cloudView.setVisibility(View.GONE);
        }

//        helper.addOnClickListener(R.id.home_arrow_imageview);
        helper.addOnClickListener(R.id.constraint_content);

    }
    /*private List<RecruitListBean.ContentBean> beans;
    private View mHeaderView;
    public static final int TYPE_HEADE = 0;
    public static final int TYPE_NOMAL = 1;

    public interface OnItemClickListener{
        void onArrowClickListener(int position);
    }

    OnItemClickListener onItemClickListener;

    public void setOnViewClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemChanged(0);
    }

    public void setData(List<RecruitListBean.ContentBean> datas){
        beans.addAll(datas);
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView != null && position == 0){
            return TYPE_HEADE;
        }
        return TYPE_NOMAL;
    }

    public HomeAdapter(Context context, List<RecruitListBean.ContentBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADE)
            return new MyViewHolder(mHeaderView);
        View view=LayoutInflater.from(context).inflate(R.layout.item_home_list, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_HEADE) {
            return;
        }
        final int realPosition = getRealPosition(holder);

        holder.mTitleTextView.setText(beans.get(realPosition).getTitle());
        holder.mpriceTextView.setText("￥" + beans.get(realPosition).getSalary());
        holder.mTimeTextView.setText("集合时间：" + beans.get(realPosition).getGatheringTime());
        holder.mAddressTextView.setText("集合地点：" + beans.get(realPosition).getGatheringAddress());
        holder.mPeopleTextView.setText("发布人：" + beans.get(realPosition).getPublisherName());

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < beans.get(realPosition).getLabels().size(); i++) {
            tags.add(beans.get(realPosition).getLabels().get(i));
        }
        holder.mTagCloudView.setTags(tags);

        if (beans.get(realPosition).getLabels().size() > 0) {
            holder.mTagCloudView.setVisibility(View.VISIBLE);
        } else {
            holder.mTagCloudView.setVisibility(View.GONE);
        }

        holder.mArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null)
                    onItemClickListener.onArrowClickListener(realPosition);
            }
        });

    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? beans.size() : beans.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView;
        TextView mpriceTextView;
        TagCloudView mTagCloudView;
        TextView mTimeTextView;
        TextView mAddressTextView;
        TextView mPeopleTextView;
        ImageView mArrowImageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView){
                return;
            }
            mTitleTextView = itemView.findViewById(R.id.home_title_textview);
            mpriceTextView = itemView.findViewById(R.id.home_price_textview);
            mTimeTextView = itemView.findViewById(R.id.home_time_textview);
            mAddressTextView = itemView.findViewById(R.id.home_address_textview);
            mPeopleTextView = itemView.findViewById(R.id.home_people_textview);
            mTagCloudView = itemView.findViewById(R.id.home_flowlayout);
            mArrowImageView = itemView.findViewById(R.id.home_arrow_imageview);
        }
    }*/
}