package com.ivp.xch.map.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ivp.xch.base.BaseEntity;

/**
 * 路段名称
 * 
 * @author hcx
 *
 */
@Entity
@Table(name = "TROAD_SECTION")
public class RoadSection extends BaseEntity {

    // 路段名称
    private String rName;

    // 路段编号
    private String rCode;

    // 每条路上的所有的点
   // @JsonBackReference
    @OneToMany(mappedBy = "roadSection", fetch = FetchType.LAZY)
    private List<FieldPoint> fpoints;


    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrCode() {
        return rCode;
    }

    public void setrCode(String rCode) {
        this.rCode = rCode;
    }

    public List<FieldPoint> getFpoints() {
        return fpoints;
    }

    public void setFpoints(List<FieldPoint> fpoints) {
        this.fpoints = fpoints;
    }



}
