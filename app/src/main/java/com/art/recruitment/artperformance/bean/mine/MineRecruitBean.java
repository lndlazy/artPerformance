package com.art.recruitment.artperformance.bean.mine;

import com.art.recruitment.common.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MineRecruitBean extends BaseBean<MineRecruitBean.DataBean> {

    /**
     * data : {"chatGroupId":"string","recruitmentId":0}
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
        /**
         * chatGroupId : string
         * recruitmentId : 0
         */

        private String chatGroupId;
        private int recruitmentId;

        public String getChatGroupId() {
            return chatGroupId;
        }

        public void setChatGroupId(String chatGroupId) {
            this.chatGroupId = chatGroupId;
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
