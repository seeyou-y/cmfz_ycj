<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <!--引入样式文件-->
    <link rel="stylesheet" href="${app}/statics/boot/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${app}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" type="text/css">
    <script type="text/javascript" src="${app}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="${app}/statics/boot/js/bootstrap.js"></script>
    <script src="${app}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script src="${app}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="${app}/statics/jqgrid/js/ajaxfileupload.js" type="text/javascript"></script>
    <script charset="utf-8" src="${app}/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${app}/kindeditor/lang/zh-CN.js"></script>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script type="text/javascript" src="${app}/statics/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${app}/statics/echarts/china.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#out").click(function () {
                $.ajax({
                    url: "${app}/admin/logOut",
                    success: function (data) {
                        window.location.href = "${app}/login/login.jsp";
                    },
                    error: function () {
                        alter("出错了！！！！")
                    }
                })
            });
        })
    </script>
</head>
<body>

<div class="container-fluid">
    <!--导航栏-->
    <div>
        <nav class="navbar navbar-default" style="background-color:black" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">持名法州后台管理系统</a>
                </div>
                <div>

                    <shiro:authenticated>
                        欢迎您：<span style="color: red"><shiro:principal></shiro:principal> </span>&emsp;&emsp;&emsp;
                        <a id="out" href="javascript:void(0)">登出</a>
                    </shiro:authenticated>
                </div>

            </div>
        </nav>
    </div>
    <!--布局-->
    <div class="row">
        <div class="col-md-3">
            <div class="panel-group" id="pg">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <a href="#pn1" class="panel-title" data-toggle="collapse"
                           data-parent="#pg">
                            轮播图管理
                        </a>
                    </div>
                    <div class="panel-collapse collapse" id="pn1">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a
                                        href="javascript:$('#showHtml').load('${app}/view/photo/banner-show.html')">轮播图详情</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <a href="#pn2" class="panel-title" data-toggle="collapse"
                           data-parent="#pg">
                            专辑管理
                        </a>
                    </div>
                    <div class="panel-collapse collapse" id="pn2">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a
                                        href="javascript:$('#showHtml').load('${app}/view/album/album-show.html')">查看专辑</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <a href="#pn3" class="panel-title" data-toggle="collapse"
                           data-parent="#pg">
                            文章管理
                        </a>
                    </div>
                    <div class="panel-collapse collapse" id="pn3">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a
                                        href="javascript:$('#showHtml').load('${app}/view/article/article-show.html')">查看文章</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <a href="#pn4" class="panel-title" data-toggle="collapse"
                           data-parent="#pg">
                            用户管理
                        </a>
                    </div>
                    <div class="panel-collapse collapse" id="pn4">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a
                                        href="javascript:$('#showHtml').load('${app}/view/user/user-show.html')">查看用户</a>
                                </li>
                                <li class="list-group-item"><a
                                        href="javascript:$('#showHtml').load('${app}/view/user/user-new.html')">入坑统计</a>
                                </li>
                                <li class="list-group-item"><a
                                        href="javascript:$('#showHtml').load('${app}/view/user/user-map.html')">地域分布</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <shiro:hasRole name="svip">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <a href="#pn5" class="panel-title" data-toggle="collapse"
                               data-parent="#pg">
                                上师管理
                            </a>
                        </div>
                        <div class="panel-collapse collapse" id="pn5">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><a
                                            href="javascript:$('#showHtml').load('${app}/view/guru/guru-show.html')">查看上师</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </shiro:hasRole>
            </div>
        </div>
        <div class="col-md-9">
            <div id="showHtml">
                <div>
                    <div class="jumbotron">
                        <p>欢迎登陆持名法州后台管理系统！</p>
                    </div>
                </div>
                <div><img src="${app}/statics/boot/001.jpg" style="width: 1000px;height: 500px"></div>
            </div>
        </div>
    </div>

</div>

</body>
</html>