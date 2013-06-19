package com.jcommerce.migration.test;

import java.util.Date;
import java.util.TimeZone;

public class Test {

	public static void main(String[] args) {
		
		System.out.println(new Date());
		TimeZone zone = TimeZone.getDefault();
	    System.out.println(zone.getDisplayName());
	    System.out.println(zone.getID());
	}

}
