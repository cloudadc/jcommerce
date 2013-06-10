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
import com.extjs.gxt.ui.client.widget.Info;
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoodsAttr;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.MyRpcProxy;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class AttributeListPanel extends ContentWidget {
	public static interface Constants {
		String AttributeList_all_GoodsType();
		String AttributeList_GoodsType();
		String AttributeList_ID();
		String AttributeList_Name();
		String AttributeList_InputType();
		String AttributeList_Values();
		String AttributeList_SortOrder();
		String AttributeList_title();
		String AttributeList_addType();
	}
	
	public static class State extends PageState {
		public static final String ATTR_ID = "attrid";
		public static final String SELECTED_GOODSTYPE_ID = "sgtid";
		
		public String getPageClassName() {
			return AttributeListPanel.class.getName();
		}
		
		public void setAttrID(String attrid) {
			setValue(ATTR_ID, attrid);
		}
		public String getAttrID() {
			return (String)getValue(ATTR_ID);
		}
		public void setSelectedGoodsTypeID(String sgtid) {
			setValue(SELECTED_GOODSTYPE_ID, sgtid);
		}
		public String getSelectedGoodsTypeID() {
			return (String)getValue(SELECTED_GOODSTYPE_ID);
		}
	}
	
    public Button getShortCutButton() {
      Button sButton = new Button(Resources.constants.AttributeList_addType());
      sButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onShortCutButtonClicked();
          }
      });
      return sButton;
    }
    public void onShortCutButtonClicked() {
		AttributePanel.State newState = new AttributePanel.State();
		newState.setIsEdit(false);
		newState.execute();
    }
	
	PagingToolBar toolBar;
	BasePagingLoader loader = null;
	int pageSize = 3;
	ListBox listGoodsType = new ListBox();
	
	private static AttributeListPanel instance;

	
	public static AttributeListPanel getInstance() {
		if(instance == null) {
			instance = new AttributeListPanel();
		}
		return instance;
	}
	
	private AttributeListPanel() {
//		add(contentPanel);
		initJS(this);
	}
	private native void initJS(AttributeListPanel me) /*-{
	   $wnd.editAttribute = function (id) {
	       me.@com.jcommerce.gwt.client.panels.goods.AttributeListPanel::editAttribute(Ljava/lang/String;)(id);
	   };
	   $wnd.deleteAttribute = function (id) {
	       me.@com.jcommerce.gwt.client.panels.goods.AttributeListPanel::deleteAttributeAndRefresh(Ljava/lang/String;)(id);
	   };
	   }-*/;
	
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return Resources.constants.AttributeList_title();
	}
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);
    	
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("  " + Resources.constants.AttributeList_GoodsType()));
		
		
//		ListField<BeanObject> listGoodsType = new ListField<BeanObject>();
//		
//		ListLoader lgtLoader = new ListService().getLoader(ModelNames.GOODSTYPE);
//		final ListStore<BeanObject> store1 = new ListStore<BeanObject>(lgtLoader);
//		listGoodsType.setStore(store1);
//		listGoodsType.setDisplayField(GoodsTypeForm.NAME);
//		lgtLoader.load();
		
		
//		ComboBox<BeanObject> listGoodsType = new ComboBox<BeanObject>();
//		
//		ListLoader lgtLoader = new ListService().getLoader(ModelNames.GOODSTYPE);
//		final ListStore<BeanObject> store1 = new ListStore<BeanObject>(lgtLoader);
//		listGoodsType.setStore(store1);
//		listGoodsType.setDisplayField(GoodsTypeForm.NAME);
//		lgtLoader.load();
		  
//		listGoodsType.addChangeListener(new ChangeListener() {
//			public void onChange(Widget sender) {
//				int index=listGoodsType.getSelectedIndex();
//				getCurState().setSelectedGoodsTypeID(listGoodsType.getValue(index));
//				refreshAttributeList();
//			}
//		});
		
		listGoodsType.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				int index=listGoodsType.getSelectedIndex();
				getCurState().setSelectedGoodsTypeID(listGoodsType.getValue(index));
				getCurState().execute();
			}
		});
		header.add(listGoodsType);
		
		add(header);
		
        
        List<String> wantedFields = new ArrayList<String>();
        wantedFields.add(AttributeForm.ID);
        wantedFields.add(AttributeForm.NAME);
