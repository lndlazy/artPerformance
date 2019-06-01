package com.art.recruitment.artperformance.bean.group;

import com.art.recruitment.common.base.BaseBean;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApplyListBean extends BaseBean<ApplyListBean.DataBean> {

    /**
     * data : {"content":[{"applyUserAge":0,"applyUserAvatar":"string","applyUserGender":0,"applyUserGenderText":"string","applyUserId":0,"applyUserName":"string","id":0,"im":{"username":"string"},"recruitmentId":0}]}
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
        private List<ContentBean> content;

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

    }

    public static class ContentBean implements MultiItemEntity {
        /**
         * applyUserAge : 0
         * applyUserAvatar : string
         * applyUserGender : 0
         * applyUserGenderText : string
         * applyUserId : 0
         * applyUserName : string
         * id : 0
         * im : {"username":"string"}
         * recruitmentId : 0
         */

        private int applyUserAge;
        private String applyUserAvatar;
        private int applyUserGender;
        private String applyUserGenderText;
        private int applyUserId;
        private String applyUserName;
        private int id;
        private ImBean im;
        private int recruitmentId;

        public int getApplyUserAge() {
            return applyUserAge;
        }

        public void setApplyUserAge(int applyUserAge) {
            this.applyUserAge = applyUserAge;
        }

        public String getApplyUserAvatar() {
            return applyUserAvatar;
        }

        public void setApplyUserAvatar(String applyUserAvatar) {
            this.applyUserAvatar = applyUserAvatar;
        }

        public int getApplyUserGender() {
            return applyUserGender;
        }

        public void setApplyUserGender(int applyUserGender) {
            this.applyUserGender = applyUserGender;
        }

        public String getApplyUserGenderText() {
            return applyUserGenderText;
        }

        public void setApplyUserGenderText(String applyUserGenderText) {
            this.applyUserGenderText = applyUserGenderText;
        }

        public int getApplyUserId() {
            return applyUserId;
        }

        public void setApplyUserId(int applyUserId) {
            this.applyUserId = applyUserId;
        }

        public String getApplyUserName() {
            return applyUserName;
        }

        public void setApplyUserName(String applyUserName) {
            this.applyUserName = applyUserName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ImBean getIm() {
            return im;
        }

        public void setIm(ImBean im) {
            this.im = im;
        }

        public int getRecruitmentId() {
            return recruitmentId;
        }

        public void setRecruitmentId(int recruitmentId) {
            this.recruitmentId = recruitmentId;
        }

        @Override
        public int getItemType() {
            return 0;
        }

        public static class ImBean {
            /**
             * username : string
             */

            private String username;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
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
