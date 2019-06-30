package com.art.recruitment.artperformance.ui.mine.activity;

import android.os.Bundle;
import android.view.View;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.base.ui.BaseFragmentActivity;
import com.blankj.utilcode.util.SPUtils;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.utils.SpKey;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.orhanobut.logger.Logger;

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

//                Logger.d("聊天内容::" + message.toString());

                if (message.getChatType() == EMMessage.ChatType.GroupChat) {
                    //群聊
//                    Logger.d("====这是群聊====");

                    if (EaseUserUtils.contactList != null && EaseUserUtils.contactList.containsKey(message.getTo())) {
                        EaseUser easeUser = EaseUserUtils.contactList.get(message.getTo());
                        if (easeUser != null) {
                            message.setAttribute(SpKey.GROUP_NAME, easeUser.getNickname());
//                            Logger.d("设置昵称::" + easeUser.getNickname());
                        }

                    }

//                    message.setAttribute(SpKey.GROUP_NAME, );

                } else {
//                    Logger.d("----这是单聊----");

                    //单聊
                    message.setAttribute(BaseConfig.BaseSPKey.USER_NAME,
                            SPUtils.getInstance().getString(BaseConfig.BaseSPKey.USER_NAME));
                    message.setAttribute(BaseConfig.BaseSPKey.HEAD_PIC_URL,
                            SPUtils.getInstance().getString(BaseConfig.BaseSPKey.HEAD_PIC_URL));
                }

            }

            @Override
            public void onEnterToChatDetails() {

                //进入群详情页面

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
        getSupportFragmentManager().beginTransaction().add(R.id.layout_chat, easeChatFragment).commit();

    }


}
