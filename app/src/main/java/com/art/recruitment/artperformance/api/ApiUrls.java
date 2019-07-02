package com.art.recruitment.artperformance.api;

public class ApiUrls {

    //////////////////////////////登录模块///////////////////////////


    /**
     * 开机启动页
     */
    public static final String HOME_STARTUP = "home/startup";

    /**
     * 获取Token
     */
    public static final String TOKEN = "authentication/token";

    /**
     * 发送手机验证码
     */
    public static final String PHONE_VERIFICATION_CODE_SMS = "verificationCode/sms";

    /**
     * 注册账号
     */
    public static final String REGISTER_ACCOUNT = "register";

    /**
     * 重置密码
     */
    public static final String RESET_PASSWORD = "accounts/password";

    //////////////////////////////首页模块///////////////////////////

    /**
     * 获取城市列表
     */
    public static final String CITI_LIST = "cities";

    /**
     * 搜索城市
     */
    public static final String CITI_SEARCH = "cities/search";

    /**
     * banner图
     */
    public static final String HOME_BANNER = "home/banner";

    /**
     * banner图详情
     */
    public static final String BANNER_DETAIL = "home/banner/{id}";

    /**
     * 信息广场-招募列表
     */
    public static final String RECRUIT_LIST = "recruitment";

    /**
     * 信息广场-招募详情
     */
    public static final String RECRUIT_DETAIL = "recruitment/{recruitmentId}";

    //////////////////////////////发布模块///////////////////////////

    /**
     * 实名认证
     */
    public static final String AUTHENTICATION_REALNAME = "authentication/realName";

    /**
     * 发布招募
     */
    public static final String RECRUITMENT_PUBLISH = "recruitment/publish";

    /**
     * 群演列表获取
     */
    public static final String ACTORS = "actors";

    /**
     * 群演详情
     */
    public static final String ACTORS_DETAIL = "actors/{actorId}";

    /**
     * 获取当前认证状态
     */
    public static final String STATUS = "actors/status/my";

    /**
     * 点赞
     */
    public static final String ACTOR_LIKES = "actors/{actorId}/likes";

    /**
     * 报名列表
     */
    public static final String APPLY_LIST = "recruitment/{recruitmentId}/apply";

    /**
     * 根据群演Id获取用户信息
     */
    public static final String ACTORID = "im/{actorId}";

    /**
     * 群聊
     */
    public static final String CHAT_GROUPS = "im/chatgroups/{recruitmentId}";

    //////////////////////////////动态圈模块///////////////////////////
    /**
     * 动态圈列表
     */
    public static final String DYNAMIC_LIST = "dynamiccircle";

    /**
     * 动态圈点赞
     */
    public static final String DYNAMIC_LIKES = "dynamiccircle/{dynamicCircleId}/likes";

    /**
     * 动态圈详情
     */
    public static final String DYNAMIC_DETAIL = "dynamiccircle/{dynamicCircleId}";

    /**
     * 动态圈详情评论
     */
    public static final String DYNAMIC_DETAIL_COMMENTS = "dynamiccircle/{dynamicCircleId}/comments";

    /**
     * 我的动态圈列表
     */
    public static final String MY_DYNAMMIC_LIST = "dynamiccircle/my";

    //////////////////////////////我的模块///////////////////////////

    /**
     * 获取个人资料
     */
    public static final String ACCOUNTS_MY = "accounts/my";

    /**
     * OSS转换文件地址
     */
    public static final String PATH_URL = "oss/convertPath2Url";

    /**
     * 完善基本信息
     */
    public static final String ACCOUNTS_CONSUMMATE = "accounts/consummate";

    /**
     * 完善3项基本信息
     */
    public static final String ACCOUNTS_CONSUMMATE3 = "accounts/consummate3";

    /**
     * OSS签名
     */
    public static final String SIGNATURE = "oss/signature";

    /**
     * 签名信息
     */
    public static final String OSS = "oss";

    /**
     * 我的报名列表
     */
    public static final String RECRUITMENT_APPLY_MY = "recruitment/apply/my";

    /**
     * 取消报名
     */
    public static final String CANCEL_RECRUITMENT = "recruitment/{recruitmentId}/apply/{applyId}/cancel";

    /**
     * 我的招募
     */
    public static final String RECRUITMENT_MY = "recruitment/my";

    /**
     * 上下架
     */
    public static final String RECRUITMENT_OPT = "recruitment/{recruitmentId}/frontend/{opt}";

    /**
     * 取消录用
     */
    public static final String CANCEL_HIRED = "recruitment/{recruitmentId}/hired/{applyId}/cancel";

    /**
     * 录用
     */
    public static final String HIRING = "recruitment/{recruitmentId}/apply/{applyId}/hiring";

    /**
     * 我的动态圈列表
     */
    public static final String MINE_DYNAMIC_LIST = "dynamiccircle/my";

    /**
     * 意见反馈
     */
    public static final String FEEDBACK = "feedback";

    //////////////////////////////环信///////////////////////////

    /**
     * 获取环信用户信息
     */
    public static final String IM_USER = "im/user";


    /**
     * 第三方登录
     */
    public static final String AUTHENTICATION_LOGIN = "authentication/token/social/{socialType}";


    /**
     * 第三方账号绑定
     */
    public static final String AUTHENTICATION_BIND = "/authentication/token/social/{socialType}/bind";


    //图标logo
    public static final String LOGO_ICON = "/home/logo";


    //我的 分享
    public static final String SHARE_APP = "home/share/app";


}
