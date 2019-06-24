package com.art.recruitment.artperformance.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;

import com.art.recruitment.artperformance.R;
import com.art.recruitment.common.base.ui.BaseFragmentActivity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

public class ChatListActivity extends BaseFragmentActivity {

    private EaseConversationListFragment conversationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        conversationFragment = new EaseConversationListFragment();

        conversationFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {

                //进入聊天详情页面
                EMConversation.EMConversationType type = conversation.getType();

                int chat_type = -1;
                if (type == EMConversation.EMConversationType.GroupChat) {
                    //群聊
                    chat_type = EaseConstant.CHATTYPE_GROUP;
                } else if (type == EMConversation.EMConversationType.Chat) {
                    //单聊
                    chat_type = EaseConstant.CHATTYPE_SINGLE;
                }

                Intent chat = new Intent(ChatListActivity.this, ChatActivity.class);
                chat.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());  //对方账号
//                        chat.putExtra(EaseConstant.EXTRA_USER_NAME, mAdapter.getData().get(position).getApplyUserName());  //对方账号
                chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chat_type); //单聊模式
                startActivity(chat);

            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.layout_chat_lisr, conversationFragment).commit();
    }
}
