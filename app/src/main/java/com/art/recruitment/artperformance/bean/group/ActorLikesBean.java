package com.art.recruitment.artperformance.bean.group;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class ActorLikesBean extends BaseBean<ActorLikesBean.DataBean> {

    /**
     * code : 0
     * data : {"beLikeActorId":0,"id":0,"likesActorId":0,"likesDate":"2019-05-22T10:09:31.321Z"}
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
         * beLikeActorId : 0
         * id : 0
         * likesActorId : 0
         * likesDate : 2019-05-22T10:09:31.321Z
         */

        private int beLikeActorId;
        private int id;
        private int likesActorId;
        private String likesDate;

        public int getBeLikeActorId() {
            return beLikeActorId;
        }

        public void setBeLikeActorId(int beLikeActorId) {
            this.beLikeActorId = beLikeActorId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikesActorId() {
            return likesActorId;
        }

        public void setLikesActorId(int likesActorId) {
            this.likesActorId = likesActorId;
        }

        public String getLikesDate() {
            return likesDate;
        }

        public void setLikesDate(String likesDate) {
            this.likesDate = likesDate;
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
