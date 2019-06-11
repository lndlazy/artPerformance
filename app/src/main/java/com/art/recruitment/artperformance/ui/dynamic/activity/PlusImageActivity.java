package com.art.recruitment.artperformance.ui.dynamic.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.ui.dynamic.adapter.ViewPagerAdapter;
import com.art.recruitment.artperformance.ui.dynamic.contract.MainConstant;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

import static com.blankj.utilcode.util.SnackbarUtils.dismiss;

/**
 * 图片预览页面
 */
public class PlusImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.release_back_imageview)
    ImageView mBackImageview;
    @BindView(R.id.release_position_textview)
    TextView mPositionTextview;
    @BindView(R.id.release_delete_imageview)
    ImageView mDeleteImageview;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private ArrayList<String> imgList; //图片的数据源
    private int mPosition; //第几张图片
    private ViewPagerAdapter mAdapter;
    private Dialog dialog;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plus_image;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        imgList = getIntent().getStringArrayListExtra(MainConstant.IMG_LIST);
        mPosition = getIntent().getIntExtra(MainConstant.POSITION, 0);

        //是否显示删除按钮
        boolean canDelete = getIntent().getBooleanExtra("canDelete", true);
        if (!canDelete)
            mDeleteImageview.setVisibility(View.INVISIBLE);

        initData();
        initBuckClick();
    }

    private void initBuckClick() {
        RxView.
                clicks(mBackImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        back();
                    }
                });

        RxView.
                clicks(mDeleteImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        deletePic();
                    }
                });
    }

    private void initData() {
        mViewPager.addOnPageChangeListener(this);

        mAdapter = new ViewPagerAdapter(this, imgList);
        mViewPager.setAdapter(mAdapter);
        mPositionTextview.setText(mPosition + 1 + "/" + imgList.size());
        mViewPager.setCurrentItem(mPosition);
    }

    private void deletePic() {

        View inflate = View.inflate(this, R.layout.dialog_cancel_or_ok, null);
        TextView mDialogTitle = inflate.findViewById(R.id.dialog_title_tv);
        TextView mCleanImageView = inflate.findViewById(R.id.release_dialogcancel_textview);
        TextView mDetermineTextview = inflate.findViewById(R.id.release_dialog_ok_textview);
        dialog = DialogWrapper.
                customViewDialog().
                context(this).
                contentView(inflate).
                cancelable(false, false).
                build();

        dialog.show();
        mDialogTitle.setText("要删除这张图片吗?");
        mCleanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        mDetermineTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgList.remove(mPosition);
                setPosition();
//                dismiss();
                dialog.cancel();

            }
        });
    }

    //设置当前位置
    private void setPosition() {
        mPositionTextview.setText(mPosition + 1 + "/" + imgList.size());
        mViewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }

    private void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgList);
        setResult(MainConstant.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        mPositionTextview.setText(position + 1 + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
