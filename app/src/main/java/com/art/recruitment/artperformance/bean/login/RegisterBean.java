package com.art.recruitment.artperformance.bean.login;

import com.art.recruitment.common.base.BaseBean;

import java.io.Serializable;
import java.util.List;

public class RegisterBean extends BaseBean<RegisterBean.DataBean> {

    /**
     * code : 0
     * data : {"age":0,"avatar":"string","bodyWeight":0,"bust":0,"gender":0,"height":0,"hips":0,"id":0,"name":"string","personalExperience":"string","personalIntroductionVideo":"string","photo":"string","telephone":"string","token":"string","username":"string","waist":0,"wechat":"string"}
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
         * age : 0
         * avatar : string
         * bodyWeight : 0
         * bust : 0
         * gender : 0
         * height : 0
         * hips : 0
         * id : 0
         * name : string
         * personalExperience : string
         * personalIntroductionVideo : string
         * photo : string
         * telephone : string
         * token : string
         * username : string
         * waist : 0
         * wechat : string
         */

        private int age;
        private String avatar;
        private int bodyWeight;
        private int bust;
        private int gender;
        private int height;
        private int hips;
        private int id;
        private String name;
        private String personalExperience;
        private String personalIntroductionVideo;
        private String photo;
        private String telephone;
        private String token;
        private String username;
        private int waist;
        private String wechat;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getBodyWeight() {
            return bodyWeight;
        }

        public void setBodyWeight(int bodyWeight) {
            this.bodyWeight = bodyWeight;
        }

        public int getBust() {
            return bust;
        }

        public void setBust(int bust) {
            this.bust = bust;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getHips() {
            return hips;
        }

        public void setHips(int hips) {
            this.hips = hips;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPersonalExperience() {
            return personalExperience;
        }

        public void setPersonalExperience(String personalExperience) {
            this.personalExperience = personalExperience;
        }

        public String getPersonalIntroductionVideo() {
            return personalIntroductionVideo;
        }

        public void setPersonalIntroductionVideo(String personalIntroductionVideo) {
            this.personalIntroductionVideo = personalIntroductionVideo;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getWaist() {
            return waist;
        }

        public void setWaist(int waist) {
            this.waist = waist;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
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
