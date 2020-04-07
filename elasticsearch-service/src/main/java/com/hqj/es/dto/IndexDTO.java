package com.hqj.es.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("索引DTO")
public class IndexDTO {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 索引名称
     */
    @ApiModelProperty("索引名称")
    private String name;
    /**
     * 索引描述
     */
    @ApiModelProperty("索引描述")
    private String description;
    /**
     * 分片数
     */
    @ApiModelProperty("分片数")
    private Integer shards;
    /**
     * 副本数
     */
    @ApiModelProperty("副本数")
    private Integer replicas;
    /**
     * 索引sql
     */
    @ApiModelProperty("索引sql")
    private IndexSqlDTO sql;
    @ApiModelProperty("索引接口")
    private IndexInterfaceDTO inter;
    /**
     * 索引job
     */
    @ApiModelProperty("索引job")
    private IndexJobDTO job;
    /**
     * 索引属性
     */
    @ApiModelProperty("索引属性")
    private List<IndexPropertyDTO> properties;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getShards() {
        return shards;
    }

    public void setShards(Integer shards) {
        this.shards = shards;
    }

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    public IndexSqlDTO getSql() {
        return sql;
    }

    public void setSql(IndexSqlDTO sql) {
        this.sql = sql;
    }

    public IndexInterfaceDTO getInter() {
        return inter;
    }

    public void setInter(IndexInterfaceDTO inter) {
        this.inter = inter;
    }

    public IndexJobDTO getJob() {
        return job;
    }

    public void setJob(IndexJobDTO job) {
        this.job = job;
    }

    public List<IndexPropertyDTO> getProperties() {
        return properties;
    }

    public void setProperties(List<IndexPropertyDTO> properties) {
        this.properties = properties;
    }
}