//        wantedFields.add(AttributeForm.GOODS_TYPE_NAME);
        wantedFields.add(AttributeForm.INPUTTYPE);
        wantedFields.add(AttributeForm.VALUES);
        wantedFields.add(AttributeForm.SORTORDER);
		loader = new PagingListService().getLoader(
				ModelNames.ATTRIBUTE);
		
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		store.addStoreListener(new StoreListener<BeanObject>() {
			public void storeUpdate(StoreEvent<BeanObject> se) {
				List<Record> changed = store.getModifiedRecords();
//				List<BeanObject> attrs = new ArrayList<BeanObject>();
				for (Record rec : changed) {
					BeanObject bean = (BeanObject) rec.getModel();
					updateAttribute(bean);
				}
			}
		});
		
		toolBar = new PagingToolBar(pageSize);
		toolBar.bind(loader);
		
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
//		columns.add(new ColumnConfig(AttributeForm.ID, Resources.constants
//				.AttributeList_ID(), 50));
		ColumnConfig col = new ColumnConfig(AttributeForm.NAME, Resources.constants
				.AttributeList_Name(), 100);
		col.setEditor(new CellEditor(new TextField()));
		columns.add(col);
		columns.add(new ColumnConfig(AttributeForm.GOODSTYPE, Resources.constants
				.AttributeList_GoodsType(), 80));
//		columns.add(new ColumnConfig(AttributeForm.GOODS_TYPE, Resources.constants
//				.AttributeList_GoodsType(), 80));
		columns.add(new ColumnConfig(AttributeForm.INPUTTYPE, Resources.constants
				.AttributeList_InputType(), 80));
		columns.add(new ColumnConfig(AttributeForm.VALUES, Resources.constants
				.AttributeList_Values(), 80));
		columns.add(new ColumnConfig(AttributeForm.SORTORDER, Resources.constants
				.AttributeList_SortOrder(), 50));
		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
				.action(), 100);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		//      grid.setAutoExpandColumn("forum");
//		grid.addPlugin(onsale);
//		grid.addPlugin(hotsold);
//		grid.addPlugin(bestsold);
//		grid.addPlugin(newadd);

		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage(GWT.getModuleBaseURL()+"icon_edit.gif");
		act.setAction("editAttribute($id)");
		
		act.setTooltip(Resources.constants.edit());
		
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage(GWT.getModuleBaseURL()+"icon_trash.gif");
		act.setAction("deleteAttribute($id)");
		act.setTooltip(Resources.constants.delete());
		render.addAction(act);

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
		panel.setBottomComponent(toolBar);

		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.addButton(new Button(Resources.constants.reset(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						store.rejectChanges();
					}
				}));

		panel.addButton(new Button(Resources.constants.save(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
//						List<Record> changed = store.getModifiedRecords();
//						for (Record rec : changed) {
//							BeanObject bean = (BeanObject) rec.getModel();
//							updateAttribute(bean);
//						}
						store.commitChanges();
					}
				}));
		panel.addButton(new Button(Resources.constants.deleteSelected(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						List<BeanObject> selected = smRowSelection.getSelectedItems();
						deleteAttributesAndRefresh(selected);
					}
				}));
		panel.addButton(new Button(Resources.constants.add(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						AttributePanel.State newState = new AttributePanel.State();
						newState.setIsEdit(false);
						newState.setSelectedGoodsTypeID(getCurState().getSelectedGoodsTypeID());
						newState.execute();
					}
				}));

		add(panel);

