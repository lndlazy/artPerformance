package com.art.recruitment.artperformance.ui.dynamic.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.utils.FileMd5Util;
import com.art.recruitment.artperformance.utils.ImageUtils;
import com.art.recruitment.artperformance.view.ActionSheetDialog;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.utils.SystemUtil;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 发布动态圈
 */
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
    private ArrayList<String> mPicObjectKeyLists = new ArrayList<>();
    private OSSCustomSignerCredentialProvider provider;
    private String beans;
    private OSS oss;
    private String compressPath;
    private String videoPath = "";//视频路径
    private String videoObjectKey = "";
    private String videoPreviewPath = "";//视频预览路径
    private String videoPreviewPathObjectkey = "";//视频预览路径objectkey
    private ProgressDialog show;
    private Bitmap videoThumbnail;


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

        mReleaseTextview.setSelected(true);
    }

    //初始化展示上传图片的GridView
    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        mReleaseRecyclerView.setAdapter(mGridViewAddImgAdapter);
        mReleaseRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (!TextUtils.isEmpty(videoPath))
                    //预览视频
                    startPreVideo();
                else {


                    if (position == parent.getChildCount() - 1) {
                        if (mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                            viewPluImg(position);
                        } else {
                            showTypeDialog(parent, position);
//                            selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                        }
                    } else {
                        viewPluImg(position);
                    }

                }


            }
        });
    }

    /**
     * 预览视频
     */
    private void startPreVideo() {
        PictureSelector.create(ReleaseDynamicActivity.this).externalPictureVideo(videoPath);
    }

    private void choosePic(AdapterView<?> parent, int position) {
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


    /**
     * 弹出选择照片的对话框
     *
     * @param parent
     * @param position
     */
    @Deprecated
    protected void showTypeDialog(final AdapterView<?> parent, final int position) {

        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("视频", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                // 视频
                                mGridViewAddImgAdapter.setType(Constant.CHOOSE_TYPE_VIDEO);
                                PictureSelectorConfig.initMultiConfig(ReleaseDynamicActivity.this, 1, Constant.CHOOSE_TYPE_VIDEO);

                            }

                        })
                .addSheetItem("图片", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                // 图片
                                mGridViewAddImgAdapter.setType(Constant.CHOOSE_TYPE_PICTURE);
                                choosePic(parent, position);
                            }
                        }).show();

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
        PictureSelectorConfig.initMultiConfig(this, maxTotal, Constant.CHOOSE_TYPE_PICTURE);
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
                    //选择的是照片
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    break;
                case 0x22:
                    //选择的是视频
                    setVideoAdapter(PictureSelector.obtainMultipleResult(data));
