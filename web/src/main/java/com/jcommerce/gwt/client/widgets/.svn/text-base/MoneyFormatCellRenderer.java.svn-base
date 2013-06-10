package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderInfo;

public class MoneyFormatCellRenderer implements GridCellRenderer<BeanObject> {
    
	GridView view;   
	double money = Double.MIN_VALUE;

	public Object render(BeanObject model, String property, ColumnData config,
			final int rowIndex, final int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {   
		if(money != Double.MIN_VALUE) {
			return "￥" + money;
		}
		else {
			if(property.equals("totalAmount")){
				double totalAmount = (Double)model.get(IOrderInfo.GOODS_AMOUNT)+(Double)model.get(IOrderInfo.PAY_FEE)+(Double)model.get(IOrderInfo.SHIPPING_FEE)
										+ (Double)model.get(IOrderInfo.INSURE_FEE);
				return "￥" + totalAmount;
			}
			return "￥" + model.getString(property);
		}
	}
	
	public void setMoney(double money) {
		this.money = money;
	}
}

