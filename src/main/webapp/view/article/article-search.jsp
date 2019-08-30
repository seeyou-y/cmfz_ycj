<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>

<div class="panel panel-info" id="emplist">
    <div class="panel-heading text-center">
        <div class="input-group">
            <input type="text" class="form-control" id="search-content" placeholder="Search for...">
            <span class="input-group-btn">
        <button class="btn btn-default" id="search-id" type="button">Go!</button>
      </span>
        </div>
    </div>
    <div class="panel-body">
        <div id="content"></div>
    </div>

</div>
<script>
    $("#search-id").click(function () {
        var content = $("#search-content").val();
        $.ajax({
            url: '${app}/article/searchArticle',
            data: {
                content: content
            },
            dataType: 'json',
            type: 'post',
            success: function (data) {
                $("#content").empty();
                $.each(data, function (index, article) {
                    $("#content").append("<hr/>")
                        .append("标题：<span>" + article.title + "</span><br/>")
                        .append("作者：<span>" + article.guruName + "</span><br/>")
                        .append("内容：<span>" + article.content + "</span><br/>")
                })
            },
            error: function (data) {
                alter("出错了~~~~")
            }

        })
    });

</script>
