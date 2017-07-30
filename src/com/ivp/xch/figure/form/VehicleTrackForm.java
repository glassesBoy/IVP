package com.ivp.xch.figure.form;

/**
 * 注意 这个类用来组装 vehicle_track 和vehicle表的信息
 * 
 * @author hcx
 *
 */
public class VehicleTrackForm {

    private Long vehicleID;

    private Long testingVehicleID;

    // 车编号
    private String vCode;
    private String vName;


    // 车队名称
    private String teamName;

    // 车体长
    private float vLength;

    // 车体宽
    private float vWidth;

    private Long gpsEID;

    private String gpsEName;

    private String gpsECode;


    private Long testedTimes;

    private Long testedTotalTimes;

    private String startTrackTime;

    private String stopTrackTime;

    public Long getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Long vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Long getTestingVehicleID() {
        return testingVehicleID;
    }

    public void setTestingVehicleID(Long testingVehicleID) {
        this.testingVehicleID = testingVehicleID;
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public float getvLength() {
        return vLength;
    }

    public void setvLength(float vLength) {
        this.vLength = vLength;
    }

    public float getvWidth() {
        return vWidth;
    }

    public void setvWidth(float vWidth) {
        this.vWidth = vWidth;
    }

    public Long getTestedTimes() {
        return testedTimes;
    }

    public void setTestedTimes(Long testedTimes) {
        this.testedTimes = testedTimes;
    }

    public Long getGpsEID() {
        return gpsEID;
    }

    public void setGpsEID(Long gpsEID) {
        this.gpsEID = gpsEID;
    }

    public String getGpsEName() {
        return gpsEName;
    }

    public void setGpsEName(String gpsEName) {
        this.gpsEName = gpsEName;
    }

    public String getGpsECode() {
        return gpsECode;
    }

    public void setGpsECode(String gpsECode) {
        this.gpsECode = gpsECode;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getStartTrackTime() {
        return startTrackTime;
    }

    public void setStartTrackTime(String startTrackTime) {
        this.startTrackTime = startTrackTime;
    }

    public String getStopTrackTime() {
        return stopTrackTime;
    }

    public void setStopTrackTime(String stopTrackTime) {
        this.stopTrackTime = stopTrackTime;
    }

    public Long getTestedTotalTimes() {
        return testedTotalTimes;
    }

    public void setTestedTotalTimes(Long testedTotalTimes) {
        this.testedTotalTimes = testedTotalTimes;
    }



}
