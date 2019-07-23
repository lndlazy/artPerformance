package com.art.recruitment.artperformance.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Process;
import android.support.multidex.MultiDex;

import com.alibaba.sdk.android.oss.common.OSSLog;
import com.art.recruitment.artperformance.utils.ResourceUtils;
import com.art.recruitment.artperformance.utils.StringsUtils;
import com.art.recruitment.common.base.BaseApplication;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.config.ApiConfig;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.android.hms.agent.HMSAgent;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.utils.SpKey;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;
import com.hyphenate.util.EMLog;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//import com.alibaba.wireless.security.jaq.JAQException;
//import com.alibaba.wireless.security.jaq.SecurityInit;

/**
 * APPLICATION
 */
public class MyApplication extends BaseApplication {

    static final boolean isDebug = true;

    //文件夹目录
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/art-debug/crash_log/";
    //文件名
    private static final String FILE_NAME = "crash";
    //文件名后缀
    private static final String FILE_NAME_SUFFIX = ".txt";
    private int mChatMessageCallBackType; //1 : 普通交易对应的ChatActivity  2：大宗交易对应的LargeAmountChatActivity
    private boolean isInit;
    private MyApplication appContext;
    private static final String TAG = "ArtPerformance";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //设置异常捕获
//        Thread.setDefaultUncaughtExceptionHandler(this);
//        //排除InputMethodManager泄漏检测，貌似是SDK泄漏，有时间再检测
//        ExcludedRefs excludedRefs= AndroidExcludedRefs.createAppDefaults().
//                instanceField("android.view.inputmethod.InputMethodManager","sInstance").
//                instanceField("android.view.inputmethod.InputMethodManager","mLastSrvView").
//                instanceField("com.android.internal.policy.PhoneWindow$DecorView", "mContext").
//                build();
//        LeakCanary.refWatcher(this).listenerServiceClass(DisplayLeakService.class).excludedRefs(excludedRefs).buildAndInstall();

        ResourceUtils.init(this);

        initLogger(TAG, isDebug);
//        OSSLog.enableLog();//开启阿里云存储log
        initUM();

        initApiConfig();

//        initJPush();
        Fresco.initialize(this);
        initEaseMob();

//        Logger.d("SHA1值:::" + sHA1(this));
        //小米
        xiaomiPush();
        //华为
        if (StringsUtils.isHuawei(this))
            HMSAgent.init(this);

