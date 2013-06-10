package com.jcommerce.web.to;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Order;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.web.util.PrintfFormat;
import com.jcommerce.web.util.WebFormatUtils;

public class OrderWrapper extends BaseModelWrapper {

	Order order;
	@Override
	protected Object getWrapped() {
		return getOrder();
	}
	public OrderWrapper(ModelObject order) {
		super();
		this.order = (Order)order;
	}
	
	public Order getOrder() {
		return order;
	}
	
	
	public String getTotalFee() {
		Order oi = getOrder();
		double total = oi.getGoodsAmount() + oi.getShippingFee() + 
			oi.getInsureFee() + oi.getPayFee()+ 
			oi.getPackFee() + oi.getCardFee() + 
			oi.getTax() - oi.getDiscount();
		return WebFormatUtils.priceFormat(total);
	}
	
	/*修改，获得订单时间*/
	public String getOrderTime() {
		long addTime = getOrder().getAddTime().getTime();
		Date date = new Date(addTime);
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String orderTime = formatter.format(date);
		return orderTime;
	}
	
	public String getStatus(){
		int status = getOrder().getStatus();
		if(status == 0)
			return "未确认";
		else
			return "已确认";
	}
	
	public String getPayStatus(){
		int payStatus = getOrder().getPayStatus();
		if(payStatus == Order.PAY_PAYED) {
			return "已付款";
		}
		else if(payStatus == Order.PAY_UNPAYED)
			return "未付款";
		else
			return "付款中";
	}
	
    // for template
    public String getOrderId() {
    	return getOrder().getId();
    }
    
    public String getPostscript() {
    	if( order.getPostScript() != null){
			return order.getPostScript();
	    }
		else{
			return "";
		}
	}
    
    public String getHandler() {
    	long orderStatues = getOrder().getStatus();
    	long shippingStatues = getOrder().getShippingStatus();
    	long payStatues = getOrder().getPayStatus();
    	Lang lang = Lang.getInstance();
    	String handler = null;
    	
    	if(orderStatues == IOrderInfo.OS_UNCONFIRMED) {
    		handler = "<a href=\"user.action?act=cancel_order&orderId=" + getOrderId() + "\" onclick=\"if (!confirm('" + lang.getString("confirmCancel") + "')) return false;\">" + lang.getString("cancel") + "</a>";
    	}
    	else if(orderStatues == IOrderInfo.OS_CONFIRMED) {
    		/* 对配送状态的处理 */
            if (shippingStatues == IOrderInfo.SS_SHIPPED)
            {
                handler = "<a href=\"user.action?act=affirm_received&orderId=" + getOrderId() + "\" onclick=\"if (!confirm('" + lang.getString("confirmReceived") + "')) return false;\">" + lang.getString("received") + "</a>";
            }
            else if (shippingStatues == IOrderInfo.SS_RECEIVED)
            {
            	handler = "<span style='color:red'>" + lang.getString("ssReceived") + "</span>";
            }
            else
            {
                if (payStatues == IOrderInfo.PS_UNPAYED)
                {
                    handler = "<a href=\"user.action?act=order_detail&orderId=" + getOrderId() + ">" + lang.getString("payMoney") + "</a>";
                }
                else
                {
                	handler = "<a href=\"user.action?act=order_detail&orderId=" + getOrderId() + ">" + lang.getString("viewOrder") + "</a>";
                }

            }
    	}
    	else
        {
        	Map map = (Map)lang.get("os");
        	String statues = "";
        	if(orderStatues == 2)
        		statues = (String) map.get("OS_CANCELED");
        	else if(orderStatues == 3)
        		statues = (String) map.get("OS_INVALID");
        	else
        		statues = (String) map.get("OS_RETURNED");
    		handler = "<span style='color:red'>" + statues + "</span>";
        }
    	
    	return handler;
    }
    
    public String getConfirmTime() {
    	long confirmTime = getOrder().getConfirmTime().getTime();
    	if(getOrder().getStatus() == IOrderInfo.OS_CONFIRMED && confirmTime > 0) {
    		return new PrintfFormat(Lang.getInstance().getString("confirmTime")).sprintf(new Object[]{confirmTime});
    	}
    	else
    		return "";
    }
    
    public String getPayTime() {
    	long payTime = getOrder().getPayTime().getTime();
    	if(getOrder().getPayStatus() != IOrderInfo.PS_UNPAYED && payTime > 0) {
    		return new PrintfFormat(Lang.getInstance().getString("payTime")).sprintf(new Object[]{payTime});
    	}
    	else
    		return "";
    }
    
