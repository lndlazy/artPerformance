package com.art.recruitment.artperformance.push.huawei;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.huawei.hms.support.api.push.PushReceiver;
import com.orhanobut.logger.Logger;

/**
 * Created by linaidao on 2019/7/15.
 */

public class HUAWEIPushRevicer extends PushReceiver {

//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//    }

    @Override
    public void onEvent(Context context, Event event, Bundle bundle) {
        super.onEvent(context, event, bundle);

        Logger.d("======onEvent======");

    }

    @Override
    public void onToken(Context context, String token, Bundle bundle) {
        super.onToken(context, token, bundle);
        String belongId = bundle.getString("belongId");
        String pushToken = token;
        Logger.d("======onToken====== belongId:" + belongId + ",pushToken:" + pushToken);

    }

    @Override
    public boolean onPushMsg(Context context, byte[] bytes, Bundle bundle) {

        Logger.d("======onPushMsg======");

        return super.onPushMsg(context, bytes, bundle);

    }

    @Override
    public void onPushMsg(Context context, byte[] bytes, String s) {
        super.onPushMsg(context, bytes, s);
        Logger.d("======onPushMsg======:" + s);

    }

    @Override
    public void onPushState(Context context, boolean b) {
        super.onPushState(context, b);
        Logger.d("======onPushState======");

    }

    @Override
    public void onToken(Context context, String s) {
        super.onToken(context, s);

        Logger.d("======onToken======:" + s);
    }
}
