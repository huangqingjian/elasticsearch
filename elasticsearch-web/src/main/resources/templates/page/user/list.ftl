<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>搜索平台</title>
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
                <label class="form_label">用户名称</label>
                <input type="text" class="form-input" name="indexName" placeholder="请输入您的用户名称">
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
            <input type="button" class="btn" value="新增" onclick="_add()">
        </div>
        <table style="width: 100%;" cellspacing="0" cellpadding="0">
            <thead>
            <tr>
                <th>用户编号</th>
                <th>用户名</th>
                <th>是否管理员</th>
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
        var url = "/user/list?q=" + $("input[name=indexName]").val() + "&pageNum=" + currentPage;
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
                            columns += "<td>" + data.data.list[i].userName + "</td>";
                            if(data.data.list[i].managed && data.data.list[i].managed == 1) {
                                columns += "<td><button type=\"button\" class=\"switch switch-checked\" managed='0' onclick='javascript:_managed(this," + data.data.list[i].id + ")'><span class=\"switch-inner\">是</span></button></td>";
                            } else {
                                columns += "<td><button type=\"button\" class=\"switch\" managed='1' onclick='javascript:_managed(this," + data.data.list[i].id + ")'><span class=\"switch-inner\">否</span></button></td>";
                            }
                            columns += "<td><a class='btn-del' href=\"javascript: _delete(" + data.data.list[i].id + ",'" + data.data.list[i].name + "')\">删除</a></td>";
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
    // 管理员
    function _managed(obj, id) {
        var user = {};
        var managed = $(obj).attr("managed");
        user.id = id;
        user.managed = managed;
        $.ajax({
            type: "POST",
            url: '/user/managed',
            contentType: "application/json",
            dataType: "json",
            data : JSON.stringify(user),
            success: function (data) {
                if (data.code == 0) {
                    if(managed == 1) {
                        $(obj).attr("managed", 0);
                        $(obj).find("span").text("是");
                        $(obj).addClass("switch-checked");
                    } else {
                        $(obj).attr("managed", 1);
                        $(obj).find("span").text("否");
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
                    url: '/user/deleteUser?id=' + id,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            location.href = "/page/user.html"
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
            title: '删除用户',
            content: '确认删除用户?'
        });
    }
    function _add() {
        $('#btn-dialog').dialogBox({
            hasClose: true,
            hasMask: true,
            hasBtn: true,
            confirmValue: '确定',
            confirm: function(){
                var user = {};
                user.userName = $(".user_name input").val();
                user.password = $(".password input").val();
                $.ajax({
                    type: "POST",
                    url: '/user/saveOrUpdate',
                    data : JSON.stringify(user),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            location.href = "/page/user.html"
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
            width: 500,
            height: 250,
            title: '新增用户',
            content: '<div class="user_name"><label class="input_label">用户名称</label><input type="text" class="normal-input" name="userName"></div><div class="password gap_top"><label class="input_label">用户密码</label><input type="password" class="normal-input" name="password"></div>'
        });
    }
</script>
</body>
</html>