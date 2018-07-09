package cn.deskie.sysentity.entity;

import cn.deskie.syscommon.excel.annotation.ExcelField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HouseDetail implements Serializable  {

    private static final long serialVersionUID = 6254901575286901129L;

    private String id;

    private String projectId;

    private String projectName;

    private String buindNo;

    private String houseNo;

    private BigDecimal area;

    private BigDecimal price;

    private BigDecimal total;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuindNo() {
        return buindNo;
    }

    public void setBuindNo(String buindNo) {
        this.buindNo = buindNo;
    }
    @ExcelField(title="房号",align=2, sort=2)
    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }
    @ExcelField(title="建筑面积（平方米）",align=2, sort=3)
    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }
    @ExcelField(title="单价（元/平方米）",align=2, sort=4)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @ExcelField(title="房屋总价（元）",align=2, sort=5)
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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