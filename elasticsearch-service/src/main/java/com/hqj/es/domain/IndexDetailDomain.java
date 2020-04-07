package com.hqj.es.domain;

import com.hqj.common.domain.BaseDomain;

/**
 * 索引
 */
public class IndexDetailDomain extends BaseDomain {
    /**
     * 主键
     */
    private Long id;
    /**
     * 索引名称
     */
    private String name;
    /**
     * 索引描述
     */
    private String description;
    /**
     * 分片数
     */
    private Integer shards;
    /**
     * 副本数
     */
    private Integer replicas;

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

}
