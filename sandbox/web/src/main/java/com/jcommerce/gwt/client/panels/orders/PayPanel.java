package com.jcommerce.gwt.client.panels.orders;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrder;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;


public class PayPanel extends ContentPanel{
	private BeanObject payments = new BeanObject(ModelNames.PAYMENT);
	private BeanObject order = new BeanObject(ModelNames.ORDER);
	private ListStore<BeanObject> store;
	private PagingToolBar toolBar = new PagingToolBar(10);
	
	private native void initJS(PayPanel me) /*-{
	   $wnd.choosePayment = function (id) {
	       me.@com.jcommerce.gwt.client.panels.orders.PayPanel::choosePayment(Ljava/lang/String;)(id);
	   };
	   }-*/;
	public BeanObject getOrder() {
		order.set(IOrder.PAYMENT, payments.getString(IPayment.ID));
		order.set(IOrder.PAYNAME, payments.getString(IPayment.NAME));
		
		return order;
	}

	public void setOrder(BeanObject order) 
	{
		this.order = order;
	}
	
	public PayPanel(){
		initJS(this);
		Criteria criteria = new Criteria();
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.PAYMENT, criteria);
    	loader.load(0, 10);
    	store = new ListStore<BeanObject>(loader);
    	
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IPayment.NAME, "名称", 80));
		columns.add(new ColumnConfig(IPayment.DESCRIPTION, "描述", 200));
		columns.add(new ColumnConfig(IPayment.FEE, "手续费", 80));
		
		ColumnConfig actcol = new ColumnConfig("Action", "选取", 100);
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
		act.setAction("choosePayment($id)");
		render.addAction(act);
		
		actcol.setRenderer(render);
		
        final ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setLayout(new FitLayout());
        panel.add(grid);
        
        this.setSize(750, 200);
        this.setHeading("选择支付方式");
        this.add(panel);
        
	}
	
	private void choosePayment(String id)
	{
		int lineIndex = -1;
		
		for(int i=0; i<store.getCount(); i++)
		{
			BeanObject thePayments = store.getAt(i);
			if(thePayments.getString(IShipping.ID).equals(id))
			{
				payments = thePayments;
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
			MessageBox.confirm("Confirm", "Are you sure you want to choose this Payments: " 
					+ payments.getString(IShipping.NAME) + " ?",
					deleteListener);
		}
		else
		{
			Info.display("Error", "Cannot find the shippingObject you selected.");
		}
	}
	
	
	public BeanObject getPayments()
	{
		return payments;
	}
	
	
}
