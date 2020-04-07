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
        * {
            margin: 0px;
            padding: 0px;
        }

        body {
            background: #cccc url("/static/image/bg.jpg") top center;
            background-size: contain;
        }

        .container {
            width: 1190px;
            margin: 0 auto;
            padding: 45px;
        }

        .login-cavas {
            background: #fff;
            width: 500px;
            padding: 30px;
            margin-top: 100px;
            text-align: center;
        }

        .login-cavas .title {
            font-size: 24px;
        }

        .login-cavas .crumb {
            color: #aaa;
            font-size: 14px;
            margin-top: 20px;
        }

        .login-cavas input {
            font-size: 14px;
        }

        .login-input {
            width: 400px;
            height: 40px;
            border: none;
            border-bottom: 1px solid #aaa;
            padding: 2px 10px;
        }

        .login-btn {
            cursor: pointer;
            background: #3cc8b4;
            box-sizing: content-box;
            width: 400px;
            height: 40px;
            padding: 2px 10px;
            border-radius: 2px;
            transition: background-color 300ms;

            color: #fff;
            font-size: 18px;
            letter-spacing: 4px;
            font-weight: 500;
        }

        .login-btn:hover {
            background: #2f9e92;
        }

        }
        .login-btn:hover {
            background: #3cc8b4;
        }

        .gap_top {
            margin-top: 20px;
        }

        .gap_top_2 {
            margin-top: 40px;
        }
    </style>
</head>
<body>
<div class="container" style="text-align: center; text-align: -webkit-center;">
    <div class="login-cavas">
        <div class="title">搜索平台</div>
        <div class="crumb">
            If we dream, everything is possible
        </div>
        <div class="gap_top_2">
            <input type="input" class="login-input" name="userName" placeholder="用户名">
        </div>
        <div class="gap_top">
            <input type="password" class="login-input" name="password" placeholder="密码">
        </div>
        <div class="gap_top_2">
            <input type="button" class="login-btn" value="登 录" onclick="_login()">
        </div>
    </div>
</div>
<div id="btn-dialog"></div>
<script type="text/javascript">
    function _login() {
        var user = {};
        user.userName = $("input[name=userName]").val();
        user.password = $("input[name=password]").val();
        $.ajax({
            type: "POST",
            url: '/user/login/check',
            contentType: "application/json",
            dataType: "json",
            data : JSON.stringify(user),
            success: function (data) {
                if (data.code == 0) {
                    location.href = "/page/home.html"
                } else {
                    $('#btn-dialog').dialogBox({
                        autoHide: true,
                        hasMask: true,
                        time: 2000,
                        width: 200,
                        title: '错误提示',
                        content: data.message
                    });
                }
            }
        });
    }


</script>
</body>
</html>