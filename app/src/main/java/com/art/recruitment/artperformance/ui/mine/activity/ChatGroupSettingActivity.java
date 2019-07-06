package com.art.recruitment.artperformance.ui.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.artperformance.utils.EventBusConstant;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragmentActivity;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * 群设置
 * Created by linaidao on 2019/7/6.
 */

public class ChatGroupSettingActivity extends BaseFragmentActivity {

    private String groupName;
    private String groupId;
    private ImageView ivback;
    private android.widget.TextView groupname;
    private android.widget.Button btndelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupName = getIntent().getStringExtra("groupName");
        groupId = getIntent().getStringExtra("groupId");


        setContentView(R.layout.activity_group_set);

        this.btndelete = (Button) findViewById(R.id.btn_delete);
        this.groupname = (TextView) findViewById(R.id.group_name);
        this.ivback = (ImageView) findViewById(R.id.iv_back);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        groupname.setText(groupName);

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exitGroup();

            }
        });
    }

    private void exitGroup() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //根据群组ID从本地获取群组基本信息
                    EMGroup group = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                    if (group != null) {
                        String owner = group.getOwner();//获取群主
                        Logger.d("群主::" + owner);

                        String hxuserid = SPUtils.getInstance().getString(BaseConfig.BaseSPKey.HX_USERNAME);

                        if (!TextUtils.isEmpty(owner) && owner.equals(hxuserid)) {

                            //自己是群主
                            EMClient.getInstance().groupManager().destroyGroup(groupId);//需异步处理
                            Logger.d("自己是群主，解散群");
                        } else {

                            EMClient.getInstance().groupManager().leaveGroup(groupId);//需异步处理
                            Logger.d("退出群");

                        }

                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort("退出成功");
                            EventBus.getDefault().post(EventBusConstant.ACTION_EXIT_GROUP);
                            finish();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
