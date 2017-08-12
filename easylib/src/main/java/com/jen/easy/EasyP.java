package com.jen.easy;

import com.jen.easy.http.param.factory.FinalDownloadParam;
import com.jen.easy.http.param.factory.FinalUploadParam;
import com.jen.easy.http.param.factory.ParamFather;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyP {
    /**
     * 网络请求********************
     */
    public static abstract class HTTP {
        /**
         * 基本请求
         */
        public static abstract class BaseParam extends ParamFather {

            private EasyL.HTTP.BaseListener bseListener;

            public EasyL.HTTP.BaseListener getBseListener() {
                return bseListener;
            }

            public void setBseListener(EasyL.HTTP.BaseListener bseListener) {
                this.bseListener = bseListener;
            }
        }

        /**
         * 下载参数
         */

        public static abstract class DownloadParam extends ParamFather {

            private EasyL.HTTP.DownloadListener downloadListener;
            public FinalDownloadParam fileParam;

            public DownloadParam() {
                fileParam = new FinalDownloadParam();
            }

            public EasyL.HTTP.DownloadListener getDownloadListener() {
                return downloadListener;
            }

            public void setDownloadListener(EasyL.HTTP.DownloadListener downloadListener) {
                this.downloadListener = downloadListener;
            }
        }

        /**
         * 上传参数
         */

        public static abstract class UploadParam extends ParamFather {

            private EasyL.HTTP.UploadListener uploadListener;
            public FinalUploadParam fileParam;

            public UploadParam() {
                fileParam = new FinalUploadParam();
            }

            public EasyL.HTTP.UploadListener getUploadListener() {
                return uploadListener;
            }

            public void setEasyHttpUploadFileListener(EasyL.HTTP.UploadListener uploadListener) {
                this.uploadListener = uploadListener;
            }
        }
    }

}
