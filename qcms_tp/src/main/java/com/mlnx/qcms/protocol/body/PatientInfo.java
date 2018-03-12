package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;
import com.mlnx.qcms.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @author fzh
 * @create 2018/1/23 11:22
 */
public class PatientInfo extends DataHeader{

    private static final int PACKAGE_LENGTH = 172;

    boolean bNewPatient;		//是否是接收新病人？还是更改病人信息?
    String firstName;	//名字,utf8编码
    String lastName;		//名字,utf8编码
    PatientType patientType;		//病人类型
    String department;	//科室，utf8编码
    String bedNum;		//床号
    float weight;			//体重：公斤
    float height;			//高度，cm
    BloodType bloodType;			//血型
    SexType sexType;			//性别
    int bPace;				//pace标记
    int birYear;			//出生年
    int birMon;			//出生月
    int birDay;			//出生日
    int reserve;			//在数据库存储中，会使用此字段

    public boolean isbNewPatient() {
        return bNewPatient;
    }

    public void setbNewPatient(boolean bNewPatient) {
        this.bNewPatient = bNewPatient;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PatientType getPatientType() {
        return patientType;
    }

    public void setPatientType(PatientType patientType) {
        this.patientType = patientType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBedNum() {
        return bedNum;
    }

    public void setBedNum(String bedNum) {
        this.bedNum = bedNum;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public SexType getSexType() {
        return sexType;
    }

    public void setSexType(SexType sexType) {
        this.sexType = sexType;
    }

    public int isbPace() {
        return bPace;
    }

    public void setbPace(int bPace) {
        this.bPace = bPace;
    }

    public int getBirYear() {
        return birYear;
    }

    public void setBirYear(int birYear) {
        this.birYear = birYear;
    }

    public int getBirMon() {
        return birMon;
    }

    public void setBirMon(int birMon) {
        this.birMon = birMon;
    }

    public int getBirDay() {
        return birDay;
    }

    public void setBirDay(int birDay) {
        this.birDay = birDay;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    public PatientInfo() {
        packageType = PackageType.PACKAGE_USERINFO;
    }

    @Override
    public void decodeData(ByteBuffer byteBuffer) throws UnsupportedEncodingException {
        byte[] b2 = new byte[2];
        byteBuffer.get(b2);
        bNewPatient = ByteUtils.bytesToInt(b2,2)==0x01?true:false;

        byte[] b42 = new byte[42];
        byteBuffer.get(b42);
        firstName = StringUtils.bytesToString(b42);

        byteBuffer.get(b42);
        lastName = StringUtils.bytesToString(b42);

        byteBuffer.get(b2);
        int code = ByteUtils.bytesToInt(b2,2);
        patientType = PatientType.decode(code);

        byte[] b32 = new byte[32];
        byteBuffer.get(b32);
        department = StringUtils.bytesToString(b32);

        byteBuffer.get(b32);
        bedNum = StringUtils.bytesToString(b32);

        byte[] b4 = new byte[4];
        byteBuffer.get(b4);
        weight = Float.intBitsToFloat(ByteUtils.bytesToInt(b4));

        byteBuffer.get(b2);
        height = Float.intBitsToFloat(ByteUtils.bytesToInt(b2,2));

        bloodType = BloodType.decode(byteBuffer.get());

        sexType = SexType.decode(byteBuffer.get());

        byteBuffer.get(b4);
        bPace = ByteUtils.bytesToInt(b4);

        byteBuffer.get(b2);
        birYear = ByteUtils.bytesToSignInt(b2,2);

        birMon = byteBuffer.get();

        birDay = byteBuffer.get();

        byteBuffer.get(b4);
        reserve = ByteUtils.bytesToInt(b4);
    }
}
