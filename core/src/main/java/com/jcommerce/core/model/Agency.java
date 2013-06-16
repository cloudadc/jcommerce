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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "agency", catalog = "ishop")
public class Agency extends ModelObject {
	
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
    
	private static final long serialVersionUID = 7320919343871010164L;
	private String name;
    private String description;
    
    private Set<AdminUser> adminUsers = new HashSet<AdminUser>();
    private Set<Order> orders = new HashSet<Order>();
    private Set<Region> regions = new HashSet<Region>();
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "agency"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "agency_id", nullable = false  )
	public Set<AdminUser> getAdminUsers() {
		return this.adminUsers;
	}
    
    public void addAdminUser(AdminUser adminUser) {
		adminUser.setAgency(this);
		this.adminUsers.add(adminUser);
	}
    
    public void setAdminUsers(final Set<AdminUser> adminUser) {
		this.adminUsers = adminUser;
	}

    @Basic( optional = true )
	@Column( name = "agency_name", length = 255  )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic( optional = true )
	@Column( name = "agency_desc", length = 2147483647  )
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "agency"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "agency_id", nullable = false  )
	public Set<Order> getOrders() {
		return this.orders;
	}
    
    public void addOrder(Order order) {
		order.setAgency(this);
		this.orders.add(order);
	}
    
    public void setOrders(final Set<Order> order) {
		this.orders = order;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "agency"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "agency_id", nullable = false  )
	public Set<Region> getRegions() {
		return this.regions;	
	}
    
    public void addRegion(Region region) {
		region.setAgency(this);
		this.regions.add(region);
	}
    
    public void setRegions(final Set<Region> region) {
		this.regions = region;
	}

}
