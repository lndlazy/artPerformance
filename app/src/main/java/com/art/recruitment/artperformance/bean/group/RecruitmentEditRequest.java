package com.art.recruitment.artperformance.bean.group;

import java.util.List;

public class RecruitmentEditRequest {
    private String applyEndTime;
    private String gatheringAddress;
    private String gatheringTime;
    private int id;
    private String labels;
    private String otherRequirement;
    private int recruitNumber;
    private int salary;
    private int salaryType;
    private String title;
    private int workingHours;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getLabelsList() {
        return labelsList;
    }

    public void setLabelsList(List<String> labelsList) {
        this.labelsList = labelsList;
    }

    private List<String> labelsList;

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
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

    public String getOtherRequirement() {
        return otherRequirement;
    }

    public void setOtherRequirement(String otherRequirement) {
        this.otherRequirement = otherRequirement;
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

    public int getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(int salaryType) {
        this.salaryType = salaryType;
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
}
