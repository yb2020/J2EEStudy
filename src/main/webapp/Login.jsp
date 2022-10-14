<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Login JSP</title>
      <style>
          form {
              padding: 20px;
          }
          div {
              line-height: 30px ;
          }
      </style>
  </head>
  <body>
  <div style="color: #f00">
  <%
      if(request.getParameter("status") != null) {
          if(request.getParameter("status").equals("1")) {
              out.println("登录成功");
          }else {
              out.println("登录失败");
          }
      }

      if(request.getAttribute("status") != null) {
          if(request.getAttribute("status").equals(1)) {
              out.println("登录成功");
          }else {
              out.println("登录失败");
          }
      }
  %></div>
    <form name="loginForm" action="logon" method="post">
        <div>
            用户名：<input type="text" name="username" />
        </div>
        <div>
            密码：<input type="password" name="password" />
        </div>
        <div>
            <button type="submit">登录</button>      <button type="reset">重置</button>
        </div>
    </form>
  </body>
</html>
