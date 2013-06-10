package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.panels.data.ImportPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ChoicePanel;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * Example file.
 */
public class NewAttribute extends ContentWidget {
    private Button btnNew = new Button();    
    private Button btnCancel = new Button();    
    private ColumnPanel contentPanel = new ColumnPanel();   

//    private BeanObject attribute = null;
    private ListBox lstGoodsType = new ListBox();
    /**
     * Initialize this example.
     */
    public NewAttribute() {
    }
    
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
    public static class State extends PageState {
        private BeanObject attribute = null;
        
        public void setAttribute(BeanObject attribute) {
            this.attribute = attribute;
            setEditting(attribute != null);
        }
        
        public BeanObject getAttribute() {
            return attribute;
        }

        public String getPageClassName() {
            return NewAttribute.class.getName();
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

    public String getDescription() {
        return "cwBasicTextDescription";
    }

    private boolean isEditting() {
        return getCurState().isEditting();
    }
    
    public String getName() {
        return !isEditting() ? "添加属性" : "编辑属性";
    }
    
//    public void setAttribute(BeanObject attribute) {
//        this.attribute = attribute;
//        getCurState().setEditting(attribute != null);
//    }
//    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);        
        add(contentPanel);       
        contentPanel.createPanel(IAttribute.NAME, "属性名称:", new TextBox()); 
        contentPanel.createPanel(IAttribute.GOODSTYPE, "所属商品类型：", lstGoodsType);
        //加一个判断，动态创建属性分组控件
        //。。。。
        //。。。。
        List<ChoicePanel.Item> indexItems = new ArrayList<ChoicePanel.Item>();
		indexItems.add(new ChoicePanel.Item("不需要检索", "0"));
		indexItems.add(new ChoicePanel.Item("关键字检索", "1"));		
		indexItems.add(new ChoicePanel.Item("范围检索", "2"));
		ChoicePanel showIndex = new ChoicePanel("0",indexItems); 
		contentPanel.createPanel(IAttribute.INDEX, "能否进行检索:", showIndex);
		
		List<ChoicePanel.Item> linkedItems = new ArrayList<ChoicePanel.Item>();
		linkedItems.add(new ChoicePanel.Item("否", "0"));
		linkedItems.add(new ChoicePanel.Item("是", "1"));		
		ChoicePanel showLinked = new ChoicePanel("0",linkedItems); 
        contentPanel.createPanel(IAttribute.LINKED, "相同属性值的商品是否关联：", showLinked);
        
        List<ChoicePanel.Item> typeItems = new ArrayList<ChoicePanel.Item>();
		typeItems.add(new ChoicePanel.Item("否", "0"));
		typeItems.add(new ChoicePanel.Item("是", "1"));	
		ChoicePanel showType = new ChoicePanel("0",typeItems); 
        contentPanel.createPanel(IAttribute.TYPE, "属性是否可选：", showType);
		
        List<ChoicePanel.Item> inputTypeItems = new ArrayList<ChoicePanel.Item>();
		inputTypeItems.add(new ChoicePanel.Item("手工录入", "0"));
		inputTypeItems.add(new ChoicePanel.Item("从下面的列表中选择（一行代表一个可选值）", "1"));		
		inputTypeItems.add(new ChoicePanel.Item("多行文本框", "2"));
		ChoicePanel showInputType = new ChoicePanel("0",inputTypeItems); 
        contentPanel.createPanel(IAttribute.INPUTTYPE, "该属性值的录入方式：", showInputType);
        
        TextArea valuesArea = new TextArea();
        valuesArea.setSize("200", "100");
        contentPanel.createPanel(IAttribute.VALUES, "可选值列表：", valuesArea);        
              
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");        
        btnCancel.setText("重置");
        panel.add(btnNew);        
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);      
        
        btnNew.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                BeanObject attribute = getCurState().getAttribute();
                String id = attribute != null ? attribute.getString(IGoodsType.ID) : null;
                attribute = new BeanObject(ModelNames.ATTRIBUTE, contentPanel.getValues());
                if (isEditting()) {
                    new UpdateService().updateBean(id, attribute, null);
                    Info.display("恭喜", "完成编辑属性.");
                    GoodsTypeInfo.State state = new GoodsTypeInfo.State();
                    state.execute();
                } else {
                    new CreateService().createBean(attribute, new CreateService.Listener() {
                        public synchronized void onSuccess(String id) { 
                            Info.display("恭喜", "完成添加属性.");
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
    	lstGoodsType.clear();
		lstGoodsType.addItem(Resources.constants.AttributeList_all_GoodsType(),"0");
		lstGoodsType.setSelectedIndex(0);
		
        final BeanObject attribute = getCurState().getAttribute();
        
		new ListService().listBeans(ModelNames.GOODSTYPE,new ListService.Listener() {									
					public synchronized void onSuccess(List<BeanObject> result) {
						int index = 1 ;
						for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
							BeanObject goodsType = it.next();
							lstGoodsType.addItem(goodsType.getString(IGoodsType.NAME),goodsType.getString(IGoodsType.ID));							
							if(attribute!= null && attribute.getString(IAttribute.GOODSTYPE).equals(goodsType.getString(IGoodsType.ID))){
								lstGoodsType.setSelectedIndex(index);
							}
		                    index++;
						}						
					}
		});
        if (attribute != null && attribute.getString(IAttribute.ID) != null) {        	
            Map<String, Object> mapGoodsType = attribute.getProperties();
            contentPanel.updateValues(mapGoodsType);
        }
        else{
        	contentPanel.clearValues();
            getCurState().setEditting(false);
        }
    }	
}
