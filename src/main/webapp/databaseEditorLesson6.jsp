<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>database editor</title>
  <style>
    * {
      margin: 0px;
      padding: 0px;
      font-size: 14px;
    }
    #all {
      background: #f0f0f0;
      display: flex;
      height: 100%;
    }
    #leftMenu {
      width: 20%;
      padding: 20px;
      background: #fff;
    }
    #leftMenu .content li {
      list-style: none;
      width: 120px;
    }
    #leftMenu .content li a{
      color: darkblue;
      text-decoration: none;
    }
    #leftMenu .content li a:hover{
      color: aqua;
      text-decoration: underline;
    }
    #rightContent {
      width: 80%;
      padding: 20px;
    }
    .tableContainer {
      width: 100%;
      display: flex;
    }
    .tableName {
      width: 70%;
    }
    .tableMeta {
      width: 30%;
    }
    .tableMeta .content table {
      width: 100%;
      text-align: left;
    }
    #rightContent .content {
      height: 400px;
      overflow: scroll;
      border: 1px solid #ccc;
    }
    #rightContent .content li {
      list-style: none;
      width: 240px;
      float: left;
    }
    #rightContent .content li a{
      color: darkblue;
      text-decoration: none;
    }
    #rightContent .content li a:hover{
      color: aqua;
      text-decoration: underline;
    }

  </style>
  <script src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js" type="text/javascript"></script>
  <script>
    function getTableByDatabase(databaseName) {
        $("#tableItem li").remove();
      var xmlhttp = new XMLHttpRequest();//创建 XMLhttpRequest 对象
      xmlhttp.open("POST", "<%=request.getContextPath()%>/databaseEditorServlet?action=tableNameList&databaseName=" + databaseName);
      xmlhttp.send();//发送请求
      xmlhttp.onreadystatechange = ()=> {//readyState每次改变就会执行onreadystatechange
        if(xmlhttp.readyState == 4){//readyState是请求状态
          if(xmlhttp.status == 200){
            var tableArray = eval("(" + xmlhttp.responseText + ")");
            for(i in tableArray) {
              var item = tableArray[i]
                $("#tableItem").append("<li><a href=\"javascript:getTableDataByTable('"+databaseName+"', '"+item.tableName+"')\">"+item.tableName+"</a></li>")
            }
          }
        }
      }
    }
    function getTableDataByTable(databaseName, tableName) {
        $("#tableMeta tr td").remove();
        $("#tableMeta tr").remove();
        $.ajax({
            url:"<%=request.getContextPath()%>/databaseEditorServlet?action=tableMeta&databaseName="+databaseName+"&tableName=" + tableName,
            dataType: "json",
            success: (result) => {
                for(i in result) {
                    var item = result[i]
                    $("#tableMeta").append("<tr>" +
                        "<td>"+item.field+"</td>" +
                        "<td>"+item.type+"</td>" +
                        "<td>"+item.key+"</td>" +
                        "</tr>")
                }
            },
            error: (XMLHttpRequest, textStatus, errorThrown) =>{
                console.error(XMLHttpRequest)
                console.error(textStatus)
                console.error(errorThrown)
            }
        });

        $("#tableData tr td").remove()
        $("#tableData tr").remove()
        $.ajax({
            url:"<%=request.getContextPath()%>/databaseEditorServlet?action=tableData&databaseName="+databaseName+"&tableName=" + tableName,
            dataType: "json",
            success: (result) => {
              console.log(result)
              var columnCount = result.columnCount
              var tableDataList = result.tableDataList

              for(i in tableDataList) {
                var row = tableDataList[i]
                var rowString = "<tr>"
                for(var j = 1 ; j <= columnCount; j ++) {
                  rowString += "<td>"+row[j]+"</td>"
                }
                rowString +="</tr>"
                $("#tableData").append(rowString)
              }
            },
            error: (XMLHttpRequest, textStatus, errorThrown) =>{
                console.error(XMLHttpRequest)
                console.error(textStatus)
                console.error(errorThrown)
            }
        });
    }
  </script>
</head>
<body>
  <div id="all">
    <div id="leftMenu">
      <h4>数据库菜单</h4>
      <div class="content">
        <ul>
          <%
            List<String> databaseList = (List<String>)request.getAttribute("databaseList");
            for(String databaseName : databaseList) {
          %>
          <li><a href="javascript: getTableByDatabase('<%=databaseName%>')"><%=databaseName%></a></li>
          <%}%>
        </ul>
      </div>
    </div>
    <div id="rightContent">
      <div class="tableContainer">
        <div class="tableName">
            <h4>数据库表</h4>
            <div class="content">
                <ul id="tableItem">
<%--                  <li><a href="<%=request.getContextPath()%>/databaseServlet?databaseName=<%=request.getAttribute("databaseName")%>&tableName=<%=tableName%>"><%=tableName%></a></li>--%>
                </ul>
            </div>
        </div>

          <div class="tableMeta">
            <h4>元数据</h4>
            <div class="content">
              <table>
                <tr>
                  <th>Field</th>
                  <th>Type</th>
                  <th>Key</th>
                </tr>
                  <tbody id="tableMeta"><!--表格列元数据-->
                  </tbody>
              </table>
            </div>
          </div><!-- tableMeta end -->
      </div><!-- tableContainer end -->
        <div class="tableDataContainer">
          <h4>表数据</h4>
          <div class="content">
            <table id="tableData">
            </table>
          </div>
        </div><!-- tableDataContainer end -->
      </div>
  </div>
</body>
</html>
