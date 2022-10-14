package com.gebilaoyi.j2ee.lesson5;

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
        Conn conn = new Conn();
        try {
            req.setAttribute("databaseList", getDatabaseList(conn));

            String databaseName = req.getParameter("databaseName") ;
            if(databaseName != null && !databaseName.equals("")) {
                req.setAttribute("databaseName", databaseName);
                req.setAttribute("tableList", getTableList(conn, databaseName));
            }

            String tableName = req.getParameter("tableName") ;//传参数
            if(tableName != null && !tableName.equals("")) {
                req.setAttribute("tableMetaList", getTableMetaList(conn, tableName)); //回数据

                Map<String, Object> map = getTableDataList(conn, tableName);
                req.setAttribute("columnCount", map.get("columnCount"));
                req.setAttribute("tableDataList", map.get("tableDataList")); //回表的数据
            }

            req.getRequestDispatcher("databaseEditorLesson5.jsp").forward(req, resp);
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            conn.close();
        }
    }



    private List<String> getDatabaseList(Conn conn) {
        List<String> databaseList = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = conn.preparedStatement("show databases").executeQuery();
            while(rs.next()) {
                String databaseName = rs.getString(1) ;
                databaseList.add(databaseName) ;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return databaseList;
    }

    private List<String> getTableList(Conn conn, String databaseName) {
        List<String> tableList = new ArrayList<>();
        ResultSet rs = null;
        try {
            conn.preparedStatement("use " + databaseName) .executeQuery();
            rs = conn.preparedStatement("show tables") .executeQuery();
            while(rs.next()) {
                tableList.add(rs.getString(1) ) ;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return tableList;
    }

    private Map<String, Object> getTableDataList(Conn conn, String tableName) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<Integer, String>> tableDataList = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = conn.preparedStatement("select * from " + tableName + " limit 10") .executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData() ;
            Integer columnCount = rsmd.getColumnCount();
            resultMap.put("columnCount", columnCount) ;

            Map<Integer, String> rowMetaMap = new HashMap<>();
            for(int i = 1 ; i <= columnCount; i ++) {
                rowMetaMap.put(i, rsmd.getColumnName(i)) ;
            }
            tableDataList.add(rowMetaMap) ;

            while (rs.next()) {
                Map<Integer, String> rowDataMap = new HashMap<>();
                for(int i = 1; i <= columnCount; i ++) {
                    rowDataMap.put(i, rs.getString(i)) ;
                }
                tableDataList.add(rowDataMap) ;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        resultMap.put("tableDataList", tableDataList);
        return resultMap;
    }

    private List<Map<String, String>> getTableMetaList(Conn conn, String tableName) {
        List<Map<String, String>> tableMetaList = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = conn.preparedStatement("desc " + tableName).executeQuery();

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
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return tableMetaList;
    }
}
