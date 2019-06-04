package com.art.recruitment.artperformance.ui.mine.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.mine.ConsummateInfoBean;
import com.art.recruitment.artperformance.bean.mine.ConsummateInfoRequest;
import com.art.recruitment.artperformance.bean.mine.MineBean;
import com.art.recruitment.artperformance.bean.mine.OssBean;
import com.art.recruitment.artperformance.bean.mine.SignaTureBean;
import com.art.recruitment.artperformance.ui.home.activity.CityActivity;
import com.art.recruitment.artperformance.ui.mine.FileType;
import com.art.recruitment.artperformance.ui.mine.ImageModel;
import com.art.recruitment.artperformance.ui.mine.adapter.MinePhotoAdapter;
import com.art.recruitment.artperformance.ui.mine.adapter.MineVideoAdapter;
import com.art.recruitment.artperformance.ui.mine.contract.MineDataContract;
import com.art.recruitment.artperformance.ui.mine.presenter.MineDataPresenter;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.utils.MatisseGlideEngine;
import com.art.recruitment.artperformance.utils.PermissionTipUtils;
import com.art.recruitment.artperformance.utils.StringsUtils;
import com.art.recruitment.artperformance.utils.UriUtil;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.artperformance.view.PermissionRationalDialog;
import com.art.recruitment.artperformance.view.SwitchButton;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.SettingService;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.SelectionCreator;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 编辑个人资料
 */
public class MineDataActivity extends BaseActivity<MineDataPresenter> implements MineDataContract {

    @BindView(R.id.coverDelete)
    ImageView coverDelete;
    @BindView(R.id.mine_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.mine_arrow_imageview)
    ImageView mArrowImageview;
    @BindView(R.id.mine_name_edittext)
    EditText mNameEdittext;
    @BindView(R.id.mine_man_redioButton)
    RadioButton mManRedioButton;
    @BindView(R.id.mine_woman_redioButton)
    RadioButton mWomanRedioButton;
    @BindView(R.id.mine_age_edittext)
    EditText mAgeEdittext;
    @BindView(R.id.mine_telePhone_edittext)
    EditText mTelePhoneEdittext;
    @BindView(R.id.mine_weChat_edittext)
    EditText mWeChatEdittext;
    @BindView(R.id.mine_height_edittext)
    EditText mHeightEdittext;
    @BindView(R.id.mine_weight_edittext)
    EditText mWeightEdittext;
    @BindView(R.id.mine_bust_edittext)
    EditText mBustEdittext;
    @BindView(R.id.mine_waist_edittext)
    EditText mWaistEdittext;
    @BindView(R.id.mine_hipline_edittext)
    EditText mHiplineEdittext;
    @BindView(R.id.mine_citiy_textview)
    TextView mCitiyTextview;
    @BindView(R.id.mine_citiy_edittext)
    TextView mCitiyEdittext;
    @BindView(R.id.mine_citiy_imageview)
    ImageView mCitiyImageview;
    //    @BindView(R.id.mine_master_graph_recyclerView)
//    RecyclerView mMasterGraphRecyclerView;
    @BindView(R.id.mine_photo_recyclerView)
    RecyclerView mPhotoRecyclerView;
    @BindView(R.id.mine_video_recyclerView)
    RecyclerView mVideoRecyclerView;
    @BindView(R.id.mine_other_edittext)
    EditText mOtherEdittext;
    @BindView(R.id.mine_other_number_textviewv)
    TextView mOtherNumberTextviewv;
    @BindView(R.id.mine_preservation_textview)
    TextView mPreservationTextview;
    @BindView(R.id.mine_data_telePhone_switchButton)
    SwitchButton mTelePhoneSwitchButton;
    @BindView(R.id.mine_data_wxChat_switchButton)
    SwitchButton mWxChatSwitchButton;
    @BindView(R.id.mine_data_redioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.mine_data_head_imageview)
    SimpleDraweeView mHeadImageview;
    @BindView(R.id.coverPic)
    SimpleDraweeView coverPic;

    private ImageModel mAddImageModel;
    private static int PHOTO_MAX_LIMIT_COUNT = 4;  //限定最多选择4张图片
    private static int MASTER_MAX_LIMIT_COUNT = 1;  //限定最多选择4张图片
    private static int VIDEO_MAX_LIMIT_COUNT = 1;  //限定最多选择4张图片
    private SelectionCreator mMatisseBuilder;
    private int mClickedItemPosition;
    private MinePhotoAdapter mPhotoAdapter;
    //    private MineMasterAdapter mMasterAdapter;
    private MineVideoAdapter mVideoAdapter;
    private List<ImageModel> mImageLists;
    //    private List<ImageModel> mImageListt;
    private List<ImageModel> mImageList;
    private List<ImageModel> mImageLisa;
    private Dialog mPermissionSettingDialog;
    private int gender;
    private List<String> mPhoto = new ArrayList<>();
    private List<String> mPhotoList = new ArrayList<>();
    private List<String> mPrimaryPhoto = new ArrayList<>();
    //    private List<String> mPrimaryPhotoList = new ArrayList<>();
    private String personalIntroductionVideo;
    private String personalIntroductionVideoList;
    private String mAvaterList;
    private String avater;
    private int telePhoneSwitchButton = 1;
    private int wxChatSwitchButton = 1;
    private int cityCode;
    private boolean isAddItemClicked;
    private int returnImageview;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_COVER_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static int output_X = 128;
    private static int output_Y = 128;
    //业务服务器返回的签名content
    private String signatureContent;
    private OSSCustomSignerCredentialProvider provider;
    private OSSClient oss;
    private String mAge, mWeight, mBust, mHeight, mHips, mName, mOther, mTelePhone, mWaist, mWeChat;
    private PutObjectRequest putVideo;
    private PutObjectRequest putPhoto;
    private PutObjectRequest putPrimaryPhoto;
    private PutObjectRequest putHead;
    private MineBean.DataBean dataBean;
    private int editorType;


