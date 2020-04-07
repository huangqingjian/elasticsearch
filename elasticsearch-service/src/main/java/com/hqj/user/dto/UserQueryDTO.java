package com.hqj.user.dto;

import com.hqj.common.dto.PageQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 */
@ApiModel("索引查询DTO")
public class UserQueryDTO extends PageQueryDTO {
    @ApiModelProperty("关键字")
    private String q;
    @ApiModelProperty("用户Id")
    private Long userId;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
