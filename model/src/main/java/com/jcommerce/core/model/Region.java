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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "region")
public class Region extends ModelObject {
	
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

	private static final long serialVersionUID = 3591141458001056801L;
	public static final int TYPE_COUNTRY = 0;
    public static final int TYPE_PROVINCE = 1;
    public static final int TYPE_CITY = 2;
    public static final int TYPE_DISTRICT = 3;

    public static final int LEVEL_ONE = TYPE_COUNTRY;    
    public static final int LEVEL_TWO = TYPE_PROVINCE;    
    public static final int LEVEL_THREE = TYPE_CITY;    
    public static final int LEVEL_FOUR = TYPE_DISTRICT;    
    
    private Region parent;
    
    private String name;
    private int type;
    private Agency agency;
    private Set<ShippingArea> areas = new HashSet<ShippingArea>();
    
    private Set<Order> orders = new HashSet<Order>();
    private Set<UserAddress> userAddresses = new HashSet<UserAddress>();
    private Set<Region> children = new HashSet<Region>();

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "parent_id", nullable = true )
    public Region getParent() {
        return parent;
    }

    public void setParent(Region parent) {
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
	@Column( name = "region_id", nullable = false  )
    public Set<Region> getChildren() {
        return children;
    }

    public void setChildren(Set<Region> children) {
        if (children == null) {
            this.children.clear();
        } else {
            this.children = children;
        }
    }

    public void addChild(Region child) {
        children.add(child);
        child.parent = this;
    }
    
    public void removeChild(Region child) {
        children.remove(child);
        child.parent = null;
    }
    
    @Basic( optional = true )
	@Column( name = "region_name", length = 120  )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic( optional = true )
	@Column( name = "region_type"  )
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = true )
	@JoinColumn(name = "agency_id", nullable = true )
    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @ManyToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "regions"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
    public Set<ShippingArea> getAreas() {
        return areas;
    }

    public void setAreas(Set<ShippingArea> areas) {
        if (areas == null) {
            this.areas.clear();
        } else {
            this.areas = areas;
        }
    }
    
    public void addArea(ShippingArea area) {
        if (!areas.contains(area)) {
            this.areas.add(area);
//            area.addRegion(this);
        }
    }
    
    public void removeArea(ShippingArea area) {
        if (areas.contains(area)) {
            this.areas.remove(area);
//            area.removeRegion(this);
        }
    }
    
    private boolean isCountry;
    
    private boolean isProvince;
    
    private boolean isCity;
    
    private boolean isDistrict;

    public boolean isCountry() {
    	isCountry = (type == TYPE_COUNTRY);
        return isCountry;
    }

	public void setCountry(boolean isCountry) {
		this.isCountry = isCountry;
	}

	public boolean isProvince() {
		isProvince = (type == TYPE_PROVINCE);
        return isProvince ;
    }

	public void setProvince(boolean isProvince) {
		this.isProvince = isProvince;
	}
    
    public boolean isCity() {
    	isCity = (type == TYPE_CITY);
		return isCity ;
	}

	public void setCity(boolean isCity) {
		this.isCity = isCity;
	}
	
	public boolean isDistrict() {
		isDistrict = (type == TYPE_DISTRICT);
		return isDistrict;
	}

	public void setDistrict(boolean isDistrict) {
		this.isDistrict = isDistrict;
	}

	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "region"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "region_id", nullable = false  )
	public Set<Order> getOrders() {
		return this.orders;
	}
    
    public void addOrder(Order order) {
		order.setRegion(this);
		this.orders.add(order);
	}
    
    public void setOrders(final Set<Order> order) {
		this.orders = order;
	}
    
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "region"  )
 	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@Basic( optional = false )
	@Column( name = "region_id", nullable = false  )
	public Set<UserAddress> getUserAddresses() {
		return this.userAddresses;
	}
    
    public void addUserAddress(UserAddress userAddress) {
		userAddress.setRegion(this);
		this.userAddresses.add(userAddress);
	}
    
    public void setUserAddresses(final Set<UserAddress> userAddress) {
		this.userAddresses = userAddress;
	}
}
