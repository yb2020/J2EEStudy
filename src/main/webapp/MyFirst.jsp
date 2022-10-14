<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>MyFirst JSP</title>
  </head>
  <body>
    <%
        String usn = request.getParameter("usn");
        String pwd = request.getParameter("pwd");
        if(usn != null) {
    %>
        <div>你的usn是：<b style='color: blue'><%= usn%></b></div>
    <%
        }
        if(pwd != null) {
    %>
    <div>你的pwd是：<b style='color: blue'><%= pwd%></b></div>
    <%
        }
        for (int i = 0 ; i < 10; i ++) {
            if(i%2==0) {
    %>
                <a href="?usn=laoyi"><h1 style="color: red"><%=i + "我是大标题"%></h1></a>
    <%
            }else {%>
                <h1 style="color: green"><%="我是大标题"%></h1>
            <%}
        }%>
  </body>
</html>
