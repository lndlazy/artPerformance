package com.hyphenate.easeui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.utils.SpKey;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EasyUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class EaseUI {
    private static final String TAG = EaseUI.class.getSimpleName();

    /**
     * the global EaseUI instance
     */
    private static EaseUI instance = null;

    /**
     * user profile provider
     */
    private EaseUserProfileProvider userProvider;

    private EaseSettingsProvider settingsProvider;

    private EaseAvatarOptions avatarOptions;

    /**
     * application context
     */
    private Context appContext = null;

    /**
     * init flag: test if the sdk has been inited before, we don't need to init again
     */
    private boolean sdkInited = false;

    /**
     * the notifier
     */
    private EaseNotifier notifier = null;

    /**
     * save foreground Activity which registered eventlistener
     */
    private List<Activity> activityList = new ArrayList<Activity>();

    public void pushActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(0, activity);
        }
    }

    public void popActivity(Activity activity) {
        activityList.remove(activity);
    }

    public Activity getTopActivity() {
        return activityList.get(0);
    }

    private EaseUI() {
    }

    /**
     * get instance of EaseUI
     *
     * @return
     */
    public synchronized static EaseUI getInstance() {
        if (instance == null) {
            instance = new EaseUI();
        }
        return instance;
    }

    /**
     * this function will initialize the SDK and easeUI kit
     *
     * @param context
     * @param options use default if options is null
     * @return
     */
    public synchronized boolean init(Context context, EMOptions options) {
        if (sdkInited) {
            return true;
        }
        appContext = context;


        // if there is application has remote service, application:onCreate() maybe called twice
        // this check is to make sure SDK will initialized only once
        // return if process name is not application's name since the package name is the default process name
        if (!isMainProcess(appContext)) {
            Log.e(TAG, "enter the service process!");
            return false;
        }
        if (options == null) {
            EMClient.getInstance().init(context, initChatOptions());
        } else {
            EMClient.getInstance().init(context, options);
        }

        initNotifier();
        registerMessageListener();

        if (settingsProvider == null) {
            settingsProvider = new DefaultSettingsProvider();
        }

        sdkInited = true;
        return true;
    }


    protected EMOptions initChatOptions() {
        Log.d(TAG, "init HuanXin Options");

        EMOptions options = new EMOptions();
        // change to need confirm contact invitation
        options.setAcceptInvitationAlways(false);
        // set if need read ack
        options.setRequireAck(true);
        // set if need delivery ack
        options.setRequireDeliveryAck(false);

        return options;
    }

    void initNotifier() {
        notifier = new EaseNotifier(appContext);
    }

    private void registerMessageListener() {
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {

                for (EMMessage msg : messages) {

                    try {

                        getNotifier().vibrateAndPlayTone(msg);
//                        getNotifier().setNotificationInfoProvider();
                        getNotifier().notify(msg);
                        Log.e("TAG", "   0000 EaseUI  接受到了消息  :::" + msg.toString());

                        if (msg.getChatType() == EMMessage.ChatType.GroupChat) {
                            //群聊

                            Log.e("TAG", "群聊消息::" + msg.toString());

                            EaseUser groupUser = new EaseUser(msg.getTo());
                            String groupName = msg.getStringAttribute(SpKey.GROUP_NAME);
                            groupUser.setNickname(groupName);
                            if (EaseUserUtils.contactList != null) {
                                EaseUserUtils.contactList.put(msg.getTo(), groupUser);
                                EaseUserUtils.save2sp();
                            }

                        } else {
                            String username = msg.getStringAttribute(SpKey.USER_NAME);
                            String picUrl = msg.getStringAttribute(SpKey.HEAD_PIC_URL);
                            //单聊
                            EaseUser easeUser = new EaseUser(msg.getFrom());
                            easeUser.setNickname(username);
                            easeUser.setAvatar(picUrl);

                            if (EaseUserUtils.contactList != null) {
                                EaseUserUtils.contactList.put(msg.getFrom(), easeUser);
                                EaseUserUtils.save2sp();
                            }
                        }

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }

                }

                EaseAtMessageHelper.get().parseMessages(messages);
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> messages) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    // To handle group-ack msg.
                    EaseDingMessageHelper.get().handleAckMessage(message);
                }
            }
        });
    }

    public EaseNotifier getNotifier() {
        return notifier;
    }

    public boolean hasForegroundActivies() {
        return activityList.size() != 0;
    }


    public void setAvatarOptions(EaseAvatarOptions avatarOptions) {
        this.avatarOptions = avatarOptions;
    }

    public EaseAvatarOptions getAvatarOptions() {
        return avatarOptions;
    }


    /**
     * set user profile provider
     *
     * @param
     */
    public void setUserProfileProvider(EaseUserProfileProvider userProvider) {
        this.userProvider = userProvider;
    }

    /**
     * get user profile provider
     *
     * @return
     */
    public EaseUserProfileProvider getUserProfileProvider() {
        return userProvider;
    }

    public void setSettingsProvider(EaseSettingsProvider settingsProvider) {
        this.settingsProvider = settingsProvider;
    }

    public EaseSettingsProvider getSettingsProvider() {
        return settingsProvider;
    }

    public boolean isMainProcess(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return context.getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }

    /**
     * User profile provider
     *
     * @author wei
     */
    public interface EaseUserProfileProvider {
        /**
         * return EaseUser for input username
         *
         * @param username
         * @return
         */
        EaseUser getUser(String username);
    }

    /**
     * Emojicon provider
     */
    public interface EaseEmojiconInfoProvider {
        /**
         * return EaseEmojicon for input emojiconIdentityCode
         *
         * @param emojiconIdentityCode
         * @return
         */
        EaseEmojicon getEmojiconInfo(String emojiconIdentityCode);

        /**
         * get Emojicon map, key is the text of emoji, value is the resource id or local path of emoji icon(can't be URL on internet)
         *
         * @return
         */
        Map<String, Object> getTextEmojiconMapping();
    }

    private EaseEmojiconInfoProvider emojiconInfoProvider;

    /**
     * Emojicon provider
     *
     * @return
     */
    public EaseEmojiconInfoProvider getEmojiconInfoProvider() {
        return emojiconInfoProvider;
    }

    /**
     * set Emojicon provider
     *
     * @param emojiconInfoProvider
     */
    public void setEmojiconInfoProvider(EaseEmojiconInfoProvider emojiconInfoProvider) {
        this.emojiconInfoProvider = emojiconInfoProvider;
    }

    /**
     * new message options provider
     */
    public interface EaseSettingsProvider {
        boolean isMsgNotifyAllowed(EMMessage message);

        boolean isMsgSoundAllowed(EMMessage message);

        boolean isMsgVibrateAllowed(EMMessage message);

        boolean isSpeakerOpened();
    }

    /**
     * default settings provider
     */
    protected class DefaultSettingsProvider implements EaseSettingsProvider {

        @Override
        public boolean isMsgNotifyAllowed(EMMessage message) {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public boolean isMsgSoundAllowed(EMMessage message) {
            return true;
        }

        @Override
        public boolean isMsgVibrateAllowed(EMMessage message) {
            return true;
        }

        @Override
        public boolean isSpeakerOpened() {
            return true;
        }
    }

    public Context getContext() {
        return appContext;
    }
}
