package com.jcommerce.gwt.client.panels.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.ICard;
import com.jcommerce.gwt.client.model.IOrder;
import com.jcommerce.gwt.client.model.IPack;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class CostSettingPanel extends ContentPanel{
	
	private BeanObject user = new BeanObject(ModelNames.USER);
	private BeanObject userAddress = new BeanObject();
	private List<BeanObject> orderGoodsList = new ArrayList<BeanObject>();

	private BeanObject card = new BeanObject(ModelNames.CARD);
	private BeanObject pack = new BeanObject(ModelNames.PACK);
	private BeanObject order = new BeanObject(ModelNames.ORDER);
	private Map<String, Object> orderOtherInfoMap;
	private Map<String, Object> orderUserAddressMap;
	private BeanObject payments = new BeanObject(ModelNames.PAYMENT);
	
	private double goodsFee;
	private double cardFee;
	private double packFee;
	
	
	private TextBox goodsPaid = new TextBox();
	private TextBox cardFeeBox = new TextBox();
	private TextBox packFeeBox = new TextBox();
//	private Label surplus = new Label();
	private TextBox integral = new TextBox();
	private TextBox toBuyer = new TextBox();
	private TextBox moneyPaid = new TextBox();
	private TextBox payFeeBox = new TextBox();
	private TextBox discount = new TextBox();
	private ColumnPanel contentPanel = new ColumnPanel();
	public CostSettingPanel() {
		
		ContentPanel panel = new ContentPanel();
		goodsPaid.setWidth("100px");
		goodsPaid.setReadOnly(true);
        contentPanel.createPanel(IOrder.PAYNOTE, "商品总金额:", goodsPaid);
        TextBox tax = new TextBox();
        tax.setWidth("100px");
        tax.setEnabled(true);
        tax.setText("0.00");
        contentPanel.createPanel(IOrder.TAX, "发票税额:", tax);
        
        TextBox shippingFee = new TextBox();
        shippingFee.setWidth("100px");
        shippingFee.setEnabled(true);
        shippingFee.setText("0.00");
        contentPanel.createPanel(IOrder.SHIPPINGFEE, "配送费用:", shippingFee);
        
        TextBox insureFee = new TextBox();
        insureFee.setWidth("100px");
        insureFee.setEnabled(true);
        insureFee.setText("0.00");
        contentPanel.createPanel(IOrder.INSUREFEE, "保价费用:", insureFee);
        
        
        payFeeBox.setWidth("100px");
        payFeeBox.setEnabled(true);
        payFeeBox.setText("0.00");
        contentPanel.createPanel(IOrder.PAYFEE, "支付费用:", payFeeBox);
        
        
        packFeeBox.setWidth("100px");
        packFeeBox.setEnabled(true);
        contentPanel.createPanel(IOrder.PACKFEE, "包装费用:", packFeeBox);
        
        
        cardFeeBox.setWidth("100px");
        cardFeeBox.setEnabled(true);
        contentPanel.createPanel(IOrder.CARDFEE, "贺卡费用:", cardFeeBox);
        
        
        discount.setWidth("100px");
        discount.setEnabled(true);
        
        contentPanel.createPanel(IOrder.DISCOUNT, "折扣:", discount);
        
//        TextBox ordersPaid = new TextBox();
//        ordersPaid.setWidth("100px");
//        ordersPaid.setEnabled(true);
//        contentPanel.createPanel("ordersPaid", "订单总金额:", ordersPaid);
//        
//        TextBox paidAccount = new TextBox();
//        paidAccount.setWidth("100px");
//        paidAccount.setEnabled(true);
//        contentPanel.createPanel("paidAccount", "已付款金额:", paidAccount);
        
        
//        surplus.setWidth("100px");
//        shippingFee.setText("0.00");
//        contentPanel.createPanel(IOrder.SURPLUS, "使用余额:", surplus);
//        
//        
//        integral.setWidth("100px");
//        contentPanel.createPanel(IOrder.INTEGRAL, "使用积分:", integral);
        
       
//        toBuyer.setWidth("100px");
//        shippingFee.setText("0.00");
//        contentPanel.createPanel(IOrder.TOBUYER, "使用红包:", toBuyer);
        
        moneyPaid.setReadOnly(true);
        moneyPaid.setWidth("100px");
        contentPanel.createPanel(IOrder.MONEYPAID, "应付款金额:", moneyPaid);
        
        
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setButtonAlign(HorizontalAlignment.CENTER);      
        panel.add(contentPanel);
        
        this.setHeading("设置费用");
        this.setSize(780, 555);
        this.add(panel);
	}
	
	public void calculatePrice()
	{
//		for(int i=0; i<orderGoodsList.size(); i++)
//		{
//			BeanObject orderGoods = orderGoodsList.get(i);
//			goodsFee += Double.parseDouble((orderGoods.getString(IOrderGoods.MARKETPRICE)));
//		}
		
		goodsFee =  Double.parseDouble(order.getString(IOrder.PAYNOTE));
		goodsPaid.setText("￥ " + goodsFee  + " 元");
		
		if(goodsFee > Double.parseDouble(card.getString(ICard.FREEMONEY)))
		{
			cardFee = 0.0;
		}
		else
		{
			cardFee = Double.parseDouble(card.getString(ICard.FEE));
		}
		
		if(goodsFee > Double.parseDouble(pack.getString(IPack.FREEMONEY)))
		{
			packFee = 0.0;
		}
		else
		{
			packFee = Double.parseDouble(pack.getString(IPack.FEE));
		}
		
		double pay = Double.parseDouble(payments.getString(IPayment.FEE));
		double payFee = pay*goodsFee/100;
		payFeeBox.setText(pay*goodsFee/100 + "");
		cardFeeBox.setText(cardFee + "");
		packFeeBox.setText(packFee + "");
		discount.setText(order.getString(IOrder.DISCOUNT));
		double discountDouble =0 ;
		if(order.getString(IOrder.DISCOUNT) !=null)
		{
			discountDouble = Double.parseDouble(order.getString(IOrder.DISCOUNT));
		}
		
		double sumFee = goodsFee*(100-discountDouble)/100 + cardFee + packFee + payFee;
		moneyPaid.setText("￥ " + sumFee + " 元");
	}
	
	public void setUser(BeanObject user) 
	{
		this.user = user;
	}

	public void setUserAddress(BeanObject userAddress)
	{
		this.userAddress = userAddress;
	}

	public void setOrderGoods(List<BeanObject> orderGoods) 
	{
		this.orderGoodsList = orderGoods;
	}

	public void setCard(BeanObject card)
	{
		this.card = card;
	}

	public void setPack(BeanObject pack) 
	{
		this.pack = pack;
	}

	public void setOrderOtherInfo(Map<String, Object> orderInfo) 
	{
		this.orderOtherInfoMap = orderInfo;
	}
	
	public void setOrderAddressMap(Map<String, Object> orderUserAddressMap) 
	{
		this.orderUserAddressMap = orderUserAddressMap;
	}

	public BeanObject getOrder() 
	{
		Map<String, Object> orderInfoMap = contentPanel.getValues();
		
		orderInfoMap.remove("ordersPaid");
		orderInfoMap.remove("paidAccount");
		
		order.setValues(orderInfoMap);
		
		return order;
	}

	public void setOrder(BeanObject order) {
		this.order = order;
	}

	public void setPayments(BeanObject payments)
	{
		this.payments = payments;
	}
}
