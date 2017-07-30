package com.ivp.xch.figure.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ivp.xch.base.BaseEntity;

/**
 * GPS设备
 * 
 * @author hcx
 * 
 */
@Entity
@Table(name = "TEQUIPMENT")
public class GPSEquipment extends BaseEntity {

    // 设备名称
    private String eName;

    // 设备编号
    private String eCode;

    // 说明
    private String notes;



    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String geteCode() {
        return eCode;
    }

    public void seteCode(String eCode) {
        this.eCode = eCode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }



}
