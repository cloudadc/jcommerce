/**
 * monkey
 */
package com.jcommerce.gwt.client.panels.member;

import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserAccount;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class CheckBounds extends ContentWidget {

	private RadioButton notConfirm; // 未确认
	private RadioButton complete; // 已完成
	ColumnPanel contentPanel = new ColumnPanel();
	Button btnOK = new Button();
	Label applyInfo = new Label();
	TextArea info = new TextArea();
//	String replyId; // 留言ID
//	BeanObject object = new BeanObject(); // 留言
	TextArea manager = new TextArea();
	
	private static CheckBounds instance;

	public static class State extends PageState {
	    BeanObject comment = new BeanObject(); // 留言
	    
		public BeanObject getComment() {
            return comment;
        }

        public void setComment(BeanObject comment) {
            this.comment = comment;
        }

        public String getPageClassName() {
			return CheckBounds.class.getName();
		}

		public String getMenuDisplayName() {
			return "到款审核";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public String getName() {
		return "到款审核";
	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}
	public static CheckBounds getInstance() {
		if (instance == null) {
			instance = new CheckBounds();
		}
		return instance;
	}

	protected void onRender(com.google.gwt.user.client.Element parent, int index) {
		super.onRender(parent, index);
		
		ContentPanel panel = new ContentPanel();
		ButtonBar buttons = new ButtonBar();
		com.extjs.gxt.ui.client.widget.button.Button message = new com.extjs.gxt.ui.client.widget.button.Button(
			"充值和申请提现", new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
                    MemberApplication.State state = new MemberApplication.State();
                    state.execute();
				}
			});
		buttons.add(message);
		buttons.setAlignment(HorizontalAlignment.RIGHT);
		panel.add(buttons);
		panel.setFrame(true);
		add(panel);
		
		VerticalPanel msgPanel = new VerticalPanel();
		applyInfo.setText("会员金额信息：");
		msgPanel.add(applyInfo);
		info.setWidth("400px");
		info.setHeight("80px");
		info.setEnabled(false);
		msgPanel.add(info);
		msgPanel.setBorders(true);
		msgPanel.setHeight(100);
		contentPanel.createPanel(null, null, msgPanel);
		
		manager.setWidth("300px");
		manager.setHeight("70");
		contentPanel.createPanel(IUserAccount.ADMINNOTE, "管理员备注：", manager);
		HorizontalPanel statePanel = new HorizontalPanel();
        notConfirm = new RadioButton(IUserAccount.PAID, "未确认");
        complete = new RadioButton(IUserAccount.PAID, "已完成");
        statePanel.add(notConfirm);
        statePanel.add(complete);
        notConfirm.setChecked(true);
        contentPanel.createPanel(IUserAccount.PAID, "到款状态：", statePanel); // 到款状态
		btnOK.setText("确定");
		contentPanel.createPanel(null, null, btnOK);

		btnOK.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                BeanObject comment = getCurState().getComment();
				Map<String, Object> values = comment.getProperties();
				values.remove(IUserAccount.ADMINNOTE);
				values.put(IUserAccount.ADMINNOTE, manager.getText());
				if( complete.isChecked() ) {
					values.remove(IUserAccount.PAID);
					values.put(IUserAccount.PAID, true);
				}
				BeanObject newBean = new BeanObject(ModelNames.USERACCOUNT, values);
				new UpdateService().updateBean(comment.getString(IUserAccount.ID), newBean, new UpdateService.Listener() {
					@Override
					public void onSuccess(Boolean success) {
						if( complete.isChecked() ) {
							Info info = new Info();
							info.show(new InfoConfig("恭喜", "到款审核成功！"));
		                    MemberApplication.State state = new MemberApplication.State();
		                    state.execute();
						} else {
		                    MemberApplication.State state = new MemberApplication.State();
		                    state.execute();
						}
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
//		replyId = bean.getString(IUserAccount.ID);
//	}
//	
	public void refresh() {
	    final BeanObject comment = getCurState().getComment();
		new ReadService().getBean(ModelNames.USER, comment.getString(IUserAccount.USER), new ReadService.Listener() {
			public synchronized void onSuccess(BeanObject result) {
				info.setText("会员名称： " + result.getString(IUser.NAME) + " 金额： " + comment.getString(IUserAccount.AMOUNT)
						+ "\n操作日期： " + comment.getString(IUserAccount.ADDTIME) + " 会员描述： " + comment.getString(IUserAccount.USERNOTE));
			}
		});
		// 得到管理员备注
		manager.setText(comment.getString(IUserAccount.ADMINNOTE));
		notConfirm.setChecked(true);
	}
	
}
