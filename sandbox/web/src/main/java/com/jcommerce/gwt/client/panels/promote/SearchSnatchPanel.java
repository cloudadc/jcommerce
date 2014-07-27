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
import com.jcommerce.gwt.client.model.ISnatch;
import com.jcommerce.gwt.client.model.ISnatchLog;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class SearchSnatchPanel extends ContentWidget {

	private ColumnPanel topPanel = new ColumnPanel();
	private PagingToolBar toolBar;    
    private ListStore<BeanObject> store;
    private Grid<BeanObject> grid;
	
	public static class State extends PageState {
		
		private BeanObject snatch = null;
		
		public BeanObject getSnatch() {
			return snatch;
		}
		public void setSnatch(BeanObject snatch) {
			this.snatch = snatch;
			setEditting(snatch != null);
		}
		public String getPageClassName() {
			return SearchSnatchPanel.class.getName();
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
		 inforPanel.setHeading(this.getCurState().getSnatch().getString(ISnatch.SNATCH_NAME));
		 HorizontalPanel infoContent = new HorizontalPanel();
		 infoContent.setSpacing(20);
		 Label sk = new Label("活动开始时间: ");
		 Label sv = new Label(this.getCurState().getSnatch().getString(ISnatch.START_TIME));
		 Label ek = new Label("活动结束时间: ");
		 Label ev = new Label(this.getCurState().getSnatch().getString(ISnatch.END_TIME));
		 infoContent.add(sk);
		 infoContent.add(sv);
		 infoContent.add(ek);
		 infoContent.add(ev);
		 inforPanel.add(infoContent);
		 
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.SNATCHLOG);
		loader.load(0, 10);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
        columns.add(new ColumnConfig("number", "编号", 50)); 
		columns.add(new ColumnConfig(ISnatchLog.USER_ID, "出价用户", 150));
        columns.add(new ColumnConfig(ISnatchLog.BID_TIME, "出价时间", 150));
        columns.add(new ColumnConfig(ISnatchLog.BID_PRICE, "所出价格", 150));
        
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
