package com.art.recruitment.artperformance.ui.mine.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragmentActivity;
import com.blankj.utilcode.util.SPUtils;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

public class ChatActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        EaseChatFragment easeChatFragment = new EaseChatFragment();  //环信聊天界面
        easeChatFragment.setArguments(getIntent().getExtras()); //需要的参数
        easeChatFragment.setChatFragmentHelper(new EaseChatFragment.EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {

                message.setAttribute(BaseConfig.BaseSPKey.USER_NAME,
                        SPUtils.getInstance().getString(BaseConfig.BaseSPKey.USER_NAME));
                message.setAttribute(BaseConfig.BaseSPKey.HEAD_PIC_URL,
                        SPUtils.getInstance().getString(BaseConfig.BaseSPKey.HEAD_PIC_URL));
            }

            @Override
            public void onEnterToChatDetails() {

            }

            @Override
            public void onAvatarClick(String username) {

            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.layout_chat,easeChatFragment).commit();

    }


}
