package com.gebilaoyi.j2ee.lesson5;

import java.sql.*;

/**
 * 连数据库驱动
 */
public class Conn {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    public Connection connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            connection = DriverManager.getConnection("jdbc:mysql://192.168.218.50:3306", "root", "root") ;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public PreparedStatement preparedStatement(String sql) {
        if(connection == null) {
            connection = connection();
        }

        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preparedStatement;
    }

    public void close() {
        try {
            if(preparedStatement != null) preparedStatement.close();
            if(connection != null) connection.close();
            preparedStatement = null;
            connection = null ;
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
