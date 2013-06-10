package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.RadioCellRenderer;

public class OrderShippingPanel extends ContentWidget{
	
	public static interface Constants {
		public String OrderShipping_title();
		public String OrderShipping_ok();
		public String OrderShipping_cancel();
		public String OrderShipping_next();
		public String OrderShipping_pre();
		public String OrderShipping_shippingName();
		public String OrderShipping_shippingDesc();
		public String OrderShipping_shippingFee();
		public String OrderShipping_freeMoney();
		public String OrderShipping_insurance();
		public String OrderShipping_selectShipping();
		
	}
	
	private static OrderShippingPanel instance = null;

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
			return Resources.constants.OrderShipping_title();
		else
			return Resources.constants.OrderUser_title();
	}
	
	public OrderShippingPanel() {
	    curState = new State();
	}
	
	public static OrderShippingPanel getInstance(){
		if (instance == null) {
			instance = new OrderShippingPanel();
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
			return OrderShippingPanel.class.getName();
		}
	}
	
	BeanObject order;
	PagingToolBar toolBar;
	RadioGroup rgShipping = new RadioGroup();
	ColumnConfig select;
	ColumnConfig colShippingFee;
	ColumnConfig colFreeMoney;
	ColumnModel cm;
	Grid<BeanObject> grid;
	

	Button btnNext;
	Button btnOk;
	Button btnPre;

