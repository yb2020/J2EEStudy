package com.gebilaoyi.j2ee.lesson8;

import com.gebilaoyi.j2ee.lesson7.getTableListQuery;
import com.gebilaoyi.j2ee.lesson8.mapper.DatabaseMapper;
import com.gebilaoyi.j2ee.lesson8.model.ColumnModel;
import com.gebilaoyi.j2ee.lesson8.model.DatabaseModel;
import com.gebilaoyi.j2ee.lesson8.model.TableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/lesson8")
@RestController
public class Lesson8DatabaseEditorApi {

    @Autowired
    private DatabaseMapper databaseMapper;

    @GetMapping("/getDatabase")
    public List<String> getDatabase() {
        return databaseMapper.getDatabase();
    }

    @PostMapping("/getTableList")
    public List<String> getTableList(@RequestBody getTableListQuery query) {
        databaseMapper.useDatabase(query.getDatabaseName());
        return databaseMapper.getTableList();
    }

    @PostMapping("/getTableMeta")
    public List<Map<String, String>> getTableMeta(@RequestBody getTableListQuery query) {
        databaseMapper.useDatabase(query.getDatabaseName());
        return databaseMapper.getTableMetaList(query.getTableName());
    }

    @PostMapping("/getTableData")
    public List<Map<String, Object>> getTableData(@RequestBody getTableListQuery query) {
        databaseMapper.useDatabase(query.getDatabaseName());
        List<Map<String, Object>> tableDataList = databaseMapper.getTableDataList(query.getTableName());

        if (tableDataList.size() > 0) {
            Map<String, Object> columnMap = new LinkedHashMap<>();
            Map<String, Object> map = tableDataList.get(0);
            for (String columnName : map.keySet()) {
                columnMap.put(columnName, columnName);
            }
            tableDataList.add(0, columnMap);
        }
        return tableDataList;
    }

    @PostMapping("/createDatabase")
    public int createDatabase(@RequestBody DatabaseModel databaseModel) {
        return databaseMapper.createDatabase(databaseModel.getDatabaseName()) ;
    }

    @PostMapping("createTable")
    public int createTable(@RequestBody TableModel tableModel) {
        databaseMapper.useDatabase(tableModel.getDatabaseName());
        StringBuffer createTableSQLBuffer = new StringBuffer("create table ") ;
        createTableSQLBuffer.append(tableModel.getTableName()).append("(") ;

        String primaryString = null ;
        for(ColumnModel model : tableModel.getTableColumns()) {
            createTableSQLBuffer.append(model.getColumnName()).append(" ")
                    .append(model.getProperty()).append("(").append(model.getLength()).append("),");
            if(model.getIsPrimary().equals("1")) {
                primaryString = String.format("PRIMARY KEY (`%s`)", model.getColumnName()) ;
            }
        }
        if(primaryString != null) {
            createTableSQLBuffer.append(primaryString) ;
        }
        createTableSQLBuffer.append(")");
        try {
            databaseMapper.createTable(createTableSQLBuffer.toString()) ;
            return 1;
        }catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}