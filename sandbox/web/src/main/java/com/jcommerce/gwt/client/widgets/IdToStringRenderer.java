package com.jcommerce.gwt.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.jcommerce.gwt.client.form.BeanObject;

public class IdToStringRenderer implements GridCellRenderer<BeanObject>{
	List<IdInfo> idToString = new ArrayList<IdInfo>();
	GridView view;
	
	public IdToStringRenderer(Grid grid) {
        this.view = grid.getView();
    }
	
	public void setIdToString(List<IdInfo> idToString) {
		this.idToString = idToString;
	}

	public void addIdInfo(IdInfo idInfo){
		idToString.add(idInfo);
	}
	
    public Object render(BeanObject model, String property, ColumnData config, int rowIndex, int colIndex,
            ListStore<BeanObject> store, Grid<BeanObject> grid) {
//	public String render(BeanObject model, String property, ColumnData config,
//			int rowIndex, int colIndex, ListStore<BeanObject> store) {
		Object name = store.getAt(rowIndex).get(property);
		for(IdInfo idInfo:idToString){
			if(name.toString().equals(idInfo.id)){
				return idInfo.string;
			}
		}
		
		return null;
	}
	public static class IdInfo{
		String id;
		String string;
		
		public IdInfo(){
			
		}
		public IdInfo(String id,String string){
			this.id = id;
			this.string = string;
		}
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String getString() {
			return string;
		}
		
		public void setString(String string) {
			this.string = string;
		}
		
	}

}
