package com.jcommerce.gwt.client.panels.member;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.DateWidget;
import com.jcommerce.gwt.client.widgets.ValueSelector;

/**
 * Example file.
 */
public class NewUsers extends ContentWidget {
    public static interface Constants {
        String User_title();
        String User_edituser();
        String User_username();
        String User_usernameexist();
        String User_email();
        String User_emailexist();
        String User_password();
        String User_confirmpassword();
        String User_userrank();
        String User_sex();
        String User_secrecy();
        String User_male();
        String User_female();
        String User_creditline();
        String User_birthday();
        String User_MSN();
        String User_QQ();
        String User_officephone();
        String User_homephone();
        String User_mobilephome();
        String User_wrongpassword();
        String User_wrongqq();
        String User_wrongphone();
        String User_wrongemail();
        String User_addSuccessfully();
        String User_modifySuccessfully();
    }

	ColumnPanel contentPanel = new ColumnPanel();	

	Button btnOK = new Button();
	Button btnCancel = new Button();

	private DateWidget birthday = new DateWidget();
	private static NewUsers instance = null;
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
	public static class State extends PageState {
	    BeanObject user;
	    
        public BeanObject getUser() {
            return user;
        }

        public void setUser(BeanObject user) {
            this.user = user;
            setEditting(user != null);
        }

        public String getPageClassName() {
			return NewUsers.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "添加会员";
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	// end of block
	
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "添加会员";
	}

	public static NewUsers getInstance() {
		if (instance == null) {
			instance = new NewUsers();
		}
		return instance;
	}
	
	/**
	 * Initialize this example.
	 */
	public NewUsers() {
	}
	
	protected void onRender(Element parent,int index){
	    super.onRender(parent,index);
	    
		add(contentPanel);
		contentPanel.createPanel(IUser.NAME, "会员名称:", new TextBox());
		//        createPanel(SN, "SN:", new TextBox());

		contentPanel.createPanel(IUser.EMAIL, "邮件地址:", new TextBox());
		contentPanel.createPanel(IUser.PASSWORD, "登陆密码:", new PasswordTextBox());
		contentPanel.createPanel(null, "确认密码:", new PasswordTextBox());
		ValueSelector selector = new ValueSelector();
		selector.setBean(ModelNames.USERRANK);
		selector.setCaption("Select RANK");
		selector.setMessage("Select RANK");
		contentPanel.createPanel(IUser.RANK, "会员等级:", selector);
		ListBox listsex = new ListBox();
		listsex.addItem("保密", "0");
		listsex.addItem("男", "1");
		listsex.addItem("女", "2");
		contentPanel.createPanel(IUser.SEX,"性别:",listsex);
		contentPanel.createPanel(IUser.BIRTHDAY, "出生日期:", birthday);
//		contentPanel.createPanel(IUser.CREDITLINE, "信用额度:", new TextBox());
		contentPanel.createPanel(IUser.MSN, "MSN:", new TextBox());
		contentPanel.createPanel(IUser.QQ, "QQ:", new TextBox());
		contentPanel.createPanel(IUser.OFFICEPHONE, "办公电话:", new TextBox());
		contentPanel.createPanel(IUser.HOMEPHONE, "家庭电话:", new TextBox());
		contentPanel.createPanel(IUser.MOBILEPHONE, "手机:", new TextBox());

		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(10);
		btnOK.setText("确定");
		btnCancel.setText("重置");
		panel.add(btnOK);
		panel.add(btnCancel);
		contentPanel.createPanel(null, null, panel);

		btnOK.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				Map<String, Object> args = contentPanel.getValues();
				
//				Window.alert(args.get(IUser.RANK).toString());
				
//				args.put(IUser.REGISTERTIME, new Date());				
//				args.put(IUser.BIRTHDAY, birthday.getValue());
				
				if (getCurState().isEditting()) {
				    BeanObject user = getCurState().getUser();
				    user.setValues(args);
				    new UpdateService().updateBean(user.getString(IUser.ID), user, null);
				} else {
    				new CreateService().createBean(new BeanObject(ModelNames.USER, args), 
    					    new CreateService.Listener()
    						{
    							public void onSuccess(String id) 
    							{
    								gotoSuccessPanel();
    							}
    						});
				}
			}
		});
		
		btnCancel.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				contentPanel.clearValues();
			}
		});
		
		if (getCurState().isEditting()) {
            contentPanel.updateValues(getCurState().getUser().getProperties());
		}
	}
	
	private void gotoSuccessPanel() {
		Success.State newState = new Success.State();
		newState.setMessage("添加会员成功！");
		// fei---------------------------------------------------------------------
		UserListPanel.State choice1 = new UserListPanel.State();
		newState.addChoice(UserListPanel.getInstance().getName(), choice1);
		
		NewUsers.State choice2 = new NewUsers.State();
		newState.addChoice(NewUsers.getInstance().getName(), choice2);

		newState.execute();
	}
}
