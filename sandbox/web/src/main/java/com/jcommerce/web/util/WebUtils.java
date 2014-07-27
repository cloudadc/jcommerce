package com.jcommerce.web.util;

import javax.servlet.http.HttpServletRequest;

import org.datanucleus.util.StringUtils;

import com.tecnick.htmlutils.htmlentities.HTMLEntities;

public class WebUtils {
	public static String encodeHtml(String in) {
		if(StringUtils.isEmpty(in)) {
			return in;
		}
		else {
			return HTMLEntities.htmlentities(in);
		}
	}
	public static String getActionName(HttpServletRequest request) {
		
		String requestURL = request.getRequestURI();
		int i1 = requestURL.lastIndexOf('/');
		int i2 = requestURL.indexOf(".action");
		
		String fileName = "home";
		if(i1>=0 && i2>=0 && i2>=i1+1) {
			fileName = requestURL.substring(i1+1, i2);
		}		
		return fileName;
	}
}
