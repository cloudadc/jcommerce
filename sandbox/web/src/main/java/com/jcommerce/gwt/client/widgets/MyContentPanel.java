package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;

public class MyContentPanel extends ContentPanel{
	
	public void setHeading(String text, Button button) {
		super.setHeading(text);
		head.insertTool(button, 0);
	}
}
