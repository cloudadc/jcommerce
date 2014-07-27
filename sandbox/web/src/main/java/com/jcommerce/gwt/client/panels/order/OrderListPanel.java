package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.model.IOrderAction;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.util.MyRpcProxy;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ConsigneeCellRenderer;
import com.jcommerce.gwt.client.widgets.MoneyFormatCellRenderer;
import com.jcommerce.gwt.client.widgets.OrderStateCellRenderer;
import com.jcommerce.gwt.client.widgets.TimeCellRenderer;

public class OrderListPanel  extends ContentWidget{
	
	public static interface Constants {
		String OrderStatus_OS_CANCELED();
		String OrderStatus_OS_CONFIRMED();
		String OrderStatus_OS_INVALID();
		String OrderStatus_OS_RETURNED();
		String OrderStatus_OS_UNCONFIRMED();
		String OrderStatus_SS_RECEIVED();
		String OrderStatus_SS_PREPARING();
		String OrderStatus_SS_SHIPPED();
		String OrderStatus_SS_UNSHIPPED();
		String OrderStatus_PS_PAYED();
		String OrderStatus_PS_PAYING();
		String OrderStatus_PS_UNPAYED();
		String OrderStatus_select();
		String OrderStatus_complete();
		String OrderList_deleteSuccessfully();
		String OrderList_deleteFailure();
		String OrderList_confirm();
		String OrderList_remove();
    }

	public interface Message extends Messages {
		String OrderList_operateSuccessfully(String orderSn);
		String OrderList_operateFail(String orderSn);
	}

    private static final int OS_UNCONFIRMED = 1;
    private static final int PS_UNPAYED = 2;
    private static final int SS_PREPARING = 3;
    private static final int COMPLETE = 4;
    private static final int PS_PAYING = 5;
    private static final int OS_CANCELED = 6;
    private static final int OS_INVALID = 7;
    private static final int OS_RETURNED = 8;
	
	private static OrderListPanel instance = null;

	protected State getCurState() {
		return (State)curState;
	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return Resources.constants.OrderList_title();
	}
	
	public Button getShortCutButton() {
      Button buttonSearchOrder = new Button(Resources.constants.SearchOrder_title());
      buttonSearchOrder.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonSearchOrder;
    }
    public void onButtonListClicked() {
		SearchOrderPanel.State newState = new SearchOrderPanel.State();
		newState.execute();
    }
	
	public static OrderListPanel getInstance(){
		if (instance == null) {
			instance = new OrderListPanel();
		}
		return instance;
	}
	
	private OrderListPanel(){
	    curState = new State();
		initJS(this);
	}
	
	public static class State extends PageState {
		public static final String PK_ID = "pkId";
		public static final String CONDITION = "condition";
		@Override
		public String getPageClassName() {
			return OrderListPanel.class.getName();
		}
		@Override
		public String getMenuDisplayName() {
			return Resources.constants.OrderList_title();
		}
		public void setId(String gtid) {
			setValue(PK_ID, gtid);
		}
		public String getPkId() {
			return (String)getValue(PK_ID);
		}
		public void setCondition(List<String> con) {
			setValue(CONDITION, con);
		}
		public List<String> getCondition() {
			return (List<String>)getValue(CONDITION);
		}
	}

	Criteria criteria = new Criteria();
	PagingToolBar toolBar;
	BasePagingLoader loader;
	Grid<BeanObject> grid;
	ColumnModel cm;
	
	ColumnConfig addTimeCol; 
	ColumnConfig consigneeCol;
	ColumnConfig totalAmountCol;	
	ColumnConfig shouldPayAmountCol;	
	ColumnConfig orderStateCol;
	
	Button btnFind = new Button(Resources.constants.GoodsList_search());
	TextField<String> orderSn = new TextField<String>();
	TextField<String> consignee = new TextField<String>();
	ListBox state_list = new ListBox();
	
