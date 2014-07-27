package com.jcommerce.web.to;

import java.util.ArrayList;
import java.util.List;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.ModelObject;

public class CategoryWrapper extends BaseModelWrapper {

	private static final long serialVersionUID = -1748347056785402637L;

	List<CategoryWrapper> children = new ArrayList<CategoryWrapper>();
	
	Category category;

	protected Object getWrapped() {
		return getCategory();
	}
	
	public CategoryWrapper(ModelObject category) {
		super();
		this.category = (Category)category;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public String getName(){
		return category.getName();
	}
	
	public String getUrl() {
		return "category.action?id="+category.getId();
	}
	
	public List<CategoryWrapper> getChildren() {
		return children;
	}
	
	public void setChildren(List<CategoryWrapper> children) {
		this.children = children;
	}

	

	
}
