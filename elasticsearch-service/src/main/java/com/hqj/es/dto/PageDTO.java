package com.hqj.es.dto;

import java.util.List;
import java.util.Map;

/**
 * es 分页
 */
public class PageDTO {
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页显示多少条
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private int total;
    /**
     * 本页的数据列表
     */
    private List<Map<String, Object>> list;

    /**
     * 总页数
     */
    private int pages;

    public PageDTO() {}

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
