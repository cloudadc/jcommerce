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
import com.jcommerce.gwt.client.model.ISnatch;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class SnatchListPanel extends ContentWidget{

    private PagingToolBar toolBar;    
    private ListStore<BeanObject> store;
    private Grid<BeanObject> grid;
    Criteria criteria = new Criteria();
	ColumnPanel contentPanel = new ColumnPanel();  
    TextBox txtUserName = new TextBox();
	Button btnSearch = new Button("搜索");
    
	public static class State extends PageState {
		public String getPageClassName() {
			return SnatchListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "夺宝奇兵 ";
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
        return "夺宝奇兵 ";
    }
    
    public SnatchListPanel() {
    	initJS(this);
    }
    
    public String getButtonText() {
        return "添加批发方案";
    }
    
    protected void buttonClicked() {
    	NewSnatchPanel.State state = new NewSnatchPanel.State();
        state.execute();
    }
    
    private native void initJS(SnatchListPanel me) /*-{
    $wnd.searchSnatch = function (id) {
        me.@com.jcommerce.gwt.client.panels.promote.SnatchListPanel::searchSnatchAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.editSnatch = function (id) {
	    me.@com.jcommerce.gwt.client.panels.promote.SnatchListPanel::editSnatchAndRefrsh(Ljava/lang/String;)(id);
	};
	$wnd.deleteSnatch = function (id) {
	    me.@com.jcommerce.gwt.client.panels.promote.SnatchListPanel::deleteSnatchAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
	private void searchSnatchAndRefrsh(final String id) {
		new ListService().listBeans(ModelNames.SNATCH, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject snatch = it.next();                    
                    if(snatch.getString(ISnatch.ID).trim().equals(id.trim())){
                    	SearchSnatchPanel.State state = new SearchSnatchPanel.State();
                        state.setSnatch(snatch);
                        state.execute();
                    }
                }               
            }
        });
	}

	private void editSnatchAndRefrsh(final String id) {
		 new ListService().listBeans(ModelNames.SNATCH, new ListService.Listener() {
	            public void onSuccess(List<BeanObject> beans) {
	                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
	                    BeanObject snatch = it.next();                    
	                    if(snatch.getString(ISnatch.ID).trim().equals(id.trim())){
	                    	ModifySnatchPanel.State state = new ModifySnatchPanel.State();
	                        state.setSnatch(snatch);
	                        state.execute();
	                    }
	                }               
	            }
	        });
	}

	private void deleteSnatchAndRefrsh(final String id) {
		new DeleteService().deleteBean(ModelNames.SNATCH, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						SnatchListPanel.State state = new SnatchListPanel.State();
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
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.SNATCH, criteria);
		loader.load(0, 10);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
        columns.add(new ColumnConfig("number", "编号", 50));
        columns.add(new ColumnConfig(ISnatch.SNATCH_NAME, "活动名称", 150));
        columns.add(new ColumnConfig(ISnatch.GOOD_NAME, "商品名称", 150));
        columns.add(new ColumnConfig(ISnatch.START_TIME, "活动开始时间", 150));
        columns.add(new ColumnConfig(ISnatch.END_TIME, "活动结束时间", 150));
        columns.add(new ColumnConfig(ISnatch.MIN_PRICE, "价格下限", 100));
        columns.add(new ColumnConfig(ISnatch.SCORE_CONSUM, "消耗积分", 100));
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
		act.setAction("searchSnatch($id)");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setAction("editSnatch($id)");
		act.setText("编辑");
		act.setTooltip("编辑");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setAction("deleteSnatch($id)");
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
					if(bean.getString(ISnatch.SNATCH_NAME).equals(txtUserName.getText())) {
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
