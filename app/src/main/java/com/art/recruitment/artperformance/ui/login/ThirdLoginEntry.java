package com.art.recruitment.artperformance.ui.login;

import com.art.recruitment.common.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by linaidao on 2019/6/14.
 */

public class ThirdLoginEntry extends BaseBean<ThirdLoginEntry.DataBean> {


    /**
     * data : {"applyEndTime":"","applyNumber":0,"applyUsers":[{"applyTime":"","applyUserAvatar":"","applyUserId":0,"applyUserImUserId":"","hireState":0,"id":0,"recruitmentId":0}],"cityId":0,"cityName":"","createTime":"","createUserId":0,"createUserName":"","delFlag":0,"frontendFlag":0,"gatheringAddress":"","gatheringTime":"","hireNumber":0,"id":0,"labelList":[],"labels":"","modifyTime":"","otherRequirement":"","publisher":0,"publisherName":"","recruitNumber":0,"releaseTime":"","salary":0,"salaryType":0,"simpleImInfo":{"username":""},"title":"","workingHours":0}
     * fieldErrs : [{"error":"","field":""}]
     */

    @SerializedName("data")
    private DataBean dataX;
    private List<FieldErrsBean> fieldErrs;

    public DataBean getDataX() {
        return dataX;
    }

    public void setDataX(DataBean dataX) {
        this.dataX = dataX;
    }

    public List<FieldErrsBean> getFieldErrs() {
        return fieldErrs;
    }

    public void setFieldErrs(List<FieldErrsBean> fieldErrs) {
        this.fieldErrs = fieldErrs;
    }

    public static class DataBean {
        /**
         * applyEndTime :
         * applyNumber : 0
         * applyUsers : [{"applyTime":"","applyUserAvatar":"","applyUserId":0,"applyUserImUserId":"","hireState":0,"id":0,"recruitmentId":0}]
         * cityId : 0
         * cityName :
         * createTime :
         * createUserId : 0
         * createUserName :
         * delFlag : 0
         * frontendFlag : 0
         * gatheringAddress :
         * gatheringTime :
         * hireNumber : 0
         * id : 0
         * labelList : []
         * labels :
         * modifyTime :
         * otherRequirement :
         * publisher : 0
         * publisherName :
         * recruitNumber : 0
         * releaseTime :
         * salary : 0
         * salaryType : 0
         * simpleImInfo : {"username":""}
         * title :
         * workingHours : 0
         */

        private String applyEndTime;
        private int applyNumber;
        private int cityId;
        private String cityName;
        private String createTime;
        private int createUserId;
        private String createUserName;
        private int delFlag;
        private int frontendFlag;
        private String gatheringAddress;
        private String gatheringTime;
        private int hireNumber;
        private int id;
        private String labels;
        private String modifyTime;
        private String otherRequirement;
        private int publisher;
        private String publisherName;
        private int recruitNumber;
        private String releaseTime;
        private int salary;
        private int salaryType;
        private SimpleImInfoBean simpleImInfo;
        private String title;
        private int workingHours;
        private List<ApplyUsersBean> applyUsers;
        private List<?> labelList;

        public String getApplyEndTime() {
            return applyEndTime;
        }

        public void setApplyEndTime(String applyEndTime) {
            this.applyEndTime = applyEndTime;
        }

        public int getApplyNumber() {
            return applyNumber;
        }

        public void setApplyNumber(int applyNumber) {
            this.applyNumber = applyNumber;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getFrontendFlag() {
            return frontendFlag;
        }

        public void setFrontendFlag(int frontendFlag) {
            this.frontendFlag = frontendFlag;
        }

        public String getGatheringAddress() {
            return gatheringAddress;
        }

        public void setGatheringAddress(String gatheringAddress) {
            this.gatheringAddress = gatheringAddress;
        }

        public String getGatheringTime() {
            return gatheringTime;
        }

        public void setGatheringTime(String gatheringTime) {
            this.gatheringTime = gatheringTime;
        }

        public int getHireNumber() {
            return hireNumber;
        }

        public void setHireNumber(int hireNumber) {
            this.hireNumber = hireNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabels() {
            return labels;
        }

        public void setLabels(String labels) {
            this.labels = labels;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getOtherRequirement() {
            return otherRequirement;
        }

        public void setOtherRequirement(String otherRequirement) {
            this.otherRequirement = otherRequirement;
        }

        public int getPublisher() {
            return publisher;
        }

        public void setPublisher(int publisher) {
            this.publisher = publisher;
        }

        public String getPublisherName() {
            return publisherName;
        }

        public void setPublisherName(String publisherName) {
            this.publisherName = publisherName;
        }

        public int getRecruitNumber() {
            return recruitNumber;
        }

        public void setRecruitNumber(int recruitNumber) {
            this.recruitNumber = recruitNumber;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }

        public int getSalaryType() {
            return salaryType;
        }

        public void setSalaryType(int salaryType) {
            this.salaryType = salaryType;
        }

        public SimpleImInfoBean getSimpleImInfo() {
            return simpleImInfo;
        }

        public void setSimpleImInfo(SimpleImInfoBean simpleImInfo) {
            this.simpleImInfo = simpleImInfo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getWorkingHours() {
            return workingHours;
        }

        public void setWorkingHours(int workingHours) {
            this.workingHours = workingHours;
        }

        public List<ApplyUsersBean> getApplyUsers() {
            return applyUsers;
        }

        public void setApplyUsers(List<ApplyUsersBean> applyUsers) {
            this.applyUsers = applyUsers;
        }

        public List<?> getLabelList() {
            return labelList;
        }

        public void setLabelList(List<?> labelList) {
            this.labelList = labelList;
        }

        public static class SimpleImInfoBean {
            /**
             * username :
             */

            private String username;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class ApplyUsersBean {
            /**
             * applyTime :
             * applyUserAvatar :
             * applyUserId : 0
             * applyUserImUserId :
             * hireState : 0
             * id : 0
             * recruitmentId : 0
             */

            private String applyTime;
            private String applyUserAvatar;
            private int applyUserId;
            private String applyUserImUserId;
            private int hireState;
            private int id;
            private int recruitmentId;

            public String getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(String applyTime) {
                this.applyTime = applyTime;
            }

            public String getApplyUserAvatar() {
                return applyUserAvatar;
            }

            public void setApplyUserAvatar(String applyUserAvatar) {
                this.applyUserAvatar = applyUserAvatar;
            }

            public int getApplyUserId() {
                return applyUserId;
            }

            public void setApplyUserId(int applyUserId) {
                this.applyUserId = applyUserId;
            }

            public String getApplyUserImUserId() {
                return applyUserImUserId;
            }

            public void setApplyUserImUserId(String applyUserImUserId) {
                this.applyUserImUserId = applyUserImUserId;
            }

            public int getHireState() {
                return hireState;
            }

            public void setHireState(int hireState) {
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
