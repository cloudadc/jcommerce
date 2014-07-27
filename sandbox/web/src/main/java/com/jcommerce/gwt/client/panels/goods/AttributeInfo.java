package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.panels.goods.NewAttribute.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.IdToStringRenderer;

public class AttributeInfo extends ContentWidget {
	public static interface Constants {
		String AttributeList_all_GoodsType();
		String AttributeList_GoodsType();
		String AttributeList_ID();
		String AttributeList_Name();
		String AttributeList_InputType();
		String AttributeList_Values();
		String AttributeList_SortOrder();
		String AttributeList_Action();
	}
	
	ColumnPanel contentPanel = new ColumnPanel();
//	String selectedGoodsTypeId = null;
	ListBox lstGoodsType = new ListBox();
	PagingToolBar toolBar;
	BasePagingLoader loader;
	ListStore<BeanObject> store;
	Grid<BeanObject> grid;
	ContentPanel panel;
	
	public AttributeInfo() {
		add(contentPanel);
		initJS(this);
	}	
	
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
    public static class State extends PageState {
        private Long selectedGoodsTypeId = null;
        
        public void setSelectedGoodsTypeId(Long selectedGoodsTypeId) {
            this.selectedGoodsTypeId = selectedGoodsTypeId;
            setEditting(selectedGoodsTypeId != null);
        }
        
        public Long getSelectedGoodsTypeId() {
            return selectedGoodsTypeId;
        }

        public String getPageClassName() {
            return AttributeInfo.class.getName();
        }
        public String getMenuDisplayName() {
            return "商品属性";
        }
    }
    
    public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
    }

