package com.art.recruitment.artperformance.bean.group;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class RecruitmentEditBean extends BaseBean<RecruitmentEditBean.DataBean> {

    /**
     * code : 0
     * data : {"applyEndTime":"2019-05-22T05:17:12.948Z","applyNumber":0,"applyUsers":[{"applyTime":"2019-05-22T05:17:12.948Z","applyUserAvatar":"string","applyUserId":0,"applyUserImUserId":"string","hireState":0,"id":0,"recruitmentId":0}],"createTime":"2019-05-22T05:17:12.948Z","createUserId":0,"createUserName":"string","delFlag":0,"frontendFlag":0,"gatheringAddress":"string","gatheringTime":"2019-05-22T05:17:12.949Z","hireNumber":0,"id":0,"labels":"string","modifyTime":"2019-05-22T05:17:12.949Z","otherRequirement":"string","publisher":0,"publisherName":"string","recruitNumber":0,"releaseTime":"2019-05-22T05:17:12.949Z","salary":0,"salaryType":0,"simpleImInfo":{"username":"string"},"title":"string","workingHours":0}
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
         * applyEndTime : 2019-05-22T05:17:12.948Z
         * applyNumber : 0
         * applyUsers : [{"applyTime":"2019-05-22T05:17:12.948Z","applyUserAvatar":"string","applyUserId":0,"applyUserImUserId":"string","hireState":0,"id":0,"recruitmentId":0}]
         * createTime : 2019-05-22T05:17:12.948Z
         * createUserId : 0
         * createUserName : string
         * delFlag : 0
         * frontendFlag : 0
         * gatheringAddress : string
         * gatheringTime : 2019-05-22T05:17:12.949Z
         * hireNumber : 0
         * id : 0
         * labels : string
         * modifyTime : 2019-05-22T05:17:12.949Z
         * otherRequirement : string
         * publisher : 0
         * publisherName : string
         * recruitNumber : 0
         * releaseTime : 2019-05-22T05:17:12.949Z
         * salary : 0
         * salaryType : 0
         * simpleImInfo : {"username":"string"}
         * title : string
         * workingHours : 0
         */

        private String applyEndTime;
        private int applyNumber;
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
        private List<String> labelsList;
        private String cityName;
        private List<ApplyUsersBean> applyUsers;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public List<String> getLabelsList() {
            return labelsList;
        }

        public void setLabelsList(List<String> labelsList) {
            this.labelsList = labelsList;
        }

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

        public static class SimpleImInfoBean {
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

        public static class ApplyUsersBean {
            /**
             * applyTime : 2019-05-22T05:17:12.948Z
             * applyUserAvatar : string
             * applyUserId : 0
             * applyUserImUserId : string
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
