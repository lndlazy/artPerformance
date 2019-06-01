package com.art.recruitment.artperformance.ui.dynamic.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.dynamic.ReleaseDynamicBean;
import com.art.recruitment.artperformance.bean.dynamic.ReleaseDynamicRequest;
import com.art.recruitment.artperformance.bean.mine.OssBean;
import com.art.recruitment.artperformance.bean.mine.SignaTureBean;
import com.art.recruitment.artperformance.ui.dynamic.adapter.GridViewAdapter;
import com.art.recruitment.artperformance.ui.dynamic.contract.MainConstant;
import com.art.recruitment.artperformance.ui.dynamic.contract.ReleaseDynamicContract;
import com.art.recruitment.artperformance.ui.dynamic.presenter.ReleaseDynamicPresenter;
import com.art.recruitment.artperformance.ui.home.adapter.HomeAdapter;
import com.art.recruitment.artperformance.utils.FileMd5Util;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;


public class ReleaseDynamicActivity extends BaseActivity<ReleaseDynamicPresenter> implements ReleaseDynamicContract {

    @BindView(R.id.dynamic_release_clean_textview)
    TextView mReleaseCleanTextview;
    @BindView(R.id.dynamic_release_textview)
    TextView mReleaseTextview;
    @BindView(R.id.dynamic_content_edittext)
    EditText mContentEdittext;
    @BindView(R.id.dynamic_release_recyclerView)
    GridView mReleaseRecyclerView;
    private GridViewAdapter mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList<>();
    private ArrayList<String> mPicLists = new ArrayList<>();
    private OSSCustomSignerCredentialProvider provider;
    private String beans;
    private PutObjectRequest put;
    private OSS oss;
    private String compressPath;

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_dynamic;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        initButtonClick();

        initGridView();
    }

    //初始化展示上传图片的GridView
    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        mReleaseRecyclerView.setAdapter(mGridViewAddImgAdapter);
        mReleaseRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    if (mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                        viewPluImg(position);
                    } else {
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                //压缩后的图片路径
                compressPath = localMedia.getCompressPath();
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }

    private void initButtonClick() {
        RxView.
                clicks(mReleaseCleanTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });

        RxView.
                clicks(mReleaseTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        String mContent = mContentEdittext.getText().toString().trim();
                        if (mContent != null){
                            closeInoutDecorView(ReleaseDynamicActivity.this);
                            provider = new OSSCustomSignerCredentialProvider() {
                                @Override
                                public String signContent(final String content) {
                                    URL url = null;
                                    try {
                                        String mContent = URLEncoder.encode(content, "UTF-8");
                                        url = new URL("http://47.94.8.204:18080/oss/signature?content=" + mContent);
                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoInput(true);
                                        httpURLConnection.setDoOutput(true);
                                        httpURLConnection.setRequestProperty("Authorization", SPUtils.getInstance().getString(BaseConfig.BaseSPKey.TOKEN));
                                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                                        BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
                                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                        int len;
                                        byte[] arr = new byte[1024];
                                        while((len=bis.read(arr))!= -1){
                                            bos.write(arr,0,len);
                                            bos.flush();
                                        }
                                        bos.close();
                                        String string = bos.toString("utf-8");
                                        Gson gson = new Gson();
                                        SignaTureBean signaTureBean = gson.fromJson(string, SignaTureBean.class);
                                        beans = signaTureBean.getData();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return beans;
                                }
                            };
                            if (mPicList.size() > 0) {
                                mPresenter.oss();
                            } else {
                                ReleaseDynamicRequest dynamicRequest = new ReleaseDynamicRequest();
                                dynamicRequest.setContent(mContentEdittext.getText().toString().trim());
                                dynamicRequest.setImagePath(mPicLists);
                                dynamicRequest.setVideoPath("");
                                dynamicRequest.setVideoPreview("");
                                Gson gson = new Gson();
                                String codeStr = gson.toJson(dynamicRequest);
                                mPresenter.releaseDynamic(codeStr);

                                mPicLists.clear();
                            }
                        } else {
                            ToastUtils.showShort("请输入发布内容");
                        }

                    }
                });
    }

    @Override
    public void returnReleaseDynamicBean(ReleaseDynamicBean.DataBean bean) {
        ToastUtils.showShort("发布成功");
    }

    @Override
    public void returnSignaTureBean(final String bean) {

    }

    @Override
    public void returnOssBean(final OssBean.DataBean bean) {

        oss = new OSSClient(getApplicationContext(), bean.getEndpoint(), provider);

        for (int i = 0; i < mPicList.size(); i++) {
            // 构造上传请求
            try {
                File file = new File(mPicList.get(i));
                InputStream inputStream = new FileInputStream(file);
                String digest = FileMd5Util.digest(inputStream);
                put = new PutObjectRequest(bean.getBucket(), "dynamic/" + digest + ".jpg", mPicList.get(i));
                mPicLists.add("dynamic/" + digest + ".jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                ReleaseDynamicActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ReleaseDynamicRequest dynamicRequest = new ReleaseDynamicRequest();
                        dynamicRequest.setContent(mContentEdittext.getText().toString().trim());
                        dynamicRequest.setImagePath(mPicLists);
                        dynamicRequest.setVideoPath("");
                        dynamicRequest.setVideoPreview("");
                        Gson gson = new Gson();
                        String codeStr = gson.toJson(dynamicRequest);
                        mPresenter.releaseDynamic(codeStr);

                        mPicLists.clear();
                    }
                });

            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

    /**
     * 关闭软件盘
     */
    public static void closeInoutDecorView(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}