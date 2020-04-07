<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>搜索平台</title>
    <link rel="stylesheet" href="/static/css/global.css">
    <script type="text/javascript" src="/static/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="/static/js/jquery.dialog.js"></script>
    <link rel="stylesheet" href="/static/css/jquery.dialog.css">
    <style type="text/css">
        *{
            margin: 0px;
            padding: 0px;
        }
        body{
            font-size: 16px;
        }
        .input_label{
            display: inline-block;
            width: 140px;
            padding: 0 20px;
        }
        .input_label span{
            color: #ff0000;
        }
        .checkout_label{
            padding: 0 5px;
        }
        .gap_top_1{
            margin-top: 10px;
        }
        .gap_top_2{
            margin-top: 20px;
        }
        .gap_left_2{
            margin-left: 20px;
        }
        .gap_left_4{
            margin-left: 40px;
        }
        .gap_left_6{
            margin-left: 60px;
        }
        .select{
            background: #fff;
            border-radius: 0;
            box-sizing: content-box;
            -webkit-appearance: inherit;
        }
        .span{
            font-size: 14px;
            color: #aaa;
        }
        .normal-input{
            width: 300px;
            height: 30px;
            border: 1px solid #aaa;
            padding: 2px 10px;
        }
        .small-input{
            width: 200px;
            height: 30px;
            border: 1px solid #aaa;
            padding: 2px 10px;
        }
        .normal-checkbox{
            height: 16px;
            vertical-align: middle;
        }
        .textarea{
            width: 847px;
            height: 100px;
            padding: 5px 10px;
            vertical-align: middle;
        }
        .container{
            width: 1190px;
            margin: 0 auto;
            padding: 45px;
        }
        .container .group_item{
            display: inline-block;
        }
        .container .property_item{
            /*background : #f6f6f6;*/
            box-shadow : 0 2px 12px 0 rgba(0,0,0,.05);
            padding-top: 10px;
            padding-bottom: 10px;
        }
        .container .property_item .indexed{
            width: 200px;
            text-align: right;
        }
        .container .btn{
            background-color: #3cc8b4;
            color: #fff;
            width: 100px;
            height: 40px;
            line-height: 40px;
            text-align: center;
            border: none;
            font-size: 12px;
        }
        .container .group_item img{
            width: 20px;
            height: 20px;
            vertical-align: middle;
        }
        .hide{
            display: none;
        }
        h3 small{
            font-weight: normal;
            font-size: 12px;
            color: #aaa;
            margin-left: 20px;
        }
    </style>
