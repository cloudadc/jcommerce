/**
 * Author: Bob Chen
 * 		   Kylin Soong	
 */

package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin_message")
public class AdminMessage extends ModelObject {
	
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

	private static final long serialVersionUID = -2014815197374233891L;
	private int senderID; // can be a user ID or a admin ID
    private int receiverID; // can be a user ID or a admin ID
    private Timestamp sentTime;
    private Timestamp readTime;
    private boolean readed;
    private boolean deleted;
    private String title;
    private String message;

    @Basic( optional = true )
	@Column( name = "sender_id"  )
    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    @Basic( optional = true )
	@Column( name = "receiver_id"  )
    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    @Basic( optional = true )
	@Column( name = "sent_time"  )
    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    @Basic( optional = true )
	@Column( name = "read_time"  )
    public Timestamp getReadTime() {
        return readTime;
    }

    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
