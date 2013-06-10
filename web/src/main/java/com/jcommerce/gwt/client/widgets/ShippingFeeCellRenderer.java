package com.jcommerce.gwt.client.widgets;

import java.util.Map;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.WaitService;

public class ShippingFeeCellRenderer implements GridCellRenderer<BeanObject> {
    String shippingId;
	String type;
	double fee;
	
    public ShippingFeeCellRenderer(String shippingId, String type) {
    	this.shippingId = shippingId;
    	this.type = type;
    	fee = 0;
    }

	public Object render(BeanObject model, String property, ColumnData config,
			int rowIndex, int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {
		
		RemoteService.getSpecialService().getShippingConfig(shippingId, new AsyncCallback<Map<String, String>>(){
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				Window.alert("ERROR: "+caught.getMessage());
			}
			public void onSuccess(Map<String, String> result) {
				if(type.equals("shippingFee"))
					fee = Double.parseDouble(result.get("base_fee"));
				else 
					fee = Double.parseDouble(result.get("freeMoney"));
			}
		});
		return null;
	}
}
