/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jcommerce.gwt.client.panels.goods;

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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * Example file.
 */
public class CategoryInfo extends ContentWidget {    
	
	ColumnPanel contentPanel = new ColumnPanel();
    ListBox b_list = new ListBox();    
    Button btnAdd = new Button("添加分类");
    ListBox lstAction = new ListBox();
    Button btnAct = new Button("OK");
    PagingToolBar toolBar;    
    
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
	public static class State extends PageState {
		public String getPageClassName() {
			return CategoryInfo.class.getName();
		}
		public String getMenuDisplayName() {
			return "商品分类";
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
        return "商品分类";
    }
    
    /**
     * Initialize this example.
     */
    public CategoryInfo() {
        add(contentPanel);        
        initJS(this);         
    }
    
    public String getButtonText() {
        return "添加分类";
    }
    
    protected void buttonClicked() {
        NewCategory.State state = new NewCategory.State();
        state.execute();
    }
    
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);

        BasePagingLoader loader = new PagingListService().getLoader(ModelNames.CATEGORY);

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
        columns.add(new ColumnConfig(ICategory.NAME, "分类名称", 150));
        columns.add(new ColumnConfig(ICategory.MEASUREUNIT, "数量单位", 80));
        columns.add(new CheckColumnConfig(ICategory.SHOWINNAVIGATOR, "导航栏", 80));
        columns.add(new CheckColumnConfig(ICategory.SHOW, "是否显示", 80));        
        columns.add(new ColumnConfig(ICategory.GRADE, "价格分级", 60));
        columns.add(new ColumnConfig(ICategory.SORTORDER, "排序", 50));        
        ColumnConfig actcol = new ColumnConfig("Action", "操作", 150);
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
        act.setAction("changeCategory($id)");
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(" 删除");
		act.setAction("deleteCategory($id)");
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
        panel.setHeight(500);
        panel.setWidth("100%");
        panel.setBottomComponent(toolBar);
        
//        panel.setButtonAlign(HorizontalAlignment.CENTER);
//        panel.addButton(new com.extjs.gxt.ui.client.widget.button.Button("添加分类", new SelectionListener<ButtonEvent>() {
//          public void componentSelected(ButtonEvent ce) {
//              NewCategory.State state = new NewCategory.State();
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
    
    private native void initJS(CategoryInfo me) /*-{
    $wnd.changeCategory = function (id) {
        me.@com.jcommerce.gwt.client.panels.goods.CategoryInfo::modifyCategoryAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.deleteCategory = function (id) {
	    me.@com.jcommerce.gwt.client.panels.goods.CategoryInfo::deleteCategoryAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
    private void modifyCategoryAndRefrsh(final String id) {    	
        final IShopServiceAsync service = getService();
        service.getBean(ModelNames.CATEGORY, id, new AsyncCallback<BeanObject>() {
            public synchronized void onSuccess(BeanObject result) {
                NewCategory.State state = new NewCategory.State();
                state.setCategory(result);
                state.execute();
            }
            public synchronized void onFailure(Throwable caught) {
                System.out.println("list onFailure("+caught);                
            }
        });        
    }
    private void deleteCategoryAndRefrsh(final String id) {
        new DeleteService().deleteBean(ModelNames.CATEGORY, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
					    CategoryInfo.State state = new CategoryInfo.State();
			            state.execute();
					}
				});
    }
    public void refresh(){
    	toolBar.refresh();
    }

}
