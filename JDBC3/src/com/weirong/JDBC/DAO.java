package com.weirong.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;

public class DAO {

	//INSERT, UPDATE, DELETE操作都可以包含在其中
	public void update(String sql, Object ... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			for(int i=0; i<args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(preparedStatement, connection, null);
		}
	}

	//查询一条记录，返回对应的对象
	public <T> T get(Class<T> clazz, String sql, Object ... args) {
		T entity = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCTools.getConnection();
			
			preparedStatement = connection.prepareStatement(sql);
			
			for(int i=0; i<args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				Map<String, Object> values = new HashMap<String, Object>();
				
				ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
				
				int columnCount = rsmd.getColumnCount();
				
				for(int i=0; i<columnCount; i++) {
					String columnLable = rsmd.getColumnLabel(i + 1);
					Object columnValue = resultSet.getObject(i + 1);
					
					values.put(columnLable, columnValue);
				}
				
				entity = clazz.newInstance();
				
				for(Map.Entry<String, Object> entry: values.entrySet()) {
					String propertyName = entry.getKey();
					Object value = entry.getValue();
					
					BeanUtils.setProperty(entity, propertyName, value);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(preparedStatement, connection, resultSet);
		}
		
		return entity;
		
	}

	//查询多条记录， 返回所对应的对象的集合
	public <T> List<T> getForList(Class<T> clazz, String sql, Object ... args) {
		List<T> list = new ArrayList();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			for(int i=0; i<args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			
			resultSet = preparedStatement.executeQuery();
			
			List<Map<String, Object>> values = new ArrayList<>();
			
			ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
			
			Map<String, Object> map = null;
			
			while(resultSet.next()) {
				map = new HashMap<>();
				
				for(int i=0; i<rsmd.getColumnCount(); i++) {
					String columnLable = rsmd.getColumnLabel(i + 1);
					Object value = resultSet.getObject(i + 1);
					
					map.put(columnLable, value);
				}
				values.add(map);
			}
			
			T bean = null;
			
			if(values.size() > 0) {
				for(Map<String, Object> m: values) {
					bean = clazz.newInstance();
					for(Map.Entry<String, Object> entry: m.entrySet()) {
						String propertyName = entry.getKey();
						Object value = entry.getValue();
						
						BeanUtils.setProperty(bean, propertyName, value);
					}
					list.add(bean);
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(preparedStatement, connection, resultSet);
		}
		return list;
	}

	//返回某条记录的某一个字段的值
	public <E> E getForValue(String sql, Object ... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			for(int i=0; i<args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				return (E) resultSet.getObject(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(preparedStatement, connection, resultSet);
		}
		
		return null;
	}
}
