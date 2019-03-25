package app.xtoolwallpaper.com.myapplication.base.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * 网络错误信息类
 *
 * @author Zhangzhen
 */
class ApiException {

    /**
     * 数据错误
     */
    static class API extends RuntimeException {

        private String code, msg;

        /**
         * 接口返回的错误
         *
         * @param code 返回的代码
         * @param msg  返回的内容
         */
        API(String code, String msg) {
            super(getErrorDesc(code, msg));
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getLocalizedMessage() {
            return msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    /**
     * 通讯错误
     */
    static class IO extends IOException {
        IO(String code, String msg) {
            super(msg);
        }
    }

    /**
     * 登录错误
     */
    static class LOGIN extends RuntimeException {
        LOGIN(String code, String msg) {
            super(msg);
        }
    }

    /**
     * 获取问题描述
     *
     * @param code 返回的状态吗
     * @param msg  返回的信息
     * @return 问题描述
     */
    private static String getErrorDesc(String code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }
}

