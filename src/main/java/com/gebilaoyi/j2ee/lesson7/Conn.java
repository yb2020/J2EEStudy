package com.gebilaoyi.j2ee.lesson7;

import java.sql.*;

/**
 * 连数据库驱动
 */
public class Conn {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;

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

    public Statement createStatement(String sql) {
        if(connection == null) {
            connection = connection();
        }

        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
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
            if(statement != null) statement.close();
            if(preparedStatement != null) preparedStatement.close();
            if(connection != null) connection.close();
            preparedStatement = null;
            connection = null ;
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
