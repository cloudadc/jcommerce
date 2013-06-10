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
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.RemoteService;

public class PaymentMetaListPanel extends ContentWidget {
    public static interface Constants {        
    	String PaymentMetaList_MenuName();
        String PaymentMetaList_ID();
        String PaymentMetaList_NAME();
        String PaymentMetaList_PAYFEE();
        String PaymentMetaList_DESC();
        String PaymentMetaList_ORDER();
        String PaymentMetaList_ISCOD();
        String PaymentMetaList_ACTION();
        String PaymentMetaList_INSTALL();
        String PaymentMetaList_UNINSTALL();
        
    }
    public static class State extends PageState {
        public String getPageClassName() {
            return PaymentMetaListPanel.class.getName();
        }
		public String getMenuDisplayName() {
			return Resources.constants.PaymentMetaList_MenuName(); 
		}
    }

    private static PaymentMetaListPanel instance;
    public static PaymentMetaListPanel getInstance() {
        if(instance == null) {
            instance = new PaymentMetaListPanel();
        }
        return instance;
    }
    
    private PaymentMetaListPanel() {
        super();
        initJS(this);
    }

    private native void initJS(PaymentMetaListPanel me) /*-{
       $wnd.installPayment = function (code) {
           me.@com.jcommerce.gwt.client.panels.system.PaymentMetaListPanel::installPayment(Ljava/lang/String;)(code);
       };
       $wnd.editPayment = function (id) {
           me.@com.jcommerce.gwt.client.panels.system.PaymentMetaListPanel::editPayment(Ljava/lang/String;)(id);
       };
       $wnd.uninstallPayment = function (id) {
           me.@com.jcommerce.gwt.client.panels.system.PaymentMetaListPanel::uninstallPayment(Ljava/lang/String;)(id);
       };
       }-*/;
    
    private void installPayment(String code) {
        System.out.println("installPayment: "+code);
        RemoteService.getSpecialService().installPayment(code, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                log("error in install payment: "+caught.getMessage());
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
    private void uninstallPayment(String id) {
        System.out.println("uninstallPayment: "+id);
        RemoteService.getSpecialService().uninstallPayment(id, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                log("error in uninstall payment: "+caught.getMessage());
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
    private void editPayment(String id){
        System.out.println("editPayment, id: "+id);
        
        PaymentMetaPanel.State newState = new PaymentMetaPanel.State();
        newState.setId(id);
        newState.setIsEdit(true);
        newState.execute();
        
    }
    
    
    @Override
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    @Override
    public String getName() {
        return Resources.constants.PaymentMetaList_MenuName();
    }
    ListLoader<ListLoadResult<BeanObject>> loader = null;
    
    int pageSize = 3;
    
    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
        // loader
        
        RpcProxy<ListLoadResult<BeanObject>> proxy = new RpcProxy<ListLoadResult<BeanObject>>() {
            public void load(Object loadConfig, AsyncCallback<ListLoadResult<BeanObject>> callback) {
                RemoteService.getSpecialService().getPaymentMetaList((ListLoadConfig) loadConfig, callback);
            }
        };
        loader = new BaseListLoader<ListLoadResult<BeanObject>>(proxy);
//        loader = new MyPagingListService().getLoader(
//                ModelNames.PAYMENT_META);
        loader.setRemoteSort(true);
        
        final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

        store.addStoreListener(new StoreListener<BeanObject>() {
            public void storeUpdate(StoreEvent<BeanObject> se) {
                List<Record> changed = store.getModifiedRecords();
            }
        });
        
        
        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
        final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
        columns.add(smRowSelection.getColumn());
        columns.add(new ColumnConfig(PaymentConfigMetaForm.ID, Resources.constants
                .PaymentMetaList_ID(), 100));
        ColumnConfig col = new ColumnConfig(PaymentConfigMetaForm.NAME, Resources.constants
                .PaymentMetaList_NAME(), 100);
        col.setEditor(new CellEditor(new TextField<String>()));
        columns.add(col);
        columns.add(new ColumnConfig(PaymentConfigMetaForm.DESCRIPTION, Resources.constants
                .PaymentMetaList_DESC(), 200));
        columns.add(new ColumnConfig(PaymentConfigMetaForm.FEE, Resources.constants
                .PaymentMetaList_PAYFEE(), 100));
        columns.add(new ColumnConfig(PaymentConfigMetaForm.COD, Resources.constants
                .PaymentMetaList_ISCOD(), 100));
        columns.add(new ColumnConfig(PaymentConfigMetaForm.ORDER, Resources.constants
                .PaymentMetaList_ORDER(), 100));
        ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
                .PaymentMetaList_ACTION(), 100);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);
        grid.setAutoExpandColumn(PaymentConfigMetaForm.DESCRIPTION);
        
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
    
    public void refresh() {
        System.out.println("----- refresh PaymentMetaList---");
        loader.load();
        System.out.println("----- finish PaymentMetaList---");
    }

    public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
    }

    class ActionCellRenderer implements GridCellRenderer<BeanObject> {
        public ActionCellRenderer(Grid grid) {
        }

        public String render(BeanObject model, String property, ColumnData config,
                final int rowIndex, final int colIndex, ListStore<BeanObject> store, Grid<BeanObject> grid) {
            try {
            Boolean install = new Boolean(""+model.get(PaymentConfigMetaForm.INSTALL));
            System.out.println("install: "+install);
            String code = (String)model.get(PaymentConfigMetaForm.CODE);
            System.out.println("code: "+code);
            String id = model.get(PaymentConfigMetaForm.ID);
            if(id==null) {
                id = "";
            }
            System.out.println("id: "+id);
            
            StringBuffer sb = new StringBuffer();
            
            // <a href=\"javascript:installPayment('${code}');\" title=\"tip\" ><img border=\"0\" src=\"${image}\">${text}</a>
            if(install) {
                sb.append("<a href=\"javascript:editPayment('");
                sb.append(id);
                sb.append("');\">").append(Resources.constants.edit()).append("</a>&nbsp;");
                sb.append("<a href=\"javascript:uninstallPayment('");
                sb.append(id);
                sb.append("');\">").append(Resources.constants.PaymentMetaList_UNINSTALL()).append("</a>");
            }
            else {
                sb.append("<a href=\"javascript:installPayment('");
                sb.append(code);
                sb.append("');\">").append(Resources.constants.PaymentMetaList_INSTALL()).append("</a>");
            }
           
            return sb.toString();
            } catch (Exception e) {
            	e.printStackTrace();
            	throw new RuntimeException(e);
            }
        }


    }
}
