<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<script type="text/javascript">
    $(function () {
        $("#album-table").jqGrid({
            url: '${app}/album/showAllAlbumByPage',
            datatype: "json",
            styleUI: 'Bootstrap',
            autowidth: true,
            height: 290,
            editurl: '${app}/album/operAlbum',
            colNames: ['ID', '标题', '封面', '星级', '内容简介', '作者', '播音', '集数', '创建时间'],
            colModel: [
                {name: 'id', index: 'id', width: 55, align: "center"},
                {name: 'title', index: 'title', align: "center", width: 90, editable: true},
                {
                    name: 'cover', index: 'cover', align: "center", width: 100, editable: true, edittype: "file",
                    formatter: function (value, option, rows) {
                        return '<img style="width: auto;height: 30px;" src="${app}/view/album/image/' + value + '">';
                    }
                },
                {name: 'score', index: 'score', width: 80, align: "center", editable: true},
                {name: 'detail', index: 'detail', width: 80, align: "center", editable: true},
                {name: 'author', index: 'author', width: 80, align: "center", editable: true},
                {name: 'boradcast', index: 'boradcast', width: 150, align: "center", editable: true},
                {
                    name: 'count', index: 'count', width: 150, align: "center",
                    formatter: function (value, option, rows) {
                        return '<span style="color:#ff8763">共' + value + '集</span>';
                    }
                },
                {name: 'createDate', index: 'createDate', width: 150, align: "center"}
            ],
            rowNum: 5,
            page: 1,
            rowList: [5, 10, 20, 30],
            pager: '#album-pager',
            sortname: 'id',
            viewrecords: true,
            rownumbers: true,
            subGrid: true,
            caption: "Chapter",
            subGridRowExpanded: function (subgrid_id, id) {

                var subgrid_table_id = subgrid_id + "_t";
                var pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        url: "${app}/chapter/findChapterByAlbumId?albumId=" + id,
                        datatype: "json",
                        styleUI: 'Bootstrap',
                        //autowidth: true,
                        editurl: '${app}/chapter/operChapter?albumId=' + id,
                        colNames: ['ID', '标题', '大小', '时长', 'MP3'],
                        colModel: [
                            {name: "id", align: "center", width: 100},
                            {name: "title", align: "center", width: 100, editable: true},
                            {name: "size", align: "center", width: 100},
                            {name: "duration", align: "center", width: 100},
                            {
                                name: "audio", align: "center", width: 200, editable: true, edittype: "file",
                                formatter: function (value, option, rows) {
                                    return '<audio controls preload="metadata" style = "width: 80%;height: 30px;">' +
                                        '<source src="${app}/view/chapter/audio/' + value + '"  type="audio/mpeg">' +
                                        '</audio>';
                                }
                            }
                        ],
                        rowList: [5, 10, 20, 30],
                        rowNum: 5,
                        autowidth: true,
                        page: 1,
                        pager: pager_id,
                        sortname: 'num',
                        viewrecords: true,
                        rownumbers: true,
                        height: '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid', "#" + pager_id, {
                        edit: true,
                        add: true,
                        del: true
                    },
                    {
                        closeAfterEdit: true,
                        afterSubmit: function (response) {
                            var chapterid = response.responseJSON.data;
                            $.ajaxFileUpload({
                                url: '${app}/chapter/upload',
                                secureuri: false,
                                fileElementId: 'audio',
                                data: {id: chapterid},
                                dataType: 'json',
                                success: function (data, status) {
                                    $("#album-table").trigger("reloadGrid");
                                }
                            });
                            return response.responseJSON.mes;
                        }
                    },
                    {
                        closeAfterAdd: true,
                        afterSubmit: function (response) {
                            var chapterid = response.responseJSON.data;
                            $.ajaxFileUpload({
                                url: '${app}/chapter/upload',
                                secureuri: false,
                                fileElementId: 'audio',
                                data: {
                                    id: chapterid,
                                    albumId: id
                                },
                                dataType: 'json',
                                success: function (data, status) {
                                    $("#album-table").trigger("reloadGrid");
                                }
                            });
                            return response.responseJSON.mes;
                        }
                    },
                    {
                        afterSubmit: function (e) {
                            $("#album-table").trigger("reloadGrid");
                            return "123";
                        }
                    }
                );
            }
        }).navGrid("#album-pager", {'add': true},
            {
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.data;
                    $.ajaxFileUpload({
                        url: '${app}/album/upload',
                        secureuri: false,
                        fileElementId: 'cover',
                        data: {id: id},
                        dataType: 'json',
                        success: function (data, status) {
                            $("#album-table").trigger("reloadGrid");
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
                        url: '${app}/album/upload',
                        secureuri: false,
                        fileElementId: 'cover',
                        data: {id: id},
                        dataType: 'json',
                        success: function (data, status) {
                            $("#album-table").trigger("reloadGrid");
                        }
                    });
                    return response.responseJSON.mes;
                }
            }
        );
    });
</script>

<body>
<!--<a href="">1111</a>-->
<div class="panel panel-info" id="emplist">
    <div class="panel-heading text-center">
        <h1 class="panel-title">专辑信息展示</h1>
        <p class="text-right"><a href="${app}/album/fileOutStream">一键导出</a></p>
    </div>
    <div class="panel-body text-center">
        <table id="album-table" class="table table-striped table-bordered table-hover"></table>
    </div>
    <div id="album-pager"></div>
</div>
</body>
