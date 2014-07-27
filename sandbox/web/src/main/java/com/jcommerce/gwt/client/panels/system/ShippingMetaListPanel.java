package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
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
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.RemoteService;

public class ShippingMetaListPanel extends ContentWidget {
    public static interface Constants {        
    	String ShippingMetaList_MenuName();
        String ShippingMetaList_ID();
        String ShippingMetaList_NAME();
        String ShippingMetaList_INSURE();
        String ShippingMetaList_DESC();
        String ShippingMetaList_COD();
        String ShippingMetaList_AUTHOR();
        String ShippingMetaList_WEBSITE();
        String ShippingMetaList_VERSION();
        String ShippingMetaList_ACTION();
        String ShippingMetaList_INSTALL();
        String ShippingMetaList_UNINSTALL();
        String ShippingMetaList_SHIPPINGAREA();
        String ShippingMetaList_EDITTEMPLATE();
    }
    
    public static class State extends PageState {
        public String getPageClassName() {
            return ShippingMetaListPanel.class.getName();
        }
		public String getMenuDisplayName() {
			return Resources.constants.ShippingMetaList_MenuName();
		}
    }
	@Override
	protected PageState getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}
    private static ShippingMetaListPanel instance;
    public static ShippingMetaListPanel getInstance() {
        if(instance == null) {
            instance = new ShippingMetaListPanel();
        }
        return instance;
    }
    
    private ShippingMetaListPanel() {
        super();
        initJS(this);
    }
    
    private native void initJS(ShippingMetaListPanel me) /*-{
    $wnd.installShipping = function (code) {
        me.@com.jcommerce.gwt.client.panels.system.ShippingMetaListPanel::installShipping(Ljava/lang/String;)(code);
    };
    $wnd.editTemplate = function (id) {
        me.@com.jcommerce.gwt.client.panels.system.ShippingMetaListPanel::editTemplate(Ljava/lang/String;)(id);
    };
    $wnd.uninstallShipping = function (id) {
        me.@com.jcommerce.gwt.client.panels.system.ShippingMetaListPanel::uninstallShipping(Ljava/lang/String;)(id);
    };
    $wnd.shippingArea = function (id) {
        me.@com.jcommerce.gwt.client.panels.system.ShippingMetaListPanel::shippingArea(Ljava/lang/String;)(id);
    };
    }-*/;
    
	@Override
	public String getName() {
		return Resources.constants.ShippingMetaList_MenuName();
	}
	ListLoader<ListLoadResult<ShippingConfigMetaForm>> loader = null;
	
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
        RpcProxy<ListLoadResult<ShippingConfigMetaForm>> proxy = new RpcProxy<ListLoadResult<ShippingConfigMetaForm>>() {
            public void load(Object loadConfig, AsyncCallback<ListLoadResult<ShippingConfigMetaForm>> callback) {
                RemoteService.getSpecialService().getCombinedShippingMetaList((ListLoadConfig) loadConfig, callback);
            }
        };
        loader = new BaseListLoader<ListLoadResult<ShippingConfigMetaForm>>(proxy);
        loader.setRemoteSort(true);
        
        final ListStore<ShippingConfigMetaForm> store = new ListStore<ShippingConfigMetaForm>(loader);

        store.addStoreListener(new StoreListener<ShippingConfigMetaForm>() {
            public void storeUpdate(StoreEvent<ShippingConfigMetaForm> se) {
                List<Record> changed = store.getModifiedRecords();
            }
        });
        
        
        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
        final CheckBoxSelectionModel<ShippingConfigMetaForm> smRowSelection = new CheckBoxSelectionModel<ShippingConfigMetaForm>();
        columns.add(smRowSelection.getColumn());
