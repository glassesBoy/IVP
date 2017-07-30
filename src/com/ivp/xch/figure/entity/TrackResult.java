package com.ivp.xch.figure.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ivp.xch.base.BaseEntity;

/**
 * 测试结果
 * 
 * @author hcx
 * 
 */
@Entity
@Table(name = "TResult")
public class TrackResult extends BaseEntity {

    //
    private Long testingVehicleID;

    private String vName;


    //
    private Long testedTimes;

    // 说明
    private float score;

    private String notes;

    public Long getTestingVehicleID() {
        return testingVehicleID;
    }

    public void setTestingVehicleID(Long testingVehicleID) {
        this.testingVehicleID = testingVehicleID;
    }

    public Long getTestedTimes() {
        return testedTimes;
    }

    public void setTestedTimes(Long testedTimes) {
        this.testedTimes = testedTimes;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }



}
