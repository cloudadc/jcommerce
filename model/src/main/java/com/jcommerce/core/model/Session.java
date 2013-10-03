/**
 * Author: Bob Chen
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
@Table(name = "sessions")
public class Session extends ModelObject {
	
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

	private static final long serialVersionUID = -8702132984985132894L;
	private Timestamp expiry;
	private User user;
	private String IP;
	private String data;
	
	private Set<Cart> carts = new HashSet<Cart>();

    public Timestamp getExpiry() {
        return expiry;
    }

    public void setExpiry(Timestamp expiry) {
        this.expiry = expiry;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic( optional = true )
    @JoinColumn(name = "userid", nullable = true )
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic( optional = true )
	@Column( length = 15  )
    public String getIP() {
        return IP;
    }

    public void setIP(String ip) {
        IP = ip;
    }

    @Basic( optional = true )
	@Column( length = 2147483647  )
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "session"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( nullable = false  )
	public Set<Cart> getCarts() {
		return this.carts;
	}
    
    public void addCart(Cart cart) {
		cart.setSession(this);
		this.carts.add(cart);
	}
    
    public void setCarts(final Set<Cart> cart) {
		this.carts = cart;
	}

}
