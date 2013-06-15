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
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "shipping", catalog = "ishop")
public class Shipping extends ModelObject {

	private static final long serialVersionUID = -7896034244403964599L;

	private Set<ShippingArea> shippingAreas = new HashSet<ShippingArea>();  
	private Set<Order> orders = new HashSet<Order>();
    
	private String id;
	private String name;
	private String code;
	private String description;
	private String insure;
	private boolean supportCod;
	private boolean enabled;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Basic( optional = true )
	@Column( name = "shipping_name", length = 120  )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic( optional = true )
	@Column( name = "shipping_code", length = 20  )
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic( optional = true )
	@Column( name = "shipping_desc", length = 255  )
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic( optional = true )
	@Column( length = 10  )
    public String getInsure() {
        return insure;
    }

    public void setInsure(String insure) {
        this.insure = insure;
    }

    @Basic( optional = true )
	@Column( name = "support_cod"  )
    public boolean isSupportCod() {
        return supportCod;
    }

    public void setSupportCod(boolean supportCod) {
        this.supportCod = supportCod;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "shipping"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "shipping_id", nullable = false  )
    public Set<ShippingArea> getShippingAreas() {
        return shippingAreas;
    }
    
    public void addShippingArea(ShippingArea shippingArea) {
		shippingArea.setShipping(this);
		this.shippingAreas.add(shippingArea);
	}

    public void setShippingAreas(Set<ShippingArea> shippingAreas) {
        this.shippingAreas = shippingAreas;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "shipping"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "shipping_id", nullable = false  )
	public Set<Order> getOrders() {
		return this.orders;
	}
    
    public void addOrder(Order order) {
		order.setShipping(this);
		this.orders.add(order);
	}
    
    public void setOrders(final Set<Order> order) {
		this.orders = order;
	}
}
