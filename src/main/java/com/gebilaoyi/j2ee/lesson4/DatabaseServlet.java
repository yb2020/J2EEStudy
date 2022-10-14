package com.gebilaoyi.j2ee.lesson4;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> databaseList = new ArrayList<>();
        List<String> tableList = new ArrayList<>();

        Connection connection = null ;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            connection = DriverManager.getConnection("jdbc:mysql://192.168.218.50:3306", "root", "root") ;
            statement = connection.prepareStatement("show databases") ;
            rs = statement.executeQuery();
            while(rs.next()) {
                String databaseName = rs.getString(1) ;
                databaseList.add(databaseName) ;
            }
            req.setAttribute("databaseList", databaseList);

            String databaseName = req.getParameter("databaseName") ;

            if(databaseName != null && !databaseName.equals("")) {
                statement = connection.prepareStatement("use " + databaseName) ;
                statement.executeQuery();

                statement = connection.prepareStatement("show tables") ;
                rs = statement.executeQuery();

                while(rs.next()) {
                    String tableName = rs.getString(1) ;
                    tableList.add(tableName) ;
                }
                req.setAttribute("databaseName", databaseName);
                req.setAttribute("tableList", tableList);
            }

            String tableName = req.getParameter("tableName") ;//传参数
            if(tableName != null && !tableName.equals("")) {
                List<Map<String, String>> tableMetaList = new ArrayList<>();
                statement = connection.prepareStatement("desc " + tableName) ;
                rs = statement.executeQuery();

                while(rs.next()) {
                    Map<String, String> map = new HashMap<>() ;
                    String field = rs.getString("Field") ;
                    String type = rs.getString("Type") ;
                    String key = rs.getString("Key");
                    map.put("field", field);
                    map.put("type", type) ;
                    map.put("key", key) ;
                    tableMetaList.add(map) ;
                }
                req.setAttribute("tableMetaList", tableMetaList); //回数据

                List<Map<Integer, String>> tableDataList = new ArrayList<>();
                statement = connection.prepareStatement("select * from " + tableName + " limit 10") ;
                rs = statement.executeQuery();

                ResultSetMetaData rsmd = rs.getMetaData() ;
                Integer columnCount = rsmd.getColumnCount();
                Map<Integer, String> rowMetaMap = new HashMap<>();
                for(int i = 1 ; i <= columnCount; i ++) {
                    rowMetaMap.put(i, rsmd.getColumnName(i)) ;
                }
                tableDataList.add(rowMetaMap) ;

                while (rs.next()) {
                    Map<Integer, String> rowDataMap = new HashMap<>();
                    for(int i = 1; i < columnCount; i ++) {
                        rowDataMap.put(i, rs.getString(i)) ;
                    }
                    tableDataList.add(rowDataMap) ;
                }

                req.setAttribute("columnCount", columnCount);
                req.setAttribute("tableDataList", tableDataList); //回表的数据
            }

            req.getRequestDispatcher("databaseEditor.jsp").forward(req, resp);
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                if(rs != null)  rs.close();
                if(statement != null) statement.close();
                if(connection != null) connection.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
