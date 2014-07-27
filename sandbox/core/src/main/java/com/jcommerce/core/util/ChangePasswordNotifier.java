/**
 * Author: Bob Chen
 */

package com.jcommerce.core.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ChangePasswordNotifier {
	private MailConfig config;

	public ChangePasswordNotifier(MailConfig config) {
		this.config = config;
	}

	public void sendMail(Address addressTo[], String user, String pwd){
        String subject = "更改JCOMMERCE账户密码";
        String mail_text = 
            "尊敬的${user}：\n" +
            "       您好！您的密码已被重置为${password}，请及时修改以免造成不必要的损失！\n\n";
        
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        
        try {
            String text = new TempleteProcessor().getText(mail_text, props);
            new MailSender(config).sendMail(addressTo, subject, text);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
	    MailConfig config = new MailConfig();
	    config.setSmtpServer("smtp.163.com");
	    config.setUser("123");
	    config.setPassword("123");
	    config.setNeedauthentication(true);
	    config.setSenderAddress("123@163.com");
	    
        try {
            Address addressTo[] = new Address[] {new InternetAddress("123@163.com")}; 
    	    new ChangePasswordNotifier(config).sendMail(addressTo, "Bob", "123456");
        } catch (AddressException e) {
            e.printStackTrace();
        }
	    
    }
}
