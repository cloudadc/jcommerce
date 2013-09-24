package com.jcommerce.gwt.client.panels.article;

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
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
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
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;


public class ArticleList extends ContentWidget{
    
	public static class State extends PageState {
		
        
		public String getPageClassName() {
			return ArticleList.class.getName();
		}

		public String getMenuDisplayName() {
			return "文章列表";
		}

		public void setArticleType(String articleType) {
			setValue("articleType", articleType);
		}

		public String getArticleType() {
			return (String) getValue("articleType");
		}
		
	}

    public ArticleList() {
        curState = new State();
        initJS(this);
    }

    
    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getName() {
        return "文章列表";
    }

	public State getCurState() {
		return (State)curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}
	
	
	private PagingToolBar toolBar ;
	ListBox lstCategory = new ListBox();
	Button btnFind = new Button("搜索");
	Button btnFind1 = new Button("搜索");
	TextBox txtKeyword = new TextBox();
	TextBox txtContent = new TextBox();
	Grid<BeanObject> grid;
	private ContentPanel TablePanel = new ContentPanel();
	Criteria criteria = new Criteria();
	BasePagingLoader loader;
	List<BeanObject> beanObj;
	
	private void search(String cat){
		criteria.removeAll();
		//Criteria criteria;
		if (lstCategory.getSelectedIndex() == 0) {
	    	//criteria = null;
	    	String keyword = txtKeyword.getText();
			if (keyword != null && keyword.trim().length() > 0) {
				
				//criteria = new Criteria();
				Condition cond = new Condition();
				cond.setField(IArticle.TITLE);
				cond.setOperator(Condition.LIKE);
				cond.setValue(keyword.trim());
				criteria.addCondition(cond);
				//this.criteria = criteria;
				((PagingListService.MyProxy)loader.getProxy()).setCriteria(criteria);	
			}else{
				((PagingListService.MyProxy)loader.getProxy()).setCriteria(null);	
			}
			
			    	
	    }else{
	    	//criteria = new Criteria();
			Condition cond = new Condition();
			cond.setField(IArticle.ARTICLECATEGORY);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(cat);
			criteria.addCondition(cond);
			String keyword = txtKeyword.getText();
			if (keyword != null && keyword.trim().length() > 0) {
				Condition cond1 = new Condition();
				cond1.setField(IArticle.TITLE);
				cond1.setOperator(Condition.LIKE);
				cond1.setValue(keyword.trim());
				criteria.addCondition(cond1);
				
			}
			//this.criteria = criteria;
			((PagingListService.MyProxy)loader.getProxy()).setCriteria(criteria);
	    }
		
		//((PagingListService.MyProxy)loader.getProxy()).setCriteria(criteria);
	    	    	    
	    toolBar.refresh();
	    
	}
	
    public String getButtonText() {
        return "添加文章";
    }
    
    protected void buttonClicked() {
        NewArticle.State state = new NewArticle.State();
        state.execute();
    }
        
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(lstCategory);				
		header.add(new Label("  " + "文章标题"));
		header.add(txtKeyword);
		header.add(btnFind);
		
		add(header);
		
		lstCategory.addItem("全部分类");
		new ListService().listBeans(ModelNames.ARTICLECATAGORY,
				new ListService.Listener() {
					public void onSuccess(List<BeanObject> result) {
						beanObj = result;
						List<String> pids = new ArrayList<String>();
						for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
							BeanObject cat = it.next();
							String name = cat.getString(IArticleCatagory.NAME);
							String id = cat.getString(IArticleCatagory.ID);
							String _pid = cat.getString(IArticleCatagory.PARENT);
							if (_pid == null) {
								pids.clear();
							} else if (!pids.contains(_pid)) {
								pids.add(_pid);
							}
							int level = pids.indexOf(_pid) + 1;
							for (int i = 0; i < level; i++) {
								name = "  " + name;
							}
							lstCategory.addItem(name, id);
						}
					}
			});
		btnFind.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				
				String cat = lstCategory.getValue(lstCategory.getSelectedIndex());								
				getCurState().setArticleType(cat);
				search(cat);												
			}
		});
		
		btnFind1.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				//new IndexService().getLoader(txtContent.getValue());
				if(txtContent.getValue()!=null && txtContent.getValue()!="") {
					ArticleContentPanel.State state = new ArticleContentPanel.State();
					state.setContent(txtContent.getValue());
					state.execute();
				} else {
					MessageBox.alert("error", "内容不能为空", null);
				}
			}
		});
		
//		String articleType = getCurState().getArticleType();
//		if(articleType == null){
//	 		articleType = "全部分类";
//		}
		
		//Criteria criteria;