//	List<Double> freeMoneyList = new ArrayList<Double>();
//	List<Double> shippingFeeList = new ArrayList<Double>();
//	int isReady;
//	int size;
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		Criteria c = new Criteria();
		c.addCondition(new Condition(IShipping.ENABLED, Condition.EQUALS, "true"));		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.SHIPPING, c);
    	final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);			
    	
    	store.addStoreListener(new StoreListener<BeanObject>() {
	    	public void storeDataChanged(StoreEvent<BeanObject> se) {
				List<BeanObject> storeData = (List<BeanObject>)se.getStore().getModels();
//				freeMoneyList.clear();
//				shippingFeeList.clear();
//				isReady = 0;
//				size = storeData.size() * 2;
				for (BeanObject object : storeData) {
					RadioCellRenderer rcr = new RadioCellRenderer(rgShipping);
					select.setRenderer(rcr);

//					RemoteService.getSpecialService().getShippingConfig((String) object.get(IShipping.ID), new AsyncCallback<Map<String, String>>(){
//						public void onFailure(Throwable caught) {
//							caught.printStackTrace();
//							Window.alert("ERROR: "+caught.getMessage());
//						}
//						public void onSuccess(Map<String, String> result) {
//							double freeMoney = Double.parseDouble(result.get("freeMoney"));
//							freeMoneyList.add(freeMoney);
//							isReady++;
//						}
//					});
//					
//					RemoteService.getSpecialService().getOrderFee(getCurState().getPkId(), (String) object.get(IShipping.ID), null, new AsyncCallback<Map<String, Object>>(){
//						public void onFailure(Throwable caught) {
//							caught.printStackTrace();
//							Window.alert("ERROR: "+caught.getMessage());
//						}
//						@Override
//						public void onSuccess(Map<String, Object> result) {
//							double shippingFee = (Double)result.get("shippingFee");
//							shippingFeeList.add(shippingFee);
//							isReady++;						
//						}
//					});
				}
//				new WaitService(new Job() {
//
//					@Override
//					public boolean isReady() {
//						if(isReady < size)
//							return false;
//						else
//							return true;
//					}
//
//					@Override
//					public void run() {
//						isReady = 0;
//						ShippingFeeCellRenderer money = new ShippingFeeCellRenderer("freeMoney");
//						ShippingFeeCellRenderer fee = new ShippingFeeCellRenderer("shippingFee");
//						money.setFreeMoney(freeMoneyList);
//						colFreeMoney.setRenderer(money);
//						fee.setShippingFee(shippingFeeList);
//						colShippingFee.setRenderer(fee);	
//						grid.reconfigure(store, cm);
//					}
//					
//				});
	    	}
	    });
    	
    	toolBar = new PagingToolBar(50);
    	toolBar.bind(loader);

    	List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
      
    	select = new ColumnConfig(IShipping.ID, "", 50);
		columns.add(select);
    	ColumnConfig colShippingName = new ColumnConfig(IShipping.NAME, Resources.constants.OrderShipping_shippingName(), 100);
		columns.add(colShippingName);
		ColumnConfig colShippingDesc = new ColumnConfig(IShipping.DESCRIPTION, Resources.constants.OrderShipping_shippingDesc(), 100);
		columns.add(colShippingDesc);
		colShippingFee = new ColumnConfig("shippingFee", Resources.constants.OrderShipping_shippingFee(), 100);
		columns.add(colShippingFee);
		colFreeMoney = new ColumnConfig("freeMoney", Resources.constants.OrderShipping_freeMoney(), 100);
		columns.add(colFreeMoney);
		ColumnConfig colInsuranceFee = new ColumnConfig(IShipping.INSURE, Resources.constants.OrderShipping_insurance(), 100);
		columns.add(colInsuranceFee);
    	
		cm = new ColumnModel(columns);
		grid = new Grid<BeanObject>(store, cm);
    	grid.setLoadMask(true);
     	grid.setBorders(true);
     	grid.setAutoExpandColumn(IShipping.DESCRIPTION);
      
     	ContentPanel panel = new ContentPanel();
     	panel.setFrame(true);
     	panel.setCollapsible(true);
     	panel.setAnimCollapse(false);
     	panel.setLayout(new FitLayout());
     	panel.add(grid);
     	panel.setHeight(350);
     	//panel.setBottomComponent(toolBar);     
      
     	panel.setButtonAlign(HorizontalAlignment.CENTER);
     	btnPre = new Button(Resources.constants.OrderShipping_pre());
     	btnPre.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				OrderGoodsPanel.State newState = new OrderGoodsPanel.State();
				newState.setIsEdit(false);
				newState.setId(getCurState().getPkId());
				newState.setUserId(getCurState().getUserId());
				newState.execute();
			}
     	});
     	panel.addButton(btnPre);

     	btnNext = new Button(Resources.constants.OrderShipping_next());
     	panel.addButton(btnNext);
		btnNext.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					if(rgShipping.getValue() == null) {
						Window.alert(Resources.constants.OrderShipping_selectShipping());
					}
					else {
						updateOrder();
						
						OrderPayPanel.State newState = new OrderPayPanel.State();
						newState.setId(getCurState().getPkId());
						newState.setUserId(getCurState().getUserId());
						newState.setIsEdit(false);
						newState.execute();
					}
				}
			});
		
		btnOk = new Button(Resources.constants.OrderShipping_ok());
		panel.addButton(btnOk);
		btnOk.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if(rgShipping.getValue() == null) {
					Window.alert(Resources.constants.OrderShipping_selectShipping());
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
		
		panel.addButton(new Button(Resources.constants.OrderShipping_cancel(), new SelectionListener<ButtonEvent>() {
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
		final String shippingId = rgShipping.getValue().getValueAttribute();
		final String orderId = getCurState().getPkId();
		
		new ReadService().getBean(ModelNames.ORDER, orderId, new ReadService.Listener() {
			public void onSuccess(final BeanObject bean) {
				new RemoteService().getSpecialService().getOrderFee(getCurState().getPkId(), shippingId, null, new AsyncCallback<Map<String, String>>() {

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
						bean.set(IOrderInfo.SHIPPING_ID, shippingId);
						bean.set(IOrderInfo.SHIPPING_FEE, result.get("shippingFee"));
						bean.set(IOrderInfo.SHIPPING_NAME, result.get("shippingName"));
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
