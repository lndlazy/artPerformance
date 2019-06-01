package com.art.recruitment.artperformance.bean.mine;

import android.os.Parcel;
import android.os.Parcelable;

import com.art.recruitment.common.base.BaseBean;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MineFecruitmentBean extends BaseBean<MineFecruitmentBean.DataBean> {

    /**
     * data : {"content":[{"applyEndTime":"2019-05-22T06:13:56.329Z","applyNumber":0,"frontendFlag":0,"frontendFlagText":"string","gatheringAddress":"string","gatheringTime":"2019-05-22T06:13:56.329Z","hireNumber":0,"id":0,"labelList":["string"],"labels":"string","publisher":0,"recruitNumber":0,"recruitmentState":0,"recruitmentStateText":"string","releaseTime":"2019-05-22T06:13:56.329Z","salary":0,"salaryType":0,"title":"string"}],"empty":true,"first":true,"last":true,"number":0,"numberOfElements":0,"pageable":{"page":0,"size":0,"sort":"string"},"size":0,"sort":{"empty":true,"sorted":true,"unsorted":true},"totalElements":0,"totalPages":0}
     * fieldErrs : [{"error":"string","field":"string"}]
     */

    private List<FieldErrsBean> fieldErrs;

    public List<FieldErrsBean> getFieldErrs() {
        return fieldErrs;
    }

    public void setFieldErrs(List<FieldErrsBean> fieldErrs) {
        this.fieldErrs = fieldErrs;
    }

    public static class DataBean implements Parcelable {
        /**
         * content : [{"applyEndTime":"2019-05-22T06:13:56.329Z","applyNumber":0,"frontendFlag":0,"frontendFlagText":"string","gatheringAddress":"string","gatheringTime":"2019-05-22T06:13:56.329Z","hireNumber":0,"id":0,"labelList":["string"],"labels":"string","publisher":0,"recruitNumber":0,"recruitmentState":0,"recruitmentStateText":"string","releaseTime":"2019-05-22T06:13:56.329Z","salary":0,"salaryType":0,"title":"string"}]
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

        protected DataBean(Parcel in) {
            empty = in.readByte() != 0;
            first = in.readByte() != 0;
            last = in.readByte() != 0;
            number = in.readInt();
            numberOfElements = in.readInt();
            size = in.readInt();
            totalElements = in.readInt();
            totalPages = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (empty ? 1 : 0));
            dest.writeByte((byte) (first ? 1 : 0));
            dest.writeByte((byte) (last ? 1 : 0));
            dest.writeInt(number);
            dest.writeInt(numberOfElements);
            dest.writeInt(size);
            dest.writeInt(totalElements);
            dest.writeInt(totalPages);
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

    public static class ContentBean implements MultiItemEntity, Parcelable {
        /**
         * applyEndTime : 2019-05-22T06:13:56.329Z
         * applyNumber : 0
         * frontendFlag : 0
         * frontendFlagText : string
         * gatheringAddress : string
         * gatheringTime : 2019-05-22T06:13:56.329Z
         * hireNumber : 0
         * id : 0
         * labelList : ["string"]
         * labels : string
         * publisher : 0
         * recruitNumber : 0
         * recruitmentState : 0
         * recruitmentStateText : string
         * releaseTime : 2019-05-22T06:13:56.329Z
         * salary : 0
         * salaryType : 0
         * title : string
         */

        private String applyEndTime;
        private int applyNumber;
        private int frontendFlag;
        private String frontendFlagText;
        private String gatheringAddress;
        private String gatheringTime;
        private int hireNumber;
        private int id;
        private String labels;
        private int publisher;
        private int recruitNumber;
        private int recruitmentState;
        private String recruitmentStateText;
        private String releaseTime;
        private int salary;
        private int salaryType;
        private String title;
        private List<String> labelList;

        protected ContentBean(Parcel in) {
            applyEndTime = in.readString();
            applyNumber = in.readInt();
            frontendFlag = in.readInt();
            frontendFlagText = in.readString();
            gatheringAddress = in.readString();
            gatheringTime = in.readString();
            hireNumber = in.readInt();
            id = in.readInt();
            labels = in.readString();
            publisher = in.readInt();
            recruitNumber = in.readInt();
            recruitmentState = in.readInt();
            recruitmentStateText = in.readString();
            releaseTime = in.readString();
            salary = in.readInt();
            salaryType = in.readInt();
            title = in.readString();
            labelList = in.createStringArrayList();
        }

        public static final Creator<ContentBean> CREATOR = new Creator<ContentBean>() {
            @Override
            public ContentBean createFromParcel(Parcel in) {
                return new ContentBean(in);
            }

            @Override
            public ContentBean[] newArray(int size) {
                return new ContentBean[size];
            }
        };

        public String getApplyEndTime() {
            return applyEndTime;
        }

        public void setApplyEndTime(String applyEndTime) {
            this.applyEndTime = applyEndTime;
        }

        public int getApplyNumber() {
            return applyNumber;
        }

        public void setApplyNumber(int applyNumber) {
            this.applyNumber = applyNumber;
        }

        public int getFrontendFlag() {
            return frontendFlag;
        }

        public void setFrontendFlag(int frontendFlag) {
            this.frontendFlag = frontendFlag;
        }

        public String getFrontendFlagText() {
            return frontendFlagText;
        }

        public void setFrontendFlagText(String frontendFlagText) {
            this.frontendFlagText = frontendFlagText;
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

        public int getHireNumber() {
            return hireNumber;
        }

        public void setHireNumber(int hireNumber) {
            this.hireNumber = hireNumber;
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

        public int getPublisher() {
            return publisher;
        }

        public void setPublisher(int publisher) {
            this.publisher = publisher;
        }

        public int getRecruitNumber() {
            return recruitNumber;
        }

        public void setRecruitNumber(int recruitNumber) {
            this.recruitNumber = recruitNumber;
        }

        public int getRecruitmentState() {
            return recruitmentState;
        }

        public void setRecruitmentState(int recruitmentState) {
            this.recruitmentState = recruitmentState;
        }

        public String getRecruitmentStateText() {
            return recruitmentStateText;
        }

        public void setRecruitmentStateText(String recruitmentStateText) {
            this.recruitmentStateText = recruitmentStateText;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
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

        public List<String> getLabelList() {
            return labelList;
        }

        public void setLabelList(List<String> labelList) {
            this.labelList = labelList;
        }

        @Override
        public int getItemType() {
            return 0;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(applyEndTime);
            dest.writeInt(applyNumber);
            dest.writeInt(frontendFlag);
            dest.writeString(frontendFlagText);
            dest.writeString(gatheringAddress);
            dest.writeString(gatheringTime);
            dest.writeInt(hireNumber);
            dest.writeInt(id);
            dest.writeString(labels);
            dest.writeInt(publisher);
            dest.writeInt(recruitNumber);
            dest.writeInt(recruitmentState);
            dest.writeString(recruitmentStateText);
            dest.writeString(releaseTime);
            dest.writeInt(salary);
            dest.writeInt(salaryType);
            dest.writeString(title);
            dest.writeStringList(labelList);
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
