package com.jcommerce.migration.sql;

import org.hibernate.cfg.Configuration;

public class SchemaExport {

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		System.out.println("\n\tStart\n");
		
		new Configuration().configure().buildSessionFactory();
		
		System.out.println("\n\tDONE");
		System.out.println("\n\tSpend " + (System.currentTimeMillis() - start) / 1000 + " seconds");
	}

}
