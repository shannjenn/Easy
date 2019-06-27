package easybase;

/**
 * @author Created by zs on 2018/10/22.
 */
public class BaseResponse {
    private int code;

    private String message;
    private String updateMsg;
    private String id;
    private String formatStr;

    public static class Res {
        private String respCode;
        private String respMsg;

        public String getRespCode() {
            return respCode == null ? "" : respCode;
        }

        public void setRespCode(String respCode) {
            this.respCode = respCode;
        }

        public String getRespMsg() {
            return respMsg == null ? "" : respMsg;
        }

        public void setRespMsg(String respMsg) {
            this.respMsg = respMsg;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpdateMsg() {
        return updateMsg == null ? "" : updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormatStr() {
        return formatStr == null ? "" : formatStr;
    }

    public void setFormatStr(String formatStr) {
        this.formatStr = formatStr;
    }
}
