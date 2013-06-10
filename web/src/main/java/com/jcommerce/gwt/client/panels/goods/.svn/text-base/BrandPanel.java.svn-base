package com.jcommerce.gwt.client.panels.goods;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.dom.client.InputElement;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BrandForm;
import com.jcommerce.gwt.client.form.GWTHttpDynaForm;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.BaseFileUploadFormPanel;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;

/**
 * Example file.
 */
public class BrandPanel extends BaseFileUploadFormPanel {    	
    
	public static interface Constants {
		String NewBrand_title();
		String NewBrand_editBrand();
		String NewBrand_brandList();
		String NewBrand_name();
		String NewBrand_site();
		String NewBrand_LOGO();
		String NewBrand_description();
		String NewBrand_order();
		String NewBrand_showOrNot();
        String NewBrand_addSuccessfully();
        String NewBrand_modifySuccessfully();
    }
	
	@Override
	public String getEntityClassName() {
		return ModelNames.BRAND; 
	}

	public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button(Resources.constants.NewBrand_brandList());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }
    public void onButtonListClicked() {
        BrandInfo.State newState = new BrandInfo.State();
		newState.execute();
    }
    /**
     * Initialize this example.
     */
    public static BrandPanel getInstance() {
    	if(instance==null) {
    		instance = new BrandPanel();
    	}
    	return instance;
    }
    private static BrandPanel instance; 
    private BrandPanel() {
    }
    
	public static class State extends BaseEntityEditPanel.State {
		@Override
		public String getPageClassName() {
			return BrandPanel.class.getName();
		}
		
		public String getMenuDisplayName() {
			return Resources.constants.NewBrand_title();
		}
	}
	
	@Override
	public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
	}

    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if(!getCurState().getIsEdit())
        	return Resources.constants.NewBrand_title();
        else
            return Resources.constants.NewBrand_editBrand(); 
    	
    }
    
//    public void setBrand(BeanObject brand) {
//        this.brand = brand;
//        editting = brand != null;
//    }
    
    HiddenField<String> idField;
    FileUploadField fufLogo;
    
    @Override
    public void setupPanelLayout() {
    	idField = BrandForm.getIdField();
    	formPanel.add(idField);
    	
        TextField<String> nameField = BrandForm.getNameField(Resources.constants.NewBrand_name()+"：");
        nameField.setFieldLabel(Resources.constants.NewBrand_name());
        formPanel.add(nameField, sfd());
        
        TextField<String> siteField = BrandForm.getSiteField(Resources.constants.NewBrand_site()+"：");
        siteField.setFieldLabel(Resources.constants.NewBrand_site());
        formPanel.add(siteField, lfd());
        
        fufLogo = BrandForm.getLogoField(Resources.constants.NewBrand_LOGO()+"：");
        fufLogo.setFieldLabel(Resources.constants.NewBrand_LOGO());
        formPanel.add(fufLogo, sfd());
        
        HiddenField<String> hfLogoFileId = new HiddenField<String>();
        hfLogoFileId.setName(IBrand.LOGO);
        formPanel.add(hfLogoFileId,sfd());
        
        TextArea descField = BrandForm.getDescField(Resources.constants.NewBrand_description()+"：");
        descField.setHeight("180px");
        descField.setWidth("100px");        
        descField.setFieldLabel(Resources.constants.NewBrand_description());
        formPanel.add(descField, lfd());
        
        TextField<String> orderField = BrandForm.getOrderField(Resources.constants.NewBrand_order()+"：");
        orderField.setFieldLabel(Resources.constants.NewBrand_order());
        formPanel.add(orderField, sfd());
        
        CheckBox showField = BrandForm.getShowField(Resources.constants.NewBrand_showOrNot()+"：");
        showField.setFieldLabel(Resources.constants.NewBrand_showOrNot());
        formPanel.add(showField, sfd());
        

        
//        formPanel.createPanel(IBrand.NAME, "品牌名称:", new TextBox());
//        formPanel.createPanel(IBrand.SITE, "品牌网址:", new TextBox());
//        final FileUploader logoUpload = new FileUploader();
//        logoUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
//        formPanel.createPanel(IBrand.LOGO, "品牌LOGO:", logoUpload);
//        formPanel.createPanel(IBrand.DESC, "品牌描述:", new TextArea());
//        formPanel.createPanel(IBrand.ORDER, "排序:", new TextBox());        
//        formPanel.createPanel(IBrand.SHOW, "是否显示:", new CheckBox());
        
//        HorizontalPanel panel = new HorizontalPanel();
//        panel.setSpacing(10);

//        formPanel.setAction(GWT.getModuleBaseURL() + "uploadService?class=Brand");

//        btnNew.addSelectionListener(new SelectionListener<ButtonEvent>() {
//        	public void componentSelected(ButtonEvent sender) {
//        		formPanel.submit();
//        		
//            }
//        });
    }  
    
    @Override
    public void postSuperRefresh() {
    	String action="com.jcommerce.gwt.server.BrandGWTAction";
    	String method="";
    	
    	if(getCurState().getIsEdit()) {
    		method = "update";
    		idField.setValue(getCurState().getId());
    	}else {
    		method = "add";
    		idField.setValue(null);
    	}
    	formPanel.setAction(GWTHttpDynaForm.constructURL(action, method));
    	
    	boolean enabled = fufLogo.isEnabled();
    	boolean isreadonly = fufLogo.isReadOnly();
    	
    	System.out.println("isenabled? "+enabled+", readonly? "+isreadonly);
    	fufLogo.reset();
    	fufLogo.setValue("");
    	isreadonly = fufLogo.isReadOnly();
    	enabled = fufLogo.isEnabled();
    	System.out.println("isenabled? "+enabled+", readonly? "+isreadonly);
    	InputElement ele = fufLogo.getFileInput();
    	if(ele!=null) {
    		String val = ele.getValue();
    		String type = ele.getType();
    		String name = ele.getName();
    		System.out.println("val: +"+val+", type="+type+", name="+name);
    	}
    	fufLogo.setEnabled(true);
    	fufLogo.setReadOnly(false);

    }
    
	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();
    	if(!getCurState().getIsEdit()) {
    		newState.setMessage(Resources.constants.NewBrand_addSuccessfully());
    	} else {
    		newState.setMessage(Resources.constants.NewBrand_modifySuccessfully());
    	}
    	
    	BrandInfo.State choice1 = new BrandInfo.State();
    	newState.addChoice(new BrandInfo().getName(), choice1);
    	
    	newState.execute();
	}
}
