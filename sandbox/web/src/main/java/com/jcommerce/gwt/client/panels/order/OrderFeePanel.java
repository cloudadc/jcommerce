package com.jcommerce.gwt.client.panels.order;

import java.util.Date;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;

public class OrderFeePanel extends ContentWidget{
	
	public static interface Constants {
		public String orderFee_ok();
		public String orderFee_pre();
		public String orderFee_cancel();
		public String orderFee_goodsAmount();
		public String orderFee_orderAmount();
		public String orderFee_shippingFee();
		public String orderFee_payFee();
		public String orderFee_insurance();
		public String orderFee_moneyPaid();
		public String orderFee_userSurplus();
		public String orderFee_moneyShouldPay();
		public String orderFee_surplus();
		public String orderFee_title();
	}
	
	private static OrderFeePanel instance = null;

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
			return Resources.constants.orderFee_title();
		else
			return Resources.constants.OrderUser_title();
	}

	public OrderFeePanel() {
	    curState = new State();
	}
	
	public static OrderFeePanel getInstance(){
		if (instance == null) {
			instance = new OrderFeePanel();
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
			return OrderFeePanel.class.getName();
		}
	}

	ContentPanel panel ;
	Label goodsAmountLabel = new Label("");
	Label totalAmountLabel = new Label("");
	TextField<String> shippingFeeField = new TextField<String>();
	Label moneyPaidFeeLabel = new Label("");
	TextField<String> insuranceField = new TextField<String>();
	HorizontalPanel surplusPanel = new HorizontalPanel();
	TextField<String> surplusField = new TextField<String>();
	Label surplusLabel = new Label("");
	TextField<String> payFeeField = new TextField<String>();
	Label orderAmountLabel = new Label("");
	
	Button btnPre;
	Button btnOk;
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		panel = new ContentPanel();
		TableLayout tl = new TableLayout(4);
		tl.setWidth("60%");
		panel.setLayout(tl);
		
		panel.add(new Label(Resources.constants.orderFee_goodsAmount() + ": "));
		panel.add(goodsAmountLabel);
		panel.add(new Label(Resources.constants.orderFee_orderAmount() + ": "));
		panel.add(totalAmountLabel);
		panel.add(new Label(Resources.constants.orderFee_shippingFee() + "："));
		panel.add(shippingFeeField);
		panel.add(new Label(Resources.constants.orderFee_moneyPaid() + "："));
		panel.add(moneyPaidFeeLabel);
		panel.add(new Label(Resources.constants.orderFee_insurance() + "："));
		panel.add(insuranceField);
		
		panel.add(new Label(Resources.constants.orderFee_userSurplus() + "："));
		surplusPanel.add(surplusField);
		surplusField.setVisible(false);
		surplusPanel.add(surplusLabel);
		panel.add(surplusPanel);		
		
		panel.add(new Label(Resources.constants.orderFee_payFee() + ": "));
		panel.add(payFeeField);
		panel.add(new Label(Resources.constants.orderFee_moneyShouldPay() + "："));
		panel.add(orderAmountLabel);
		
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		btnPre = new Button(Resources.constants.orderFee_pre(), new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				OrderPayPanel.State newState = new OrderPayPanel.State();
				newState.setUserId(getCurState().getUserId());
				newState.setIsEdit(false);
				newState.setId(getCurState().getPkId());
				newState.execute();
			}
		});
     	panel.addButton(btnPre);
     	
     	btnOk = new Button(Resources.constants.orderFee_ok(), new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
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
						if(!getCurState().getIsEdit()) {
							OrderDetailPanel.State newState = new OrderDetailPanel.State();
							newState.setId(getCurState().getPkId());
							newState.execute();
						}
						else {
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
					}
				});	
			}
		});
     	panel.addButton(btnOk);
		
		panel.addButton(new Button(Resources.constants.orderFee_cancel(),new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				cancel();
			}
		}));
		
		add(panel);
	}	

	BeanObject user;
	BeanObject order;
	@Override
	public void refresh() {
		isChange = false;
		user = null;
		order = null;
     	btnPre.setVisible(false);
     	if(!getCurState().getIsEdit()) {
     		btnPre.setVisible(true);
     	}
		
		String orderId = getCurState().getPkId();
		String userId = getCurState().getUserId();
		surplusField.setVisible(false);
		
		new ReadService().getBean(ModelNames.ORDER, orderId, new ReadService.Listener() {
			public void onSuccess(BeanObject bean) {
				order = bean;
				Double goodsAmount = order.get(IOrderInfo.GOODS_AMOUNT);
				Double shippingFee = order.get(IOrderInfo.SHIPPING_FEE);
				Double insurance = order.get(IOrderInfo.INSURE_FEE);
				Double payFee = order.get(IOrderInfo.PAY_FEE);
				Double moneyPaid = order.get(IOrderInfo.MONEY_PAID);
				Double surplus = order.get(IOrderInfo.SURPLUS);
				Double orderAmount = order.get(IOrderInfo.ORDER_AMOUNT);
				double totalAmount = goodsAmount + shippingFee + payFee + insurance;
				goodsAmountLabel.setText("￥" + goodsAmount);
				totalAmountLabel.setText("￥" + totalAmount);
				shippingFeeField.setValue(String.valueOf(shippingFee));
				moneyPaidFeeLabel.setText("￥" + moneyPaid);
				insuranceField.setValue(String.valueOf(insurance));
				payFeeField.setValue(String.valueOf(payFee));
				orderAmountLabel.setText("￥" + orderAmount);	
				surplusField.setValue(String.valueOf(surplus));
				surplusLabel.setText(Resources.constants.orderFee_surplus() + ":0");
			}
		});
		
		if(userId != null) {
			new ReadService().getBean(ModelNames.USER, userId, new ReadService.Listener() {
				public void onSuccess(BeanObject bean) {
					user = bean;
					surplusField.setVisible(true);
					Double surplus = bean.get(IUser.MONEY);
					surplusLabel.setText(Resources.constants.orderFee_surplus() + ":" + surplus);
				}
			});
		}
	}
	
	boolean updateIsReady;
	boolean isChange = false;
	boolean isIncrease = false;
	private void updateOrder() {
		updateIsReady = false;
		final String orderId = getCurState().getPkId();
		String userId = getCurState().getUserId();
		
		final double insurance = getDoubleValue(insuranceField);
		final double shippingFee = getDoubleValue(shippingFeeField);
		final double payFee = getDoubleValue(payFeeField);
		final Double moneyPaid = order.get(IOrderInfo.MONEY_PAID);
		double surplus = 0;
		if(userId != null) {
			surplus = getDoubleValue(surplusField);
			if(surplus > (Double)user.get(IUser.MONEY)) {
				surplus = 0;
			} else {
				//待解决，生日为null
				if(user.get(IUser.BIRTHDAY) == null)
					user.set(IUser.BIRTHDAY, new Date());
				
				user.set(IUser.MONEY, (Double)user.get(IUser.MONEY) - surplus);
				new UpdateService().updateBean(userId, user, null);
			}
		}				
		Double goodsAmount = order.get(IOrderInfo.GOODS_AMOUNT);
		double orderAmount = goodsAmount + shippingFee + insurance + payFee - surplus - moneyPaid;
		
		if(getCurState().getIsEdit()) {
			if(order.getInt(IOrderInfo.PAY_STATUS) == IOrderInfo.PS_PAYED && (Double)order.get(IOrderInfo.ORDER_AMOUNT) != orderAmount) {
				isChange = true;
				if((Double)order.get(IOrderInfo.ORDER_AMOUNT) > orderAmount)
					isIncrease = false;
				else {
					isIncrease = true;
					order.set(IOrderInfo.PAY_STATUS, IOrderInfo.PS_UNPAYED);
				}
			}
		}
		
		order.set(IOrderInfo.INSURE_FEE, insurance);
		order.set(IOrderInfo.SHIPPING_FEE, shippingFee);
		order.set(IOrderInfo.PAY_FEE, payFee);
		order.set(IOrderInfo.SURPLUS, surplus);
		order.set(IOrderInfo.ORDER_AMOUNT, orderAmount);
		new UpdateService().updateBean(orderId, order, new UpdateService.Listener() {
			public void onSuccess(Boolean success) {
				updateIsReady = true;
			}							
		});
	}
	
	private double getDoubleValue(TextField<String> field) {
		double value;
		try {
			value = Double.valueOf(field.getValue());
		} catch(NumberFormatException e) {
			value = 0;
		}
		return value;
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
