package com.jcommerce.gwt.client.panels;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.gwt.user.client.Window;

public abstract class BaseFileUploadFormPanel extends BaseEntityEditPanel {

    @Override
    protected void afterRender() {
    	super.afterRender();
        formPanel.setEncoding(FormPanel.Encoding.MULTIPART);
        formPanel.setMethod(FormPanel.Method.POST);
        formPanel.addListener(Events.Submit, new Listener<FormEvent>() {
			public void handleEvent(FormEvent be) {
				String result = be.getResultHtml();
				// TODO result is somehow become sth like <pre style="word-wrap: break-word; white-space: pre-wrap;">0</pre>
				// here we workaround the problem, but need to figure out what happened.
				
				if(result!=null && result.contains("200 OK")) {
					gotoSuccessPanel();
				}
				else {
					Window.alert("Error: "+result);	
				}
				} 
        });

	}

    @Override
    protected void submit() {
    	formPanel.submit();
    }

}
