package com.gebilaoyi.j2ee.lesson8.model;

import java.util.ArrayList;
import java.util.List;

public class TableModel {
    private String databaseName;

    private String tableName;

    private ArrayList<ColumnModel> tableColumns;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnModel> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(ArrayList<ColumnModel> tableColumns) {
        this.tableColumns = tableColumns;
    }
}
