package com.ivp.xch.figure.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ivp.xch.base.BaseEntity;

/**
 * 障碍物 包括模拟人
 * 
 * @author hcx
 * 
 */
@Entity
@Table(name = "TOBSTACLE")
public class Obstacle extends BaseEntity {

    // 经度 longitude
    // 小车坐标如果是GPS坐标，那么x 就是 经度
    private double x;

    // 维度 latitude
    private double y;

    // 障碍物名称
    private String oName;

    // 说明
    private String notes;


    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}