    private String headPicUrl;//用户头像
    private File photoFile;
    private File outFile;//裁剪输出文件夹
    private String bucket;
    private String endpoint;
    private String coverObjectKey;////封面ObjectKey
    private String coverPicUrl;//封面图片
    private String headPicObjectKey;//头像ObjectKey

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_data;
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

        editorType = getIntent().getIntExtra(Constant.EDITOR_TYPE, -1);

        mPresenter.getPersonalData();

        initPic();

        initMatisse();

        initButtonClick();

        initRecyclerView();

        mTelePhoneSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    telePhoneSwitchButton = 1;
                } else {
                    telePhoneSwitchButton = 0;
                }
            }
        });

        mWxChatSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    wxChatSwitchButton = 1;
                } else {
                    wxChatSwitchButton = 0;
                }
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.mine_man_redioButton:
                        gender = 1;
                        break;
                    case R.id.mine_woman_redioButton:
                        gender = 2;
                        break;
                    default:
                        break;
                }
            }
        });

        coverDelete.setVisibility(View.INVISIBLE);

    }

    private void initPic() {

        //获取阿里云签名
        if (TextUtils.isEmpty(signatureContent) || provider == null)
            setProvider();

        //oss签名信息
        if (oss == null || TextUtils.isEmpty(bucket) || TextUtils.isEmpty(endpoint))
            mPresenter.oss();

        //图片缓存目录
        photoFile = new File(Constant.CACHE_DIR);
        //文件夹不存在就创建文件夹
        if (!photoFile.exists())
            photoFile.mkdirs();


    }

    private void initButtonClick() {
        RxView.
                clicks(mReturnImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });

        RxView.
                clicks(mPreservationTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        initComit();
                    }
                });

        RxView.
                clicks(mCitiyImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivityForResult(new Intent(MineDataActivity.this, CityActivity.class), 100);
                    }
                });

        RxView.
                clicks(mArrowImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        choseHeadImageFromGallery();
                    }
                });

        RxView.
                clicks(mHeadImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        choseHeadImageFromGallery();
                    }
                });

        RxView.
                clicks(coverPic).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
