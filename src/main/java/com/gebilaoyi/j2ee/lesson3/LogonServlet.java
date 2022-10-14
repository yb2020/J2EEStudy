package com.gebilaoyi.j2ee.lesson3;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LogonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username") ;
        String password = req.getParameter("password") ;
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.print("<html>");
        pw.print("<head>");
        pw.print("<title>Logon</title>");
        pw.print("</head>");
        pw.print("<body>");
        if(username != null)
            pw.print("<div>你的usn是：<b style='color: blue'>" + username + "</b></div>");
        if(password != null)
            pw.print("<div>你的密码是：<b style='color: blue'>" + password + "</b></div>");

        pw.print("</body>");
        pw.print("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username") ;
        String password = req.getParameter("password") ;
        if(username.equals("laoyi") && password.equals("111111")) {
//            resp.sendRedirect("Login.jsp?status=1");
            req.setAttribute("status", 1);
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
        }else {
//            resp.sendRedirect("Login.jsp?status=0");
            req.setAttribute("status", 0);
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
        }
    }
}
