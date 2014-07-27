package com.jcommerce.gwt.client.panels.email;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IEmailReceiver;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.service.EmailSettings;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.richTextBox.RichTextToolbar;

/**
 * @author monkey
 */
public class NewEmail extends ContentWidget {

	private ColumnPanel contentPanel = new ColumnPanel();
	private boolean isReply = false; // 判断是新建还是回复
	private BeanObject emailBean; // 假如操作是回复，数据存储的地方
	private Label senderName = new Label();
//	private String sender = null; // 发件人
	private String receive = null; // 收件人
	private TextBox receiverName = new TextBox(); // 收件人
	private TextBox emailSubject = new TextBox(); // 主题
	private final RichTextArea area = new RichTextArea(); // 正文
	private MessageBox messageBox;
	
	public static class State extends PageState {
	    private String sender = null; // 发件人
	    public String getSender() {
	        return sender;
	    }

	    public void setSender(String sender) {
	        this.sender = sender;
	    }

	    
		public String getPageClassName() {
			return NewEmail.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "写信";
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
		return "NewEmailDescription";
	}

	@Override
	public String getName() {
		if( isReply ) {
			return "回复";
		} else {
			return "写信";
		}
	}
	
//	public String getSender() {
//		return sender;
//	}
//
//	public void setSender(String sender) {
//		this.sender = sender;
//	}

	/**
	 * 设置默认发件人
	 * @param senderName
	 */
	public NewEmail() {
		
	}
	
	/**
	 * 如果是回复，通过数据set默认信息。
	 * @param data
	 */
	public void setData(String data) {
		receive = data;
		isReply = data != null;
	}
	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		// 加载默认发件账户
		if( !isReply ) {
			new EmailSettings().getSettingsInfo( new EmailSettings.Listener() {
				public void onSuccess(HashMap<String, String> result) {					
					String sender = result.get("account");
					senderName.setText(sender);
					getCurState().setSender(sender);
				}
			});
		} else {
			// 回复
			receiverName.setText(receive);
		}
		
		// Create a tab panel
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("100%");
		tabPanel.setAnimationEnabled(true);

		// Create the text area and toolbar
		area.setSize("100%", "14em");
		final RichTextToolbar toolbar = new RichTextToolbar(area);
		toolbar.setWidth("100%");

		// Add the components to a panel
		final Grid grid = new Grid(2, 1);
		grid.setStyleName("cw-RichText");
		grid.setWidget(0, 0, toolbar);
		grid.setWidget(1, 0, area);

		// Add a detail tab
		HTML properties2 = new HTML("properites");
		tabPanel.add(grid, "内容编辑");
		tabPanel.selectTab(0);
		tabPanel.ensureDebugId("cwTabPanel");
		
		// button panel
		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setLayout(new FitLayout());
        panel.setSize(600, 0);   
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        Button send = new Button("发送");
        Button cancel = new Button("取消");
        send.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	// 发送邮件
            	if( receiverName.getText() == null || receiverName.getText().trim().equals("")) {
            		MessageBox.alert("Error", "请填写收件人地址", null);
            	} else {
            		EmailSettings settings = new EmailSettings();
            		HashMap<String, String> email = new HashMap<String, String>();
            		email.put("sendTo", receiverName.getText().trim());
            		email.put("subject", emailSubject.getText().trim());
            		email.put("content", area.getText().trim());
            		settings.sendEmailAndGetState(email, new EmailSettings.Listener() {
            			public void onSuccess(Boolean result) {
            				if( result.booleanValue() ) {
            					MessageBox.alert("信息", "发送成功", null);
            					receiverName.setText("");
        						emailSubject.setText("");
        						area.setText("");
            				} else {
            					MessageBox.alert("信息", "发送失败", null);
            				}
            			}
            		});
            	}
            }
        });
        // 确认退出
        cancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				messageBox = MessageBox.confirm("确认", "你确定要退出吗？", new Listener<MessageBoxEvent>() {
				  public void handleEvent(MessageBoxEvent ce) {
				    Button btn = ce.getButtonClicked();
					String name = receiverName.getText();
					String subject = emailSubject.getText();
					String areaContent = area.getText();
					if( btn.getText().equals("否") ) {
						receiverName.setText(name);
						emailSubject.setText(subject);
						area.setText(areaContent);
					} else {
						receiverName.setText("");
						emailSubject.setText("");
						area.setText("");
					}
				}
				});
			}
        });
        panel.addButton(send);
        panel.addButton(cancel);
        
        receiverName.setWidth("350px");
        emailSubject.setWidth("350px");
        contentPanel.createPanel(IEmailReceiver.SENDERNAME, "发件人： ", senderName);
        contentPanel.createPanel(IEmailReceiver.RECEIVERNAME, "收件人： ", receiverName);
        contentPanel.createPanel(IEmailReceiver.MAILSUBJECT, "主题： ", emailSubject);
        contentPanel.createPanel(IEmailReceiver.EMAILCONTENT, "内容： ", tabPanel);
        contentPanel.createPanel("buttons", "", panel);
        
        add(contentPanel);
	}
	
	public void refresh() {
		// editting
		if ( this.emailBean != null ) {
			Map<String, Object> data = emailBean.getProperties();
			receiverName.setText((String) data.get(IEmailReceiver.RECEIVERNAME));
			emailSubject.setText((String) data.get(IEmailReceiver.MAILSUBJECT));	
			area.setText((String) data.get(IEmailReceiver.EMAILCONTENT));
		}
	}
}