//                        returnImageview = 2;
//                        requestPermissionAndSelectImage();
                        chooseCoverPic();
                    }
                });
        coverDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除封面
                coverPicUrl = "";
                coverPic.setImageURI(Uri.parse("res://" + getPackageName() + "/" + R.mipmap.icon_my_add));
            }
        });
    }


    private void chooseCoverPic() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //不具有权限，需要进行权限申请
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x21);
        } else {
            Intent mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(mIntent, CODE_COVER_REQUEST);
        }

    }

    //保存
    private void initComit() {

        mAge = mAgeEdittext.getText().toString().trim();
        mWeight = mWeightEdittext.getText().toString().trim();
        mBust = mBustEdittext.getText().toString().trim();
        mHeight = mHeightEdittext.getText().toString().trim();
        mHips = mHiplineEdittext.getText().toString().trim();
        mName = mNameEdittext.getText().toString().trim();
        mOther = mOtherEdittext.getText().toString().trim();
        mTelePhone = mTelePhoneEdittext.getText().toString().trim();
        mWaist = mWaistEdittext.getText().toString().trim();
        mWeChat = mWeChatEdittext.getText().toString().trim();

        switch (editorType) {

            case Constant.EDITOR_TYPE_APPLY://应聘

                applyNextStep();

                break;
            case Constant.EDITOR_TYPE_RELEASE://发布招募

                releaseNextStep();

                break;

            default:
                releaseNextStep();
                break;
        }

    }

    private void applyNextStep() {

        if (!TextUtils.isEmpty(mAge) && !TextUtils.isEmpty(mWeight) && !TextUtils.isEmpty(mBust) &&
                !TextUtils.isEmpty(mHeight) && !TextUtils.isEmpty(mHips) && !TextUtils.isEmpty(mName) &&
                !TextUtils.isEmpty(mOther) && !TextUtils.isEmpty(mTelePhone) && !TextUtils.isEmpty(mWaist) &&
                !TextUtils.isEmpty(mWeChat) && !TextUtils.isEmpty(avater) && !TextUtils.isEmpty(personalIntroductionVideo)) {

            setProvider();

            Logger.d("personalIntroductionVideo==>" + personalIntroductionVideo
                    + "    ,avater:" + avater);

            if (mPhoto.size() > 0 && mPrimaryPhoto.size() > 0 && personalIntroductionVideo.length() > 0 && avater.length() > 0) {
                mPresenter.oss();
            } else {
                ToastUtils.showShort(getResources().getString(R.string.apply_input_all));
            }

        } else {
            ToastUtils.showShort(getResources().getString(R.string.apply_input_all));
        }
    }

    //发布招募的保存信息
    private void releaseNextStep() {

        if (TextUtils.isEmpty(mNameEdittext.getText().toString())) {
            ToastUtils.showShort(getResources().getString(R.string.input_name));
            return;
        }

        if (!mManRedioButton.isChecked() && !mWomanRedioButton.isChecked()) {
            ToastUtils.showShort(getResources().getString(R.string.input_sex));
            return;
        }

        if (TextUtils.isEmpty(mTelePhoneEdittext.getText().toString())) {
            ToastUtils.showShort(getResources().getString(R.string.input_tel));
            return;
        }

        inputInfo();

    }

    private void inputInfo() {

        ConsummateInfoRequest request = new ConsummateInfoRequest();

        if (StringsUtils.is2Int(mAge))
            request.setAge(Integer.parseInt(mAge));
//        request.setAvatar(mAvaterList);

        Logger.d("头像地址::" + headPicUrl + ",头像objectKey::" + headPicObjectKey);
        if (!TextUtils.isEmpty(headPicObjectKey))
            request.setAvatar(headPicObjectKey);

        if (StringsUtils.is2Int(mWeight))
            request.setBodyWeight(Integer.parseInt(mWeight));

        if (StringsUtils.is2Int(mBust))
            request.setBust(Integer.parseInt(mBust));
//        if (cityCode != -1)
        request.setCityId(cityCode);
        request.setGender(gender);

        if (StringsUtils.is2Int(mHeight))
            request.setHeight(Integer.parseInt(mHeight));

        if (StringsUtils.is2Int(mHips))
            request.setHips(Integer.parseInt(mHips));
        request.setName(mName);
        //其他经历
        request.setPersonalExperience(mOther);
        request.setPersonalIntroductionVideo(personalIntroductionVideoList);
        request.setPhoto(mPhotoList);
        //封面
//        request.setPrimaryPhoto(mPrimaryPhotoList);
        ArrayList<String> strings = new ArrayList<>();
        strings.add(coverObjectKey);
        request.setPrimaryPhoto(strings);
        request.setTelephone(mTelePhone);
        //是否隐藏电话
        request.setTelephoneHiddenFlag(telePhoneSwitchButton);

        if (StringsUtils.is2Int(mWaist))
            request.setWaist(Integer.parseInt(mWaist));
        request.setWechat(mWeChat);
        //是否隐藏微信
        request.setWechatHiddenFlag(wxChatSwitchButton);

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String codeStr = gson.toJson(request);
        mPresenter.consummateInfo3(codeStr);
    }

    private void setProvider() {
        provider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(final String content) {

//                Logger.d("content内容::" + content);

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

//                    Logger.d("返回的数据??==>" + string);
                    Gson gson = new Gson();
                    SignaTureBean signaTureBean = gson.fromJson(string, SignaTureBean.class);
                    signatureContent = signaTureBean.getData();

//                    Logger.d("signatureContent???===>" + signatureContent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return signatureContent;
            }
        };

    }

    @Override
    public void returnMineDataBean(MineBean.DataBean bean) {

        mNameEdittext.setText(bean.getName());
        if (bean.getGender() == 1) {
            mManRedioButton.setChecked(true);
            mWomanRedioButton.setChecked(false);
        } else if (bean.getGender() == 2) {
            mWomanRedioButton.setChecked(true);
            mManRedioButton.setChecked(false);
        }

        if (bean.getAge() > 0)
            mAgeEdittext.setText(bean.getAge() + "");

        mTelePhoneEdittext.setText(bean.getTelephone());
        mWeChatEdittext.setText(bean.getWechat());

        if (bean.getHeight() > 0)
            mHeightEdittext.setText(bean.getHeight() + "");

        if (bean.getBodyWeight() > 0)
            mWeightEdittext.setText(bean.getBodyWeight() + "");

        if (bean.getBust() > 0)
            mBustEdittext.setText(bean.getBust() + "");

        if (bean.getWaist() > 0)
            mWaistEdittext.setText(bean.getWaist() + "");

        if (bean.getHips() > 0)
            mHiplineEdittext.setText(bean.getHips() + "");

        if (!TextUtils.isEmpty(bean.getCityName()))
            mCitiyEdittext.setText(bean.getCityName());

        //设置头像
        if (!TextUtils.isEmpty(bean.getAvatarView())) {
            mHeadImageview.setImageURI(Uri.parse(bean.getAvatarView()));
            headPicUrl = bean.getAvatar();
        }

        mOtherEdittext.setText(bean.getPersonalExperience());
        dataBean = bean;

//        if (bean.getPhotoView() != null && bean.getPhotoView().size() > 0) {
//            List<ImageModel> mTempImageList = new ArrayList<>();
//            for (int i = 0; i < dataBean.getPhotoView().size(); i++) {
//                ImageModel model = new ImageModel();
//                model.setUris(dataBean.getPhotoView().get(i));
//                mTempImageList.add(model);
//            }
//            mImageList.remove(mClickedItemPosition);
//            mImageList.addAll(mTempImageList);
//            if (mImageList.size() < 4) {
//                mImageList.add(mAddImageModel);
//            }
//            mPhotoAdapter.setNewData(mImageList);
//            for (int i = 0; i < mImageList.size() - 1; i++) {
//                mPhoto.add(mImageList.get(i).getUris());
//                String urisPhoto = mImageList.get(i).getUris();
//                String subPhoto = urisPhoto.substring(urisPhoto.indexOf("minedata/"), urisPhoto.indexOf("?"));
//                mPhotoList.add(subPhoto);
//            }
//            mPhotoAdapter.notifyDataSetChanged();
//        }


//          封面??????
//        if (bean.getPrimaryPhotoView() != null && bean.getPrimaryPhotoView().size() > 0) {
//            List<ImageModel> mTempImageList1 = new ArrayList<>();
//            for (int i = 0; i < dataBean.getPrimaryPhotoView().size(); i++) {
//                ImageModel model = new ImageModel();
//                model.setUris(dataBean.getPrimaryPhotoView().get(i));
//                mTempImageList1.add(model);
//            }
//            mImageListt.remove(mClickedItemPosition);
//            mImageListt.addAll(mTempImageList1);
//            if (mImageListt.size() < 1) {
//                mImageListt.add(mAddImageModel);
//            }
//            mMasterAdapter.setNewData(mImageListt);
//            mPrimaryPhoto.add(mImageListt.get(0).getUris());
//            String urisPrimaryPhoto = mImageList.get(0).getUris();
//            String subPrimaryPhoto = urisPrimaryPhoto.substring(urisPrimaryPhoto.indexOf("minedata/"), urisPrimaryPhoto.indexOf("?"));
//            mPrimaryPhotoList.add(subPrimaryPhoto);
//            mMasterAdapter.notifyDataSetChanged();
//        }

        if (!TextUtils.isEmpty(bean.getPersonalIntroductionVideo())) {
            //TODO videolist?
//            List<ImageModel> mTempImageList2 = new ArrayList<>();
//            ImageModel model2 = new ImageModel();
//            model2.setUris(dataBean.getPersonalIntroductionVideo());
//            mTempImageList2.add(model2);
//            mImageLists.remove(mClickedItemPosition);
//            mImageLists.addAll(mTempImageList2);
//            if (mImageLists.size() < 1) {
//                mImageLists.add(mAddImageModel);
//            }
//            personalIntroductionVideo = mImageLists.get(0).getUris();
//            String urisVideo = mImageList.get(0).getUris();
//
//            Log.e("TAG", "urisVideo======》》》》》》" + urisVideo);
//
//            try {
//
//                String subVideo = urisVideo.substring(urisVideo.indexOf("minedata/"), urisVideo.indexOf("?"));
//                personalIntroductionVideoList = subVideo;
//                mVideoAdapter.setNewData(mImageLists);
//                mVideoAdapter.notifyDataSetChanged();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        }

//        if (!TextUtils.isEmpty(bean.getAvatar()))

//        Glide.with(this).load(bean.getAvatar()).into(mHeadImageview).onLoadFailed(new BitmapDrawable( getResources().getDrawable(R.mipmap.icon_my_e)));

//        if (!TextUtils.isEmpty(dataBean.getAvatarView())) {
//            List<ImageModel> mTempImageList3 = new ArrayList<>();
//            ImageModel imageModel3 = new ImageModel();
//            imageModel3.setUris(dataBean.getAvatarView());
//            mTempImageList3.add(imageModel3);
//            mImageLisa = new ArrayList<>();
//            mImageLisa.addAll(mTempImageList3);
//            avater = mImageLisa.get(0).getUris();
//            String urisAvater = mImageLisa.get(0).getUris();
//            String subAvater = urisAvater.substring(urisAvater.indexOf("minedata/"), urisAvater.indexOf("?"));
//            mAvaterList = subAvater;
//        }

    }

    @Override
    public void returnEssentialInfoBean(ConsummateInfoBean.DataBean bean) {
        ToastUtils.showShort("资料已保存");
        mPhotoList.clear();
//        mPrimaryPhotoList.clear();
        personalIntroductionVideoList = null;
        finish();
//        mHeadImageview = null;
    }

    @Override
    public void returnSignaTureBean(String bean) {
        ToastUtils.showShort(bean);
    }

    @Override
    public void returnOssBean(OssBean.DataBean bean) {

        if (bean == null) {
            ToastUtils.showShort("数据错误");
            return;
        }

        Logger.d("Bucket:::" + bean.getBucket() + ",,,,Endpoint::" + bean.getEndpoint());

        oss = new OSSClient(getApplicationContext(), bean.getEndpoint(), provider);
        bucket = bean.getBucket();
        endpoint = bean.getEndpoint();

//        if (mHeadImageview != null) {
//            // 构造上传请求
//            if (!avater.contains("minedata/")) {
//                try {
//                    mAvaterList = null;
//                    File file = new File(avater);
//                    InputStream inputStream = new FileInputStream(file);
//                    String digest = FileMd5Util.digest(inputStream);
//                    putHead = new PutObjectRequest(bean.getBucket(), "minedata/" + digest + ".jpg", avater);
//                    mAvaterList = "minedata/" + digest + ".jpg";
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//            } else {
//                putHead = new PutObjectRequest(bean.getBucket(), mAvaterList, avater);
//            }
//
//        }
//
//        for (int i = 0; i < mPrimaryPhoto.size(); i++) {
//            // 构造上传请求
//            if (!mPrimaryPhoto.get(i).contains("minedata/")) {
//                try {
//                    mPrimaryPhotoList.remove(mPrimaryPhotoList.get(i));
//                    File file = new File(mPrimaryPhoto.get(i));
//                    InputStream inputStream = new FileInputStream(file);
//                    String digest = FileMd5Util.digest(inputStream);
//                    putPrimaryPhoto = new PutObjectRequest(bean.getBucket(), "minedata/" + digest + ".jpg", mPrimaryPhoto.get(i));
//                    mPrimaryPhotoList.add("minedata/" + digest + ".jpg");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                putPrimaryPhoto = new PutObjectRequest(bean.getBucket(), mPrimaryPhotoList.get(i), mPrimaryPhoto.get(i));
//            }
//
//        }
//
//        for (int i = 0; i < mPhoto.size(); i++) {
//            // 构造上传请求
//            if (!mPhoto.get(i).contains("minedata/")) {
//                try {
//                    mPhotoList.remove(mPhotoList.get(i));
//                    File file = new File(mPhoto.get(i));
//                    InputStream inputStream = new FileInputStream(file);
//                    String digest = FileMd5Util.digest(inputStream);
//                    putPhoto = new PutObjectRequest(bean.getBucket(), "minedata/" + digest + ".jpg", mPhoto.get(i));
//                    mPhotoList.add("minedata/" + digest + ".jpg");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                putPhoto = new PutObjectRequest(bean.getBucket(), mPhotoList.get(i), mPhoto.get(i));
//            }
//
//        }
//
//        if (personalIntroductionVideo.length() > 0) {
//            // 构造上传请求
//            if (!personalIntroductionVideo.contains("minedata/")) {
//                try {
//                    personalIntroductionVideoList = null;
//                    File file = new File(personalIntroductionVideo);
//                    InputStream inputStream = new FileInputStream(file);
//                    String digest = FileMd5Util.digest(inputStream);
//                    putVideo = new PutObjectRequest(bean.getBucket(), "minedata/" + digest + ".mp4", personalIntroductionVideo);
//                    personalIntroductionVideoList = "minedata/" + digest + ".mp4";
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                putVideo = new PutObjectRequest(bean.getBucket(), personalIntroductionVideoList, personalIntroductionVideo);
//            }
//        }
//
//        // 异步上传时可以设置进度回调
//        /*put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
//            }
//        });*/
//
//        oss.asyncPutObject(putHead, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
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
//                }
//            }
//        });
//
//        oss.asyncPutObject(putPrimaryPhoto, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
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
//                }
//            }
//        });
//
//        oss.asyncPutObject(putPhoto, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
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
//                }
//            }
//        });
//
//        oss.asyncPutObject(putVideo, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
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
//                }
//            }
//        });
//
//        ConsummateInfoRequest request = new ConsummateInfoRequest();
//        request.setAge(Integer.parseInt(mAge));
//        request.setAvatar(mAvaterList);
//        request.setBodyWeight(Integer.parseInt(mWeight));
//        request.setBust(Integer.parseInt(mBust));
//        request.setCityId(cityCode);
//        request.setGender(gender);
//        request.setHeight(Integer.parseInt(mHeight));
//        request.setHips(Integer.parseInt(mHips));
//        request.setName(mName);
//        request.setPersonalExperience(mOther);
//        request.setPersonalIntroductionVideo(personalIntroductionVideoList);
//        request.setPhoto(mPhotoList);
//        request.setPrimaryPhoto(mPrimaryPhotoList);
//        request.setTelephone(mTelePhone);
//        request.setTelephoneHiddenFlag(telePhoneSwitchButton);
//        request.setWaist(Integer.parseInt(mWaist));
//        request.setWechat(mWeChat);
//        request.setWechatHiddenFlag(wxChatSwitchButton);
//
//        Gson gson = new Gson();
//        String codeStr = gson.toJson(request);
//        mPresenter.consummateInfo(codeStr);
    }

    @Override
    public void returnPathUrlBean(String picUrl, int type) {

        Logger.d("服务器返回的图片地址::" + picUrl);
        if (TextUtils.isEmpty(picUrl))
            return;

        switch (type) {

            case FileType.PIC_TYPE_HEAD://头像
                headPicUrl = picUrl;
                break;

            case FileType.PIC_TYPE_COVER://封面

                coverPicUrl = picUrl;
                coverDelete.setVisibility(View.VISIBLE);

                break;

            case FileType.PIC_TYPE_PHOTO://照片

                break;

            case FileType.PIC_TYPE_VIDEO://视频
                break;

        }

    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            ToastUtils.showShort(message);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == AppCompatActivity.RESULT_OK) {
            mPhoto.removeAll(mPhoto);
            List<Uri> mSelectedImages = Matisse.obtainResult(data);
            if (mSelectedImages != null && mSelectedImages.size() > 0) {
                List<String> mSelectedImagesPath = UriUtil.getImagePathes(this, mSelectedImages);
                if (mSelectedImagesPath != null && mSelectedImagesPath.size() > 0) {
                    List<ImageModel> mTempImageList = new ArrayList<>();
                    for (String path : mSelectedImagesPath) {
                        ImageModel imageModel = new ImageModel();
                        imageModel.setUris(path);
                        mTempImageList.add(imageModel);
                    }
                    if (isAddItemClicked) {  //点击的添加item
                        mImageList.remove(mClickedItemPosition);
                        mImageList.addAll(mTempImageList);  //追加新选择数据
                        if (mImageList.size() < 4) {
                            mImageList.add(mAddImageModel);
                        }
                    } else {  //更换图片，只能选择一张
                        mImageList.set(mClickedItemPosition, mTempImageList.get(0));
                    }
                    mPhotoAdapter.setNewData(mImageList);
                    for (int i = 0; i < mImageList.size() - 1; i++) {
                        mPhoto.add(mImageList.get(i).getUris());
                    }
                    mPhotoAdapter.notifyDataSetChanged();
                }
            }
        } else if (requestCode == 300 && resultCode == AppCompatActivity.RESULT_OK) {
            personalIntroductionVideo = null;
            List<Uri> mSelectedImages = Matisse.obtainResult(data);
            if (mSelectedImages != null && mSelectedImages.size() > 0) {
                List<String> mSelectedImagesPath = UriUtil.getImagePathes(this, mSelectedImages);
                if (mSelectedImagesPath != null && mSelectedImagesPath.size() > 0) {
                    List<ImageModel> mTempImageList = new ArrayList<>();
                    for (String path : mSelectedImagesPath) {
                        ImageModel imageModel = new ImageModel();
                        imageModel.setUris(path);
                        mTempImageList.add(imageModel);
                    }
                    mImageLists.remove(mClickedItemPosition);
                    mImageLists.addAll(mTempImageList);
                    if (mImageLists.size() < 1) {
                        mImageLists.add(mAddImageModel);
                    }
                    personalIntroductionVideo = mImageLists.get(0).getUris();
                    mVideoAdapter.setNewData(mImageLists);
                    mVideoAdapter.notifyDataSetChanged();
                }
            }
        } else if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {

            if (data != null && data.getExtras() != null) {
                String city = data.getExtras().getString("city");
                cityCode = data.getExtras().getInt("code");
                mCitiyEdittext.setText(city);
            }

        } else if (requestCode == CODE_GALLERY_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {

            //相册选择后 开始裁剪
            outFile = new File(photoFile.getPath(), UUID.randomUUID().toString() + ".jpg");
//            Logger.d("outFile地址::" + outFile.getPath() + "====>" + outFile.getAbsolutePath());
            cropRawPhoto(checkSelectPhoto(data));//裁剪

//            cropRawPhoto(data.getData());
//            Uri data1 = data.getData();
//            String mSelectedImagesPath = UriUtil.getRealFilePath(this, data1);
//            ImageModel imageModel = new ImageModel();
//            imageModel.setUris(mSelectedImagesPath);
//
//            Logger.d("头像地址::" + imageModel.getUris());
//            avater = imageModel.getUris();

        } else if (requestCode == CODE_COVER_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {

            //选择封面

            if (data == null)
                return;

            Uri uri = data.getData();
            if (uri != null) {
                coverPic.setImageURI(uri.toString());
                Logger.d("uri路径::" + uri.toString() + "   ::uri.getPath():" + uri.getPath());
                startUploadPic(Constant.DIR_COVER, UriUtil.getRealFilePath(this, uri), uri, FileType.PIC_TYPE_COVER);
            }

//            mPrimaryPhoto.removeAll(mPrimaryPhoto);
//            List<Uri> mSelectedImages = Matisse.obtainResult(data);
//            if (mSelectedImages != null && mSelectedImages.size() > 0) {
//                List<String> mSelectedImagesPath = UriUtil.getImagePathes(this, mSelectedImages);
//                if (mSelectedImagesPath != null && mSelectedImagesPath.size() > 0) {
//                    List<ImageModel> mTempImageList = new ArrayList<>();
//                    for (String path : mSelectedImagesPath) {
//                        ImageModel imageModel = new ImageModel();
//                        imageModel.setUris(path);
//                        mTempImageList.add(imageModel);
//                    }
//                    mImageListt.remove(mClickedItemPosition);
//                    mImageListt.addAll(mTempImageList);
//                    if (mImageListt.size() < 1) {
//                        mImageListt.add(mAddImageModel);
//                    }
//
//                    mPrimaryPhoto.add(mImageListt.get(0).getUris());
//
//                    mMasterAdapter.setNewData(mImageListt);
//                    mMasterAdapter.notifyDataSetChanged();
//                }
//            }
        } else if (requestCode == CODE_RESULT_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            //裁剪后返回的数据
            if (data != null) {
                //加载,上传 头像
                setImageToHeadView(data);
            }
        }

    }

    //开始上传图片
//    private void startUploadPic(String filePath, final int type) {
    private void startUploadPic(final String pic_type, final String filePath, final Uri uri, final int type) {

        Logger.d("文件地址:::" + filePath);

        //oss签名信息
        if (oss != null && !TextUtils.isEmpty(bucket) && !TextUtils.isEmpty(endpoint)) {

            if (TextUtils.isEmpty(StringsUtils.getMd5Name(uri, this))) {
                ToastUtils.showShort("文件未找到");
                return;
            }

            // 构造上传请求
            switch (pic_type) {

                case Constant.DIR_COVER://封面

                    coverObjectKey = pic_type + StringsUtils.getMd5Name(uri, this);
                    putHead = new PutObjectRequest(bucket, coverObjectKey, filePath);

                    break;

                case Constant.DIR_HEADPIC://头像
                    headPicObjectKey = pic_type + StringsUtils.getMd5Name(uri, this);
                    putHead = new PutObjectRequest(bucket, headPicObjectKey, filePath);
                    break;

            }

            putHead.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                    Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                }
            });

            oss.asyncPutObject(putHead, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, final PutObjectResult result) {
//                    Log.d("PutObject", "UploadSuccess");
//                    Log.d("ETag", result.getETag());
//                    Log.d("RequestId", result.getRequestId());

//                    if (TextUtils.isEmpty(coverObjectKey)) {
//                        ToastUtils.showShort("文件不存在");
//                        return;
//                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String objectKey = "";
                            switch (pic_type) {

                                case Constant.DIR_COVER://封面
                                    objectKey = coverObjectKey;
                                    break;
                                case Constant.DIR_HEADPIC://头像
                                    objectKey = headPicObjectKey;
                                    break;
                            }

                            mPresenter.pathUrl(objectKey, type);
                        }
                    });
