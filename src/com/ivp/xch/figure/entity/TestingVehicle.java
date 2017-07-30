package com.ivp.xch.figure.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * 安装了 GPS设备的车辆
 * 测试中或待测试的车辆
 * 
 * @author hcx
 *
 */
@Entity
@Table(name = "TTESTING_VEHICLE")
public class TestingVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, precision = 10, scale = 0)
    protected Long id;


    // private Vehicle vehicle;

    private Long vehicleID;

    // 车辆名称
    private String vName;

    // 车编号
    private String vCode;

    // 车队名称
    private String teamName;

    // 车体长
    private float vLength;

    // 车体宽
    private float vWidth;


    /**
     * 当前车辆使用的 gps设备ID
     */
    // 其实这里还要有个GPS设备ID之类的
    private Long gpsEID;

    private String gpsEName;

    private String gpsECode;

    private boolean testing = false;

    /**
     * 已测试的次数
     */

    private Long testedTimes  = 0l;


    private Long obstacleID;

    private String oName;

    private double x;
    private double y;



    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "testingVehicle")
    private List<VehicleTrack> trackList;



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public Long getVehicleID() {
        return vehicleID;
    }

    public Long getvid() {
        return vehicleID;
    }



    public void setVehicleID(Long vehicleID) {
        this.vehicleID = vehicleID;
    }



    public String getvName() {
        return vName;
    }



    public void setvName(String vName) {
        this.vName = vName;
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



    public Long getGpsEID() {
        return gpsEID;
    }

    public Long getgpseid() {
        return gpsEID;
    }


    public void setGpsEID(Long gpsEID) {
        this.gpsEID = gpsEID;
    }



    public String getGpsEName() {
        return gpsEName;
    }

    public String geteName() {
        return gpsEName;
    }

    public void setGpsEName(String gpsEName) {
        this.gpsEName = gpsEName;
    }



    public String getGpsECode() {
        return gpsECode;
    }

    public String geteCode() {
        return gpsECode;
    }

    public void setGpsECode(String gpsECode) {
        this.gpsECode = gpsECode;
    }

    public Long getTestedTimes() {
        return testedTimes;
    }

    public void setTestedTimes(Long testedTimes) {
        this.testedTimes = testedTimes;
    }

    public List<VehicleTrack> getTrackList() {
        return trackList;
    }


    public void setTrackList(List<VehicleTrack> trackList) {
        this.trackList = trackList;
    }



    public boolean isTesting() {
        return testing;
    }



    public void setTesting(boolean testing) {
        this.testing = testing;
    }



    public Long getObstacleID() {
        return obstacleID;
    }

    public Long getoid() {
        return obstacleID;
    }

    public void setObstacleID(Long obstacleID) {
        this.obstacleID = obstacleID;
    }



    public String getoName() {
        return oName;
    }



    public void setoName(String oName) {
        this.oName = oName;
    }



    public double getX() {
        return x;
    }



    public void setX(double x) {
        this.x = x;
    }



    public double getY() {
        return y;
    }



    public void setY(double y) {
        this.y = y;
    }



}
