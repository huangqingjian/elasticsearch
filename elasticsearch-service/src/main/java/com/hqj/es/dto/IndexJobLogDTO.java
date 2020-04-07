package com.hqj.es.dto;

import com.hqj.es.enums.SyncType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 索引job日志
 */
@ApiModel("索引job日志")
public class IndexJobLogDTO {
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
     * jobId
     */
    @ApiModelProperty("jobId")
    private Long jobId;
    /**
     * 数据同步方式：0：SQL，1：提供服务接口
     */
    @ApiModelProperty("数据同步方式：0：SQL，1：提供服务接口")
    private Integer dataSyncType;
    @ApiModelProperty("数据同步描述")
    private String dataSyncTypeDesc;
    /**
     * job开始时间
     */
    @ApiModelProperty("job开始时间")
    private Date startTime;
    @ApiModelProperty("job开始时间字串")
    private String startTimeStr;
    /**
     * job结束时间
     */
    @ApiModelProperty("job结束时间")
    private Date endTime;
    @ApiModelProperty("job结束时间字串")
    private String endTimeStr;
    /**
     * 本次同步数据量
     */
    @ApiModelProperty("本次同步数据量")
    private Integer total;
    /**
     * 错误码：0-成功，1-失败
     */
    @ApiModelProperty("错误码：0-成功，1-失败")
    private String errorCode;
    /**
     * 错误码描述
     */
    private String errorCodeDesc;
    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    private String errorMessage;
    /**
     * 是否删除，0-正常 1-删除
     */
    @ApiModelProperty("是否删除，0-正常 1-删除")
    private Integer deleted;

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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        if(startTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            setStartTimeStr(sdf.format(startTime));
        }
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        if(endTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            setEndTimeStr(sdf.format(endTime));
        }
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        if("0".equals(errorCode)) {
            setErrorCodeDesc("成功");
        } else {
            setErrorCodeDesc("失败");
        }
    }

    public String getErrorCodeDesc() {
        return errorCodeDesc;
    }

    public void setErrorCodeDesc(String errorCodeDesc) {
        this.errorCodeDesc = errorCodeDesc;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
