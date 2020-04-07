package com.hqj.user.dto;

import com.hqj.common.enums.YesNo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户DTO")
public class UserDTO {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String userName;
    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    private String password;
    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String face;
    /**
     * 是否管理员
     */
    @ApiModelProperty("是否管理员")
    private Integer managed;
    /**
     * 是否管理员描述
     */
    @ApiModelProperty("是否管理员描述")
    private String managedDesc;
    /**
     * 用户Id
     */
    @ApiModelProperty("用户Id")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getManaged() {
        return managed;
    }

    public void setManaged(Integer managed) {
        this.managed = managed;
        setManagedDesc(YesNo.getYesNoDesc(managed));
    }

    public String getManagedDesc() {
        return managedDesc;
    }

    public void setManagedDesc(String managedDesc) {
        this.managedDesc = managedDesc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
