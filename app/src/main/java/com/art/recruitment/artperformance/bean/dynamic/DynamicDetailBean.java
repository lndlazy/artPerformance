package com.art.recruitment.artperformance.bean.dynamic;

import com.art.recruitment.common.base.BaseBean;

import java.util.List;

public class DynamicDetailBean extends BaseBean<DynamicDetailBean.DataBean> {

    /**
     * code : 0
     * data : {"canDelete":true,"commentNumber":0,"content":"string","createTime":"2019-05-20T07:12:10.924Z","id":0,"imagePath":["string"],"isLikes":true,"likes":0,"publisher":0,"publisherAvatar":"string","publisherName":"string","timeAgo":"string","videoPath":"string","videoPreview":"string"}
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
         * canDelete : true
         * commentNumber : 0
         * content : string
         * createTime : 2019-05-20T07:12:10.924Z
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

        private boolean canDelete;
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

        public boolean isCanDelete() {
            return canDelete;
        }

        public void setCanDelete(boolean canDelete) {
            this.canDelete = canDelete;
        }

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
