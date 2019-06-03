package com.art.recruitment.artperformance.ui.mine.contract;

import com.art.recruitment.artperformance.bean.mine.ConsummateInfoBean;
import com.art.recruitment.artperformance.bean.mine.MineBean;
import com.art.recruitment.artperformance.bean.mine.OssBean;
import com.art.recruitment.common.base.BaseView;

public interface MineDataContract extends BaseView {

    /**
     * 获取个人资料
     */
    void returnMineDataBean(MineBean.DataBean bean);

    /**
     * 完善基本资料
     */
    void returnEssentialInfoBean(ConsummateInfoBean.DataBean bean);

    /**
     * OSS签名
     */
    void returnSignaTureBean(String bean);

    /**
     * OSS签名信息
     */
    void returnOssBean(OssBean.DataBean bean);

    /**
     * id转换为url
     * @param picUrl 图片地址
     * @param type
     */
    void returnPathUrlBean(String picUrl, int type);


}
