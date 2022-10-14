package com.gebilaoyi.j2ee.lesson7;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DatabaseEditorApi {

    @PostMapping("/getDatabase")
    public List<String> getDatabase() {
        Conn conn = new Conn();
        DatabaseService databaseService = new DatabaseService();
        return databaseService.getDatabaseList(conn);
    }

    @PostMapping("/getTableList")
    public List<String> getTableList(@RequestBody getTableListQuery query) {
        Conn conn = new Conn();
        DatabaseService databaseService = new DatabaseService();
        return databaseService.getTableList(conn, query.getDatabaseName());
    }

    @PostMapping("/getTableMeta")
    public List<Map<String, String>> getTableMeta(@RequestBody getTableListQuery query) {
        Conn conn = new Conn();
        DatabaseService databaseService = new DatabaseService();
        return databaseService.getTableMetaList(conn, query.getDatabaseName(), query.getTableName());
    }

    @PostMapping("/getTableData")
    public List<Map<String, Object>> getTableData(@RequestBody getTableListQuery query) {
        Conn conn = new Conn();
        DatabaseService databaseService = new DatabaseService();
        return databaseService.getTableDataList(conn, query.getDatabaseName(), query.getTableName());
    }
}
