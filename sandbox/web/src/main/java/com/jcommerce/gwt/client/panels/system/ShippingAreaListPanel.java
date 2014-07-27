package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
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
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.panels.goods.NewBrand;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class ShippingAreaListPanel extends ContentWidget {
    PagingToolBar toolBar;    

    public static interface Constants {        

    	String ShippingAreaList_NAME();
    	String ShippingAreaList_REGION_NAMES();
    	String ShippingAreaList_ACTION();
    }
	public static class State extends PageState {
		static final String SHIPPING_ID = "ShippingId";
        public String getPageClassName() {
            return ShippingAreaListPanel.class.getName();
        }
        public void setShippingId(String shippingId) {
        	setValue(SHIPPING_ID, shippingId);
        }
        public String getShippingId() {
        	return (String)getValue(SHIPPING_ID);
        }
        
        public String getMenuDisplayName() {
            return "配送区域";
        }
    }

	@Override
	protected State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
    
    private ShippingAreaListPanel() {
        super();
        initJS(this);
    }

    public static ShippingAreaListPanel getInstance() {
        return new ShippingAreaListPanel();
    }

    @Override
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    @Override
    public String getName() {
        return "配送区域";
    }
    
    public String getButtonText() {
        return "新建配送区域";
    }
    
    protected void buttonClicked() {
        ShippingAreaPanel.State newState = new ShippingAreaPanel.State();
        newState.setIsEdit(false);
        newState.setShippingId(getCurState().getShippingId());
        newState.execute();
    }
    
    private native void initJS(ShippingAreaListPanel me) /*-{
       $wnd.editShippingArea = function (id) {
           me.@com.jcommerce.gwt.client.panels.system.ShippingAreaListPanel::editShippingArea(Ljava/lang/String;)(id);
       };
       $wnd.deleteShippingArea = function (id) {
           me.@com.jcommerce.gwt.client.panels.system.ShippingAreaListPanel::deleteShippingArea(Ljava/lang/String;)(id);
       };

       }-*/;

    private void editShippingArea(String shippingAreaId) {
        System.out.println("editShippingArea: "+shippingAreaId);
        ShippingAreaPanel.State newState = new ShippingAreaPanel.State();
        newState.setShippingId(getCurState().getShippingId());
        newState.setIsEdit(true);
        newState.setId(shippingAreaId);
        newState.execute();
    }
    private void deleteShippingArea(String id) {
        System.out.println("deleteShippingArea: "+id);
        new DeleteService().deleteBean(ModelNames.SHIPPING_AREA, id, new DeleteService.Listener() {
            public void onFailure(Throwable caught) {
                log("error in deleteShippingArea : "+caught.getMessage());
            }
            public void onSuccess(Boolean result) {
                if(result) {
                    refresh();
                }
                else {
                    Info.display("oops", "deleteShippingArea failed!!!");
                }
            }
        });
    }
    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        // loader
        
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IShippingArea.SHIPPING, Condition.EQUALS, getCurState().getShippingId()));
        BasePagingLoader loader = new PagingListService().getLoader(ModelNames.SHIPPING_AREA, criteria);

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
        final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
        columns.add(smRowSelection.getColumn());
        ColumnConfig col = new ColumnConfig(IShippingArea.NAME, Resources.constants
                .ShippingAreaList_NAME(), 150);
        col.setEditor(new CellEditor(new TextField<String>()));
        columns.add(col);
//        columns.add(new ColumnConfig(IShippingArea.REGIONS, Resources.constants
//                .ShippingAreaList_REGION_NAMES(), 150));

        ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
                .ShippingAreaList_ACTION(), 100);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        //grid.setSelectionModel(sm);
//        grid.setAutoExpandColumn("forum");

        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();        
        act.setText(Resources.constants.edit());
        act.setAction("editShippingArea($id)");
        act.setTooltip(Resources.constants.GoodsList_action_edit());
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(Resources.constants.delete());
        act.setAction("deleteShippingArea($id)");
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
        panel.setSize(750, 350);
        panel.setBottomComponent(toolBar);     
                
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.addButton(new com.extjs.gxt.ui.client.widget.button.Button(Resources.constants.ShippingMetaList_MenuName(), new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                ShippingMetaListPanel.State state = new ShippingMetaListPanel.State();
                state.execute();
            }
        }));
      
        Window.addResizeHandler(new ResizeHandler() {
            public void onResize(ResizeEvent event) {
                int w = event.getWidth() - 300;
                panel.setWidth(w + "px");
            }
        });
      
        add(panel);        
    }
    
	@Override 
	public void refresh() {
	    toolBar.refresh();
	}
}
