package com.art.recruitment.artperformance.bean.group;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class GroupDetailBean extends BaseBean<GroupDetailBean.DataBean> {

    /**
     * code : 0
     * data : {"age":0,"bodyWeight":0,"bust":0,"cityId":0,"cityName":"string","gender":0,"genderText":"string","height":0,"hips":0,"id":0,"imSimpleInfo":{"username":"string"},"isLikes":true,"likes":0,"name":"string","personalExperience":"string","personalIntroductionVideo":"string","personalIntroductionVideoView":"string","photo":"string","photoView":["string"],"primaryPhoto":"string","primaryPhotoView":["string"],"telephone":"string","waist":0,"wechat":"string"}
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
         * bodyWeight : 0
         * bust : 0
         * cityId : 0
         * cityName : string
         * gender : 0
         * genderText : string
         * height : 0
         * hips : 0
         * id : 0
         * imSimpleInfo : {"username":"string"}
         * isLikes : true
         * likes : 0
         * name : string
         * personalExperience : string
         * personalIntroductionVideo : string
         * personalIntroductionVideoView : string
         * photo : string
         * photoView : ["string"]
         * primaryPhoto : string
         * primaryPhotoView : ["string"]
         * telephone : string
         * waist : 0
         * wechat : string
         */

        private int age;
        private int bodyWeight;
        private int bust;
        private int cityId;
        private String cityName;
        private int gender;
        private String genderText;
        private int height;
        private int hips;
        private int id;
        private ImSimpleInfoBean imSimpleInfo;
        private boolean isLikes;
        private int likes;
        private String name;
        private String personalExperience;
        private String personalIntroductionVideo;
        private String personalIntroductionVideoView;
        private String photo;
        private String primaryPhoto;
        private String telephone;
        private int waist;
        private String wechat;
        private List<String> photoView;
        private List<String> primaryPhotoView;
        private String personalIntroductionVideoPreviewView;
        private int wechatHiddenFlag;
        private int telephoneHiddenFlag;

        public String getPersonalIntroductionVideoPreviewView() {
            return personalIntroductionVideoPreviewView;
        }

        public void setPersonalIntroductionVideoPreviewView(String personalIntroductionVideoPreviewView) {
            this.personalIntroductionVideoPreviewView = personalIntroductionVideoPreviewView;
        }

        public boolean isLikes() {
            return isLikes;
        }

        public void setLikes(boolean likes) {
            isLikes = likes;
        }

        public int getWechatHiddenFlag() {
            return wechatHiddenFlag;
        }

        public void setWechatHiddenFlag(int wechatHiddenFlag) {
            this.wechatHiddenFlag = wechatHiddenFlag;
        }

        public int getTelephoneHiddenFlag() {
            return telephoneHiddenFlag;
        }

        public void setTelephoneHiddenFlag(int telephoneHiddenFlag) {
            this.telephoneHiddenFlag = telephoneHiddenFlag;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
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

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getGenderText() {
            return genderText;
        }

        public void setGenderText(String genderText) {
            this.genderText = genderText;
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

        public ImSimpleInfoBean getImSimpleInfo() {
            return imSimpleInfo;
        }

        public void setImSimpleInfo(ImSimpleInfoBean imSimpleInfo) {
            this.imSimpleInfo = imSimpleInfo;
        }

        public boolean isIsLikes() {
            return isLikes;
        }

        public void setIsLikes(boolean isLikes) {
            this.isLikes = isLikes;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
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

        public String getPersonalIntroductionVideoView() {
            return personalIntroductionVideoView;
        }

        public void setPersonalIntroductionVideoView(String personalIntroductionVideoView) {
            this.personalIntroductionVideoView = personalIntroductionVideoView;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPrimaryPhoto() {
            return primaryPhoto;
        }

        public void setPrimaryPhoto(String primaryPhoto) {
            this.primaryPhoto = primaryPhoto;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
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

        public List<String> getPhotoView() {
            return photoView;
        }

        public void setPhotoView(List<String> photoView) {
            this.photoView = photoView;
        }

        public List<String> getPrimaryPhotoView() {
            return primaryPhotoView;
        }

        public void setPrimaryPhotoView(List<String> primaryPhotoView) {
            this.primaryPhotoView = primaryPhotoView;
        }

        public static class ImSimpleInfoBean {
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