</head>
<body>
    <#include "/common/header.ftl"/>
    <div class="container">
        <div class="index">
            <h3 class="gap_left_2">基本信息<small>索引建立的基础数据</small></h3>
            <input type="hidden" name="id" value="${_index.id}">
            <div class="index_basic gap_top_2">
                <label class="input_label">索引名称<span>*</span></label><input type="text" class="normal-input" name="name" value="${_index.name!''}" disabled="disabled">
            </div>
            <div class="index_shards gap_top_2">
                <div class="group_item">
                    <label class="input_label">索引分片</label><input type="text" class="normal-input" name="shards" value="<#if _index.shards?? && _index.shards != 0>${_index.shards!''}</#if>" disabled="disabled">
                </div>
                <div class="group_item gap_left_4">
                    <label class="input_label">索引副本</label><input type="text" class="normal-input" name="replicas" value="<#if _index.replicas?? && _index.replicas != 0>${_index.replicas!''}</#if>" disabled="disabled">
                </div>
            </div>
            <div class="index_desc gap_top_2">
                <div class="group_item">
                    <label class="input_label">索引描述</label><textarea class="textarea" id="fn-index-desc">${_index.description!''}</textarea>
                </div>
            </div>
        </div>
        <#if _index.properties?? && _index.properties?size gt 0>
            <div class="index_property gap_top_2">
                <h3 class="gap_left_2">索引映射<small>自定义索引映射，如未设置，搜索引擎会根据传入的数据动态索引映射</small></h3>
                <#list _index.properties as prop>
                <div class="property_item gap_top_2">
                    <input type="hidden" name="propertyId" value="${prop.id}">
                    <div class="group_item">
                        <label class="input_label">字段名称<span>*</span></label><input type="text" class="small-input" name="propertyName" value="${prop.name!''}" disabled="disabled">
                    </div>
                    <div class="group_item gap_left_4">
                        <label class="input_label">字段类型<span>*</span></label><select class="small-input select" name="propertyType" disabled="disabled">
                            <#if _dataTypes?? && _dataTypes?size gt 0>
                                <#list _dataTypes as item>
                                    <option value="${item.value!''}" anayzed="${item.anayzed}" <#if item.value == prop.type>selected</#if>>${item.value!''}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="group_item indexed">
                        <div class="group_item">
                            <input type="checkbox" class="normal-checkbox" name="indexed" <#if prop.indexed?? && prop.indexed == 1>checked</#if> disabled="disabled"><label class="checkout_label">是否索引</label>
                        </div>
                    </div>
                    <div class="gap_top_2 analyzer <#if prop.analyzed?? && prop.indexed?? && prop.analyzed == 1 && prop.indexed == 1><#else>hide</#if>">
                        <div class="group_item">
                            <label class="input_label">索引分词<span>*</span></label><select class="small-input select" name="analyzer" disabled="disabled">
                                <#if _analyzers?? && _analyzers?size gt 0>
                                    <#list _analyzers as item>
                                        <option value="${item.value!''}" <#if item.value == prop.analyzer>selected</#if>>${item.value!''}</option>
                                    </#list>
                                </#if>
                            </select>
                        </div>
                        <div class="group_item gap_left_4">
                            <label class="input_label">搜索分词<span>*</span></label><select class="small-input select" name="searchAnalyzer" disabled="disabled">
                                <#if _analyzers?? && _analyzers?size gt 0>
                                    <#list _analyzers as item>
                                        <option value="${item.value!''}" <#if item.value == prop.searchAnalyzer>selected</#if>>${item.value!''}</option>
                                    </#list>
                                </#if>
                            </select>
                        </div>
                    </div>
                </div>
                </#list>
                <div class="gap_top_2 add_property_item">
                    <div class="group_item">
                        <label class="input_label"></label><input type="button" class="btn add-btn" value="下载索引映射" onclick="window.open('/es/exportCsv/${_index.id}')">
                    </div>
                </div>
            </div>
        </#if>
        <#if _index.job??>
        <div class="index_sync gap_top_2">
            <h3 class="gap_left_2">数据同步<small>数据同步到搜索引擎方式</small></h3>
            <div class="sync_item gap_top_2">
                <input type="hidden" name="jobId" value="${_index.job.id}">
                <div class="group_item">
                    <label class="input_label">同步方式<span>*</span></label><select class="normal-input select" name="syncType">
                        <#if _syncTypes?? && _syncTypes?size gt 0>
                            <#list _syncTypes as item>
                                <option value="${item.value!''}" <#if _index.job.dataSyncType == item.value>selected</#if>>${item.desc!''}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
                <div class="sync_sql <#if _index.job.dataSyncType==1>hide</#if>">
                    <input type="hidden" value="<#if _index.sql??>${_index.sql.id}</#if>" id="fn-index-sql-id">
                    <div class="gap_top_2">
                        <div class="group_item">
                            <label class="input_label">同步SQL<span>*</span></label><textarea class="textarea" style="height: 200px;" id="fn-index-sql">${_index.sql.sql}</textarea>
                        </div>
                    </div>
                    <div class="gap_top_1">
                        <div class="group_item">
                            <label class="input_label"></label><span class="span">我们将使用您输入的sql进行数据同步。</span>
                        </div>
                    </div>
                </div>
                <div class="sync_interface <#if _index.job.dataSyncType==0>hide</#if>">
                    <input type="hidden" value="<#if _index.inter??>${_index.inter.id}</#if>" id="fn-index-interface-id">
                    <div class="gap_top_2">
                        <div class="group_item">
                            <label class="input_label">同步接口<span>*</span></label><textarea class="textarea" id="fn-index-interface">${_index.inter.url}</textarea>
                        </div>
                    </div>
                    <div class="gap_top_1">
                        <div class="group_item">
                            <label class="input_label"></label><span class="span">我们将使用您输入的接口URL进行数据同步。</span>
                        </div>
                    </div>
                </div>
                <div class="sync_item gap_top_2">
                    <div class="group_item">
                        <label class="input_label">全量规则</label><select class="normal-input select" name="syncAllRule">
                            <option value=""></option>
                            <#if _syncAllRules?? && _syncAllRules?size gt 0>
                                <#list _syncAllRules as item>
                                    <option value="${item.value!''}" <#if _index.job.dataSyncAllRule ?? && (_index.job.dataSyncAllRule == item.value)>selected</#if>>${item.desc!''}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="group_item gap_left_4">
                        <label class="input_label">增量规则</label><select class="normal-input select" name="syncIncrRule">
                            <option value=""></option>
                            <#if _syncIncrRules?? && _syncIncrRules?size gt 0>
                                <#list _syncIncrRules as item>
                                    <option value="${item.value!''}" <#if _index.job.dataSyncIncrRule?? && (_index.job.dataSyncIncrRule == item.value)>selected</#if>>${item.desc!''}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                </div>
                <div class="gap_top_2">
                    <div class="group_item">
                        <label class="input_label"></label><input type="checkbox" class="normal-checkbox" name="switchs" <#if _index.job.switchs ?? && _index.job.switchs == 1>checked</#if>><label class="checkout_label">是否开启</label>
                    </div>
                </div>
            </div>
        </div>
        </#if>
        <div class="index_save gap_top_2">
            <div class="group_item">
                <label class="input_label"></label><input type="button" class="btn" value="保存">
            </div>
        </div>
    </div>
    <div id="btn-dialog"></div>
    <script type="text/javascript">
        $(function(){
            // 提交
            $(".index_save .btn").click(function(){
                // 待提交数据
                var index = {};
                index.id = $("input[name=id]").val();
                index.description = $("#fn-index-desc").val();
                if($("select[name=syncType]").val() == 0) {
                    var sql = {};
                    sql.id = $("#fn-index-sql-id").val();
                    sql.sql = $("#fn-index-sql").val();
                    index.sql = sql;
                } else if($("select[name=syncType]").val() == 1) {
                    var inter = {};
                    inter.id = $("#fn-index-interface-id").val();
                    inter.url = $("#fn-index-interface").val();
                    index.inter = inter;
                }
                var job = {};
                job.id = $("input[name=jobId]").val();
                job.switchs = $("input[name=switchs]").prop("checked") ? 1 : 0;
                job.dataSyncType = $("select[name=syncType]").val();
                job.dataSyncAllRule = $("select[name=syncAllRule]").val();
                job.dataSyncIncrRule = $("select[name=syncIncrRule]").val();
                index.job = job;
                // 数据提交
                $.ajax({
                    type : "POST",
                    url : "/es/updateIndex",
                    contentType : "application/json",
                    dataType : "json",
                    data : JSON.stringify(index),
                    success : function(data) {
                        if(data.code == 0) {
                            location.href = "/page/list.html";
                        } else {
                            alert(data.message);
                        }
                    }
                });
            });
            // 同步方式
            $("select[name=syncType]").change(function(){
                if($(this).val() == 0) {
                    $(".sync_sql").removeClass("hide");
                    $(".sync_interface").addClass("hide");
                } else {
                    $(".sync_sql").addClass("hide");
                    $(".sync_interface").removeClass("hide");
                }
            });
        });
    </script>
</body>
</html>