//                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
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

    private void setVideoAdapter(List<LocalMedia> localMedia) {

        if (localMedia != null && localMedia.size() > 0) {

            videoPath = localMedia.get(0).getPath();

            videoThumbnail = ImageUtils.getVideoThumbnail(videoPath, 500, 500);
            List<Bitmap> bitmaps = new ArrayList<>();
            bitmaps.add(videoThumbnail);
            mGridViewAddImgAdapter.setBitmapList(bitmaps);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统 sd 卡权限
        PictureFileUtils.deleteCacheDirFile(ReleaseDynamicActivity.this);
    }

    private void initButtonClick() {

        //取消发布
        RxView.
                clicks(mReleaseCleanTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });

        //发布动态圈
        RxView.
                clicks(mReleaseTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        nextStep();

                    }
                });
    }

    private void nextStep() {

        String mContent = mContentEdittext.getText().toString().trim();

        if (TextUtils.isEmpty(mContent) && TextUtils.isEmpty(videoPath) &&
                mPicList.size() < 1)
            return;

        SystemUtil.closeInoutDecorView(ReleaseDynamicActivity.this);

        //获取provider
        if (!TextUtils.isEmpty(videoPath) || mPicList.size() > 0) {
            setProvider();
            mPresenter.oss();
        } else
            sendDynamic();

    }

    //发布动态圈
    private void sendDynamic() {

        ReleaseDynamicRequest dynamicRequest = new ReleaseDynamicRequest();
        dynamicRequest.setContent(mContentEdittext.getText().toString().trim());
        dynamicRequest.setImagePath(mPicObjectKeyLists);
        dynamicRequest.setVideoPath(videoObjectKey);
        dynamicRequest.setVideoPreview(videoPreviewPathObjectkey);
        Gson gson = new Gson();
        String codeStr = gson.toJson(dynamicRequest);
        mPresenter.releaseDynamic(codeStr);

        mPicObjectKeyLists.clear();
        mPicList.clear();

        videoPath = "";//视频路径
        videoObjectKey = "";
        videoPreviewPath = "";//视频预览路径
        videoPreviewPathObjectkey = "";//视频预览路径objectkey
    }


    private void setProvider() {

        provider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(final String content) {
                URL url = null;
                try {
                    String mContent = URLEncoder.encode(content, "UTF-8");
                    url = new URL(Api.HTTP_URL + Api.OSS_SIGNATURE + mContent);
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
                    while ((len = bis.read(arr)) != -1) {
                        bos.write(arr, 0, len);
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
    }

    @Override
    public void returnReleaseDynamicBean(ReleaseDynamicBean.DataBean bean) {
        ToastUtils.showShort("发布成功");
        finish();
    }

    @Override
    public void returnSignaTureBean(final String bean) {

    }

    //获取到签名信息,开始上传
    @Override
    public void returnOssBean(final OssBean.DataBean bean) {

        oss = new OSSClient(getApplicationContext(), bean.getEndpoint(), provider);

        show = ProgressDialog.show(ReleaseDynamicActivity.this, "", "上传中...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (!TextUtils.isEmpty(videoPath)) {
                        //上传视频

                        File videoFile = new File(videoPath);
                        InputStream inputStream = new FileInputStream(videoFile);
                        String videoMd5 = FileMd5Util.digest(inputStream);
                        videoObjectKey = Constant.DIR_DYNAMIC_VIDEO + FileMd5Util.digest(inputStream) + Constant.VIDEO_DIR;
                        PutObjectRequest videoPut = new PutObjectRequest(bean.getBucket(), videoObjectKey, videoPath);
//                        String s = UUID.randomUUID().toString();
//                        ImageUtils.get
                        //用视频的md5值 当封面图片的 名称
                        videoPreviewPath = ImageUtils.saveBitmap(videoThumbnail, videoMd5 + ".jpg");
                        File videoPreviewFile = new File(videoPreviewPath);
                        InputStream inputPreviewStream = new FileInputStream(videoPreviewFile);
                        videoPreviewPathObjectkey = Constant.DIR_DYNAMIC_VIDEO_PREVIEW + FileMd5Util.digest(inputPreviewStream) + Constant.PIC_DIR;
                        PutObjectRequest videoPathPut = new PutObjectRequest(bean.getBucket(), videoPreviewPathObjectkey, videoPreviewPath);

                        oss.putObject(videoPut);
                        oss.putObject(videoPathPut);
                        onUiThreadUp();

                    } else {

                        //上传图片
                        for (int i = 0; i < mPicList.size(); i++) {
                            // 构造上传请求
                            try {
                                File file = new File(mPicList.get(i));
                                InputStream inputStream = new FileInputStream(file);
                                String digest = FileMd5Util.digest(inputStream);
                                PutObjectRequest picturePut = new PutObjectRequest(bean.getBucket(), Constant.DIR_DYNAMIC + digest + Constant.PIC_DIR, mPicList.get(i));
                                mPicObjectKeyLists.add(Constant.DIR_DYNAMIC + digest);
                                PutObjectResult result = oss.putObject(picturePut);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }

                        onUiThreadUp();

                    }


                } catch (ClientException e) {
                    e.printStackTrace();
                } catch (ServiceException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    dismissDialog();
                }

            }
        }).start();

//        // 异步上传时可以设置进度回调
//        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
//            }
//        });
//
//        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//
//                ReleaseDynamicActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ReleaseDynamicRequest dynamicRequest = new ReleaseDynamicRequest();
//                        dynamicRequest.setContent(mContentEdittext.getText().toString().trim());
//                        dynamicRequest.setImagePath(mPicObjectKeyLists);
//                        dynamicRequest.setVideoPath("");
//                        dynamicRequest.setVideoPreview("");
//                        Gson gson = new Gson();
//                        String codeStr = gson.toJson(dynamicRequest);
//                        mPresenter.releaseDynamic(codeStr);
//
//                        mPicObjectKeyLists.clear();
//                    }
//                });
//
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                // 请求异常
//                if (clientExcepion != null) {
//                    // 本地异常如网络异常等
//                    clientExcepion.printStackTrace();
//                }
//                if (serviceException != null) {
//                    // 服务异常
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
//                }
//            }
//        });

    }

    private void onUiThreadUp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissDialog();
                sendDynamic();
            }
        });
    }

    private void dismissDialog() {
        if (show != null && show.isShowing())
            show.dismiss();
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

}