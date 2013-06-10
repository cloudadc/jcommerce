package com.jcommerce.gwt.client.model;

public interface RegularExConstants 
{
	public static final String CONSIGNEE = "([\\u4e00-\\u9fa5]+)|(\\b[A-Z][a-z]*\\b\\s*)+";
	public static final String CONSIGNEEREGMSG = "Must be a correct Name format like 张三 or Jim William";
	
	public static final String EMAIL = "\\b([A-z]*\\d*)*@.+.([A-z]*\\d*)*com\\b";
	public static final String EMAILREGMSG = "Must be a correct email format like qq@ecit.com";
	
	public static final String ZIP = "(\\d{1,5}-){0,1}\\d{4,9}";
	public static final String ZIPREGMSG = "Must be a correct zip format like 2210-11111";
	
	public static final String TEL = "\\(0\\d{2,4}\\)[- ]?\\d{7,8}|0\\d{2,4}[- ]?\\d{7,8}";
	public static final String TELREGMSG = "Must be a correct telnumber format like 010-65325212 " +
										"or (010)65325212";
	public static final String MOBILE = "1\\d{10,30}";
	public static final String MOBILEREGMSG = "Must be a correct zip format like 13535222156";

	public static final String NATURALNO= "[^0]\\d{0,1}|0";
	public static final String NATURALNOREGMSG = "Must be a natural number like 10";
	
	public static final String POSITIVEINT= "[^0]\\d*|0";
	public static final String POSITIVEINTREGMSG = "Must be a positive number like 1000";
}
