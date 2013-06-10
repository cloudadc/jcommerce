package com.jcommerce.gwt.client.panels.goods;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.iShop;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.panels.goods.NewAttribute.State;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * Example file.
 */
public class NewGoodsType extends ContentWidget {
    private Button btnNew = new Button();    
    private Button btnCancel = new Button();    
    private ColumnPanel contentPanel = new ColumnPanel();   

//    private boolean editting = false;
//    private BeanObject goodsType = null;
    
    /**
     * Initialize this example.
     */
    public NewGoodsType() {
    }
    
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
    public static class State extends PageState {
        private BeanObject goodsType = null;
        
        public BeanObject getGoodsType() {
            return goodsType;
        }
        public void setGoodsType(BeanObject goodsType) {
            this.goodsType = goodsType;
            setEditting(goodsType != null);
        }
        
        public String getPageClassName() {
            return NewGoodsType.class.getName();
        }
        public String getMenuDisplayName() {
            return !isEditting() ? "新建商品类型" : "编辑商品类型";
        }
    }
    
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
        if(!getCurState().isEditting())
    	return "新建商品类型";
        else
        	return "编辑商品类型";
    }
    
//    public void setGoodsType(BeanObject goodsType) {
//        this.goodsType = goodsType;
//        editting = goodsType != null;
//    }
//    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        System.out.println("----------NewGoodsType");
        add(contentPanel);
       
        contentPanel.createPanel(IGoodsType.NAME, "商品类型名称:", new TextBox());
        contentPanel.add(new Label("注：每行一个商品属性组。排序也将按照自然顺序排序。")); 
        TextArea attributeGroup = new TextArea();
        attributeGroup.setSize("500", "400");
        contentPanel.createPanel(IGoodsType.ATTRIBUTEGROUP, "属性分组:", attributeGroup);       
        contentPanel.createPanel(IGoodsType.ENABLED, "是否启用：", new CheckBox());        
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");        
        btnCancel.setText("重置");
        panel.add(btnNew);        
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);      
        
        btnNew.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                BeanObject goodsType = getCurState().getGoodsType();
                
                String id = goodsType != null ? goodsType.getString(IGoodsType.ID) : null;
                goodsType = new BeanObject(ModelNames.GOODSTYPE, contentPanel.getValues());
                if (getCurState().isEditting()) {
                    new UpdateService().updateBean(id, goodsType, null);
                    Info.display("恭喜", "完成修改商品类型.");
                    GoodsTypeInfo.State state = new GoodsTypeInfo.State();
                    state.execute();
                } else {
                    new CreateService().createBean(goodsType, new CreateService.Listener() {
                        public synchronized void onSuccess(String id) { 
                            Info.display("恭喜", "完成添加商品类型.");
                            GoodsTypeInfo.State state = new GoodsTypeInfo.State();
                            state.execute();
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
        BeanObject goodsType = getCurState().getGoodsType();
        
        if (goodsType!=null&&goodsType.getString(IGoodsType.ID) != null) {
        	
            Map<String, Object> mapGoodsType = goodsType.getProperties();
            contentPanel.updateValues(mapGoodsType);
        }
        else{
        	contentPanel.clearValues();
            getCurState().setEditting(false);
            }
    }
}