//                    Logger.d("body：：：" + result.getServerCallbackReturnBody());

                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    // 请求异常。
                    if (clientExcepion != null) {
                        // 本地异常，如网络异常等。
                        clientExcepion.printStackTrace();
                    }
                    if (serviceException != null) {
                        // 服务异常。
                        Log.e("ErrorCode", serviceException.getErrorCode());
//                        Log.e("RequestId", serviceException.getRequestId());
//                        Log.e("HostId", serviceException.getHostId());
//                        Log.e("RawMessage", serviceException.getRawMessage());
                    }
                }
            });


        } else {
            //TODO 为空时单独处理
        }

    }


    /**
     * 相册选择的图片
     *
     * @param data
     */
    public Uri checkSelectPhoto(Intent data) {

        if (data == null) {
            ToastUtils.showShort("图片读取失败");
            return null;
        }

        Uri selectedImage = data.getData();
        if (selectedImage == null) {
            ToastUtils.showShort("图片读取失败");
            return null;
        }

        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        if (cursor == null) {
            ToastUtils.showShort("图片读取失败");
            return null;
        }
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

//        Logger.d("picturePath==>" + picturePath);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return FileProvider.getUriForFile(this, Constant.FILE_PROVIDER_PATH, new File(picturePath));
        else
            return Uri.fromFile(new File(picturePath));

    }

    /**
     * 初始化第三方图片选择库Matisse
     */
    private void initMatisse() {

        Set<MimeType> mMimeType = new HashSet<>();
        mMimeType.add(MimeType.PNG);
        mMimeType.add(MimeType.JPEG);
        mMimeType.add(MimeType.MP4);
        mMatisseBuilder = Matisse.
                from(this).
                choose(mMimeType).
                capture(false).
                captureStrategy(new CaptureStrategy(true, Constant.FILE_PROVIDER_PATH)).
                countable(false).
                spanCount(3).
                imageEngine(new MatisseGlideEngine()).
                gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size)).
                restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).
                thumbnailScale(0.85f).
                theme(R.style.Matisse_Dracula);

        resetPhotoImagePicker(PHOTO_MAX_LIMIT_COUNT);
        resetPhotoImagePicker(MASTER_MAX_LIMIT_COUNT);

        resetPhotoImagePicker(VIDEO_MAX_LIMIT_COUNT);

    }

    /**
     * 重置Matisse的状态
     *
     * @param
     */
    private void resetPhotoImagePicker(int photolimitNum) {
        mMatisseBuilder.maxSelectable(photolimitNum);
    }

    private void resetMasterImagePicker(int masterlimitNum) {
        mMatisseBuilder.maxSelectable(masterlimitNum);
    }

    private void resetVideoImagePicker(int videolimitNum) {
        mMatisseBuilder.maxSelectable(videolimitNum);
    }

    private void initRecyclerView() {
        mImageList = new ArrayList<>();
        mAddImageModel = new ImageModel();
        mAddImageModel.setUris(ImageModel.ADD_IMAGE_URI);
        mImageList.add(mAddImageModel);
        mPhotoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPhotoAdapter = new MinePhotoAdapter(this, mImageList);
        mPhotoRecyclerView.setAdapter(mPhotoAdapter);

//        mImageListt = new ArrayList<>();
//        mAddImageModel = new ImageModel();
//        mAddImageModel.setUris(ImageModel.ADD_IMAGE_URI);
//        mImageListt.add(mAddImageModel);
//        mMasterGraphRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        mMasterAdapter = new MineMasterAdapter(this, mImageListt);
//        mMasterGraphRecyclerView.setAdapter(mMasterAdapter);

        mImageLists = new ArrayList<>();
        mAddImageModel = new ImageModel();
        mAddImageModel.setUris(ImageModel.ADD_IMAGE_URI);
        mImageLists.add(mAddImageModel);
        mVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mVideoAdapter = new MineVideoAdapter(this, mImageLists);
        mVideoRecyclerView.setAdapter(mVideoAdapter);

        mPhotoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mClickedItemPosition = position;
                returnImageview = 1;
                switch (adapter.getItemViewType(position)) {
                    case ImageModel.TYPE_IMAGE_ADD:
                        isAddItemClicked = true;
                        int imageCountsCanSelect = PHOTO_MAX_LIMIT_COUNT - position;
                        boolean isMultiMode = imageCountsCanSelect > 1;
                        resetPhotoImagePicker(imageCountsCanSelect);

                        requestPermissionAndSelectImage();
                        break;

                    case ImageModel.TYPE_IMAGE_SELECTED:

                        switch (view.getId()) {
                            case R.id.item_appeal_evidence_list_evidence_image:
                                isAddItemClicked = false;
                                resetPhotoImagePicker(1);
                                requestPermissionAndSelectImage();
                                break;

                            case R.id.item_appeal_evidence_list_delete_image:
                                adapter.getData().remove(position);
                                if (!adapter.getData().contains(mAddImageModel)) {
                                    adapter.getData().add(mAddImageModel);
                                }
                                adapter.notifyDataSetChanged();
                                break;
                        }

                        break;
                }

            }
        });

