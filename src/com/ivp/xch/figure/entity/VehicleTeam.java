package com.ivp.xch.figure.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ivp.xch.base.BaseEntity;

/**
 * 车队信息
 * 
 * @author hcx
 *
 */
@Entity
@Table(name = "TVEHICLE_TEAM")
public class VehicleTeam extends BaseEntity {



    // 车队名称
    private String teamName;

    // 车队编号
    private String teamCode;

    // 如果有 logo文件，那么记录
    private String logoPath;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<Vehicle> vList;


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public List<Vehicle> getvList() {
        return vList;
    }

    public void setvList(List<Vehicle> vList) {
        this.vList = vList;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }



}
