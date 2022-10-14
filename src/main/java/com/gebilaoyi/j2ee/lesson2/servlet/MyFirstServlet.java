package com.gebilaoyi.j2ee.lesson2.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFirstServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usn = req.getParameter("usn") ;
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.print("<html>");
        pw.print("<head>");
        pw.print("<title>Hello world!</title>");
        pw.print("</head>");
        pw.print("<body>");
        if(usn != null)
            pw.print("<div>你的usn是：<b style='color: blue'>" + usn + "</b></div>");

        for(int i = 0 ; i < 10; i ++) {
            if(i % 2 == 0) {
                pw.print("<a href='helloWorld?usn=laoyi'><h1 style=\"color: #f00\">我是大标题</h1></a>");
            }else {
                pw.print("<h1 style=\"color: green\">我是大标题</h1>");
            }
        }
        pw.print("</body>");
        pw.print("</html>");
    }
}
