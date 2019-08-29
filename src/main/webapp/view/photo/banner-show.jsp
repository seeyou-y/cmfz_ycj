<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<script type="text/javascript">
    $(function () {
        $('#banner-table').jqGrid({
            url: '${app}/banner/showAllBanner',
            //引入bootstrap的UI样式
            styleUI: 'Bootstrap',
            datatype: 'json',
            colNames: ['编号', '图片', '标题', '状态', '创建日期', '详细', '最后修改时间'],
            // 指定表单编辑时提交的路径
            editurl: '${app}/banner/operBanner',
            colModel: [
                {name: 'id', align: 'center'},
                {
                    name: 'cover', align: 'center', editable: true,
                    edittype: "file",
                    editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value, options, row) {
                        return '<img style="height: 50px;" src="${app}/view/photo/image/' + row.cover + '"/>';
                    }
                },
                {name: 'title', align: 'center', editable: true},
                {
                    name: 'status', align: 'center', editable: true,
                    edittype: "select",
                    editoptions: {value: "正常:正常;冻结:冻结"}
                },
                {
                    name: 'createDate', align: 'center',
                    formatter: "date",
                    formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}
                },
                {name: 'description', align: 'center', editable: true},
                {
                    name: 'lastUpdateDate', align: 'center',
                    formatter: "date",
                    formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}
                }
            ],
            pager: '#banner-pager',
            rowNum: 5,
            page: 1,
            rowList: [5, 10, 20, 30],
            viewrecords: true,
            rownumbers: true,
            height: 350,
            autowidth: true
        }).navGrid('#banner-pager', {'add': true},
            {
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.data;
                    $.ajaxFileUpload({
                        url: '${app}/banner/upload',
                        secureuri: false,
                        fileElementId: 'cover',
                        data: {id: id},
                        dataType: 'json',
                        success: function (data, status) {
                            $("#banner-table").trigger("reloadGrid");
                        }
                    });
                    return response.responseJSON.mes;
                }
            },
            {
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.data;
                    $.ajaxFileUpload({
                        url: '${app}/banner/upload',
                        secureuri: false,
                        fileElementId: 'cover',
                        data: {id: id},
                        dataType: 'json',
                        success: function (data, status) {
                            $("#banner-table").trigger("reloadGrid");
                        }
                    });
                    return response.responseJSON.mes;
                }
            }, {}
        );
    })
</script>

<div class="panel panel-info" id="emplist">
    <div class="panel-heading text-center">
        <h1 class="panel-title">轮播图信息展示</h1>

    </div>
    <div class="panel-body text-center">
        <table id="banner-table" class="table table-striped table-bordered table-hover">
        </table>
    </div>
    <div id="banner-pager">
    </div>
</div>

