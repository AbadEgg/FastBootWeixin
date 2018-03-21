package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;

import java.nio.ByteBuffer;

/**
 * temp参数
 *
 * @author fzh
 * @create 2018/1/23 15:45
 */
public class TempData extends DataHeader{

    float t1;
    float t1HighLimt;
    float t1LowLimt;
    float t2;
    float t2HighLimt;
    float t2LowLimt;
    float td;
    float tdHighLimt;		//TD高限, 无低限

    public float getT1() {
        return t1;
    }

    public void setT1(float t1) {
        this.t1 = t1;
    }

    public float getT1HighLimt() {
        return t1HighLimt;
    }

    public void setT1HighLimt(float t1HighLimt) {
        this.t1HighLimt = t1HighLimt;
    }

    public float getT1LowLimt() {
        return t1LowLimt;
    }

    public void setT1LowLimt(float t1LowLimt) {
        this.t1LowLimt = t1LowLimt;
    }

    public float getT2() {
        return t2;
    }

    public void setT2(float t2) {
        this.t2 = t2;
    }

    public float getT2HighLimt() {
        return t2HighLimt;
    }

    public void setT2HighLimt(float t2HighLimt) {
        this.t2HighLimt = t2HighLimt;
    }

    public float getT2LowLimt() {
        return t2LowLimt;
    }

    public void setT2LowLimt(float t2LowLimt) {
        this.t2LowLimt = t2LowLimt;
    }

    public float getTd() {
        return td;
    }

    public void setTd(float td) {
        this.td = td;
    }

    public float getTdHighLimt() {
        return tdHighLimt;
    }

    public void setTdHighLimt(float tdHighLimt) {
        this.tdHighLimt = tdHighLimt;
    }

    public TempData() {
        packageType = PackageType.PACKAGE_TEMP;
    }

    @Override
    public String toString() {
        return "TempData{" +
                "t1=" + t1 +
                ", t1HighLimt=" + t1HighLimt +
                ", t1LowLimt=" + t1LowLimt +
                ", t2=" + t2 +
                ", t2HighLimt=" + t2HighLimt +
                ", t2LowLimt=" + t2LowLimt +
                ", td=" + td +
                ", tdHighLimt=" + tdHighLimt +
                '}';
    }

    @Override
    public void decodeData(ByteBuffer buf) {
        byte[] b4 = new byte[4];
        buf.get(b4);
        t1 = validate(ByteUtils.getFloat(b4));
        buf.get(b4);
        t1HighLimt = validate(ByteUtils.getFloat(b4));
        buf.get(b4);
        t1LowLimt = validate(ByteUtils.getFloat(b4));
        buf.get(b4);
        t2 = validate(ByteUtils.getFloat(b4));
        buf.get(b4);
        t2HighLimt = validate(ByteUtils.getFloat(b4));
        buf.get(b4);
        t2LowLimt = validate(ByteUtils.getFloat(b4));
        buf.get(b4);
        td = validate(ByteUtils.getFloat(b4));
        buf.get(b4);
        tdHighLimt = validate(ByteUtils.getFloat(b4));
//        System.out.println(this.toString());
    }

    public static float validate(float f){
        int invalid = -100;
        if(f != invalid){
            f /= 10;
        }
        return f;
    }
}
