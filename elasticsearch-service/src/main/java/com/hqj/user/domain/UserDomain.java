package com.hqj.user.domain;

import com.hqj.common.domain.BaseDomain;

/**
 * 用户
 */
public class UserDomain extends BaseDomain {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户头像
     */
    private String face;
    /**
     * 是否管理员
     */
    private Integer managed;

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
    }
}
