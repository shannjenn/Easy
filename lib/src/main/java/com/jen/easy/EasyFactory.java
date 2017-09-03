package com.jen.easy;

import com.jen.easy.http.HttpParam;

/**
 * 参数类
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public final class EasyFactory {
    private EasyFactory() {
    }

    /*****************************************************************************************************
     ★★★ 网络请求 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
     ****************************************************************************************************/
    public static final class HTTP {
        private HTTP() {
        }

        /**
         * 基本请求
         */
        public static abstract class BaseParamRequest extends HttpParam {
            private EasyListener.HTTP.BaseListener bseListener;
            public Request request = new Request();

            public final class Request {
                /**
                 * 请求标识
                 */
                public int flagCode;

                /**
                 * 请求标识
                 */
                public String flag;

                /**
                 * 返回实体
                 */
                public Class resopseBaseClass;
                /**
                 * 返回实体中resopseBaseClass的对象
                 */
                public Class resopseBaseClassObject;

                /**
                 * 用户停止
                 */
                public boolean userCancel;

            }

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

        public static abstract class DownloadParamRequest extends HttpParam {
            private EasyListener.HTTP.DownloadListener downloadListener;
            public Request request = new Request();

            public final class Request {
                /**
                 * 请求标识
                 */
                public int flagCode;

                /**
                 * 请求标识
                 */
                public String flag;

                /**
                 * 用户停止
                 */
                public boolean userCancel;


                /**
                 * 上传/下载文件位置
                 */
                public String filePath;
                /**
                 * 开始位置
                 */
                public long startPoit;
                /**
                 * 结束位置
                 */
                public long endPoit;

                /**
                 * 是否断点下载
                 */
                public boolean isBreak;

                /**
                 * 是否在下载删除旧文件
                 */
                public boolean deleteOldFile;
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

        public static abstract class UploadParamRequest extends HttpParam {
            private EasyListener.HTTP.UploadListener uploadListener;
            public Request request = new Request();

            public final class Request {
                /**
                 * 请求标识
                 */
                public int flagCode;

                /**
                 * 请求标识
                 */
                public String flag;

                /**
                 * 用户停止
                 */
                public boolean userCancel;

                /**
                 * 返回实体
                 */
                public Class resopseBaseClass;

                /**
                 * 返回实体中的对象
                 */
                public Class resopseBaseClassObject;


                /**
                 * 上传/下载文件位置
                 */
                public String filePath;
                /**
                 * 开始位置
                 */
                public long startPoit;
                /**
                 * 结束位置
                 */
                public long endPoit;

                /**
                 * 是否断点下载
                 */
                public boolean isBreak;
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
