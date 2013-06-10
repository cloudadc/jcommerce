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
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.ui.CheckBox;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrder;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;


public class SelectShippingPanel extends ContentPanel{
	

	public static ContentPanel getShippingPanel(){
		Criteria criteria = new Criteria();
		PagingToolBar toolBar;
    	
    	BasePagingLoader loader = new PagingListService().getLoader(ModelNames.SHIPPING, criteria);
    	loader.load(0, 10);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IShipping.NAME, "名称", 80));
		columns.add(new ColumnConfig(IShipping.DESCRIPTION, "描述", 104));
		columns.add(new ColumnConfig(IOrder.SHIPPINGFEE, "配送费", 80));
		columns.add(new ColumnConfig("freeMoney", "免费额度", 80));
		columns.add(new ColumnConfig(IShipping.INSURE, "保价费", 80));
		
		ColumnModel cm = new ColumnModel(columns);
		
        Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSize(750, 200);
       
        ColumnPanel wantedInsure = new ColumnPanel();
        wantedInsure.createPanel("insureOrNot", "我要保价", new CheckBox());
        
        
        final ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setSize(750, 200);
        panel.setLayout(new FitLayout());
        panel.setHeading("选择配送方式");
        panel.add(grid);
        panel.add(wantedInsure);
        
        return panel;
        
		
	}
}
