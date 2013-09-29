package com.jcommerce.migration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTest {

	public static void main(String[] args) {

		String[] paths = {"/META-INF/applicationContext.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
	}

}
