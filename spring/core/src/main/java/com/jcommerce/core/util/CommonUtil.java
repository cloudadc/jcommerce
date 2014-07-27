package com.jcommerce.core.util;

import java.util.Date;
import java.text.SimpleDateFormat;

public class CommonUtil {


	public static String getOrderSN(Date createDate){
		//TODO this is temp solution, SN should increase sequential in one day
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(createDate);
	}
	
}
