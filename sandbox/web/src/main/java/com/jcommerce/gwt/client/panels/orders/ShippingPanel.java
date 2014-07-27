package com.jcommerce.gwt.client.panels.orders;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
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
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class ShippingPanel extends ContentPanel{
	
	private String usrAddrInfo = "";
//	BasePagingLoader loader;
	ListStore<BeanObject> store;
	PagingToolBar toolBar = new PagingToolBar(10);
	BeanObject shipping;
	BeanObject order = new BeanObject(ModelNames.ORDER);
	

	public BeanObject getOrder() {
		order.set(IOrder.SHIPPINGNAME, shipping.getString(IShipping.NAME));
		order.set(IOrder.SHIPPING, shipping.getString(IShipping.ID));
		order.set(IOrder.SHIPPINGFEE, 0);
		return order;
	}

	public void setOrder(BeanObject order)
	{
		this.order = order;
	}

	public void setUsrAddrInfo(String usrAddrInfo) 
	{
		this.usrAddrInfo = usrAddrInfo;
	}

	private native void initJS(ShippingPanel me) /*-{
	   $wnd.chooseShipping = function (id) {
	       me.@com.jcommerce.gwt.client.panels.orders.ShippingPanel::chooseShipping(Ljava/lang/String;)(id);
	   };
	   }-*/;
	
	public ShippingPanel(Criteria criteria)
	{
		initJS(this);
		store = new ListStore<BeanObject>();
		
		new ListService().listBeans(ModelNames.SHIPPING, criteria, new ListService.Listener(){

			public void onSuccess(List<BeanObject> beans)
			{
				for(int i=0; i<beans.size(); i++)
				{
					store.add(beans.get(i));
				}
			}
			
		});
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		columns.add(new ColumnConfig(IShipping.NAME, "名称", 80));
		columns.add(new ColumnConfig(IShipping.DESCRIPTION, "描述", 130));
		columns.add(new ColumnConfig(IOrder.SHIPPINGFEE, "配送费", 80));
		columns.add(new ColumnConfig("freeMoney", "免费额度", 80));
		columns.add(new ColumnConfig(IShipping.INSURE, "保价费", 80));
		ColumnConfig actcol = new ColumnConfig("Action", "操作", 100);
		columns.add(actcol);
		
		ColumnModel cm = new ColumnModel(columns);
		
        Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSize(750, 200);
       
        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = null;
        act = new ActionCellRenderer.ActionInfo();
        act.setImage("yes.gif");
		act.setAction("chooseShipping($id)");
		render.addAction(act);
              
        actcol.setRenderer(render);
        
        ColumnPanel wantedInsure = new ColumnPanel();
        wantedInsure.createPanel(IShipping.SUPPORTCOD, "我要保价", new CheckBox());
        
        final ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setLayout(new FitLayout());
        panel.add(grid);      
		panel.add(wantedInsure);
		panel.setBottomComponent(toolBar);
		
        this.setSize(750, 555);
        this.setHeading("选择配送方式");
		this.add(panel);
		
	}
	
	private void chooseShipping(String id)
	{
		int lineIndex = -1;
		
		for(int i=0; i<store.getCount(); i++)
		{
			BeanObject theShipping = store.getAt(i);
			if(theShipping.getString(IShipping.ID).equals(id))
			{
				shipping = theShipping;
				lineIndex = i;
				break;
			}
			else
			{
				continue;
			}
		}
		if(lineIndex > -1)
		{	
            final Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
                public void handleEvent(MessageBoxEvent ce) {
                    Button btn = ce.getButtonClicked();
					if (btn.getText().equals("是")) 
					{
						store.removeAll();
					}

				}
			};
			MessageBox.confirm("Confirm", "Are you sure you want to choose this Shipping: " 
					+ shipping.getString(IShipping.NAME) + " ?",
					deleteListener);
		}
		else
		{
			Info.display("Error", "Cannot find the shippingObject you selected.");
		}
	}
	
	public BeanObject getShipping() 
	{
		return shipping;
	}
	
	
}
