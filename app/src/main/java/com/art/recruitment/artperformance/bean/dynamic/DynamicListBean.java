package com.art.recruitment.artperformance.bean.dynamic;

import com.art.recruitment.common.base.BaseBean;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DynamicListBean extends BaseBean<DynamicListBean.DataBean> {

    /**
     * data : {"content":[{"commentNumber":0,"content":"string","createTime":"2019-05-20T07:26:07.219Z","id":0,"imagePath":["string"],"isLikes":true,"likes":0,"publisher":0,"publisherAvatar":"string","publisherName":"string","timeAgo":"string","videoPath":"string","videoPreview":"string"}],"empty":true,"first":true,"last":true,"number":0,"numberOfElements":0,"pageable":{"page":0,"size":0,"sort":"string"},"size":0,"sort":{"empty":true,"sorted":true,"unsorted":true},"totalElements":0,"totalPages":0}
     * fieldErrs : [{"error":"string","field":"string"}]
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
         * content : [{"commentNumber":0,"content":"string","createTime":"2019-05-20T07:26:07.219Z","id":0,"imagePath":["string"],"isLikes":true,"likes":0,"publisher":0,"publisherAvatar":"string","publisherName":"string","timeAgo":"string","videoPath":"string","videoPreview":"string"}]
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
         * commentNumber : 0
         * content : string
         * createTime : 2019-05-20T07:26:07.219Z
         * id : 0
         * imagePath : ["string"]
         * isLikes : true
         * likes : 0
         * publisher : 0
         * publisherAvatar : string
         * publisherName : string
         * timeAgo : string
         * videoPath : string
         * videoPreview : string
         */

        private int commentNumber;
        private String content;
        private String createTime;
        private int id;
        private boolean isLikes;
        private int likes;
        private int publisher;
        private String publisherAvatar;
        private String publisherName;
        private String timeAgo;
        private String videoPath;
        private String videoPreview;
        private List<String> imagePath;

        public int getCommentNumber() {
            return commentNumber;
        }

        public void setCommentNumber(int commentNumber) {
            this.commentNumber = commentNumber;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public int getPublisher() {
            return publisher;
        }

        public void setPublisher(int publisher) {
            this.publisher = publisher;
        }

        public String getPublisherAvatar() {
            return publisherAvatar;
        }

        public void setPublisherAvatar(String publisherAvatar) {
            this.publisherAvatar = publisherAvatar;
        }

        public String getPublisherName() {
            return publisherName;
        }

        public void setPublisherName(String publisherName) {
            this.publisherName = publisherName;
        }

        public String getTimeAgo() {
            return timeAgo;
        }

        public void setTimeAgo(String timeAgo) {
            this.timeAgo = timeAgo;
        }

        public String getVideoPath() {
            return videoPath;
        }

        public void setVideoPath(String videoPath) {
            this.videoPath = videoPath;
        }

        public String getVideoPreview() {
            return videoPreview;
        }

        public void setVideoPreview(String videoPreview) {
            this.videoPreview = videoPreview;
        }

        public List<String> getImagePath() {
            return imagePath;
        }

        public void setImagePath(List<String> imagePath) {
            this.imagePath = imagePath;
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
