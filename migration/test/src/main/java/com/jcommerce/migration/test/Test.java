package com.jcommerce.migration.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jcommerce.core.service.impl.KylinSoongManagerImpl;


@Component
public class Test {
	
	@Autowired
	private KylinSoongManagerImpl manager;

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		Test test = context.getBean(Test.class);
		test.test();
	}

	private void test() {
		System.out.println(manager);
	}

}
