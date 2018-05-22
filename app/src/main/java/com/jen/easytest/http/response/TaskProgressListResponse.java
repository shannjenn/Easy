package com.jen.easytest.http.response;

import com.jen.easy.Easy;
import com.jen.easytest.http.TaskProgressInfo;

import java.util.List;

/**
 * Created by zs on 2018/5/14.
 * 任务进展接口 返回参数
 */
public class TaskProgressListResponse extends TaskHttpBaseResponse {

    @Easy.HTTP.ResponseParam("bo")
    List<TaskProgressInfo> taskProgressInfos;

    public List<TaskProgressInfo> getTaskProgressInfos() {
        return taskProgressInfos;
    }

    public void setTaskProgressInfos(List<TaskProgressInfo> taskProgressInfos) {
        this.taskProgressInfos = taskProgressInfos;
    }
}
