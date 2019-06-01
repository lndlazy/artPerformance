package com.art.recruitment.artperformance.bean.home;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class ApplyBean extends BaseBean<ApplyBean.DataBean> {

    /**
     * code : 0
     * data : {"applyTime":"2019-05-22T02:32:51.291Z","applyUserId":0,"hireState":"string","id":0,"recruitmentId":0}
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
         * applyTime : 2019-05-22T02:32:51.291Z
         * applyUserId : 0
         * hireState : string
         * id : 0
         * recruitmentId : 0
         */

        private String applyTime;
        private int applyUserId;
        private String hireState;
        private int id;
        private int recruitmentId;

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public int getApplyUserId() {
            return applyUserId;
        }

        public void setApplyUserId(int applyUserId) {
            this.applyUserId = applyUserId;
        }

        public String getHireState() {
            return hireState;
        }

        public void setHireState(String hireState) {
            this.hireState = hireState;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRecruitmentId() {
            return recruitmentId;
        }

        public void setRecruitmentId(int recruitmentId) {
            this.recruitmentId = recruitmentId;
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
