/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.client.panels.promote;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBonusType;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.DateWidget;
import com.jcommerce.gwt.client.widgets.RadioPanel;

/**
 * Example file.
 */
public class NewBonusPanel extends ContentWidget {    	
    private DateWidget startSendDate = new DateWidget();
    private DateWidget endSendDate = new DateWidget();
    private TextBox maxAmount = new TextBox();
    
    private Button btnNew = new Button();    
    private Button btnCancel = new Button();    
    private ColumnPanel contentPanel = new ColumnPanel();
   
    public static class State extends PageState {
        private BeanObject bonus = null;
        
        public BeanObject getBonus() {
            return bonus;
        }

        public void setBonus(BeanObject bonus) {
            this.bonus = bonus;
            setEditting(bonus != null);
        }

        public String getPageClassName() {
            return NewBonusPanel.class.getName();
        }
        public String getMenuDisplayName() {
            return !isEditting() ? "添加红包类型" : "编辑红包类型";
        }
    }
    
    public State getCurState() {
        if (curState == null ) {
            curState = new State();
        }
        return (State)curState;
    }
    
    public NewBonusPanel() {
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if(!getCurState().isEditting())
        	return "添加红包类型";
        else
            return "编辑红包类型"; 	
    }
    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
        add(contentPanel);
       
        contentPanel.createPanel(IBonusType.TYPE_NAME, "类型名称:", new TextBox());
        contentPanel.createPanel(IBonusType.TYPE_MONEY, "红包金额:", new TextBox());
        contentPanel.createPanel(IBonusType.MIN_GOODS_AMOUNT, "最小订单金额:", new TextBox());
        
        RadioPanel radios = new RadioPanel(IBonusType.SEND_TYPE);
        radios.addButton(IBonusType.SEND_BY_USER, "按用户发放");
        radios.addButton(IBonusType.SEND_BY_GOODS, "按商品发放");
        radios.addButton(IBonusType.SEND_BY_ORDER, "按订单金额发放");
        radios.addButton(IBonusType.SEND_BY_PRINT, "线下发放的红包");
        
        radios.addListener(new RadioPanel.Listener() {
            
            public void buttonClicked(int value) {
                maxAmount.setVisible(value == IBonusType.SEND_BY_ORDER);
                startSendDate.setEnabled(value == IBonusType.SEND_BY_ORDER || value == IBonusType.SEND_BY_GOODS);
                endSendDate.setEnabled(value == IBonusType.SEND_BY_ORDER || value == IBonusType.SEND_BY_GOODS);
            }
        });
        
        contentPanel.createPanel(radios.getName(), "如何发放此类型红包:", radios);
        contentPanel.createPanel(IBonusType.MIN_AMOUNT, "订单下限:", maxAmount);
        contentPanel.createPanel(IBonusType.SEND_START_DATE, "发放起始日期:", startSendDate);
        contentPanel.createPanel(IBonusType.SEND_END_DATE, "发放结束日期:", endSendDate);
        contentPanel.createPanel(IBonusType.USE_START_DATE, "使用起始日期:", new DateWidget());
        contentPanel.createPanel(IBonusType.USE_END_DATE, "使用结束日期:", new DateWidget());
        
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");
        btnCancel.setText("重置");
        panel.add(btnNew);
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);        

        btnNew.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                BeanObject bonus = new BeanObject(ModelNames.BONUSTYPE, contentPanel.getValues());
                if (getCurState().isEditting()) {
                    String id = getCurState().getBonus().getString(IBonusType.ID);
                    new UpdateService().updateBean(id, bonus, null);
                    BonusListPanel.State state = new BonusListPanel.State();
                    state.execute();
                } else {
                    new CreateService().createBean(bonus, new CreateService.Listener() {
                        public void onSuccess(String id) {
                            BonusListPanel.State state = new BonusListPanel.State();
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
        BeanObject bonus = getCurState().getBonus();
        
        if (bonus!=null && bonus.getString(IBonusType.ID) != null) {            
            Map<String, Object> mapBonus = bonus.getProperties();
            contentPanel.updateValues(mapBonus);            
        } else {
            contentPanel.clearValues();
            getCurState().setBonus(null);
        }
    }
}
