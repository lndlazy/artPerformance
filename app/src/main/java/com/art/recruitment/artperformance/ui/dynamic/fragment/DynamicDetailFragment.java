package com.art.recruitment.artperformance.ui.dynamic.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.dynamic.DeleteDynamicBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentRequest;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentsBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicDetailBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicLikesBean;
import com.art.recruitment.artperformance.bean.model.NineGridTestModel;
import com.art.recruitment.artperformance.ui.dynamic.DynamicType;
import com.art.recruitment.artperformance.ui.dynamic.activity.PlusImageActivity;
import com.art.recruitment.artperformance.ui.dynamic.adapter.DynamicCommentsAdapter;
import com.art.recruitment.artperformance.ui.dynamic.contract.DynamicDataContract;
import com.art.recruitment.artperformance.ui.dynamic.contract.MainConstant;
import com.art.recruitment.artperformance.ui.dynamic.presenter.DynamicDataPresenter;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.utils.DateFormatUtils;
import com.art.recruitment.artperformance.utils.Defaultcontent;
import com.art.recruitment.artperformance.utils.ShareUtils;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.artperformance.view.ExpandableTextView;
import com.art.recruitment.artperformance.view.NineGridLayout;
import com.art.recruitment.artperformance.view.NineGridTestLayout;
import com.art.recruitment.common.base.adapter.BaseRecyclerViewAdapter;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.utils.SystemUtil;
import com.art.recruitment.common.utils.UIUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.PictureSelector;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 动态圈详情页面
 */
public class DynamicDetailFragment extends BaseFragment<DynamicDataPresenter, DynamicCommentsBean.ContentBean> implements DynamicDataContract {

    @BindView(R.id.dynamic_datail_return_imageview)
    ImageView mReturnImageview;

    //    @BindView(R.id.dynamic_detail_head_imageview)
    ImageView mHeadImageview;


    //    @BindView(R.id.dynamic_detail_name_textview)
    TextView mNameTextview;


    //    @BindView(R.id.dynamic_detail_time_textview)
    TextView mTimeTextview;

    //    @BindView(R.id.dynamic_detail_expandableTextView)
    ExpandableTextView mExpandableTextView;

    //    @BindView(R.id.dynamic_give_imageview)
    ImageView mGiveImageview;

    //    @BindView(R.id.dynamic_detail_give_constraintLayout)
    ConstraintLayout mGiveConstraintLayout;

    //    @BindView(R.id.dynamic_comment_imageview)
    ImageView mCommentImageview;

    //    @BindView(R.id.dynamic_detail_comment_constraintLayout)
    ConstraintLayout mCommentConstraintLayout;

    //    @BindView(R.id.dynamic_share_imageview)
    ImageView mShareImageview;

    //    @BindView(R.id.dynamic_detail_share_constraintLayout)
    ConstraintLayout mShareConstraintLayout;

    @BindView(R.id.dynamic_detail_comment_recyclerView)
    RecyclerView mCommentRecyclerView;

    @BindView(R.id.dynamic_detail_send_edittext)
    EditText mSendEdittext;
    @BindView(R.id.dynamic_detail_send_textview)
    TextView mSendTextview;


    //    @BindView(R.id.dynamic_give_textview)
    TextView mGiveTextview;


    //    @BindView(R.id.dynamic_comment_textview)
    TextView mCommentTextview;


    //    @BindView(R.id.dynamic_detail_delete_imageview)
    ImageView mDeleteImageview;

    @BindView(R.id.dynamic_detail_comment_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    //    @BindView(R.id.dynamic_detail_nineGridTestLayout)
    NineGridTestLayout mNineGridTestLayout;
    private int dynamic_id;
    private Dialog dialog;
    //    private int pageSize;
    private DynamicCommentsAdapter commentAdapter;
    //    private List<NineGridTestModel> mList;
    private Dialog shareDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic_detail;
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return mSmartRefreshLayout;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mCommentRecyclerView;
    }

