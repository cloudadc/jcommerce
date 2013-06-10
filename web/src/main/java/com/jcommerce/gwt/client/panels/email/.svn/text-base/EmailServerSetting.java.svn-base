package com.jcommerce.gwt.client.panels.email;

import java.util.HashMap;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.ServerSettingConstants;
import com.jcommerce.gwt.client.service.EmailSettings;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * @author monkey
 */

public class EmailServerSetting extends ContentWidget{

	/**
	 * 定义的常量和配置文件EmailServerSettings.properties中的字段一致。
	 */
	private class Constants {
		
		public static final String SERVICE_TYPE_MAIL = "serviceType_mail";
		public static final String SERVICE_TYPE_SMTP = "serviceType_smtp";
		public static final String SERVICE_TYPE = "serviceType";
		public static final String SERVICE_ADDRESS = "serverAddress";
		public static final String SERVICE_PORT = "serverPort";
		public static final String ACCOUNT = "account";
		public static final String PASSWORD = "password";
		public static final String REPLYADDRESS = "replyAddress";
		public static final String ENCODED = "encoded";
		public static final String EMAILADDRESS = "emailAddress";
		public static final String MAIL = "mail";
		public static final String SMTP = "smtp";
		
	}
	
	private ColumnPanel contentPanel = new ColumnPanel();
	private ColumnPanel total = new ColumnPanel();
	private Button btnNew = new Button();    
    private Button btnCancel = new Button(); 
    private Button testSendButton = new Button();
    private TextBox serverAddress = new TextBox();
    private TextBox serverPort = new TextBox();
    private TextBox account = new TextBox();
    private TextField  password = new TextField();
    private TextBox reply = new TextBox();
    private TextBox encoded = new TextBox();
    private TextBox mailAddress = new TextBox();
    private RadioButton mail;
    private RadioButton smtp;
    private boolean isSaved = false;
    
	/**
	 * @return the isSaved
	 */
	public boolean isSaved() {
		return isSaved;
	}

	/**
	 * @param isSaved the isSaved to set
	 */
	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public static class State extends PageState {
		public String getPageClassName() {
			return EmailServerSetting.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "邮件服务器设置";
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	
	@Override
	public String getDescription() {
		return "EmailServerSettingDescription";
	}

	@Override
	public String getName() {
		return "邮件服务器设置";
	}
	
	public EmailServerSetting() {
		// 信息提示.
		final ContentPanel inforPanel = new ContentPanel();
		inforPanel.add(new Label("如果您的服务器支持 Mail 函数（具体信息请咨询您的空间提供商）。我们建议您使用系统的 Mail 函数。当您的服务器不支持 Mail 函数的时候您也可以选用 SMTP 作为邮件服务器。"));
		inforPanel.setWidth(850);
		inforPanel.setHeight(100);
		inforPanel.setFrame(true);
		inforPanel.setCollapsible(true);
		
        // 邮件服务.
        HorizontalPanel emailServicePanel = new HorizontalPanel();
        mail = new RadioButton(ServerSettingConstants.SERVICE, "采用服务器内置的 Mail 服务");
        smtp = new RadioButton(ServerSettingConstants.SERVICE, "采用其他的 SMTP 服务");
        emailServicePanel.add(mail);
        emailServicePanel.add(smtp);
        contentPanel.createPanel(ServerSettingConstants.SERVICE, "邮件服务: ", emailServicePanel);
        contentPanel.createPanel(null, null, new Label("(如果您选择了服务器内置的 Mail 服务，就无需填写下面内容)"));
        // 存放发送邮件服务器地址(SMTP).
        serverAddress.setWidth("300px");
        contentPanel.createPanel(ServerSettingConstants.SERVERADDRESS, "发送邮件服务器地址(SMTP):", serverAddress); 
        contentPanel.createPanel(null, null, new Label("(邮件服务器主机地址。如果本机可以发送邮件则设置为localhost)"));
        // 服务器端口.
        serverPort.setWidth("300px");
        contentPanel.createPanel(ServerSettingConstants.SERVERPORT, "服务器端口:", serverPort); 
        // 邮件发送帐号.
        account.setWidth("300px");
        contentPanel.createPanel(ServerSettingConstants.ACCOUNT, "邮件发送帐号:", account);
        contentPanel.createPanel(null, null, new Label("(发送邮件所需的认证帐号，如果没有就空着)"));
        // 帐号密码.
        password.setWidth("300px");
        password.setPassword(true);
        contentPanel.createPanel(ServerSettingConstants.ACCOUNTPASSWORD, "帐号密码:", password); 
        // 邮件回复地址.
        reply.setWidth("300px");
        contentPanel.createPanel(ServerSettingConstants.EMAILREPLYADDRESS, "邮件回复地址:", reply); 
        // 邮件编码.
        HorizontalPanel emailEncodedPanel = new HorizontalPanel();
        RadioButton utf8 = new RadioButton(ServerSettingConstants.EMAILENCOEDED, "国际化编码(utf8)");
        RadioButton simple = new RadioButton(ServerSettingConstants.EMAILENCOEDED, "简体中文");
        RadioButton complex = new RadioButton(ServerSettingConstants.EMAILENCOEDED, "繁体中文");
        utf8.setChecked(true);
        emailEncodedPanel.add(utf8);
        emailEncodedPanel.add(simple);
        emailEncodedPanel.add(complex);
        encoded.setWidth("300px");
        contentPanel.createPanel(ServerSettingConstants.EMAILENCOEDED, "邮件编码:", emailEncodedPanel); 
        // 邮件地址.
        HorizontalPanel address = new HorizontalPanel();
        mailAddress.setWidth("300px");
        address.add(mailAddress);
        testSendButton.setText("发送测试邮件");
        testSendButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				EmailSettings emailSettings = new EmailSettings();
				emailSettings.sendTestEmailAndGetState(new EmailSettings.Listener() {
					public synchronized void onSuccess(Boolean result) {
						boolean isSent = false;
						isSent = result.booleanValue();
						if( isSent ) {
							MessageBox.alert("Successful", "Send test email successfully!", null);
						}else {
							MessageBox.alert("Failure", " Send test email failure! Please cheak the SMTP address or email account and password! ", null);
						}
					}	
				});
			}
        	
        });
        address.add(testSendButton);
        contentPanel.createPanel(ServerSettingConstants.EMAILADDRESS, "邮件地址:", address); 
        // 按钮.
        contentPanel.createPanel(null, null, getButtonPanel());
        final ContentPanel emailPanel = new ContentPanel();
        emailPanel.add(contentPanel);
        emailPanel.setHeading("邮件服务器设置");
        emailPanel.setWidth(850);
        emailPanel.setHeight(500);
        emailPanel.setFrame(true);
        emailPanel.setCollapsible(true);

