package com.qxb.student.common.module.bean;

import java.util.List;

/**
 * @author winky
 * @date 2018/9/4
 */
public class SchoolCondition {

    private List<CollegeBatch> bats;
    private List<CollegeProvinces> provinces;
    private List<CollegeTag> tags;

    public List<CollegeBatch> getBats() {
        return bats;
    }

    public void setBats(List<CollegeBatch> bats) {
        this.bats = bats;
    }

    public List<CollegeProvinces> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<CollegeProvinces> provinces) {
        this.provinces = provinces;
    }

    public List<CollegeTag> getTags() {
        return tags;
    }

    public void setTags(List<CollegeTag> tags) {
        this.tags = tags;
    }
}
