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
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrder;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class SetOthers extends ContentPanel{
	
	public SetOthers() {
		
	}
	
	public static ContentPanel setOthers() {
		ContentPanel totalPanel = new ContentPanel();
		
		BasePagingLoader loader = null;
		
		List<String> wantedFields = new ArrayList<String>();
    	wantedFields.add(IOrder.PACKFEE);
    	wantedFields.add(IOrder.CARDFEE);

    	loader = new PagingListService().getLoader(ModelNames.ORDER, wantedFields);
    	
    	final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		
		List<ColumnConfig> packColumns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		packColumns.add(smRowSelection.getColumn());
        packColumns.add(new ColumnConfig("packOrNot", "名称", 80));
        packColumns.add(new ColumnConfig(IOrder.PACKFEE, "包装费", 104));
        packColumns.add(new ColumnConfig("freeMoney", "免费额度", 80));
        
		ColumnModel cm1 = new ColumnModel(packColumns);			
		
        Grid<BeanObject> grid1 = new EditorGrid<BeanObject>(store, cm1);
        grid1.setLoadMask(true);
        grid1.setBorders(true);
        grid1.setSize(750, 150);
		
        final ContentPanel selectPackPanel = new ContentPanel();
        selectPackPanel.setFrame(true);
        selectPackPanel.setCollapsible(true);
        selectPackPanel.setAnimCollapse(false);
        selectPackPanel.setSize(750, 150);
        selectPackPanel.setButtonAlign(HorizontalAlignment.CENTER);
        selectPackPanel.setLayout(new FitLayout());
        selectPackPanel.setHeading("选择包装");
        selectPackPanel.add(grid1);
        
        List<ColumnConfig> cardColumns = new ArrayList<ColumnConfig>();
        cardColumns.add(smRowSelection.getColumn());
        cardColumns.add(new ColumnConfig("cardOrNot", "名称", 80));
        cardColumns.add(new ColumnConfig(IOrder.CARDFEE, "贺卡费", 104));
        cardColumns.add(new ColumnConfig("freeMoney", "免费额度", 80)); 	
        
		ColumnModel cm2 = new ColumnModel(cardColumns);	
        Grid<BeanObject> grid2 = new EditorGrid<BeanObject>(store, cm1);
        grid2.setLoadMask(true);
        grid2.setBorders(true);
        grid2.setSize(750, 150);
		
        final ContentPanel selectCardPanel = new ContentPanel();
        selectCardPanel.setFrame(true);
        selectCardPanel.setCollapsible(true);
        selectCardPanel.setAnimCollapse(false);
        selectCardPanel.setSize(750, 150);
        selectCardPanel.setButtonAlign(HorizontalAlignment.CENTER);
        selectCardPanel.setLayout(new FitLayout());
        selectCardPanel.setHeading("选择贺卡");
        selectCardPanel.add(grid2);
        
        /**
         * Set other information
         */
        ContentPanel otherInformation = new ContentPanel();
        ColumnPanel contentPanel = new ColumnPanel();
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
        contentPanel.createPanel("invoiceTitle", "发票抬头:", invoiceTitle);
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
        contentPanel.createPanel(IOrder.HOWOSS, "缺贺处理:", howOos);
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
        totalPanel.setHeading("设置其它信息");
        totalPanel.setSize(780, 750);
		return totalPanel;
	}
}
