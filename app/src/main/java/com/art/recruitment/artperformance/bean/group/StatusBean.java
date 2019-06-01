package com.art.recruitment.artperformance.bean.group;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class StatusBean extends BaseBean<StatusBean.DataBean> {

    /**
     * code : 0
     * data : {"actorId":0,"basicInfoFlag":0,"realNameFlag":0}
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
         * actorId : 0
         * basicInfoFlag : 0
         * realNameFlag : 0
         */

        private int actorId;
        private int basicInfoFlag;
        private int realNameFlag;

        public int getActorId() {
            return actorId;
        }

        public void setActorId(int actorId) {
            this.actorId = actorId;
        }

        public int getBasicInfoFlag() {
            return basicInfoFlag;
        }

        public void setBasicInfoFlag(int basicInfoFlag) {
            this.basicInfoFlag = basicInfoFlag;
        }

        public int getRealNameFlag() {
            return realNameFlag;
        }

        public void setRealNameFlag(int realNameFlag) {
            this.realNameFlag = realNameFlag;
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
