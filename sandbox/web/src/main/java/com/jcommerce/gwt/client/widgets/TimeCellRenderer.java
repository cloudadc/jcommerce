package com.jcommerce.gwt.client.widgets;

import java.util.Date;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.jcommerce.gwt.client.form.BeanObject;

public class TimeCellRenderer implements GridCellRenderer<BeanObject> {
	String format = "yy-MM-dd HH:mm:ss";

	public TimeCellRenderer(){		
	}

	public Object render(BeanObject model, String property, ColumnData config,
			int rowIndex, int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {
		if(model.get(property) != null && (Long)model.get(property) != 0L ){
			Date dateTime = new Date((Long)model.get(property));
			DateTimeFormat formatter = DateTimeFormat.getFormat(format);
			String timeStr = formatter.format(dateTime);
			return timeStr;
		}
		else{
			return "";
		}
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
