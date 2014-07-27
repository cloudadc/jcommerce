package com.jcommerce.gwt.client.widgets;


import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.resources.Resources;

public class OrderStateCellRenderer implements GridCellRenderer<BeanObject> {
    
	GridView view;
	String type = null;
	
	public OrderStateCellRenderer(String type) {
		this.type = type;
    }
    

	public Object render(BeanObject model, String property, ColumnData config,
			final int rowIndex, final int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {   
		
		String orderStatusStr = null;
		String shippingStatusStr = null;
		String payStatusStr = null;
		int orderStatus = IOrderInfo.OS_UNCONFIRMED;
		if (model.get(IOrderInfo.ORDER_STATUS) != null) {
		    orderStatus = ((Number)model.get(IOrderInfo.ORDER_STATUS)).intValue(); 
		}
		int shippingStatus = IOrderInfo.SS_UNSHIPPED;
		if (model.get(IOrderInfo.SHIPPING_STATUS) != null) {
		    shippingStatus = ((Number)model.get(IOrderInfo.SHIPPING_STATUS)).intValue();
		}
		int payStatus = IOrderInfo.PS_UNPAYED;
		if (model.get(IOrderInfo.PAY_STATUS) != null) {
		    payStatus = ((Number)model.get(IOrderInfo.PAY_STATUS)).intValue();
		}
		
		switch(orderStatus) { 
			case IOrderInfo.OS_CANCELED: 
//				orderStatusStr = "OrderStatus_OS_CANCELED";
                orderStatusStr = Resources.constants.OrderStatus_OS_CANCELED();
				break;
			case IOrderInfo.OS_CONFIRMED: 
//				orderStatusStr = "OrderStatus_OS_CONFIRMED";
                orderStatusStr = Resources.constants.OrderStatus_OS_CONFIRMED();
				break;
			case IOrderInfo.OS_INVALID: 
//				orderStatusStr = "OrderStatus_OS_INVALID";
                orderStatusStr = Resources.constants.OrderStatus_OS_INVALID();
				break;
			case IOrderInfo.OS_RETURNED: 
//				orderStatusStr = "OrderStatus_OS_RETURNED";
                orderStatusStr = Resources.constants.OrderStatus_OS_RETURNED();
				break;
			case IOrderInfo.OS_UNCONFIRMED: 
//				orderStatusStr = "OrderStatus_OS_UNCONFIRMED";
                orderStatusStr = Resources.constants.OrderStatus_OS_UNCONFIRMED();
				break;
		}
		
		switch(payStatus) {
			case IOrderInfo.PS_PAYED: 
//				payStatusStr = "OrderStatus_PS_PAYED";
                payStatusStr = Resources.constants.OrderStatus_PS_PAYED();
				break;
			case IOrderInfo.PS_PAYING: 
//				payStatusStr = "OrderStatus_PS_PAYING";
                payStatusStr = Resources.constants.OrderStatus_PS_PAYING();
				break;
			case IOrderInfo.PS_UNPAYED:
//				payStatusStr = "OrderStatus_PS_UNPAYED";
                payStatusStr = Resources.constants.OrderStatus_PS_UNPAYED();
				break;
		}
		
		switch(shippingStatus) {
			case IOrderInfo.SS_PREPARING: 
//				shippingStatusStr = "OrderStatus_SS_PREPARING"; 
                shippingStatusStr = Resources.constants.OrderStatus_SS_PREPARING(); 
				break;
			case IOrderInfo.SS_RECEIVED: 
//				shippingStatusStr = "OrderStatus_SS_RECEIVED"; 
                shippingStatusStr = Resources.constants.OrderStatus_SS_RECEIVED(); 
				break;
			case IOrderInfo.SS_SHIPPED: 
//				shippingStatusStr = "OrderStatus_SS_SHIPPED"; 
                shippingStatusStr = Resources.constants.OrderStatus_SS_SHIPPED(); 
				break;
			case IOrderInfo.SS_UNSHIPPED: 
//				shippingStatusStr = "OrderStatus_SS_UNSHIPPED"; 
                shippingStatusStr = Resources.constants.OrderStatus_SS_UNSHIPPED(); 
				break;
		}
		
		if(type == null) {
			return orderStatusStr + "," + payStatusStr + "," + shippingStatusStr;
		}
		else if(type.equals("os"))
			return orderStatusStr;
		else if(type.equals("ps"))
			return payStatusStr;
		else
			return shippingStatusStr;
	}
}