	Button btnConfirm = new Button(Resources.constants.OrderList_confirm());
	Button btnInvalid = new Button(Resources.constants.OrderList_invalid());
	Button btnCancel = new Button(Resources.constants.OrderList_cancel());
	Button btnRemove = new Button(Resources.constants.OrderList_remove());
    Button btnPrint = new Button("Print");
	SelectionListener<ButtonEvent> selectionListener = new SelectionListener<ButtonEvent>() {
	    public void componentSelected(ButtonEvent sender) {
	    	operate(sender);
	    }
	};
	
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);		
		
		loader = new PagingListService().getLoader(ModelNames.ORDER, criteria);
		//loader.load(0, 10);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		smRowSelection.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				orders = new ArrayList<String>();
				List<BeanObject> selection = se.getSelection();
				if(selection.size() > 0) {
					btnConfirm.setEnabled(true);
					btnInvalid.setEnabled(true);
					btnCancel.setEnabled(true);
					btnRemove.setEnabled(true);
                    btnPrint.setEnabled(true);
					for(BeanObject bean : selection) {
						orders.add((String) bean.get(IOrderInfo.ORDER_ID));
					}
				}
				else {
					btnConfirm.setEnabled(false);
					btnInvalid.setEnabled(false);
					btnCancel.setEnabled(false);
					btnRemove.setEnabled(false);
                    btnPrint.setEnabled(false);
				}
			}
		});
		columns.add(smRowSelection.getColumn());

		ColumnConfig col = new ColumnConfig(IOrderInfo.ORDER_ID, Resources.constants.OrderList_orderSN(), 150);
		columns.add(col);
		
		addTimeCol = new ColumnConfig(IOrderInfo.ADD_TIME, Resources.constants.OrderList_addTime(), 200);
		TimeCellRenderer tcr =new TimeCellRenderer();
		tcr.setFormat("MM-dd HH:mm");
		addTimeCol.setRenderer(tcr);
		columns.add(addTimeCol);
		
		consigneeCol = new ColumnConfig(IOrderInfo.CONSIGNEE, Resources.constants.OrderList_consignee(), 200);
		consigneeCol.setRenderer(new ConsigneeCellRenderer());
		columns.add(consigneeCol);
		
//		totalAmountCol = new ColumnConfig("totalAmount", Resources.constants.OrderList_totalAmount(), 100);
//		totalAmountCol.setRenderer(new MoneyFormatCellRenderer());
//		columns.add(totalAmountCol);
//		
		shouldPayAmountCol = new ColumnConfig(IOrderInfo.ORDER_AMOUNT, Resources.constants.OrderList_shouldPay(), 100);
		shouldPayAmountCol.setRenderer(new MoneyFormatCellRenderer());
		columns.add(shouldPayAmountCol);
		
		orderStateCol = new ColumnConfig(IOrderInfo.ORDER_STATUS, Resources.constants.OrderList_state(), 100);
		orderStateCol.setRenderer(new OrderStateCellRenderer(null));
		columns.add(orderStateCol);
		
		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.OrderList_action(), 100);
		columns.add(actcol);

		cm = new ColumnModel(columns);
		
		grid = new Grid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		grid.setAutoExpandColumn(IOrderInfo.ORDER_STATUS);
	
		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage(GWT.getModuleBaseURL()+"icon_edit.gif");
		act.setAction("viewOrder($id)");
		act.setTooltip(Resources.constants.edit());
		render.addAction(act);

		actcol.setRenderer(render);
		
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label(Resources.constants.OrderList_orderSN()));
		header.add(orderSn);
		header.add(new Label(Resources.constants.OrderList_consignee()));
		header.add(consignee);
		header.add(new Label(Resources.constants.OrderList_state()));
		header.add(state_list);
		state_list.addItem(Resources.constants.OrderStatus_select(),"0");
		state_list.addItem(Resources.constants.OrderStatus_OS_UNCONFIRMED(), ""+OS_UNCONFIRMED);
		state_list.addItem(Resources.constants.OrderStatus_PS_UNPAYED(), ""+PS_UNPAYED);
		state_list.addItem(Resources.constants.OrderStatus_SS_PREPARING(), ""+SS_PREPARING);
		state_list.addItem(Resources.constants.OrderStatus_complete(), ""+COMPLETE);
		state_list.addItem(Resources.constants.OrderStatus_PS_PAYING(), ""+PS_PAYING);
		state_list.addItem(Resources.constants.OrderStatus_OS_CANCELED(), ""+OS_CANCELED);
		state_list.addItem(Resources.constants.OrderStatus_OS_INVALID(), ""+OS_INVALID);
		state_list.addItem(Resources.constants.OrderStatus_OS_RETURNED(), ""+OS_RETURNED);
		
		header.add(btnFind);
		btnFind.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				search(state_list.getSelectedIndex());
			}
		});
		
		Label unConfirmedLabel = new Label(Resources.constants.OrderStatus_OS_UNCONFIRMED());
		unConfirmedLabel.setStyleName("SearchOrderLabel");
	
		unConfirmedLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				search(OS_UNCONFIRMED);
			}
		});
		header.add(unConfirmedLabel);
		
		Label unPayedLabel = new Label(Resources.constants.OrderStatus_PS_UNPAYED());
		unPayedLabel.setStyleName("SearchOrderLabel");
		unPayedLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				search(PS_UNPAYED);
			}
		});
		header.add(unPayedLabel);
		
		Label preparingLabel = new Label(Resources.constants.OrderStatus_SS_PREPARING());
		preparingLabel.setStyleName("SearchOrderLabel");
		preparingLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				search(SS_PREPARING);
			}
		});
		header.add(preparingLabel);
		
		add(header);
		
		final ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		//        panel.setHeading("Paging Grid");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setHeight(500);
		panel.setWidth("100%");
		panel.setBottomComponent(toolBar);

		panel.setButtonAlign(HorizontalAlignment.LEFT);
		panel.addButton(btnConfirm);
		btnConfirm.addSelectionListener(selectionListener);
		btnConfirm.setEnabled(false);
		panel.addButton(btnInvalid);
		btnInvalid.addSelectionListener(selectionListener);
		btnInvalid.setEnabled(false);
		panel.addButton(btnCancel);
		btnCancel.addSelectionListener(selectionListener);
		btnCancel.setEnabled(false);
		panel.addButton(btnRemove);	
		btnRemove.addSelectionListener(selectionListener);	
		btnRemove.setEnabled(false);
