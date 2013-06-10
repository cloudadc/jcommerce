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
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
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
import com.jcommerce.gwt.client.model.IWholesale;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class WholesaleListPanel extends ContentWidget {

	private ColumnPanel contentPanel = new ColumnPanel();
	private Criteria criteria = new Criteria();
    private PagingToolBar toolBar;    
    private ListStore<BeanObject> store;
    private Grid<BeanObject> grid;
    TextBox txtUserName = new TextBox();
	Button btnSearch = new Button("搜索");
    
	public static class State extends PageState {
		public String getPageClassName() {
			return WholesaleListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "批发管理";
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
        return "批发方案列表";
    }
    
    public String getButtonText() {
        return "添加批发方案";
    }
    
    protected void buttonClicked() {
    	NewWholesalePanel.State state = new NewWholesalePanel.State();
        state.execute();
    }
    
    public WholesaleListPanel() {
    	initJS(this); 
    }
    
    private native void initJS(WholesaleListPanel me) /*-{
    $wnd.changeWholesale = function (id) {
        me.@com.jcommerce.gwt.client.panels.promote.WholesaleListPanel::modifyWholesaleAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.deleteWholesale = function (id) {
	    me.@com.jcommerce.gwt.client.panels.promote.WholesaleListPanel::deleteWholesaleAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
    private void modifyWholesaleAndRefrsh(final String id) {    	
        new ListService().listBeans(ModelNames.WHOLESALE, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject wholesale = it.next();                    
                    if(wholesale.getString(IWholesale.ID).trim().equals(id.trim())){
                    	ModifyWholesalePanel.State state = new ModifyWholesalePanel.State();
                        state.setWholesale(wholesale);
                        state.execute();
                    }
                }               
            }
        });        
    }
    
    private void deleteWholesaleAndRefrsh(final String id) {
        new DeleteService().deleteBean(ModelNames.WHOLESALE, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						WholesaleListPanel.State state = new WholesaleListPanel.State();
					    state.execute();
					    refresh();
					}
				});
    }
    

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("商品名称"));
		header.add(txtUserName);
		header.add(btnSearch);
		add(header);
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.WHOLESALE, criteria);
		loader.load(0, 10);
		store = new ListStore<BeanObject>(loader);
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();   
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		ColumnConfig number = new ColumnConfig("number", "编号", 100);
        columns.add(number);
        columns.add(new ColumnConfig(IWholesale.GOODS_NAME, "商品名称", 200));
        columns.add(new ColumnConfig(IWholesale.RANK_IDS, "适用会员等级", 200));
        CheckColumnConfig enable = new CheckColumnConfig(IWholesale.ENABLED, "启用", 100);
        columns.add(enable);
//        enable.addListener(new EventType(), new Listener(){
//			public void handleEvent(BaseEvent be) {
//				store.commitChanges();
//			}});
//        enable.addListener(eventType, listener)
        ColumnConfig action = new ColumnConfig("", "操作", 100);
        columns.add(action);
        
        ColumnModel cm = new ColumnModel(columns);
        grid = new Grid<BeanObject>(store, cm);
        
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);  
        grid.addPlugin(smRowSelection);
        grid.addPlugin(enable);
        
        ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_edit.gif");
		act.setAction("changeWholesale($id)");
		act.setTooltip("编辑");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage("icon_trash.gif");
		act.setAction("deleteWholesale($id)");
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
        panel.setSize(850, 350);
        panel.setBottomComponent(toolBar);
        
        btnSearch.addSelectionListener(new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				List<BeanObject> newBeans = new ArrayList<BeanObject>();
				for(BeanObject bean : store.getModels()) {
					if(bean.getString(IWholesale.GOODS_NAME).equals(txtUserName.getText())) {
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
