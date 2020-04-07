package com.hqj.es.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("es 范围查询字段DTO")
public class SearchRangeFieldDTO {
    @ApiModelProperty("字段名")
    private String fieldName;
    @ApiModelProperty("字段值")
    private String startValue;
    @ApiModelProperty("多字段值")
    private String endValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }
}
