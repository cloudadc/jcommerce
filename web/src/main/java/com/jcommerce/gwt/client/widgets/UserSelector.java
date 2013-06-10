package com.jcommerce.gwt.client.widgets;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.Button;
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

/**
 * @author monkey
 */

public class UserSelector extends Composite {
	private TextBox text = new TextBox();
    private String id = null;
    private String textContent = null;
    private Button btn = new Button();
    
    private String caption = "Select Value";
    private String message = "";
    private String bean = null;
    private boolean hasData = false;
    
    public UserSelector() {
    	HorizontalPanel contentPanel = new HorizontalPanel();
        text.setReadOnly(false);
        contentPanel.add(text);
        
        btn.setText("搜索");
        btn.setHeight("25");
        btn.setWidth("65");
        
        contentPanel.add(btn);

        initWidget(contentPanel);
        
        btn.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                DialogBox dlg = createDialogBox();
                dlg.center();
                dlg.show();
            }
            
        });
    }    
    
    public void setText(String content) {
    	text.setText(content);
    }
    
    public void setValue(String id) {
        this.id = id;
        if (id == null || id.trim().length() == 0) {
            return;
        }
        
        new ReadService().getBean(bean, id, new ReadService.Listener() {
            public void onSuccess(BeanObject bean) {
                text.setText((String)bean.get("name"));
            }
        });
    }
    
    public String getValue() {
        return id;
    }
    
    /**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the bean
	 */
	public String getBean() {
		return bean;
	}

	/**
	 * @param bean the bean to set
	 */
	public void setBean(String bean) {
		this.bean = bean;
	}

	/**
	 * set button enable state.
	 */
	public void setButtonState(boolean state) {
		btn.setEnabled(state);
	}
	
	/**
     * Create the user selection result dialog box.
     * If the text content is null, then return the listBox with top 20 users.
     * Else if the text not null, then return the result searched by approximate match.
     * @return dialogBox
     */
    private DialogBox createDialogBox() {
    	/* Create a dialog box and set the caption text */
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText(caption);
        
        /* Create a table to layout the content */
        VerticalPanel dialogContents = new VerticalPanel();
        dialogContents.setSpacing(4);
        dialogBox.setWidget(dialogContents);
        
        /* Add some text to the top of the dialog */
        HTML details = new HTML(message);
        dialogContents.add(details);

        /* Add ListBox on this panel */
        final ListBox listAll = new ListBox();
        listAll.setWidth("15em");
        listAll.setVisibleItemCount(20);
        dialogContents.add(listAll);
        
        if (bean == null) {        	
            throw new RuntimeException("bean == null");
        }
        /* If the text content is null, get the top 20 users */
        if(text.getText() == null || text.getText().trim().equals("")) {      
        	hasData = false;
        } else {
        	hasData = true;
        	textContent = text.getText().trim();
        }
        new ListService().listBeans(bean, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject data = it.next();  
                    /* If the text has data, get the content, then search the users. */
                    if(hasData) {
                    	if(((String)data.get("name")).contains(textContent)) {
                    		listAll.addItem((String)data.get("name"), data.get("id") + "");
                    	}
                    }else{
                        listAll.addItem((String)data.get("name"), data.get("id") + "");
                    }
                }
            }
        });
        
        HorizontalPanel holder = new HorizontalPanel();
        holder.setSpacing(20);
        
        // Add a close button at the bottom of the dialog
        Button btnOK = new Button("OK", new ClickListener() {
            public void onClick(Widget sender) {
                id = Utils.getSelectedValue(listAll);
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
}
