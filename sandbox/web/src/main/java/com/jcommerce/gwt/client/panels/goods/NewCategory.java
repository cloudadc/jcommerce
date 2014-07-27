package com.jcommerce.gwt.client.panels.goods;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.panels.system.Success;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * Example file.
 */
public class NewCategory extends ContentWidget {    	
	private ListBox c_parent = new ListBox();
    private Button btnNew = new Button();    
    private Button btnCancel = new Button();    
    private ColumnPanel contentPanel = new ColumnPanel();
    
    private Map<String, BeanObject> categorys = new HashMap<String, BeanObject>();

//    private boolean editting = false;
//    private BeanObject category = null;
    
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
    public static class State extends PageState {
        private BeanObject category = null;
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

        
        public void setCategory(BeanObject category) {
            this.category = category;
            setEditting(category != null);
        }
        
        public BeanObject getCategory() {
            return category;
        }

        public String getPageClassName() {
            return NewCategory.class.getName();
        }
        public String getMenuDisplayName() {
            return !isEditting() ? "添加属性" : "编辑属性";
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
    public static NewCategory getInstance() {
    	if(instance==null) {
    		instance = new NewCategory();
    	}
    	return instance;
    }
    private static NewCategory instance;
    private NewCategory() {
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if (!getCurState().isEditting())
			return "添加分类";
		else
			return "编辑分类";    	
    }
    
//    public void setCategory(BeanObject category) {
//        this.category = category;
//        editting = category != null;
//    }
//    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        add(contentPanel);
       
        contentPanel.createPanel(ICategory.NAME, "分类名称:", new TextBox());                        
        contentPanel.createPanel(ICategory.PARENT, "上级分类:", c_parent);        
        contentPanel.createPanel(ICategory.MEASUREUNIT, "数量单位:", new TextBox());
        contentPanel.createPanel(ICategory.SORTORDER, "排序:", new TextBox());
        contentPanel.createPanel(ICategory.SHOW, "是否显示:", new CheckBox());
        contentPanel.createPanel(ICategory.SHOWINNAVIGATOR, "是否显示在导航栏:", new CheckBox());
        contentPanel.createPanel(ICategory.GRADE, "价格区间个数:", new TextBox());
        contentPanel.createPanel(ICategory.STYLE, "分类的样式表文件:", new TextBox());
        contentPanel.createPanel(ICategory.KEYWORDS, "关键字:", new TextBox());
        contentPanel.createPanel(ICategory.DESC, "分类描述:", new TextArea());
        
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");        
        btnCancel.setText("重置");
        panel.add(btnNew);        
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);      
        
        btnNew.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                BeanObject category = getCurState().getCategory();
                String id = category != null ? category.getString(ICategory.ID) : null;
                category = new BeanObject(ModelNames.CATEGORY, contentPanel.getValues());
                if (getCurState().isEditting()) {
                    new UpdateService().updateBean(id, category, null);
                    gotoSuccessPanel();
                } else {
                    new CreateService().createBean(category, new CreateService.Listener() {
                        public synchronized void onSuccess(String id) {
                            gotoSuccessPanel();
                        }
                    });
                }
            }
        });

        btnCancel.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                contentPanel.clearValues();
            }            
        });        
    }   
    
    public void refresh() {
    	c_parent.clear();
    	c_parent.addItem("");
        
        new ListService().listBeans(ModelNames.CATEGORY, new ListService.Listener() {            
            public synchronized void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject category = it.next();                    
                    c_parent.addItem(category.getString(ICategory.NAME), category.getString(ICategory.ID));
                }               
            }
        });
        
        BeanObject category = getCurState().getCategory();
        if ( category!=null&& category.getString(ICategory.ID) != null) {
            categorys.put(category.getString(ICategory.ID), category);
            Map<String, Object> mapCategory = category.getProperties();
            contentPanel.updateValues(mapCategory);
        }
        else{
        	contentPanel.clearValues();
            getCurState().setCategory(null);
            }
    }

    public void gotoSuccessPanel() {
        Success.State newState = new Success.State();
        if(!getCurState().isEditting()) {
            newState.setMessage("成功添加商品分类");
        } else {
            newState.setMessage("成功编辑商品分类");
        }
        
        NewCategory.State choice1 = new NewCategory.State();
        CategoryInfo.State choice2 = new CategoryInfo.State();
        newState.addChoice("继续添加商品分类", choice1);
        newState.addChoice("察看商品分类列表", choice2);
        if (getCurState().getBackPage() != null) {
            newState.addChoice(getCurState().getBackLabel(), getCurState().getBackPage());
        }
        
        newState.execute();
    }
}
