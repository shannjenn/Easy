package com.jen.easytest.model;

import android.view.View;

import com.jen.easy.EasyResponse;
import com.jen.easy.EasyTable;

/**
 * Created by Administrator on 2018/3/2.
 */
@EasyTable("ImageLoaderModel")
public class ImageLoaderModel {

    @EasyResponse(value = "aid")
    private String aid;// 文章编号

    @EasyResponse("catid")
    private String catid;//分类编号

    @EasyResponse("title")
    private String title;// 标题

    @EasyResponse("summary")
    private String summary;// 摘要

    @EasyResponse("pic")
    private String pic;//图片

    @EasyResponse("dateline")
    private String dateline;// 发布时间戳
    @EasyResponse("movieid")
    private String movieid;

    /**
     * 0正常数据，1轮播一，2轮播二
     */

    private int viewType;
    private int viewSize = 1;

    int bitTitleVisible = View.GONE;//显示大标题
    boolean showEnter;//显示进入
    boolean showChange;//显示晃一晃


    public String getAid() {
        return aid == null ? "" : aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCatid() {
        return catid == null ? "" : catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary == null ? "" : summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPic() {
        return pic == null ? "" : pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDateline() {
        return dateline == null ? "" : dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getMovieid() {
        return movieid == null ? "" : movieid;
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

    public int getBitTitleVisible() {
        return bitTitleVisible;
    }

    public void setBitTitleVisible(int bitTitleVisible) {
        this.bitTitleVisible = bitTitleVisible;
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
}
