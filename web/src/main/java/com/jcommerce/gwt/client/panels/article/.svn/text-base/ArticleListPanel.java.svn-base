package com.jcommerce.gwt.client.panels.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.ArticleCatForm;
import com.jcommerce.gwt.client.form.ArticleForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.GWTHttpDynaForm;
import com.jcommerce.gwt.client.model.IArticleCategory;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.TimeCellRenderer;

public class ArticleListPanel extends ContentWidget implements URLConstants{
	public static interface Constants{
		String Article_coltitle();
		String Article_colarticleCat();
		String Article_colarticleType();
		String Article_colisOpen();
		String Article_coladdTime();
		String Article_tipView();
		String Article_btnaddArticle();
		String Article_listTitle();
		String Article_menuName();
		String Article_btnsearch();
		String Article_lblarticleTitle();
		String Article_lblarticleCat();

	}
	
	public static class State extends PageState{
		

		@Override
		public String getPageClassName() {
			// TODO Auto-generated method stub
			return ArticleListPanel.class.getName();
		}

		public String getMenuDisplayName(){
			return Resources.constants.Article_menuName();
		}
		
	}
	
	private ArticleListPanel(){
	    curState = new State();
		initJS(this);
	}
	private native void initJS(ArticleListPanel me) /*-{
		$wnd.viewArticle = function(id){
			window.open('/web/front/article.action?id='+id);
		};
		$wnd.editArticle = function(id){
			me.@com.jcommerce.gwt.client.panels.article.ArticleListPanel::editArticle(Ljava/lang/String;)(id);
		};
		$wnd.deleteArticle = function(id){
			me.@com.jcommerce.gwt.client.panels.article.ArticleListPanel::deleteArticle(Ljava/lang/String;)(id);
		};
    }-*/;
	
	private static ArticleListPanel instance;
	public static ArticleListPanel getInstance(){
		if(instance == null){
			instance = new ArticleListPanel();
		}
		return instance;
	}
	
	PagingToolBar toolBar;
	BasePagingLoader loader = null;
	int pageSize = 5;

	ListStore<BeanObject> articleCatList = new ListStore<BeanObject>();
	ComboBox<BeanObject> articleCat = new ComboBox<BeanObject>();
	TextField<String> articleTitle = new TextField<String>();
	Button btnFind = new Button(Resources.constants.Article_btnsearch());
	
	
	protected void onRender(Element parent,int index){
		super.onRender(parent,index);
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label(Resources.constants.Article_lblarticleCat()));
		articleCat.setStyleAttribute("margin", "0 4 0 4");
		//articleCat.setReadOnly(true);
		articleCat.setDisplayField(IArticleCategory.NAME);
		articleCat.setValueField(IArticleCategory.ID);
		articleCat.setTriggerAction(TriggerAction.ALL);
		articleCat.setStore(articleCatList);
		header.add(articleCat);
		header.add(new Label(Resources.constants.Article_lblarticleTitle()));
		articleTitle.setStyleAttribute("margin", "0 4 0 4");
		header.add(articleTitle);
		header.add(btnFind);
		add(header);
		
//		List<String> wantedFields = new ArrayList<String>();
//		wantedFields.add(ArticleForm.ID);
//		wantedFields.add(ArticleForm.TITLE);
//		wantedFields.add(ArticleForm.ARTICLE_TYPE);
//		wantedFields.add(ArticleForm.CAT_ID);
//		wantedFields.add(ArticleForm.IS_OPEN);
//		wantedFields.add(ArticleForm.ADD_TIME);
		Map<String,List<String>> wantedFields = new HashMap<String,List<String>>();
		String model = ModelNames.ARTICLE;
		List<String> fields = new ArrayList<String>();
		fields.add(ArticleForm.ID);
		fields.add(ArticleForm.TITLE);
		fields.add(ArticleForm.ARTICLETYPE);
		fields.add(ArticleForm.ARTICLECATEGORY);
		fields.add(ArticleForm.OPEN);
		fields.add(ArticleForm.ADDTIME);
		wantedFields.put(model, fields);
		model = ModelNames.ARTICLECATAGORY;
		fields = new ArrayList<String>();
		fields.add(ArticleCatForm.ID);
		fields.add(ArticleCatForm.NAME);
		wantedFields.put(model, fields);
		
