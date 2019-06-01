package com.art.recruitment.artperformance.bean.home;

import com.art.recruitment.common.base.BaseBean;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class RecruitListBean extends BaseBean<RecruitListBean.DataBean> {


    /**
     * code : 0
     * data : {"content":[{"gatheringAddress":"string","gatheringTime":"2019-05-09T10:18:43.763Z","id":0,"labels":["string"],"publisher":0,"publisherName":0,"salary":0,"salaryType":0,"title":"string"}],"empty":true,"first":true,"last":true,"number":0,"numberOfElements":0,"pageable":{"page":0,"size":0,"sort":"string"},"size":0,"sort":{"empty":true,"sorted":true,"unsorted":true},"totalElements":0,"totalPages":0}
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
         * content : [{"gatheringAddress":"string","gatheringTime":"2019-05-09T10:18:43.763Z","id":0,"labels":["string"],"publisher":0,"publisherName":0,"salary":0,"salaryType":0,"title":"string"}]
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
         * gatheringAddress : string
         * gatheringTime : 2019-05-09T10:18:43.763Z
         * id : 0
         * labels : ["string"]
         * publisher : 0
         * publisherName : 0
         * salary : 0
         * salaryType : 0
         * title : string
         */

        private String gatheringAddress;
        private String gatheringTime;
        private int id;
        private int publisher;
        private String publisherName;
        private int salary;
        private int salaryType;
        private String title;
        private List<String> labels;

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

        public List<String> getLabels() {
            return labels;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
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
