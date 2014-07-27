/**
* Author: Kylin Soong
*         
*/

package com.jcommerce.core.service.impl;

import java.util.ArrayList;
import java.util.List;


public class ShopConfigMeta {
	

	
	public ShopConfigMeta(String group, String code, String defaultValue, String type,
			String label, String[][] storeRange, String tip) {
		super();
		this.code = code;
		this.storeRange = new ArrayList<String[]>();
		if(storeRange!=null) {
			for(String[] s : storeRange) {
				this.storeRange.add(s);
			}
		}
		this.type = type;
		this.label = label;
		this.tip = tip;
		this.group = group;
		// assign value and defaultValue
		this.defaultValue = defaultValue;
		this.value = defaultValue;
	}



	private String code;
	
	// meta
	private List<String[]> storeRange;
	private final String type;
	private final String label;
	private final String group;
	private final String tip;
	private final String defaultValue;
	
	// data
	private Long pkId;
	private String value;




	public String getLabel() {
		return label;
	}


	public String getGroup() {
		return group;
	}




	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}







	public String getType() {
		return type;
	}





	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getTip() {
		return tip;
	}


	public String getDefaultValue() {
		return defaultValue;
	}


	public List<String[]> getStoreRange() {
		return storeRange;
	}


	public void setStoreRange(List<String[]> storeRange) {
		this.storeRange = storeRange;
	}


	
	
	
}
