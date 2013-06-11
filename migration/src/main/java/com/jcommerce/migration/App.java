package com.jcommerce.migration;

import javax.persistence.Persistence;

public class App {

	public static void main(String[] args) {
		
		Persistence.createEntityManagerFactory("jcommerce.migration");

		System.out.println("DONE");
	}
}
