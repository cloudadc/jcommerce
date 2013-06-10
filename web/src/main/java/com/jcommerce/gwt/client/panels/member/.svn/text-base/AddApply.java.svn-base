package com.jcommerce.gwt.client.panels.member;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.GoodsTypeForm;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserAccount;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.panels.privilege.AdminLog;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.UserSelector;
/**
 * 添加申请
 * @author monkey
 */
public class AddApply extends ContentWidget {

	public static class State extends PageState {
	    private BeanObject user;
	    
		public BeanObject getUser() {
            return user;
        }

        public void setUser(BeanObject user) {
            this.user = user;
            setEditting(user != null);
        }

        public String getPageClassName() {
			return AddApply.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "添加申请";
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
		return "AddApplyDescription";
	}

	@Override
	public String getName() {
		return "添加申请";
	}

    public String getButtonText() {
        return "充值和提现申请列表";
    }
    
    protected void buttonClicked() {
        clear();
        MemberApplication.State state = new MemberApplication.State();
        state.execute();
    }
    
	private ColumnPanel contentPanel;
	private RadioButton save; // 转账
	private RadioButton take; // 提现
	private RadioButton notConfirm; // 未确认
	private RadioButton complete; // 已完成
	private RadioButton cancel; // 取消
	private Button btnOK = new Button(); // OK
	private Button btnCancel = new Button(); // cancel
	
	private UserSelector userSelector;
	private TextField accountField;
	private ListBox paymentType;
	private TextArea manager;
	private TextArea user;
	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		add(createMainPanel()); // 主界面
	}

	/**
	 * 添加信息界面
	 * @return contentPanel
	 */
	private ColumnPanel createMainPanel() {
		contentPanel = new ColumnPanel();	
		userSelector = new UserSelector();
		userSelector.setBean(ModelNames.USER);
		userSelector.setCaption("Select User");
		userSelector.setMessage("Select User");
		
		accountField = GoodsTypeForm.getNameField("金额:");
		accountField.setWidth(300);
		contentPanel.createPanel(IUserAccount.USER, "会员名称：", userSelector); // 会员名称
		contentPanel.createPanel(IUserAccount.AMOUNT, "金额：", accountField); // 金额
		paymentType = new ListBox();
		paymentType.addItem("请选择...");
		paymentType.addItem("银行汇帐/转账");
		paymentType.addItem("余额支付");
		paymentType.addItem("邮局汇款");
		contentPanel.createPanel(IUserAccount.PAYMENT, "支付方式：", paymentType); // 支付方式
		HorizontalPanel typePanel = new HorizontalPanel();
		save = new RadioButton(IUserAccount.PROCESSTYPE, "充值");
        take = new RadioButton(IUserAccount.PROCESSTYPE, "提现");
        save.setChecked(true);
        typePanel.add(save);
        Label sep = new Label();
        sep.setWidth(20);
        typePanel.add(sep);
        typePanel.add(take);
        contentPanel.createPanel(IUserAccount.PROCESSTYPE, "类型：", typePanel); // 类型
        manager = new TextArea();
        manager.setWidth(400);
        contentPanel.createPanel(IUserAccount.ADMINNOTE, "管理员备注：", manager); // 管理员备注
        user = new TextArea();
        user.setWidth(400);
        contentPanel.createPanel(IUserAccount.USERNOTE, "会员描述：", user); // 会员描述
        HorizontalPanel statePanel = new HorizontalPanel();
        notConfirm = new RadioButton(IUserAccount.PAID, "未确认");
        complete = new RadioButton(IUserAccount.PAID, "已完成");
        cancel = new RadioButton(IUserAccount.PAID, "取消");
        statePanel.add(notConfirm);
        sep = new Label();
        sep.setWidth(20);
        statePanel.add(sep);
        statePanel.add(complete);
        sep = new Label();
        sep.setWidth(20);
        statePanel.add(sep);
        statePanel.add(cancel);
        notConfirm.setChecked(true);
        contentPanel.createPanel(IUserAccount.PAID, "到款状态：", statePanel); // 到款状态
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setSpacing(10);
		btnOK.setText("确定");
		btnCancel.setText("重置");
		btnOK.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	if( !getCurState().isEditting() ) {
            		Map<String, Object> values = new HashMap<String, Object>();
                	String userName = userSelector.getValue();// 会员名称
                	values.put(IUserAccount.USER, userName);
                	String account = accountField.getRawValue();// 金额
                	values.put(IUserAccount.AMOUNT, account);
                	String payment = paymentType.getItemText(paymentType.getSelectedIndex());// 支付方式
                	values.put(IUserAccount.PAYMENT, payment);
                	// 类型
                	if( save.isChecked() ) {
                		values.put(IUserAccount.PROCESSTYPE, IUserAccount.TYPE_SAVING);
                	} else {
                		values.put(IUserAccount.PROCESSTYPE, IUserAccount.TYPE_DRAWING);
                	}
                	values.put(IUserAccount.ADMINNOTE, manager.getRawValue()); // 管理员备注
                	values.put(IUserAccount.USERNOTE, user.getRawValue()); // 会员描述
                	// 到款状态
                	if( notConfirm.isChecked() ) {
                		values.put(IUserAccount.PAID, false);
                	} else {
                		values.put(IUserAccount.PAID, true);
                	}
                	// 操作时间
                	Date currentTime = new Date();
    				Timestamp nowTime = new Timestamp(currentTime.getTime());
    				values.put(IUserAccount.ADDTIME, nowTime);
    				// 管理员
    				values.put(IUserAccount.ADMINUSER, "admin");
    				BeanObject applyBean = new BeanObject(ModelNames.USERACCOUNT, values);
    				new CreateService().createBean(applyBean, new CreateService.Listener() {
    					@Override
    					public void onSuccess(String id) {
    						Info info = new Info();
    						info.show(new InfoConfig("恭喜", "添加申请成功！"));
    						MemberApplication.State state = new MemberApplication.State();
    						state.execute();
    						AdminLog.createAdminLog("添加充值与体现申请;");
    					}
    				});
    				clear();
            	} else {
            		clear();
                    MemberApplication.State state = new MemberApplication.State();
                    state.execute();
            		AdminLog.createAdminLog("确认充值与体现申请;");
            	}
            }
		});
		
		btnCancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	clear();
            }
		});
		
		buttonPanel.add(btnOK);
		buttonPanel.add(btnCancel);
		contentPanel.createPanel(null, null, buttonPanel);
		contentPanel.setBorders(true);
		return contentPanel;
	}

	/**
	 * 初始化界面时，将数据显示
	 * @param object 
	 */
