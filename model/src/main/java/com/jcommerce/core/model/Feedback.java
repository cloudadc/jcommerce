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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback extends ModelObject {
	
private String id;
    
	@Id 
	@GeneratedValue
	@Basic( optional = false )
	@Column( name = "id", nullable = false, length = 32  )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	private static final long serialVersionUID = -3223417619084896383L;
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

	@ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "parent_id", nullable = true )
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

	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "parent"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "msg_id", nullable = false  )
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
	@Column( name = "user_name", length = 60  )
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Basic( optional = true )
	@Column( name = "user_email", length = 60  )
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Basic( optional = true )
	@Column( name = "msg_title", length = 200  )
	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	@Basic( optional = true )
	@Column( name = "msg_type"  )
	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	@Basic( optional = true )
	@Column( name = "msg_content", length = 2147483647  )
	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	@Basic( optional = true )
	@Column( name = "msg_time"  )
	public Timestamp getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Timestamp msgTime) {
		this.msgTime = msgTime;
	}

	@Basic( optional = true )
	@Column( name = "message_img", length = 255  )
	public String getmessageImage() {
		return messageImage;
	}

	public void setmessageImage(String messageImage) {
		this.messageImage = messageImage;
	}

	@Basic( optional = true )
	@Column( name = "order_id"  )
	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

}
