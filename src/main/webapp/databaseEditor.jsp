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
          <li><a href="<%=request.getContextPath()%>/databaseServlet?databaseName=<%=databaseName%>"><%=databaseName%></a></li>
          <%}%>
        </ul>
      </div>
    </div>
    <div id="rightContent">
      <div class="tableContainer">
        <div class="tableName">
            <h4>数据库表</h4>
            <div class="content">
              <ul>
              <%
                Object o = request.getAttribute("tableList");
                if(o != null) {
                  List<String> tableList = (List<String>) o;
                  for(String tableName : tableList) {
              %>
                  <li><a href="<%=request.getContextPath()%>/databaseServlet?databaseName=<%=request.getAttribute("databaseName")%>&tableName=<%=tableName%>"><%=tableName%></a></li>
              <%
                  }
                }
              %>
                </ul>
              </div>
            </div>

        <%
          Object tableMetaObject = request.getAttribute("tableMetaList");
          if(tableMetaObject != null) {
            List<Map<String, String>> tableMetaList = (List<Map<String, String>>) tableMetaObject;
        %>
          <div class="tableMeta">
            <h4>元数据</h4>
            <div class="content">
              <table>
                <tr>
                  <th>Field</th>
                  <th>Type</th>
                  <th>Key</th>
                </tr>
                <% for(Map<String, String> rowMap : tableMetaList) {%>
                <tr>
                  <td><%=rowMap.get("field")%></td>
                  <td><%=rowMap.get("type")%></td>
                  <td><%=rowMap.get("key")%></td>
                </tr>
                <%}%>
              </table>
            </div>
          </div><!-- tableMeta end -->
        <%}%>
        </div><!-- tableContainer end -->
        <%
          Object tableDataObject = request.getAttribute("tableDataList");
          Integer columnCount = (Integer) request.getAttribute("columnCount");
          if(tableDataObject != null) {
            List<Map<Integer, String>> tableDataList = (List<Map<Integer, String>>) tableDataObject;
        %>
        <div class="tableDataContainer">
          <h4>表数据</h4>
          <div class="content">
            <table>
              <% for(Map<Integer, String> rowDataMap : tableDataList) {%>
                <tr>
                  <%for(int i = 1; i < columnCount; i ++) {%>
                    <td><%=rowDataMap.get(i)%></td>
                  <%}%>
                </tr>
              <%}%>
            </table>
          </div>
        </div><!-- tableDataContainer end -->
        <%}%>
      </div>
  </div>
</body>
</html>
