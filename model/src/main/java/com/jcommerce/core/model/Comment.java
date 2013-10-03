/**
* Author: Kylin Soong
*         
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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment extends ModelObject {
	
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

	private static final long serialVersionUID = -6671573012929381400L;
	public static final int COMMENTTYPE_GOODS=0;//评论商品
	public static final int COMMENTTYPE_ARTICLE=1;//评论文章		
	
	private int commentType;
	private String idValue;//评论对象ID
	private String email;
	private User user;
	private String userName;
	private String content;
	private int commentRank;//评论等级
	private Timestamp addTime;
	private String ipAddress;//评论人的IP
	private boolean status;//是否允许显示评论
	private Comment parent;
	private Set<Comment> children = new HashSet<Comment>();
	
	@Basic( optional = true )
	@Column( name = "comment_type"  )
	public int getCommentType() {
		return commentType;
	}

	public void setCommentType(int commentType) {
		this.commentType = commentType;
	}
	
	@Basic( optional = true )
	@Column( name = "id_value", length = 255  )
	public String getIdValue() {
		return idValue;
	}

	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	@Basic( optional = true )
	@Column( length = 60  )
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "user_id", nullable = true )
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	@Column( name = "comment_rank"  )
	public int getCommentRank() {
		return commentRank;
	}

	public void setCommentRank(int commentRank) {
		this.commentRank = commentRank;
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
	@Column( name = "ip_address", length = 15  )
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "parent_id", nullable = true )
	public Comment getParent() {
		return parent;
	}

	public void setParent(Comment parent) {
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
	@Column( name = "comment_id", nullable = false  )
	public Set<Comment> getChildren() {
		return children;
	}

	public void setChildren(Set<Comment> children) {
		if (children == null) {
			this.children.clear();
		} else {
			this.children = children;
		}
	}

	public void addChild(Comment child) {
		children.add(child);
		child.parent = this;
	}

	public void removeChild(Comment child) {
		children.remove(child);
		child.parent = null;
	}

	@Basic( optional = true )
	@Column( name = "user_name", length = 60  )
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
