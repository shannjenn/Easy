package com.jen.easytest.request;

import com.jen.easy.EasyResponse;
import com.jen.easy.invalid.EasyInvalid;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统基本参数 返回 默认
 *
 * @author Created by zs on 2018/10/22.
 */
public class SystemParamResponse extends BaseResponse {
    private Result result;

    public static class Result extends Res {
        @EasyResponse("info")
        private List<Label> label;

        public List<Label> getLabel() {
            if (label == null) {
                return new ArrayList<>();
            }
            return label;
        }

        public void setLebel(List<Label> labels) {
            this.label = labels;
        }
    }

    public static class Label {
        private String code;
        private String label;
        @EasyResponse("sublabel")
        private List<SubLabel> subLabel;

        @EasyResponse("info")
        private List<SubLabel> subInfo;

        public static class SubLabel {
            private String code;
            private String label;
            @EasyResponse("sublabel")
            private List<SubSubLabel> subSubLabel;

            @EasyInvalid
            int type;
            @EasyInvalid
            int position;

            public static class SubSubLabel {
                private String code;
                private String label;

                public String getCode() {
                    return code == null ? "" : code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getLabel() {
                    return label == null ? "" : label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public String getCode() {
                return code == null ? "" : code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getLabel() {
                return label == null ? "" : label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public List<SubSubLabel> getSubSubLabel() {
                if (subSubLabel == null) {
                    return new ArrayList<>();
                }
                return subSubLabel;
            }

            public void setSubSubLabel(List<SubSubLabel> subSubLabel) {
                this.subSubLabel = subSubLabel;
            }
        }

        public String getCode() {
            return code == null ? "" : code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLabel() {
            return label == null ? "" : label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<SubLabel> getSubLabel() {
            if (subLabel == null) {
                return new ArrayList<>();
            }
            return subLabel;
        }

        public void setSubLabel(List<SubLabel> subLabel) {
            this.subLabel = subLabel;
        }

        public List<SubLabel> getSubInfo() {
            if (subInfo == null) {
                return new ArrayList<>();
            }
            return subInfo;
        }

        public void setSubInfo(List<SubLabel> subInfo) {
            this.subInfo = subInfo;
        }
    }

    public Result getResult() {
        if (result == null) {
            result = new Result();
        }
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
