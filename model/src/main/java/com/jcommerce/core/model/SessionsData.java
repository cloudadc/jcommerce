/**
 * @author KingZhao
 *         Kylin Soong
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
@Table(name = "sessions_data")
public class SessionsData extends ModelObject {
	
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

	private static final long serialVersionUID = 7092032939069062524L;
	private String sesskey;
	private Timestamp expiry;
	private String data;

	@Basic( optional = true )
	@Column( length = 32  )
	public String getSesskey() {
		return sesskey;
	}

	public void setSesskey(String sesskey) {
		this.sesskey = sesskey;
	}

	public Timestamp getExpiry() {
		return expiry;
	}

	public void setExpiry(Timestamp expiry) {
		this.expiry = expiry;
	}

	@Basic( optional = true )
	@Column( length = 2147483647  )
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
