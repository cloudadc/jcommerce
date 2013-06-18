package com.jcommerce.migration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jcommerce.core.dao.KylinSoongDAO;
import com.jcommerce.core.model.KylinSoong;

public class DAOTest {

	public static void main(String[] args) {
		
		String[] paths = {"/META-INF/applicationContext.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
		
		KylinSoongDAO dao = (KylinSoongDAO) ctx.getBean("KylinSoongDAO");
		
		dao.saveKylinSoong(new KylinSoong());
		
//		dao.getList("select k from KylinSoong as k");
	
		System.out.println("DONE");
	}

}
