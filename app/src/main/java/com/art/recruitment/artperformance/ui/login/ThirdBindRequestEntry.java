package com.art.recruitment.artperformance.ui.login;

/**
 * Created by linaidao on 2019/6/15.
 */

public class ThirdBindRequestEntry  {


    /**
     * mobileNo :
     * openId :
     * socialAccount :
     * verificationCode :
     */

    private String mobileNo;
    private String openId;
    private String socialAccount;//第三方账号
    private String verificationCode;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSocialAccount() {
        return socialAccount;
    }

    public void setSocialAccount(String socialAccount) {
        this.socialAccount = socialAccount;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