        //魅族
        if (MzSystemUtils.isBrandMeizu()) {
            PushManager.register(this, "122193", "3f69382260bf46fbb9c59f0696278caf");
        }


    }

    private void xiaomiPush() {

        //初始化push推送服务
//        if(shouldInit()) {
        MiPushClient.registerPush(this, "2882303761518061472", "5741806120472");
//        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
                Logger.d("setTag::" + tag);
            }

            @Override
            public void log(String content, Throwable t) {
                Logger.d("loglog====::" + content, t);
            }

            @Override
            public void log(String content) {
                Logger.d("=====log::::" + content);
            }
        };
        com.xiaomi.mipush.sdk.Logger.setLogger(this, newLogger);


    }

    public String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void initUM() {
        UMShareAPI.get(this);//初始化sdk
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = isDebug;
    }

    //各个平台的配置
    {
        //微信
        PlatformConfig.setWeixin("wx133b5a31ac26c802", "2f7181d2615fd54af5034b9e16152321");
        //QQ
        PlatformConfig.setQQZone("1108808045", "x7QBGpxSYpMH2xCr");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    /**
     * 初始化环信
     */
    private void initEaseMob() {
        appContext = this;

        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);

        EMPushConfig.Builder builder = new EMPushConfig.Builder(this);
        builder
//                .enableVivoPush() // 推送证书相关信息配置在AndroidManifest.xml中
                .enableMeiZuPush("122193", "3f69382260bf46fbb9c59f0696278caf")
                .enableMiPush("2882303761518061472", "5741806120472")
//                .enableOppoPush(String appKey, String appSecret)
                .enableHWPush(); //开发者需要调用该方法来开启华为推送
//                .enableFCM(String senderId); //开发者需要调用该方法来开启FCM推送

        options.setPushConfig(builder.build());
        EaseUI.getInstance().init(this, options);
        // 设置开启debug模式
//        EMClient.getInstance().setDebugMode(isDebug);

        EMPushHelper.getInstance().setPushListener(new PushListener() {
            @Override
            public void onError(EMPushType pushType, long errorCode) {
                EMLog.e("PushClient", "Push client occur a error: " + pushType + " - " + errorCode);
                Logger.d("====onError===");

// TODO: 开发者会在这个回调中收到使用推送的相关错误信息，各推送类型的error code开发者可以自己去各推送平台官网查询错误原因。
            }

            @Override
            public boolean isSupportPush(EMPushType pushType, EMPushConfig pushConfig) {
                Logger.d("====isSupportPush===");
                return super.isSupportPush(pushType, pushConfig);
// TODO：开发者可以复写该方法控制设备是否支持某推送的判断。
            }
        });

        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<HashMap<String, EaseUser>>() {
        }.getType();
        Map<String, EaseUser> objectMap = gson.fromJson(SPUtils.getInstance().getString(SpKey.CONTACT_LIST), type);
        if (objectMap != null && objectMap.size() > 0)
            EaseUserUtils.contactList.putAll(objectMap);

//        int pid = android.os.Process.myPid();
//        String processAppName = getAppName(pid);
//
//        if (processAppName == null ||!processAppName.equalsIgnoreCase(appContext.getPackageName())) {
//            Log.e(TAG, "enter the service process!");
//
//            // 则此application::onCreate 是被service 调用的，直接返回
//            return;
//        }
//
//        if(isInit){
//            return ;
//        }
//
//        EMOptions options = new EMOptions();
//        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
//        // options.setAppKey("lzan13#hxsdkdemo");
//        // 设置自动登录
//        options.setAutoLogin(true);
//        // 设置是否需要发送已读回执
//        options.setRequireAck(true);
//        // 设置是否需要发送回执，TODO 这个暂时有bug，上层收不到发送回执
//        options.setRequireDeliveryAck(true);
//        // 设置是否需要服务器收到消息确认
//        options.setAutoTransferMessageAttachments(true);
//        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
//        options.setAcceptInvitationAlways(false);
//        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
//        options.setAutoAcceptGroupInvitation(false);
//        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
//        options.setDeleteMessagesAsExitGroup(false);
//        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
//        options.allowChatroomOwnerLeave(true);
//
//        // 调用初始化方法初始化sdk
//        EaseUI.getInstance().init(this, options);
//
//        // 设置开启debug模式
//        EMClient.getInstance().setDebugMode(true);
//
//        // 设置初始化已经完成
//        isInit = true;

    }

    private void initApiConfig() {
        ApiConfig mApiConfig = new ApiConfig();
//        mApiConfig.setHostServer(BuildConfig.HOST_SERVER);
//        mApiConfig.setShopHostServer(BuildConfig.SHOP_HOST_SERVER);
//        mApiConfig.setShopH5HostServerr(BuildConfig.SHOP_HOST_H5_SERVER);
//        mApiConfig.setmAssetsWallet2HostServer(BuildConfig.ASSETS_HOST_WALLET2_SERVER);
//        mApiConfig.setReadTimeOut(BuildConfig.READ_TIME_OUT);
//        mApiConfig.setConnectTimeOut(BuildConfig.CONNECT_TIME_OUT);
        Api.setConfig(mApiConfig);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /**
     * 初始化极光推送
     */
   /* private void initJPush() {
        JPushInterface.setDebugMode(BuildConfig.IS_DEBUG);
        JPushInterface.init(this);
    }*/

    /**
     * 判定是否需要处理小红点
     * 说明：1.5.2版本已经添加了小红点处理逻辑的扩展字段  但是App端未处理小红点逻辑，App端1.6版本才开始上小红点，不做版本判断
     * ，1.5.2被1.6版本覆盖安装，则小红点显示会出现异常的情况，订单列表没红点
     *
     * @return true：需要处理小红点   false：不需要处理小红点
     */
    private boolean needHandleRedDot() {

        String mAppVersion = AppUtils.getAppVersionName();
        String mFixedVersion = "1.5.2"; //该版本服务端上了小红点逻辑需要的扩展字段，但是App端未处理

        String[] mAppVersionArray = mAppVersion.split("\\.");
        String[] mFixedVersionArray = mFixedVersion.split("\\.");
        boolean needCheckNext = false;
        boolean needHandle = false;

        if (Integer.parseInt(mAppVersionArray[0]) > Integer.parseInt(mFixedVersionArray[0])) {
            needCheckNext = false; //第一位发现新版本，不再检查下一位
            needHandle = true; //标志有新版本
        } else if (Integer.parseInt(mAppVersionArray[0]) == Integer.parseInt(mFixedVersionArray[0])) {
            needCheckNext = true; //第一位相等，需要检查下一位
            needHandle = false; //标志没有新版本
        } else {
            needCheckNext = false; //手机第一位比服务器都高，不再检查以后位数
            needHandle = false; //标志没有新版本
        }

        if (needCheckNext) {
            if (Integer.parseInt(mAppVersionArray[1]) > Integer.parseInt(mFixedVersionArray[1])) {
                needCheckNext = false;
                needHandle = true;
            } else if (Integer.parseInt(mAppVersionArray[1]) == Integer.parseInt(mFixedVersionArray[1])) {
                needCheckNext = true;
                needHandle = false;
            } else {
                needCheckNext = false;
                needHandle = false;
            }
        }

        if (needCheckNext) {
            if (Integer.parseInt(mAppVersionArray[2]) > Integer.parseInt(mFixedVersionArray[2])) {
                needHandle = true;
            } else if (Integer.parseInt(mAppVersionArray[2]) == Integer.parseInt(mFixedVersionArray[2])) {
                needHandle = false;
            } else {
                needHandle = false;
            }
        }

        return needHandle;
    }


    private void initLogger(String tag, final boolean isShow) {
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .tag(tag) // 全局tag
//                .build();

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // 是否显示线程信息，默认 显示
                .methodCount(2)         // 方法栈打印的个数，默认是 2
                .methodOffset(5)        // 设置调用堆栈的函数偏移值，默认是 5
//                .logStrategy(customLog) // 设置log打印策略，默认是 LogCat
                .tag(tag)   // 设置全局TAG，默认是 PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
//                return BuildConfig.DEBUG;
                return isShow;
            }
        });

//        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
//        Logger.addLogAdapter(new DiskLogAdapter());
    }


    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
//        getApplicationInfo().processName;
        String mainProcessName = getApplicationInfo().processName;
//        Process.myPid()
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
