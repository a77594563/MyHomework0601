<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>我的投資組合<觀察股></title>
        <%@include file="/WEB-INF/jsp/include/head.jspf" %>
        
        <script>
            var watch_id = ${sessionScope.watch_id};
            var watch = null;
            
            $(document).ready(function () {
                /*$("#myTable").on("click", "tr", function () {
                    var id = $(this).find('td').eq(0).text().trim();
                    console.log(id);
                    $.get("/MyHomework/mvc/portfolio/investor/" + id, function (data, status) {
                        console.log(JSON.stringify(data));
                        $('#myform').find('#id').val(data.id);
                        $('#myform').find('#username').val(data.username);
                        $("#myform").find("#password").val(data.password);
                        $("#myform").find("#email").val(data.email);
                        $("#myform").find("#balance").val(data.balance);
                    });
                });

                $("#add").on("click", function () {
                    var jsonObj = $("#myform").serializeObject()
                    var jsonStr = JSON.stringify(jsonObj);
                    $.ajax({
                        url: "/MyHomework/mvc/portfolio/investor/",
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        data: jsonStr,
                        async: true,
                        cache: false,
                        processData: false,
                        success: function (resposeJsonObject) {
                            //alert(JSON.stringify(resposeJsonObject));
                            table_list();
                        }
                    });
                });

                $("#upt").on("click", function () {
                    var jsonObj = $("#myform").serializeObject();
                    var jsonStr = JSON.stringify(jsonObj);
                    $.ajax({
                        url: "/MyHomework/mvc/portfolio/investor/" + jsonObj.id,
                        type: "PUT",
                        contentType: "application/json; charset=utf-8",
                        data: jsonStr,
                        async: true,
                        cache: false,
                        processData: false,
                        success: function (resposeJsonObject) {
                            table_list();
                        }
                    });
                });


                $("#del").on("click", function () {
                    var id = $("#myform").find("#id").val();
                    $.ajax({
                        url: "/MyHomework/mvc/portfolio/investor/" + id,
                        type: "DELETE",
                        async: true,
                        cache: false,
                        processData: false,
                        success: function (resposeJsonObject) {
                            table_list();
                        }
                    });
                });*/

                // 資料列表
                table_list();
            });

            function table_list() {
                $.get("/MyHomework/mvc/portfolio/watch/" + watch_id, function (datas, status) {
                    console.log(JSON.stringify(data));
                    $("#myform").find("#id").val(datas.id);
                    $("#myform").find("#name").val(datas.name);
                    watch = datas; // 設定 watch 變數資料
                    
                    $("#myTable tbody > tr").remove();
                    $.each(watch.tStocks, function (i, item) {
                        var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td tstock_id="{4}">{5}</td></tr>';
                        delbtn_html = '<button type="button" class="pure-button pure-button-primary">刪除</button>';
                        $('#myTable').append(String.format(html,
                                item.id,
                                item.name,
                                item.symbol,
                                item.classify.name,
                                item.id,
                                delbtn_html
                                ));
                    });
                });
            }
            
            function table_list2() {
                $.get("/MyHomework/mvc/portfolio/tstock/" + watch_id, function (datas, status) {
                    console.log("Datas: " + datas);
                    $("#myform").find("#id").val(datas.id);
                    $("#myform").find("#name").val(datas.name);
                    watch = datas; // 設定 watch 變數資料
                    
                    $("#myTable tbody > tr").remove();
                    $.each(watch.tStocks, function (i, item) {
                        var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td tstock_id="{4}">{5}</td></tr>';
                        delbtn_html = '<button type="button" class="pure-button pure-button-primary">刪除</button>';
                        $('#myTable').append(String.format(html,
                                item.id,
                                item.name,
                                item.symbol,
                                item.classify.name,
                                item.id,
                                delbtn_html
                                ));
                    });
                });
            }
        </script> 
    </head>
    <body>
        <div id="layout">
            <!-- Menu toggle -->
            <%@include file="/WEB-INF/jsp/include/toggle.jspf" %>

            <!--Menu -->
            <%@include file="/WEB-INF/jsp/include/menu.jspf" %>

            <div id="main">
                <div class="header">
                    <h1>Watch</h1>
                    <h2>我的觀察股, Watch_name: ${sessionScope.watch_id}</h2>
                </div>

                <table>
                    <td valign="top">
                        <div class="content">
                            <form id="myform" class="pure-form">
                                <fieldset>
                                    <legend> <h2 class="content-subhead">資料維護</h2> </legend>

                                    <input id="id" name="id" placeholder="ID" readonly="true"/><p />
                                    <input id="name" name="name" placeholder="Watch 名稱"/><p />

                                    <button id="upt" type="button" class="pure-button pure-button-primary">修改</button>
                                </fieldset>
                            </form>
                        </div>
                    </td>
                    <td valign="top">
                        <div class="content">
                            <form class="pure-form">
                                <fieldset>
                                    <legend> <h2 class="content-subhead">Watch 資料列表</h2> </legend>

                                    <table id="myTable" class="pure-table pure-table-bordered">
                                        <thead>
                                            <tr>
                                                <th>id</th>
                                                <th>name</th>
                                                <th>symbol</th>
                                                <th>classify name</th>
                                                <th>刪除</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- 自動插入列表內容 -->
                                        </tbody>
                                    </table>
                                </fieldset>
                            </form>
                        </div>
                    </td>
                    <td valign="top">
                        <div class="content">
                            <form class="pure-form">
                                <fieldset>
                                    <legend> <h2 class="content-subhead">TStock 資料列表</h2> </legend>

                                    <table id="myTable2" class="pure-table pure-table-bordered">
                                        <thead>
                                            <tr>
                                                <th>id</th>
                                                <th>name</th>
                                                <th>symbol</th>
                                                <th>classify name</th>
                                                <th>增加</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- 自動插入列表內容 -->
                                        </tbody>
                                    </table>
                                </fieldset>
                            </form>
                        </div>
                    </td>
                </table>

            </div>
        </div>

        <!-- Foot -->
        <%@include file="/WEB-INF/jsp/include/foot.jspf"  %>       
    </body>
</html>
