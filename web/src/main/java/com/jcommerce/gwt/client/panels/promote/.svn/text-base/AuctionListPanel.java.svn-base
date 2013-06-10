package com.jcommerce.gwt.client.panels.promote;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAuction;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class AuctionListPanel extends ContentWidget{

    private PagingToolBar toolBar;    
    private ListStore<BeanObject> store;
    private Grid<BeanObject> grid;
    Criteria criteria = new Criteria();
	ColumnPanel contentPanel = new ColumnPanel();  
    TextBox txtUserName = new TextBox();
	Button btnSearch = new Button("搜索");
    
	public static class State extends PageState {
		public String getPageClassName() {
			return AuctionListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "拍卖活动列表 ";
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
        return "拍卖活动列表 ";
    }
    
    public AuctionListPanel() {
    	initJS(this);
    }
    
    public String getButtonText() {
        return "添加拍卖活动";
    }
    
    protected void buttonClicked() {
    	NewAuctionPanel.State state = new NewAuctionPanel.State();
        state.execute();
    }
    
    private native void initJS(AuctionListPanel me) /*-{
    $wnd.searchAuction = function (id) {
        me.@com.jcommerce.gwt.client.panels.promote.AuctionListPanel::searchAuctionAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.editAuction = function (id) {
	    me.@com.jcommerce.gwt.client.panels.promote.AuctionListPanel::editAuctionAndRefrsh(Ljava/lang/String;)(id);
	};
	$wnd.deleteAuction = function (id) {
	    me.@com.jcommerce.gwt.client.panels.promote.AuctionListPanel::deleteAuctionAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
	private void searchAuctionAndRefrsh(final String id) {
		new ListService().listBeans(ModelNames.AUCTION, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject Auction = it.next();                    
                    if(Auction.getString(IAuction.ID).trim().equals(id.trim())){
                    	SearchAuctionPanel.State state = new SearchAuctionPanel.State();
                        state.setAuction(Auction);
                        state.execute();
                    }
                }               
            }
        });
	}

	private void editAuctionAndRefrsh(final String id) {
		 new ListService().listBeans(ModelNames.AUCTION, new ListService.Listener() {
	            public void onSuccess(List<BeanObject> beans) {
	                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
	                    BeanObject Auction = it.next();                    
	                    if(Auction.getString(IAuction.ID).trim().equals(id.trim())){
	                    	ModifyAuctionPanel.State state = new ModifyAuctionPanel.State();
	                        state.setAuction(Auction);
	                        state.execute();
	                    }
	                }               
	            }
	        });
	}

	private void deleteAuctionAndRefrsh(final String id) {
		new DeleteService().deleteBean(ModelNames.AUCTION, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						AuctionListPanel.State state = new AuctionListPanel.State();
					    state.execute();
					    refresh();
					}
				});
	}

    protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("活动名称"));
		header.add(txtUserName);
		header.add(btnSearch);
		add(header);
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.AUCTION, criteria);
		loader.load(0, 10);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
        columns.add(new ColumnConfig("number", "编号", 50));
        columns.add(new ColumnConfig(IAuction.AUCTION_NAME, "拍卖活动名称", 150));
        columns.add(new ColumnConfig(IAuction.GOOD_NAME, "商品名称", 150));
        columns.add(new ColumnConfig(IAuction.START_TIME, "活动开始时间", 150));
        columns.add(new ColumnConfig(IAuction.END_TIME, "活动结束时间", 150));
        columns.add(new ColumnConfig(IAuction.START_PRICE, "起拍价", 100));
        columns.add(new ColumnConfig(IAuction.DIR_PRICE, "一口价", 100));
        ColumnConfig action = new ColumnConfig("", "操作", 100);
        columns.add(action);
        
        ColumnModel cm = new ColumnModel(columns);
        grid = new Grid<BeanObject>(store, cm);
        
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);  
        grid.addPlugin(smRowSelection);
        grid.setHeight(350);
        
        ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setText("查看");
		act.setTooltip("查看活动详情");
		act.setAction("searchAuction($id)");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setAction("editAuction($id)");
		act.setText("编辑");
		act.setTooltip("编辑");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setAction("deleteAuction($id)");
		act.setText("删除");
		act.setTooltip("删除");
		render.addAction(act);
        action.setRenderer(render);
        
        ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setBottomComponent(toolBar);
        
        btnSearch.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				List<BeanObject> newBeans = new ArrayList<BeanObject>();
				for(BeanObject bean : store.getModels()) {
					if(bean.getString(IAuction.AUCTION_NAME).equals(txtUserName.getText())) {
						newBeans.add(bean);
					}
				}
				if(newBeans.size() > 0) {
					store.removeAll();
					store.add(newBeans);
					store.commitChanges();
				}
				
			}});
		
		contentPanel.add(panel);
		add(contentPanel);
    }
    
    public void refresh() {
		toolBar.refresh();
	}
}
