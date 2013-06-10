/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsAttribute;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class AttributePanel extends ContentWidget {
    public static interface Constants {
        String Attribute_name();
        String Attribute_goodsType();
        String Attribute_retrieve();
        String Attribute_relateSameGoods();
        String Attribute_attributeSelection();
        String Attribute_inputType();
        String Attribute_valueList();
        String Attribute_yes();
        String Attribute_no();
        String Attribute_select();
        String Attribute_noRetrieve();
        String Attribute_keywordRetrieve();
        String Attribute_scaleRetrieve();
        String Attribute_onlyAttribute();
        String Attribute_simpleAttribute();
        String Attribute_multipleAttribute();
        String Attribute_addSuccessfully();
        String Attribute_modifySuccessfully();
        String Attribute_manualInput();
        String Attribute_listInput();
        String Attribute_textAreaInput();
    }
    
    public static class State extends BaseEntityEditPanel.State {
        public static final String SELECTED_GOODSTYPE_ID = "sgtid";
        public String getPageClassName() {
            return AttributePanel.class.getName();
        }
        public void setSelectedGoodsTypeID(String sgtid) {
            setValue(SELECTED_GOODSTYPE_ID, sgtid);
        }
        public String getSelectedGoodsTypeID() {
            return (String)getValue(SELECTED_GOODSTYPE_ID);
        }
    }

    SimplePanel contentPanel = new SimplePanel();
    ListBox lstTypes = new ListBox();
    Map<String, BeanObject> types = new HashMap<String, BeanObject>();
    
    protected String getEntityClassName() {
        return ModelNames.ATTRIBUTE;
    }

    @Override
    public String getName() {
        return Resources.constants.AttributeList_title(); 
    } 

    private static AttributePanel instance;

    
    public static AttributePanel getInstance() {
        if(instance == null) {
            instance = new AttributePanel();
        }
        return instance;
    }
    
    public AttributePanel() {
        init();
    }
    
    void updateValues(final BeanObject goods) {
        if (goods == null) {
            lstTypes.setSelectedIndex(0);
        } else {
            String type = goods.getString(IGoods.TYPE);
            int size = lstTypes.getItemCount();
            for (int i = 0; i < size; i++) {
                if (type != null && type.equals(lstTypes.getValue(i))) {
                    lstTypes.setSelectedIndex(i);
                    break;
                }
            }

            Criteria criteria = new Criteria();
            criteria.addCondition(new Condition(IAttribute.GOODSTYPE, Condition.EQUALS, type));
            new ListService().listBeans(ModelNames.ATTRIBUTE, criteria, new ListService.Listener() {
                public void onSuccess(List<BeanObject> attrs) {
                    createWidgets(attrs);
                    updateGoodsAttributes(goods);
                }
            });
        }
    }
    
    private void updateGoodsAttributes(BeanObject goods) {
        String[] gattrs = goods.getIDs(IGoods.ATTRIBUTES);
        if (gattrs != null) {
            final ColumnPanel panel = (ColumnPanel)contentPanel.getWidget();
            for (final String attr : gattrs) {
                new ReadService().getBean(ModelNames.GOODSATTRIBUTE, attr, new ReadService.Listener() {
                    public void onSuccess(BeanObject bean) {
                        panel.setValue(bean.getString(IGoodsAttribute.ATTRIBUTE), bean.getString(IGoodsAttribute.VALUE));
                    }
                });
            }
        }        
    }
    
    Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<String, Object>();
        int index = lstTypes.getSelectedIndex();
        if (index >= 0) {
            String type = lstTypes.getValue(index);
            values.put(IGoods.TYPE, type);
        }
        
        final ColumnPanel panel = (ColumnPanel)contentPanel.getWidget();
        Map<String, Object> attrsPanel = panel.getValues();
        
        List<Map> attrs = new ArrayList<Map>();
        for (Iterator it = attrsPanel.keySet().iterator(); it.hasNext();) {
            String id = (String) it.next();
            Map<String, Object> attr = new HashMap<String, Object>();
            attr.put(IGoodsAttribute.ATTRIBUTE, id);
            attr.put(IGoodsAttribute.VALUE, attrsPanel.get(id));
            attrs.add(attr);
        }
        values.put(IGoods.ATTRIBUTES, attrs);
        
        return values;
    }
    
    private void init() {
        lstTypes.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent arg0) {
                updateType(lstTypes);
            }
        });

        new ListService().listBeans(ModelNames.GOODSTYPE, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (BeanObject obj : beans) {
                    String id = obj.getString(IGoodsType.ID);
                    lstTypes.addItem(obj.getString(IGoodsType.NAME), id);
                    types.put(id, obj);
                }
                
                updateType(lstTypes);
            }
        });
        add(lstTypes);
        add(contentPanel);              
    }

    private void updateType(ListBox lstTypes) {
        int index = lstTypes.getSelectedIndex();
        if (index < 0) {
            return;
        }

        String type = lstTypes.getValue(index);
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IAttribute.GOODSTYPE, Condition.EQUALS, type));
        new ListService().listBeans(ModelNames.ATTRIBUTE, criteria, new ListService.Listener() {
            public void onSuccess(List<BeanObject> attrs) {
                createWidgets(attrs);
            }
        });
    }
    
    private void createWidgets(List<BeanObject> attrs) {
        ColumnPanel panel = new ColumnPanel();
        
        for (BeanObject attr : attrs) {
            String id = attr.getString(IAttribute.ID);
            String name = attr.getString(IAttribute.NAME);            
            int inputType = ((Number)attr.get(IAttribute.INPUTTYPE)).intValue();
            String values = attr.getString(IAttribute.VALUES);
            if (inputType == IAttribute.INPUT_SINGLELINETEXT) {
                panel.createPanel(id, name, new TextBox());
            } else if (inputType == IAttribute.INPUT_MULTIPLELINETEXT) {
                panel.createPanel(id, name, new TextArea());
            } else if (inputType == IAttribute.INPUT_CHOICE) {
                ListBox lst = new ListBox();
                if (values != null) {
                    String[] vs = values.split("\n");
                    for (String s : vs) {
                        lst.addItem(s);
                    }
                }
                panel.createPanel(id, name, lst);
            } else {
                throw new RuntimeException("Unknown input type: " + inputType);
            }
        }
        
        contentPanel.setWidget(panel);
    }

    @Override
    public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