//		refresh();
    }

	private void refreshListGoodsType() {
		listGoodsType.clear();
		listGoodsType.insertItem(Resources.constants.AttributeList_all_GoodsType(), "all", 0);
		new ListService().listBeans(ModelNames.GOODSTYPE,
				new ListService.Listener() {
					int i=1;
					public synchronized void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it
								.hasNext();) {
							BeanObject goodsType = it.next();
							listGoodsType.insertItem(goodsType.getString(IGoodsType.NAME),
									goodsType.getString(IGoodsType.ID), i);
							if(getCurState().getSelectedGoodsTypeID()!=null && getCurState().getSelectedGoodsTypeID().equals(goodsType.getString(IGoodsType.ID))) {
								listGoodsType.setSelectedIndex(i);
							}
							i++;
						}
					}
				});
	}

	
	public void refresh() {
		System.out.println("----- refresh attributeList---");
		refreshAttributeList();
		refreshListGoodsType();
	}

	private void refreshAttributeList() {
		String selectedGoodsTypeId = getCurState().getSelectedGoodsTypeID();
		System.out.println("selectedGoodsTypeId= "+ selectedGoodsTypeId);
		Criteria criteria = new Criteria(); 
		if(!"all".equals(selectedGoodsTypeId) && (selectedGoodsTypeId!=null && !"".equals(selectedGoodsTypeId))) {
			Condition cond = new Condition();
			cond.setField(AttributeForm.GOODSTYPE);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(selectedGoodsTypeId);
			criteria.addCondition(cond);			
		}
		MyRpcProxy proxy = (MyRpcProxy)loader.getProxy();
		proxy.setCriteria(criteria);
//		loader.load(0, pageSize);
		toolBar.refresh();
	}

	private void deleteAttributeAndRefresh(String id) {
		new DeleteService().deleteBean(ModelNames.ATTRIBUTE, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						toolBar.refresh();
					}
				});
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IGoodsAttr.ATTR_ID, Condition.EQUALS, id));
		new ListService().listBeans(ModelNames.GOODSATTRIBUTE, criteria, new ListService.Listener(){

			@Override
			public void onSuccess(List<BeanObject> beans) {
				List<String> ids = new ArrayList<String>();
				for(Iterator i = beans.iterator(); i.hasNext();) {
					BeanObject bean = (BeanObject) i.next();
					ids.add(bean.getString(IGoodsAttr.ID));
				}
				new DeleteService().deleteBeans(ModelNames.GOODSATTRIBUTE, ids, null);				
			}
			
		});
	}
	private void deleteAttributesAndRefresh(List<BeanObject> selected) {
		List<String> ids = new ArrayList<String>();
		for(BeanObject bean:selected) {
			ids.add(bean.getString(AttributeForm.ID));
		}
		new DeleteService().deleteBeans(ModelNames.ATTRIBUTE, ids,
				new DeleteService.BatchDeleteListener() {
					public void onSuccess(Integer successCount) {
						Info.display("恭喜", successCount+"个商品属性已删除");
						toolBar.refresh();
					}
					public void onFailure(Throwable caught) {
						super.onFailure(caught);
						Info.display("糟糕", "删除商品属性失败");
					}					
				});
	}
	private void updateAttribute(final BeanObject att) {
		new UpdateService().updateBean(att.getString(AttributeForm.ID), att,
				new UpdateService.Listener() {
					@Override
					public void onFailure(Throwable caught) {
						super.onFailure(caught);
						Info.display("糟糕", "更新商品属性失败"+att.getString(AttributeForm.NAME));
					}

					@Override
					public void onSuccess(Boolean success) {
						if(success) {
							Info.display("恭喜", "更新商品属性成功"+att.getString(AttributeForm.NAME));
						} else {
							Info.display("糟糕", "更新商品属性失败"+att.getString(AttributeForm.NAME));
						}
					}
			
		});
	}
	private void editAttribute(String id) {
		AttributePanel.State newState = new AttributePanel.State();
		newState.setIsEdit(true);
		newState.setId(id);
		newState.setSelectedGoodsTypeID(getCurState().getSelectedGoodsTypeID());
		newState.execute();
		
//		new ReadService().getBean(ModelNames.ATTRIBUTE, id,
//				new ReadService.Listener() {
//					public void onSuccess(BeanObject bean) {
//						iShop.getInstance().displayAttributePanel(bean);
//					}
//				});
	}
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}
}
