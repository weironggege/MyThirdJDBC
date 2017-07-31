package com.weirong.JDBC;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestDAO {

	DAO dao = new DAO();
	
//	@Test
//	public void testUpdate() {
//		String sql = "insert into customer (ID, name, email) values (?, ?, ?)";
//		dao.update(sql, 5, "xiaorong", "xiaoyu@qq.com");
//	}

	@Test
	public void testGet() {
		String sql = "select ID id, name, email from customer where ID=?";
		Customer customer = dao.get(Customer.class, sql, 2);
		
		System.out.println(customer);
	}
	
	@Test
	public void testGetForList() {
		String sql = "select ID, name, email from customer";
		List<Customer> customer = dao.getForList(Customer.class, sql); 
		System.out.println(customer);
	}
	
	@Test
	public void testGetForValue() {
		String sql = "select email from customer where ID=?";
		
		String email = dao.getForValue(sql, 2);
		System.out.println(email);
	}
}
