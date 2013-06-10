package com.jcommerce.gwt.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;


public class ChoicePanel extends Composite{
	private String selectedValue = null;	
	private List<Item> itemList = new ArrayList<Item>();	
	private ListBox listBox = new ListBox();
	 
	
	public void setSelectValue(String selectedValue) {
		this.selectedValue = selectedValue;
		setSelectedValue(this.listBox,selectedValue);
	}
	
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	
	public ChoicePanel(String selectedValue,List<Item> itemList){
		this.selectedValue = selectedValue;
		this.itemList = itemList;		
		refresh();
	}
	
	public void refresh(){
		HorizontalPanel contentPanel = new HorizontalPanel();
		if(this.itemList == null){
		return;
		}
		int selectedIndex = 0;
		listBox.setSelectedIndex(0);
		for(Item item:this.itemList){			
			listBox.addItem(item.name,item.value);
			if(selectedValue.equals(item.value))
				listBox.setSelectedIndex(selectedIndex);			
			selectedIndex++;
		}
		contentPanel.add(listBox);
        initWidget(contentPanel);
	}
	
	 public static void setSelectedValue(ListBox list, String value) {
	        if (value == null) {
	            value = "";
	        }	        
	        int size = list.getItemCount();
	        for (int i = 0 ; i < size ; i++) {
	            if (value.equals(list.getValue(i))) {
	                list.setSelectedIndex(i);
	                break;
	            }
	        }
	    }
	
	public String getValue(){
		int index = this.listBox.getSelectedIndex();
			return this.listBox.getValue(index);
	}
	
	public static class Item{
		private String name;
		private String value;
		
		public Item(String name,String value){
			this.name = name;
			this.value = value;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}
	
}
