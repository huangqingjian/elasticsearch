package com.hqj.es.dto;

import com.hqj.es.enums.SwitchEnum;
import com.hqj.es.enums.SyncAllRule;
import com.hqj.es.enums.SyncIncrRule;
import com.hqj.es.enums.SyncType;
import com.hqj.common.domain.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 索引job
 */
@ApiModel("索引job")
public class IndexJobDTO extends BaseDomain {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 索引Id
     */
    @ApiModelProperty("索引Id")
    private Long indexId;
    /**
     * 索引名称
     */
    @ApiModelProperty("索引名称")
    private String indexName;
    /**
     * 开关:0 关，1：开
     */
    @ApiModelProperty("开关:0 关，1：开")
    private Integer switchs;
    /**
     * 开关描述
     */
    @ApiModelProperty("开关描述")
    private String switchsDesc;
    /**
     * 数据同步方式：0：SQL，1：提供服务接口
     */
    @ApiModelProperty("数据同步方式：0：SQL，1：提供服务接口")
    private Integer dataSyncType;
    /**
     * 数据同步方式描述
     */
    @ApiModelProperty("数据同步方式描述")
    private String dataSyncTypeDesc;
    /**
     * 全量同步数据规则：1：每天一次，2：每周一次：3：每月一次
     */
    @ApiModelProperty("全量同步数据规则：1：每天一次，2：每周一次：3：每月一次")
    private Integer dataSyncAllRule;
    /**
     * 数据同步方式描述
     */
    @ApiModelProperty("全量同步数据规则描述")
    private String dataSyncAllRuleDesc;
    /**
     * 增量同步数据规则：1：5分钟一次，2：30分钟一次 3：60分钟一次
     */
    @ApiModelProperty("增量同步数据规则：1：5分钟一次，2：30分钟一次 3：60分钟一次")
    private Integer dataSyncIncrRule;
    /**
     * 数据同步方式描述
     */
    @ApiModelProperty("增量同步数据规则描述")
    private String dataSyncIncrRuleDesc;
    /**
     * 最近一次同步完成时间
     */
    @ApiModelProperty("最近一次同步完成时间")
    private Date lastSyncTime;
    /**
     * 最近一次同步完成时间字串
     */
    @ApiModelProperty("最近一次同步完成时间字串")
    private String lastSyncTimeStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIndexId() {
        return indexId;
    }

    public void setIndexId(Long indexId) {
        this.indexId = indexId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Integer getSwitchs() {
        return switchs;
    }

    public void setSwitchs(Integer switchs) {
        this.switchs = switchs;
        setSwitchsDesc(SwitchEnum.getSwitchDesc(switchs));
    }

    public String getSwitchsDesc() {
        return switchsDesc;
    }

    public void setSwitchsDesc(String switchsDesc) {
        this.switchsDesc = switchsDesc;
    }

    public Integer getDataSyncType() {
        return dataSyncType;
    }

    public void setDataSyncType(Integer dataSyncType) {
        this.dataSyncType = dataSyncType;
        setDataSyncTypeDesc(SyncType.getSyncTypeDesc(dataSyncType));
    }

    public String getDataSyncTypeDesc() {
        return dataSyncTypeDesc;
    }

    public void setDataSyncTypeDesc(String dataSyncTypeDesc) {
        this.dataSyncTypeDesc = dataSyncTypeDesc;
    }

    public Integer getDataSyncAllRule() {
        return dataSyncAllRule;
    }

    public void setDataSyncAllRule(Integer dataSyncAllRule) {
        this.dataSyncAllRule = dataSyncAllRule;
        setDataSyncAllRuleDesc(SyncAllRule.getRuleDesc(dataSyncAllRule));
    }

    public String getDataSyncAllRuleDesc() {
        return dataSyncAllRuleDesc;
    }

    public void setDataSyncAllRuleDesc(String dataSyncAllRuleDesc) {
        this.dataSyncAllRuleDesc = dataSyncAllRuleDesc;
    }

    public Integer getDataSyncIncrRule() {
        return dataSyncIncrRule;
    }

    public void setDataSyncIncrRule(Integer dataSyncIncrRule) {
        this.dataSyncIncrRule = dataSyncIncrRule;
        setDataSyncIncrRuleDesc(SyncIncrRule.getRuleDesc(dataSyncIncrRule));
    }

    public String getDataSyncIncrRuleDesc() {
        return dataSyncIncrRuleDesc;
    }

    public void setDataSyncIncrRuleDesc(String dataSyncIncrRuleDesc) {
        this.dataSyncIncrRuleDesc = dataSyncIncrRuleDesc;
    }

    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
        if(lastSyncTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.lastSyncTimeStr = sdf.format(lastSyncTime);
        }
    }

    public String getLastSyncTimeStr() {
        return lastSyncTimeStr;
    }

    public void setLastSyncTimeStr(String lastSyncTimeStr) {
        this.lastSyncTimeStr = lastSyncTimeStr;
    }
}
