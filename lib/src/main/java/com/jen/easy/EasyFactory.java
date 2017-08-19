package com.jen.easy;

import com.jen.easy.http.param.factory.FinalDownloadParam;
import com.jen.easy.http.param.factory.FinalUploadParam;
import com.jen.easy.http.param.factory.ParamFather;

/**
 * 参数类
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public final class EasyFactory {
    private EasyFactory() {
    }

    /**
     * 网络请求********************
     */
    public static final class HTTP {
        private HTTP() {
        }

        /**
         * 基本请求
         */
        public static abstract class BaseParam extends ParamFather {

            private EasyListener.HTTP.BaseListener bseListener;

            public EasyListener.HTTP.BaseListener getBseListener() {
                return bseListener;
            }

            public void setBseListener(EasyListener.HTTP.BaseListener bseListener) {
                this.bseListener = bseListener;
            }
        }

        /**
         * 下载参数
         */

        public static abstract class DownloadParam extends ParamFather {

            private EasyListener.HTTP.DownloadListener downloadListener;
            public FinalDownloadParam fileParam;

            public DownloadParam() {
                fileParam = new FinalDownloadParam();
            }

            public EasyListener.HTTP.DownloadListener getDownloadListener() {
                return downloadListener;
            }

            public void setDownloadListener(EasyListener.HTTP.DownloadListener downloadListener) {
                this.downloadListener = downloadListener;
            }
        }

        /**
         * 上传参数
         */

        public static abstract class UploadParam extends ParamFather {

            private EasyListener.HTTP.UploadListener uploadListener;
            public FinalUploadParam fileParam;

            public UploadParam() {
                fileParam = new FinalUploadParam();
            }

            public EasyListener.HTTP.UploadListener getUploadListener() {
                return uploadListener;
            }

            public void setEasyHttpUploadFileListener(EasyListener.HTTP.UploadListener uploadListener) {
                this.uploadListener = uploadListener;
            }
        }
    }

}
