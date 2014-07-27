package com.jcommerce.web.to;

import java.util.ArrayList;
import java.util.List;

import com.jcommerce.core.model.ArticleCategory;
import com.jcommerce.core.model.ModelObject;

public class ArticleCategoryWrapper extends BaseModelWrapper {

	private static final long serialVersionUID = 6866015169737655171L;

	List<ArticleCategoryWrapper> children = new ArrayList<ArticleCategoryWrapper>();
	
	ArticleCategory articleCat;
	@Override
	protected Object getWrapped() {
		return getCategory();
	}
	public ArticleCategoryWrapper(ModelObject articleCat) {
		super();
		this.articleCat = (ArticleCategory)articleCat;
	}
	
	public ArticleCategory getCategory() {
		return articleCat;
	}
	
	public String getUrl() {
		return "articleCat.action?id="+articleCat.getId();
	}
	public List<ArticleCategoryWrapper> getChildren() {
		return children;
	}
	public void setChildren(List<ArticleCategoryWrapper> children) {
		this.children = children;
	}

	public String getName(){
		return articleCat.getName();
	}

	
}
