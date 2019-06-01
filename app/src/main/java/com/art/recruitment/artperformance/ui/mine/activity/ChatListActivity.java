package com.art.recruitment.artperformance.ui.mine.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.art.recruitment.artperformance.R;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

public class ChatListActivity extends FragmentActivity {

    private EaseConversationListFragment conversationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        conversationFragment = new EaseConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_chat_lisr,conversationFragment).commit();
    }
}
