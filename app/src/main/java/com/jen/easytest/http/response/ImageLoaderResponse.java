package com.jen.easytest.http.response;

import com.jen.easy.EasyMouse;
import com.jen.easytest.model.ImageLoaderModel;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/12/5:22:12
 * 说明：首页精选返回参数
 * {{"00","359","35","230","116",
 * "118","231","346","117",
 * "243","240","343","321",
 * "44","42","43","224",
 * "268","269","271","270"},
 * {"精选","财经原创","外汇视频","大宗商品","股灵经怪",
 * "金融学院","慧眼识陷阱","理财叨叨叨","金融小百科",
 * "财经百味","热点狙击","企业宣传","个人宣传",
 * "汇誉团队","精英  专访","机构风采","汇誉大电影",
 * "港股指数直击","牛股火线快评","港股午盘直击","港股早盘直击"}};
 */

public class ImageLoaderResponse {
    private ImageLoaderResponse.Data data;

    public static class Data {
        String total;
        String per_page;
        String current_page;
        String last_page;
        String next_page_url;
        String prev_page_url;
        String from;
        String to;

        @EasyMouse.HTTP.ResponseParam("data")
        private List<ImageLoaderModel> data2;



     /*※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
     ❀❀❀❀❀❀❀❀❀❀❀下面是getter、setter❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀
     ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※*/

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public String getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(String current_page) {
            this.current_page = current_page;
        }

        public String getLast_page() {
            return last_page;
        }

        public void setLast_page(String last_page) {
            this.last_page = last_page;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(String prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public List<ImageLoaderModel> getData2() {
            return data2;
        }

        public void setData2(List<ImageLoaderModel> data2) {
            this.data2 = data2;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
