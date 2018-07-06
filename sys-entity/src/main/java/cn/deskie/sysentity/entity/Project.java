package cn.deskie.sysentity.entity;

import cn.deskie.syscommon.excel.annotation.ExcelField;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable {


    private static final long serialVersionUID = 5340493755278287063L;

    private String id;

    private String batchNo;

    private String batchId;

    private String projectName;

    private String developer;

    private String address;

    private String projectType;

    private String buildNo;

    private String areaNo;

    private Integer count;

    private Date publicTime;

    private Date addTime;

    private Date updateTime;
    @ExcelField(title="序号",type = 2,align=2, sort=1)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @ExcelField(title="项目名称", align=2, sort=2)
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    @ExcelField(title="开发企业", align=2, sort=3)
    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
    @ExcelField(title="项目地址", align=2, sort=4)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    @ExcelField(title="本次公示楼幢号", align=2, sort=5)
    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }
    @ExcelField(title="本次公示面积", align=2, sort=6)
    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }
    @ExcelField(title="本次公示总套数", align=2, sort=7)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Date publicTime) {
        this.publicTime = publicTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}