//        columns.add(new ColumnConfig(ShippingConfigMetaForm.ID, Resources.constants
//                .ShippingMetaList_ID(), 50));
        ColumnConfig col = new ColumnConfig(ShippingConfigMetaForm.NAME, Resources.constants
                .ShippingMetaList_NAME(), 100);
        col.setEditor(new CellEditor(new TextField<String>()));
        columns.add(col);
        columns.add(new ColumnConfig(ShippingConfigMetaForm.DESCRIPTION, Resources.constants
                .ShippingMetaList_DESC(), 200));
        columns.add(new ColumnConfig(ShippingConfigMetaForm.INSURE, Resources.constants
                .ShippingMetaList_INSURE(), 100));
        columns.add(new ColumnConfig(ShippingConfigMetaForm.SUPPORTCOD, Resources.constants
                .ShippingMetaList_COD(), 100));
        columns.add(new ColumnConfig(ShippingConfigMetaForm.VERSION, Resources.constants
                .ShippingMetaList_VERSION(), 100));
        columns.add(new ColumnConfig(ShippingConfigMetaForm.AUTHOR, Resources.constants
                .ShippingMetaList_AUTHOR(), 100));        
        ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
                .ShippingMetaList_ACTION(), 200);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        Grid<ShippingConfigMetaForm> grid = new EditorGrid<ShippingConfigMetaForm>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);
        grid.setAutoExpandColumn(ShippingConfigMetaForm.NAME);
        
        ActionCellRenderer render = new ActionCellRenderer(grid);
        actcol.setRenderer(render);
        
        final ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        //        panel.setHeading("Paging Grid");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setHeight(500);
        panel.setWidth("100%");
//        panel.setBottomComponent(toolBar);
        
        add(panel);

        Window.addResizeHandler(new ResizeHandler() {
            public void onResize(ResizeEvent event) {
                int w = event.getWidth() - 300;
                panel.setWidth(w + "px");
            }
        });
    }
    
    
    class ActionCellRenderer implements GridCellRenderer<ShippingConfigMetaForm> {
        public ActionCellRenderer(Grid grid) {
        }

        public String render(ShippingConfigMetaForm model, String property, ColumnData config,
                final int rowIndex, final int colIndex, ListStore<ShippingConfigMetaForm> store, Grid<ShippingConfigMetaForm> grid) {
            try {
            Boolean install = model.getInstall();
            System.out.println("install: "+install);
            String code = model.getShippingCode();
            System.out.println("code: "+code);
            String id = model.getID();
            if(id==null) {
                id = "";
            }
            System.out.println("id: "+id);
            
            StringBuffer sb = new StringBuffer();
            
            if(install) {
                sb.append("<a href=\"javascript:uninstallShipping('");
                sb.append(id);
                sb.append("');\">").append(Resources.constants.ShippingMetaList_UNINSTALL()).append("</a>&nbsp;");            	
                sb.append("<a href=\"javascript:shippingArea('");
                sb.append(id);
                sb.append("');\">").append(Resources.constants.ShippingMetaList_SHIPPINGAREA()).append("</a>&nbsp;");
                sb.append("<a href=\"javascript:editTemplate('");
                sb.append(id);
                sb.append("');\">").append(Resources.constants.ShippingMetaList_EDITTEMPLATE()).append("</a>&nbsp;");                
            }
            else {
                sb.append("<a href=\"javascript:installShipping('");
                sb.append(code);
                sb.append("');\">").append(Resources.constants.ShippingMetaList_INSTALL()).append("</a>");
            }
           
            return sb.toString();
            } catch (Exception e) {
            	e.printStackTrace();
            	throw new RuntimeException(e);
            }
        }


    }
    public void refresh() {
        loader.load();
    }
    private void installShipping(String code) {
        System.out.println("installShipping: "+code);
        RemoteService.getSpecialService().installShipping(code, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                log("error in install shipping: "+caught.getMessage());
            }

            public void onSuccess(Boolean result) {
                if(result) {
                    refresh();
                }
                else {
                	Info.display("oops", "install failed!!!");
                }
            }
        });
    }
    private void uninstallShipping(String id) {
        System.out.println("uninstallShipping: "+id);
        RemoteService.getSpecialService().uninstallShipping(id, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                log("error in uninstall shipping: "+caught.getMessage());
            }

            public void onSuccess(Boolean result) {
                if(result) {
                    refresh();
                }
                else {
                    Info.display("oops", "uninstall failed!!!");
                }
            }
        });
    }
    private void editTemplate(String id){
        System.out.println("editTemplate, id: "+id);
        
        ShippingTemplatePanel.State newState = new ShippingTemplatePanel.State();
        newState.setId(id);
        newState.setIsEdit(true);
        newState.execute();
        
    }
    private void shippingArea(String id) {
    	System.out.println("shippingArea, id: "+id);
    	ShippingAreaListPanel.State newState = new ShippingAreaListPanel.State();
    	newState.setShippingId(id);
    	newState.execute();
    	
    	
    }
    
}
