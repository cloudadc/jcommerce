package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.RadioCellRenderer;

public class OrderPayPanel extends ContentWidget{
	
	public static interface Constants {
		public String OrderPay_title();
		public String OrderPay_ok();
		public String OrderPay_cancel();
		public String OrderPay_next();
		public String OrderPay_pre();
		public String OrderPay_paymentName();
		public String OrderPay_paymentDesc();
		public String OrderPay_paymentFee();
		public String OrderPay_selectPay();
	}
	
	private static OrderPayPanel instance = null;

	protected State getCurState() {
		return (State)curState;
	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		if(getCurState().getIsEdit())
			return Resources.constants.OrderPay_title();
		else
			return Resources.constants.OrderUser_title();
	}
	
	public OrderPayPanel() {
	    curState = new State();
	}
	
	public static OrderPayPanel getInstance(){
		if (instance == null) {
			instance = new OrderPayPanel();
		}
		return instance;
	}

	public static class State extends PageState {

		public static final String PKID = "pkId";
		public static final String USERID = "userId";
		public static final String ISEDIT = "isEdit";
		
		public void setId(String id){
			setValue(PKID, id);
		}
		
		public String getPkId(){
			return (String)getValue(PKID);
		}
		
		public void setUserId(String id){
			setValue(USERID, id);
		}
		
		public String getUserId(){
			return (String)getValue(USERID);
		}
		
		public void setIsEdit(boolean isEdit){
			setValue(ISEDIT, String.valueOf(isEdit));
		}
		
		public boolean getIsEdit(){
			return Boolean.valueOf((String)getValue(ISEDIT)).booleanValue();
		}
		
		@Override
		public String getPageClassName() {
			return OrderPayPanel.class.getName();
		}
	}
	
	PagingToolBar toolBar;
	RadioGroup rgPay = new RadioGroup();
	ColumnConfig select;

