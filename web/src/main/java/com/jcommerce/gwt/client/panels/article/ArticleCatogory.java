package com.jcommerce.gwt.client.panels.article;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IArticle;
import com.jcommerce.gwt.client.model.IArticleCatagory;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.TreeListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class ArticleCatogory extends ContentWidget{
	
	public ArticleCatogory() {
	    curState = new State();
		initJS(this);
	}
	public static class State extends PageState {
		
		public String getPageClassName() {
			return ArticleCatogory.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "文章分类";
		}
		public void setParentId(String parentId) {
			setValue("parentId", parentId);
		}

		public String getParentId() {
			return (String) getValue("parentId");
		}
	}
	
	public State getCurState() {
		return (State)curState;
	}
	
	public void setCurState(State curState) {
		this.curState = curState;
		
	}
	private static ArticleCatogory instance;

	public static ArticleCatogory getInstance() {
		if(instance == null) {
			instance = new ArticleCatogory();
		}
		return instance;
	}
	
	private BaseTreeLoader<BeanObject> loader;
	private TreeGrid<BeanObject> treeGrid;
	private PagingToolBar toolBar = new PagingToolBar(10);
	private ContentPanel TablePanel = new ContentPanel();
	
    public String getButtonText() {
        return "添加文章分类";
    }
    
    protected void buttonClicked() {
        NewArticleCat.State state = new NewArticleCat.State();
        state.execute();
    }
    	
	protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
       
       
        final BaseTreeLoader<BeanObject> loader = new TreeListService().getLoader(ModelNames.ARTICLECATAGORY);
       
		this.loader = loader;
        
		final TreeStore<BeanObject> store = new TreeStore<BeanObject>(loader);  
		store.addStoreListener(new StoreListener<BeanObject>() {
			public void storeUpdate(StoreEvent<BeanObject> se) {
				List<Record> changed = store.getModifiedRecords();
				for (Record rec : changed) {
					BeanObject bean = (BeanObject) rec.getModel();
					new UpdateService().updateBean(bean.getLong(IArticleCatagory.ID), bean, null);
				}
			}
		});
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		ColumnConfig catogory_name = new ColumnConfig(IArticleCatagory.NAME,
				"文章分类名称", 200);		
		columns.add(catogory_name);
//		ColumnConfig catogory_type = new ColumnConfig(IArticleCatagory.TYPE,
//				"分类类型", 150);
//		
//		columns.add(catogory_type);
		ColumnConfig description = new ColumnConfig(IArticleCatagory.DESCRIPTION,
				"描述", 150);
		
		columns.add(description);
		ColumnConfig order = new ColumnConfig(IArticleCatagory.SORTRORDER,
				"排序", 150);
		
		columns.add(order);
		CheckColumnConfig navigation = new CheckColumnConfig(IArticleCatagory.NAVIGATOR,
				"是否显示在导航栏", 150);
		
		columns.add(navigation);
		ColumnConfig actcol1 = new ColumnConfig("Action", Resources.constants
				.GoodsList_action(), 100);
		columns.add(actcol1);
		
		ColumnModel cm = new ColumnModel(columns);
		
        TreeGrid<BeanObject> treeGrid = new TreeGrid<BeanObject>(store, cm) {
            @Override
            protected boolean hasChildren(BeanObject parent) {
                return parent.getProperties().get("children") != null;
            }

            @Override
            protected void onDoubleClick(GridEvent<BeanObject> e) {
                if (e.getRowIndex() != -1) {
                    fireEvent(Events.RowDoubleClick, e);
                    if (e.getColIndex() != -1) {
                        fireEvent(Events.CellDoubleClick, e);
                    }
                }
            }
        };
        
        treeGrid.addListener(Events.Attach, new Listener<GridEvent<BeanObject>>() {
            public void handleEvent(GridEvent<BeanObject> be) {
                loader.load();

            }

        });

        catogory_name.setRenderer(new TreeGridCellRenderer<BeanObject>());

        treeGrid.setBorders(true);
        treeGrid.setSize(400, 400);
        treeGrid.setAutoExpandColumn("name");
        treeGrid.setTrackMouseOver(false);
        treeGrid.addPlugin(navigation);
        this.treeGrid = treeGrid;

        ActionCellRenderer render = new ActionCellRenderer(treeGrid);

        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
        act.setText("编辑 ");
        act.setAction("onManageClick($id)");
        act.setTooltip("编辑");
        render.addAction(act);
        actcol1.setRenderer(render);

        act = new ActionCellRenderer.ActionInfo();
        act.setText(" 删除");
        act.setAction("onDeleteClick($id)");
        act.setTooltip(Resources.constants.Region_action_delete());
        render.addAction(act);
        actcol1.setRenderer(render);

        // TreeCellLinkRender render1 = new TreeCellLinkRender(treeGrid);
        // TreeCellLinkRender.ActionInfo act0 = new
        // TreeCellLinkRender.ActionInfo();
        // act0.setAction("onManageClick($id)");
        // render1.addAction(act0);
        // catogory_name.setRenderer(render1);

        TablePanel.add(treeGrid);
        TablePanel.setFrame(true);
        TablePanel.setCollapsible(true);
        TablePanel.setAnimCollapse(false);
        TablePanel.setButtonAlign(HorizontalAlignment.CENTER);
        TablePanel.setIconStyle("icon-table");

        TablePanel.setLayout(new FitLayout());
        TablePanel.setHeading("文章分类");
        TablePanel.setSize(800, 350);

//        TablePanel.setButtonAlign(HorizontalAlignment.CENTER);
//        TablePanel.setButtonAlign(HorizontalAlignment.CENTER);
//        TablePanel.addButton(new com.extjs.gxt.ui.client.widget.button.Button("添加文章分类",
//                new SelectionListener<ButtonEvent>() {
//                    public void componentSelected(ButtonEvent ce) {
//                        NewArticleCat.State state = new NewArticleCat.State();
//                        state.execute();
//                    }
//                }));
        add(TablePanel);
	}
	
	private native void initJS(ArticleCatogory me) /*-{
	$wnd.onManageClick = function (id) {      
		me.@com.jcommerce.gwt.client.panels.article.ArticleCatogory::editArticleCat(Ljava/lang/Long;)(id);
	};
	$wnd.onDeleteClick = function (id) {
		me.@com.jcommerce.gwt.client.panels.article.ArticleCatogory::deleteArticleCat(Ljava/lang/Long;)(id);
	};
	}-*/;

    private void editArticleCat(Long id) {
        new ReadService().getBean(ModelNames.ARTICLECATAGORY, id, new ReadService.Listener() {
            public void onSuccess(BeanObject bean) {
                NewArticleCat.State state = new NewArticleCat.State();
                state.setArticleCategory(bean);
                state.execute();
            }
        });
    }

    private void deleteArticleCat(final Long id) {
        List<ModelData> children = treeGrid.getSelectionModel().getSelectedItem().getChildren();
        if (children != null && children.size() > 0) {
            MessageBox.alert("DeleteError", "Please first delete the subnodes", null);
        } else {

            Criteria criteria = new Criteria();
            Condition cond = new Condition();
            cond.setField(IArticle.ARTICLECATEGORY);
            cond.setOperator(Condition.EQUALS);
            cond.setValue(id);
            criteria.addCondition(cond);
            new ListService().listBeans(ModelNames.ARTICLE, criteria, new ListService.Listener() {
                public void onSuccess(List<BeanObject> result) {
                    if (result != null && result.size() > 0) {
                        MessageBox.alert("DeleteError", "this category has articles", null);
                    } else {

                        new DeleteService().deleteBean(ModelNames.ARTICLECATAGORY, id, new DeleteService.Listener() {
                            public void onSuccess(Boolean success) {
                                Info.display("Congratulation", "deleted: id=" + id);
                                loader.load();

                            }
                        });
                    }
                }
            });

        }
    }

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "文章分类";
	}
}
