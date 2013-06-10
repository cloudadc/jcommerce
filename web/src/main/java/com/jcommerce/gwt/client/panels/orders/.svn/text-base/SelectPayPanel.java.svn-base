package com.jcommerce.gwt.client.panels.orders;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrder;
import com.jcommerce.gwt.client.service.PagingListService;


public class SelectPayPanel extends ContentPanel{
	
	public SelectPayPanel(){
		
	} 
	public static ContentPanel getPayPanel(){
		
		List<String> wantedFields = new ArrayList<String>();
    	wantedFields.add(IOrder.PAYMENT);
    	wantedFields.add(IOrder.PAYNOTE);
    	
    	BasePagingLoader loader = new PagingListService().getLoader(ModelNames.ORDER, wantedFields);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IOrder.PAYMENT, "名称", 80));
		columns.add(new ColumnConfig(IOrder.PAYNOTE, "描述", 104));
		columns.add(new ColumnConfig("handlingFee", "手续费", 80));
		
		ColumnModel cm = new ColumnModel(columns);
		
        Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSize(750, 200);
       
		
        final ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setSize(750, 200);
        panel.setLayout(new FitLayout());
        panel.setHeading("选择支付方式");
        panel.add(grid);
        return panel;
        
		
	}
	
}
