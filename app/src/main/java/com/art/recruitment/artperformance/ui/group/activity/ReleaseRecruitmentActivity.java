package com.art.recruitment.artperformance.ui.group.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.bean.group.RecruitmentEditBean;
import com.art.recruitment.artperformance.bean.group.ReleaseRecruitmentRequest;
import com.art.recruitment.artperformance.bean.group.ReleaseRecruitmentbBean;
import com.art.recruitment.artperformance.bean.home.RecruitmentInforBean;
import com.art.recruitment.artperformance.bean.mine.MineFecruitmentBean;
import com.art.recruitment.artperformance.ui.group.contract.ReleaseRecruitmentContract;
import com.art.recruitment.artperformance.ui.group.presenter.ReleaseRecruitmentPresenter;
import com.art.recruitment.artperformance.ui.home.activity.CityActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineDataActivity;
import com.art.recruitment.artperformance.ui.mine.activity.MineFecruitmentActivity;
import com.art.recruitment.artperformance.utils.Constant;
import com.art.recruitment.artperformance.utils.DateFormatUtils;
import com.art.recruitment.artperformance.utils.StringsUtils;
import com.art.recruitment.artperformance.view.CustomDatePicker;
import com.art.recruitment.artperformance.view.DialogWrapper;
import com.art.recruitment.artperformance.view.Flowlayout;
import com.art.recruitment.common.base.callback.IToolbar;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.baserx.RxClickTransformer;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.utils.UIUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

/**
 * 发布招募
 */
public class ReleaseRecruitmentActivity extends BaseActivity<ReleaseRecruitmentPresenter> implements ReleaseRecruitmentContract {

    @BindView(R.id.release_title_edittext)
    EditText mTitleEdittext;
    @BindView(R.id.release_title_number_textviewv)
    TextView mTitleNumberTextviewv;
    @BindView(R.id.release_return_imageview)
    ImageView mReturnImageview;
    @BindView(R.id.release_head_portrait_imageview)
    CircleImageView mHeadPortraitImageview;
    @BindView(R.id.release_title_constraintLayout)
    ConstraintLayout mTitleConstraintLayout;
    @BindView(R.id.release_title_textview)
    TextView mTitleTextview;
    @BindView(R.id.release_number_textView)
    TextView mNumberTextView;
    @BindView(R.id.release_number_edittext)
    EditText mNumberEdittext;
    @BindView(R.id.release_wages_textview)
    TextView mWagesTextview;
    @BindView(R.id.release_wages_edittext)
    EditText mWagesEdittext;
    @BindView(R.id.release_face_textview)
    CheckBox mFaceTextview;
    @BindView(R.id.release_work_time_textview)
    TextView mWorkTimeTextview;
    @BindView(R.id.release_work_time_edittext)
    EditText mWorkTimeEdittext;
    @BindView(R.id.release_selection_time)
    TextView mSelectionTime;
    @BindView(R.id.release_selection_time_textview)
    TextView mSelectionTimeTextview;
    @BindView(R.id.release_detailed_location_textview)
    TextView mDetailedLocationTextview;
    @BindView(R.id.release_detailed_location_edittext)
    EditText mDetailedLocationEdittext;
    @BindView(R.id.release_deadline_for_registration_textview)
    TextView mDeadlineForRegistrationTextview;
    @BindView(R.id.release_other_textview)
    TextView mOtherTextview;
    @BindView(R.id.release_other_edittext)
    EditText mOtherEdittext;
    @BindView(R.id.release_other_number_textviewv)
    TextView mOtherNumberTextviewv;
    @BindView(R.id.release_label_flowlayout)
    Flowlayout mLabelFlowlayout;
    @BindView(R.id.release_gather_time_imageview)
    ImageView mGatherTimeImageview;
    @BindView(R.id.release_sing_up_time_imageview)
    ImageView mSingUpTimeImageview;
    @BindView(R.id.release_confirm_publication_textview)
    TextView mConfirmPublicationTextview;
    @BindView(R.id.release_detailed_city_textview)
    TextView mCityTextview;
    @BindView(R.id.release_detailed_city_imageview)
    ImageView mCityImageview;
    private ConstraintLayout addLabel;
    private Dialog dialog;
    private TextView mtitleEdittext;
    List<String> list = new ArrayList<String>();
    private CustomDatePicker mTimerPicker;
    private int count;
    private boolean mIsTitleOk = false;
    private boolean mIsNumberOk = false;
    private boolean mIsWagesOk = false;
    private boolean mIsWorkTimeOk = false;
    private boolean mIsDetailedLocationTimeOk = false;
    private int code;

