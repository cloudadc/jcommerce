package com.jcommerce.core.model.test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.junit.Ignore;

@Ignore
public class GenerateTables {

	public static void main(String[] args) {
		
//		Session session = new AnnotationConfiguration()
//		   
//	   
//	    .setProperty(Environment.HBM2DDL_AUTO, "create")
//	    .setProperty(Environment.POOL_SIZE, "1")
//	    .setProperty(Environment.SHOW_SQL, "true")
//	    .setProperty(Environment.FORMAT_SQL, "true")
//	    .setProperty(Environment.AUTOCOMMIT, "false")
//	   
//	    .setProperty(Environment.URL, "jdbc:mysql://localhost:3306/test")
//	    .setProperty(Environment.USER, "root")
//	    .setProperty(Environment.PASS, "secret")
//	    .setProperty(Environment.DRIVER, com.mysql.jdbc.Driver.class.getName())
//	    .setProperty(Environment.DIALECT, org.hibernate.dialect.MySQL5InnoDBDialect
//	        .class.getName())
//	       
//	    .buildSessionFactory().openSession();

		Persistence.createEntityManagerFactory("com.jcommerce.core.model");

	}

}
