package com.jcommerce.core.model.util;

import java.io.File;

public class MappingClassGenerate {
	
	static final String MODEL_PATH = new File("src").getAbsolutePath() + File.separator + "main/java/com/jcommerce/core/model";

	public static void main(String[] args) {

		File file = new File(MODEL_PATH);
		
		int count = 0 ;
		for(File f : file.listFiles()) {
			
			String name = f.getAbsolutePath();
			name = name.replace('/', '.');
			name = name.substring(name.indexOf("com.jcommerce.core"));
			name = name.substring(0, name.length() - 5);
			
			if(!name.endsWith("Constants") && !name.endsWith("LinkGood") && ! (name.length() <= "com.jcommerce.core.model".length())){
				count ++ ;
				name = "<class>" + name + "</class> ";
				System.out.println(name);
			}
		}
		
		System.out.println("Total class number: " + count);
	}

}