    public String getShippingTime() {
    	long shippingTime = getOrder().getShippingTime().getTime();
    	if((getOrder().getShippingStatus() == IOrderInfo.SS_SHIPPED || getOrder().getShippingStatus() == IOrderInfo.SS_RECEIVED)
    			&& shippingTime > 0) {
    		return new PrintfFormat(Lang.getInstance().getString("shippingTime")).sprintf(new Object[]{shippingTime});
    	}
    	else
    		return "";
    }
    
    public String getInvoiceNo() {
    	return getOrder().getInvoiceNO();
    }
    
    public String getToBuyer() {
    	return getOrder().getToBuyer();
    }
    
    public String getFormatedGoodsAmount() {
    	return WebFormatUtils.priceFormat(getOrder().getGoodsAmount());
    }
    
    public String getFormatedShippingFee() {
    	return WebFormatUtils.priceFormat(getOrder().getShippingFee());
    }
    public String getFormatedInsureFee() {
    	return WebFormatUtils.priceFormat(getOrder().getInsureFee());
    }
    public String getFormatedPayFee() {
    	return WebFormatUtils.priceFormat(getOrder().getPayFee());
    }
    public String getFormatedMoneyPaid() {
    	return WebFormatUtils.priceFormat(getOrder().getMoneyPaid());
    }
    public String getFormatedSurplus() {
    	return WebFormatUtils.priceFormat(getOrder().getSurplus());
    }
    public String getFormatedOrderAmount() {
    	return WebFormatUtils.priceFormat(getOrder().getOrderAmount());
    }
    
    public String getPackName() {
    	return getOrder().getPackName();
    }
    public String getCardName() {
    	return getOrder().getCardName();
    }
    public String getCardMessage() {
    	return getOrder().getCardMessage();
    }
    public String getInvPayee() {
    	return getOrder().getInvoicePayee();
    }
    public String getInvContent() {
    	return getOrder().getInvoiceContent();
    }
    
    public String getHowOosName() {
    	return getOrder().getHowOss() == null ? "" : getOrder().getHowOss();
    }
    
    public String getAddress() {
        return getOrder().getAddress();
    }
    
    public double getBonus() {
        return getOrder().getBonusMoney();
    }
    
    public double getCardFee() {
        return getOrder().getCardFee();
    }
    
    public String getConsignee() {
        return getOrder().getConsignee();
    }
    
    public double getDiscount() {
        return getOrder().getDiscount();
    }
    
    public String getFormatedBonus() {
        return WebFormatUtils.priceFormat(getOrder().getBonusMoney());
    }
    
    public String getFormatedCardFee() {
        return WebFormatUtils.priceFormat(getOrder().getCardFee());
    }
    
    public String getFormatedDiscount() {
        return WebFormatUtils.priceFormat(getOrder().getDiscount());
    }
    
    public String getFormatedIntegralMoney() {
        return WebFormatUtils.priceFormat(getOrder().getIntegralMoney());
    }
    
    public String getFormatedPackFee() {
        return WebFormatUtils.priceFormat(getOrder().getPackFee());
    }
    
    public String getFormatedTotalFee() {
        return WebFormatUtils.priceFormat(getOrder().getSurplus());
    }
    
    public double getInsureFee() {
        return getOrder().getInsureFee();
    }
    
    public double getIntegralMoney() {
        return getOrder().getIntegralMoney();
    }
    
    public String getInvoiceNote() {
        return getOrder().getInvoiceNote();
    }
    
    public String getMobile() {
        return getOrder().getMobile();
    }
    
    public double getMoneyPaid() {
        return getOrder().getMoneyPaid();
    }
    
    public String getOrderSn() {
        return getOrder().getSN();
    }
    
    public double getPackFee() {
        return getOrder().getPackFee();
    }
    
    public double getPayFee() {
        return getOrder().getPayFee();
    }
    
    public String getPayName() {
        return getOrder().getPayName();
    }
    
    public String getPayNote() {
        return getOrder().getPayNote();
    }
    
    public String getRegion() {
        return getOrder().getRegion().getName();
    }
    
    public String getShippingName() {
        return getOrder().getShippingName();
    }
    
    public double getShippingFee() {
        return getOrder().getShippingFee();
    }
    
    public double getSurplus() {
        return getOrder().getSurplus();
    }
    
    public String getTel() {
        return getOrder().getPhone();
    }
    
    public String getUserName() {
        return getOrder().getUser().getName();
    }
    
    public String getZipcode() {
        return getOrder().getZip();
    }
}