    //标签列表
    List<String> labelsList = new ArrayList<>();
    private int release_id;
    private int pos;
    private MineFecruitmentBean.ContentBean data;
    private long startTime;//集合时间
    private long workEndTime;//报名截止时间

    @Override
    protected IToolbar getIToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_recruitment;
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

        release_id = getIntent().getIntExtra("release_id", 0);
        pos = getIntent().getIntExtra("pos", 0);
        data = getIntent().getParcelableExtra("id_id");
        String group_head = getIntent().getStringExtra("group_head");
        Glide.with(this).load(group_head).into(mHeadPortraitImageview);
        if (release_id == 1) {
            mPresenter.recuitDetail(data.getId());
        }

        addLabel = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.layout_add_label, null);

        initLayout(list);

        initEtittextLisnter();
        initButtonClick();
        initTimerPicker();

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
                clicks(mHeadPortraitImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(MineFecruitmentActivity.class);
                    }
                });

        RxView.
                clicks(mGatherTimeImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        count = 1;
                        mTimerPicker.show(System.currentTimeMillis());

                    }
                });

        RxView.
                clicks(mSelectionTimeTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        count = 1;
//                        mTimerPicker.show(mSelectionTimeTextview.getText().toString());
                        mTimerPicker.show(System.currentTimeMillis());
                    }
                });

        RxView.
                clicks(mSingUpTimeImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        count = 2;
//                        mTimerPicker.show(mDeadlineForRegistrationTextview.getText().toString());
                        mTimerPicker.show(System.currentTimeMillis());
                    }
                });

        RxView.
                clicks(mDeadlineForRegistrationTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        count = 2;
//                        mTimerPicker.show(mDeadlineForRegistrationTextview.getText().toString());
                        mTimerPicker.show(System.currentTimeMillis());
                    }
                });

        RxView.
                clicks(addLabel).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        addLabelDialog();
                    }
                });

        RxView.
                clicks(mFaceTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        mFaceTextview.setChecked(true);
                        if (mFaceTextview.isChecked())
                            mWagesEdittext.setText("");

                        setButtonStatus();

                    }
                });

        RxView.
                clicks(mConfirmPublicationTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        confirmPublication();
                    }
                });

        RxView.
                clicks(mCityImageview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivityForResult(CityActivity.class, 100);
                    }
                });
        RxView.
                clicks(mCityTextview).
                compose(RxClickTransformer.getClickTransformer()).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivityForResult(CityActivity.class, 100);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {
            String city=data.getExtras().getString("city");
            code = data.getExtras().getInt("code");
            mCityTextview.setText(city);
        }
    }

    //发布招募
    private void confirmPublication() {

        String mTitle = mTitleEdittext.getText().toString().trim();
        String mNumber = mNumberEdittext.getText().toString().trim();
        String mWages = mWagesEdittext.getText().toString().trim();
        String mWorkTime = mWorkTimeEdittext.getText().toString().trim();
        String mSelectionTime = mSelectionTimeTextview.getText().toString().trim();

        //集合时间
        if (startTime <= System.currentTimeMillis()) {
            ToastUtils.showShort("集合时间不能早于当前时间");
            return;
        }


//        if (workEndTime <= startTime) {
//            ToastUtils.showShort("报名截止时间需大于集合时间");
//            return;
//        }


        String mDetailedLocation = mDetailedLocationEdittext.getText().toString().trim();
        String mDeadlineForRegistration = mDeadlineForRegistrationTextview.getText().toString().trim();
        String mOther = mOtherEdittext.getText().toString().trim();

        ReleaseRecruitmentRequest recruitmentRequest = new ReleaseRecruitmentRequest();
        recruitmentRequest.setCityId(code);
        recruitmentRequest.setLabels(labelsList);
        recruitmentRequest.setTitle(mTitle);

        if (StringsUtils.is2Int(mNumber))
        recruitmentRequest.setRecruitNumber(Integer.parseInt(mNumber));
        if (StringsUtils.is2Int(mWages))
            recruitmentRequest.setSalary(Integer.parseInt(mWages));
        recruitmentRequest.setGatheringAddress(mDetailedLocation);
        recruitmentRequest.setApplyEndTime(mDeadlineForRegistration);
        recruitmentRequest.setGatheringTime(mSelectionTime);

        if (StringsUtils.is2Int(mWorkTime))
            recruitmentRequest.setWorkingHours(Integer.parseInt(mWorkTime));
        recruitmentRequest.setOtherRequirement(mOther);

        if (mFaceTextview.isClickable()) {
            recruitmentRequest.setSalaryType("2");
        } else {
            recruitmentRequest.setSalaryType("1");
        }
        Gson gson = new Gson();
        String codeStr = gson.toJson(recruitmentRequest);

        if (release_id == 1){
            //编辑招募信息
            mPresenter.recruitmentEdit(data.getId(), codeStr);
        } else {
            //发布招募
            mPresenter.releaseRecruitmen(codeStr);
        }

    }

    private void perfectInformation() {

        View inflate = View.inflate(this, R.layout.dialog_perfect_information, null);
        TextView mDetermineTextview = inflate.findViewById(R.id.release_perfect_determine_textview);
        dialog = DialogWrapper.
                customViewDialog().
                context(this).
                contentView(inflate).
                cancelable(true, true).
                build();

        dialog.show();

        mDetermineTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完善个人 资料
                Intent m = new Intent(ReleaseRecruitmentActivity.this, MineDataActivity.class);
                m.putExtra(Constant.EDITOR_TYPE, Constant.EDITOR_TYPE_RELEASE);
                startActivity(m);
            }
        });

    }