        total.add(inforPanel);
        total.add(emailPanel);
		add(total);
		
		getSettingsData();
	}
	
	/**
	 * Button
	 * @return buttonPanel
	 */
	private HorizontalPanel getButtonPanel() {
		
		HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定"); 
        btnNew.addClickListener(new ClickListener() {
        	
			public void onClick(Widget sender) {
				EmailSettings emailSettings = new EmailSettings();
				emailSettings.saveSettingsInfo(saveData(), new EmailSettings.Listener() {	
					public synchronized void onSuccess(Boolean result) {
						if( result.booleanValue() ) {
							MessageBox.alert("OK", "save data successfully!", null);
						} else {
							MessageBox.alert("Error", "save data error!", null);
						}					
					}	
					public void onFailure(Throwable caught) {		
						MessageBox.alert("Error", "save data error!", null);
					}
				});	
			}
        	
        });
        btnCancel.setText("重置");
        btnCancel.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				serverAddress.setText("");
				serverPort.setText("");
				account.setText("");
				password.setValue("");
				reply.setText("");
				encoded.setText("");
				mailAddress.setText("");
			}
        	
        });
        panel.add(btnNew);        
        panel.add(btnCancel);
        
        return panel;
	}
	
	/**
	 * 通过读取的各个字段的值将界面初始化。
	 */
	private void getSettingsData() {
		EmailSettings emailSettings = new EmailSettings();
		emailSettings.getSettingsInfo(new EmailSettings.Listener() {
			public synchronized void onSuccess(HashMap<String, String> result) {
				HashMap<String, String> settings = result;
				// Email service: mail
				String serviceType_mail = settings.get(Constants.SERVICE_TYPE_MAIL);
				// Email service: SMTP
				String serviceType_smtp = settings.get(Constants.SERVICE_TYPE_SMTP);
				// set data to the panel
				String serviceType = settings.get(Constants.SERVICE_TYPE);
				if( serviceType_mail.equals(serviceType) ){
					mail.setChecked(true);
				} else {
					smtp.setChecked(true);
				}
				// server address
				serverAddress.setText(settings.get(Constants.SERVICE_ADDRESS));
				// server port
				serverPort.setText(settings.get(Constants.SERVICE_PORT));
				// account
				account.setText(settings.get(Constants.ACCOUNT));
				// password
				password.setValue(settings.get(Constants.PASSWORD));
				// reply
				reply.setText(settings.get(Constants.REPLYADDRESS));
				// encoded
				encoded.setText(settings.get(Constants.ENCODED));
				// mailAddress
				mailAddress.setText(settings.get(Constants.EMAILADDRESS));
			}
		});		
	}
	
	/**
	 * 将用户配置信息保存。
	 */
	private HashMap<String, String> saveData() {

		// key : profile information
		// value : data of configuration
		HashMap<String, String> profile = new HashMap<String, String>();
		profile.put(Constants.SERVICE_TYPE_MAIL, Constants.MAIL);
		profile.put(Constants.SERVICE_TYPE_SMTP, Constants.SMTP);
		
		// read user configuration data
		String serviceType = null;
		if( mail.isChecked() ) {
			serviceType = Constants.MAIL;
		}else if( smtp.isChecked() ) {
			serviceType = Constants.SMTP;
		}
		String address = serverAddress.getText().trim();
		String port = serverPort.getText().trim();
		String userAccount = account.getText();
		String userPassword = (String)password.getValue();
		String mailReply = reply.getText();
		String mailEncoded = encoded.getText();
		String emailAddress = mailAddress.getText();
		
		profile.put(Constants.SERVICE_TYPE, serviceType);
		profile.put(Constants.SERVICE_ADDRESS, address);
		profile.put(Constants.SERVICE_PORT, port);
		profile.put(Constants.ACCOUNT, userAccount);
		profile.put(Constants.PASSWORD, userPassword);
		profile.put(Constants.REPLYADDRESS, mailReply);	
		profile.put(Constants.ENCODED, mailEncoded);
		profile.put(Constants.EMAILADDRESS, emailAddress);
		
		return profile;
	}
	
}
