package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.widget.form.RadioGroup;

/**
 * This class overcome a problem (perhaps a bug) in GXT RadioGroup.
 * See afterRender()
 * @author yli
 *
 */
public class MyRadioGroup extends RadioGroup {
	String myName;
	public MyRadioGroup() {
		super();
	}

	public MyRadioGroup(String name) {
		super(name);
		this.myName = name;
	}
	
	@Override
	public void afterRender() {
		super.afterRender();
		
    	// this is hacking...
    	// see Field.getName and setName,  
    	// we have to add it here to ensure the name is set after render, 
    	// thus it can go into DOM  
    	// otherwise, in DOM the "name" attribute will be sth like "gxt.RadioGroup.0"
		
		String name = getName();
		System.out.println("===name: "+name);
		setName(myName);
	}
	
	public void setName(String name) {
		super.setName(name);
		this.myName = name;
	}
	
	
}
