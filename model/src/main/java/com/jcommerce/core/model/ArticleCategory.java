/**
 * @author KingZhao
 * @date 2008.9.22
 * @author Kylin Soong
 * @date 2013.6.15
 */
package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "article_category")
public class ArticleCategory extends ModelObject {
	
	private Long id;
	
	@Id 
	@GeneratedValue
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private static final long serialVersionUID = 7429311667993573426L;
	private static final int TYPE_INNEWS=1;//站内快讯
	private static final int TYPE_SYSTEMMSG=2;//系统分类
	private static final int TYPE_SHOPMSG=3;//网店信息	
	private static final int TYPE_HELPMSG=4;//网店帮助分类	
	
	private Set<Article> articles = new HashSet<Article>();
		
	private String name;
	private int type;//文章类型
	private String keywords;
	private String description;//类型描述
	private int sortOrder;
	private boolean showInNavigator;//是否在导航中显示
	private ArticleCategory parent;
	private Set<ArticleCategory> children = new HashSet<ArticleCategory>();


	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "articleCategory"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "cat_id", nullable = false  )
	public Set<Article> getArticles() {
		return this.articles;
	}
	
	public void addArticle(Article article) {
		article.setArticleCategory(this);
		this.articles.add(article);
	}
	
	public void setArticles(final Set<Article> article) {
		this.articles = article;
	}
	
	@Basic( optional = true )
	@Column( name = "cat_name", length = 255  )
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic( optional = true )
	@Column( name = "cat_type"  )
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Basic( optional = true )
	@Column( length = 255  )
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Basic( optional = true )
	@Column( name = "cat_desc", length = 255  )
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic( optional = true )
	@Column( name = "sort_order"  )
	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Basic( optional = true )
	@Column( name = "show_in_nav"  )
	public boolean isShowInNavigator() {
		return showInNavigator;
	}

	public void setShowInNavigator(boolean showInNavigator) {
		this.showInNavigator = showInNavigator;
	}

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "parent_id", nullable = true )
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

	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "parent"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "cat_id", nullable = false  )
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
