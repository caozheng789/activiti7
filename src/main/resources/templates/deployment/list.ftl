<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/css/layui.css"  media="all">


</head>
<body>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>已部署业务流程</legend>
</fieldset>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs"  data-method="edit">查看流程图</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" data-type="del">删除</a>
</script>
<table class="layui-hide" id="deployList"></table>


<script src="../layui/layui.js" charset="utf-8"></script>
<script>

    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#deployList'
            ,url:'/getProcessDeploy'

            ,cols: [[
                {checkbox: true, fixed: true, width: '5%'}
                ,{field:'id',  title: 'ID', sort: true}
                ,{field:'name', title: '名称'}
                ,{field:'key', title: '键', sort: true}
                ,{field:'version',  title: '版本'}
                ,{field:'resourceName', title: '资源名',  }
                ,{field:'deploymentId', title: '部署编号', sort: true}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo'}
            ]]
            ,page: true
        });

    });



//    var $ = layui.$, active = {
//        edit: function () {
//            console.log(123)
//            var checkStatus = table.checkStatus('deployList')
//                    , data = checkStatus.data;
////            if (data.length != 1) {
////                layer.msg('请选择一行编辑,已选['+data.length+']行', {icon: 5});
////                return false;
////            }
//            layer.msg("data[0].id = "+data[0].id,{icon: 5})
//            //open('修改保养计划', 'updateSerPlanShow?id=' + data[0].id, 1000, 700);
//        }
//    }
//    $('.layui-btn').on('click', function () {
//        console.log(213)
//        var type = $(this).data('type');
//        active[type] ? active[type].call(this) : '';
//    });


    function open(title, url, w, h) {
        if (title == null || title == '') {
            title = false;
        }
        if (url == null || url == '') {
            url = "404.html";
        }
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        }
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        }
        layer.open({
            id: 'open1',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url
        });
    }
</script>

</body>
</html>