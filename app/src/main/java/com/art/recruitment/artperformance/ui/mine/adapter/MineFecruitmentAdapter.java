package com.art.recruitment.artperformance.ui.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.MineFecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.MineSignUpBean;
import com.art.recruitment.artperformance.view.TagCloudView;
import com.art.recruitment.common.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class MineFecruitmentAdapter extends RecyclerView.Adapter<MineFecruitmentAdapter.MyViewHolder>  {
    private Context context;
    private List<MineFecruitmentBean.ContentBean> beans;
    private String upOrNext;

    public interface OnItemClickListener{
        void onArrowClickListener(int position, View view, String string);
    }

    MineFecruitmentAdapter.OnItemClickListener onItemClickListener;

    public void setOnViewClickListener(MineFecruitmentAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public MineFecruitmentAdapter(Context context, List<MineFecruitmentBean.ContentBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @Override
    public MineFecruitmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_fecruitment_list, parent,false);
        return new MineFecruitmentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MineFecruitmentAdapter.MyViewHolder holder, final int position) {

        if (beans.get(position).getFrontendFlag() == 1){
            holder.mNextFrameTextview.setText(UIUtils.getString(R.string.mine_up_frame));
            upOrNext = "up";
        } else if (beans.get(position).getFrontendFlag() == 0){
            holder.mNextFrameTextview.setText(UIUtils.getString(R.string.mine_next_frame));
            upOrNext = "down";
        }

        holder.mStateTextView.setText(beans.get(position).getRecruitmentStateText());
        holder.mTimeTextView.setText(beans.get(position).getReleaseTime());
        holder.mTitleTextView.setText(beans.get(position).getTitle());
        holder.mSignTimeTextView.setText(beans.get(position).getGatheringTime());
        holder.mSignAdressTextview.setText(beans.get(position).getGatheringAddress());
        holder.mPriceTextView.setText("￥ " + beans.get(position).getSalary());

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < beans.get(position).getLabelList().size(); i++) {
            tags.add(beans.get(position).getLabelList().get(i));
        }
        holder.mTagCloudView.setTags(tags);

        holder.mNextFrameTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)
                    onItemClickListener.onArrowClickListener(position, v, upOrNext);
            }
        });

        holder.mRecruitmentInformationTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)
                    onItemClickListener.onArrowClickListener(position, v, upOrNext);
            }
        });

        holder.mFecruitmentPersonnelTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)
                    onItemClickListener.onArrowClickListener(position, v, upOrNext);
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
        TextView mNextFrameTextview;
        TextView mRecruitmentInformationTextview;
        TextView mFecruitmentPersonnelTextview;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTimeTextView = itemView.findViewById(R.id.mine_fecruitment_time_textview);
            mStateTextView = itemView.findViewById(R.id.mine_fecruitment_state_textview);
            mTitleTextView = itemView.findViewById(R.id.mine_fecruitment_title_textview);
            mPriceTextView = itemView.findViewById(R.id.mine_fecruitment_price_textview);
            mSignTimeTextView = itemView.findViewById(R.id.mine_sign_time_textview);
            mTagCloudView = itemView.findViewById(R.id.details_flowlayout);
            mSignAdressTextview = itemView.findViewById(R.id.mine_sign_adress_textview);
            mNextFrameTextview = itemView.findViewById(R.id.mine_next_frame_textview);
            mRecruitmentInformationTextview = itemView.findViewById(R.id.mine_editorial_recruitment_information_textview);
            mFecruitmentPersonnelTextview = itemView.findViewById(R.id.mine_fecruitment_personnel_textview);

        }
    }
}
