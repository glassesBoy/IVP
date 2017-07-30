package com.ivp.xch.map;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.ivp.xch.base.BaseEntity;
import com.ivp.xch.map.entity.RoadSection;

/**
 * 
 * @author hcx
 *
 */
@MappedSuperclass
public class GPSPoint extends BaseEntity {

    // 经度
    private float longitude;
    
    // 维度
    private float latitude;

   
    

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }


//    public String getRoadSectionCode() {
//        return roadSectionCode;
//    }
//
//    public void setRoadSectionCode(String roadSectionCode) {
//        this.roadSectionCode = roadSectionCode;
//    }



}
