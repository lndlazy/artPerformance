package com.art.recruitment.common.http.error;

/**
 * 网络请求错误码
 */

public class ErrorCode {

    //未知错误
    public static final int CODE_UNKNOWN=10000;

    //解析错误
    public static final int CODE_PARSE=10001;

    //网络连接错误
    public static final int CODE_NETWORK=10002;

    /**
     * 警告：该错误码并未在RxSubscriber中错误统一封装处理中进行处理，设定该值只是用于在_onError()方法中方便统一处理空界面或错误界面的视图类型。调用显示空界面或错误界面视图的方法传递ErrorCode即可
     */

    //接口定义错误码
    public static final int CODE_SERVER_200=200;

    public static final int CODE_SERVER_SUCCESS = CODE_SERVER_200;  //接口返回码为200时视为成功

    //http 错误码
    public static final int CODE_UNAUTHORIZED=401;

    public static final int CODE_FORBIDDEN=403;

    public static final int CODE_NOT_FOUND=404;

    public static final int CODE_UNBIND=-501;//三方登录未绑定手机号


}
