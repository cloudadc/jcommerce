/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.IShopServiceAsync;
import com.jcommerce.gwt.client.Utils;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;

public class MultiValueSelector extends Composite {
    private ListBox list = new ListBox(); 
    private ArrayList<String> ids = new ArrayList<String>();
    
    private String caption = "Select Value";
    private String message = "";
    private String bean = null;
    
    public MultiValueSelector() {
        HorizontalPanel contentPanel = new HorizontalPanel();
        VerticalPanel lpanel = new VerticalPanel(); 
        VerticalPanel rpanel = new VerticalPanel();
        contentPanel.add(lpanel);
        contentPanel.add(rpanel);
        
        list.setWidth("11em");
        list.setVisibleItemCount(4);
        lpanel.add(list);
        
        Button btnAdd = new Button();
        btnAdd.setText("Add");
        btnAdd.setHeight("25");        
        btnAdd.setWidth("70");
        rpanel.add(btnAdd);

        btnAdd.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                DialogBox dlg = createDialogBox();
                dlg.center();
                dlg.show();
            }
            
        });

        Button btnDelete = new Button();
        btnDelete.setText("Delete");
        btnDelete.setHeight("25");
        btnDelete.setWidth("70");
        rpanel.add(btnDelete);

        btnDelete.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                int index = list.getSelectedIndex();
                if (index >= 0) {
                    list.removeItem(index);
                }
            }            
        });

        initWidget(contentPanel);        
    }
    
    /**
     * the value format is a,b,c
     */
    public void setValue(String id) {
        ids.clear();
        list.clear();
    	if (id == null || id.trim().length() == 0) {
            return;
        }
        String[] a = id.split(",");
        for (int i = 0; i < a.length; i++) {
            ids.add(a[i]);
        }
        
        for (int i = 0; i < a.length; i++) {
            new ReadService().getBean(bean, a[i], new ReadService.Listener() {
                public void onSuccess(BeanObject bean) {
                    list.addItem(bean.getString("name"));
                }
            });
        }
    }
    
    public String getValue() {
        String id = null;
        for (Iterator it = ids.iterator(); it.hasNext();) {
            String _id = (String) it.next();
            if (id == null) {
                id = _id;
            } else {
                id += "," + _id;
            }
        }
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
                    listAll.addItem(data.getString("name"), data.getString("id"));
                }
            }
        });

        HorizontalPanel holder = new HorizontalPanel();
        holder.setSpacing(20);
        
        // Add a close button at the bottom of the dialog
        Button btnOK = new Button("OK", new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                dialogBox.hide();

                String id = Utils.getSelectedValue(listAll);
                if (!ids.contains(id)) {
                    ids.add(id);
                    list.addItem(Utils.getSelectedText(listAll));
                } else {
                    Utils.showErrorDialog("Duplicate value not allowed!");
                }
            }
        });
        holder.add(btnOK);

        Button btnCancel = new Button("Cancel", new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                dialogBox.hide();
            }
        });
        holder.add(btnCancel);
        
        dialogContents.add(holder);
        
        return dialogBox;
    }
}