	Button btnNext;
	Button btnOk;
	Button btnPre;
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		//Criteria c = new Criteria();
		//c.addCondition(new Condition(IPayment.ENABLED, Condition.EQUALS, "true"));
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.PAYMENT);
    	final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);			
    	
    	store.addStoreListener(new StoreListener<BeanObject>() {
	    	public void storeDataChanged(StoreEvent<BeanObject> se) {
				List<BeanObject> storeData = (List<BeanObject>)se.getStore().getModels();
				for (BeanObject object : storeData) {
					RadioCellRenderer rcr = new RadioCellRenderer(rgPay);
					select.setRenderer(rcr);
				}				
	    	}
	    });
    	
    	toolBar = new PagingToolBar(50);
    	toolBar.bind(loader);

    	List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
      
    	select = new ColumnConfig(IPayment.ID, "", 50);
		columns.add(select);
    	ColumnConfig colPayName = new ColumnConfig(IPayment.NAME, Resources.constants.OrderPay_paymentName(), 100);
		columns.add(colPayName);
		ColumnConfig colPayDesc = new ColumnConfig(IPayment.DESCRIPTION, Resources.constants.OrderPay_paymentDesc(), 100);
		columns.add(colPayDesc);
		ColumnConfig colPayFee = new ColumnConfig(IPayment.FEE, Resources.constants.OrderPay_paymentFee(), 100);
		columns.add(colPayFee);
    	
		ColumnModel cm = new ColumnModel(columns);
		Grid<BeanObject> grid = new Grid<BeanObject>(store, cm);
    	grid.setLoadMask(true);
     	grid.setBorders(true);
     	grid.setAutoExpandColumn(IPayment.DESCRIPTION);
      
     	ContentPanel panel = new ContentPanel();
     	panel.setFrame(true);
     	panel.setCollapsible(true);
     	panel.setAnimCollapse(false);
     	panel.setLayout(new FitLayout());
     	panel.add(grid);
     	panel.setHeight(350);
     	//panel.setBottomComponent(toolBar);     
      
     	panel.setButtonAlign(HorizontalAlignment.CENTER);
     	btnPre = new Button(Resources.constants.OrderPay_pre(), new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				OrderShippingPanel.State newState = new OrderShippingPanel.State();
				newState.setUserId(getCurState().getUserId());
				newState.setIsEdit(false);
				newState.setId(getCurState().getPkId());
				newState.execute();
			}
		});
     	panel.addButton(btnPre);

     	btnNext = new Button(Resources.constants.OrderPay_next(), new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if(rgPay.getValue() == null) {
					Window.alert(Resources.constants.OrderPay_selectPay());
				}
				else {
					updateOrder();
					new WaitService(new WaitService.Job() {
						public boolean isReady() {
							if(!updateIsReady) {
								return false;
							}
							else {
								return true;
							}
						}
						public void run() {
							OrderFeePanel.State newState = new OrderFeePanel.State();
							newState.setUserId(getCurState().getUserId());
							newState.setIsEdit(false);
							newState.setId(getCurState().getPkId());
							newState.execute();
						}
					});	
				}
			}
		});
		panel.addButton(btnNext);
		
     	btnOk = new Button(Resources.constants.OrderPay_ok(), new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if(rgPay.getValue() == null) {
					Window.alert(Resources.constants.OrderPay_selectPay());
				}
				else {
					updateOrder();
					new WaitService(new WaitService.Job() {
						public boolean isReady() {
							if(!updateIsReady) {
								return false;
							}
							else {
								return true;
							}
						}
						public void run() {
							if(!isChange) {
								OrderDetailPanel.State newState = new OrderDetailPanel.State();
								newState.setId(getCurState().getPkId());
								newState.execute();
							}
							else if(isIncrease) {
								Success.State newState = new Success.State();
						    	newState.setMessage(Resources.constants.OrderDetail_orderAmountIncrease());
						    	OrderDetailPanel.State choice1 = new OrderDetailPanel.State();
						    	choice1.setId(getCurState().getPkId());
						    	newState.addChoice(OrderDetailPanel.getInstance().getName(), choice1);
								newState.execute();
							}
							else {
								Success.State newState = new Success.State();
						    	newState.setMessage(Resources.constants.OrderDetail_orderAmountDecrease());
						    	OrderDetailPanel.State choice1 = new OrderDetailPanel.State();
						    	choice1.setId(getCurState().getPkId());
						    	newState.addChoice(OrderDetailPanel.getInstance().getName(), choice1);
								newState.execute();
							}
						}
					});	
				}
			}
		});
     	panel.addButton(btnOk);
		
		panel.addButton(new Button(Resources.constants.OrderPay_cancel(),new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				cancel();
			}
		}));
     
     	add(panel);       
	}
	
	

	@Override
	public void refresh() {
		toolBar.refresh();

		btnOk.setVisible(false);
     	btnPre.setVisible(false);
     	btnNext.setVisible(false);
		if(getCurState().getIsEdit()) {
			btnOk.setVisible(true);
		}
		else {
			btnPre.setVisible(true);
			btnNext.setVisible(true);
		}
		isChange = false;
	}
	
	boolean updateIsReady;
	boolean isChange = false;
	boolean isIncrease = false;
	private void updateOrder() {
		updateIsReady = false;
		final String payId = rgPay.getValue().getValueAttribute();
		final String orderId = getCurState().getPkId();
		
		new ReadService().getBean(ModelNames.ORDER, orderId, new ReadService.Listener() {
			public void onSuccess(final BeanObject bean) {
				new RemoteService().getSpecialService().getOrderFee(getCurState().getPkId(), null, payId, new AsyncCallback<Map<String, String>>() {

					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						Window.alert("ERROR: "+caught.getMessage());
					}


					public void onSuccess(Map<String, String> result) {
						
						if(getCurState().getIsEdit()) {
							if(bean.getInt(IOrderInfo.PAY_STATUS) == IOrderInfo.PS_PAYED && (Double)bean.get(IOrderInfo.ORDER_AMOUNT) != new Double(result.get("amount"))) {
								isChange = true;
								if((Double)bean.get(IOrderInfo.ORDER_AMOUNT) > new Double(result.get("amount")))
									isIncrease = false;
								else {
									isIncrease = true;
									bean.set(IOrderInfo.PAY_STATUS, IOrderInfo.PS_UNPAYED);
								}
							}
						}
						
						bean.set(IOrderInfo.ORDER_AMOUNT, result.get("amount"));
						bean.set(IOrderInfo.PAY_ID, payId);
						bean.set(IOrderInfo.PAY_FEE, result.get("payFee"));
						bean.set(IOrderInfo.PAY_NAME, result.get("payName"));
						new UpdateService().updateBean(orderId, bean, new UpdateService.Listener() {
							public void onSuccess(Boolean success) {
								updateIsReady = true;
							}							
						});
					}						
				});
			}
		});
	}
	
	private void cancel() {
		if(getCurState().getIsEdit()) {
			OrderDetailPanel.State newState = new OrderDetailPanel.State();
			newState.setId(getCurState().getPkId());
			newState.execute();
		}
		else {
			//删除订单及订单商品
			new RemoteService().getSpecialService().deleteOrder(getCurState().getPkId(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
				}

				public void onSuccess(Boolean result) {
				}
			});
			OrderListPanel.State newState = new OrderListPanel.State();
			newState.execute();			
		}
	}
}
