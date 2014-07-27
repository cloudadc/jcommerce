package com.jcommerce.core.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

public class ResourceUtil {
	
	public static final String BUNDLE_SHOPCONFIG = "com.jcommerce.core.resource.shopConfig";
	
	public static ResourceBundle getShopConfigResource(Locale locale) {
		return ResourceBundle.getBundle(BUNDLE_SHOPCONFIG, locale);
	}
	
	public static Locale parseLocale (String str) {
		Locale res = null;
		str = str.trim();
		String[] tokens = StringUtils.split(str, '_');
		String lang=null, country=null;
		if(tokens.length==1) {
			lang = tokens[0];
			res = new Locale(lang);
		}
		else if(tokens.length==2) {
			lang = tokens[0];
			country = tokens[1];
			res = new Locale(lang, country);
		}
		
		return res;
	}
}
