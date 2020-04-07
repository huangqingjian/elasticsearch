package com.hqj.es.bean;

import io.swagger.annotations.ApiModel;

/**
 * 索引查询条件
 */
@ApiModel("索引查询DTO")
public class IndexQueryBean {
    /**
     * 关键字
     */
    private String q;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

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

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
