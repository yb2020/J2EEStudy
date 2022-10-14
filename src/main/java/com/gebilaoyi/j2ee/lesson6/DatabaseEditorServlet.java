package com.gebilaoyi.j2ee.lesson6;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseEditorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String databaseName = req.getParameter("databaseName") ;
        Conn conn = new Conn();
        req.setAttribute("databaseList", getDatabaseList(conn));

        String action = req.getParameter("action") ;
        if(action == null) {
            req.getRequestDispatcher("databaseEditorLesson6.jsp").forward(req, resp);
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        switch (action) {
            case "tableNameList":
                out.println(getTableByDatabase(conn, databaseName));
                break;
            case "tableMeta":
                String tableName = req.getParameter("tableName");
                out.println(getTableMetaByTableName(conn, databaseName, tableName));
                break;
            case "tableData":
                tableName = req.getParameter("tableName");
                out.println(getTableDataByTableName(conn, databaseName, tableName));
                break;
        }
        out.close();
        conn.close();

    }

    private String getTableByDatabase(Conn conn, String databaseName) {
        List<String> tableList = getTableList(conn, databaseName);
        StringBuffer sb = new StringBuffer("[") ;
        for(String tableName : tableList) {
            sb.append("{");
            sb.append("'tableName':'").append(tableName).append("'");
            sb.append("},");
        }
        return sb.substring(0, sb.length() - 1) + "]" ;
    }

    private String getTableMetaByTableName(Conn conn, String databaseName, String tableName) {
        List<Map<String, String>> tableMetaList = getTableMetaList(conn, databaseName, tableName);
        StringBuffer sb = new StringBuffer("[") ;
        for(Map<String, String> rowMap : tableMetaList) {
            sb.append("{");
            String columnString = "" ;
            for(Map.Entry<String, String> entry : rowMap.entrySet()) {
                columnString += "\""+entry.getKey()+"\":\"" + entry.getValue() + "\",";
            }
            sb.append(columnString.substring(0, columnString.length() - 1)) ;
            sb.append("},");
        }
        return sb.substring(0, sb.length() - 1) + "]" ;
    }

    private String getTableDataByTableName(Conn conn, String databaseName, String tableName) {
        Map<String, Object> map = getTableDataList(conn, databaseName, tableName);
        int columnCount = (Integer) map.get("columnCount") ;
        List<Map<Integer, String>> tableDataList = (List<Map<Integer, String>>) map.get("tableDataList");
        StringBuffer sb = new StringBuffer("{") ;
        sb.append("\"columnCount\":").append(columnCount).append(",").append("\"tableDataList\":[");
        for(Map<Integer, String> rowMap : tableDataList) {
            sb.append("{");
            String columnString = "" ;
            for(Map.Entry<Integer, String> entry : rowMap.entrySet()) {
                columnString += "\""+entry.getKey()+"\":\"" + entry.getValue() + "\",";
            }
            sb.append(columnString.substring(0, columnString.length() - 1)) ;
            sb.append("},");
        }
        return sb.substring(0, sb.length() - 1) + "]}" ;
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

    private Map<String, Object> getTableDataList(Conn conn, String databaseName, String tableName) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<Integer, String>> tableDataList = new ArrayList<>();
        ResultSet rs = null;
        try {
            conn.preparedStatement("use " + databaseName) .executeQuery();
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

    private List<Map<String, String>> getTableMetaList(Conn conn, String databaseName, String tableName) {
        List<Map<String, String>> tableMetaList = new ArrayList<>();
        ResultSet rs = null;
        try {
            conn.preparedStatement("use " + databaseName) .executeQuery();
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
