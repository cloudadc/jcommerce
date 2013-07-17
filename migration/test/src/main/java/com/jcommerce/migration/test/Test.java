package com.jcommerce.migration.test;

import com.jcommerce.core.service.KylinSoongManager;
import com.jcommerce.core.service.impl.KylinSoongManagerImpl;

public class Test {
	
	private KylinSoongManagerImpl manager = new KylinSoongManagerImpl();

	public static void main(String[] args) {
		
		new Test().test();
	}

	private void test() {
		System.out.println(manager.getDAO());
	}

}
