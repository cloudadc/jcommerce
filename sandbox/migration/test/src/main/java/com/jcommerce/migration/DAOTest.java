package com.jcommerce.migration;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jcommerce.core.dao.KylinSoongDAO;
import com.jcommerce.core.model.KylinSoong;

public class DAOTest {

	public static void main(String[] args) {
		
		String[] paths = {"/META-INF/applicationContext.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
		
		KylinSoongDAO dao = (KylinSoongDAO) ctx.getBean("KylinSoongDAOImpl");
		
//		dao.saveKylinSoong(new KylinSoong());
//		
//		dao.removeKylinSoong("2");
		
		System.out.println(dao);
	}

}