    @Override
    protected BaseRecyclerViewAdapter getRecyclerViewAdapter() {
        commentAdapter = new DynamicCommentsAdapter(mContext, mDataList);
        commentAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        View headView = View.inflate(getContext(), R.layout.headview_dynamic_comment, null);
        commentAdapter.addHeaderView(headView);
        initHeadView(headView);
        return commentAdapter;
    }

    private void initHeadView(View headView) {

        mHeadImageview = headView.findViewById(R.id.dynamic_detail_head_imageview);
        mNameTextview = headView.findViewById(R.id.dynamic_detail_name_textview);
        mTimeTextview = headView.findViewById(R.id.dynamic_detail_time_textview);
        mExpandableTextView = headView.findViewById(R.id.dynamic_detail_expandableTextView);
        mGiveImageview = headView.findViewById(R.id.dynamic_give_imageview);
        mGiveConstraintLayout = headView.findViewById(R.id.dynamic_detail_give_constraintLayout);
        mCommentImageview = headView.findViewById(R.id.dynamic_comment_imageview);
        mCommentConstraintLayout = headView.findViewById(R.id.dynamic_detail_comment_constraintLayout);
        mShareImageview = headView.findViewById(R.id.dynamic_share_imageview);
        mShareConstraintLayout = headView.findViewById(R.id.dynamic_detail_share_constraintLayout);
        mGiveTextview = headView.findViewById(R.id.dynamic_give_textview);
        mCommentTextview = headView.findViewById(R.id.dynamic_comment_textview);
        mDeleteImageview = headView.findViewById(R.id.dynamic_detail_delete_imageview);
        mNineGridTestLayout = headView.findViewById(R.id.dynamic_detail_nineGridTestLayout);

    }

    @Override
    protected boolean enableAdapterLoadMore() {
        return true;
    }

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initListRequest(int page) {
        super.initListRequest(page);
//        pageSize = page;
        Logger.d("当前page::" + page);
        mPresenter.dynamicComments(dynamic_id, page, BaseConfig.DEFAULT_PAGE_SIZE, Constant.SORT_DESC);

    }

    @Override
    protected void initView() {

        if (getActivity() == null)
            return;

        dynamic_id = getActivity().getIntent().getIntExtra("dynamic_id", 0);

//        int userID = SPUtils.getInstance().getInt(BaseConfig.BaseSPKey.ID);
//        SPUtils.getInstance().put(BaseConfig.BaseSPKey.ID, registerBean.getId());
//        Logger.d("dynamic_id::" + dynamic_id + "，userID:" + userID);

        mPresenter.dynamicDetail(dynamic_id);
        autoRefresh();
        initButtonClick();
    }

    @Override
    protected void lazyLoad() {
        autoRefresh();
    }

