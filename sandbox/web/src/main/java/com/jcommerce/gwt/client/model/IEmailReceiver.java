package com.jcommerce.gwt.client.model;
/**
 * @author monkey
 */
public class IEmailReceiver {

	public static final int EMAIL_PRI_HIGH = 1;   //紧急
	public static final int EMAIL_PRI_COMMON = 2; //一般
	public static final int EMAIL_PRI_LOW = 3;    //缓慢
	
	
	public static final String ID = "id";
	public static final String SENDERNAME = "senderName";
	public static final String MAILSUBJECT = "mailSubject";
	public static final String RECEIVERNAME = "receiverName";
	public static final String RECEIVETIME = "receiveTime";
	public static final String ISATTACH = "isAttach";
	public static final String ATTACHFILENAME = "attachFilename";
	public static final String EMAILCONTENT = "emailContent";
	public static final String ISJUNK = "isJunk";
	public static final String EMAILPRI = "emailPri";
	public static final String ISREAD = "isRead";
	
}
