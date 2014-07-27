package com.jcommerce.core.module.shipping.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

	private Properties ps = new Properties();

	public PropertyReader(String propertyName, Class classType) {
		if (propertyName != null) {
			try {
				InputStream in = classType.getResourceAsStream(propertyName);
				ps.load(in);
				in.close();
			} catch (IOException e) {
				System.out
						.println("Property file load error!" + e.getMessage());
			}
		} else {
			throw new RuntimeException("property name is not exist. classType="
					+ classType);
		}
	}

	public String getValue(String key) {
		return ps.getProperty(key);
	}
	
	public void setValue(String key, String value) {
		ps.setProperty(key, value);
	}
}