    private void initButtonClick() {
        RxView.
                clicks(mReturnImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getActivity().finish();
                    }
                });

        //点赞
        RxView.
                clicks(mGiveConstraintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.dynamicLikes(dynamic_id);
                    }
                });

        RxView.
                clicks(mShareConstraintLayout).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        //分享
                        String subUrl = "/dynamiccircle/" + dynamic_id + "/share";
                        startShare(subUrl);

                    }
                });

        //删除动态圈
        RxView.
                clicks(mDeleteImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        deleteDialog();
                    }
                });

        //评论
        RxView.
                clicks(mSendTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        DynamicCommentRequest request = new DynamicCommentRequest();
                        request.setCommentContent(mSendEdittext.getText().toString().trim());
                        Gson gson = new Gson();
                        String codeStr = gson.toJson(request);
                        mPresenter.dynamicComment(dynamic_id, codeStr);

                    }
                });
    }

    private void deleteDialog() {
        View inflate = View.inflate(getContext(), R.layout.dialog_delete_dynamic, null);
        TextView mCleanTextview = inflate.findViewById(R.id.dialog_delete_clean_textview);
        TextView mDetermineTextview = inflate.findViewById(R.id.dialog_delete_determine_textview);

        dialog = DialogWrapper.
                customViewDialog().
                context(getContext()).
                contentView(inflate).
                cancelable(false, false).
                build();

        dialog.show();

        mCleanTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        mDetermineTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

                mPresenter.deleteDynamic(dynamic_id);
            }
        });
    }

    @Override
    public void returnDynamicDataBean(final DynamicDetailBean.DataBean bean) {

//        mPresenter.dynamicComments(dynamic_id, 0, BaseConfig.DEFAULT_PAGE_SIZE, Constant.SORT_DESC);

        Glide.with(mContext).load(bean.getPublisherAvatar()).into(mHeadImageview);
        mNameTextview.setText(bean.getPublisherName());
        mTimeTextview.setText(bean.getCreateTime());
        mExpandableTextView.setText(bean.getContent());
        mGiveTextview.setText(bean.getLikes() + "");
        mCommentTextview.setText(bean.getCommentNumber() + "");

        mDeleteImageview.setVisibility(bean.isCanDelete() ? View.VISIBLE : View.GONE);
//        bean.isCanDelete()

        if (bean.isIsLikes()) {
            mGiveImageview.setImageDrawable(UIUtils.getDrawable(R.mipmap.icon_circle_like_p));
        } else {
            mGiveImageview.setImageDrawable(UIUtils.getDrawable(R.mipmap.icon_circle_like));
        }

        mNineGridTestLayout.setFragment(this);
        mNineGridTestLayout.setIsShowAll(false);


        if (bean.getImagePath() != null || !TextUtils.isEmpty(bean.getVideoPreview())) {

            mNineGridTestLayout.setVisibility(View.VISIBLE);
            if (bean.getImagePath() != null &&   bean.getImagePath().size() > 0) {
                mNineGridTestLayout.setUrlList(bean.getImagePath());
//                mNineGridTestLayout.setVideo(false);
            } else if (!TextUtils.isEmpty(bean.getVideoPreview())) {
                List<String> pics = new ArrayList<>();
                pics.add(bean.getVideoPreview());
//                mNineGridTestLayout.setVideo(true);
                mNineGridTestLayout.setUrlList(pics);
            }

        } else {

            mNineGridTestLayout.setVisibility(View.GONE);

        }
        mNineGridTestLayout.setOnItemClick(new NineGridLayout.OnItemClick() {
            @Override
            public void onclick(int i) {

                if (!TextUtils.isEmpty(bean.getVideoPreview())) {
                    PictureSelector.create(DynamicDetailFragment.this).externalPictureVideo(bean.getVideoPath());
                } else {

                    Intent intent = new Intent(mContext, PlusImageActivity.class);
                    intent.putStringArrayListExtra(MainConstant.IMG_LIST, (ArrayList<String>) bean.getImagePath());
                    intent.putExtra(MainConstant.POSITION, i);
                    intent.putExtra("canDelete", false);
                    mContext.startActivity(intent);
                }

            }
        });


//        if (!TextUtils.isEmpty(bean.getVideoPath())) {
//            //视频
//            List<String> videoList = new ArrayList<>();
//            videoList.add(bean.getVideoPreview());
//            mNineGridTestLayout.setVideoUrl(bean.getVideoPath());
//            mNineGridTestLayout.setUrlList(videoList);
//
//        } else {
//            mNineGridTestLayout.setIsShowAll(false);
//            mNineGridTestLayout.setUrlList(bean.getImagePath());
//        }

//        mList = new ArrayList<>();
//        NineGridTestModel model1 = new NineGridTestModel();
//
//        for (int i = 0; i < bean.getImagePath().size(); i++) {
//            if (bean.getImagePath().size() > 0) {
//                model1.urlList.add(bean.getImagePath().get(i));
//            }
//        }
//
//        mList.add(model1);
//
//        for (int i = 0; i < mList.size(); i++) {
//            mNineGridTestLayout.setIsShowAll(mList.get(i).isShowAll);
//            mNineGridTestLayout.setUrlList(mList.get(i).urlList);
//        }

    }

    @Override
    public void returnDynamicCommentsBean(DynamicCommentsBean.DataBean bean) {
        if (bean.getContent() == null || bean.getContent().size() == 0) {
            setEmptyErrorViewData(R.mipmap.img_show_empty, "暂时没有数据");
            mSmartRefreshLayout.finishRefresh(true);
        } else {
            resetStateWhenLoadDataSuccess(bean.getContent());
        }
    }

    //评论成功
    @Override
    public void returnDynamicCommentBean(DynamicCommentBean.DataBean bean) {

        //列表上添加刚评论的内容
        if (commentAdapter != null) {
            List<DynamicCommentsBean.ContentBean> data = commentAdapter.getData();
            DynamicCommentsBean.ContentBean contentBean = new DynamicCommentsBean.ContentBean();
            contentBean.setCommentContent(mSendEdittext.getText().toString().trim());
            contentBean.setCommentTime(DateFormatUtils.formatDateTime(new Date().getTime(), DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS));
            contentBean.setCommentUserAvatar(SPUtils.getInstance().getString(BaseConfig.BaseSPKey.HEAD_PIC_URL));
            contentBean.setCommentUserName(SPUtils.getInstance().getString(BaseConfig.BaseSPKey.NAME));
            data.add(0, contentBean);
            commentAdapter.notifyDataSetChanged();
        }
        mSendEdittext.setText("");
//        mPresenter.dynamicComments(dynamic_id, pageSize, BaseConfig.DEFAULT_PAGE_SIZE, Constant.SORT_DESC);
        SystemUtil.closeInoutDecorView(getActivity());
        EventBus.getDefault().post(DynamicType.TYPE_ADD_COMMENT);
    }


    //删除动态圈成功
    @Override
    public void returnDeleteDynamicBean(DeleteDynamicBean.DataBean bean) {

        if (getActivity() != null) {
            Intent intent = new Intent();
            intent.setAction("DELETE_DYNAMIC");
            intent.putExtra("delete", 1);
            getActivity().sendBroadcast(intent);

            ToastUtils.showShort("删除成功");
            getActivity().finish();
        }

    }

    @Override
    public void returnDynamicLikesBean(DynamicLikesBean.DataBean bean) {
        mPresenter.dynamicDetail(dynamic_id);

        EventBus.getDefault().post(DynamicType.TYPE_ADD_LIKE);
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

    /**
     * 分享
     *
     * @param subUrl
     */
    private void startShare(String subUrl) {

        View inflate = View.inflate(getContext(), R.layout.dialog_mine_share, null);
        TextView mCleanTextview = inflate.findViewById(R.id.mine_share_clean_textview);
        ConstraintLayout mWechatConstraintLayout = inflate.findViewById(R.id.share_wechat_constraintLayout);
        ConstraintLayout mCircleFriendsConstraintLayout = inflate.findViewById(R.id.share_circle_of_friends_constraintLayout);
        ConstraintLayout mQQZoneConstraintLayout = inflate.findViewById(R.id.share_qq_zone_constraintLayout);
        ConstraintLayout mQQConstraintLayout = inflate.findViewById(R.id.share_qq_constraintLayout);

        shareDialog = DialogWrapper.
                customViewDialog().
                context(getContext()).
                contentView(inflate).
                cancelable(false, false).
                build();

        Window window = shareDialog.getWindow();
        if (window != null)
            window.setWindowAnimations(R.style.mystyle);

        shareDialog.show();

        mCleanTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideDialog();
            }
        });

        final String shareUrl = Api.HTTP_URL + subUrl;

        mWechatConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, ShareUtils.SHARE_TITLE_DYNAMIC
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN
                );
                hideDialog();
            }
        });

        mCircleFriendsConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, ShareUtils.SHARE_TITLE_DYNAMIC
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.WEIXIN_CIRCLE
                );
                hideDialog();
            }
        });

        mQQZoneConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, ShareUtils.SHARE_TITLE_DYNAMIC
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.QZONE
                );
                hideDialog();
            }
        });

        mQQConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(getActivity(), shareUrl, ShareUtils.SHARE_TITLE_DYNAMIC
                        , ShareUtils.SHARE_DESC, Defaultcontent.imageurl, R.mipmap.login_logo, SHARE_MEDIA.QQ
                );
                hideDialog();
            }
        });


    }

    private void hideDialog() {
        if (shareDialog != null)
            shareDialog.cancel();
    }
}
