package com.jcommerce.gwt.client.panels.article;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IArticle;
import com.jcommerce.gwt.client.model.IArticleCatagory;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.validator.SpaceChecker;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.FileUploader;
import com.jcommerce.gwt.client.widgets.richTextBox.RichTextToolbar;

public class NewArticle extends ContentWidget {
	public static class State extends PageState {
	    private BeanObject article = null;
	    
		public BeanObject getArticle() {
            return article;
        }

        public void setArticle(BeanObject article) {
            this.article = article;
            setEditting(article != null);
        }

        public String getPageClassName() {
			return NewArticle.class.getName();
		}

		public String getMenuDisplayName() {
			return "添加新文章";
		}
	}

	public NewArticle() {
	    curState = new State();
	}
	
	public State getCurState() {
		return (State)curState;
	}

//	public void setArticle(BeanObject article) {
//		this.article = article;
//		this.articleId = article != null ? article.getString(IArticle.ID) : null;
//		editting = article != null;
//		
//		goodsPanel.setArticleId(articleId);
//	}
	
//	private String articleId = null;
//	private boolean editting = false;
	private ColumnPanel contentPanelGeneral = new ColumnPanel();
	private ListBox Art_list = new ListBox();
	private Button btnOK = new Button();
	private Button btnCancel = new Button();
	private GoodsPanel goodsPanel = new GoodsPanel();
	Grid grid = new Grid(2, 1);
//	private BeanObject article = null;
	
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		contentPanelGeneral.createPanel(IArticle.TITLE, "文章标题", new TextBox(), new SpaceChecker(
				"文章标题"));
		contentPanelGeneral.createPanel(IArticle.ARTICLECATEGORY, "文章分类", Art_list,new SpaceChecker(
		"文章分类"));
		
		
		contentPanelGeneral.createPanel(IArticle.OPENTYPE, "是否置顶", new CheckBox());
		contentPanelGeneral.createPanel(IArticle.OPEN, "是否显示", new CheckBox());
		contentPanelGeneral.createPanel(IArticle.AUTHOR, "文章作者", new TextBox());
		contentPanelGeneral.createPanel(IArticle.AUTHOREMAIL, "作者email", new TextBox());
		contentPanelGeneral.createPanel(IArticle.KEYWORDS, "关键字", new TextBox());
		final TextBox txt = new TextBox();
		txt.setText("http://");
		contentPanelGeneral.createPanel(IArticle.LINK, "外部链接", txt);
		
		final FileUploader fileUpload = new FileUploader();
		
		contentPanelGeneral.createPanel(IArticle.FILEURL, "上传文件", fileUpload);
		
		HorizontalPanel h_panel = new HorizontalPanel();
		h_panel.setSpacing(10);
		btnOK.setText("确定");
		btnCancel.setText("重置");
		h_panel.add(btnOK);
		h_panel.add(btnCancel);
		
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("100%");
		tabPanel.setAnimationEnabled(true);
		
		tabPanel.add(contentPanelGeneral, "通用信息");

		// Create the text area and toolbar
		RichTextArea area = new RichTextArea();
		area.setSize("100%", "14em");
		RichTextToolbar toolbar = new RichTextToolbar(area);
		toolbar.setWidth("100%");
		
		
		grid.setStyleName("cw-RichText");
		grid.setWidget(0, 0, toolbar);
		grid.setWidget(1, 0, area);
		
		HTML properties2 = new HTML("properites");
		tabPanel.add(grid, "文章内容");

		tabPanel.add(goodsPanel, "关联商品");
		
		tabPanel.selectTab(0);
		tabPanel.ensureDebugId("cwTabPanel");
		add(tabPanel);
		add(h_panel);
		
		createList();
		
		btnCancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				contentPanelGeneral.clearValues();
				txt.setText("http://");
				//attrPanel.updateValues(null);
			}
		});
		
		
		btnOK.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				
				if (validate()) {
					Date currentTime = new Date();
					Timestamp nowTime = new Timestamp(currentTime.getTime());
					Map<String, Object> argsLeft = contentPanelGeneral.getValues();
					//String content = grid.getText(1, 0);
					RichTextArea wid = (RichTextArea)(grid.getWidget(1, 0));
					String content = wid.getText();
					
					argsLeft.put("addTime", nowTime);
					argsLeft.put("content", content);
					
					final List<String> relatedGoods = goodsPanel.getValues();
					
					if (getCurState().isEditting()) {
					    BeanObject article = getCurState().getArticle();
					    final Long articleId = article != null ? article.getLong(IArticle.ID) : null;
						new UpdateService().updateBean(articleId, new BeanObject(ModelNames.ARTICLE, argsLeft),new UpdateService.Listener() {
							
							@Override
							public void onSuccess(Boolean success) {
								new DeleteService().deleteBean(ModelNames.LINKGOODS, articleId, null);
								createLinkGoods(articleId, relatedGoods);
								
								getCurState().setEditting(false);
								ArticleList.State state = new ArticleList.State();
								state.execute();
								Info.display("恭喜", "完成修改商品信息.");
								
							}
						});
						
						//update related goods
					} else {
						new CreateService().createBean(new BeanObject(ModelNames.ARTICLE, argsLeft), 
							new CreateService.Listener() {
								@Override
								public void onSuccess(String id) {
									createLinkGoods(Long.valueOf(id), relatedGoods);
	                                ArticleList.State state = new ArticleList.State();
	                                state.execute();
									Info.display("恭喜", "完成添加新文章.");
								}									
							});
					}
				}
			}
						
		});
		
	}
	private void createLinkGoods(Long articleID, List<String> relatedGoods) {
		System.out.println("++++++++++++++++++++++++++");
		System.out.println(articleID);
		for(String id : relatedGoods) {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("goods", articleID);
			values.put("linkGoods", id);
			//values.put("bidirectional", relatedPanel.isBidirectional());
			if(!id.equals(articleID))
				new CreateService().createBean(new BeanObject(ModelNames.LINKGOODS, values), null);
		}
		
	}
	private void createList() {
		new ListService().listBeans(ModelNames.ARTICLECATAGORY,
				new ListService.Listener() {
					public void onSuccess(List<BeanObject> result) {
						
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
							Art_list.addItem(name, id);
						}
					}
			});
	}
	
	private boolean validate() {
		List<String> errs = contentPanelGeneral.validate();
		
		if (errs != null && errs.size() > 0) {
			displayError(errs);
			return false;
		}

		return true;
	}
	private void displayError(List<String> errs) {
		StringBuffer sb = new StringBuffer();
		for (String err : errs) {
			sb.append(err).append("<br>");
		}
		MessageBox.alert("ERROR", sb.toString(), null);
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		if (!getCurState().isEditting())
			return "添加新文章";
		else
			return "编辑文章内容";
	}

	public void refresh() {
	    BeanObject article = getCurState().getArticle();
		if (article != null && article.getString(IArticleCatagory.ID) != null) {
			contentPanelGeneral.updateValues(article.getProperties());
			RichTextArea wid = (RichTextArea)grid.getWidget(1, 0);
			wid.setText(article.getString("content"));
//			this.article = null;
		} else {
			contentPanelGeneral.clearValues();
			
			getCurState().setEditting(false);
		}
	}
}
