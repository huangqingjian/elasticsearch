<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>搜索平台</title>
    <link rel="stylesheet" href="/static/css/global.css">
    <script type="text/javascript" src="/static/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="/static/js/jquery.dialog.js"></script>
    <script type="text/javascript" src="/static/js/jquery.uploadfile.min.js"></script>
    <link rel="stylesheet" href="/static/css/jquery.dialog.css">
    <link rel="stylesheet" href="/static/css/uploadfile.css">
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
            <div class="index_basic gap_top_2">
                <label class="input_label">索引名称<span>*</span></label><input type="text" class="normal-input" name="name">
            </div>
            <div class="index_shards gap_top_2">
                <div class="group_item">
                    <label class="input_label">索引分片</label><input type="text" class="normal-input" name="shards" value="3">
                </div>
                <div class="group_item gap_left_4">
                    <label class="input_label">索引副本</label><input type="text" class="normal-input" name="replicas" value="1">
                </div>
            </div>
            <div class="index_desc gap_top_2">
                <div class="group_item">
                    <label class="input_label">索引描述</label><textarea class="textarea" id="fn-index-desc"></textarea>
                </div>
            </div>
        </div>
        <#--
        <div class="index_mapping gap_top_2">
            <h3 class="gap_left_2">索引映射</h3>
            <div class="index_basic gap_top_2">
                <label class="input_label">获取映射字段方式<span>*</span></label><select class="normal-input select" name="deMappingType">
                    <option value="0">sql</option>
                    <option value="1">服务接口</option>
                </select>
            </div>
            <div class="index_sql gap_top_2">
                <div class="index_sql_text">
                    <div class="group_item">
                        <label class="input_label">索引SQL</label><textarea class="textarea" id="fn-index-sql"></textarea>
                    </div>
                    <div class="gap_top_1">
                        <div class="group_item">
                            <label class="input_label"></label><span class="span">我们将解析您输入的sql，定义索引映射字段，您可以设置字段的类型、分词等信息。</span>
                        </div>
                    </div>
                </div>
                <div class="index_sql_parse gap_top_1">
                    <div class="group_item">
                        <label class="input_label"></label><input type="button" class="btn" value="立即解析">
                    </div>
                </div>
            </div>
            <div class="index_interface gap_top_2 hide">
                <div class="index_interface_text">
                    <div class="group_item">
                        <label class="input_label">接口地址</label><textarea class="textarea" id="fn-index-interface"></textarea>
                    </div>
                    <div class="gap_top_1">
                        <div class="group_item">
                            <label class="input_label"></label><span class="span">我们将请求您输入的地址，定义索引映射字段，您可以设置字段的类型、分词等信息。</span>
                        </div>
                    </div>
                </div>
                <div class="index_interface_get gap_top_1">
                    <div class="group_item">
                        <label class="input_label"></label><input type="button" class="btn" value="立即获取">
                    </div>
                </div>
            </div>
        </div>
        -->
        <div class="index_property gap_top_2">
            <h3 class="gap_left_2">索引映射<small>自定义索引映射（手动指定、json映射或导入csv文件），如未设置，搜索引擎会根据传入的数据动态索引映射</small></h3>
            <div class="property_item gap_top_2 hide">
                <div class="group_item">
                    <label class="input_label">字段名称<span>*</span></label><input type="text" class="small-input" name="propertyName">
                </div>
                <div class="group_item gap_left_4 propertyType">
                    <label class="input_label">字段类型<span>*</span></label><select class="small-input select" name="propertyType" indexed="0" onchange="javascript:$(this).parent().siblings('.indexed').find('input[name=indexed]').attr('anayzed', $(this).find('option:selected').attr('anayzed'));($(this).find('option:selected').attr('anayzed')==1 && $(this).attr('indexed')==1)?($(this).parent().siblings('.analyzer').removeClass('hide')):($(this).parent().siblings('.analyzer').addClass('hide'));">
                        <#if _dataTypes?? && _dataTypes?size gt 0>
                        <#list _dataTypes as item>
                        <option value="${item.value!''}" anayzed="${item.anayzed}">${item.value!''}</option>
                        </#list>
                        </#if>
                    </select>
                </div>
                <div class="group_item indexed">
                    <div class="group_item">
                        <input type="checkbox" class="normal-checkbox" name="indexed" anayzed="1" onclick="javascript:$(this).parents('.indexed').siblings('.propertyType').find('select').attr('indexed', ($(this).prop('checked')?1:0));($(this).prop('checked') && $(this).attr('anayzed')==1)?($(this).parents('.indexed').siblings('.analyzer').removeClass('hide')):($(this).parents('.indexed').siblings('.analyzer').addClass('hide'));"><label class="checkout_label">是否索引</label>
                    </div>
                </div>
                <div class="group_item">
                    <span class="del_property_item gap_left_2" onclick="javascript: $(this).parents('.property_item').remove();">
                        <img src="/static/image/del.png">
                    </span>
                </div>
                <div class="gap_top_2 analyzer hide">
                    <div class="group_item">
                        <label class="input_label">索引分词<span>*</span></label><select class="small-input select" name="analyzer">
                            <#if _analyzers?? && _analyzers?size gt 0>
                                <#list _analyzers as item>
                                    <option value="${item.value!''}">${item.value!''}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="group_item gap_left_4">
                        <label class="input_label">搜索分词<span>*</span></label><select class="small-input select" name="searchAnalyzer">
                            <#if _analyzers?? && _analyzers?size gt 0>
                                <#list _analyzers as item>
                                    <option value="${item.value!''}">${item.value!''}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                </div>
            </div>
            <div class="gap_top_2 add_property_item">
                <div class="group_item">
                    <label class="input_label"></label><input type="button" class="btn add-btn" value="+增加映射">
                </div>
                <div class="group_item gap_left_2">
                    <input type="button" class="btn json-btn" value="JSON映射">
                </div>
                <div class="group_item gap_left_2">
                    <div id="fileuploader"></div>
                </div>
            </div>
        </div>
        <div class="index_sync gap_top_2">
            <h3 class="gap_left_2">数据同步<small>数据同步到搜索引擎方式</small></h3>
            <div class="sync_item gap_top_2">
                <div class="group_item">
                    <label class="input_label">同步方式<span>*</span></label><select class="normal-input select" name="syncType">
                        <#if _syncTypes?? && _syncTypes?size gt 0>
                            <#list _syncTypes as item>
                                <option value="${item.value!''}">${item.desc!''}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
                <div class="sync_sql">
                    <div class="gap_top_2">
                        <div class="group_item">
                            <label class="input_label">同步SQL<span>*</span></label><textarea class="textarea" style="height: 200px;" id="fn-index-sql"></textarea>
                        </div>
                    </div>
                    <div class="gap_top_1">
                        <div class="group_item">
                            <label class="input_label"></label><span class="span">我们将使用您输入的sql进行数据同步。</span>
                        </div>
                    </div>
                </div>
                <div class="sync_interface hide">
                    <div class="gap_top_2">
                        <div class="group_item">
                            <label class="input_label">同步接口<span>*</span></label><textarea class="textarea" id="fn-index-interface"></textarea>
                        </div>
                    </div>
                    <div class="gap_top_1">
                        <div class="group_item">
                            <label class="input_label"></label><span class="span">我们将使用您输入的接口URL进行数据同步。</span>
                        </div>
                    </div>
                </div>
                <div class="gap_top_2">
                    <div class="group_item">
                        <label class="input_label">全量规则</label><select class="normal-input select" name="syncAllRule">
                            <option value=""></option>
                            <#if _syncAllRules?? && _syncAllRules?size gt 0>
                                <#list _syncAllRules as item>
                                    <option value="${item.value!''}">${item.desc!''}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="group_item gap_left_4">
                        <label class="input_label">增量规则</label><select class="normal-input select" name="syncIncrRule">
                            <option value=""></option>
                            <#if _syncIncrRules?? && _syncIncrRules?size gt 0>
                                <#list _syncIncrRules as item>
                                    <option value="${item.value!''}">${item.desc!''}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="gap_top_2">
                        <div class="group_item">
                            <label class="input_label"></label><input type="checkbox" class="normal-checkbox" name="switchs"><label class="checkout_label">是否开启</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="index_save gap_top_2">
            <div class="group_item">
                <label class="input_label"></label><input type="button" class="btn" value="保存">
            </div>
        </div>
    </div>
    <div id="btn-dialog"></div>
    <script type="text/javascript">
        $(function(){
            var $property = $(".index_property").html();
            var $property_item = "<div class='property_item gap_top_2'>" + $(".index_property .property_item").html() + "</div>";
            // 提交
            $(".index_save .btn").click(function(){
                // 待提交数据
                var index = {};
                index.name = $("input[name=name]").val();
                index.shards = $("input[name=shards]").val();
                index.replicas = $("input[name=replicas]").val();
                index.description = $("#fn-index-desc").val();
                if($(".property_item").not(".hide").length > 0) {
                    var properties = [];
                    for(var i = 0; i < $(".property_item").not(".hide").length; i++) {
                        var $item = ($(".property_item").not(".hide"))[i];
                        var property = {};
                        property.name = $($item).find("input[name=propertyName]").val();
                        property.type = $($item).find("select[name=propertyType]").val();
                        var indexed = $($item).find("input[name=indexed]").prop("checked");
                        property.indexed = indexed ? 1 : 0;
                        var analyzed = $($item).find("select[name=propertyType]").find('option:selected').attr('anayzed');
                        if(indexed && analyzed==1) {
                            property.analyzer = $($item).find("select[name=analyzer]").val();
                            property.searchAnalyzer = $($item).find("select[name=searchAnalyzer]").val();
                        }
                        properties.push(property);
                    }
                    index.properties = properties;
                }
                if($("select[name=syncType]").val() == 0) {
                    var sql = {};
                    sql.sql = $("#fn-index-sql").val();
                    index.sql = sql;
                } else if($("select[name=syncType]").val() == 1) {
                    var inter = {};
                    inter.url = $("#fn-index-interface").val();
                    index.inter = inter;
                }
                var job = {};
                job.switchs = $("input[name=switchs]").prop("checked") ? 1 : 0;
                job.dataSyncType = $("select[name=syncType]").val();
                job.dataSyncAllRule = $("select[name=syncAllRule]").val();
                job.dataSyncIncrRule = $("select[name=syncIncrRule]").val();
                index.job = job;
                // 数据提交
                $.ajax({
                    type : "POST",
                    url : "/es/createIndex",
                    contentType : "application/json",
                    dataType : "json",
                    data : JSON.stringify(index),
                    success : function(data) {
                        if(data.code == 0) {
                            location.href = "/page/list.html";
                        } else {
                            $('#btn-dialog').dialogBox({
                                autoHide: true,
                                hasMask: true,
                                time: 2000,
                                title: '失败提示',
                                content: data.message
                            });
                        }
                    }
                });
            });
            // sql解析
            $(".index_sql_parse .btn").click(function(data){
                var sql = {};
                sql.sql = $("#fn-index-sql").val();
                $.ajax({
                    type : "POST",
                    url : "/es/parseSelectSql",
                    contentType : "application/json",
                    dataType : "json",
                    data : JSON.stringify(sql),
                    success : function(data) {
                        if(data.code == 0) {
                            if(data.data.length > 0) {
                                $(".index_property").removeClass("hide");
                                var $properties = "<div class=\"group_item\"><label class=\"input_label\">索引属性</label></div>";
                                for (var i = 0; i < data.data.length; i++) {
                                    $properties += $property.replace("#propertyName#", data.data[i]);
                                }
                                $(".index_property").html($properties);
                            }
                        } else {
                            $('#btn-dialog').dialogBox({
                                autoHide: true,
                                hasMask: true,
                                time: 2000,
                                title: '失败提示',
                                content: '解析失败～'
                            });
                        }
                    }
                });
            });
            // 请求接口
            $(".index_interface_get .btn").click(function(){
                var inter = {};
                inter.url = $("#fn-index-interface").val();
                $.ajax({
                    type : "POST",
                    url : "/es/requestUrl",
                    contentType : "application/json",
                    dataType : "json",
                    data : JSON.stringify(inter),
                    success : function(data) {
                        if(data.code == 0) {
                            if(data.data.length > 0) {
                                $(".index_property").removeClass("hide");
                                var $properties = "<div class=\"group_item\"><label class=\"input_label\">索引属性</label></div>";
                                for (var i = 0; i < data.data.length; i++) {
                                    $properties += $property.replace("#propertyName#", data.data[i]);
                                }
                                $(".index_property").html($properties);
                            }
                        } else {
                            $('#btn-dialog').dialogBox({
                                autoHide: true,
                                hasMask: true,
                                time: 2000,
                                title: '失败提示',
                                content: '请求失败～'
                            });
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
            // 添加映射
            $(".index_property .add-btn").click(function(){
                $(this).parents(".add_property_item").before($property_item);
            });
            // json映射
            $(".index_property .json-btn").click(function(){
                $('#btn-dialog').dialogBox({
                    hasClose: true,
                    hasMask: true,
                    hasBtn: true,
                    confirmValue: '立即映射',
                    confirm: function(){
                        if($("#fn-mapping-json").val()) {
                            $.ajax({
                                type: "POST",
                                url: '/es/parseMappingJson',
                                data: $("#fn-mapping-json").val(),
                                contentType: "application/json",
                                dataType: "json",
                                success: function (data) {
                                    if (data.code == 0) {
                                        if (data.data && data.data.length > 0) {
                                            for (var i = 0; i < data.data.length; i++) {
                                                $(".index_property .json-btn").parents(".add_property_item").before($property_item.replace("name=\"propertyName\"", "name=\"propertyName\" value=\"" + data.data[i] + "\""));
                                            }
                                        }
                                    } else {
                                        $('#btn-dialog').dialogBox({
                                            autoHide: true,
                                            hasMask: true,
                                            time: 2000,
                                            title: '映射失败',
                                            content: data.message
                                        });
                                    }
                                }
                            });
                        }
                    },
                    cancelValue: '取消',
                    width: 600,
                    height: 400,
                    title: 'JSON映射',
                    content: '<textarea style="width: 540px; height: 260px; padding: 5px 10px" id="fn-mapping-json"></textarea>'
                });
            });
            // 上传
            $("#fileuploader").uploadFile({
                url:"/es/importCsv",
                fileName:"file",
                returnType: "json",
                maxFileCount: -1,
                showDone: false,
                showDelete: false,
                showProgress: false,
                showFileCounter: false,
                onSuccess: function(files, data, xhr,pd) {
                    if(data.code == 0) {
                        $(".add_property_item").before(data.data);
                    } else {
                        $('#btn-dialog').dialogBox({
                            autoHide: true,
                            hasMask: true,
                            time: 2000,
                            title: '失败提示',
                            content: data.message
                        });
                    }
                }
            });
        });
    </script>
</body>
</html>