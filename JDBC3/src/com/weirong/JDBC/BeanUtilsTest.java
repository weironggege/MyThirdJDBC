package com.weirong.JDBC;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

public class BeanUtilsTest {

	@Test
	public void testSetProperty() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object object = new Customer();
		System.out.println(object);
		
		BeanUtils.setProperty(object, "name", "weirong");
		System.out.println(object);
		
		Object val = BeanUtils.getProperty(object, "name");
		System.out.println(val);
	}

}
