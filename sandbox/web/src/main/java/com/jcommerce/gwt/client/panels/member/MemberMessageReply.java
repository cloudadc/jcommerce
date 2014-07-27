/**
 * @author monkey
 * @time 2010.02.02
 */
package com.jcommerce.gwt.client.panels.member;

import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IFeedback;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.panels.privilege.AdminLog;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class MemberMessageReply extends ContentWidget {

	ColumnPanel contentPanel = new ColumnPanel();
	Button btnOK = new Button();
	TextArea msgSubject = new TextArea();
	TextArea msgContent = new TextArea();
	Label msgTime = new Label();
	TextBox replyEmail = new TextBox();
	TextArea replyContent = new TextArea();
//	String replyId; // 留言ID
//	BeanObject object = new BeanObject(); // 留言
	
	private static MemberMessageReply instance;

	public static class State extends PageState {
	    BeanObject comment = null; // 留言
	    
		public BeanObject getComment() {
            return comment;
        }

        public void setComment(BeanObject comment) {
            this.comment = comment;
            setEditting(comment != null);
        }

        public String getPageClassName() {
			return MemberMessageReply.class.getName();
		}

		public String getMenuDisplayName() {
			return "回复";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public String getName() {
		return "回复";
	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}
	public static MemberMessageReply getInstance() {
		if (instance == null) {
			instance = new MemberMessageReply();
		}
		return instance;
	}

	protected void onRender(com.google.gwt.user.client.Element parent, int index) {
		super.onRender(parent, index);
		
		ContentPanel panel = new ContentPanel();
		ButtonBar buttons = new ButtonBar();
		com.extjs.gxt.ui.client.widget.button.Button message = new com.extjs.gxt.ui.client.widget.button.Button(
			"会员留言", new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					// 显示留言列表
					MemberMessage.State state = new MemberMessage.State();
					state.execute();
				}
			});
		buttons.add(message);
		buttons.setAlignment(HorizontalAlignment.RIGHT);
		panel.add(buttons);
		panel.setWidth(850);
		panel.setFrame(true);
		add(panel);
		
		VerticalPanel msgPanel = new VerticalPanel();
		msgSubject.setHeight("60px");
		msgSubject.setWidth("500px");
		msgPanel.add(msgSubject);
		msgContent.setHeight("60px");
		msgContent.setWidth("500px");
		msgPanel.add(msgContent);
		msgTime.setHeight("60px");
		msgPanel.add(msgTime);
		msgPanel.setBorders(true);
		msgPanel.setHeight(200);
		msgPanel.setWidth(500);

		replyEmail.setWidth("300px");
		replyContent.setWidth("300px");
		contentPanel.createPanel(null, "原留言内容：", msgPanel);
		contentPanel.createPanel(null, "email：", replyEmail);
		contentPanel.createPanel(null, "回复内容：", replyContent);
		
		btnOK.setText("确定");
		contentPanel.createPanel(null, null, btnOK);

		btnOK.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				// 标记状态为已经回复
                BeanObject comment = getCurState().getComment();
				Map<String, Object> values = comment.getProperties();
				values.remove(IFeedback.ORDERID); // 0表示未回复
				values.put(IFeedback.ORDERID, 1); // 1表示已回复
				
//				values.remove(IFeedback.USERNAME);
//				values.put(IFeedback.USERNAME, "匿名用户");
//				values.remove(IFeedback.MSGTITLE);
//				values.put(IFeedback.MSGTITLE, "没收到货物");
//				values.remove(IFeedback.MSGCONTENT);
//				values.put(IFeedback.MSGCONTENT, "我的订单号是××××××，显示发货已经3天，但是还没有收到货物！");
				
				BeanObject bean = new BeanObject(ModelNames.FEEDBACK, values);
				
				String replyId = bean.getString(IFeedback.ID);
				new UpdateService().updateBean(replyId, bean, new UpdateService.Listener() {
					@Override
					public void onSuccess(Boolean success) {
						MemberMessage.State state = new MemberMessage.State();
						state.execute();
						Info.display("状态", "已经回复");
						AdminLog.createAdminLog("回复用户留言;");
					}
				});
				
			}
		});
		
		add(contentPanel);
	}
	
	/**
	 * 初始化界面时，将数据显示
	 * @param object 
	 */
//	public void setData( BeanObject bean ) {
//		this.object = bean;
//		replyId = bean.getString(IFeedback.ID);
//	}
//	
	public void refresh() {
	    BeanObject comment = getCurState().getComment();
	    
		String title = comment.getString(IFeedback.MSGTITLE); // 留言主题
		String content = comment.getString(IFeedback.MSGCONTENT); // 内容
		String time = comment.getString(IFeedback.USERNAME) + "@" + comment.getString(IFeedback.MSGTIME); // 时间
		msgSubject.setText(title);
		msgSubject.setEnabled(false);
		msgContent.setText(content);
		msgContent.setEnabled(false);
		msgTime.setText(time);
	}
	
}
