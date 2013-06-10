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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.treegrid.EditorTreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

/**
 * Example file.
 */
public class CategoryListPanel extends ContentWidget {    
	
	public static interface Constants {
        String CategoryList_title();
        String CategoryList_navigator();
        String CategoryList_priceLevel();
    }
	
//	ColumnPanel contentPanel = new ColumnPanel();
    ListBox b_list = new ListBox();    
    Button btnAdd = new Button(Resources.constants.Category_title());
    ListBox lstAction = new ListBox();
    Button btnAct = new Button("OK");
    PagingToolBar toolBar;    
    /**
     * Initialize this example.
     */
    public static CategoryListPanel getInstance() {
    	if(instance==null) {
    		instance = new CategoryListPanel();
    	}
    	return instance;
    }
    private static CategoryListPanel instance;
    private CategoryListPanel() {
        System.out.println("----------CategoryInfo");
//        add(contentPanel);        
        initJS(this);   
    }
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
	public static class State extends PageState {
		public String getPageClassName() {
			return CategoryListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.CategoryList_title();
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
        return Resources.constants.CategoryList_title();
    }
    TreeStore<BeanObject> store= new TreeStore<BeanObject>();
    
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);

        //BasePagingLoader loader = new PagingListService().getLoader(ModelNames.CATEGORY);

        //loader.load(0, 50);
    	
        //final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

        store.addStoreListener(new StoreListener<BeanObject>() {
            public void storeUpdate(StoreEvent<BeanObject> se) {
                List<Record> changed = store.getModifiedRecords();
            }
        });
        
        //toolBar = new PagingToolBar(50);
        //toolBar.bind(loader);

        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
        //CheckBoxSelectionModel<BeanObject> sm = new CheckBoxSelectionModel<BeanObject>();
        //columns.add(sm.getColumn()); 
        ColumnConfig colName = new ColumnConfig(ICategory.NAME, Resources.constants.Category_name(), 150);
        colName.setRenderer(new TreeGridCellRenderer<BeanObject>());
        columns.add(colName);
        columns.add(new ColumnConfig(ICategory.MEASUREUNIT, Resources.constants.Category_unit(), 100));
        columns.add(new CheckColumnConfig(ICategory.SHOWINNAVIGATOR, Resources.constants.CategoryList_navigator(), 100) {
        	// TODO: wrap the code for Long type column into a baseclass
        	  protected String getCheckState(ModelData model, String property, int rowIndex,
        		      int colIndex) {
        		    int v = 0;
        		    if (model.get(property) != null) {
        		        v = ((Number)model.get(property)).intValue();
        		    }
        		    // see IConstants.DBTYPE_TRUE
        		    String on = v == 1 ? "-on" : "";
        		    return on;
        		  }
        });
        columns.add(new CheckColumnConfig(ICategory.SHOW, Resources.constants.Category_showOrNot(), 100));        
        columns.add(new ColumnConfig(ICategory.GRADE, Resources.constants.CategoryList_priceLevel(), 100));
        columns.add(new ColumnConfig(ICategory.SORTORDER, Resources.constants.Category_order(), 100));        
        ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.action(), 150);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        EditorTreeGrid<BeanObject> grid = new EditorTreeGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setAutoExpandColumn(ICategory.NAME);
        //grid.setSelectionModel(sm);
//        grid.setAutoExpandColumn("forum");


        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();        
        act.setText(Resources.constants.edit());
        act.setAction("changeCategory($id)");
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(" " + Resources.constants.delete());
		act.setAction("deleteCategory($id)");
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
        panel.setHeight(350);
        panel.setBottomComponent(toolBar);
        
        panel.setButtonAlign(HorizontalAlignment.CENTER);
//        panel.addButton(new Button(Resources.constants.Category_title(), new SelectionListener<ButtonEvent>() {
//          public void componentSelected(ButtonEvent ce) {
////              JCommerceGae.getInstance().displayNewCategory();
//        	  	CategoryPanel.State newState = new CategoryPanel.State();
//				newState.setIsEdit(false);
//				newState.execute();
//          }
//        }));
        
        add(panel);        
    }    
    
    private native void initJS(CategoryListPanel me) /*-{
    $wnd.changeCategory = function (id) {
        me.@com.jcommerce.gwt.client.panels.goods.CategoryListPanel::modifyCategoryAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.deleteCategory = function (id) {
	    me.@com.jcommerce.gwt.client.panels.goods.CategoryListPanel::deleteCategoryAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    public Button getShortCutButton(){
    	Button sButton = new Button(Resources.constants.Category_title());
    	sButton.addSelectionListener(new SelectionListener<ButtonEvent>(){

			@Override
			public void componentSelected(ButtonEvent ce) {
				CategoryPanel.State newState = new CategoryPanel.State();
				newState.setIsEdit(false);
				newState.execute();
			}
    		
    	});
    	return sButton;
    }
    private void modifyCategoryAndRefrsh(final String id) {    	
		CategoryPanel.State newState = new CategoryPanel.State();
		newState.setIsEdit(true);
		newState.setId(id);
//		newState.setSelectedParentID(getCurState().getSelectedGoodsTypeID());
		newState.execute();
		
//        final IDefaultServiceAsync service = getService();
//        service.getBean(ModelNames.CATEGORY, id, new AsyncCallback<BeanObject>() {
//            public synchronized void onSuccess(BeanObject result) {
//            	JCommerceGae.getInstance().displayModifyCategory(result);                               
//            }
//            public synchronized void onFailure(Throwable caught) {
//                System.out.println("list onFailure("+caught);                
//            }
//        });        
    }
    private void deleteCategoryAndRefrsh(final String id) {
        new DeleteService().deleteBean(ModelNames.CATEGORY, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						toolBar.refresh();
					}
				});
    }
    public void refresh(){
    	Map<String,List<String>> wantedFields = new HashMap<String,List<String>>();
    	String model = ModelNames.CATEGORY;
    	List<String> fields = null;
    	wantedFields.put(model, fields);
    	// FIXME
//    	new ListService().treeListBeans(ModelNames.CATEGORY,null,wantedFields,new ListService.Listener(){
//
//			@Override
//			public void onSuccess(List<BeanObject> beans) {
//				if(store.getChildCount()>0){
//					store.removeAll();
//				}
//				store.add(beans, true);
//			}
//    		
//    	});
    	//toolBar.refresh();
    }

}
