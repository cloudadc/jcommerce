/**
 * @author KingZhao
 *         Kylin Soong
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "article", catalog = "ishop")
public class Article extends ModelObject {
	
private String id;
    
	@Id 
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	private static final long serialVersionUID = 2724625262432800742L;
	private ArticleCategory articleCategory;
	private String title;
	
	private String content;
	private String author;
	private String authorEmail;
	private String keywords;
	private int articleType;
	private boolean open;
	private Timestamp addTime;
	private String fileURL;
	private boolean openType;
	private String link;
	
	private Set<GoodsArticle> goodsArticles = new HashSet<GoodsArticle>();


	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "cat_id", nullable = true )
	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	@Basic( optional = true )
	@Column( length = 150  )
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Basic( optional = true )
	@Column( length = 2147483647  )
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Basic( optional = true )
	@Column( length = 30  )
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Basic( optional = true )
	@Column( name = "author_email", length = 60  )
	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
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
	@Column( name = "article_type"  )
	public int getArticleType() {
		return articleType;
	}

	public void setArticleType(int articleType) {
		this.articleType = articleType;
	}

	@Basic( optional = true )
	@Column( name = "is_open"  )
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Basic( optional = true )
	@Column( name = "add_time"  )
	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	@Basic( optional = true )
	@Column( name = "file_url", length = 255  )
	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	@Basic( optional = true )
	@Column( name = "open_type"  )
	public boolean getOpenType() {
		return openType;
	}

	public void setOpenType(boolean openType) {
		this.openType = openType;
	}

	@Basic( optional = true )
	@Column( length = 255  )
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "article"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "article_id", nullable = false  )
	public Set<GoodsArticle> getGoodsArticles() {
		return this.goodsArticles;
		
	}
	
	public void addGoodsArticle(GoodsArticle goodsArticle) {
		goodsArticle.setArticle(this);
		this.goodsArticles.add(goodsArticle);
	}
	
	public void setGoodsArticles(final Set<GoodsArticle> goodsArticle) {
		this.goodsArticles = goodsArticle;
	}

}