//        panel.addButton(btnPrint); 
        btnPrint.addSelectionListener(selectionListener);  
        btnPrint.setEnabled(false);
		
		add(panel);
		
        Window.addResizeHandler(new ResizeHandler() {
            public void onResize(ResizeEvent event) {
                int w = event.getWidth() - 300;
                panel.setWidth(w + "px");
            }
        });
	}
	
	protected void search(int selected) {
		criteria.removeAll();
		
		//搜索已完成的订单
		if(selected == COMPLETE) {
			criteria.addCondition(new Condition(IOrderInfo.ORDER_STATUS, Condition.EQUALS, ((Integer)IOrderInfo.OS_CONFIRMED).toString()));
			criteria.addCondition(new Condition(IOrderInfo.PAY_STATUS, Condition.EQUALS, ((Integer)IOrderInfo.PS_PAYED).toString()));
			criteria.addCondition(new Condition(IOrderInfo.SHIPPING_STATUS, Condition.GREATERTHAN, ((Integer)IOrderInfo.SS_SHIPPED).toString()));
			criteria.addCondition(new Condition(IOrderInfo.SHIPPING_STATUS, Condition.LESSTHAN, ((Integer)IOrderInfo.SS_RECEIVED).toString()));
		}
		
		else {
			Condition condition = new Condition();
			condition.setOperator(Condition.EQUALS);		
			switch(selected) {
				case OS_UNCONFIRMED:
					condition.setField(IOrderInfo.ORDER_STATUS);
					condition.setValue(""+IOrderInfo.OS_UNCONFIRMED);
					break;
				case PS_UNPAYED:
					condition.setField(IOrderInfo.PAY_STATUS);
					condition.setValue(""+IOrderInfo.PS_UNPAYED);
					break;
				case SS_PREPARING:
					condition.setField(IOrderInfo.SHIPPING_STATUS);
					condition.setValue(""+IOrderInfo.SS_PREPARING);
					break;
				case PS_PAYING:
					condition.setField(IOrderInfo.PAY_STATUS);
					condition.setValue(""+IOrderInfo.PS_PAYING);
					break;
				case OS_CANCELED:
					condition.setField(IOrderInfo.ORDER_STATUS);
					condition.setValue(""+IOrderInfo.OS_CANCELED);
					break;
				case OS_INVALID:
					condition.setField(IOrderInfo.ORDER_STATUS);
					condition.setValue(""+IOrderInfo.OS_INVALID);
					break;
				case OS_RETURNED:
					condition.setField(IOrderInfo.ORDER_STATUS);
					condition.setValue(""+IOrderInfo.OS_RETURNED);
					break;
			}
			if(selected != 0)
				criteria.addCondition(condition);
		}
		MyRpcProxy proxy = (MyRpcProxy)loader.getProxy();
		proxy.setCriteria(criteria);
		toolBar.refresh();
	}

	@Override
	public void refresh() {
		refreshOrderList();
		toolBar.refresh();
		//获得管理员用户名
		RemoteService.getSpecialService().getAdminUserInfo(new AsyncCallback<Map<String,String>>(){
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			public void onSuccess(Map<String, String> result) {
				adminUserName = result.get(IAdminUser.NAME);
			}
		});
		btnConfirm.setEnabled(false);
		btnInvalid.setEnabled(false);
		btnCancel.setEnabled(false);
		btnRemove.setEnabled(false);
		btnPrint.setEnabled(false);
	}
	
	private void refreshOrderList() {
		
		Criteria criteria = new Criteria(); 
		String userId = getCurState().getPkId();
		if(userId != null) {
			criteria.addCondition(new Condition(IOrderInfo.USER_ID, Condition.EQUALS, userId));
		}
		
		List<String> con = getCurState().getCondition();
		if(con != null) {
			for(String keyValue : con) {
				int position = keyValue.indexOf(":");
				if(position != -1) {
					String key = keyValue.substring(0,position);
					String value = keyValue.substring(position + 1, keyValue.length());
					if(!key.equals("fromDate") && !key.equals("toDate")) {
						criteria.addCondition(new Condition(key, Condition.EQUALS, value));
					}
					else if(key.equals("fromDate")){
						criteria.addCondition(new Condition(IOrderInfo.ADD_TIME, Condition.GREATERTHAN, value));
					}
					else {
						criteria.addCondition(new Condition(IOrderInfo.ADD_TIME, Condition.LESSTHAN, value));
					}
				}
			}
		}		
		
		MyRpcProxy proxy = (MyRpcProxy)loader.getProxy();
		proxy.setCriteria(criteria);			
	}

	private void updateOrder(BeanObject order, UpdateService.Listener listener) {
		new UpdateService().updateBean(order.getString(IOrderInfo.ID), order,
				listener);
	}
	
	List<String> orders;//要操作的订单列表
	List<String> fail;//不能执行操作的订单
	List<String> success = new ArrayList<String>();
	//订单操作
	private void operate(ButtonEvent sender) {
		fail = new ArrayList<String>();
		success = new ArrayList<String>();
		operateReady = false;
		
		if(sender.getButton().equals(btnConfirm)) {
			for(String orderId : orders) {
				confirmOrder(orderId);
			}
		} 
		else if(sender.getButton().equals(btnCancel)) {
			for(String orderId : orders) {
				cancelOrder(orderId);
			}
		}
		else if(sender.getButton().equals(btnInvalid)) {
			for(String orderId : orders) {
				invalidOrder(orderId);
			}
		}
        else if(sender.getButton().equals(btnPrint)) {
            for(String orderId : orders) {
                printOrder(orderId);
            }
        }
		else {
			for(String orderId : orders) {
				removeOrder(orderId);
			}
		}
		new WaitService(new WaitService.Job() {
			public boolean isReady() {
				return operateReady;
			}
			public void run() {
				gotoSuccessPage();
			}
		});
	}
	
	private void gotoSuccessPage() {
		Success.State newState = new Success.State();
		if(fail.size() > 0) {
			String orderSns = "";
			for(String orderSn : fail) {
				orderSns = orderSns + orderSn + ",";
			}
			newState.setMessage(Resources.messages.OrderList_operateFail(orderSns));
		}
		else {
			String orderSns = "";
			for(String orderSn : success) {
				orderSns = orderSns + orderSn + ",";
			}
			newState.setMessage(Resources.messages.OrderList_operateSuccessfully(orderSns));
		}
    	OrderListPanel.State choice1 = new OrderListPanel.State();
    	newState.addChoice(OrderListPanel.getInstance().getName(), choice1);
		newState.execute();
	}

	boolean operateReady;

    private void printOrder(final String orderId) {
        Window.open("http://google.com", "_blank", null);
    }
	
	private void invalidOrder(final String orderId) {
		// 状态：已确认&&未付款&&未发货（或配货中） || 未确认
		new ReadService().getBean(ModelNames.ORDER, orderId, new ReadService.Listener() {
			public void onSuccess(BeanObject bean) {
				int orderStatus = ((Number)bean.get(IOrderInfo.ORDER_STATUS)).intValue();
				int payStatus = ((Number)bean.get(IOrderInfo.PAY_STATUS)).intValue();
				int shippingStatus = ((Number)bean.get(IOrderInfo.SHIPPING_STATUS)).intValue();
				if(orderStatus == IOrderInfo.OS_UNCONFIRMED ||
					(orderStatus == IOrderInfo.OS_CONFIRMED && payStatus == IOrderInfo.PS_UNPAYED && 
						(IOrderInfo.SS_UNSHIPPED == shippingStatus || IOrderInfo.SS_PREPARING == shippingStatus))) {

					bean.set(IOrderInfo.ORDER_STATUS, IOrderInfo.OS_INVALID);
					bean.set(IOrderInfo.PAY_STATUS, IOrderInfo.PS_UNPAYED);
					new UpdateService().updateBean(orderId, bean, null);
					success.add((String) bean.get(IOrderInfo.ORDER_SN));
				}
				else {
					fail.add((String) bean.get(IOrderInfo.ORDER_ID));
				}
				operateReady = true;
			}
		});
	}

	private void removeOrder(final String orderId) {
		//状态：无效 ||取消
		new ReadService().getBean(ModelNames.ORDER, orderId, new ReadService.Listener() {
			public void onSuccess(BeanObject bean) {
				int orderStatus = ((Number)bean.get(IOrderInfo.ORDER_STATUS)).intValue();
				if(orderStatus == IOrderInfo.OS_INVALID || orderStatus == IOrderInfo.OS_CANCELED) {
					new RemoteService().getSpecialService().deleteOrder(orderId, new AsyncCallback<Boolean>(){
						public void onFailure(Throwable caught) {
						}

						public void onSuccess(Boolean result) {
						}
					});
					success.add((String) bean.get(IOrderInfo.ORDER_SN));
				}
				else {
					fail.add((String) bean.get(IOrderInfo.ORDER_SN));
				}
				operateReady = true;
			}
		});
	}

	private void cancelOrder(final String orderId) {
		//状态：未确认 || 已确认&&未发货（或配货中）
		new ReadService().getBean(ModelNames.ORDER, orderId, new ReadService.Listener() {
			public void onSuccess(BeanObject bean) {
				int orderStatus = ((Number)bean.get(IOrderInfo.ORDER_STATUS)).intValue();
				int shippingStatus = ((Number)bean.get(IOrderInfo.SHIPPING_STATUS)).intValue();
				if(orderStatus == IOrderInfo.OS_UNCONFIRMED ||
					(orderStatus == IOrderInfo.OS_CONFIRMED && 
						(IOrderInfo.SS_UNSHIPPED == shippingStatus || IOrderInfo.SS_PREPARING == shippingStatus))) {
					/*标记订单为取消，未付款*/
					bean.set(IOrderInfo.ORDER_STATUS, IOrderInfo.OS_CANCELED);
					bean.set(IOrderInfo.PAY_STATUS, IOrderInfo.PS_UNPAYED);
					bean.set(IOrderInfo.PAY_TIME, 0);
					bean.set(IOrderInfo.ORDER_AMOUNT, bean.get(IOrderInfo.MONEY_PAID));
					bean.set(IOrderInfo.MONEY_PAID, 0);
					new UpdateService().updateBean(orderId, bean, null);
					success.add((String) bean.get(IOrderInfo.ORDER_SN));
				}
				else {
					fail.add((String) bean.get(IOrderInfo.ORDER_SN));
				}
				operateReady = true;
			}
		});
	}

	private void confirmOrder(final String orderId) {
		//状态：！确认
		new ReadService().getBean(ModelNames.ORDER, orderId, new ReadService.Listener() {
			public void onSuccess(BeanObject bean) {
				int orderStatus = ((Number)bean.get(IOrderInfo.ORDER_STATUS)).intValue();
				if(orderStatus != IOrderInfo.OS_CONFIRMED) {
					bean.set(IOrderInfo.ORDER_STATUS, IOrderInfo.OS_CONFIRMED);
					new UpdateService().updateBean(orderId, bean, null);
					addOrderAction(orderId, bean);
					success.add((String) bean.get(IOrderInfo.ORDER_SN));
				}
				else {
					fail.add((String) bean.get(IOrderInfo.ORDER_SN));
				}
				operateReady = true;
			}
		});
	}
	
	String adminUserName;
	protected void addOrderAction(String orderId, BeanObject orderInfo) {
		long logTime = new Date().getTime();
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(IOrderAction.ORDER, orderId);
		props.put(IOrderAction.ORDERSTATUS, orderInfo.get(IOrderInfo.ORDER_STATUS));
		props.put(IOrderAction.PAYSTATUS, orderInfo.get(IOrderInfo.PAY_STATUS));
		props.put(IOrderAction.SHIPPINGSTATUS, orderInfo.get(IOrderInfo.SHIPPING_STATUS));
		props.put(IOrderAction.LOGTIME, logTime);
		props.put(IOrderAction.ACTIONUSER, adminUserName);
		
		BeanObject form = new BeanObject(ModelNames.ORDERACTION, props);
		new CreateService().createBean(form, null);
	}

	private native void initJS(OrderListPanel me) /*-{
	   $wnd.viewOrder = function (id) {
	       me.@com.jcommerce.gwt.client.panels.order.OrderListPanel::viewOrder(Ljava/lang/String;)(id);
	   };
	   }-*/;

	private void viewOrder(String id){
		OrderDetailPanel.State newState = new OrderDetailPanel.State();
		newState.setId(id);
		newState.execute();
	}
	
}
