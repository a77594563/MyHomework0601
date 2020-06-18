<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>我的投資組合<投資者管理></title>
        <%@include file="/WEB-INF/jsp/include/head.jspf" %>
        <script>
            $(document).ready(function(){
                $("#myTable").on("click","tr",function(){
                    
                });
            });
            
            function table_list(){
                $get("/MyHomework/mvc/portfolio/investor/",function(data, status){
                    console.log("data: " + data);
                    $("#myTable tbody > tr").remove();
                    $.each(datas, function (i, item) {
                        var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td></tr>';
                        $('#myTable').append(String.format(html,
                                item.id,
                                item.username,
                                item.password,
                                item.email,
                                item.balance,
                                item.code,
                                item.pass
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
                    <h1>Investor</h1>
                    <h2>投資人</h2>
                </div>

                <table>
                    <td valign="top">
                        <div class="content">
                            <form id="myform" class="pure-form">
                                <fieldset>
                                    <legend> <h2 class="content-subhead">資料維護</h2> </legend>

                                    <input id="id" name="id" placeholder="ID" readonly="true"/><p />
                                    <input id="username" name="username" placeholder="username"/><p />
                                    <input id="password" name="password" placeholder="password"/><p />
                                    <input id="email" name="email" placeholder="email"/><p />
                                    <input id="balance" name="balance" placeholder="balance" type="number"/><p />

                                    <button id="add" type="button" class="pure-button pure-button-primary">新增</button>
                                    <button id="upt" type="button" class="pure-button pure-button-primary">修改</button>
                                    <button id="del" type="button" class="pure-button pure-button-primary">刪除</button>
                                </fieldset>
                            </form>
                        </div>
                    </td>
                    <td valign="top">
                        <div class="content">
                            <form class="pure-form">
                                <fieldset>
                                    <legend> <h2 class="content-subhead">資料列表</h2> </legend>

                                    <table id="myTable" class="pure-table pure-table-bordered">
                                        <thead>
                                            <tr>
                                                <th>id</th>
                                                <th>username</th>
                                                <th>password</th>
                                                <th>email</th>
                                                <th>balance</th>
                                                <th>code</th>
                                                <th>pass</th>
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