//    /**
//     * 面议按钮状态
//     */
//    public void faceColor(boolean tvScreenIsSelect) {
//        mFaceTextview.setTextColor(tvScreenIsSelect ? UIUtils.getColor(R.color.color_fd7b25) : UIUtils.getColor(R.color.color_b7bcbe));
//        mFaceTextview.setBackground(tvScreenIsSelect ? UIUtils.getDrawable(R.drawable.dialog_release_recruitment_face_enable) : UIUtils.getDrawable(R.drawable.dialog_release_recruitment_face_unenable));
//    }

    private void addLabelDialog() {

        View inflate = View.inflate(this, R.layout.dialog_release_add_label, null);
        mtitleEdittext = inflate.findViewById(R.id.release_label_title_edittext);
        ImageView mCleanImageView = inflate.findViewById(R.id.release_label_clean_imageview);
        TextView mDetermineTextview = inflate.findViewById(R.id.release_label_determine_textview);
        dialog = DialogWrapper.
                customViewDialog().
                context(this).
                contentView(inflate).
                cancelable(false, false).
                build();

        dialog.show();

        mCleanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        mDetermineTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label = mtitleEdittext.getText().toString().trim();

                if (!TextUtils.isEmpty(label)) {
                    list.add(label);
                    initLayout(list);
                    dialog.cancel();
                }

//                if (label != null && !label.equals("")) {
//                    list.add(label);
//                    initLayout(list);
//                    dialog.cancel();
//                }
            }
        });

    }

    /**
     * 按钮的点击状态
     */
    private void setButtonStatus() {

        if (mIsTitleOk && mIsNumberOk && (mIsWagesOk || mFaceTextview.isChecked())&& mIsWorkTimeOk && mIsDetailedLocationTimeOk) {
            mConfirmPublicationTextview.setEnabled(true);
        } else {
            mConfirmPublicationTextview.setEnabled(false);
        }
    }

    private void initEtittextLisnter() {
        mTitleEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitleNumberTextviewv.setText(s.length() + "/20");
                if (!TextUtils.isEmpty(s)) {
                    mIsTitleOk = true;
                } else {
                    mIsTitleOk = false;
                }
                setButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNumberEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
                    mIsNumberOk = true;
                } else {
                    mIsNumberOk = false;
                }
                setButtonStatus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mWagesEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
                    mIsWagesOk = true;
                    mFaceTextview.setChecked(false);
                } else {
                    mIsWagesOk = false;
                }
                setButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mWorkTimeEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mIsWorkTimeOk = true;
                } else {
                    mIsWorkTimeOk = false;
                }
                setButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDetailedLocationEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mIsDetailedLocationTimeOk = true;
                } else {
                    mIsDetailedLocationTimeOk = false;
                }
                setButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mOtherEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOtherNumberTextviewv.setText(s.length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initLayout(final List<String> arr) {

        mLabelFlowlayout.removeAllViewsInLayout();

        mLabelFlowlayout.addView(addLabel);

        /**
         * 创建 textView数组
         */
        final TextView[] textViews = new TextView[arr.size()];
        final ImageView[] icons = new ImageView[arr.size()];

        for (int i = 0; i < arr.size(); i++) {
            labelsList.add(arr.get(i));
            final View view = LayoutInflater.from(this).inflate(R.layout.text_view, mLabelFlowlayout, false);

            final TextView text = view.findViewById(R.id.release_label_text);
            final ImageView icon = view.findViewById(R.id.release_label_delete_imageview);

            text.setText(arr.get(i));
            textViews[i] = text;
            icons[i] = icon;

            icon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //遍历  图标  删除 当前  被点击项
                    for (int j = 0; j < icons.length; j++) {
                        if (icon.equals(icons[j])) {  //获取   当前  点击删除图标的位置：
                            mLabelFlowlayout.removeViewAt(j);
                            list.remove(j);
                            initLayout(list);
                        }
                    }
                }
            });

            mLabelFlowlayout.addView(view);
        }

    }

    private void initTimerPicker() {
        String beginTime = "2010.01.01 00:00";
        String endTime = "2090.12.30 00:00";
//        String beginTime = DateFormatUtils.getTodayDateTime(DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HM);
//        long currentTime = System.currentTimeMillis();

//        if (count == 1) {
//            mSelectionTimeTextview.setText(endTime);
//        } else if (count == 2) {
//            mDeadlineForRegistrationTextview.setText(endTime);
//        }

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                if (count == 1) {
                    startTime = timestamp;
                    mSelectionTimeTextview.setText(DateFormatUtils.long2Str(timestamp, true));

                } else if (count == 2) {
                    mDeadlineForRegistrationTextview.setText(DateFormatUtils.long2Str(timestamp, true));
                    workEndTime = timestamp;
                }
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);

