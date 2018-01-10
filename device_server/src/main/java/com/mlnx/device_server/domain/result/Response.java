package com.mlnx.device_server.domain.result;

public class Response {
    /**
     * 返回信息码
     */
    private String responseCode = "0000";
    /**
     * 返回信息内容
     */
    private String msg = "操作成功";

    public Response() {
    }

    public Response(ExceptionMsg msg) {
        this.responseCode = msg.getCode();
        this.msg = msg.getMsg();
    }

    public Response(String responseCode) {
        this.responseCode = responseCode;
        this.msg = "";
    }

    public Response(String responseCode, String msg) {
        this.responseCode = responseCode;
        this.msg = msg;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseCode='" + responseCode + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
