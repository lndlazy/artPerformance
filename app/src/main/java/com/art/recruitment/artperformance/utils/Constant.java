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

    //bucket 头像存放目录
    String DIR_HEADPIC = "head_pic/";
    //视频目录
    String DIR_VIDEO = "video/";
    //封面
    String DIR_COVER = "cover/";
    //照片集
    String DIR_PHOTOS = "photos/";


    String SORT_DESC = "desc";//降序
    String SORT_ASC = "asc";//升序


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

}
