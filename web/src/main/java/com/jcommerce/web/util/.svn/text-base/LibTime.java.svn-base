package com.jcommerce.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LibTime {
	
	public static String localDate(String format, Long time) {
		String res = "";
		try {
			Date date = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			res = sdf.format(date);
		} catch (Exception ex) {
			System.out.println("in [LibTime.localDate]: failed. format="+format+", time="+time);
		}
		return res;
		
	}
}
