<style type="text/css">
    .header{
        width: 100%;
        height: 100px;
        background-color: #000033;
        color: #fff;
    }
    .header .title{
        font-size: 24px;
        font-weight: bold;
        line-height: 24px;
        padding-top: 38px;
        padding-left: 38px;
        cursor: pointer;
    }
    .header .title a{
        color: #fff;
        text-decoration: none;
    }
    .header .user {
        position: absolute;
        right: 38px;
        top: 38px;
    }
    .header .user span{
        font-size: 12px;
    }
    .header .user img{
        width: 40px;
        border-radius: 50%;
        vertical-align: middle;
        cursor: pointer;
    }
</style>
<div class="header">
    <div class="title">
        <a href="/">搜索平台</a>
    </div>
    <div class="user">
        <span></span>
        <a href="/page/loginout.html">
            <img src="/static/image/logout.png" alt="avatar">
        </a>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $.ajax({
            type : "GET",
            url : "/user/getCurrentUser",
            contentType : "application/json",
            dataType : "json",
            success : function(data) {
                if(data.code == 0) {
                    $(".header .user span").text(data.data.userName);
                } else {s
                    console(data.message);
                }
            }
        });
    });
</script>