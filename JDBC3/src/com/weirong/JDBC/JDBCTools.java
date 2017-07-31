package com.weirong.JDBC;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class JDBCTools {

	public void update(String sql) {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			conn = JDBCTools.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(statement, conn, rs);
		}
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3306/mysql";
		String user = "root";
		String password = "bottle19920314";
		String driverClass = "com.mysql.jdbc.Driver";
		
		Class.forName(driverClass);
		
		return  (Connection) DriverManager.getConnection(url, user, password);
	}
	
	public static void release(Statement statement, Connection connection, ResultSet resultSet) {
		if(resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
