/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table(name="ishop_Region")
public class Region extends ModelObject {

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
    private transient Set<Region> children = new HashSet<Region>();
    private String name;
    private int type;
    private Agency agency;
    private Set<ShippingArea> areas = new HashSet<ShippingArea>();

    @ManyToOne
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @ManyToOne
    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @ManyToMany  
    @JoinTable(name="ishop_region_areas") 
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

    public boolean isCountry() {
        return type == TYPE_COUNTRY;
    }

    public boolean isProvince() {
        return type == TYPE_PROVINCE;
    }

    public boolean isCity() {
        return type == TYPE_CITY;
    }

    public boolean isDistrict() {
        return type == TYPE_DISTRICT;
    }
}
