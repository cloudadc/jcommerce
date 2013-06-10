/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.widgets;

import java.util.Date;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

public class DateWidget extends Composite {
    private DatePicker dp_start = new DatePicker();
    
    private TextBox txt_start_date = new TextBox();
    
    private Date start_date;
    
    private PopupPanel pp_start = new PopupPanel();
    
    private DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");

    public DateWidget() {
        HTML btn_img_start = new HTML("<div style='width:20px;'><input type='image' src='date.gif'/></div>");
        btn_img_start.setWidth("20");
        
        txt_start_date.setWidth("100");
        
        dp_start.setVisible(false);
        pp_start.setWidget(dp_start);
        pp_start.hide();
        
        dp_start.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent be) {
                start_date = (Date) dp_start.getValue();
                String str_start_date = format.format(start_date);
                txt_start_date.setText(str_start_date);
                dp_start.setVisible(false);
                pp_start.hide();
            }
        });
        txt_start_date.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                if(dp_start.isVisible()){
                    dp_start.setVisible(false);
                    pp_start.hide();
                }else{
                    pp_start.setPopupPosition(txt_start_date.getAbsoluteLeft(), txt_start_date.getAbsoluteTop());
                    pp_start.show();
                    dp_start.setVisible(true);
                }
            }
        });
        btn_img_start.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                if(dp_start.isVisible()){
                    dp_start.setVisible(false);
                    pp_start.hide();
                }else{
                    pp_start.show();
                    dp_start.setVisible(true);
                }
            }
        });
        
        HorizontalPanel contentPanel = new HorizontalPanel();
        
        contentPanel.add(txt_start_date);
//        contentPanel.add(btn_img_start);

        initWidget(contentPanel);
    }
    
    public Date getValue() {
        return start_date;
    }
    
    public void setValue(Date d) {
        this.start_date = d;

        String str_start_date = format.format(start_date);
        txt_start_date.setText(str_start_date);        
    }
    
    public void setEnabled(boolean enabled) {
        txt_start_date.setEnabled(enabled);
    }
}
