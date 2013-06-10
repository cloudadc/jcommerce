package com.jcommerce.web.util;

import com.jcommerce.gwt.client.model.IOrderInfo;

public class ConstantsMappingUtils {
	
	public static String getOrderStatus(long os) {
		
		int s = (int)os;
		String res = "";
		switch(s) { 
			case IOrderInfo.OS_CANCELED: 
				res = "OS_CANCELED";
				break;
			case IOrderInfo.OS_CONFIRMED: 
				res = "OS_CONFIRMED";
				break;
			case IOrderInfo.OS_INVALID: 
				res = "OS_INVALID";
				break;
			case IOrderInfo.OS_RETURNED: 
				res = "OS_RETURNED";
				break;
			case IOrderInfo.OS_UNCONFIRMED: 
				res = "OS_UNCONFIRMED";
				break;
		}
		return res;
	}
	
	public static String getPayStatus (long ps) {
		int s = (int)ps;
		String res ="";
		switch(s) {
			case IOrderInfo.PS_PAYED: 
				res = "PS_PAYED"; 
				break;
			case IOrderInfo.PS_PAYING: 
				res = "PS_PAYING";
				break;
			case IOrderInfo.PS_UNPAYED:
				res = "PS_UNPAYED";
				break;
		}
		return res;
	}
	
	public static String getShippingStatus (long ss) {
		int s = (int)ss;
		String res = "";
		switch(s) {
			case IOrderInfo.SS_PREPARING: 
				res = "SS_PREPARING"; 
				break;
			case IOrderInfo.SS_RECEIVED: 
				res = "SS_RECEIVED";
				break;
			case IOrderInfo.SS_SHIPPED: 
				res = "SS_SHIPPED";
				break;
			case IOrderInfo.SS_UNSHIPPED: 
				res = "SS_UNSHIPPED";
				break;
		}
		return res;
	}
	
	
}
