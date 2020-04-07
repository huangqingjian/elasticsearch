package com.hqj.es.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * es 查询条件
 */
@ApiModel("es 查询条件")
public class SearchDTO {
    @ApiModelProperty("索引名称")
    private String indexName;
    @ApiModelProperty("起始页")
    private Integer startPage = 0;
    @ApiModelProperty("页大小")
    private Integer pageSize = 20;
    @ApiModelProperty("条件字段")
    private List<SearchFieldDTO> fields;
    @ApiModelProperty("或字段")
    private List<SearchFieldDTO> orFields;
    @ApiModelProperty("范围字段")
    private List<SearchRangeFieldDTO> rangeFields;
    @ApiModelProperty("或范围字段")
    private List<SearchRangeFieldDTO> orRangeFields;
    @ApiModelProperty("需要显示的字段，逗号分隔（缺省为全部字段）")
    private String showField;
    @ApiModelProperty("高亮字段")
    private String highlightField;
    @ApiModelProperty("排序字段")
    private String sortField;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<SearchFieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<SearchFieldDTO> fields) {
        this.fields = fields;
    }

    public List<SearchFieldDTO> getOrFields() {
        return orFields;
    }

    public void setOrFields(List<SearchFieldDTO> orFields) {
        this.orFields = orFields;
    }

    public List<SearchRangeFieldDTO> getRangeFields() {
        return rangeFields;
    }

    public void setRangeFields(List<SearchRangeFieldDTO> rangeFields) {
        this.rangeFields = rangeFields;
    }

    public List<SearchRangeFieldDTO> getOrRangeFields() {
        return orRangeFields;
    }

    public void setOrRangeFields(List<SearchRangeFieldDTO> orRangeFields) {
        this.orRangeFields = orRangeFields;
    }

    public String getShowField() {
        return showField;
    }

    public void setShowField(String showField) {
        this.showField = showField;
    }

    public String getHighlightField() {
        return highlightField;
    }

    public void setHighlightField(String highlightField) {
        this.highlightField = highlightField;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

}
