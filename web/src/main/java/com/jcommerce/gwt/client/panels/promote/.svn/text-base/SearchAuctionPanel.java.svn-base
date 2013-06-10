package com.jcommerce.gwt.client.panels.promote;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAuction;
import com.jcommerce.gwt.client.model.IAuctionLog;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class SearchAuctionPanel extends ContentWidget {

	private ColumnPanel topPanel = new ColumnPanel();
	private PagingToolBar toolBar;    
    private ListStore<BeanObject> store;
    private Grid<BeanObject> grid;
	
	public static class State extends PageState {
		
		private BeanObject Auction = null;
		
		public BeanObject getAuction() {
			return Auction;
		}
		public void setAuction(BeanObject Auction) {
			this.Auction = Auction;
			setEditting(Auction != null);
		}
		public String getPageClassName() {
			return SearchAuctionPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "查看活动详情  ";
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
	
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
        return "查看活动详情 ";
    }
    
    protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		 ContentPanel inforPanel = new ContentPanel();
//		 inforPanel.setHeading(this.getCurState().getAuction().getString(IAuction.AUCTION_NAME));
		 HorizontalPanel infoContent = new HorizontalPanel();
		 infoContent.setSpacing(20);
		 Label sk = new Label("拍卖活动名称：");
		 Label sv = new Label(this.getCurState().getAuction().getString(IAuction.AUCTION_NAME));
		 Label ek = new Label("拍卖商品名称：");
		 Label ev = new Label(this.getCurState().getAuction().getString(IAuction.GOOD_NAME));
		 infoContent.add(sk);
		 infoContent.add(sv);
		 infoContent.add(ek);
		 infoContent.add(ev);
		 inforPanel.add(infoContent);
		 
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.AUCTIONLOG);
		loader.load(0, 10);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
        columns.add(new ColumnConfig(IAuctionLog.BID_USER, "买家", 150)); 
		columns.add(new ColumnConfig(IAuctionLog.BID_PRICE, "出价", 150));
        columns.add(new ColumnConfig(IAuctionLog.BID_TIME, "时间", 150));
        columns.add(new ColumnConfig(IAuctionLog.LOG_ID, "状态", 150));
        
        ColumnModel cm = new ColumnModel(columns);
        grid = new Grid<BeanObject>(store, cm);
        
//        grid.setLoadMask(true);
//        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);  
        grid.addPlugin(smRowSelection);
        grid.setHeight(250);
		 
		ContentPanel contentPanel = new ContentPanel();
		contentPanel.setFrame(true);
		contentPanel.setCollapsible(true);
		contentPanel.setAnimCollapse(false);
		contentPanel.setButtonAlign(HorizontalAlignment.CENTER);
		contentPanel.setIconStyle("icon-table");
		contentPanel.setLayout(new FitLayout());
		contentPanel.add(grid);
		contentPanel.setBottomComponent(toolBar);
		 
		 
		topPanel.add(inforPanel);
		topPanel.add(contentPanel);
		add(topPanel);
    }

}
