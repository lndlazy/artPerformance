package com.art.recruitment.artperformance.bean.login;

import com.art.recruitment.common.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResetPasswordBean extends BaseBean<ResetPasswordBean.DataBean> {

    /**
     * data : {}
     * fieldErrs : [{"error":"string","field":"string"}]
     */

    private List<FieldErrsBean> fieldErrs;

    public List<FieldErrsBean> getFieldErrs() {
        return fieldErrs;
    }

    public void setFieldErrs(List<FieldErrsBean> fieldErrs) {
        this.fieldErrs = fieldErrs;
    }

    public static class DataBean {
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