		loader = new PagingListService().getLoader(
		  ModelNames.ARTICLE,wantedFields);
		final ListStore<BeanObject> store= new ListStore<BeanObject>(loader);
		store.addStoreListener(new StoreListener<BeanObject>(){
			public void storeUpdate(StoreEvent<BeanObject> se){
				List<Record> changed = store.getModifiedRecords();
				for (Record rec : changed) {
					BeanObject bean = (BeanObject) rec.getModel();
					//updateUser(bean);
				}
			}
		});
		
		toolBar = new PagingToolBar(pageSize);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		ColumnConfig colname = new ColumnConfig(ArticleForm.TITLE, Resources.constants.Article_coltitle(), 100);
		colname.setEditor(new CellEditor(ArticleForm.getNameField(Resources.constants.Article_coltitle())));
		columns.add(colname);
		ColumnConfig colcat = new ColumnConfig(ArticleCatForm.NAME, Resources.constants.Article_colarticleCat(),150);
		columns.add(colcat);
		ColumnConfig coltype = new ColumnConfig(ArticleForm.ARTICLETYPE, Resources.constants.Article_colarticleType(),150);
		columns.add(coltype);
		coltype.setRenderer(new GridCellRenderer<BeanObject>(){

			public Object render(BeanObject model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanObject> store, Grid<BeanObject> grid) {
				//convert the Text.

				return null;
			}
			
		});
		columns.add(new CheckColumnConfig(ArticleForm.OPEN, Resources.constants.Article_colisOpen(), 150));
		ColumnConfig coltime = new ColumnConfig(ArticleForm.ADDTIME, Resources.constants.Article_coladdTime(), 160);
		columns.add(coltime);
		ColumnConfig colaction = new ColumnConfig("Action", Resources.constants.action(), 100);
		//colpayPoint.setEditor(new CellEditor(new TextField()));
		columns.add(colaction);
		ColumnModel cm = new ColumnModel(columns);
		final Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setAutoExpandColumn(ArticleForm.TITLE);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage(GWT.getModuleBaseURL()+"icon_view.gif");
		act.setAction("viewArticle($id)");
		act.setTooltip(Resources.constants.Article_tipView());
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();
		act.setImage(GWT.getModuleBaseURL()+"icon_edit.gif");
		act.setTooltip(Resources.constants.edit());
		act.setAction("editArticle($id)");
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();
		act.setImage(GWT.getModuleBaseURL()+"icon_drop.gif");
		act.setTooltip(Resources.constants.delete());
		act.setAction("deleteArticle($id)");
		render.addAction(act);
		colaction.setRenderer(render);
		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		//        panel.setHeading("Paging Grid");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setHeight(350);
		panel.setBottomComponent(toolBar);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		add(panel);
	}
	
	public Button getShortCutButton(){
		Button sButton = new Button("Add Article");
	      sButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
	          public void componentSelected(ButtonEvent ce) {
	          	onShortCutButtonClicked();
	          }
	      });
	     return sButton;
	}
	
	public void onShortCutButtonClicked(){
		ArticlePanel.State newState = new ArticlePanel.State();
		newState.setIsEdit(false);
		newState.execute();
	}
	
	public void refresh(){
		refreshArticleCat();
		refreshArticleList();
		
	}
	
	private void refreshArticleCat(){
		new ListService().listBeans(ModelNames.ARTICLECATAGORY, new ListService.Listener() {
			
			@Override
			public void onSuccess(List<BeanObject> beans) {
				//add the "all" option.
				articleCatList.removeAll();
				articleCatList.add(beans);
			}
		});
		
	}
	
	private void refreshArticleList(){
		toolBar.refresh();
	}
	
	
	private void editArticle(String id){
		ArticlePanel.State newState= new ArticlePanel.State();
		newState.setIsEdit(true);
		newState.setId(id);
		newState.execute();
	}
	
	private void deleteArticle(String id){
		new DeleteService().deleteBean(ModelNames.ARTICLE, id, new DeleteService.Listener() {
			
			@Override
			public void onSuccess(Boolean success) {
				if(success){
					toolBar.refresh();
				}
			}
		});
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return Resources.constants.Article_listTitle();
	}

}
