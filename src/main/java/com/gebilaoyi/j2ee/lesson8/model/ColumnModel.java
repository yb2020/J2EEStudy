package com.gebilaoyi.j2ee.lesson8.model;

public class ColumnModel {
    private String columnName;

    private String property;

    private String length;

    private String isPrimary;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setPrimary(String primary) {
        isPrimary = primary;
    }
}
