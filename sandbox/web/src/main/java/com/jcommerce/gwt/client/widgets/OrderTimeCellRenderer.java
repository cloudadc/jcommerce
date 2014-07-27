package com.jcommerce.gwt.client.widgets;


import java.util.Date;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderInfo;

public class OrderTimeCellRenderer extends TimeCellRenderer {
    
	
	public OrderTimeCellRenderer() {
		super();
	}

	@Override
	public Object render(BeanObject model, String property, ColumnData config,
			final int rowIndex, final int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {   
//		String userId = model.getString(IOrderInfo.USER_ID);
		Date dateTime = new Date((Long)model.get(IOrderInfo.ADD_TIME));
		DateTimeFormat formatter = DateTimeFormat.getFormat("MM-dd HH:mm");
		final String timeStr = formatter.format(dateTime);
//		if ( userId == null ){
//			return Resources.constants.OrderList_anonymous() + "<br>" + timeStr;
//		}else{
//			new ReadService().getBean(ModelNames.USER, userId, new ReadService.Listener(){
//				@Override
//				public void onSuccess(BeanObject bean) {
//					setOrderTime( bean.getString(IUser.USER_NAME) + "<br>" + timeStr );
//				}
//	    	});
//		}
		return timeStr;
		
	}
}

