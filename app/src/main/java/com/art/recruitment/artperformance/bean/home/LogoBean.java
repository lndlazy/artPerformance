package com.art.recruitment.artperformance.bean.home;

import com.art.recruitment.common.base.BaseBean;

/**
 * Created by linaidao on 2019/6/21.
 */

public class LogoBean extends BaseBean<LogoBean.DataBean> {


    /**
     * code : 200
     * data : {"url":"https://yizhan-app-dev.oss-cn-beijing.aliyuncs.com/logo/logo.png"}
     */

//    private int code;


//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }



    public static class DataBean {
        /**
         * url : https://yizhan-app-dev.oss-cn-beijing.aliyuncs.com/logo/logo.png
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
