package com.art.recruitment.artperformance.bean.group;

import java.util.List;

public class ReleaseRecruitmentRequest {
    private String title;
    private int recruitNumber;
    private int salary;
    private int workingHours;
    private String gatheringTime;
    private String gatheringAddress;
    private String applyEndTime;
    private String otherRequirement;
    private String salaryType;
    private List<String> labels;
    private int cityId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRecruitNumber() {
        return recruitNumber;
    }

    public void setRecruitNumber(int recruitNumber) {
        this.recruitNumber = recruitNumber;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public String getGatheringTime() {
        return gatheringTime;
    }

    public void setGatheringTime(String gatheringTime) {
        this.gatheringTime = gatheringTime;
    }

    public String getGatheringAddress() {
        return gatheringAddress;
    }

    public void setGatheringAddress(String gatheringAddress) {
        this.gatheringAddress = gatheringAddress;
    }

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public String getOtherRequirement() {
        return otherRequirement;
    }

    public void setOtherRequirement(String otherRequirement) {
        this.otherRequirement = otherRequirement;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
