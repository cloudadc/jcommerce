package com.jcommerce.gwt.client.panels.member;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserAccount;
import com.jcommerce.gwt.client.panels.goods.NewBrand;
import com.jcommerce.gwt.client.panels.member.CheckBounds.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.BeanCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class MemberApplication extends ContentWidget {

	ColumnPanel contentPanel = new ColumnPanel();
	TextBox commentContent = new TextBox();
	Button btnFind = new Button(Resources.constants.GoodsList_search());
	ListBox appType = new ListBox();
	ListBox payType = new ListBox();
	ListBox payStatus = new ListBox();
	TextBox userName = new TextBox();
	String name = null;
	
	Button btnAct = new Button(Resources.constants.GoodsList_action_OK());
	Criteria criteria = new Criteria();
	int deleteSize = 1;
	PagingToolBar toolBar;

	public static class State extends PageState {
		public String getPageClassName() {
			return MemberApplication.class.getName();
		}

		public String getMenuDisplayName() {
			return "充值与提现申请";
		}
	}

	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	public MemberApplication() {
		add(contentPanel);
		initJS(this);
	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "充值与提现申请";
	}

    public String getButtonText() {
        return "添加申请";
    }
    
    protected void buttonClicked() {
        AddApply.State state = new AddApply.State();
        state.execute();
    }
    
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		BasePagingLoader loader = new PagingListService().getLoader(
				ModelNames.USERACCOUNT, criteria);
		loader.load(0, 10);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		ColumnConfig user = new ColumnConfig(IUserAccount.USER, "会员名称", 90);
		columns.add(user);
		columns.add(new ColumnConfig(IUserAccount.ADDTIME, "操作日期", 150));
		ColumnConfig type = new ColumnConfig(IUserAccount.PROCESSTYPE, "类型", 60);
		columns.add(type);
		columns.add(new ColumnConfig(IUserAccount.AMOUNT, "金额", 100));
		columns.add(new ColumnConfig(IUserAccount.PAYMENT, "支付方式", 140));
		ColumnConfig money = new ColumnConfig(IUserAccount.PAID, "到款状态", 90);
		columns.add(money);
		columns.add(new ColumnConfig(IUserAccount.ADMINUSER, "操作员", 90));

		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
				.GoodsList_action(), 120);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);

		user.setRenderer(new BeanCellRenderer(ModelNames.USER, IUser.NAME, grid));
		OperateActionCellRenderer operate = new OperateActionCellRenderer(grid, "提现", "充值");
		type.setRenderer(operate);
		OperateActionCellRenderer moneyStatus = new OperateActionCellRenderer(grid, "已完成", "未确认");
		money.setRenderer(moneyStatus);
		
		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		
		act.setImage("icon_trash.gif");
		act.setAction("deleteApply($id)");
		act.setTooltip("删除");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_edit.gif");
		act.setAction("confirmApply($id)");
		render.addAction(act);
		actcol.setRenderer(render);
		
		appType.addItem("类型", "---");
		appType.addItem("充值", ""+IUserAccount.TYPE_SAVING);
		appType.addItem("提现", ""+IUserAccount.TYPE_DRAWING);
		
		payType.addItem("支付方式", "---");
		payType.addItem("余额支付", ""+IUserAccount.PAY_BALANCE);
		payType.addItem("银行汇款转账", ""+IUserAccount.PAY_BANK_REMIT);
		payType.addItem("邮局汇款", ""+IUserAccount.PAY_POST_REMIT);
		
		payStatus.addItem("到款状态", "---");
		payStatus.addItem("未确认", ""+IUserAccount.PS_UNCOMFIRM);
		payStatus.addItem("已完成", ""+IUserAccount.PS_COMPLETE);
		payStatus.addItem("取消", ""+IUserAccount.PS_CANCEL);
		
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("会员名称"));
		header.add(userName);
		header.add(appType);
		header.add(payType);
		header.add(payStatus);
		header.add(btnFind);
		add(header);

		btnFind.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				search(); // 匹配条件为会员名称。condition可以限制搜索范围。
			}
		});

		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(800, 350);
		panel.setBottomComponent(toolBar);
		panel.setButtonAlign(HorizontalAlignment.CENTER);

		add(panel);
	}

	private void search() {
		criteria.removeAll();
		// 类型
		if (appType.getSelectedIndex() > 0) {
			String value = appType.getItemText(appType.getSelectedIndex()); // 0表示充值，1表示提现
			Condition cond = new Condition();
			cond.setField(IUserAccount.PROCESSTYPE);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(value);
			criteria.addCondition(cond);
		} else if (payType.getSelectedIndex() > 0) { // 支付方式
			String paymentType = payType.getItemText(payType.getSelectedIndex()); 
			Condition cond = new Condition();
			cond.setField(IUserAccount.PAYMENT);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(paymentType);
			criteria.addCondition(cond);
		} else if (payStatus.getSelectedIndex() > 0) {
			boolean confirm = false;
			if(payStatus.getSelectedIndex() > 1) {
				confirm = true;
			} else {
				confirm = false;
			}
			Condition cond = new Condition();
			cond.setField(IUserAccount.PAID);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(String.valueOf(confirm));
			criteria.addCondition(cond);
		} else {
			System.out.println("Input nothing!");
		}
		
		final String id = userName.getText().trim(); // 用户名ID
		if( id != null && !id.equals("")) {
			new ListService().listBeans(ModelNames.USER, new ListService.Listener() {
				@Override
				public void onSuccess(List<BeanObject> beans) {
					for( BeanObject bean : beans ) {
						if( bean.getString(IUser.NAME).equals(id) ) {
							name = bean.getString(IUser.ID);
							break;
						}
					}
					
				}
			});
			Condition cond = new Condition();
			cond.setField(IUserAccount.USER);
			cond.setOperator(Condition.LIKE);
			cond.setValue(name);
			criteria.addCondition(cond);
		}
		toolBar.refresh();
	}

	private native void initJS(MemberApplication me) /*-{
	   $wnd.deleteApply = function (id) {
	       me.@com.jcommerce.gwt.client.panels.member.MemberApplication::deleteApplyAndRefrsh(Ljava/lang/String;)(id);
	   };
	   $wnd.confirmApply = function (id) {
	       me.@com.jcommerce.gwt.client.panels.member.MemberApplication::applyConfirm(Ljava/lang/String;)(id);
	   };
	   }-*/;

	private void deleteApplyAndRefrsh(final String id) {
		final Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		      public void handleEvent(MessageBoxEvent ce) {
		          Button btn = ce.getButtonClicked();
				if (btn.getText().equals("是")) {
					new DeleteService().deleteBean(ModelNames.USERACCOUNT, id,
							new DeleteService.Listener() {
								public void onSuccess(Boolean success) {
									refresh();
								}
							});
				}

			}
		};
		MessageBox.confirm("Confirm", "Are you sure you want to do that?",
				deleteListener);

	}

	private void applyConfirm(String id) {
		new ReadService().getBean(ModelNames.USERACCOUNT, id,
				new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
						// 判断是否确认，假如是返回具体信息，否则返回确认界面
						Boolean isConfirm = bean.get(IUserAccount.PAID);
						if( isConfirm.booleanValue() ) {
			                AddApply.State state = new AddApply.State();
			                state.setUser(bean);
			                state.execute();							
						} else {
							CheckBounds.State state = new CheckBounds.State();
                            state.setComment(bean);
                            state.execute();                            
						}
					}
				});
	}

	public void refresh() {
		toolBar.refresh();
	}

	class DeleteListener extends DeleteService.Listener {
		private boolean finished = false;

		public void onSuccess(Boolean sucess) {
			finished = true;
		}

		public void onFailure(Throwable caught) {
			finished = true;
		}

		boolean isFinished() {
			return finished;
		}
	}

	class UpdateListener extends UpdateService.Listener {
		private boolean finished = false;

		public void onSuccess(Boolean sucess) {
			finished = true;
		}

		public void onFailure(Throwable caught) {
			finished = true;
		}

		boolean isFinished() {
			return finished;
		}
	}

	/**
	 * @author monkey
	 */
	class OperateActionCellRenderer extends ActionCellRenderer{
		private String isTruthStr;
		private String isFailureStr;
		
		@SuppressWarnings("unchecked")
		public OperateActionCellRenderer(Grid grid, String isTruthStr, String isFailureStr) {
			super(grid);
			this.isTruthStr = isTruthStr;
			this.isFailureStr = isFailureStr;
		}
		
		public String render(BeanObject model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<BeanObject> store) {
			
			String booleanValue = (String) model.get(property).toString();

			StringBuffer sb = new StringBuffer();
			ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
			
			if(booleanValue.equals("1")){
				act.setText(isTruthStr);
			}else if(booleanValue.equals("0")){
				act.setText(isFailureStr);
			}else if(booleanValue.equals("true")) {
				act.setText(isTruthStr);
			}else if(booleanValue.equals("false")) {
				act.setText(isFailureStr);
			}
			
			if (act.getText() != null && act.getText().trim().length() > 0) {
				sb.append(act.getText());
			}
	        return sb.toString();
		}
	}
	
}
