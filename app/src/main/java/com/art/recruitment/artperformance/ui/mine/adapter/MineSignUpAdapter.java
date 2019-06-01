package com.art.recruitment.artperformance.ui.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.home.RecruitListBean;
import com.art.recruitment.artperformance.bean.mine.MineSignUpBean;
import com.art.recruitment.artperformance.ui.home.adapter.HomeAdapter;
import com.art.recruitment.artperformance.view.TagCloudView;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MineSignUpAdapter extends RecyclerView.Adapter<MineSignUpAdapter.MyViewHolder>  {
    private Context context;
    private List<MineSignUpBean.ContentBean> beans;

    public interface OnItemClickListener{
        void onArrowClickListener(int position, View view);
    }

    MineSignUpAdapter.OnItemClickListener onItemClickListener;

    public void setOnViewClickListener(MineSignUpAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public MineSignUpAdapter(Context context, List<MineSignUpBean.ContentBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @Override
    public MineSignUpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_mine_list, parent,false);
        return new MineSignUpAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MineSignUpAdapter.MyViewHolder holder, final int position) {

        if (beans.get(position).getHireState() == "已录用"){
            holder.mSignCleanTextview.setVisibility(View.VISIBLE);
        } else {
            holder.mSignCleanTextview.setVisibility(View.VISIBLE);
        }
        holder.mStateTextView.setText(beans.get(position).getHireState());
        holder.mTimeTextView.setText(beans.get(position).getModifyTime());
        holder.mTitleTextView.setText(beans.get(position).getRecruitmentTitle());
        holder.mSignTimeTextView.setText(beans.get(position).getGatheringTime());
        holder.mSignAdressTextview.setText(beans.get(position).getGatheringAddress());
        holder.mSignPeopleTextview.setText(beans.get(position).getPublisherName());

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < beans.get(position).getLabels().size(); i++) {
            tags.add(beans.get(position).getLabels().get(i));
        }
        holder.mTagCloudView.setTags(tags);

        holder.mSignCleanTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)
                    onItemClickListener.onArrowClickListener(position, v);
            }
        });

        holder.mSignChatImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)
                    onItemClickListener.onArrowClickListener(position, v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTimeTextView;
        TextView mStateTextView;
        TagCloudView mTagCloudView;
        TextView mTitleTextView;
        TextView mPriceTextView;
        TextView mSignTimeTextView;
        TextView mSignAdressTextview;
        ImageView mSignArrowImageview;
        ImageView mSignChatImageview;
        TextView mSignPeopleTextview;
        TextView mSignCleanTextview;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTimeTextView = itemView.findViewById(R.id.mine_sign_up_time_textview);
            mStateTextView = itemView.findViewById(R.id.mine_sign_up_state_textview);
            mTitleTextView = itemView.findViewById(R.id.mine_sign_title_textview);
            mPriceTextView = itemView.findViewById(R.id.mine_sign_price_textview);
            mSignTimeTextView = itemView.findViewById(R.id.mine_sign_time_textview);
            mTagCloudView = itemView.findViewById(R.id.details_flowlayout);
            mSignAdressTextview = itemView.findViewById(R.id.mine_sign_adress_textview);
            mSignArrowImageview = itemView.findViewById(R.id.mine_sign_arrow_imageview);
            mSignPeopleTextview = itemView.findViewById(R.id.mine_sign_people_textview);
            mSignChatImageview = itemView.findViewById(R.id.mine_sign_chat_imageview);
            mSignCleanTextview = itemView.findViewById(R.id.mine_sign_clean_textview);

        }
    }

    /*public MineSignUpAdapter(Context context, List<MineSignUpBean.ContentBean> data) {
        super(context, data);
        addItemType(BaseConfig.SINGLE_ITEM_TYPE, R.layout.item_mine_list);

    }

    @Override
    protected void convert(BaseViewHolder helper, MineSignUpBean.ContentBean item) {
        helper.setText(R.id.mine_sign_time_textview, item.getGatheringTime());
    }*/
}
