package com.art.recruitment.artperformance.bean.mine;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class FeedbackBean extends BaseBean<FeedbackBean.DataBean> {

    /**
     * code : 0
     * data : {"cityId":0,"content":"string","createTime":"2019-05-20T05:29:32.430Z","delFlag":0,"feedbackUserId":0,"id":0,"modifyTime":"2019-05-20T05:29:32.430Z"}
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
         * cityId : 0
         * content : string
         * createTime : 2019-05-20T05:29:32.430Z
         * delFlag : 0
         * feedbackUserId : 0
         * id : 0
         * modifyTime : 2019-05-20T05:29:32.430Z
         */

        private int cityId;
        private String content;
        private String createTime;
        private int delFlag;
        private int feedbackUserId;
        private int id;
        private String modifyTime;

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getFeedbackUserId() {
            return feedbackUserId;
        }

        public void setFeedbackUserId(int feedbackUserId) {
            this.feedbackUserId = feedbackUserId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
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
