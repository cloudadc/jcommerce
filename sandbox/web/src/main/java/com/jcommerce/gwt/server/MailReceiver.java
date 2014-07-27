package com.jcommerce.gwt.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class MailReceiver {

	private MimeMessage mimeMessage = null;    
	private String saveAttachPath = ""; //附件下载后的存放目录    
	private StringBuffer bodytext = new StringBuffer();//存放邮件内容    
	private String dateformat = "yy-MM-dd HH:mm"; //默认的日前显示格式
	private String fromAddr;//发件人
	
	public MailReceiver(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}
	
	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}
	
	/*
	 * 获得发件人的地址和姓名
	 */
	public String getFromAddress() {
		try {
			InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
			// 获得发件人地址
			String from = address[0].getAddress();
			if( from == null ) {
				from = "";
			}
			// 获得发件人姓名
			String personal = address[0].getPersonal();
			if( personal == null ) {
				personal = "";
			}
			// 构造发件人显示地址
			fromAddr = personal + "<" + from + ">";
		} catch (MessagingException e) {
			throw new RuntimeException("Exception when returns the value of the RFC 822 from header fields");
		}
		return fromAddr;
	}
	
	/*  
	 * 根据所传递的参数的不同,获得邮件的收件人，抄送，和密送的地址和姓名
	 * "to"----收件人
	 * "cc"---抄送人地址 
	 * "bcc"---密送人地址   
	 */   
	
	public String getMailAddress(String type) {
		String mailAddr = "";
		String receiveType = type.toUpperCase();
		InternetAddress[] address = null;
		
		// 根据类型获得接受地址
		try {
			if( receiveType.equals("TO") ) {
				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
			} else if( receiveType.equals("CC") ) {
				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
			} else if( receiveType.equals("BCC") ) {
				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
			} else {
				throw new RuntimeException("unknown type for : " + type);
			}	
		} catch (MessagingException e) {
			throw new RuntimeException("MessagingException : " + e.getMessage());
		}
		
		if( address != null ) {
			for( InternetAddress addr : address ) {
				String email = addr.getAddress();
				if( email == null ) {
					email = "";
				} else {					
					try {
						// 将email地址的内容编码成邮件安全的形式
						email = MimeUtility.decodeText(email);
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException("encoding the email address exception : " + e.getMessage());
					}	
				}
				String personal = addr.getPersonal();
				if ( personal == null ) {
					personal = "";
				} else {
					try {
						// 将email地址的内容编码成邮件安全的形式
						personal = MimeUtility.decodeText(personal);
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException("encoding the email personal exception : " + e.getMessage());
					}	
				}
				String total = personal + "<" + email + ">";
				mailAddr += "," + total;
			}
			// 去掉第一个逗号
			mailAddr = mailAddr.substring(1);
		}
		return mailAddr;
	}
	
	/*
	 * 获得邮件主题
	 */
	public String getSubject() {
		String subject = "";
		try {
			subject = mimeMessage.getSubject();
			if( subject == null ) {
				subject = "";
			} else {
				subject = MimeUtility.decodeText(subject);
			}
		} catch (MessagingException e) {
			throw new RuntimeException("get subject exception : " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encoding subject exception : " + e.getMessage());
		}		
		return subject;
	}
	
	/*   
	 * 获得邮件发送日期   
	 */   
	public String getSentDate() throws Exception {    
		Date sentdate = mimeMessage.getSentDate();    
		SimpleDateFormat format = new SimpleDateFormat(dateformat);    
		return format.format(sentdate);    
	} 
	
	/*
	 * 获得邮件正文内容
	 */
	public String getContent() {
		return bodytext.toString();
	}
	
	/*  
	 * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，
	 * 解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析   
	 */
	public void getMailContent(Part part) throws Exception {

		String contenttype = part.getContentType();
        int nameindex = contenttype.indexOf("name");
        boolean conname = false;
        if (nameindex != -1) {
            conname = true;
        }
        // 邮件类型包含两种：text/plain,text/html 
        // 没有html标记
        if (part.isMimeType("text/plain") && !conname) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("text/html") && !conname) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
            	System.out.println(counts);
                getMailContent(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent());
        } else {
        	
        }
    }
	
	/*
     * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
     */
    public boolean getReplySign() throws MessagingException {
    	boolean replysign = false;
    	// 获得回执功能的headers
    	String needReply[] = mimeMessage.getHeader("Disposition-Notification-To");
    	if( needReply != null ) {
    		replysign = true;
    	}
        return replysign;
    }

    /*
     * 获得此邮件的Message-ID
     */
    public String getMessageId() throws MessagingException {
        return mimeMessage.getMessageID();
    }
    
    /*
     * 判断此邮件是否已读，如果未读返回返回 false,反之返回true
     */
    public boolean isNew() throws MessagingException {
    	boolean isNew = false;
    	Flags flags = ((Message)mimeMessage).getFlags();
    	Flags.Flag[] flag = flags.getSystemFlags();
    	System.out.println("flags's length: " + flag.length);
    	for(int i = 0; i < flag.length; i++ ) {
    		if( flag[i] == Flags.Flag.SEEN ) {
    			isNew = true;
    			break;
    		}
    	}
        return isNew;
    }
    
    /*
     * 判断此邮件是否包含附件
     */
    public boolean isContainAttach(Part part) throws Exception
    {
        boolean attachFlag = false;
        
        if ( part.isMimeType( "multipart/*" ) ) {
        	
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
                	attachFlag = true;
                } else if (mpart.isMimeType("multipart/*")) {
                	attachFlag = isContainAttach((Part) mpart);
                } else {
                    String contype = mpart.getContentType();
                    if (contype.toLowerCase().indexOf("application") != -1) {
                    	attachFlag = true;
                    }
                    if (contype.toLowerCase().indexOf("name") != -1) {
                    	attachFlag = true;
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
        	attachFlag = isContainAttach((Part) part.getContent());
        }
        return attachFlag;
    }
    
    /*
     * 保存附件
     */
    public void saveAttachMent(Part part) throws Exception
    {
        String fileName = "";
        if (part.isMimeType("multipart/*")) {
        	
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
                    fileName = mpart.getFileName();
                    if (fileName != null && fileName.toLowerCase().indexOf("gb2312") != -1) {
                        fileName = MimeUtility.decodeText(fileName);
                    }
                    saveFile(fileName, mpart.getInputStream());
                } else if (mpart.isMimeType("multipart/*")) {
                    saveAttachMent(mpart);
                } else {
                    fileName = mpart.getFileName();
                    if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
                        fileName = MimeUtility.decodeText(fileName);
                        saveFile(fileName, mpart.getInputStream());
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachMent((Part) part.getContent());
        }
    }
    
    /**
     * 真正的保存附件到指定目录里
     */
    private void saveFile(String fileName, InputStream in) throws Exception {
    	
        String osName = System.getProperty("os.name");
        String storedir = getAttachPath(); // 附件保存路径
        String separator = "";
        if (osName == null) {
            osName = "";
        }
        if (osName.toLowerCase().indexOf("win") != -1) {
            separator = "\\";
            if (storedir == null || storedir.equals("")) {
                storedir = "c:\\tmp";
            }
        } else {
            separator = "/";
            storedir = "/tmp";
        }
        File storefile = new File(storedir + separator + fileName);
        // for(int i=0;storefile.exists();i++){
        // storefile = new File(storedir+separator+fileName+i);
        // }
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storefile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("文件保存失败!");
        } finally {
            bos.close();
            bis.close();
        }
    }

    /*
     * 设置附件存放路径
     */
    public void setAttachPath(String attachpath) {
        this.saveAttachPath = attachpath;
    }

    /*
     * 获得附件存放路径
     */
    public String getAttachPath() {
        return saveAttachPath;
    }
    
    /*
     * 设置日期显示格式
     */
    public void setDateFormat(String format) throws Exception {
    	this.dateformat = format;
    }
    
    /*
     * @test
     */
    public static void main(String[] args) throws Exception {
    	Properties props = System.getProperties(); 
    	props.put("mail.smtp.host", "smtp.163.com");   
    	props.put("mail.smtp.auth", "true");
    	Session session = Session.getDefaultInstance(props, null);
    	URLName urln = new URLName("pop3", "pop3.163.com", 110, null, "wang8763710", "wang3864995");
    	Store store = session.getStore(urln);
    	try {
			store.connect();
		} catch (Exception e) {
			throw new RuntimeException("connection exception : " + e.getMessage());
		}   
    	Folder folder = store.getFolder("INBOX");   
    	folder.open(Folder.READ_ONLY);   
    	Message message[] = folder.getMessages();   
    	System.out.println("Messages's length: " + message.length + "\n");   
    	MailReceiver mailReceiver = null; 
    	for(int i = 0; i < message.length; i++ ) {
    		mailReceiver = new MailReceiver((MimeMessage) message[i]);
    		System.out.println("Message " + i + " subject: " + mailReceiver.getSubject());   
    		System.out.println("Message " + i + " sentdate: "+ mailReceiver.getSentDate());   
    		System.out.println("Message " + i + " replysign: "+ mailReceiver.getReplySign());   
    		System.out.println("Message " + i + " hasRead: " + mailReceiver.isNew());   
    		System.out.println("Message " + i + "  containAttachment: "+ mailReceiver.isContainAttach((Part) message[i]));   
    		System.out.println("Message " + i + " form: " + mailReceiver.getFromAddress());   
    		System.out.println("Message " + i + " to: "+ mailReceiver.getMailAddress("to"));   
    		System.out.println("Message " + i + " cc: "+ mailReceiver.getMailAddress("cc"));   
    		System.out.println("Message " + i + " bcc: "+ mailReceiver.getMailAddress("bcc"));   
    		mailReceiver.setDateFormat("yy年MM月dd日 HH:mm");   
    		System.out.println("Message " + i + " sentdate: "+ mailReceiver.getSentDate());   
    		System.out.println("Message " + i + " Message-ID: "+ mailReceiver.getMessageId());   
    		// 获得邮件内容  
    		mailReceiver.getMailContent((Part) message[i]);   
    		System.out.println("Message " + i + " bodycontent: \r\n" + mailReceiver.getContent());   
    		mailReceiver.setAttachPath("c:\\");    
    		mailReceiver.saveAttachMent((Part) message[i]);
    	}
	}
    
}
