package com.ivp.xch.figure.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ivp.xch.base.BaseEntity;

/**
 * 车辆信息
 * 
 * @author hcx
 *
 */
@Entity
@Table(name = "TVEHICLE")
public class Vehicle extends BaseEntity {

    /**
     * 车辆所在坐标，实时的话，应该在轨迹表中记录
     */
    // private Point point;

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

    // 车体高度 现在应该还用不到
    private float vHeight;

  

    @ManyToOne(fetch = FetchType.LAZY)
    private VehicleTeam team;


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

    public float getvHeight() {
        return vHeight;
    }

    public void setvHeight(float vHeight) {
        this.vHeight = vHeight;
    }

    public VehicleTeam getTeam() {
        return team;
    }

    public void setTeam(VehicleTeam team) {
        this.team = team;
    }


}
