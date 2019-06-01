package com.art.recruitment.artperformance.bean.group;

import com.art.recruitment.common.base.BaseBean;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class GroupListBean extends BaseBean<GroupListBean.DataBean> {

    /**
     * code : 0
     * data : {"content":[{"age":0,"cityId":0,"cityName":"string","gender":0,"genderText":"string","id":0,"isLikes":true,"likes":0,"name":"string","primaryPhoto":"string"}],"empty":true,"first":true,"last":true,"number":0,"numberOfElements":0,"pageable":{"page":0,"size":0,"sort":"string"},"size":0,"sort":{"empty":true,"sorted":true,"unsorted":true},"totalElements":0,"totalPages":0}
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
         * content : [{"age":0,"cityId":0,"cityName":"string","gender":0,"genderText":"string","id":0,"isLikes":true,"likes":0,"name":"string","primaryPhoto":"string"}]
         * empty : true
         * first : true
         * last : true
         * number : 0
         * numberOfElements : 0
         * pageable : {"page":0,"size":0,"sort":"string"}
         * size : 0
         * sort : {"empty":true,"sorted":true,"unsorted":true}
         * totalElements : 0
         * totalPages : 0
         */

        private boolean empty;
        private boolean first;
        private boolean last;
        private int number;
        private int numberOfElements;
        private PageableBean pageable;
        private int size;
        private SortBean sort;
        private int totalElements;
        private int totalPages;
        private List<ContentBean> content;

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public PageableBean getPageable() {
            return pageable;
        }

        public void setPageable(PageableBean pageable) {
            this.pageable = pageable;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public SortBean getSort() {
            return sort;
        }

        public void setSort(SortBean sort) {
            this.sort = sort;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class PageableBean {
            /**
             * page : 0
             * size : 0
             * sort : string
             */

            private int page;
            private int size;
            private SortBean sort;

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public SortBean getSort() {
                return sort;
            }

            public void setSort(SortBean sort) {
                this.sort = sort;
            }
        }

        public static class SortBean {
            /**
             * empty : true
             * sorted : true
             * unsorted : true
             */

            private boolean empty;
            private boolean sorted;
            private boolean unsorted;

            public boolean isEmpty() {
                return empty;
            }

            public void setEmpty(boolean empty) {
                this.empty = empty;
            }

            public boolean isSorted() {
                return sorted;
            }

            public void setSorted(boolean sorted) {
                this.sorted = sorted;
            }

            public boolean isUnsorted() {
                return unsorted;
            }

            public void setUnsorted(boolean unsorted) {
                this.unsorted = unsorted;
            }
        }

    }

    public static class ContentBean implements MultiItemEntity {
        /**
         * age : 0
         * cityId : 0
         * cityName : string
         * gender : 0
         * genderText : string
         * id : 0
         * isLikes : true
         * likes : 0
         * name : string
         * primaryPhoto : string
         */

        private int age;
        private int cityId;
        private String cityName;
        private int gender;
        private String genderText;
        private int id;
        private boolean isLikes;
        private int likes;
        private String name;
        private String primaryPhoto;
        private List<String> primaryPhotoView;

        public boolean isLikes() {
            return isLikes;
        }

        public void setLikes(boolean likes) {
            isLikes = likes;
        }

        public List<String> getPrimaryPhotoView() {
            return primaryPhotoView;
        }

        public void setPrimaryPhotoView(List<String> primaryPhotoView) {
            this.primaryPhotoView = primaryPhotoView;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getGenderText() {
            return genderText;
        }

        public void setGenderText(String genderText) {
            this.genderText = genderText;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsLikes() {
            return isLikes;
        }

        public void setIsLikes(boolean isLikes) {
            this.isLikes = isLikes;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrimaryPhoto() {
            return primaryPhoto;
        }

        public void setPrimaryPhoto(String primaryPhoto) {
            this.primaryPhoto = primaryPhoto;
        }

        @Override
        public int getItemType() {
            return 0;
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
