package com.jcommerce.gwt.client.panels.article;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IArticle;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.IndexService;

public class ArticleContentPanel extends ContentWidget{

    public ArticleContentPanel() {
        curState = new State();
    }
    
    public static class State extends PageState {
        private String content;
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
		
		public String getPageClassName() {
			return ArticleContentPanel.class.getName();
		}

		public String getMenuDisplayName() {
			return "文章内容列表";
		}
	}

	public State getCurState() {
		return (State)curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}
	
//	private String content;
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}

	private PagingToolBar toolBar ;
	
	Grid<BeanObject> grid;
	private ContentPanel TablePanel = new ContentPanel();
	Criteria criteria = new Criteria();
	BasePagingLoader loader;
	List<BeanObject> beanObj;
	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		loader= new IndexService().getLoader(getCurState().getContent());
		
		toolBar = new PagingToolBar(10);
		loader.load(0, 10);
		toolBar.bind(loader);
		
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
		ColumnConfig title = new ColumnConfig(IArticle.TITLE,
				"文章标题", 200);
		title.setEditor(new CellEditor(new TextField()));
		columns.add(title);
		
		ColumnConfig articleconent = new ColumnConfig("content",
				"文章内容", 500);
		columns.add(articleconent);
		
		
		ColumnModel cm = new ColumnModel(columns);
		
		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setSelectionModel(smRowSelection);
		grid.setLoadMask(true);
		grid.setBorders(true);
		this.grid = grid;
		
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
		
		add(TablePanel);
	}
	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getName() {
		return "文章内容列表";
	}

}
