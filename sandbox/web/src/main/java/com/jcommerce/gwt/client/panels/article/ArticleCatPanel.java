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
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.ArticleCatForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.member.UserListPanel;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.MyRadioGroup;

public class ArticleCatPanel extends BaseEntityEditPanel{
	public static interface Constants {
		String ArticleCat_name();
		String ArticleCat_parentCat();
		String ArticleCat_topCat();
		String ArticleCat_sortOrder();
		String ArticleCat_isInNav();
		String ArticleCat_keyword();
		String ArticleCat_desc();
		String ArticleCat_add_success();
		String ArticleCat_modify_success();
		String ArticleCat_add();
	}
	
	public ArticleCatPanel() {
	    curState = new State();
	}
	
	public static class State extends BaseEntityEditPanel.State{
		@Override
		public String getPageClassName() {
			// TODO Auto-generated method stub
			return ArticleCatPanel.class.getName();
		}
	}

	@Override
	protected State getCurState() {
		return (State)curState;
	}
	
	private static ArticleCatPanel instance;
	public static ArticleCatPanel getInstance(){
		if(instance == null){
			instance = new ArticleCatPanel();
		}
		return instance;
	}

	@Override
	protected String getEntityClassName() {
		// TODO Auto-generated method stub
		return ModelNames.ARTICLECATAGORY;
	}
	
	ListStore<BeanObject> articleCat;
	ComboBox<BeanObject> fListArticleCat;
	HiddenField catType;

	@Override
	protected void postSuperRefresh() {
		
		if(!getCurState().getIsEdit()){
			catType.setValue("1");
		}
		if(!catType.getValue().toString().equals("1")&&!catType.getValue().toString().equals("5")){
			fListArticleCat.setReadOnly(true);
		}
		else {
			fListArticleCat.setReadOnly(false);
		}
		    	
		new ListService().listBeans(ModelNames.ARTICLECATAGORY, new ListService.Listener() {
			@Override
			public void onSuccess(List<BeanObject> beans) {
		    	articleCat.removeAll();
		    	articleCat.add(beans);
				populateField(fListArticleCat);
			}
		});
		
	}

	@Override
	protected void setupPanelLayout() {
		System.out.println("=============AticleCatPanel============");
		TextField<String> tf = ArticleCatForm.getNameField(Resources.constants.ArticleCat_name()+": ");
		tf.setFieldLabel(Resources.constants.ArticleCat_name());
		formPanel.add(tf, sfd());
		catType = ArticleCatForm.getCatType();
		formPanel.add(catType);
		articleCat = new ListStore<BeanObject>();
		fListArticleCat = ArticleCatForm.getParentCatField();
		fListArticleCat.addSelectionChangedListener(new SelectionChangedListener<BeanObject>(){

			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				final MessageBox msgBox = new MessageBox();
				msgBox.addCallback(new Listener<MessageBoxEvent>(){
					public void handleEvent(MessageBoxEvent be) {
						fListArticleCat.clearSelections();
					}
	            });
				msgBox.setModal(true);
				BeanObject selectedCat = se.getSelectedItem();
				String curArticleCat = (String) catType.getValue();
				if(selectedCat.getString(ArticleCatForm.TYPE).equals("4")){
					catType.setValue("5");
				}
				else if(selectedCat.getString(ArticleCatForm.TYPE).equals("1")){
					catType.setValue("1");
				}
				else if(selectedCat.getString(ArticleCatForm.TYPE).equals("2")){
					if(catType.getValue().toString().equals("3")||catType.getValue().toString().equals("4")){
						return;
					}
					else {
						msgBox.setMessage("Can't add other child category for this system category");
						msgBox.show();
					}
				}
				else {
					//3,5can't have child.
					//msgBox.setTitle("GCShop Warning...");
					msgBox.setMessage("Can't add child category for this category");
					msgBox.show();
				}
			}
		});
		fListArticleCat.setStore(articleCat);
		fListArticleCat.setEmptyText(Resources.constants.ArticleCat_topCat());
		fListArticleCat.setFieldLabel(Resources.constants.ArticleCat_parentCat());
		formPanel.add(fListArticleCat, sfd());
		
		NumberField nf = ArticleCatForm.getOrderField();
		nf.setFieldLabel(Resources.constants.ArticleCat_sortOrder());
		formPanel.add(nf,tfd());
		
		MyRadioGroup rg = ArticleCatForm.getIsShowInNavField();
		rg.setFieldLabel(Resources.constants.ArticleCat_isInNav());
		formPanel.add(rg,sfd());
		
		TextField<String> keyword = ArticleCatForm.getKeywordField();
		keyword.setFieldLabel(Resources.constants.ArticleCat_keyword());
		formPanel.add(keyword,sfd());
		
		TextArea ta = ArticleCatForm.getDescField();
		ta.setFieldLabel(Resources.constants.ArticleCat_desc());
		formPanel.add(ta,lfd());
		
	}
	
	public Button getShortCutButton(){
		Button sButton = new Button("文章分类列表");
		sButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
	          public void componentSelected(ButtonEvent ce) {
	          	onShortCutButtonClicked();
	          }
	    });
		return sButton;
	}
	
	private void onShortCutButtonClicked(){
		ArticleCatListPanel.State newState = new ArticleCatListPanel.State();
		newState.execute();
	}
	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();

    	if(!getCurState().getIsEdit()) {
    		newState.setMessage(Resources.constants.ArticleCat_add_success());
    	} else {
    		newState.setMessage(Resources.constants.ArticleCat_modify_success());
    	}
    	
    	ArticleCatListPanel.State choice1 = new ArticleCatListPanel.State();
    	newState.addChoice(ArticleCatListPanel.getInstance().getName(), choice1);
    	
    	newState.execute();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return Resources.constants.ArticleCat_add();
	}

}
