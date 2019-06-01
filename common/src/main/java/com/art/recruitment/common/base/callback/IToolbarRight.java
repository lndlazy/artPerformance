package com.art.recruitment.common.base.callback;


public interface   IToolbarRight extends IToolbar  {
    void setRightTitle(String rightTitle);
    String getRightTitle();
    void onRightTitleClicked();
}
