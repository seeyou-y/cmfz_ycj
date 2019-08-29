<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script type="text/javascript" src="${app}/kindeditor/kindeditor-all-min.js"></script>
    <script type="text/javascript" src="${app}/kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#article-table").jqGrid({
                url: '${app}/article/findAllArticleByPage',
                //引入bootstrap的UI样式
                styleUI: 'Bootstrap',
                datatype: 'json',
                colNames: ['编号', '作者', '详细', '标题', '出版时间', '操作'],
                // 指定表单编辑时提交的路径
                editurl: '${app}/article/operArticle',
                colModel: [
                    {name: 'id', align: 'center'},
                    {name: 'guruName', align: 'center', editable: true},
                    {name: 'content', align: 'center', editable: true},
                    {name: 'title', align: 'center', editable: true},
                    {
                        name: 'publishDate', align: 'center', editable: true,
                        formatter: "date",
                        formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}
                    },
                    {
                        name: "oper", align: 'center',
                        formatter: function (value, option, rows) {
                            return "<a class='btn btn-primary' onclick=\"openModal('edit','" + rows.id + "')\">修改</a>";
                        }
                    }
                ],
                pager: '#article-pager',
                rowNum: 5,
                page: 1,
                rowList: [5, 10, 20, 30],
                viewrecords: true,
                rownumbers: true,
                height: 350,
                autowidth: true
            }).navGrid('#article-pager', {
                'add': false,
                'edit': false,
                'del': true
            }, {closeAfterEdit: true}, {closeAfterAdd: true}, {closeAfterDel: true});
        });
        //富文本编辑器
        var options = {
            width: '100%',
            uploadJson: '${app}/article/upload',
            //上传文件名
            filePostName: 'aa',
            allowFileManager: true,
            fileManagerJson: '${app}/article/cloud',
            //回调函数
            afterBlur: function () {
                this.sync();
            }
        };
        KindEditor.create('#editor_id', options);

        //打开模态框
        function openModal(oper, id) {
            //alert(oper);
            //加载上师信息
            $.ajax({
                type: "post",
                async: false,
                url: "${app}/guru/findAllGuru",
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        var length = data.length;
                        $("#article-author").empty();
                        for (var i = 0; i < length; i++) {
                            var option = $("<option value='" + data[i].dharma + "'>" + data[i].dharma + "</option>")
                            $("#article-author").append(option);
                        }
                    }
                }
            });
            KindEditor.html("#editor_id", "");
            $("#article-form")[0].reset();
            var article = $("#article-table").jqGrid("getRowData", id);
            $("#article-id").val(article.id);
            $("#article-oper").val(oper);
            $("#article-title").val(article.title);
            $("#article-author").val(article.guruName);
            KindEditor.html("#editor_id", article.content);
            //展示模态框
            $("#myModal").modal('show');

        }

        //保存文章信息
        function saveArticle() {
            $.ajax({
                url: '${app}/article/operArticle',
                type: 'post',
                data: $("#article-form").serialize(),
                dataType: 'json',
                success: function (data) {
                    //关闭模态框
                    $("#myModal").modal('hide');
                    //刷新jqGrid
                    $("#article-table").trigger("reloadGrid");
                },
                error: function () {
                    alert("出错了~~~~")
                }
            })
        }

    </script>
</head>

<div class="panel panel-info" id="emplist">
    <div class="panel-heading text-center">
        <ul class="nav nav-pills">
            <li role="presentation" class="active"><a href="#">展示所有</a></li>
            <li role="presentation"><a onclick="openModal('add')">添加文章</a></li>
        </ul>
    </div>
    <div class="panel-body text-center">
        <table id="article-table" class="table table-striped table-bordered table-hover">
        </table>
    </div>
    <div id="article-pager">
    </div>

    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">文章信息</h4>
                </div>
                <div class="modal-body">
                    <form class="form-inline" id="article-form">
                        <div class="form-group">
                            <label for="article-title">标题：</label>
                            <input type="hidden" name="id" class="form-control" id="article-id">
                            <input type="hidden" name="oper" class="form-control" id="article-oper">
                            <input type="text" name="title" class="form-control" id="article-title"
                                   placeholder="请输入标题~~~">
                        </div>
                        <div class="form-group">
                            <label for="article-author">作者：</label>
                            <select name="guruName" id="article-author" class="form-control">
                            </select>
                        </div>
                        <hr>
                        <div class="form-group">
                            <label for="editor_id">文章内容：</label>
                            <textarea id="editor_id" name="content" style="width:700px;height:300px;">
                            </textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveArticle()">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

</div>
</html>