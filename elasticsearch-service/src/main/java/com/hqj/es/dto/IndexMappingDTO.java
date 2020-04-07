package com.hqj.es.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("索引映射DTO")
public class IndexMappingDTO {
    /**
     * 索引映射名称
     */
    @ApiModelProperty("映射名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
