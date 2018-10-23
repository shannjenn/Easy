package com.jen.easytest.http.request;

import com.jen.easy.Easy;
import com.jen.easytest.http.response.TaskProgressListResponse;

import java.io.Serializable;

/**
 * Created by zs on 2018/5/14.
 * 任务进展接口 请求参数
 */
@Easy.HTTP.GET(URLBASE = "http://apitest.zte.com.cn:8888/api/zte-km-jps-task/v1/progress/list/{task_id}/{page}/{limit}", Response = TaskProgressListResponse.class)
public class TaskProgressListRequest extends TaskHttpBaseRequest implements Serializable {

    /*任务编号*/
    @Easy.HTTP.RequestParam(value = "task_id", type = Easy.HTTP.TYPE.URL)
    private int taskId;
    /*任务编号*/
    @Easy.HTTP.RequestParam(value = "page", type = Easy.HTTP.TYPE.URL)
    private int page;
    /*任务编号*/
    @Easy.HTTP.RequestParam(value = "limit", type = Easy.HTTP.TYPE.URL)
    private int limit;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
