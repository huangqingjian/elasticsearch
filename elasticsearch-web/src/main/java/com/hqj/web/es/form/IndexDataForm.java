package com.hqj.web.es.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel("索引数据表单")
public class IndexDataForm {
    @ApiModelProperty("索引名称")
    private String indexName;
    @ApiModelProperty("类型名称")
    private String typeName;
    @ApiModelProperty("ID")
    private String id;
    @ApiModelProperty("待添加数据")
    private Map<String, Object> objectMap;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, Object> objectMap) {
        this.objectMap = objectMap;
    }
}
