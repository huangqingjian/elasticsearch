package com.hqj.es.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("索引类型映射DTO")
public class IndexPropertyDTO {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 映射字段名称
     */
    @ApiModelProperty("映射字段名称")
    private String name;
    /**
     * 映射字段类型
     */
    @ApiModelProperty("映射字段类型")
    private String type;
    /**
     * 是否索引
     */
    @ApiModelProperty("是否索引")
    private Integer indexed;
    /**
     * 是否分词
     */
    @ApiModelProperty("是否分词")
    private Integer analyzed;
    /**
     * 索引分词
     */
    @ApiModelProperty("索引分词")
    private String analyzer;
    /**
     * 搜索分词
     */
    @ApiModelProperty("搜索分词")
    private String searchAnalyzer;
    /**
     * 索引Id
     */
    @ApiModelProperty("索引Id")
    private Long indexId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIndexed() {
        return indexed;
    }

    public void setIndexed(Integer indexed) {
        this.indexed = indexed;
    }

    public Integer getAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(Integer analyzed) {
        this.analyzed = analyzed;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public String getSearchAnalyzer() {
        return searchAnalyzer;
    }

    public void setSearchAnalyzer(String searchAnalyzer) {
        this.searchAnalyzer = searchAnalyzer;
    }

    public Long getIndexId() {
        return indexId;
    }

    public void setIndexId(Long indexId) {
        this.indexId = indexId;
    }

}
