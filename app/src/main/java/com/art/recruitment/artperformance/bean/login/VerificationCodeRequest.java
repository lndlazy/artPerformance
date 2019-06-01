package com.art.recruitment.artperformance.bean.login;

public class VerificationCodeRequest {
    private String mobilePhone;
    private String type;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
