/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.client.panels.promote;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IPack;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class PackListPanel extends ContentWidget {    
	ColumnPanel contentPanel = new ColumnPanel();
    PagingToolBar toolBar;    
    
	public static class State extends PageState {
		public String getPageClassName() {
			return PackListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "商品包装";
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
        return "商品包装";
    }
    
    /**
     * Initialize this example.
     */
    public PackListPanel() {
        add(contentPanel);        
        initJS(this);         
    }
    
    public String getButtonText() {
        return "添加商品包装";
    }
    
    protected void buttonClicked() {
        NewPackPanel.State state = new NewPackPanel.State();
        state.execute();
    }
    
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);

        BasePagingLoader loader = new PagingListService().getLoader(ModelNames.PACK);

        loader.load(0, 50);
    	
        final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

        store.addStoreListener(new StoreListener<BeanObject>() {
            public void storeUpdate(StoreEvent<BeanObject> se) {
                List<Record> changed = store.getModifiedRecords();
            }
        });
        
        toolBar = new PagingToolBar(50);
        toolBar.bind(loader);

        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
        //CheckBoxSelectionModel<BeanObject> sm = new CheckBoxSelectionModel<BeanObject>();
        //columns.add(sm.getColumn());        
        columns.add(new ColumnConfig(IPack.NAME, "商品包装名称", 100));
        columns.add(new ColumnConfig(IPack.FEE, "费用", 80));
        columns.add(new ColumnConfig(IPack.FREEMONEY, "免费额度", 80));
        columns.add(new ColumnConfig(IPack.DESC, "包装描述", 300));
        ColumnConfig actcol = new ColumnConfig("Action", "操作", 100);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        //grid.setSelectionModel(sm);
//        grid.setAutoExpandColumn("forum");

        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();        
        act.setText("编辑 ");
        act.setAction("changePack($id)");
        act.setTooltip(Resources.constants.GoodsList_action_edit());
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(" 删除");
		act.setAction("deletePack($id)");
		act.setTooltip(Resources.constants.GoodsList_action_delete());
		render.addAction(act);
        actcol.setRenderer(render);        
        
        ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setHeading("Paging Grid");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setSize(750, 350);
        panel.setBottomComponent(toolBar);     
        
//        panel.setButtonAlign(HorizontalAlignment.CENTER);
//        panel.addButton(new com.extjs.gxt.ui.client.widget.button.Button("添加品牌", new SelectionListener<ButtonEvent>() {
//          public void componentSelected(ButtonEvent ce) {
//              NewBrand.State state = new NewBrand.State();
//              state.execute();
//          }
//        }));
        
        add(panel);       
    }    
    
    private native void initJS(PackListPanel me) /*-{
    $wnd.changePack = function (id) {
        me.@com.jcommerce.gwt.client.panels.promote.PackListPanel::modifyPackAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.deletePack = function (id) {
	    me.@com.jcommerce.gwt.client.panels.promote.PackListPanel::deletePackAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
    private void modifyPackAndRefrsh(final String id) {    	
        new ListService().listBeans(ModelNames.PACK, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject pack = it.next();                    
                    if(pack.getString(IPack.ID).trim().equals(id.trim())){
                        NewPackPanel.State state = new NewPackPanel.State();
                        state.setPack(pack);
                        state.execute();
                    }
                }               
            }
        });        
    }
    
    private void deletePackAndRefrsh(final String id) {
        new DeleteService().deleteBean(ModelNames.PACK, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
					    PackListPanel.State state = new PackListPanel.State();
					    state.execute();
					}
				});
    }
    
    public void refresh(){
    	toolBar.refresh();
    }
}
