/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.widgets;

import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.Utils;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;

public class ValueSelector extends Composite {
    private TextBox text = new TextBox();
    

	private Long id = null;
    private com.extjs.gxt.ui.client.widget.button.Button btn = 
    		new com.extjs.gxt.ui.client.widget.button.Button("搜索");
    private String caption = "Select Value";
    private String message = "";
    private String bean = null;
    
    
    public void enable()
    {
    	btn.enable();
    }
    
    public void disable()
    {
    	btn.disable();
    }
    
    public ValueSelector() {
        HorizontalPanel contentPanel = new HorizontalPanel();
        text.setReadOnly(true);
        contentPanel.add(text);
        
        btn.setText("搜索");
        btn.setHeight("25");
        btn.setWidth("65");
        
        contentPanel.add(btn);

        initWidget(contentPanel);
        btn.addSelectionListener(new SelectionListener<ButtonEvent>()
        {
			public void componentSelected(ButtonEvent ce)
			{
				DialogBox dlg = createDialogBox();
				dlg.center();
				dlg.show();
			}
        	
        });
        
        
        
//        btn.addClickListener(new ClickListener() {
//
//            public void onClick(Widget sender) {
//                DialogBox dlg = createDialogBox();
//                dlg.center();
//                dlg.show();
//            }
//            
//        });
    }
    
    public TextBox getTextBox() {
		return text;
	}
    
    public void setValue(Long id) {
        this.id = id;
        if (id == null || id < 0) {
            return;
        }
        
        new ReadService().getBean(bean, id, new ReadService.Listener() {
            public void onSuccess(BeanObject bean) {
                text.setText((String)bean.get("name"));
            }
        });
    }
    
    public Long getValue() {
        return id;
    }
    
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    private DialogBox createDialogBox() {
        // Create a dialog box and set the caption text
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText(caption);

        // Create a table to layout the content
        VerticalPanel dialogContents = new VerticalPanel();
        dialogContents.setSpacing(4);
        dialogBox.setWidget(dialogContents);

        // Add some text to the top of the dialog
        HTML details = new HTML(message);
        dialogContents.add(details);

        final ListBox listAll = new ListBox();
        listAll.setWidth("15em");
        listAll.setVisibleItemCount(20);
        dialogContents.add(listAll);

        if (bean == null) {        	
            throw new RuntimeException("bean == null");
        }
        
        new ListService().listBeans(bean, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator it = beans.iterator(); it.hasNext();) {
                    BeanObject data = (BeanObject) it.next();                    
                    listAll.addItem((String)data.get("name"), data.get("id")+"");
                }
            }
        });
        
        HorizontalPanel holder = new HorizontalPanel();
        holder.setSpacing(20);
        
        // Add a close button at the bottom of the dialog
        Button btnOK = new Button("OK", new ClickListener() {
            public void onClick(Widget sender) {
                id = Long.valueOf(Utils.getSelectedValue(listAll));
                text.setText(Utils.getSelectedText(listAll));
                dialogBox.hide();
            }
        });
        holder.add(btnOK);

        Button btnCancel = new Button("Cancel", new ClickListener() {
            public void onClick(Widget sender) {
                dialogBox.hide();
            }
        });
        holder.add(btnCancel);
        
        dialogContents.add(holder);
        
        return dialogBox;
    }

	public Long getId() {
		return id;
	}
}
