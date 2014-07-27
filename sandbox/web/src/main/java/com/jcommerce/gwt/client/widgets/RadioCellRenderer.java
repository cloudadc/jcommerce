package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.jcommerce.gwt.client.form.BeanObject;

public class RadioCellRenderer implements GridCellRenderer<BeanObject> {
    
	RadioGroup rg;
    public RadioCellRenderer(RadioGroup rg) {
    	this.rg = rg;
    }

	public Object render(BeanObject model, String property, ColumnData config,
			int rowIndex, int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {
		Radio radio = new Radio();
		radio.setValueAttribute((String) model.get(property));
		rg.add(radio);
        return radio;
	}
}
