/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.widget.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;

public class RadioPanel extends Composite {
    String name;
    List<Choice> buttons = new ArrayList<Choice>();
    ClickHandler  clickHandler = null;
    HorizontalPanel contentPanel = new HorizontalPanel();
    Listener listener;
    
    public RadioPanel(String name) {
        this.name = name;
        
        initWidget(contentPanel);
        
        clickHandler = new ClickHandler() {            
            public void onClick(ClickEvent evt) {
                if (listener == null) {
                    return;
                }
                
                for (Choice choice : buttons) {
                    if (choice.button == evt.getSource()) {
                        listener.buttonClicked(choice.value);
                        break;
                    }
                }
            }
        };
    }
    
    public void addButton(int value, String text) {
        RadioButton button = new RadioButton(name, text);
        button.addClickHandler(clickHandler);
        
        Choice choice = new Choice();
        choice.button = button;
        choice.text = text;
        choice.value = value;
        buttons.add(choice);
        
        contentPanel.add(button);
        
        Label label = new Label();
        label.setWidth(20);
        contentPanel.add(label);
    }
    
    public void addListener(Listener listener) {
        this.listener = listener;
    }
    
    public int getValue() {
        for (Choice choice : buttons) {
            if (choice.button.isChecked()) {
                return choice.value;
            }
        }
        
        return -1;
    }
    
    public void setValue(int value) {
        for (Choice choice : buttons) {
            if (choice.value == value) {
                choice.button.setChecked(true);
                if (listener != null) {
                    listener.buttonClicked(choice.value);
                }
            }
        }
    }
    
    public String getName() {
        return name;
    }
    
    public void setEnabled(boolean enabled) {
        for (Choice choice : buttons) {
            choice.button.setEnabled(enabled);
        }        
    }

    public void setEnabled(int value, boolean enabled) {
        for (Choice choice : buttons) {
            if (choice.value == value) {
                choice.button.setEnabled(enabled);
            }
        }        
    }
    
    private class Choice {
        RadioButton button;
        String text;
        int value;
    }
    
    public static interface Listener {
        public void buttonClicked(int value);
    }
}