//	public void setData( BeanObject bean ) {
//		this.object = bean;
//		isNew = (object != null) ? false : true;
//	}
	
	public void refresh() {
	    BeanObject object = getCurState().getUser();
		if (object != null) {
			new ReadService().getBean(ModelNames.USER, object.getString(IUserAccount.USER), new ReadService.Listener() {
				public synchronized void onSuccess(BeanObject result) {
					userSelector.setText(result.getString(IUser.NAME));  // 用户名
				}
			});
			accountField.setRawValue(object.getString(IUserAccount.AMOUNT));
			accountField.setEnabled(false); // 金额
			Map<String, Integer> paymentTypes = new HashMap<String, Integer>();
			paymentTypes.put("银行汇帐/转账", 1);
			paymentTypes.put("余额支付", 2);
			paymentTypes.put("邮局汇款", 3);
			paymentType.setSelectedIndex(paymentTypes.get(object.getString(IUserAccount.PAYMENT))); 
			paymentType.setEnabled(false);
			Integer in = object.get(IUserAccount.PROCESSTYPE);
			if( in.intValue() == IUserAccount.TYPE_SAVING ) {
				save.setChecked(true);
			} else {
				take.setChecked(true);
			}
			save.setEnabled(false);
			take.setEnabled(false);
			manager.setRawValue(object.getString(IUserAccount.ADMINNOTE));
			manager.setEnabled(false);
			user.setRawValue(object.getString(IUserAccount.USERNOTE));
			user.setEnabled(false);
			Boolean isConfirm = object.get(IUserAccount.PAID);
			if( isConfirm.booleanValue() ) {
				complete.setChecked(true);
			} else {
				notConfirm.setChecked(true);
			}
			complete.setEnabled(false);
			notConfirm.setEnabled(false);
			cancel.setEnabled(false);
		} else {
			accountField.setEnabled(true);
			complete.setEnabled(true);
			notConfirm.setEnabled(true);
			cancel.setEnabled(true);
			paymentType.setEnabled(true);
			save.setEnabled(true);
			take.setEnabled(true);
			user.setEnabled(true);
			manager.setEnabled(true);
		}
	}
	
	private void clear() {
		accountField.setRawValue("");
    	paymentType.setSelectedIndex(0);
    	manager.setRawValue("");
    	user.setRawValue("");
    	userSelector.setText("");
	}
}
