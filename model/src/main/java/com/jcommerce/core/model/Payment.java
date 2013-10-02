/**
 * Author: Bob Chen
 *         Kylin Soong
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment extends ModelObject {
	
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
    
	private static final long serialVersionUID = 4013558398428845761L;
	/**
	 * the payment code
	 */
	private String code;
	private String name;
	private String fee;
	private String description;
	/**
	 *  payment sort order
	 */
	private int order;
	private String config;
	/**
	 * true if installed
	 */
	private boolean enabled;
	private boolean cod;
	private boolean online;
	
	private Set<Order> orders = new HashSet<Order>();

	
	@Basic( optional = true )
	@Column( name = "pay_code", length = 20  )
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic( optional = true )
	@Column( name = "pay_name", length = 120  )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic( optional = true )
	@Column( name = "pay_fee", length = 10  )
    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Basic( optional = true )
	@Column( name = "pay_desc", length = 2147483647  )
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic( optional = true )
	@Column( name = "pay_order"  )
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Basic( optional = true )
	@Column( name = "pay_config", length = 2147483647  )
    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Basic( optional = true )
	@Column( name = "is_cod"  )
    public boolean isCod() {
        return cod;
    }

    public void setCod(boolean cod) {
        this.cod = cod;
    }

    @Basic( optional = true )
	@Column( name = "is_online"  )
    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "payment"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "pay_id", nullable = false  )
	public Set<Order> getOrders() {
		return this.orders;
	}
    
    public void addOrder(Order order) {
		order.setPayment(this);
		this.orders.add(order);
	}
    
    public void setOrders(final Set<Order> order) {
		this.orders = order;
	}

}
