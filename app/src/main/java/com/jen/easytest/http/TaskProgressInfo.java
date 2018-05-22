package com.jen.easytest.http;


import com.jen.easy.Easy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pj on 2017/9/16.
 * 任务进展信息
 */

public class TaskProgressInfo implements Serializable {
    /**
     * 进展ID
     */
    private int id;

    /**
     * 项目ID
     */
    @Easy.HTTP.ResponseParam("project_id")
    private String projectId;

    /**
     * 任务ID
     */
    @Easy.HTTP.ResponseParam("task_id")
    private int taskId;

    /**
     * 进展状态
     * 状态1=绿灯，2=黄灯，3=红灯
     * 使用详情外面的light_status,该值只用传递
     */
    private int status;

    /**
     * 进度%
     */
    @Easy.HTTP.ResponseParam("current_rate")
    private int currentRate;

    /*使用详情外面的current_value,该值只用传递*/
    @Easy.HTTP.ResponseParam("current_value")
    private int currentValue;

    /**
     * 进展内容
     */
    private String remark;

    /**
     * 点赞数
     */
    @Easy.HTTP.ResponseParam("praise_num")
    private int praiseNum;

    /**
     * 是否点过赞
     */
    @Easy.HTTP.ResponseParam("is_praised")
    private int isPraised;

    /**
     * 是否点过踩
     */
    @Easy.HTTP.ResponseParam("is_opposed")
    private int isOpposed;

    /**
     * 创建时间
     */
    @Easy.HTTP.ResponseParam("create_time")
    private long createTime;

    /**
     * 创建人
     */
    @Easy.HTTP.ResponseParam("creator")
    private TaskUserInfo creator;
    /**
     * 创建人
     */
    @Easy.HTTP.ResponseParam("reply_list")
    private List<TaskProgressInfo> replys;

    /**
     * 回复
     */
    /*public static class Reply {
        private int id;

        @Easy.HTTP.ResponseParam("parent_id")
        private int parentId;

        @Easy.HTTP.ResponseParam("progress_id")
        private int progressId;

        @Easy.HTTP.ResponseParam("task_id")
        private int taskId;

        private String content;

        @Easy.HTTP.ResponseParam("reply_list")
        private String createTime;

        @Easy.HTTP.ResponseParam("creator")
        private TaskUserInfo creator;

        @Easy.HTTP.ResponseParam("users")
        private List<String> users;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getProgressId() {
            return progressId;
        }

        public void setProgressId(int progressId) {
            this.progressId = progressId;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
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

        public TaskUserInfo getCreator() {
            return creator;
        }

        public void setCreator(TaskUserInfo creator) {
            this.creator = creator;
        }

        public List<String> getUsers() {
            return users;
        }

        public void setUsers(List<String> users) {
            this.users = users;
        }
    }*/


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(int currentRate) {
        this.currentRate = currentRate;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getIsPraised() {
        return isPraised;
    }

    public void setIsPraised(int isPraised) {
        this.isPraised = isPraised;
    }

    public int getIsOpposed() {
        return isOpposed;
    }

    public void setIsOpposed(int isOpposed) {
        this.isOpposed = isOpposed;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public TaskUserInfo getCreator() {
        return creator;
    }

    public void setCreator(TaskUserInfo creator) {
        this.creator = creator;
    }

    public List<TaskProgressInfo> getReplys() {
        return replys;
    }

    public void setReplys(List<TaskProgressInfo> replys) {
        this.replys = replys;
    }
}
