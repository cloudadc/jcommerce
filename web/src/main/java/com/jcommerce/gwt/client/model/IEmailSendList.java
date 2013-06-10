package com.jcommerce.gwt.client.model;

public interface IEmailSendList {

	public static final int TEMPLATEID_SENDPASSWORD  = 1;
	public static final int TEMPLATEID_ORDERCONFIRM  = 2;
	public static final int TEMPLATEID_DELIVERNOTICE = 3;
	public static final int TEMPLATEID_ORDERCANCEL   = 4;
	public static final int TEMPLATEID_ORDERINVALID  = 5;
	public static final int TEMPLATEID_SENDBONUS     = 6;
	public static final int TEMPLATEID_GROUPBUY      = 7;
	public static final int TEMPLATEID_REGISTERVALIDATE  = 8;
	public static final int TEMPLATEID_VIRTUALCARD   = 9;
	public static final int TEMPLATEID_ATTENTIONLIST = 10;
	public static final int TEMPLATEID_REMINDOFNEWORDER  = 11;
	
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String TEMPLATEID = "templateId";
	public static final String EMAILCONTENT = "emailContent";
	public static final String ERROR = "error";
	public static final String PRIORITY = "priority";
	public static final String LASTSEND = "lastSend";
	
}
