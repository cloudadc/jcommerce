/**
 *@author KingZhao
 */
package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * Example file.
 */
public class GoodsTypeInfo extends ContentWidget {    
    public static interface Constants {
        String GoodsTypeList_MenuName();
        String GoodsTypeList_ColumnName();
        String GoodsTypeList_ColumnGroup();
        String GoodsTypeList_ColumnNumber();
        String GoodsTypeList_ColumnState();
        String GoodsTypeList_typeList();
        String GoodsTypeList_addNewType();
    }
	
	ColumnPanel contentPanel = new ColumnPanel();
    ListBox b_list = new ListBox();        
    ListBox lstAction = new ListBox();
    Button btnAct = new Button("OK");
    PagingToolBar toolBar;    
    
	public static class State extends PageState {
		public String getPageClassName() {
			return GoodsTypeInfo.class.getName();
		}
		public String getMenuDisplayName() {
			return "商品类型";
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
        return "商品类型";
    }
    
    /**
     * Initialize this example.
     */
    public GoodsTypeInfo() {        
        add(contentPanel);        
        initJS(this);         
    }
    
    public String getButtonText() {
        return "新建商品类型";
    }
    
    protected void buttonClicked() {
        NewGoodsType.State state = new NewGoodsType.State();
        state.execute();
    }
    
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);

    	final IShopServiceAsync service = getService();
    	RpcProxy proxy = new RpcProxy() {
            public void load(Object loadConfig, AsyncCallback callback) {
            	service.getGoodsTypeUnit(true, (PagingLoadConfig)loadConfig, callback);
            }
        };
        
    	BasePagingLoader loader = new BasePagingLoader(proxy);
    	loader.setRemoteSort(true);      

        loader.load(0, 10);
    	
        final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

        store.addStoreListener(new StoreListener<BeanObject>() {
            public void storeUpdate(StoreEvent<BeanObject> se) {
                List<Record> changed = store.getModifiedRecords();
            }
        });
        
        toolBar = new PagingToolBar(10);
        toolBar.bind(loader);

        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();            
        //CheckBoxSelectionModel<BeanObject> sm = new CheckBoxSelectionModel<BeanObject>();
        //columns.add(sm.getColumn());   
        columns.add(new ColumnConfig(IGoodsType.NAME, "商品类型名称", 150));
        columns.add(new ColumnConfig(IGoodsType.ATTRIBUTEGROUP, "属性分组", 180));
        columns.add(new ColumnConfig(IGoodsType.ATTRCOUNT, "属性数", 80));
        columns.add(new CheckColumnConfig(IGoodsType.ENABLED, "是否启用", 80)); 
        ColumnConfig actcol = new ColumnConfig("Action", "操作", 150);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        Grid<BeanObject> grid = new Grid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        //grid.setSelectionModel(sm);
        //grid.setAutoExpandColumn("forum");


        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();        
        act.setText("属性列表 ");
        act.setAction("attrList($id)");
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText("编辑 ");
        act.setAction("changeType($id)");
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(" 删除");
		act.setAction("deleteType($id)");
		act.setTooltip(Resources.constants.GoodsList_action_delete());
		render.addAction(act);
        actcol.setRenderer(render);        
        
        final ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");        
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setHeight(500);
        panel.setWidth("100%");
        panel.setBottomComponent(toolBar);
        
//        panel.setButtonAlign(HorizontalAlignment.CENTER);
//        panel.addButton(new com.extjs.gxt.ui.client.widget.button.Button("新建商品类型", new SelectionListener<ButtonEvent>() {
//          public void componentSelected(ButtonEvent ce) {
//              NewGoodsType.State state = new NewGoodsType.State();
//              state.execute();
//          }
//        }));
//        
        add(panel);        
        
        Window.addResizeHandler(new ResizeHandler() {
            public void onResize(ResizeEvent event) {
                int w = event.getWidth() - 300;
                panel.setWidth(w + "px");
            }
        });
    }    
    
    private native void initJS(GoodsTypeInfo me) /*-{
    $wnd.attrList = function (id) {
        me.@com.jcommerce.gwt.client.panels.goods.GoodsTypeInfo::attrListAndRefresh(Ljava/lang/Long;)(id);
    };
    $wnd.changeType = function (id) {
        me.@com.jcommerce.gwt.client.panels.goods.GoodsTypeInfo::modifyTypeAndRefresh(Ljava/lang/Long;)(id);
    };
    $wnd.deleteType = function (id) {
	    me.@com.jcommerce.gwt.client.panels.goods.GoodsTypeInfo::deleteTypeAndRefresh(Ljava/lang/Long;)(id);
	};
    }-*/;
    
    private void modifyTypeAndRefresh(final Long id) {    	
        final IShopServiceAsync service = getService();
        service.getBean(ModelNames.GOODSTYPE, id, new AsyncCallback<BeanObject>() {
            public synchronized void onSuccess(BeanObject result) {
                NewGoodsType.State state = new NewGoodsType.State();
                state.setGoodsType(result);
                state.execute();
            }
            public synchronized void onFailure(Throwable caught) {
                System.out.println("list onFailure("+caught);
                
            }
        });        
    }
    private void deleteTypeAndRefresh(final Long id) {
        new DeleteService().deleteBean(ModelNames.GOODSTYPE, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
					    GoodsTypeInfo.State state = new GoodsTypeInfo.State();
					    state.execute();
					}
				});
    }
    
    private void attrListAndRefresh(final Long id) {
        AttributeInfo.State state = new AttributeInfo.State();
        state.setSelectedGoodsTypeId(id);
        state.execute();
    }

    public void refresh(){    	
    	toolBar.refresh();
    }
}
