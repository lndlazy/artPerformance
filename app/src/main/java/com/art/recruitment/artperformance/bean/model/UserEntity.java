package com.art.recruitment.artperformance.bean.model;

import android.widget.ImageView;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by YoKey on 16/10/8.
 */
public class UserEntity implements IndexableEntity {
    private String nick;
    private int cityCode;

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    private ImageView avatar;
    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public UserEntity(String nick, int cityCode) {
        this.nick = nick;
        this.cityCode = cityCode;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String name) {
        this.nick = name;
    }

    @Override
    public String getFieldIndexBy() {
        return nick;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.nick = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        // 需要用到拼音时(比如:搜索), 可增添pinyin字段 this.pinyin  = pinyin
        // 见 CityEntity
    }
}
