package com.jcommerce.migration;

import javax.persistence.Persistence;

public class TableGenerateTest {

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		System.out.println("\n\tStart\n");
		
		Persistence.createEntityManagerFactory("jcommerce.migration");

		System.out.println("\n\tDONE");
		System.out.println("\n\tSpend " + (System.currentTimeMillis() - start) / 1000 + " seconds");
	}
}
