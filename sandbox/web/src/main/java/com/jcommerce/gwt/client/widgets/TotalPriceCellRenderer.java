package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderGoods;

public class TotalPriceCellRenderer implements GridCellRenderer<BeanObject> {
	
	GridView view;

	public TotalPriceCellRenderer(Grid grid) {
    	this.view = grid.getView();
    }
	public TotalPriceCellRenderer(){
		
	}
	public Object render(BeanObject model, String property, ColumnData config,
			int rowIndex, int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {
		double price = model.get(IOrderGoods.GOODSPRICE);
		int num = ((Number)model.get(IOrderGoods.GOODSNUMBER)).intValue();
		return price * num;
	}
}
