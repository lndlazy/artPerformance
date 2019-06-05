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


}
