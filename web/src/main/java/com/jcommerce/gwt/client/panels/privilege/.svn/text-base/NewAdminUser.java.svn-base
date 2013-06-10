package com.jcommerce.gwt.client.panels.privilege;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.GoodsTypeForm;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class NewAdminUser extends ContentWidget {

	@Override
	public String getDescription() {
		return "NewAdminUserDescription";
	}
	@Override
	public String getName() {
		return "添加管理员";
	}

	public static class State extends PageState {
	    BeanObject adminUser;
	    
		public BeanObject getAdminUser() {
            return adminUser;
        }
        public void setAdminUser(BeanObject adminUser) {
            this.adminUser = adminUser;
            setEditting(adminUser != null);
        }
        public String getPageClassName() {
			return NewAdminUser.class.getName();
		}
		public String getMenuDisplayName() {
			return "管理员列表";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	
	ColumnPanel contentPanel = new ColumnPanel();	
	Button btnOK = new Button();
	Button btnCancel = new Button();
//	boolean isEdit = false;
//	BeanObject adminUser;
	TextField<String> userName;
	TextBox email = new TextBox();
	
	public NewAdminUser() {
		add(contentPanel);
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		userName = GoodsTypeForm.getNameField("用户名：");
		TextField<String> password = GoodsTypeForm.getNameField("密码：");
		password.setPassword(true);
		final TextField<String> passwordConfirm = GoodsTypeForm.getNameField("确认密码：");
		passwordConfirm.setPassword(true);
		userName.setWidth(200);
		password.setWidth(200);
		email.setWidth("200px");
		passwordConfirm.setWidth(200);
		contentPanel.createPanel(IAdminUser.NAME, "用户名：", userName);
		contentPanel.createPanel(IAdminUser.EMAIL, "Email地址：", email);
		contentPanel.createPanel(IAdminUser.PASSWORD, "密码：", password);
		contentPanel.createPanel("confirm", "确认密码：", passwordConfirm);
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(10);
		btnOK.setText("确定");
		btnCancel.setText("重置");
		panel.add(btnOK);
		panel.add(btnCancel);
		btnOK.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				Map<String, Object> values = contentPanel.getValues();
				Date currentTime = new Date();
				Timestamp nowTime = new Timestamp(currentTime.getTime());
				values.put(IAdminUser.ADDTIME, nowTime);
				if ( !getCurState().isEditting() ) {
					new CreateService().createBean(new BeanObject(ModelNames.ADMINUSER, values), new CreateService.Listener() {
						@Override
						public void onSuccess(String id) {
							contentPanel.clearValues();
							Info info = new Info();
							info.show(new InfoConfig("恭喜", "添加管理员成功！"));
							AdminLog.createAdminLog("添加管理员；");
                            AdminList.State state = new AdminList.State();
                            state.execute();
						}
					});
				} else {
					new UpdateService().updateBean(getCurState().getAdminUser().getString(IAdminUser.ID), new BeanObject(ModelNames.ADMINUSER, values), new UpdateService.Listener() {
						@Override
						public void onSuccess(Boolean success) {
							contentPanel.clearValues();
							Info info = new Info();
							info.show(new InfoConfig("恭喜", "修改管理员信息成功！"));
							AdminLog.createAdminLog("修改管理员信息；");
                            AdminList.State state = new AdminList.State();
                            state.execute();
						}
					});
				}
			}
		});
		btnCancel.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
				contentPanel.clearValues();
				passwordConfirm.setData("confirm",null);
			}
		});
		contentPanel.createPanel(null, null, panel);
		contentPanel.setBorders(true);
		contentPanel.setSize(500, 250);
	}
	
	public void refresh() {
		if( getCurState().isEditting() ) {
		    BeanObject adminUser = getCurState().getAdminUser();
			userName.setRawValue(adminUser.getString(IAdminUser.NAME));
			email.setText(adminUser.getString(IAdminUser.EMAIL));
		}
	}
	
//	public void setData(BeanObject bean) {
//		adminUser = bean;
//		isEdit = bean == null ? false : true;
//	}
//	
}
