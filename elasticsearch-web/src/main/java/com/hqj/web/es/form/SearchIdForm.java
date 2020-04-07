package com.hqj.web.es.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * es 查询条件
 */
@ApiModel("es 查询条件")
public class SearchIdForm {
    @ApiModelProperty("索引名称")
    private String indexName;
    @ApiModelProperty("起始页")
    private String id;
    @ApiModelProperty("页大小")
    private List<String> ids;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
