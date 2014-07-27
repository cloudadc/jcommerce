package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;

public class PaymentMetaList extends ContentWidget {
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
            return PaymentMetaList.class.getName();
        }
		public String getMenuDisplayName() {
			return Resources.constants.PaymentMetaList_MenuName();
		}
    }

    private static PaymentMetaList instance;
    public static PaymentMetaList getInstance() {
        if(instance == null) {
            instance = new PaymentMetaList();
        }
        return instance;
    }
    
    private PaymentMetaList() {
        super();
        initJS(this);
    }

    private native void initJS(PaymentMetaList me) /*-{
       $wnd.installPayment = function (code) {
           me.@com.jcommerce.gwt.client.panels.system.PaymentMetaList::installPayment(Ljava/lang/String;)(code);
       };
       $wnd.editPayment = function (id) {
           me.@com.jcommerce.gwt.client.panels.system.PaymentMetaList::editPayment(I)(id);
       };
       $wnd.uninstallPayment = function (id) {
           me.@com.jcommerce.gwt.client.panels.system.PaymentMetaList::uninstallPayment(I)(id);
       };
       }-*/;
    
    private void installPayment(String code) {
        System.out.println("installPayment: "+code);
        getService().installPayment(code, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                GWT.log("error in install payment", caught);
                System.out.println("my msg: "+caught.getMessage());
                Window.alert(caught.getMessage());
            }

            public void onSuccess(Boolean result) {
                if(result) {
                    refresh();
                }
                else {
                    Window.alert("install failed!!!");
                }
            }
        });
    }
    private void uninstallPayment(int id) {
        System.out.println("uninstallPayment: "+id);
        getService().uninstallPayment(""+id, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                GWT.log("error in uninstall payment", caught);
                System.out.println("my msg: "+caught.getMessage());
                Window.alert(caught.getMessage());
            }

            public void onSuccess(Boolean result) {
                if(result) {
                    refresh();
                }
                else {
                    Window.alert("uninstall failed!!!");
                }
            }
        });
    }
    private void editPayment(int id){
        System.out.println("editPayment, id: "+id);
        
        PaymentMetaPanel.State newState = new PaymentMetaPanel.State();
//        newState.setID(id);
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
    ListLoader loader = null;
    int pageSize = 3;
    
    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
//        final IShopServiceAsync service = getService();
//        RpcProxy proxy = new RpcProxy() {
//            public void load(Object loadConfig, AsyncCallback callback) {
//                service.getPaymentMetaList((ListLoadConfig) loadConfig, callback);
//            }
//        };
//        loader = new MyPagingListService().getLoader(
//                ModelNames.PAYMENT_META);
        // loader
//        loader = new BaseListLoader(proxy);
        loader.setRemoteSort(true);
        
//        PagingToolBar toolBar = new PagingToolBar(pageSize);
//        toolBar.bind(loader);
        
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
                .PaymentMetaList_ID(), 50));
        ColumnConfig col = new ColumnConfig(PaymentConfigMetaForm.NAME, Resources.constants
                .PaymentMetaList_NAME(), 100);
        col.setEditor(new CellEditor(new TextField()));
        columns.add(col);
        columns.add(new ColumnConfig(PaymentConfigMetaForm.DESCRIPTION, Resources.constants
                .PaymentMetaList_DESC(), 200));
        columns.add(new ColumnConfig(PaymentConfigMetaForm.FEE, Resources.constants
                .PaymentMetaList_PAYFEE(), 50));
        columns.add(new ColumnConfig(PaymentConfigMetaForm.COD, Resources.constants
                .PaymentMetaList_ISCOD(), 50));
        columns.add(new ColumnConfig(PaymentConfigMetaForm.ORDER, Resources.constants
                .PaymentMetaList_ORDER(), 50));
        ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
                .PaymentMetaList_ACTION(), 100);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);
        
        ActionCellRenderer render = new ActionCellRenderer(grid);
        actcol.setRenderer(render);
        
        
        ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        //        panel.setHeading("Paging Grid");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setSize(900, 350);
//        panel.setBottomComponent(toolBar);
        
        add(panel);
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
        
        public Object render(BeanObject model, String property, ColumnData config,
                final int rowIndex, final int colIndex, ListStore<BeanObject> store, Grid<BeanObject> grid) {
            
            Boolean install = (Boolean)model.get(PaymentConfigMetaForm.INSTALL);
            System.out.println("install: "+install);
            String code = (String)model.get(PaymentConfigMetaForm.CODE);
            System.out.println("code: "+code);
            Object obj = model.get(PaymentConfigMetaForm.ID);
            int id = -1;
            if(obj!=null) {
                id = (Integer)obj;
            }
            System.out.println("id: "+id);
            
            StringBuffer sb = new StringBuffer();
            
            // <a href=\"javascript:installPayment('${code}');\" title=\"tip\" ><img border=\"0\" src=\"${image}\">${text}</a>
            if(install) {
                sb.append("<a href=\"javascript:editPayment(");
                sb.append(id);
                sb.append(");\">").append(Resources.constants.edit()).append("</a>&nbsp;");
                sb.append("<a href=\"javascript:uninstallPayment(");
                sb.append(id);
                sb.append(");\">").append(Resources.constants.PaymentMetaList_UNINSTALL()).append("</a>");
            }
            else {
                sb.append("<a href=\"javascript:installPayment('");
                sb.append(code);
                sb.append("');\">").append(Resources.constants.PaymentMetaList_INSTALL()).append("</a>");
            }
           
            return sb.toString();
        }
    }
}