//        coverPic.
//        mMasterAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                mClickedItemPosition = position;
//                returnImageview = 2;
//                switch (adapter.getItemViewType(position)) {
//                    case ImageModel.TYPE_IMAGE_ADD:
//                        isAddItemClicked = true;
//                        int imageCountsCanSelect = MASTER_MAX_LIMIT_COUNT - position;
//                        boolean isMultiMode = imageCountsCanSelect > 1;
//                        resetMasterImagePicker(imageCountsCanSelect);
//
//                        requestPermissionAndSelectImage();
//                        break;
//
//                    case ImageModel.TYPE_IMAGE_SELECTED:
//
//                        switch (view.getId()) {
//                            case R.id.item_appeal_evidence_list_evidence_image:
////                                注释代码是点击替换图片的功能代码
//                                isAddItemClicked = false;
//                                resetMasterImagePicker(1);
//                                requestPermissionAndSelectImage();
//                                break;
//
//                            case R.id.item_appeal_evidence_list_delete_image:  //删除图片
//                                adapter.getData().remove(position);
//                                if (!adapter.getData().contains(mAddImageModel)) {
//                                    adapter.getData().add(mAddImageModel);
//                                }
//                                adapter.notifyDataSetChanged();
//                                break;
//                        }
//
//                        break;
//                }
//
//            }
//        });

        mVideoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mClickedItemPosition = position;
                returnImageview = 3;

                switch (adapter.getItemViewType(position)) {
                    case ImageModel.TYPE_IMAGE_ADD:
                        isAddItemClicked = true;
                        int imageCountsCanSelect = VIDEO_MAX_LIMIT_COUNT - position;
                        boolean isMultiMode = imageCountsCanSelect > 1;
                        resetVideoImagePicker(imageCountsCanSelect);

                        requestPermissionAndSelectImage();
                        break;

                    case ImageModel.TYPE_IMAGE_SELECTED:

                        switch (view.getId()) {
                            case R.id.item_appeal_evidence_list_evidence_image:
//                                注释代码是点击替换图片的功能代码
                                isAddItemClicked = false;
                                resetVideoImagePicker(1);
                                requestPermissionAndSelectImage();
                                break;

                            case R.id.item_appeal_evidence_list_delete_image:  //删除图片
                                adapter.getData().remove(position);
                                if (!adapter.getData().contains(mAddImageModel)) {
                                    adapter.getData().add(mAddImageModel);
                                }
                                adapter.notifyDataSetChanged();
                                break;
                        }

                        break;
                }

            }
        });
    }

    /**
     * 请求权限，选择照片
     */
    private void requestPermissionAndSelectImage() {
        AndPermission.
                with(this).
                permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA).
                rationale(new PermissionRationalDialog()).
                onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(MineDataActivity.this, permissions)) {
                            List<String> permissionNames = Permission.transformText(MineDataActivity.this, permissions);

                            final SettingService mSettingService = AndPermission.permissionSetting(MineDataActivity.this);
                            mPermissionSettingDialog = DialogWrapper.
                                    tipDialog().
                                    context(MineDataActivity.this).
                                    buttonType(DialogWrapper.BUTTON_DOUBLE).
                                    title("权限提示").
                                    message(PermissionTipUtils.getTipMessage(permissionNames)).
                                    leftButtonText("放弃").
                                    rightButtonText("立即开启").
                                    buttonClickListener(new DialogWrapper.TipTypeButtonClickListener() {
                                        @Override
                                        public void onLeftButtonClicked(TextView view) {

                                            if (mPermissionSettingDialog != null) {
                                                mPermissionSettingDialog.dismiss();
                                            }

                                            mSettingService.cancel();
                                        }

                                        @Override
                                        public void onSingleButtonClicked(TextView view) {

                                        }

                                        @Override
                                        public void onRightButtonClicked(TextView view) {
                                            if (mPermissionSettingDialog != null) {
                                                mPermissionSettingDialog.dismiss();
                                            }
                                            mSettingService.execute();
                                        }
                                    }).
                                    build();

                            mPermissionSettingDialog.show();
                        }
                    }
                }).
                onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (returnImageview == 1) {
                            mMatisseBuilder.forResult(10);

                        } else if (returnImageview == 2) {
                            mMatisseBuilder.forResult(200);

                        } else if (returnImageview == 3) {
                            mMatisseBuilder.forResult(300);

                        }

                    }
                }).
                start();

    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //不具有权限，需要进行权限申请
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x21);
        } else {
            Intent mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(mIntent, CODE_GALLERY_REQUEST);
        }

//        Intent intentFromGallery = new Intent();
//        // 设置文件类型
//        intentFromGallery.setType("image/*");
//        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        if (uri == null) {
            Log.d(TAG, "The uri is not exist.");
            return;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); // 输出的图片格式
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(outFile.getPath()));
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 取消人脸识别
        intent.putExtra("noFaceDetection", true);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);

//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//
//        intent.putExtra("crop", "true");
//
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//
//        intent.putExtra("outputX", output_X);
//        intent.putExtra("outputY", output_Y);
//        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Uri uri = intent.getData();

        if (uri == null)
            uri = Uri.fromFile(outFile);
        Logger.d("裁剪后的uri" + uri);
        if (uri != null) {
            Logger.d("uri.toString():::" + uri.toString());
            mHeadImageview.setImageURI(uri.toString());
            startUploadPic(Constant.DIR_HEADPIC, outFile.getPath(), uri, FileType.PIC_TYPE_HEAD);
//            startUploadPic(outFile.getPath(), FileType.PIC_TYPE_HEAD);
        }

//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            Bitmap photo = (Bitmap) extras.get("data");
////            mHeadImageview.setImageURI();
//            mHeadImageview.setImageBitmap(photo);
//        }
    }

}