package com.example.safeentry;

public class Hospital {
    private String name;
    private String telNum;
    private int emergencyRoomCount; // 응급실
    private String code; // 기관 코드
    private int hospitalRoomCount; // 입원실

    public Hospital() {

    }
    public Hospital(String name, String telNum, int emergencyRoomCount, String code, int hospitalRoom) {
        this.name = name;
        this.telNum = telNum;
        this.emergencyRoomCount = emergencyRoomCount;
        this.code = code;
        this.hospitalRoomCount = hospitalRoom;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTelNum() {
        return telNum;
    }
    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }
    public int getEmergencyRoomCount() {
        return emergencyRoomCount;
    }
    public void setEmergencyRoomCount(int count) {
        this.emergencyRoomCount = count;
    }
    public String getCode() {
        return code;
    }
    public void setCode (String code) {
        this.code = code;
    }
    public int getHospitalRoomCount() {
        return hospitalRoomCount;
    }
    public void setHospitalRoomCount(int count) {
        this.hospitalRoomCount = count;
    }
}
