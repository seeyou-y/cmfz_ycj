<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<script type="text/javascript">
    $(function () {
        $.ajax({
            url: '${app}/user/getCountByDays',
            type: 'get',
            dataType: 'json',
            success: function (result) {
                var myChart = echarts.init(document.getElementById('main'));
                var option = {
                    title: {
                        text: '萌新入坑数量展示'
                    },
                    tooltip: {},
                    legend: {
                        data: ['人数']
                    },
                    xAxis: {
                        data: ["一周内", "两周内", "三周内", "一月内", "两月内", "半年内"]
                    },
                    yAxis: {},
                    series: [{
                        name: '人数',
                        type: 'bar',
                        data: [result.data1, result.data2, result.data3, result.data4, result.data5, result.data6]
                    }]
                };
                myChart.setOption(option);
            },
            error: function () {
                alert("出错了~~~")
            }
        })

        var goEasy = new GoEasy({
            appkey: "BC-5ef79b4d44d2467799f085015a79dff8"
        });
        goEasy.subscribe({
            channel: "my_channel",
            onMessage: function (message) {
                var result = eval("(" + message.content + ")");
                var myChart = echarts.init(document.getElementById('main'));
                var option = {
                    title: {
                        text: '萌新入坑数量展示'
                    },
                    tooltip: {},
                    legend: {
                        data: ['人数']
                    },
                    xAxis: {
                        data: ["一周内", "两周内", "三周内", "一月内", "两月内", "半年内"]
                    },
                    yAxis: {},
                    series: [{
                        name: '人数',
                        type: 'bar',
                        data: [result.data1, result.data2, result.data3, result.data4, result.data5, result.data6]
                    }]
                };
                myChart.setOption(option);
            }
        });
    })
</script>
<body>
<div id="main" style="width: 100%;height:400px;"></div>
</body>
