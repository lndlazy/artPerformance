package com.art.recruitment.artperformance.bean.mine;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class ConsummateInfoBean extends BaseBean<ConsummateInfoBean.DataBean> {

    /**
     * code : 0
     * data : {"age":0,"avatar":"string","avatarView":"string","basicInfoFlag":0,"birthday":"string","bodyWeight":0,"bust":0,"cityId":0,"cityName":"string","gender":0,"genderText":"string","height":0,"hips":0,"id":0,"idCard":"string","name":"string","personalExperience":"string","personalIntroductionVideo":"string","photo":["string"],"photoView":["string"],"primaryPhoto":["string"],"primaryPhotoView":["string"],"realName":"string","realNameFlag":0,"residenceAddress":"string","telephone":"string","telephoneHiddenFlag":0,"username":"string","waist":0,"wechat":"string","wechatHiddenFlag":0}
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
         * avatarView : string
         * basicInfoFlag : 0
         * birthday : string
         * bodyWeight : 0
         * bust : 0
         * cityId : 0
         * cityName : string
         * gender : 0
         * genderText : string
         * height : 0
         * hips : 0
         * id : 0
         * idCard : string
         * name : string
         * personalExperience : string
         * personalIntroductionVideo : string
         * photo : ["string"]
         * photoView : ["string"]
         * primaryPhoto : ["string"]
         * primaryPhotoView : ["string"]
         * realName : string
         * realNameFlag : 0
         * residenceAddress : string
         * telephone : string
         * telephoneHiddenFlag : 0
         * username : string
         * waist : 0
         * wechat : string
         * wechatHiddenFlag : 0
         */

        private int age;
        private String avatar;
        private String avatarView;
        private int basicInfoFlag;
        private String birthday;
        private int bodyWeight;
        private int bust;
        private int cityId;
        private String cityName;
        private int gender;
        private String genderText;
        private int height;
        private int hips;
        private int id;
        private String idCard;
        private String name;
        private String personalExperience;
        private String personalIntroductionVideo;
        private String realName;
        private int realNameFlag;
        private String residenceAddress;
        private String telephone;
        private int telephoneHiddenFlag;
        private String username;
        private int waist;
        private String wechat;
        private int wechatHiddenFlag;
        private List<String> photo;
        private List<String> photoView;
        private List<String> primaryPhoto;
        private List<String> primaryPhotoView;

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

        public String getAvatarView() {
            return avatarView;
        }

        public void setAvatarView(String avatarView) {
            this.avatarView = avatarView;
        }

        public int getBasicInfoFlag() {
            return basicInfoFlag;
        }

        public void setBasicInfoFlag(int basicInfoFlag) {
            this.basicInfoFlag = basicInfoFlag;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
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

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
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

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getRealNameFlag() {
            return realNameFlag;
        }

        public void setRealNameFlag(int realNameFlag) {
            this.realNameFlag = realNameFlag;
        }

        public String getResidenceAddress() {
            return residenceAddress;
        }

        public void setResidenceAddress(String residenceAddress) {
            this.residenceAddress = residenceAddress;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getTelephoneHiddenFlag() {
            return telephoneHiddenFlag;
        }

        public void setTelephoneHiddenFlag(int telephoneHiddenFlag) {
            this.telephoneHiddenFlag = telephoneHiddenFlag;
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

        public int getWechatHiddenFlag() {
            return wechatHiddenFlag;
        }

        public void setWechatHiddenFlag(int wechatHiddenFlag) {
            this.wechatHiddenFlag = wechatHiddenFlag;
        }

        public List<String> getPhoto() {
            return photo;
        }

        public void setPhoto(List<String> photo) {
            this.photo = photo;
        }

        public List<String> getPhotoView() {
            return photoView;
        }

        public void setPhotoView(List<String> photoView) {
            this.photoView = photoView;
        }

        public List<String> getPrimaryPhoto() {
            return primaryPhoto;
        }

        public void setPrimaryPhoto(List<String> primaryPhoto) {
            this.primaryPhoto = primaryPhoto;
        }

        public List<String> getPrimaryPhotoView() {
            return primaryPhotoView;
        }

        public void setPrimaryPhotoView(List<String> primaryPhotoView) {
            this.primaryPhotoView = primaryPhotoView;
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
