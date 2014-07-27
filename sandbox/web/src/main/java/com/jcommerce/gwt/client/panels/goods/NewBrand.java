/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.client.panels.goods;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.FileUploader;

public class NewBrand extends ContentWidget {    	
    
    private Button btnNew = new Button();    
    private Button btnCancel = new Button();    
    private ColumnPanel contentPanel = new ColumnPanel();
   
//    private boolean editting = false;
//    
//    private BeanObject brand = null;
//    
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
    public static class State extends PageState {
        private BeanObject brand = null;
        String backLabel;
        PageState backPage;
        
        public void setBackPage(String backLabel, PageState backPage) {
            this.backLabel = backLabel;
            this.backPage = backPage;
        }
        
        public String getBackLabel() {
            return backLabel;
        }

        public PageState getBackPage() {
            return backPage;
        }

        public void setBrand(BeanObject brand) {
            this.brand = brand;
            setEditting(brand != null);
        }
        
        public BeanObject getBrand() {
            return brand;
        }

        public String getPageClassName() {
            return NewBrand.class.getName();
        }
        public String getMenuDisplayName() {
            return !isEditting() ? "添加品牌" : "编辑品牌";
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
    private static NewBrand instance; 
    
    public static NewBrand getInstance() {
    	if(instance==null) {
    		instance = new NewBrand();
    	}
    	return instance;
    }
    
    private NewBrand() {
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if(!getCurState().isEditting())
        	return "添加品牌";
        else
            return "编辑品牌"; 
    	
    }
    
//    public void setBrand(BeanObject brand) {
//        this.brand = brand;
//        editting = brand != null;
//    }
//    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
        add(contentPanel);
       
        contentPanel.createPanel(IBrand.NAME, "品牌名称:", new TextBox());
        contentPanel.createPanel(IBrand.SITE, "品牌网址:", new TextBox());
        final FileUploader logoUpload = new FileUploader();
        logoUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
        if (getCurState().isEditting()) {
            logoUpload.setImageInfo(ModelNames.BRAND, getCurState().getBrand().getString(IBrand.ID), IBrand.LOGO);
        }
        contentPanel.createPanel(IBrand.LOGO, "品牌LOGO:", logoUpload);
        contentPanel.createPanel(IBrand.DESC, "品牌描述:", new TextArea());
        contentPanel.createPanel(IBrand.ORDER, "排序:", new TextBox());
        contentPanel.createPanel(IBrand.SHOW, "是否显示:", new CheckBox());
        
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");
        btnCancel.setText("重置");
        panel.add(btnNew);
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);        

        btnNew.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                if (!logoUpload.submit()) {
                    return;
                }
               
                new WaitService(new WaitService.Job() {
                    public boolean isReady() {
                        return logoUpload.isFinish();
                    }

                    public void run() {
                        BeanObject brand = getCurState().getBrand();
                        String id = brand != null ? brand.getString(IBrand.ID) : null;
                        brand = new BeanObject(ModelNames.BRAND, contentPanel.getValues());
                        if (getCurState().isEditting()) {
                            new UpdateService().updateBean(id, brand, null);
                            gotoSuccessPanel();
                        } else {
                            new CreateService().createBean(brand, new CreateService.Listener() {
                                public void onSuccess(String id) {
                                    gotoSuccessPanel();
                                }
                            });
                        }
                    }
                });
            }
        });

        btnCancel.addClickHandler(new ClickHandler() {            
            public void onClick(ClickEvent arg0) {
                contentPanel.clearValues();
            }            
        });        
    }  
    
    public void refresh() {
        BeanObject brand = getCurState().getBrand();
        
        if (brand!=null && brand.getString(IBrand.ID) != null) {            
            Map<String, Object> mapBrand = brand.getProperties();
            contentPanel.updateValues(mapBrand);
        }
        else{
          contentPanel.clearValues();
          getCurState().setBrand(null);
        }
    }

    public void gotoSuccessPanel() {
        Success.State newState = new Success.State();
        if(!getCurState().isEditting()) {
            newState.setMessage(Resources.constants.NewBrand_addSuccessfully());
        } else {
            newState.setMessage(Resources.constants.NewBrand_modifySuccessfully());
        }
        
        NewBrand.State choice1 = new NewBrand.State();
        BrandInfo.State choice2 = new BrandInfo.State();
        newState.addChoice("继续添加品牌", choice1);
        newState.addChoice("察看品牌列表", choice2);
        if (getCurState().getBackPage() != null) {
            newState.addChoice(getCurState().getBackLabel(), getCurState().getBackPage());
        }
        
        newState.execute();
    }
}