//        mTimerPicker.setSelectedTime(System.currentTimeMillis(), true);
    }

    @Override
    public void returnReleaseRecruitmentBean(ReleaseRecruitmentbBean.DataBean bean) {
        ToastUtils.showShort("发布招募成功");
        finish();
    }

    @Override
    public void returnRecruitmentEdieBean(RecruitmentEditBean.DataBean bean) {
        ToastUtils.showShort("发布成功");
        finish();
    }

    @Override
    public void returnRecruitInforBean(RecruitmentInforBean.DataBean bean) {
        mTitleEdittext.setText(bean.getTitle());

        mNumberEdittext.setText(bean.getRecruitNumber() + "");
        if (bean.getSalaryType() == 2){
            mFaceTextview.setChecked(true);
        } else {
            mFaceTextview.setChecked(false);
        }
        mWagesEdittext.setText(bean.getSalary() + "");
        mWorkTimeEdittext.setText(bean.getWorkingHours() + "");
        mSelectionTimeTextview.setText(bean.getGatheringTime());
        mDetailedLocationEdittext.setText(bean.getGatheringAddress());
        mDeadlineForRegistrationTextview.setText(bean.getApplyEndTime());
        mOtherEdittext.setText(bean.getOtherRequirement());
        mCityTextview.setText(bean.getCityName());
        code = bean.getCityId();

        if (bean.getLabelList() != null) {
            initLayout(bean.getLabelList());
        }
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        if (message != null) {
            if (message.equals("请完善资料后再发布招募")) {
                perfectInformation();
            } else if (message.equals("请实名认证后再发布招募")) {
                initlRealName();
            } else {
                ToastUtils.showShort(message);
            }

        }
    }

    private void initlRealName(){
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
        mDialogTitle.setText("是否实名认证");
        mCleanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        mDetermineTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                startActivity(RealNameActivity.class);

            }
        });
    }

}