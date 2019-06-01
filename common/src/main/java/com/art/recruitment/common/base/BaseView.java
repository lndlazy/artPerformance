package com.art.recruitment.common.base;


import com.art.recruitment.common.http.error.ErrorType;

public interface BaseView {
    void showErrorTip(ErrorType errorType, int errorCode, String message);
}
