package com.art.recruitment.artperformance.ui.login;

import com.art.recruitment.common.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by linaidao on 2019/6/15.
 */

public class ThirdBindResultEntry extends BaseBean<ThirdBindResultEntry.DataBean> {


    /**
     * data : {"newUser":true,"tokenInfo":{"age":0,"avatar":"","bodyWeight":0,"bust":0,"gender":0,"height":0,"hips":0,"id":0,"name":"","personalExperience":"","personalIntroductionVideo":"","photo":"","telephone":"","token":"","username":"","waist":0,"wechat":""}}
     * fieldErrs : [{"error":"","field":""}]
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
         * newUser : true
         * tokenInfo : {"age":0,"avatar":"","bodyWeight":0,"bust":0,"gender":0,"height":0,"hips":0,"id":0,"name":"","personalExperience":"","personalIntroductionVideo":"","photo":"","telephone":"","token":"","username":"","waist":0,"wechat":""}
         */

        private boolean newUser;
        private TokenInfoBean tokenInfo;

        public boolean isNewUser() {
            return newUser;
        }

        public void setNewUser(boolean newUser) {
            this.newUser = newUser;
        }

        public TokenInfoBean getTokenInfo() {
            return tokenInfo;
        }

        public void setTokenInfo(TokenInfoBean tokenInfo) {
            this.tokenInfo = tokenInfo;
        }

        public static class TokenInfoBean {
            /**
             * age : 0
             * avatar :
             * bodyWeight : 0
             * bust : 0
             * gender : 0
             * height : 0
             * hips : 0
             * id : 0
             * name :
             * personalExperience :
             * personalIntroductionVideo :
             * photo :
             * telephone :
             * token :
             * username :
             * waist : 0
             * wechat :
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
    }

    public static class FieldErrsBean {
        /**
         * error :
         * field :
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
