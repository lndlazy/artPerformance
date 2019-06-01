package com.art.recruitment.artperformance.bean.login;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class StartUpBean extends BaseBean<StartUpBean.DataBean> {

    /**
     * code : 0
     * data : {"hasStartPage":true,"imageUrl":"string"}
     * fieldErrs : [{"error":"string","field":"string"}]
     * message : string
     */

    private List<FieldErrsBean> fieldErrs;

    public List<FieldErrsBean> getFieldErrs() {
        return fieldErrs;
    }

    public void setFieldErrs(List<FieldErrsBean> fieldErrs) {
        this.fieldErrs = fieldErrs;
    }

    public static class DataBean {
        /**
         * hasStartPage : true
         * imageUrl : string
         */

        private boolean hasStartPage;
        private String imageUrl;

        public boolean isHasStartPage() {
            return hasStartPage;
        }

        public void setHasStartPage(boolean hasStartPage) {
            this.hasStartPage = hasStartPage;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class FieldErrsBean {
        /**
         * error : string
         * field : string
         */

        private String error;
        private String field;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }
}
