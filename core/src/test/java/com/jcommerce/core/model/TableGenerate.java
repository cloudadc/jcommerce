package com.jcommerce.core.model;

import javax.persistence.Persistence;

public class TableGenerate {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		
		System.out.println("\n\tStart\n");
		
		Persistence.createEntityManagerFactory( "com.jcommerce.core.model" );
		
		System.out.println("\n\tDONE");
		System.out.println("\n\tSpend " + (System.currentTimeMillis() - start) / 1000 + " seconds");
	}

}
