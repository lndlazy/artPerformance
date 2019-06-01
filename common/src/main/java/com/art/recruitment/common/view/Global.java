package com.art.recruitment.common.view;

/**
 * description:全局常量类
 */

public class Global {
//-------------------------------------------app组------------------------------------------------------------
    /**
     * 前往WelcomeActivity
     */
    public static final String GO_WELCOME_ACTIVITY = "/app/WelcomeActivity";
    /**
     * 前往ManinActivity
     */
    public static final String GO_MAIN_ACTIVITY = "/app/MainActivity";
    /**
     * 前往用户设置实名认证的界面
     */
    public static final String GO_APP_USERINFO_ACTIVITY = "/app/UserInfoActivity";


//-------------------------------------------login组------------------------------------------------------------

    /**
     * 前往登录界面
     */
    public static final String GO_LOGIN_ACTIVITY = "/login/LoginActivity";

//-------------------------------------------userinfo组------------------------------------------------------------
    /**
     * 前往个人信息
     */
    public static final String GO_USER_INFO_ACTIVITY = "/userinfo/UserinfoActivity";

    /**
     * 文件提供路径
     */

//-------------------------------------------公共常量区域---------------------------------------------

    /**
     * 验证码位数
     */
    public static final int CODE_SIZE = 6;

    /**
     * 手机号位数
     */
    public static final int PHONE_NUMBER_SIZE = 11;

    /**
     * 身份证位数
     */
    public static final int ID_NUMBER_SIZE = 18;

    /**
     * 手机号位数
     */
    public static final int PASS_WORD_MAX_SIZE = 16;
    /**
     * 手机号位数
     */
    public static final int PASSWORD_MIN_SIZE = 6;

    /**
     * 登录的token   令牌
     */
    public static final String APP_TOKEN_KEY = "token";
    /**
     * 登录的temptoken  临时令牌 仅供注册登录时使用
     */
    public static final String APP_TEMP_TOKEN_KEY = "temptoken";
    /**
     * 登录的盐值
     */
    public static final String APP_SALT_KEY = "salt";

    /**
     * 设备id
     */
    public static final String DEVICE_ID = "deviceId";
    /**
     * uid
     */
    public static final String APP_UID_KEY = "user_uid";

    /**
     * 验证码类型：找回密码
     */
    public static final String SENDCODETOPHONE_CHANGEPASSWORD = "2";

    /**
     * 验证码类型：绑定手机号
     */
    public static final String SENDCODETOPHONE_PHONEBANGDING = "4";
    /**
     * 验证码类型：注册
     */
    public static final String SENDCODETOPHONE_REGISTER = "5";
    /**
     * 验证码类型：身份认证
     */
    public static final String SENDCODETOPHONE_IDAUTHENTICATION = "6";
    public static String NEWRANK = "newRank";

    public static String MONEY = "money";

    public static String MEMBERUPGRADE_BUSINESSTYPE = "upgrade";

    /**
     * nickName 昵称
     */
    public static String APP_NICKNAME_KEY = "nickName";
    /**
     * userNo
     */
    public static String APP_USERNO_KEY = "userNo";
    /**
     * AVATER 头像
     */
    public static String APP_AVATER_KEY = "avater";

}
