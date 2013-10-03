package com.jcommerce.core.model.test;

import javax.persistence.Persistence;

import org.junit.Ignore;

@Ignore
public class GenerateTables {

	public static void main(String[] args) {
		

		Persistence.createEntityManagerFactory("com.jcommerce.core.model");

	}

}
