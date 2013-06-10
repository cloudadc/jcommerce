package com.jcommerce.gwt.client.panels.goods;

import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.CategoryForm;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.MyRadioGroup;

public class CategoryPanel extends BaseEntityEditPanel {    
	
	public static interface Constants {
        String Category_title();
        String Category_categoryList();
        String Category_name();
        String Category_parentCategory();
        String Category_topCategory();
        String Category_unit();
        String Category_showInNavi();
        String Category_priceNumber();
        String Category_css();
        String Category_yes();
        String Category_no();
        String Category_order();
        String Category_showOrNot();
        String Category_editCategory();
        String Category_priceTip();
        String Category_cssTip();
        String Category_addSuccessfully();
        String Category_modifySuccessfully();
    }
	
	@Override
	public String getEntityClassName() {
		return ModelNames.CATEGORY; 
	}

	public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button(Resources.constants.Category_categoryList());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }
    public void onButtonListClicked() {
		CategoryListPanel.State newState = new CategoryListPanel.State();
		newState.execute();
    }
	ListStore<BeanObject> categoryList;
	ComboBox<BeanObject> fParentId;
	RadioGroup mfIsShow;
	RadioGroup mfShowInNav;
	
	public static class State extends BaseEntityEditPanel.State {
		public static final String SELECTED_PARENT_ID = "parentId";
		
		public String getPageClassName() {
			return CategoryPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.Category_title();
		}
	}
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

    /**
     * Initialize this example.
     */
    public static CategoryPanel getInstance() {
    	if(instance==null) {
    		instance = new CategoryPanel();
    	}
    	return instance;
    }
    private static CategoryPanel instance;
    private CategoryPanel() {
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if(!getCurState().getIsEdit())
			return Resources.constants.Category_title();
		else
			return Resources.constants.Category_editCategory();    	
    }
    

    
    @Override
    public void setupPanelLayout() {
        System.out.println("----------CategoryPanel");

        TextField<String> fText = CategoryForm.getNameField(Resources.constants.Category_name()+"：");
        fText.setFieldLabel(Resources.constants.Category_name());
        formPanel.add(fText, sfd());
        
        categoryList = new ListStore<BeanObject>();
        fParentId = CategoryForm.getParentIdField();
        fParentId.setFieldLabel(Resources.constants.Category_parentCategory());
        fParentId.setStore(categoryList);
        fParentId.setEmptyText(Resources.constants.Category_topCategory());
        formPanel.add(fParentId, sfd());
        
        fText = CategoryForm.getMeasureUnitField();
        fText.setFieldLabel(Resources.constants.Category_unit());
        formPanel.add(fText, sfd());
        
        NumberField fNum = CategoryForm.getSortOrderField();
        fNum.setFieldLabel(Resources.constants.Category_order());
        formPanel.add(fNum, tfd());

        mfIsShow = new MyRadioGroup();
		formPanel.add(mfIsShow, sfd());
		mfIsShow.setFieldLabel(Resources.constants.Category_showOrNot());
		mfIsShow.setName(ICategory.SHOW);

		mfIsShow.setSelectionRequired(true);
		Radio yes = new Radio();
		yes.setName(ICategory.SHOW);
		yes.setValueAttribute("true");
		yes.setBoxLabel(Resources.constants.Category_yes());
		mfIsShow.add(yes);
		
		Radio no = new Radio();
		no.setName(ICategory.SHOW);
		no.setValueAttribute("false");
		no.setBoxLabel(Resources.constants.Category_no());
		mfIsShow.add(no);
        
		

		
		mfShowInNav = new MyRadioGroup();
		mfShowInNav.setFieldLabel(Resources.constants.Category_showInNavi());
		mfShowInNav.setName(ICategory.SHOWINNAVIGATOR);
		mfShowInNav.setSelectionRequired(true);
		yes = new Radio();
		yes.setName(ICategory.SHOWINNAVIGATOR);
		yes.setValueAttribute("1");
		yes.setBoxLabel(Resources.constants.Category_yes());
		mfShowInNav.add(yes);
		
		no = new Radio();
		no.setName(ICategory.SHOWINNAVIGATOR);
		no.setValueAttribute("0");
		no.setBoxLabel(Resources.constants.Category_no());
		mfShowInNav.add(no);
        
		formPanel.add(mfShowInNav, sfd());
		
        fText = CategoryForm.getGradeField();
        fText.setFieldLabel("？"+Resources.constants.Category_priceNumber());
        fText.setToolTip(Resources.constants.Category_priceTip());
        formPanel.add(fText, tfd());
        
        fText = CategoryForm.getStyleField();
        fText.setFieldLabel("？"+Resources.constants.Category_css());
        fText.setToolTip(Resources.constants.Category_cssTip());
        formPanel.add(fText, tfd());

    }   
    

    
    
    @Override
    public void postSuperRefresh() {
    	
		new ListService().listBeans(ModelNames.CATEGORY, new ListService.Listener() {
			@Override
			public void onSuccess(List<BeanObject> beans) {
		    	categoryList.removeAll();
				categoryList.add(beans);
				populateField(fParentId);
			}
		});
		

    }
    
	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();
    	if(!getCurState().getIsEdit()) {
    		newState.setMessage(Resources.constants.Category_addSuccessfully());
    	} else {
    		newState.setMessage(Resources.constants.Category_modifySuccessfully());
    	}
    	
    	CategoryListPanel.State choice1 = new CategoryListPanel.State();
    	newState.addChoice(CategoryListPanel.getInstance().getName(), choice1);
    	
    	newState.execute();
	}
}
