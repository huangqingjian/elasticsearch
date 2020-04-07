package com.hqj.es.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("es 查询字段DTO")
public class SearchFieldDTO {
    @ApiModelProperty("字段名")
    private String fieldName;
    @ApiModelProperty("字段值")
    private String fieldValue;
    @ApiModelProperty("多字段值")
    private List<String> fieldValues;
    @ApiModelProperty("匹配类型")
    private String matchType;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

}
