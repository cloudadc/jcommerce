package com.jcommerce.entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jcommerce.core.model.AccountLog;
import com.jcommerce.core.service.AccountLogManager;


public class Test {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		AccountLogManager accountLogManager =  (AccountLogManager) ctx.getBean("AccountLogManager");
		
		AccountLog log = new AccountLog();
		
		accountLogManager.saveAccountLog(log);
	}

}
