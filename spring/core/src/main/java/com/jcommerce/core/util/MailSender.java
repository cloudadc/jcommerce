/**
 * Author: Bob Chen
 */

package com.jcommerce.core.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	private MailConfig config;

	public MailSender(MailConfig config) {
		this.config = config;
	}

    public void sendMail(Address addressTo[], String subject, String content) throws MessagingException {
        Authenticator auth = new javax.mail.Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                String username = config.getUser();
                String password = config.getPassword();
                return new PasswordAuthentication(username, password);
            }
        };

        Properties props = new Properties();
        props.put("mail.smtp.host", config.getSmtpServer());
        props.put("mail.smtp.auth", "" + config.isNeedauthentication());
        Session sendMailSession = Session.getInstance(props, auth);

        Transport transport = sendMailSession.getTransport("smtp");
        transport.connect(config.getSmtpServer(), config.getUser(), config.getPassword());
        Message newMessage = new MimeMessage(sendMailSession);

        newMessage.setSubject(subject);

        if (config.getSenderAddress() != null && config.getSenderAddress().trim().length() > 0) {
            newMessage.setFrom(new InternetAddress(config.getSenderAddress()));
        }
        newMessage.setRecipients(Message.RecipientType.TO, addressTo);

        newMessage.setText(content);
        transport.send(newMessage);
        transport.close();
    }
}
