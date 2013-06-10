package com.jcommerce.web.to;

import java.util.Date;

import com.jcommerce.core.model.Article;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.gwt.client.util.URLConstants;

public class ArticleWrapper extends BaseModelWrapper implements URLConstants{
	Article article;
	@Override
	protected Object getWrapped() {
		return getArticle();
	}
	public ArticleWrapper(ModelObject article) {
		super();
		this.article = (Article)article;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public String getUrl() {
		return "article.action?id="+article.getId();
	}
	public String getShortTitle() {
		if(article.getTitle().length()>15){
			return article.getTitle().substring(0, 15) + "..."; 
		}
		return article.getTitle();
	}
	public String getAddTime(){
		return new Date(article.getAddTime().getTime()).toLocaleString();
	}
	
}
