package com.jcommerce.core.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Comment extends ModelObject {

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
	Set<Comment> children = new HashSet<Comment>();

	public int getCommentType() {
		return commentType;
	}

	public void setCommentType(int commentType) {
		this.commentType = commentType;
	}
	
	public String getIdValue() {
		return idValue;
	}

	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCommentRank() {
		return commentRank;
	}

	public void setCommentRank(int commentRank) {
		this.commentRank = commentRank;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
