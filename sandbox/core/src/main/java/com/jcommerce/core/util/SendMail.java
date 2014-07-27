package com.jcommerce.core.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import freemarker.template.*;

public class SendMail {
	
	private Address addressTo[] = new Address[20];
	private String userName;

	public SendMail(Address addressTo[],String userName) {
		this.addressTo= addressTo;
		this.userName = userName;
	}
	public boolean sendChangePWMail(String pwd){

		   Properties props = new Properties();
		   props.put("mail.smtp.host", "smtp.vanceinfo.com");   
		   props.put("mail.smtp.auth", "true"); //允许smtp校验   
		   Session sendMailSession = Session.getInstance(props, null);

		   try {
		    Transport transport = sendMailSession.getTransport("smtp");
		    transport.connect("smtp.vanceinfo.com", "zhao_jin", "2008YWBOMHF");
		    Message newMessage = new MimeMessage(sendMailSession);

		    //设置mail主题
		    String mail_subject = "更改ishop账户密码";
		    sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();   
		    newMessage.setSubject("=?GB2312?B?"+enc.encode(mail_subject.getBytes())+"?=");		    

		    //设置发信人地址   
		    String strFrom = "zhao_jin@vanceinfo.com"; 
		    strFrom = new String(strFrom.getBytes(), "8859_1");
		    newMessage.setFrom(new InternetAddress (strFrom));		    
		    //设置收件人地址		    
		    newMessage.setRecipients(Message.RecipientType.TO, this.addressTo);

		    //设置mail正文   
		    newMessage.setSentDate(new java.util.Date());
		    String mail_text = "尊敬的用户"+this.userName+"：\n" +
		    		"       您好！您的密码已被重置为"+pwd+"，请及时修改以免造成不必要的损失！\n\n" +
		    		"ishop客服\n" +
		    		"zj36083@163.com";
		    mail_text = new String(mail_text.getBytes("iso-8859-1"),"gb2312");		    
		    newMessage.setContent(mail_text, "text/plain;charset=gb2312");
		    newMessage.saveChanges(); //保存发送信息   
		    transport.sendMessage(newMessage, newMessage.getRecipients(Message.RecipientType.TO)); //发送邮件   
		    transport.close();		   
		    System.out.println("Send success！");
		    return true;
		   } catch (Exception e) {
		    System.out.println("Send false!"+e);
		    return false;
		   }
	}
}