//	public void setSelectedGoodsTypeId(String selectedGoodsTypeId) {
//		this.selectedGoodsTypeId = selectedGoodsTypeId;
//	}
//	
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "商品属性";
	}	
	
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index); 					
    	Long selectedGoodsTypeId = getCurState().getSelectedGoodsTypeId();
    	
    	if(selectedGoodsTypeId != null && !selectedGoodsTypeId.equals("0")){
    		Criteria criteria = new Criteria();
    		Condition cond = new Condition();
    		cond.setField(IAttribute.GOODSTYPE);
    		cond.setOperator(Condition.EQUALS);
    		cond.setValue(Long.valueOf(selectedGoodsTypeId));
    		criteria.addCondition(cond);		
    		this.loader = new PagingListService().getLoader(ModelNames.ATTRIBUTE, criteria);
    	}
    	else{
    		this.loader = new PagingListService().getLoader(ModelNames.ATTRIBUTE);
    	}    		
    	this.loader.load(0, 50);	
		this.store = new ListStore<BeanObject>(this.loader);		
		this.store.addStoreListener(new StoreListener<BeanObject>() {
			public void storeUpdate(StoreEvent<BeanObject> se) {
				List<Record> changed = store.getModifiedRecords();
				for (Record rec : changed) {
					BeanObject bean = (BeanObject) rec.getModel();
					updateAttribute(bean, null);
				}
			}
		});
		toolBar = new PagingToolBar(50);
		toolBar.bind(loader);	
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IAttribute.ID, Resources.constants.AttributeList_ID(), 50));
		ColumnConfig col = new ColumnConfig(IAttribute.NAME, Resources.constants.AttributeList_Name(), 140);
		col.setEditor(new CellEditor(new TextField()));
		columns.add(col);
		ColumnConfig goodsType = new ColumnConfig(IAttribute.GOODSTYPE, "商品类型", 120);
		columns.add(goodsType);
		ColumnConfig inputType = new ColumnConfig(IAttribute.INPUTTYPE, Resources.constants.AttributeList_InputType(), 120);
		columns.add(inputType);
		columns.add(new ColumnConfig(IAttribute.VALUES, Resources.constants.AttributeList_Values(), 150));
		columns.add(new ColumnConfig(IAttribute.SORTORDER, Resources.constants.AttributeList_SortOrder(), 50));
		ColumnConfig actcol = new ColumnConfig("Action", "Resources.constants.AttributeList_Action()", 100);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		grid = new EditorGrid<BeanObject>(this.store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);		
		//      grid.setAutoExpandColumn("forum");

		IdToStringRenderer inputTypeCell = new IdToStringRenderer(grid);
		inputTypeCell.addIdInfo(new IdToStringRenderer.IdInfo("0","手工录入"));
		inputTypeCell.addIdInfo(new IdToStringRenderer.IdInfo("1","从列表中选择"));
		inputType.setRenderer(inputTypeCell);
		
		final List<IdToStringRenderer.IdInfo> goodsTypeInfo = new ArrayList<IdToStringRenderer.IdInfo>();
		lstGoodsType.clear();
		lstGoodsType.addItem(Resources.constants.AttributeList_all_GoodsType(),"0");
		new ListService().listBeans(ModelNames.GOODSTYPE,new ListService.Listener() {
					int i=1;					
					public synchronized void onSuccess(List<BeanObject> result) {
					    String selectedGoodsTypeId = getCurState().getSelectedGoodsTypeId();
					    
						for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
							BeanObject goodsType = it.next();
							lstGoodsType.addItem(goodsType.getString(IGoodsType.NAME),goodsType.getString(IGoodsType.ID));
							goodsTypeInfo.add(i-1,new IdToStringRenderer.IdInfo(goodsType.getString(IGoodsType.ID),goodsType.getString(IGoodsType.NAME)));
							if(selectedGoodsTypeId!=null && selectedGoodsTypeId.equals(goodsType.getString(IGoodsType.ID))) {
								lstGoodsType.setSelectedIndex(i);
							}
							i++;
						}
					}
				});		
		
		IdToStringRenderer goodsTypeCell = new IdToStringRenderer(grid);
		goodsTypeCell.setIdToString(goodsTypeInfo);			
		goodsType.setRenderer(goodsTypeCell);
		
		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_edit.gif");
		act.setAction("editAttributeInfo($id)");
		act.setTooltip(Resources.constants.edit());
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage("icon_trash.gif");
		act.setAction("deleteAttributeInfo($id)");
		act.setTooltip(Resources.constants.delete());
		render.addAction(act);
		actcol.setRenderer(render);
		
		
		
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("  " + Resources.constants.AttributeList_GoodsType()));
		header.add(lstGoodsType);
		add(header);
		
		panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");		
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(800, 350);
		panel.setBottomComponent(toolBar);

		panel.setButtonAlign(HorizontalAlignment.LEFT);
		panel.addButton(new Button(Resources.constants.reset(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						store.rejectChanges();
						toolBar.refresh();
					}
				}));

		panel.addButton(new Button(Resources.constants.save(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						store.commitChanges();
						toolBar.refresh();
					}
				}));

		panel.addButton(new Button(Resources.constants.add(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
					    NewAttribute.State state = new NewAttribute.State();
					    state.execute();
					}
				}));		

		add(panel);	
		
		lstGoodsType.addChangeHandler(new ChangeHandler() {
            
            public void onChange(ChangeEvent arg0) {
				getCurState().setSelectedGoodsTypeId(lstGoodsType.getValue(lstGoodsType.getSelectedIndex()));				
				refresh();
			}
			
		});
    }	
	
    private void updateAttribute(BeanObject attribute, UpdateService.Listener listener) {    	
		new UpdateService().updateBean(attribute.getLong(IAttribute.ID), attribute, listener);		
	}
    
	public void refresh() {		
	    String selectedGoodsTypeId = getCurState().getSelectedGoodsTypeId();
		if (selectedGoodsTypeId != null&& !selectedGoodsTypeId.equals("0")) {
			Criteria criteria = new Criteria();
			Condition cond = new Condition();
			cond.setField(IAttribute.GOODSTYPE);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(Long.valueOf(selectedGoodsTypeId));
			criteria.addCondition(cond);
			loader = new PagingListService().getLoader(ModelNames.ATTRIBUTE,criteria);
			loader.load(0, 50);
			this.store.removeAll();
			this.store = new ListStore<BeanObject>(loader);
			this.store.addStoreListener(new StoreListener<BeanObject>() {
				public void storeUpdate(StoreEvent<BeanObject> se) {
					List<Record> changed = store.getModifiedRecords();
					for (Record rec : changed) {
						BeanObject bean = (BeanObject) rec.getModel();
						updateAttribute(bean, null);
					}
				}
			});
			toolBar.clear();
			toolBar = new PagingToolBar(50);
			toolBar.bind(loader);
			List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
			final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
			columns.add(smRowSelection.getColumn());
			columns.add(new ColumnConfig(IAttribute.ID, Resources.constants.AttributeList_ID(), 50));
			ColumnConfig col = new ColumnConfig(IAttribute.NAME, Resources.constants.AttributeList_Name(), 140);
			col.setEditor(new CellEditor(new TextField()));
			columns.add(col);
			ColumnConfig goodsType = new ColumnConfig(IAttribute.GOODSTYPE,	"商品类型", 120);
			columns.add(goodsType);
			ColumnConfig inputType = new ColumnConfig(IAttribute.INPUTTYPE,	Resources.constants.AttributeList_InputType(), 120);
			columns.add(inputType);
			columns.add(new ColumnConfig(IAttribute.VALUES, Resources.constants
					.AttributeList_Values(), 150));
			columns.add(new ColumnConfig(IAttribute.SORTORDER, Resources.constants.AttributeList_SortOrder(), 50));
			ColumnConfig actcol = new ColumnConfig("Action", "Resources.constants.AttributeList_Action()", 100);
			columns.add(actcol);

			ColumnModel cm = new ColumnModel(columns);
			grid.reconfigure(store, cm);
			grid.setLoadMask(true);
			grid.setBorders(true);
			grid.setSelectionModel(smRowSelection);
			IdToStringRenderer inputTypeCell = new IdToStringRenderer(grid);
			inputTypeCell.addIdInfo(new IdToStringRenderer.IdInfo("0", "手工录入"));
			inputTypeCell.addIdInfo(new IdToStringRenderer.IdInfo("1", "从列表中选择"));
			inputType.setRenderer(inputTypeCell);

			final List<IdToStringRenderer.IdInfo> goodsTypeInfo = new ArrayList<IdToStringRenderer.IdInfo>();

			new ListService().listBeans(ModelNames.GOODSTYPE,new ListService.Listener() {
						int i = 1;
						public synchronized void onSuccess(List<BeanObject> result) {
						    String selectedGoodsTypeId = getCurState().getSelectedGoodsTypeId();
							for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
								BeanObject goodsType = it.next();
								goodsTypeInfo.add(i - 1, new IdToStringRenderer.IdInfo(goodsType.getString(IGoodsType.ID), goodsType.getString(IGoodsType.NAME)));
								if (selectedGoodsTypeId != null && selectedGoodsTypeId.equals(goodsType.getString(IGoodsType.ID))) {
									lstGoodsType.setSelectedIndex(i);
								}
								i++;
							}
						}
					});

			IdToStringRenderer goodsTypeCell = new IdToStringRenderer(grid);
			goodsTypeCell.setIdToString(goodsTypeInfo);
			goodsType.setRenderer(goodsTypeCell);

			ActionCellRenderer render = new ActionCellRenderer(grid);
			ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
			act.setImage("icon_edit.gif");
			act.setAction("editAttributeInfo($id)");
			act.setTooltip(Resources.constants.edit());
			render.addAction(act);
			act = new ActionCellRenderer.ActionInfo();
			act.setImage("icon_trash.gif");
			act.setAction("deleteAttributeInfo($id)");
			act.setTooltip(Resources.constants.delete());
			render.addAction(act);
			actcol.setRenderer(render);
		} 
		else {
			loader = new PagingListService().getLoader(ModelNames.ATTRIBUTE);
			loader.load(0, 50);
			this.store.removeAll();
			this.store = new ListStore<BeanObject>(loader);
			this.store.addStoreListener(new StoreListener<BeanObject>() {
				public void storeUpdate(StoreEvent<BeanObject> se) {
					List<Record> changed = store.getModifiedRecords();
					for (Record rec : changed) {
						BeanObject bean = (BeanObject) rec.getModel();
						updateAttribute(bean, null);
					}
				}
			});
			toolBar.clear();
			toolBar = new PagingToolBar(50);
			toolBar.bind(loader);
			List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
			final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
			columns.add(smRowSelection.getColumn());
			columns.add(new ColumnConfig(IAttribute.ID, Resources.constants.AttributeList_ID(), 50));
			ColumnConfig col = new ColumnConfig(IAttribute.NAME, Resources.constants.AttributeList_Name(), 140);
			col.setEditor(new CellEditor(new TextField()));
			columns.add(col);
			ColumnConfig goodsType = new ColumnConfig(IAttribute.GOODSTYPE, "商品类型", 120);
			columns.add(goodsType);
			ColumnConfig inputType = new ColumnConfig(IAttribute.INPUTTYPE,	Resources.constants.AttributeList_InputType(), 120);
			columns.add(inputType);
			columns.add(new ColumnConfig(IAttribute.VALUES, Resources.constants.AttributeList_Values(), 150));
			columns.add(new ColumnConfig(IAttribute.SORTORDER,
					Resources.constants.AttributeList_SortOrder(), 50));
			ColumnConfig actcol = new ColumnConfig("Action",
					"Resources.constants.AttributeList_Action()", 100);
			columns.add(actcol);

			ColumnModel cm = new ColumnModel(columns);
			grid.reconfigure(store, cm);
			grid.setLoadMask(true);
			grid.setBorders(true);
			grid.setSelectionModel(smRowSelection);
			IdToStringRenderer inputTypeCell = new IdToStringRenderer(grid);
			inputTypeCell.addIdInfo(new IdToStringRenderer.IdInfo("0", "手工录入"));
			inputTypeCell
					.addIdInfo(new IdToStringRenderer.IdInfo("1", "从列表中选择"));
			inputType.setRenderer(inputTypeCell);

			final List<IdToStringRenderer.IdInfo> goodsTypeInfo = new ArrayList<IdToStringRenderer.IdInfo>();
			new ListService().listBeans(ModelNames.GOODSTYPE, new ListService.Listener() {
						int i = 1;
						public synchronized void onSuccess(List<BeanObject> result) {
						    String selectedGoodsTypeId = getCurState().getSelectedGoodsTypeId();
							for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
								BeanObject goodsType = it.next();
								goodsTypeInfo.add(i - 1, new IdToStringRenderer.IdInfo(goodsType.getString(IGoodsType.ID), goodsType.getString(IGoodsType.NAME)));
								if (selectedGoodsTypeId != null && selectedGoodsTypeId.equals(goodsType.getString(IGoodsType.ID))) {
									lstGoodsType.setSelectedIndex(i);
								}
								i++;
							}
						}
					});

			IdToStringRenderer goodsTypeCell = new IdToStringRenderer(grid);
			goodsTypeCell.setIdToString(goodsTypeInfo);
			goodsType.setRenderer(goodsTypeCell);
			ActionCellRenderer render = new ActionCellRenderer(grid);
			ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
			act.setImage("icon_edit.gif");
			act.setAction("editAttributeInfo($id)");
			act.setTooltip(Resources.constants.edit());
			render.addAction(act);
			act = new ActionCellRenderer.ActionInfo();
			act.setImage("icon_trash.gif");
			act.setAction("deleteAttributeInfo($id)");
			act.setTooltip(Resources.constants.delete());
			render.addAction(act);
			actcol.setRenderer(render);
		}		
		toolBar.refresh();	
	}
	
	private native void initJS(AttributeInfo me) /*-{
	   $wnd.editAttributeInfo = function (id) {
	       me.@com.jcommerce.gwt.client.panels.goods.AttributeInfo::editAttributeInfo(Ljava/lang/Long;)(id);
	   };
	   $wnd.deleteAttributeInfo = function (id) {
	       me.@com.jcommerce.gwt.client.panels.goods.AttributeInfo::deleteAttributeAndRefrsh(Ljava/lang/Long;)(id);
	   };
	   }-*/;

	private void deleteAttributeAndRefrsh(Long id) {
		new DeleteService().deleteBean(ModelNames.ATTRIBUTE, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						toolBar.refresh();
					}
				});
	}

	private void editAttributeInfo(Long id) {
		new ReadService().getBean(ModelNames.ATTRIBUTE, id,
				new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
                        NewAttribute.State state = new NewAttribute.State();
                        state.setAttribute(bean);
                        state.execute();
					}
				});
	}
}
