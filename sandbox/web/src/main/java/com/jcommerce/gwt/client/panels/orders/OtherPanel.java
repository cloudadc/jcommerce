package com.jcommerce.gwt.client.panels.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.ICard;
import com.jcommerce.gwt.client.model.IOrder;
import com.jcommerce.gwt.client.model.IPack;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class OtherPanel extends ContentPanel{
	
	private ListStore<BeanObject> packStore;
	private ListStore<BeanObject> cardStore;
	private Criteria criteria = new Criteria();
	private BeanObject pack;
	private BeanObject card;
	private ColumnPanel contentPanel;
	private Map<String, Object> orderInfoMap;
	BeanObject order = new BeanObject(ModelNames.ORDER);
	
	public BeanObject getOrder() {
		order.setValues(contentPanel.getValues());
		order.set(IOrder.CARD, card.getString(ICard.ID));
		order.set(IOrder.PACK, card.getString(IPack.ID));
		
		order.set(IOrder.CARDNAME, card.getString(ICard.NAME));
		order.set(IOrder.PACKNAME, card.getString(IPack.NAME));
		
		return order;
	}

	public void setOrder(BeanObject order) {
		this.order = order;
	}	
	private native void initJS(OtherPanel me) /*-{
	   $wnd.choosePack = function (id) {
	       me.@com.jcommerce.gwt.client.panels.orders.OtherPanel::choosePack(Ljava/lang/String;)(id);
	   };
	   $wnd.chooseCard = function (id) {
	       me.@com.jcommerce.gwt.client.panels.orders.OtherPanel::chooseCard(Ljava/lang/String;)(id);
	   };
	   }-*/;
	
	public OtherPanel() {
		initJS(this);
		
		ContentPanel totalPanel = new ContentPanel();
		
		List<ColumnConfig> packColumns = new ArrayList<ColumnConfig>();
		packStore = new ListStore<BeanObject>();
		
		new ListService().listBeans(ModelNames.PACK, criteria, new ListService.Listener(){

			public void onSuccess(List<BeanObject> beans)
			{
				for(int i=0; i<beans.size(); i++)
				{
					packStore.add(beans.get(i));
				}
			}
		});
		
        packColumns.add(new ColumnConfig(IPack.NAME, "名称", 80));
        packColumns.add(new ColumnConfig(IPack.FEE, "包装费", 104));
        packColumns.add(new ColumnConfig(IPack.FREEMONEY, "免费额度", 80));
        ColumnConfig packActcol = new ColumnConfig("Action", "选 取", 100);
        packColumns.add(packActcol);
        
		ColumnModel packCm = new ColumnModel(packColumns);			
		
        Grid<BeanObject> packGrid = new EditorGrid<BeanObject>(packStore, packCm);
        packGrid.setLoadMask(true);
        packGrid.setBorders(true);
        packGrid.setSize(750, 150);
        
        ActionCellRenderer packRender = new ActionCellRenderer(packGrid);
        ActionCellRenderer.ActionInfo packAct = null;
        packAct = new ActionCellRenderer.ActionInfo();
        packAct.setImage("yes.gif");
		packAct.setAction("choosePack($id)");
		packRender.addAction(packAct);
              
        packActcol.setRenderer(packRender);
		
        final ContentPanel selectPackPanel = new ContentPanel();
        selectPackPanel.setFrame(true);
        selectPackPanel.setCollapsible(true);
        selectPackPanel.setAnimCollapse(false);
        selectPackPanel.setSize(750, 150);
        selectPackPanel.setButtonAlign(HorizontalAlignment.CENTER);
        selectPackPanel.setLayout(new FitLayout());
        selectPackPanel.setHeading("选择包装");
        selectPackPanel.add(packGrid);
        
        cardStore = new ListStore<BeanObject>();
        
        new ListService().listBeans(ModelNames.CARD, criteria, new ListService.Listener(){

			public void onSuccess(List<BeanObject> beans)
			{
				for(int i=0; i<beans.size(); i++)
				{
					cardStore.add(beans.get(i));
				}
			}
		});
        
        List<ColumnConfig> cardColumns = new ArrayList<ColumnConfig>();
        cardColumns.add(new ColumnConfig(ICard.NAME, "名称", 80));
        cardColumns.add(new ColumnConfig(ICard.FEE, "贺卡费", 104));
        cardColumns.add(new ColumnConfig(ICard.FREEMONEY, "免费额度", 80)); 	
        ColumnConfig carActcol = new ColumnConfig("Action", "选 取", 100);
        cardColumns.add(carActcol);
        
		ColumnModel cardCm = new ColumnModel(cardColumns);	
        Grid<BeanObject> cardGrid = new EditorGrid<BeanObject>(cardStore, cardCm);
        cardGrid.setLoadMask(true);
        cardGrid.setBorders(true);
        cardGrid.setSize(750, 150);
		
        ActionCellRenderer cardRender = new ActionCellRenderer(cardGrid);
        ActionCellRenderer.ActionInfo cardAct = null;
        cardAct = new ActionCellRenderer.ActionInfo();
        cardAct.setImage("yes.gif");
        cardAct.setAction("chooseCard($id)");
        cardRender.addAction(cardAct);
		carActcol.setRenderer(cardRender);
        
        final ContentPanel selectCardPanel = new ContentPanel();
        selectCardPanel.setFrame(true);
        selectCardPanel.setCollapsible(true);
        selectCardPanel.setAnimCollapse(false);
        selectCardPanel.setSize(750, 150);
        selectCardPanel.setButtonAlign(HorizontalAlignment.CENTER);
        selectCardPanel.setLayout(new FitLayout());
        selectCardPanel.setHeading("选择贺卡");
        selectCardPanel.add(cardGrid);
        
        /**
         * Set other information
         */
        ContentPanel otherInformation = new ContentPanel();
        contentPanel = new ColumnPanel();
        TextBox cardMessageBox = new TextBox();
        cardMessageBox.setSize("500px", "50px");
        cardMessageBox.setEnabled(true);
        contentPanel.createPanel(IOrder.CARDMESSAGE, "贺卡祝福语:", cardMessageBox);
        TextBox invoiceType = new TextBox();
        invoiceType.setWidth("300px");
        invoiceType.setEnabled(true);
        contentPanel.createPanel(IOrder.INVOICETYPE, "发票类型:", invoiceType);
        TextBox invoiceTitle = new TextBox();
        invoiceTitle.setWidth("300px");
        invoiceTitle.setEnabled(true);
        contentPanel.createPanel(IOrder.INVOICENO, "发票抬头:", invoiceTitle);
        TextBox invoiceContent = new TextBox();
        invoiceContent.setWidth("300px");
        invoiceContent.setEnabled(true);
        contentPanel.createPanel(IOrder.INVOICECONTENT, "发票内容:", invoiceContent);
        TextBox toSeller = new TextBox();
        toSeller.setSize("500px", "50px");
        toSeller.setEnabled(true);
        contentPanel.createPanel("toSeller", "客户给商家的留言:", toSeller);
        TextBox howOos = new TextBox();
        howOos.setWidth("300px");
        howOos.setEnabled(true);
        contentPanel.createPanel(IOrder.HOWSURPLUS, "缺货处理:", howOos);
        TextBox toBuyer = new TextBox();
        toBuyer.setSize("500px", "50px");
        toBuyer.setEnabled(true);
        contentPanel.createPanel(IOrder.TOBUYER, "商家给客户的留言:", toBuyer);
        
        otherInformation.setFrame(true);
        otherInformation.setCollapsible(true);
        otherInformation.setButtonAlign(HorizontalAlignment.CENTER);
        otherInformation.setIconStyle("icon-table");
        otherInformation.setButtonAlign(HorizontalAlignment.CENTER);      
        
        otherInformation.add(contentPanel);
        
        totalPanel.add(selectPackPanel);
        totalPanel.add(selectCardPanel);
        totalPanel.add(otherInformation);
        
        this.setHeading("设置其它信息");
        this.setSize(780, 750);
        this.add(totalPanel);
	}
	
	public boolean valiatePanelInfor()
	{
		if(pack == null)
		{
			Info.display("Error", "Pelase select a pack for your goods");
			return false;
		}
		
		if(card == null)
		{
			Info.display("Error", "Pelase select a card for your deal");
			return false;
		}
		
		orderInfoMap = contentPanel.getValues();
		
		return true;
	}
	
	private void choosePack(String id)
	{
		int lineIndex = -1;
		
		for(int i=0; i<packStore.getCount(); i++)
		{
			BeanObject thePack = packStore.getAt(i);
			if(thePack.getString(IPack.ID).equals(id))
			{
				pack = thePack;
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
                      packStore.removeAll();
                  }
		        }
		    };
			MessageBox.confirm("Confirm", "Are you sure you want to choose this Pack: " 
					+ pack.getString(IPack.NAME) + " ?",
					deleteListener);
		}
		else
		{
			Info.display("Error", "Cannot find the packObject you selected.");
		}
	}
	
	private void chooseCard(String id)
	{
		int lineIndex = -1;
		
		for(int i=0; i<cardStore.getCount(); i++)
		{
			BeanObject theCard = cardStore.getAt(i);
			if(theCard.getString(IPack.ID).equals(id))
			{
				card = theCard;
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
						cardStore.removeAll();
					}

				}
			};
			MessageBox.confirm("Confirm", "Are you sure you want to choose this Card: " 
					+ card.getString(ICard.NAME) + " ?",
					deleteListener);
		}
		else
		{
			Info.display("Error", "Cannot find the cardObject you selected.");
		}
	}
	
	public BeanObject getPack() 
	{
		return pack;
	}

	public BeanObject getCard() 
	{
		return card;
	}

	public Map<String, Object> getOrderInfoMap() {
		return orderInfoMap;
	}

}
