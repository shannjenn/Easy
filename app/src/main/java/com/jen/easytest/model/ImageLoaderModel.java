package com.jen.easytest.model;

import android.view.View;

import com.jen.easy.Easy;

/**
 * Created by Administrator on 2018/3/2.
 */
@Easy.DB.Table("ImageLoaderModel")
public class ImageLoaderModel {
    @Easy.HTTP.ResponseParam("aid")
    private String aid;// 文章编号

    @Easy.HTTP.ResponseParam("catid")
    private String catid;//分类编号

    @Easy.HTTP.ResponseParam("title")
    private String title;// 标题

    @Easy.HTTP.ResponseParam("summary")
    private String summary;// 摘要

    @Easy.HTTP.ResponseParam("pic")
    private String pic;//图片

    @Easy.HTTP.ResponseParam("dateline")
    private String dateline;// 发布时间戳
    @Easy.HTTP.ResponseParam("movieid")
    private String movieid;

    /**
     * 0正常数据，1轮播一，2轮播二
     */
    @Easy.HTTP.ResponseParam(noResp = true)
    @Easy.DB.Column(noColumn = true)
    private int viewType;
    @Easy.HTTP.ResponseParam(noResp = true)
    @Easy.DB.Column(noColumn = true)
    private int viewSize = 1;

    @Easy.HTTP.ResponseParam(noResp = true)
    @Easy.DB.Column(noColumn = true)
    int bitTitleVisible = View.GONE;//显示大标题
    @Easy.HTTP.ResponseParam(noResp = true)
    @Easy.DB.Column(noColumn = true)
    boolean showEnter;//显示进入
    @Easy.HTTP.ResponseParam(noResp = true)
    @Easy.DB.Column(noColumn = true)
    boolean showChange;//显示晃一晃


    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getMovieid() {
        return movieid;
    }

    public void setMovieid(String movieid) {
        this.movieid = movieid;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getViewSize() {
        return viewSize;
    }

    public void setViewSize(int viewSize) {
        this.viewSize = viewSize;
    }

    public boolean isShowEnter() {
        return showEnter;
    }

    public void setShowEnter(boolean showEnter) {
        this.showEnter = showEnter;
    }

    public boolean isShowChange() {
        return showChange;
    }

    public void setShowChange(boolean showChange) {
        this.showChange = showChange;
    }

    public int getBitTitleVisible() {
        return bitTitleVisible;
    }

    public void setBitTitleVisible(int bitTitleVisible) {
        this.bitTitleVisible = bitTitleVisible;
    }
}
