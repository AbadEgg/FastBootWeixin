package com.mlnx.device_server.domain.result;

public class ResponseData extends Response{
    private Object obj;

    public ResponseData(Object obj) {
        this.obj = obj;
    }
    
    public ResponseData(ExceptionMsg msg) {
    	  super(msg);
    }
    
    public ResponseData(String rspCode, String rspMsg) {
        super(rspCode, rspMsg);
    }

    public ResponseData(String rspCode, String rspMsg, Object obj) {
        super(rspCode, rspMsg);
        this.obj = obj;
    }

    public ResponseData(ExceptionMsg msg, Object obj) {
        super(msg);
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "obj=" + obj +
                "} " + super.toString();
    }
}
