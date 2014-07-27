package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.GoodsService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class DeletedGoodsList extends ContentWidget {
	
	public static class State extends PageState {
		public String getPageClassName() {
			return DeletedGoodsList.class.getName();
		}
		public String getMenuDisplayName() {
			return "商品回收站";
		}
	}
	
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

	ColumnPanel contentPanel = new ColumnPanel();
	ListBox b_list = new ListBox();
//	ListBox lstCategory = new ListBox();
//	ListBox lstBrand = new ListBox();
//	ListBox lstType = new ListBox();
	TextBox txtKeyword = new TextBox();
	Button btnFind = new Button(Resources.constants.GoodsList_search());
//	ListBox lstAction = new ListBox();
//	Button btnAct = new Button(Resources.constants.GoodsList_action_OK());

	Criteria criteria = new Criteria();

	PagingToolBar toolBar;

	public DeletedGoodsList() {
		add(contentPanel);
		initJS(this);

	}

	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return "商品回收站";
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		criteria.addCondition(new Condition(IGoods.DELETED, Condition.EQUALS, "true"));
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.GOODS, criteria);
		loader.load(0, 10);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		columns.add(new ColumnConfig(IGoods.ID, "ID", 50));
		ColumnConfig col = new ColumnConfig(IGoods.NAME, Resources.constants
				.Goods_name(), 100);
		columns.add(col);
		col = new ColumnConfig(IGoods.SN, Resources.constants.Goods_SN(), 100);
		columns.add(col);
        
		col = new ColumnConfig(IGoods.SHOPPRICE, Resources.constants
				.Goods_shopPrice(), 80);
		col.setAlignment(HorizontalAlignment.RIGHT);
		col.setNumberFormat(NumberFormat.getCurrencyFormat());
		columns.add(col);
		
		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
				.GoodsList_action(), 100);
//		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		//      grid.setAutoExpandColumn("forum");

		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage("icon_edit.gif");
		act.setAction("editGoods($id)");
		act.setTooltip(Resources.constants.GoodsList_action_edit());
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage("icon_trash.gif");
		act.setAction("deleteGoods($id)");
		act.setTooltip(Resources.constants.GoodsList_action_delete());
		render.addAction(act);

		actcol.setRenderer(render);

		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label("  " + Resources.constants.GoodsList_keyword()));
		header.add(txtKeyword);
		header.add(btnFind);
		add(header);

		btnFind.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				search();
			}
		});

		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		//        panel.setHeading("Paging Grid");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(880, 350);
		panel.setBottomComponent(toolBar);

		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.addButton(new Button("彻底删除",
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
                        List<BeanObject> items = smRowSelection.getSelectedItems();
                        purgeGoods(items);
					}
				}));

		panel.addButton(new Button("恢复",
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
		                List<BeanObject> items = smRowSelection.getSelectedItems();
		                restoreGoods(items);
					}
				}));
				
		add(panel);
	}

	private void restoreGoods(final List<BeanObject> items) {
        if (items == null) {
            return;
        }
        
        final List<GoodsListener> listeners = new ArrayList<GoodsListener>();
        
        for (BeanObject item : items) {
            GoodsListener listener = new GoodsListener();
            listeners.add(listener);
            new GoodsService().undoDeletedGoods(item.getString(IGoods.ID), listener);
        }
        
        new WaitService(new WaitService.Job() {
            public boolean isReady() {
                if (listeners.size() != items.size()) {
                    return false;
                }
                for (GoodsListener l : listeners) {
                    if (!l.isFinished()) {
                        return false;
                    }
                }
                return false;
            }

            public void run() {
                toolBar.refresh();
            }
        });        
	}
	
    private void purgeGoods(final List<BeanObject> items) {
        if (items == null) {
            return;
        }
        
        final List<GoodsListener> listeners = new ArrayList<GoodsListener>();
        
        for (BeanObject item : items) {
            GoodsListener listener = new GoodsListener();
            listeners.add(listener);
            new GoodsService().purgeGoods(item.getString(IGoods.ID), listener);
        }
        
        new WaitService(new WaitService.Job() {
            public boolean isReady() {
                if (listeners.size() != items.size()) {
                    return false;
                }
                for (GoodsListener l : listeners) {
                    if (!l.isFinished()) {
                        return false;
                    }
                }
                return false;
            }

            public void run() {
                toolBar.refresh();
            }
        });        
    }
    
	private void search() {
		criteria.removeAll();

		String keyword = txtKeyword.getText();
		if (keyword != null && keyword.trim().length() > 0) {
			Condition cond = new Condition();
			cond.setField(IGoods.KEYWORDS);
			cond.setOperator(Condition.CONTAINS);
			cond.setValue(keyword.trim());
			criteria.addCondition(cond);
		}

		toolBar.refresh();
	}

	private native void initJS(DeletedGoodsList me) /*-{
	   $wnd.deleteGoods = function (id) {
	       me.@com.jcommerce.gwt.client.panels.goods.GoodsList::deleteGoodsAndRefrsh(Ljava/lang/String;)(id);
	   };
	   $wnd.editGoods = function (id) {
	       me.@com.jcommerce.gwt.client.panels.goods.GoodsList::editGoods(Ljava/lang/String;)(id);
	   };
	   }-*/;

	private void deleteGoods(String id, DeleteService.Listener listener) {
		new DeleteService().deleteBean(ModelNames.GOODS, id, listener);
	}

	private void deleteGoodsAndRefrsh(String id) {
		new DeleteService().deleteBean(ModelNames.GOODS, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						toolBar.refresh();
					}
				});
	}

	private void editGoods(String id) {
		new ReadService().getBean(ModelNames.GOODS, id,
				new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
					    NewGoods.State state = new NewGoods.State();
					    state.setGoods(bean);
                        state.execute();
					}
				});
	}

	class GoodsListener extends GoodsService.Listener {
		private boolean finished = false;

		public void onSuccess(Boolean sucess) {
			finished = true;
		}

		public void onFailure(Throwable caught) {
			finished = true;
		}

		boolean isFinished() {
			return finished;
		}
	}


}
