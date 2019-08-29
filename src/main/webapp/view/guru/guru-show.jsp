<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<script type="text/javascript">
    $(function () {
        $("#guru-table").jqGrid({
            url: '${app}/guru/findAllGuruByPage',
            //引入bootstrap的UI样式
            styleUI: 'Bootstrap',
            datatype: 'json',
            colNames: ['编号', '法名', '状态', '头像', '创建日期'],
            // 指定表单编辑时提交的路径
            editurl: '${app}/guru/operGuru',
            colModel: [
                {name: 'id', align: 'center'},
                {name: 'dharma', align: 'center', editable: true},
                {
                    name: 'status', align: 'center', editable: true,
                    edittype: "select",
                    editoptions: {value: "正常:正常;冻结:冻结"}
                },
                {
                    name: 'photo', align: 'center', editable: true,
                    edittype: "file",
                    editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value) {
                        return '<img style="width: auto;height: 30px;" src="${app}/view/guru/image/' + value + '">';
                    }
                },
                {
                    name: 'createDate', align: 'center',
                    formatter: "date",
                    formatoptions: {scrformat: 'Y-m-d', newformat: 'Y-m-d'}
                }
            ],
            pager: '#guru-pager',
            rowNum: 5,
            page: 1,
            rowList: [5, 10, 20, 30],
            viewrecords: true,
            rownumbers: true,
            height: 350,
            autowidth: true
        }).navGrid('#guru-pager', {'add': true},
            {
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.data;
                    $.ajaxFileUpload({
                        url: '${app}/guru/upload',
                        secureuri: false,
                        fileElementId: 'photo',
                        data: {id: id},
                        dataType: 'json',
                        success: function (data, status) {
                            $("#guru-table").trigger("reloadGrid");
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
                        url: '${app}/guru/upload',
                        secureuri: false,
                        fileElementId: 'photo',
                        data: {id: id},
                        dataType: 'json',
                        success: function (data, status) {
                            $("#guru-table").trigger("reloadGrid");
                        }
                    });
                    return response.responseJSON.mes;
                }
            }, {}
        )

    })
</script>
<div class="panel panel-info" id="emplist">
    <div class="panel-heading text-center">
        <h1 class="panel-title">上师信息展示</h1>
    </div>
    <div class="panel-body text-center">
        <table id="guru-table" class="table table-striped table-bordered table-hover">
        </table>
    </div>
    <div id="guru-pager">
    </div>
</div>

