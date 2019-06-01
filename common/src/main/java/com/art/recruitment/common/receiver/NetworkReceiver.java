package com.art.recruitment.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.art.recruitment.common.base.callback.NoNetworkCallback;
import com.art.recruitment.common.utils.NetWorkUtils;

/**
 * @Desc: 网络状态广播receiver
 */

public class NetworkReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NoNetworkCallback mCallback = (NoNetworkCallback) context;

        if (!NetWorkUtils.isNetConnected(context)){
            mCallback.showNoNetworkTipView();
        }else {
            mCallback.hideNoNetworkTipView();
        }
    }
}
