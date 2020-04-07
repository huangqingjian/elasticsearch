<!doctype>
<html lang="zh-CN">
<head>
    <meta charset=utf-8" />
    <title>搜索平台</title>
    <script type="text/javascript" src="/static/js/jquery-2.0.3.min.js"></script>
    <style type="text/css">
        *{
            margin: 0px;
            padding: 0px;
        }
        body{
            font-size: 16px;
        }
        .container{
            width: 1190px;
            margin: 0 auto;
            padding: 45px;
        }
        .gap_top_1{
            margin-top: 10px;
        }
        .gap_top_2{
            margin-top: 20px;
        }
        .gap_left_1{
            margin-left: 10px;
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
        .intro{
            line-height: 28px;
            font-size: 14px;
        }
        .tip{
            color: #ff0000;
        }
    </style>
</head>
<body>
    <#include "/common/header.ftl"/>
    <div class="container">
        <h3>搜索平台接入文档</h3>
        <div class="gap_top_2">
            我们的宗旨：助力开发构建搜索通道，一站式接入搜索引擎，提升研发效率。接入我们，您只需做以下几件事：
        </div>
        <div class="gap_top_2">
            <div>1、在搜索平台平台创建您的索引任务，<a href="/page/add.html">点此</a>立马体验。</div>
            <div class="gap_left_2 intro">
                <div class="gap_top_1">
                    a、索引名称不能是中文，索引分片是指您的索引在elasticsearch的存储分片，索引副本是指索引分片的副本数
                </div>
                <div class="gap_top_1">
                    b、需要指定您的索引映射，我们将依据您指定的映射信息帮您创建指定索引，字段类型为text并且允许字段被索引情况下，您需指定索引分词等；如果您希望字符数据不被分词，您可以指定字段类型为keyword
                </div>
                <div class="gap_top_1">
                    c、数据同步，目前我们为您提供了2中数据同步方式：
                    <div class="gap_top_1 gap_left_2">
                        1）sql，如果您是增量方式同步，sql中你需要指定#startTime#，我们将动态替换#startTime#帮您同步数据。示例如下
                        <div class="gap_left_2">
                            <div>select id,</div>
                            <div>name</div>
                            <div>from tbl_portfolio</div>
                            <div>where is_deleted = 0 and update_time > #startTime#</div>
                        </div>
                    </div>
                    <div class="gap_top_1 gap_left_2">
                        2）接口同步，对于复杂逻辑，sql同步可能实现困难，我们提供了基于接口同步方式。接口同步我们约定：您的入参必须包含pageNum（当前页码）、pageSize（页大小）方便我们分批同步；对于增量同步，您还需提供startTime（日期类型）入参。您的返回值格式：
                        <div class="gap_left_2">
                            {
                            <div class="gap_left_2">"code" : "0",</div>
                            <div class="gap_left_2">"message" : "错误信息",</div>
                            <div class="gap_left_2">"data" : [</div>
                            <div class="gap_left_4">{</div>
                            <div class="gap_left_6">具体数据</div>
                            <div class="gap_left_4">},</div>
                            <div class="gap_left_4">{</div>
                            <div class="gap_left_6">具体数据</div>
                            <div class="gap_left_4">}</div>
                            <div class="gap_left_2">]</div>
                            }
                        </div>
                        其中code值为0表示接口响应成功。
                    </div>
                </div>
            </div>
            <div class="gap_top_2">
                3、补充说明
            </div>
            <div class="gap_left_2 intro tip">
                <div class="gap_top_1">
                    a、您自定义的映射字段如果为date类型，请确认sql或接口返回的对应字段值格式为:yyyy-MM-dd HH:mm:ss，否则可能数据无法正常插入搜索引擎。
                </div>
            </div>
        </div>
    </div>
</body>
</html>
