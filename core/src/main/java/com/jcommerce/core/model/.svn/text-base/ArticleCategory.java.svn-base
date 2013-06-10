/**
 * @author KingZhao
 * @date 2008.9.22
 */
package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

public class ArticleCategory extends ModelObject {
	
	private static final int TYPE_INNEWS=1;//站内快讯
	private static final int TYPE_SYSTEMMSG=2;//系统分类
	private static final int TYPE_SHOPMSG=3;//网店信息	
	private static final int TYPE_HELPMSG=4;//网店帮助分类	
	
	private String name;
	private int type;//文章类型
	private String keywords;
	private String description;//类型描述
	private int sortOrder;
	private boolean showInNavigator;//是否在导航中显示
	private ArticleCategory parent;
	Set<ArticleCategory> children = new HashSet<ArticleCategory>();


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isShowInNavigator() {
		return showInNavigator;
	}

	public void setShowInNavigator(boolean showInNavigator) {
		this.showInNavigator = showInNavigator;
	}

	public ArticleCategory getParent() {
		return parent;
	}

	public void setParent(ArticleCategory parent) {
		if (parent == getParent()) {
			return;
		}

		if (getParent() != null) {
			getParent().removeChild(this);
		}
		this.parent = parent;
		if (this.parent != null) {
			this.parent.addChild(this);
		}
	}

	public Set<ArticleCategory> getChildren() {
		return children;
	}

	public void setChildren(Set<ArticleCategory> children) {
		if (children == null) {
			this.children.clear();
		} else {
			this.children = children;
		}
	}

	public void addChild(ArticleCategory child) {
		children.add(child);
		child.parent = this;
	}

	public void removeChild(ArticleCategory child) {
		children.remove(child);
		child.parent = null;
	}
}
