package com.hqj.common.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public abstract class BaseDTO {
    @ApiModelProperty(name = "创建时间")
    private Date createTime;
    @ApiModelProperty(name = "创建人")
    private Long createBy;
    @ApiModelProperty(name = "更新时间")
    private Date updateTime;
    @ApiModelProperty(name = "更新人")
    private Long updateBy;

    public BaseDTO() {
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}
