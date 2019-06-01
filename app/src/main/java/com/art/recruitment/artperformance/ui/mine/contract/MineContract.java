package com.art.recruitment.artperformance.ui.mine.contract;

import com.art.recruitment.artperformance.bean.mine.MineBean;
import com.art.recruitment.common.base.BaseView;

public interface MineContract extends BaseView {

    /**
     * 获取个人资料
     */
    void returnMineDataBean(MineBean.DataBean bean);
}
