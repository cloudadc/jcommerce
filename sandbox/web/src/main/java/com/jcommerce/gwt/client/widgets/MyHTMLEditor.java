package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.widget.form.HtmlEditor;

public class MyHTMLEditor extends HtmlEditor {
	
	  @Override
	  public void syncValue() {
		  System.out.println("syncValue()");
		  // temporary solution for the issue that it's not rendered when submitting
		  // TODO however a bug is the existing value will be clear after submission
		  try {
			  
			  super.syncValue();
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
}
