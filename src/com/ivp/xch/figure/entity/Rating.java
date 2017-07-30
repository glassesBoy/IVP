package com.ivp.xch.figure.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ivp.xch.base.BaseEntity;

/**
 * 打分标准
 * 
 * @author hcx
 * 
 */
@Entity
@Table(name = "TRATING")
public class Rating extends BaseEntity {

    // 评分名称
    private String ratingName;

    // 分值
    private float ratingValue;

    // 说明
    private String notes;

    public String getRatingName() {
        return ratingName;
    }

    public void setRatingName(String ratingName) {
        this.ratingName = ratingName;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }



}
