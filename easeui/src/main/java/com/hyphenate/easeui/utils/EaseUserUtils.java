package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }


    public static Map<String, EaseUser> contactList = new LinkedHashMap();

    /**
     * get EaseUser according username
     *
     * @param hxId
     * @return
     */
    public static EaseUser getUserInfo(String hxId) {
//        if(userProvider != null)
//            return userProvider.getUser(hxId);
//
//        return null;

        if (hxId.equals(EMClient.getInstance().getCurrentUser())) {
            EaseUser currentUserInfo = EaseCommonUtils.getCurrentUserInfo(hxId);
            return currentUserInfo;
        }

        EaseUser easeUser;
        if (contactList != null && contactList.containsKey(hxId)) {

        } else { // 如果内存中没有，则将本地数据库中的取出到内存中。
            //TODO
//            getContactList();
        }
        // // TODO 获取不在好友列表里的群成员具体信息，即陌生人信息，demo未实现
        // if (user == null && getRobotList() != null) {
        // user = getRobotList().get(hxId);
        // }
        easeUser = contactList.get(hxId);
        if (easeUser == null) {
            easeUser = new EaseUser(hxId);
        } else {
            if (TextUtils.isEmpty(easeUser.getNickname())) { // 如果名字为空，则显示环信号码
                easeUser.setNickname(easeUser.getUsername());
            }
        }
        return easeUser;


    }

    /**
     * set user avatar
     *
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView) {
        EaseUser user = getUserInfo(username);
        if (user != null && user.getAvatar() != null) {
            try {
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                RequestOptions options = new RequestOptions();
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
                options.placeholder(R.drawable.ease_default_avatar);
                Glide.with(context).load(user.getAvatar()).apply(options).into(imageView);
            }
        } else {
            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        }
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user != null && user.getNickname() != null) {
                textView.setText(user.getNickname());
            } else {
                textView.setText(username);
            }
        }
    }

}
