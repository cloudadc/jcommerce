
package com.jcommerce.gwt.client.panels.goods;

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
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class BrandInfo extends ContentWidget {    
    public static interface Constants {
        String GoodsBrand_title();
    }
	
	ColumnPanel contentPanel = new ColumnPanel();
    ListBox b_list = new ListBox();    
    ListBox lstAction = new ListBox();
    Button btnAct = new Button("OK");
    PagingToolBar toolBar;    
    
	public static class State extends PageState {
		public String getPageClassName() {
			return BrandInfo.class.getName();
		}
		public String getMenuDisplayName() {
			return "商品品牌";
		}
	}
	
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
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
        return "商品品牌";
    }
    
    /**
     * Initialize this example.
     */
    public BrandInfo() {
        add(contentPanel);        
        initJS(this);         
    }
    
    public String getButtonText() {
        return "添加品牌";
    }
    
    protected void buttonClicked() {
        NewBrand.State state = new NewBrand.State();
        state.execute();
    }
    
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);

        BasePagingLoader loader = new PagingListService().getLoader(ModelNames.BRAND);

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
        columns.add(new ColumnConfig(IBrand.NAME, "品牌名称", 80));
        columns.add(new ColumnConfig(IBrand.SITE, "品牌网址", 150));
        columns.add(new ColumnConfig(IBrand.DESC, "品牌描述", 230));
        columns.add(new ColumnConfig(IBrand.ORDER, "排序", 50));
        columns.add(new CheckColumnConfig(IBrand.SHOW, "是否显示", 80));
        ColumnConfig actcol = new ColumnConfig("Action", "操作", 140);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        final Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        //grid.setSelectionModel(sm);
//        grid.setAutoExpandColumn("forum");

        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();        
        act.setText("编辑 ");
        act.setAction("changeBrand($id)");
        act.setTooltip(Resources.constants.GoodsList_action_edit());
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(" 删除");
		act.setAction("deleteBrand($id)");
		act.setTooltip(Resources.constants.GoodsList_action_delete());
		render.addAction(act);
        actcol.setRenderer(render);        
        
        final ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setHeading("Paging Grid");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setSize("100%", "500");
        panel.setBottomComponent(toolBar);     
//        panel.setButtonAlign(HorizontalAlignment.CENTER);
//        panel.addButton(new com.extjs.gxt.ui.client.widget.button.Button("添加品牌", new SelectionListener<ButtonEvent>() {
//          public void componentSelected(ButtonEvent ce) {
//              NewBrand.State state = new NewBrand.State();
//              state.execute();
//          }
//        }));
        
        Window.addResizeHandler(new ResizeHandler() {
            public void onResize(ResizeEvent event) {
                int w = event.getWidth() - 300;
                panel.setWidth(w + "px");
            }
        });
        
        add(panel);       
    }    
    
    private native void initJS(BrandInfo me) /*-{
    $wnd.changeBrand = function (id) {
        me.@com.jcommerce.gwt.client.panels.goods.BrandInfo::modifyBrandAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.deleteBrand = function (id) {
	    me.@com.jcommerce.gwt.client.panels.goods.BrandInfo::deleteBrandAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
    private void modifyBrandAndRefrsh(final String id) {    	
        new ListService().listBeans(ModelNames.BRAND, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject brand = it.next();                    
                    if(brand.getString(IBrand.ID).trim().equals(id.trim())){
                        NewBrand.State state = new NewBrand.State();
                        state.setBrand(brand);
                        state.execute();
                    }
                }               
            }
            public synchronized void onFailure(Throwable caught) {
                System.out.println("list onFailure("+caught);
                
            }
        });        
    }
    
    private void deleteBrandAndRefrsh(final String id) {
        new DeleteService().deleteBean(ModelNames.BRAND, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
					    refresh();
					}
				});
    }
    
    public void refresh(){
    	toolBar.refresh();
    }
}
