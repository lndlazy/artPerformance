package com.art.recruitment.artperformance.bean.dynamic;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class DynamicLikesBean extends BaseBean<DynamicLikesBean.DataBean> {

    /**
     * code : 0
     * data : {"dynamicCircleId":0,"likes":0}
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
         * dynamicCircleId : 0
         * likes : 0
         */

        private int dynamicCircleId;
        private int likes;

        public int getDynamicCircleId() {
            return dynamicCircleId;
        }

        public void setDynamicCircleId(int dynamicCircleId) {
            this.dynamicCircleId = dynamicCircleId;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
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