//		
//		if(articleType == null){
//			criteria = null;
//		}else{
//			criteria = new Criteria();
//			Condition cond = new Condition();
//			cond.setField(IArticle.ARTICLECATEGORY);
//			cond.setOperator(Condition.EQUALS);
//			cond.setValue(articleType);
//			criteria.addCondition(cond);
//			
//			
//		}
		//this.criteria = criteria;	
		loader= new PagingListService().getLoader(
				ModelNames.ARTICLE);
		toolBar = new PagingToolBar(10);
		loader.load(0, 10);
		toolBar.bind(loader);
		
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		
		store.addStoreListener(new StoreListener<BeanObject>() {
			public void storeUpdate(StoreEvent<BeanObject> se) {
				List<Record> changed = store.getModifiedRecords();
				for (Record rec : changed) {
					BeanObject bean = (BeanObject) rec.getModel();
					String cat_name = bean.getString("articleCategory");
					bean.remove("articleCategory");
					for (Iterator<BeanObject> it = beanObj.iterator(); it.hasNext();) {
						BeanObject cat = it.next();
						if(cat.getString(IArticleCatagory.NAME) == cat_name){
							bean.set("articleCategory", cat.getString(IArticleCatagory.ID));
							
							break;
						}
					}
					new UpdateService().updateBean(bean.getString(IArticle.ID), bean, null);
					
					bean.remove("articleCategory");
					bean.set("articleCategory", cat_name);
					
				}
			}
		});
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		ColumnConfig title = new ColumnConfig(IArticle.TITLE,
				"文章标题", 200);
		title.setEditor(new CellEditor(new TextField()));
		columns.add(title);
		ColumnConfig category = new ColumnConfig(IArticle.ARTICLECATEGORY,
				"文章分类", 100);
		columns.add(category);
		CheckColumnConfig openType = new CheckColumnConfig(IArticle.OPENTYPE,
				"是否置顶", 80);
		columns.add(openType);
		CheckColumnConfig open = new CheckColumnConfig(IArticle.OPEN,
				"是否显示", 80);
		columns.add(open);
		ColumnConfig addType = new ColumnConfig(IArticle.ADDTIME,
				"添加日期", 150);
		columns.add(addType);
		
		ColumnConfig actcol1 = new ColumnConfig("Action", Resources.constants
				.GoodsList_action(), 150);
		columns.add(actcol1);
		ColumnModel cm = new ColumnModel(columns);
		
		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setSelectionModel(smRowSelection);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.addPlugin(open);
		grid.addPlugin(openType);
		this.grid = grid;
		
		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		
		act.setText("查看 ");
		act.setAction("onViewClick($id)");
		act.setTooltip("查看  ");
		render.addAction(act);
		
		act = new ActionCellRenderer.ActionInfo();
		act.setText("编辑  ");
		act.setAction("editArticle($id)");
		act.setTooltip("编辑");
		render.addAction(act);
		
		act = new ActionCellRenderer.ActionInfo();
		act.setText("删除");
		act.setAction("deleteArticle($id)");
		act.setTooltip("删除");
		render.addAction(act);
		
		actcol1.setRenderer(render);
		
		TablePanel.add(grid);
		TablePanel.setFrame(true);
		TablePanel.setCollapsible(true);
		TablePanel.setAnimCollapse(false);
		TablePanel.setButtonAlign(HorizontalAlignment.CENTER);
		TablePanel.setIconStyle("icon-table");

		TablePanel.setLayout(new FitLayout());
		
		TablePanel.setSize(800, 350);
		TablePanel.setBottomComponent(toolBar);
		TablePanel.setButtonAlign(HorizontalAlignment.CENTER);

		TablePanel.addButton(new Button("重置",
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						store.rejectChanges();
					}
				}));

		TablePanel.addButton(new Button("保存",
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						store.commitChanges();
					}
				}));
		TablePanel.addButton(new Button("批量删除",
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						deleteArticles();
					}
				}));

//		TablePanel.addButton(new Button("添加新文章",
//				new SelectionListener<ButtonEvent>() {
//					public void componentSelected(ButtonEvent ce) {
//						NewArticle.State state = new NewArticle.State();
//						state.execute();
//					}
//				}));
		
		add(TablePanel);
		
		HorizontalPanel header1 = new HorizontalPanel();
		header1.add(Resources.images.icon_search().createImage());
				
		header1.add(new Label("  " + "文章内容"));
		header1.add(txtContent);
		header1.add(btnFind1);
		
		add(header1);
	}
	private void deleteArticles(){
		List<BeanObject> objs = grid.getSelectionModel().getSelectedItems();
		for (Iterator<BeanObject> it = objs.iterator(); it.hasNext();) {
			BeanObject obj = it.next();
			String id = obj.getString(IArticle.ID);
			deleteArticle(id);
		}
	}
	private native void initJS(ArticleList me) /*-{
	   $wnd.deleteArticle = function (id) {
	       me.@com.jcommerce.gwt.client.panels.article.ArticleList::deleteArticle(Ljava/lang/String;)(id);
	   };
	   $wnd.editArticle = function (id) {
	       me.@com.jcommerce.gwt.client.panels.article.ArticleList::editArticle(Ljava/lang/String;)(id);
	   };
	   }-*/;
	
	private void deleteArticle(String id) {
		new DeleteService().deleteBean(ModelNames.ARTICLE, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						toolBar.refresh();
					}
				});
	}
	private void editArticle(String id){
		new ReadService().getBean(ModelNames.ARTICLE, id,
				new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
                        NewArticle.State state = new NewArticle.State();
                        state.setArticle(bean);
                        state.execute();
					}
				});
	}
	
}
