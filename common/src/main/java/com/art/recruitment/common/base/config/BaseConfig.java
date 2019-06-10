package com.art.recruitment.common.base.config;


public class BaseConfig {

    //RxBinding点击事件间隔
    public static final int BUTTON_CLICK_INTERVAL = 1000;

    public static final int SINGLE_ITEM_TYPE = 0;

    /**
     * 标记fragment tag
     */
    public static final String FRAGMENT_TAG_NAME = "fragmentTagName";

    /**
     * Fragment的参数：广告类别
     * 1-待录用，2-已录用
     */
    public static final String FRAGMENT_TAG_NAME_ADS_TYPE = "fragmentTagNameAdsType";

    /**
     * 默认每页加载数据条数
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final String STATUS_BAR_HEIGHT = "StatusBarHeight";

    public static class RebackConfig {

        /**
         * app进入后台时保存当时时间
         */
        public static final String APP_TO_BACKGROUND_TIME = "APP_TO_BACKGROUND_TIME";
    }

    /**
     * <p style color='#ff461f'>请注意是否需要在退出时或者初始化时置位如下字段!<p/>
     * SharePreference的key
     */
    public static class BaseSPKey {
        /**
         * 用户token
         */
        public static final String TOKEN = "token";

        public static final String NAME = "name";

        /**
         * 登录时间
         */
        public static final String LOGIN_TIME = "loginTime";


        /**
         * 用户ID
         */
        public static final String ID = "id";

        public static final String USER_NAME = "username";

        public static final String TELE_PHONE = "telephone";

        public static final String PHOTO = "photo";

        /**
         * 外网ip key值
         */
        public static final String EXTRANET_IP = "extranetIp";
    }
}
