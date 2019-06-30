package com.art.recruitment.artperformance.bean.mine;

import com.art.recruitment.common.base.BaseBean;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class MineSignUpBean extends BaseBean<MineSignUpBean.DataBean> {

    /**
     * code : 0
     * data : {"content":[{"applyUserId":0,"gatheringAddress":"string","gatheringTime":"2019-05-20T01:10:19.207Z","hireState":"string","id":0,"labels":["string"],"modifyTime":"2019-05-20T01:10:19.207Z","publisherName":"string","recruitmentId":0,"recruitmentTitle":"string"}],"empty":true,"first":true,"last":true,"number":0,"numberOfElements":0,"pageable":{"page":0,"size":0,"sort":"string"},"size":0,"sort":{"empty":true,"sorted":true,"unsorted":true},"totalElements":0,"totalPages":0}
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
         * content : [{"applyUserId":0,"gatheringAddress":"string","gatheringTime":"2019-05-20T01:10:19.207Z","hireState":"string","id":0,"labels":["string"],"modifyTime":"2019-05-20T01:10:19.207Z","publisherName":"string","recruitmentId":0,"recruitmentTitle":"string"}]
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
        private String publisherAvatarView;

        public String getPublisherAvatarView() {
            return publisherAvatarView;
        }

        public void setPublisherAvatarView(String publisherAvatarView) {
            this.publisherAvatarView = publisherAvatarView;
        }

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
         * applyUserId : 0
         * gatheringAddress : string
         * gatheringTime : 2019-05-20T01:10:19.207Z
         * hireState : string
         * id : 0
         * labels : ["string"]
         * modifyTime : 2019-05-20T01:10:19.207Z
         * publisherName : string
         * recruitmentId : 0
         * recruitmentTitle : string
         */

        private int applyUserId;
        private String gatheringAddress;
        private String gatheringTime;
        private String hireState;
        private int id;
        private String modifyTime;
        private String publisherName;
        private String recruitmentId;
        private String recruitmentTitle;
        private List<String> labels;
        private String salary;
        private int salaryType;
        private ImSimpleInfo imSimpleInfo;

        public ImSimpleInfo getImSimpleInfo() {
            return imSimpleInfo;
        }

        public void setImSimpleInfo(ImSimpleInfo imSimpleInfo) {
            this.imSimpleInfo = imSimpleInfo;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public int getSalaryType() {
            return salaryType;
        }

        public void setSalaryType(int salaryType) {
            this.salaryType = salaryType;
        }

        public int getApplyUserId() {
            return applyUserId;
        }

        public void setApplyUserId(int applyUserId) {
            this.applyUserId = applyUserId;
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

        public String getHireState() {
            return hireState;
        }

        public void setHireState(String hireState) {
            this.hireState = hireState;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getPublisherName() {
            return publisherName;
        }

        public void setPublisherName(String publisherName) {
            this.publisherName = publisherName;
        }

        public String getRecruitmentId() {
            return recruitmentId;
        }

        public void setRecruitmentId(String recruitmentId) {
            this.recruitmentId = recruitmentId;
        }

        public String getRecruitmentTitle() {
            return recruitmentTitle;
        }

        public void setRecruitmentTitle(String recruitmentTitle) {
            this.recruitmentTitle = recruitmentTitle;
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


        public static class ImSimpleInfo {
            private String username;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
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
