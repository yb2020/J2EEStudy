package com.gebilaoyi.j2ee.lesson7;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseService {
    public List<String> getDatabaseList(Conn conn) {
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

    public List<String> getTableList(Conn conn, String databaseName) {
        List<String> tableList = new ArrayList<>();
        ResultSet rs = null;
        try {
            conn.createStatement("use " + databaseName);
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


    public List<Map<String, String>> getTableMetaList(Conn conn, String databaseName, String tableName) {
        List<Map<String, String>> tableMetaList = new ArrayList<>();
        ResultSet rs = null;
        try {
            conn.createStatement("use " + databaseName);
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


    public List<Map<String, Object>> getTableDataList(Conn conn, String databaseName, String tableName) {
        List<Map<String, Object>> tableDataList = new ArrayList<>();
        ResultSet rs = null;
        try {
            conn.createStatement("use " + databaseName);
            rs = conn.preparedStatement("select * from " + tableName + " limit 10") .executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData() ;
            Integer columnCount = rsmd.getColumnCount();

            Map<String, Object> rowMetaMap = new HashMap<>();
            for(int i = 1 ; i <= columnCount; i ++) {
                rowMetaMap.put(i + "", rsmd.getColumnName(i)) ;
            }
            tableDataList.add(rowMetaMap) ;

            while (rs.next()) {
                Map<String, Object> rowDataMap = new HashMap<>();
                for(int i = 1; i <= columnCount; i ++) {
                    rowDataMap.put((String)rowMetaMap.get("" + i), rs.getString(i)) ;
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
        return tableDataList;
    }
}
