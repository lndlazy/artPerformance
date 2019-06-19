package com.art.recruitment.artperformance.utils;

import android.os.Environment;

/**
 * Created by linaidao on 2019/6/2.
 */

public interface Constant {

    //编辑个人资料的类型 1，发布  2，应聘 （发布时只要填写必选项，应聘时需要全部选项都要填写）
    String EDITOR_TYPE = "editor_type";

    int EDITOR_TYPE_RELEASE = 1;//1，发布
    int EDITOR_TYPE_APPLY = 2;//2，应聘

    //图片缓存的目录
    String CACHE_DIR = Environment.getExternalStorageDirectory().getPath() + "/artperformance";

    String FILE_PROVIDER_PATH = "com.art.recruitment.artperformance.fileprovider";// 文件提供路径

    //bucket 头像
//    String DIR_HEADPIC = "head_pic/";
    String DIR_HEADPIC = "actor/img/avatar/";
    //个人视频
    String DIR_VIDEO = "actor/video/personal/";
    //个人视频预览图
    String DIR_VIDEO_COVER = "actor/img/preview/personal/";
    //    String DIR_VIDEO = "video/";
    //主图
//    String DIR_COVER = "cover/";
    String DIR_COVER = "actor/img/primary/";
    //照片
//    String DIR_PHOTOS = "photos/";
    String DIR_PHOTOS = "actor/img/photo/";
    //动态圈图片
    String DIR_DYNAMIC = "actor/img/dynamiccircle/";
    //动态圈视频
    String DIR_DYNAMIC_VIDEO = "actor/video/dynamiccircle/";
    //动态圈视频预览图
    String DIR_DYNAMIC_VIDEO_PREVIEW = "actor/img/preview/dynamiccircle/";

    //个人视频预览图ObjectKey：actor/img/preview/personal/{previewImgName}


    String PIC_DIR = ".jpg";
    String VIDEO_DIR = ".mp4";


    String SORT_DESC = "desc";//降序
    String SORT_ASC = "asc";//升序

    //首页价格排序
    String SALARY_DESC = "salary,desc";
    String SALARY_ASC = "salary,asc";


    String SELECT_DATE_START = "2010.01.01 00:00";//选择日期 的开始时间
    String SELECT_DATE_END = "2090.12.30 00:00";//选择日期 的结束时间


    String TYPE_PRICE_SURE = "1";//确定好的价格
    String TYPE_PRICE_FACE = "2";//面议

    int TYPE_PRICE_SURE_INT = 1;//确定好的价格
    int TYPE_PRICE_FACE_INT = 2;//面议


    String MSG_ACTION_DOWN = "down";
    String MSG_ACTION_UP = "up";

    //录用状态（1-待录用，2-已录用）
    String APPLY_TYPE_WAIT = "1";
    String APPLY_TYPE_ALREADY = "2";


    int GENDER_MALE = 1;//男性
    int GENDER_FREMALE = 2;//女性


    int CHOOSE_TYPE_VIDEO = 1;//选择的是视频
    int CHOOSE_TYPE_PICTURE = 0;//选择的是图片


    String THIRD_LOGIN_WX = "weChat";//微信第三方登录
    String THIRD_LOGIN_QQ = "qq";//qq第三方登录

}
