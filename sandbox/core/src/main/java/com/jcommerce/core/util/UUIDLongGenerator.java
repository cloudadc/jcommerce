package com.jcommerce.core.util;

public class UUIDLongGenerator extends UUIDGenerator {
	
	public static Long newUUID() {
		
		double d = Math.random();
		long ld = Math.round(d*1000000000l);
		
		return ld;
		
	}
}
