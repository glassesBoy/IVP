package com.ivp.xch.map.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.ivp.xch.map.GPSPoint;


/**
 * 这些是 道路或者区块上面的点
 * 
 * @author hcx
 *
 */

@Entity
@Table(name = "TFIELD_POINT")
public class FieldPoint extends GPSPoint {


    // 路段编号
    // private String roadSectionCode;
    @ManyToOne(fetch = FetchType.LAZY)
    private RoadSection roadSection;


    public RoadSection getRoadSection() {
        return roadSection;
    }

    public void setRoadSection(RoadSection roadSection) {
        this.roadSection = roadSection;
    }
}
