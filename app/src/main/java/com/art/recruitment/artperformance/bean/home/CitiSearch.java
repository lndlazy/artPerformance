package com.art.recruitment.artperformance.bean.home;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class CitiSearch extends BaseBean<CitiSearch.DataBean> {

    /**
     * code : 0
     * data : {"cityCode":0,"cityName":"string","cityNamePinyin":"string","delFlag":0,"id":0}
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
         * cityCode : 0
         * cityName : string
         * cityNamePinyin : string
         * delFlag : 0
         * id : 0
         */

        private int cityCode;
        private String cityName;
        private String cityNamePinyin;
        private int delFlag;
        private int id;

        public int getCityCode() {
            return cityCode;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityNamePinyin() {
            return cityNamePinyin;
        }

        public void setCityNamePinyin(String cityNamePinyin) {
            this.cityNamePinyin = cityNamePinyin;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
