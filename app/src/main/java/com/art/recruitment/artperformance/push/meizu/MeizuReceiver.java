package com.art.recruitment.artperformance.push.meizu;

import android.content.Context;

import com.art.recruitment.artperformance.R;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.orhanobut.logger.Logger;

/**
 * Created by linaidao on 2019/7/17.
 */

public class MeizuReceiver extends MzPushMessageReceiver {

    @Override
    @Deprecated
    public void onRegister(Context context, String pushid) {
        //调用 PushManager.register(context）方法后，会在此回调注册状态
        //应用在接受返回的 pushid
        Logger.d("魅族pushid::" + pushid);
    }

    @Override
    public void onMessage(Context context, String s) {
        //接收服务器推送的透传消息
    }

    @Override
    @Deprecated
    public void onUnRegister(Context context, boolean b) {
        //调用 PushManager.unRegister(context）方法后，会在此回调反注册状态
    }

    //设置通知栏小图标
    @Override
    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
        super.onUpdateNotificationBuilder(pushNotificationBuilder);

        pushNotificationBuilder.setmStatusbarIcon(R.mipmap.app_icon);
        //重要,详情参考应用小图标自定设置
    }


    //    //设置通知栏小图标
//    @Override
//    public PushNotificationBuilder onUpdateNotificationBuilder(PushNotificationBuilder
//                                        pushNotificationBuilder) {
//        //重要,详情参考应用小图标自定设置
//
//        pushNotificationBuilder.setmStatusbarIcon(R.drawable.mz_push_notification_small_icon);
//    }

    @Override
    public void onPushStatus(Context context,PushSwitchStatus
            pushSwitchStatus) {
        //检查通知栏和透传消息开关状态回调
    }

    @Override
    public void onRegisterStatus(Context context,RegisterStatus
            registerStatus) {
        //调用新版订阅 PushManager.register(context,appId,appKey)回调
    }

    @Override
    public void onUnRegisterStatus(Context context,UnRegisterStatus
            unRegisterStatus) {
        //新版反订阅回调
    }

    @Override
    public void onSubTagsStatus(Context context,SubTagsStatus
            subTagsStatus) {
        //标签回调
    }

    @Override
    public void onSubAliasStatus(Context context,SubAliasStatus
            subAliasStatus) {
        //别名回调
    }
    @Override
    public void onNotificationArrived(Context context, MzPushMessage
            mzPushMessage) {
        //通知栏消息到达回调，flyme6 基于 android6.0 以上不再回调
    }

    @Override
    public void onNotificationClicked(Context context, MzPushMessage
            mzPushMessage) {
        //通知栏消息点击回调
    }

    @Override
    public void onNotificationDeleted(Context context, MzPushMessage
            mzPushMessage) {
        //通知栏消息删除回调；flyme6 基于 android6.0 以上不再回调
    }

}
