package com.jcommerce.gwt.client.panels.article;

import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.i18n.client.Messages;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.ArticleForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IArticleCategory;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.MyRadioGroup;

public class ArticlePanel extends BaseEntityEditPanel{
	public static interface Constants{
		String Article_tabgeneral();
		String Article_tabcontent();
		String Article_fltitle();
		String Article_flcategory();
		String Article_flisOpen();
		String Article_fltype();
		String Article_flauthor();
		String Article_flemail();
		String Article_flkeyword();
		String Article_sbtnArticleList();
		String Article_title();
	}
	public interface Message extends Messages{
		String Article_addSuccess();
		String Article_modifySuccess();
		String Article_selectCatWarning();
	}
	
	public static class State extends  BaseEntityEditPanel.State{

		@Override
		public String getPageClassName() {
			return ArticlePanel.class.getName();
		}
	}

	public ArticlePanel() {
	    curState = new State();
	}
	
	private static ArticlePanel instance;
	public static ArticlePanel getInstance(){
		if(instance == null){
			instance = new ArticlePanel();
		}
		return instance;
	}
    
	@Override
	protected State getCurState() {
		return (State)curState;
	}

	@Override
	protected String getEntityClassName() {
		return ModelNames.ARTICLE;
	}

	@Override
	public void gotoSuccessPanel() {
		Success.State newState = new Success.State();
    	if(!getCurState().getIsEdit()) {
    		newState.setMessage(Resources.messages.Article_addSuccess());
    	} else {
    		newState.setMessage(Resources.messages.Article_modifySuccess());
    	}
    	ArticleListPanel.State choice1 = new ArticleListPanel.State();
    	newState.addChoice(ArticleListPanel.getInstance().getName(), choice1);
    	newState.execute();
	}

	@Override
	protected void postSuperRefresh() {
		getArticleCatRefresh();
		
	}
	private void getArticleCatRefresh(){
		new ListService().listBeans(ModelNames.ARTICLECATAGORY, new ListService.Listener(){

			@Override
			public void onSuccess(List<BeanObject> beans) {
				articleCat.removeAll();
				articleCat.add(beans);	
				populateField(articleCatList);
			}
			
		});
	}
	ComboBox<BeanObject> articleCatList = new ComboBox<BeanObject>();
	ListStore<BeanObject> articleCat = new ListStore<BeanObject>();

	@Override
	protected void setupPanelLayout() {
		formPanel.setHeaderVisible(false);
		formPanel.setBodyBorder(false);
		formPanel.setPadding(0);
		//formPanel.setLayout(new FitLayout());
		TabPanel tabs = new TabPanel();
		tabs.setAutoHeight(true);
		formPanel.add(tabs);
		setupGeneralInfo(tabs);
		setupArticleContent(tabs);
		
	}
	
	private void setupGeneralInfo(TabPanel tabs){
		TabItem general = new TabItem();
		general.setStyleAttribute("padding", "10");
		tabs.add(general);
		general.setText(Resources.constants.Article_tabgeneral());
		FormLayout fl = getFormLayout();
		general.setLayout(fl);
		TextField<String> nameField = ArticleForm.getNameField(Resources.constants.Article_fltitle());
		nameField.setFieldLabel(Resources.constants.Article_fltitle());
		general.add(nameField, sfd());
		articleCatList = ArticleForm.getArticleCat("Article Category");
		articleCatList.addSelectionChangedListener(new SelectionChangedListener<BeanObject>(){

			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				BeanObject selectedItem = se.getSelectedItem();
				if(selectedItem.getString(IArticleCategory.TYPE).equals("2")||selectedItem.getString(IArticleCategory.TYPE).equals("4")){
					final MessageBox msgBox = new MessageBox();
					msgBox.addCallback(new Listener<MessageBoxEvent>(){
						public void handleEvent(MessageBoxEvent be) {
							articleCatList.clearSelections();
						}

		            });
					msgBox.setModal(true);
					//msgBox.setTitle("GCShop Warning...");
					msgBox.setMessage(Resources.messages.Article_selectCatWarning());
					msgBox.show();
				}
			}
			
		});
		articleCatList.setFieldLabel(Resources.constants.Article_flcategory());
		articleCatList.setStore(articleCat);
		general.add(articleCatList, sfd());
		MyRadioGroup isOpenField = ArticleForm.getIsOpen();
		isOpenField.setFieldLabel(Resources.constants.Article_flisOpen());
		general.add(isOpenField, tfd());
		MyRadioGroup articleTypeField = ArticleForm.getArticleType();
		articleTypeField.setFieldLabel(Resources.constants.Article_fltype());
		general.add(articleTypeField, tfd());
		TextField<String> authorField = ArticleForm.getAuthorField();
		authorField.setFieldLabel(Resources.constants.Article_flauthor());
		general.add(authorField, tfd());
		TextField<String> authorEmailField = ArticleForm.getAuthorEmail();
		authorEmailField.setFieldLabel(Resources.constants.Article_flemail());
		general.add(authorEmailField, sfd());
		TextField<String> keywordField = ArticleForm.getKeyword();
		keywordField.setFieldLabel(Resources.constants.Article_flkeyword());
		general.add(keywordField, tfd());
		
	}
	
	private void setupArticleContent(TabPanel tabs){
		TabItem content = new TabItem();
		content.setStyleAttribute("padding", "10");
		tabs.add(content);
		content.setText(Resources.constants.Article_tabgeneral());
		FormLayout fl = getFormLayout();
		fl.setHideLabels(true);
		content.setLayout(fl);
		HtmlEditor contentField = ArticleForm.getContent();
		contentField.setHeight(300);
		content.add(contentField, lfd());
	}
	
	public FormLayout getFormLayout() {
        FormLayout fl = new FormLayout();
        fl.setLabelWidth(150);
        fl.setLabelPad(50);
        return fl;
	}
	
	public Button getShortCutButton(){
		Button sButton = new Button(Resources.constants.Article_sbtnArticleList());
		sButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
	          public void componentSelected(ButtonEvent ce) {
	          	onShortCutButtonClicked();
	          }
	      });
		return sButton;
	}
	
	private void onShortCutButtonClicked(){
		ArticleListPanel.State newState = new ArticleListPanel.State();
		newState.execute();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return Resources.constants.Article_title();
	}

}
