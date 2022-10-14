package com.gebilaoyi.j2ee.lesson8.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface DatabaseMapper {
    @Select("show databases")
    List<String> getDatabase() ;

    @Select("use ${databaseName}")
    void useDatabase(String databaseName);
    @Select("show tables;")
    List<String> getTableList();

    @Select("desc ${tableName}")
    List<Map<String, String>> getTableMetaList(String tableName) ;

    @Select("select * from ${tableName} limit 10")
    List<Map<String, Object>> getTableDataList(String tableName);


    @Update("create database ${databaseName}")
    int createDatabase(String databaseName) ;

    @Update("${createTableSQL}")
    int createTable(String createTableSQL);
}
