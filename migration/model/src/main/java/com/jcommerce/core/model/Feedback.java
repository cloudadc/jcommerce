/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Feedback extends ModelObject {
    public static final int M_MESSAGE = 0; // 留言
    public static final int M_COMPLAINT = 1; // 投诉
    public static final int M_ENQUIRY = 2; // 询问
    public static final int M_CUSTOME = 3; // 售后
    public static final int M_BUY = 4; // 求购
    public static final int M_BUSINESS = 5; // 商家

	private Feedback parent;
	Set<Feedback> children = new HashSet<Feedback>();
	private User user;
	private String userName;
	private String userEmail;
	private String msgTitle;
	private int msgType;
	private String msgContent;
	private Timestamp msgTime;
	private String messageImage;
	private int orderID;

	public Feedback getParent() {
		return parent;
	}

	public void setParent(Feedback parent) {
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

	public Set<Feedback> getChildren() {
		return children;
	}

	public void setChildren(Set<Feedback> children) {
		if (children == null) {
			this.children.clear();
		} else {
			this.children = children;
		}
	}

	public void addChild(Feedback child) {
		children.add(child);
		child.parent = this;
	}

	public void removeChild(Feedback child) {
		children.remove(child);
		child.parent = null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Timestamp getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Timestamp msgTime) {
		this.msgTime = msgTime;
	}

	public String getmessageImage() {
		return messageImage;
	}

	public void setmessageImage(String messageImage) {
		this.messageImage = messageImage;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

}
