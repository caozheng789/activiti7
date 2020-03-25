<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>lenosp开源脚手架</title>
    <meta name="keywords" content="lenosp,lenos,lenosp开源脚手架,lenos脚手架,lenosp脚手架,lenosp开源框架">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="/layui/css/layui.css">
    <script type="text/javascript" src="/layui/layui.all.js"
            charset="utf-8"></script>
</head>

<body>

<table id="userList" class="layui-hide" lay-filter="user"></table>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看流程图</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script>

    layui.use('table', function () {
        var table = layui.table;
        //方法级渲染
        table.render({
            id: 'userList',
            elem: '#userList'
            ,url:'/getProcessDeploy'
            ,cols: [[
                {field:'id',  title: 'ID', sort: true}
                ,{field:'name', title: '名称'}
                ,{field:'key', title: '键', sort: true}
                ,{field:'version',  title: '版本'}
                ,{field:'resourceName', title: '流程名称',  }
                ,{field:'diagramResourceName', title: '流程图名称',  }
                ,{field:'deploymentId', title: '部署编号', sort: true}
                ,{field: 'right', title: '操作', toolbar: "#barDemo"}
            ]]
            ,page: true
        });

        //监听工具条
        table.on('tool(user)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                detail('查看流程图', 'showBpmn?key=' + data.key, 700, 450);
            } else if (obj.event === 'del') {
                layer.confirm('确定删除用户[<label style="color: #00AA91;">' + data.username + '</label>]?', {
                    btn: ['逻辑删除', '物理删除']
                }, function () {
                    del(data.deploymentId, true);
                }, function () {
                    del(data.deploymentId, false);
                });
            }
        });

    });

    function del(deployId) {
        $.ajax({
            url:"del",
            type:"get",
            data:{deployId:deployId},
            async:false,
            success:function(d){
                if(d.flag){
                    window.top.layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
                    layui.table.reload('userList');
                }else{
                    window.top.layer.msg(d.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
                }
            },error:function(){
                alert('error');
            }
        });
    }
    function detail(title, url, w, h) {
        var number = 1;
        if (title == null || title == '') {
            title = false;
        };
        if (url == null || url == '') {
            url = "error/404";
        };
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        };
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        };
        layer.open({
            id: 'user-detail',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            content: url ,
            // btn:['关闭']
        });
    }


</script>
</body>

</html>
