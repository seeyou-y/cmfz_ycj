<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<script type="text/javascript">
    $(function () {
        $("#user-table").jqGrid({
            url: '${app}/user/findAllUserByPage',
            //引入bootstrap的UI样式
            styleUI: 'Bootstrap',
            datatype: 'json',
            colNames: ['编号', '手机', '密码', '加盐', '头像', '法名',
                '真实姓名', '上师法名', '性别', '省份', '城市', '个性签名', '状态', '创建日期', '最后修改时间'],
            // 指定表单编辑时提交的路径
            editurl: '${app}/user/operUser',
            colModel: [
                {name: 'id', align: 'center'},
                {name: 'phone', align: 'center', editable: true},
                {name: 'password', align: 'center', editable: true},
                {name: 'salt', align: 'center'},
                {
                    name: 'photo', align: 'center',
                    formatter: function (value) {
                        return '<img style="width: auto;height: 30px;" src="${app}/view/user/image/' + value + '">';
                    }, editable: true, edittype: "file"
                },
                {name: 'dharma', align: 'center', editable: true},
                {name: 'username', align: 'center', editable: true},
                {
                    name: 'guruName', align: 'center', editable: true,
                    edittype: 'select',
                    editoptions: {value: gettypes()}
                },
                {
                    name: 'sex', align: 'center', editable: true,
                    edittype: 'select', editoptions: {value: "男:男;女:女"}
                },
                {
                    name: 'province', align: 'center',
                    editable: true
                    /* ,
                     edittype: 'select',
                     editoptions: {value: getPro()}*/

                },
                {name: 'city', align: 'center', editable: true},
                {name: 'sign', align: 'center', editable: true},

                {
                    name: 'status', align: 'center',
                    editable: true,
                    edittype: 'select',
                    editoptions: {value: "正常:正常;冻结:冻结"}
                },
                {
                    name: 'createDate', align: 'center',
                    formatter: "date",
                    formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}
                },
                {
                    name: 'lastUpdateDate', align: 'center',
                    formatter: "date",
                    formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}
                }
            ],
            pager: '#user-pager',
            rowNum: 5,
            page: 1,
            rowList: [5, 10, 20, 30],
            viewrecords: true,
            rownumbers: true,
            height: 350,
            autowidth: true
        }).navGrid('#user-pager', {'add': true},
            {
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.data;
                    $.ajaxFileUpload({
                        url: '${app}/user/upload',
                        secureuri: false,
                        fileElementId: 'photo',
                        data: {id: id},
                        dataType: 'json',
                        success: function (data, status) {
                            $("#user-table").trigger("reloadGrid");
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
                        url: '${app}/user/upload',
                        secureuri: false,
                        fileElementId: 'photo',
                        data: {id: id},
                        dataType: 'json',
                        success: function (data, status) {
                            $("#user-table").trigger("reloadGrid");
                        }
                    });
                    return response.responseJSON.mes;
                }
            }, {}
        );

        //获取所有上师
        function gettypes() {
            //动态生成select内容
            var str = "";
            $.ajax({
                type: "post",
                async: false,
                url: "${app}/guru/findAllGuru",
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        var length = data.length;
                        for (var i = 0; i < length; i++) {
                            if (i != length - 1) {
                                str += data[i].dharma + ":" + data[i].dharma + ";";
                            } else {
                                // 这里是option里面的 value:label
                                str += data[i].dharma + ":" + data[i].dharma;
                            }
                        }
                    }
                }
            });
            return str;
        }

    })
</script>
<div class="panel panel-info" id="emplist">
    <div class="panel-heading text-center">
        <h1 class="panel-title">用户信息展示</h1>
        <p class="panel-title text-right" id="loginer"></p>
    </div>
    <div class="panel-body text-center">
        <table id="user-table" class="table table-striped table-bordered table-hover">
        </table>
    </div>
    <div id="user-pager">
    </div>
</div>

