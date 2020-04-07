package com.hqj.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Response VO")
public class ResponseVO<T> {
    @ApiModelProperty(name = "成功标志")
    private boolean success;
    @ApiModelProperty(name = "提示信息")
    private String msg;
    @ApiModelProperty(name = "数据")
    private T datas;

    public static ResponseVO success() {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setSuccess(true);
        return responseVO;
    }

    public static <D> ResponseVO<D> success(D d) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setSuccess(true);
        responseVO.setDatas(d);
        return responseVO;
    }

    public static ResponseVO fail() {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setSuccess(false);
        return responseVO;
    }

    public static  ResponseVO fail(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setSuccess(false);
        responseVO.setMsg(msg);
        return responseVO;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }
}
