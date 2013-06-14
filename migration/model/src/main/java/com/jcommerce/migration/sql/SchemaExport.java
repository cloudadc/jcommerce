package com.jcommerce.migration.sql;

import org.hibernate.cfg.Configuration;

public class SchemaExport {

	public static void main(String[] args) {
		
		new Configuration().configure().buildSessionFactory();
		
		System.out.println("\n\tDONE");
	}

}
