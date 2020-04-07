<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>搜索平台</title>
    <link rel="stylesheet" href="/static/css/global.css">
    <script type="text/javascript" src="/static/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="/static/js/pagination.js"></script>
    <script type="text/javascript" src="/static/js/pagination-score.js"></script>
    <script type="text/javascript" src="/static/js/jquery.dialog.js"></script>
    <link rel="stylesheet" href="/static/css/jquery.dialog.css">
    <link rel="stylesheet" href="/static/css/pagination.css">
    <style type="text/css">
        *{
            margin: 0px;
            padding: 0px;
        }
        body{
            font-size: 16px;
        }
        .input_label{
            padding: 0 20px;
        }
        .form_label{
            padding: 0 20px;
        }
        .checkout_label{
            padding: 0 5px;
        }
        .gap_top{
            margin-top: 20px;
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
        .normal-input{
            width: 300px;
            height: 30px;
            border: 1px solid #aaa;
            padding: 2px 10px;
        }
        .form-input{
            width: 300px;
            height: 30px;
            border: 1px solid #aaa;
            padding: 2px 10px;
        }
        .normal-checkbox{
            height: 16px;
            vertical-align: middle;
        }
        .container{
            width: 1190px;
            margin: 0 auto;
            padding: 45px;
        }
        .container .index .indices{
            margin-left: 60px;
        }
        .container .group_item{
            display: inline-block;
        }
        .container .btn{
            background-color: #3cc8b4;
            color: #fff;
            width: 100px;
            height: 36px;
            line-height: 36px;
            margin-left: 20px;
            text-align: center;
            border: none;
            font-size: 12px;
        }
        .btn-reset{
            background-color: #ccc !important;
        }
        .btn-del{
            background-color: #ccc !important;
        }
        .container .form-item{
            float: left;
        }
        .clear{
            clear: both;
        }
        .container table tbody tr:hover{
            background-color: #eaeaea;
        }
        .container table th, .container table td{
            height: 40px;
            text-align: left;
        }
        .container table th:last-child, .container table td:last-child{
            text-align: right;
        }

        .container .btn-add, .container .btn-edit, .container .btn-del{
            background-color: #3cc8b4;
            border-radius: 1px;
            color: #fff;
            font-size: 14px;
            text-decoration: none;
            padding: 2px 4px;
            text-align: center;
            border: none;
        }
        .pagination{
            margin-top: 20px;
            text-align: right;
        }
        .switch{
            margin:0;
            padding:0;
            color:#666;
            font-size:14px;
            font-variant:tabular-nums;
            line-height:1.7;
            list-style:none;
            font-feature-settings:"tnum";
            position:relative;
            display:inline-block;
            box-sizing:border-box;
            min-width:44px;
            height:22px;
            line-height:20px;
            vertical-align:middle;
            background-color:#aaa;
            border:1px solid transparent;
            border-radius:100px;
            cursor:pointer;
            -webkit-transition:all .36s;
            transition:all .36s;
            -webkit-user-select:none;
            -moz-user-select:none;
            -ms-user-select:none;
            user-select:none;
        }
        .switch-inner{
            display:block;
            margin-right:6px;
            margin-left:24px;
            color:#fff;
            font-size:12px
        }
        .switch:after{
            position:absolute;
            top:1px;
            left:1px;
            width:18px;
            height:18px;
            background-color:#fff;
            border-radius:18px;
            cursor:pointer;
            -webkit-transition:all .36s cubic-bezier(.78,.14,.15,.86);
            transition:all .36s cubic-bezier(.78,.14,.15,.86);
            content:" ";
        }
        .switch-checked{
            background-color: #3cc8b4;
        }
        .switch-checked:after{
            left: 100%;
            margin-left: -1px;
            -webkit-transform: translateX(-100%);
            transform: translateX(-100%);
        }
        .switch-checked .switch-inner{
            margin-right: 24px;
            margin-left: 6px;
        }
    </style>
</head>
<body>
<#include "/common/header.ftl"/>
<div class="container">
    <div class="form">
        <form>
            <div class="form-item">
                <label class="form_label">索引名称</label>
                <input type="text" class="form-input" name="indexName">
            </div>
            <div class="form-item">
                <div class="group_item">
                    <input type="button" class="btn" value="查询" onclick="_search(1)">
                    <input type="reset" class="btn btn-reset" value="重置">
                </div>
            </div>
        </form>
    </div>
    <div class="clear"></div>
    <div style="padding: 40px 20px;">
        <div style="text-align: right;">
            <input type="button" class="btn" value="新增" onclick="location.href='/page/add.html'">
        </div>
        <table style="width: 100%;" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th>索引编号</th>
                    <th>索引名称</th>
                    <th>同步任务</th>
                    <th>同步方式</th>
                    <th>全量同步规则</th>
                    <th>增量同步规则</th>
                    <th>最近一次同步</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <div class="pagination">
        </div>
    </div>
</div>
<div id="btn-dialog"></div>
<script type="text/javascript">
     // 搜索
     function _search(currentPage) {
        var url = "/es/listIndex?q=" + $("input[name=indexName]").val() + "&pageNum=" + currentPage;
        $.ajax({
            type : "GET",
            url : url,
            contentType : "application/json",
            dataType : "json",
            success : function(data) {
                if(data.code == 0) {
                    if(data.data.list.length >= 0) {
                        var columns = ""
                        for(var i = 0; i < data.data.list.length; i++) {
                            columns += "<tr>";
                            columns += "<td>" + (i + 1) + "</td>";
                            columns += "<td>" + data.data.list[i].name + "</td>";
                            if(data.data.list[i].job) {
                                if(data.data.list[i].job.switchs && data.data.list[i].job.switchs == 1) {
                                    columns += "<td><button type=\"button\" class=\"switch switch-checked\" switchs='0' onclick='javascript:_switch(this," + data.data.list[i].job.id + ")'><span class=\"switch-inner\">开</span></button></td>";
                                } else {
                                    columns += "<td><button type=\"button\" class=\"switch\" switchs='1' onclick='javascript:_switch(this," + data.data.list[i].job.id + ")'><span class=\"switch-inner\">关</span></button></td>";
                                }
                                columns += "<td>" + data.data.list[i].job.dataSyncTypeDesc + "</td>";
                                columns += "<td>" + (data.data.list[i].job.dataSyncAllRuleDesc ? data.data.list[i].job.dataSyncAllRuleDesc : "") + "</td>";
                                columns += "<td>" + (data.data.list[i].job.dataSyncIncrRuleDesc ? data.data.list[i].job.dataSyncIncrRuleDesc : "") + "</td>";
                                columns += "<td>" + (data.data.list[i].job.lastSyncTimeStr ? data.data.list[i].job.lastSyncTimeStr : "") + "</td>";
                            } else {
                                columns += "<td></td>";
                                columns += "<td></td>";
                                columns += "<td></td>";
                                columns += "<td></td>";
                                columns += "<td></td>";
                            }
                            columns += "<td><a class='btn-add' href=\"javascript: _manual_all(" + data.data.list[i].id + ")\">全量执行</a>&nbsp;&nbsp;<a class='btn-add' href=\"javascript: _manual_incr(" + data.data.list[i].id + ")\">增量执行</a>&nbsp;&nbsp;<a class='btn-edit' href='/page/copy.html?from=" + data.data.list[i].id + "'>复制</a>&nbsp;&nbsp;<a class='btn-edit' href='/page/edit.html?id=" + data.data.list[i].id + "'>编辑</a>&nbsp;&nbsp;<a class='btn-del' href=\"javascript: _delete(" + data.data.list[i].id + ",'" + data.data.list[i].name + "')\">删除</a></td>";
                            columns += "</tr>";
                        }
                        $(".container table tbody").html(columns);
                        $(".pagination").html("");
                        // 分页
                        new pagination({
                            pagination:$('.pagination'),
                            maxPage: 7,
                            startPage: 1,
                            currentPage: currentPage,
                            totalItemCount: data.data.total,
                            totalPageCount: data.data.pages,
                            callback:function(pageNum){
                                _search(pageNum);
                            }
                        });
                    }
                } else {
                    alert(data.message);
                }
            }
        });
     }
    _search(1);
    // 全量执行
    function _manual_all(id) {
        $('#btn-dialog').dialogBox({
            hasClose: true,
            hasMask: true,
            hasBtn: true,
            confirmValue: '确定',
            confirm: function(){
                $.ajax({
                    type: "POST",
                    url: '/es/manual/all?id=' + id,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            $('#btn-dialog').dialogBox({
                                autoHide: true,
                                hasMask: true,
                                time: 2000,
                                title: '成功提示',
                                content: '执行成功～'
                            });
                            setTimeout(function(){ location.href = "/page/list.html"; }, 2000);
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
            },
            cancelValue: '取消',
            title: '全量数据同步',
            content: '确认执行全量数据同步?'
        });

    }
     // 增量执行
     function _manual_incr(id) {
         $('#btn-dialog').dialogBox({
             hasClose: true,
             hasMask: true,
             hasBtn: true,
             confirmValue: '确定',
             confirm: function(){
                 $.ajax({
                     type: "POST",
                     url: '/es/manual/incr?id=' + id,
                     contentType: "application/json",
                     dataType: "json",
                     success: function (data) {
                         if (data.code == 0) {
                             $('#btn-dialog').dialogBox({
                                 autoHide: true,
                                 hasMask: true,
                                 time: 2000,
                                 title: '成功提示',
                                 content: '执行成功～'
                             });
                             setTimeout(function(){ location.href = "/page/list.html"; }, 2000);
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
             },
             cancelValue: '取消',
             title: '增量数据同步',
             content: '确认执行增量数据同步?'
         });
     }
     // 删除
    function _delete(id, name) {
        $('#btn-dialog').dialogBox({
            hasClose: true,
            hasMask: true,
            hasBtn: true,
            confirmValue: '确定',
            confirm: function(){
                $.ajax({
                    type: "DELETE",
                    url: '/es/deleteIndex?id=' + id,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            location.href = "/page/list.html"
                        } else {
                            $('#btn-dialog').dialogBox({
                                autoHide: true,
                                hasMask: true,
                                time: 2000,
                                title: '失败提示',
                                content: '索引' + name + '删除失败～'
                            });
                        }
                    }
                });
            },
            cancelValue: '取消',
            title: '删除索引',
            content: '确认删除索引 ' + name + '?'
        });
    }
    // 开关
    function _switch(obj, id) {
        var job = {};
        var switchs = $(obj).attr("switchs");
        job.id = id;
        job.switchs = switchs;
        $.ajax({
            type: "POST",
            url: '/es/switchs',
            contentType: "application/json",
            dataType: "json",
            data : JSON.stringify(job),
            success: function (data) {
                if (data.code == 0) {
                    if(switchs == 1) {
                        $(obj).attr("switchs", 0);
                        $(obj).find("span").text("开");
                        $(obj).addClass("switch-checked");
                    } else {
                        $(obj).attr("switchs", 1);
                        $(obj).find("span").text("关");
                        $(obj).removeClass("switch-checked");
                    }
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
    }
</script>
</body>
</html>