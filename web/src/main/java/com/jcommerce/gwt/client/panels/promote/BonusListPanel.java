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
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBonusType;
import com.jcommerce.gwt.client.model.IPack;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.BonusService;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class BonusListPanel extends ContentWidget {    
	ColumnPanel contentPanel = new ColumnPanel();
    PagingToolBar toolBar;    
    
	public static class State extends PageState {
		public String getPageClassName() {
			return BonusListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "红包类型";
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
        return "红包类型";
    }
    
    /**
     * Initialize this example.
     */
    public BonusListPanel() {
        add(contentPanel);        
        initJS(this);         
    }
    
    public String getButtonText() {
        return "添加红包类型";
    }
    
    protected void buttonClicked() {
        NewBonusPanel.State state = new NewBonusPanel.State();
        state.execute();
    }
    
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);

        BasePagingLoader loader = new BonusService().getLoader(null);

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
        columns.add(new ColumnConfig(IBonusType.TYPE_NAME, "类型名称", 100));
        ColumnConfig typeCol = new ColumnConfig(IBonusType.SEND_TYPE, "发放类型", 100);
        typeCol.setRenderer(new TypeCellRenderer());
        columns.add(typeCol);
        columns.add(new ColumnConfig(IBonusType.TYPE_MONEY, "红包金额", 80));
        columns.add(new ColumnConfig(IBonusType.MIN_AMOUNT, "订单下限", 80));
        columns.add(new ColumnConfig(IBonusType.NUMBER, "发放数量", 80));
        columns.add(new ColumnConfig(IBonusType.USED_NUMBER, "使用数量", 80));
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
        act.setAction("changeBonus($id)");
        act.setTooltip(Resources.constants.GoodsList_action_edit());
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(" 删除");
		act.setAction("deleteBonus($id)");
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
    
    private native void initJS(BonusListPanel me) /*-{
    $wnd.changeBonus = function (id) {
        me.@com.jcommerce.gwt.client.panels.promote.BonusListPanel::modifyBonusAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.deleteBonus = function (id) {
	    me.@com.jcommerce.gwt.client.panels.promote.BonusListPanel::deleteBonusAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
    private void modifyBonusAndRefrsh(final String id) {    	
        new ListService().listBeans(ModelNames.BONUSTYPE, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject bonus = it.next();                    
                    if(bonus.getString(IPack.ID).trim().equals(id.trim())){
                        NewBonusPanel.State state = new NewBonusPanel.State();
                        state.setBonus(bonus);
                        state.execute();
                    }
                }               
            }
        });        
    }
    
    private void deleteBonusAndRefrsh(final String id) {
        new DeleteService().deleteBean(ModelNames.BONUSTYPE, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
					    BonusListPanel.State state = new BonusListPanel.State();
					    state.execute();
					}
				});
    }
    
    public void refresh(){
    	toolBar.refresh();
    }

    class TypeCellRenderer implements GridCellRenderer<BeanObject> {
        public Object render(BeanObject model, String property, ColumnData config,
                final int rowIndex, final int colIndex, ListStore<BeanObject> store,
                Grid<BeanObject> grid) {   
            int type = (Integer)model.get(IBonusType.SEND_TYPE);
            if (type == IBonusType.SEND_BY_USER) {
                return "按用户发放";
            } else if (type == IBonusType.SEND_BY_GOODS) {
                return "按商品发放";
            } else if (type == IBonusType.SEND_BY_ORDER) {
                return "按订单金额发放";
            } else if (type == IBonusType.SEND_BY_PRINT) {
                return "线下发放的红包";
            }
            
            throw new RuntimeException("Unknown send type: "+type);
        }
    }
}
