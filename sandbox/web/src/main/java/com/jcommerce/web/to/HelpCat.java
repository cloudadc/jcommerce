package com.jcommerce.web.to;

import java.util.List;

public class HelpCat {
	String catName;
	List<HelpItem> article;
	
	
	public HelpCat(String catName, List<HelpItem> article) {
		super();
		this.catName = catName;
		this.article = article;
	}


	public static class HelpItem {
		public HelpItem(String url, String title, String shortTitle) {
			super();
			this.url = url;
			this.title = title;
			this.shortTitle = shortTitle;
		}
		String url;
		String title;
		String shortTitle;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getShortTitle() {
			return shortTitle;
		}
		public void setShortTitle(String shortTitle) {
			this.shortTitle = shortTitle;
		}
		
	}


	public String getCatName() {
		return catName;
	}


	public void setCatName(String catName) {
		this.catName = catName;
	}


	public List<HelpItem> getArticle() {
		return article;
	}


	public void setArticle(List<HelpItem> article) {
		this.article = article;
	}


}
